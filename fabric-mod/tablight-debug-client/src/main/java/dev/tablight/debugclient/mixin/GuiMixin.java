/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package dev.tablight.debugclient.mixin;

import com.mojang.blaze3d.vertex.PoseStack;

import dev.tablight.debugclient.event.GuiRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.InteractionResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {
    @Shadow @Final private Minecraft minecraft;

    @Inject(method = "render", at = @At("RETURN"), cancellable = true)
    private void render(PoseStack matrices, float tickDelta, CallbackInfo ci) {
        InteractionResult result = GuiRenderCallback.EVENT.invoker().interact(matrices, tickDelta, minecraft);

        if (result == InteractionResult.FAIL) {
            ci.cancel();
        }
    }
}
