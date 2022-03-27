/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package dev.tablight.debugclient.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionResult;

public interface GuiRenderCallback {
    Event<GuiRenderCallback> EVENT = EventFactory.createArrayBacked(GuiRenderCallback.class, (listeners) -> (matrices, tickDelta, client) -> {
        for (GuiRenderCallback listener : listeners) {
            InteractionResult result = listener.interact(matrices, tickDelta, client);

            if (result != InteractionResult.PASS) {
                return result;
            }
        }
        return InteractionResult.PASS;
    });

    InteractionResult interact(PoseStack matrices, float tickDelta, Minecraft client);
}
