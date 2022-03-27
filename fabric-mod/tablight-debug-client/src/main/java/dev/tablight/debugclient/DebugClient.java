/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package dev.tablight.debugclient;

import dev.tablight.debugclient.event.GuiRenderCallback;

import net.fabricmc.api.ClientModInitializer;

import net.minecraft.client.gui.Font;
import net.minecraft.network.Connection;
import net.minecraft.world.InteractionResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DebugClient implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("DebugClient");
	@Override
	public void onInitializeClient() {
		LOGGER.info("Initializing Tablight debug client...");

		GuiRenderCallback.EVENT.register((matrices, tickDelta, client) -> {
			final int width = client.getWindow().getGuiScaledWidth();
			final int height = client.getWindow().getGuiScaledHeight();
			final Font font = client.font;
			final Connection connection = client.getConnection().getConnection();

			// Average Receive
			final String avgRecText = "Avg Rec: " + connection.getAverageReceivedPackets();
			final int avgRecWidth = width - font.width(avgRecText) - 2;
			final int avgRecHeight = height - font.lineHeight - 2;
			font.drawShadow(matrices, avgRecText, avgRecWidth, avgRecHeight, 14737632);

			// Average Sent
			final String avgSentText = "Avg Sent: " + connection.getAverageSentPackets();
			final int avgSentWidth = width - font.width(avgSentText) - 2;
			final int avgSentHeight = avgRecHeight - font.lineHeight - 2;
			font.drawShadow(matrices, avgSentText, avgSentWidth, avgSentHeight, 14737632);

			return InteractionResult.PASS;
		});
	}
}
