package ca.blutopia.removehud.mixin;

import ca.blutopia.removehud.ModConfig;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.FirstPersonHandsAndItemsRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.FirstPersonHandsAndItemsRenderState;
import net.minecraft.client.renderer.state.level.PlayerRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 26.3 replaced ItemInHandRenderer with FirstPersonHandsAndItemsRenderer, whose
 * submitHandsWithItems takes render states instead of (LocalPlayer, combinedLight).
 */
@Mixin(FirstPersonHandsAndItemsRenderer.class)
public abstract class RemoveHandButNotHud {

    @Inject(at = @At("HEAD"), cancellable = true, method = "submitHandsWithItems(FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/PlayerRenderState;Lnet/minecraft/client/renderer/state/level/FirstPersonHandsAndItemsRenderState;)V")
    public void render(float partialTick, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, PlayerRenderState playerRenderState, FirstPersonHandsAndItemsRenderState handsState, CallbackInfo info) {
        if (ModConfig.INSTANCE.removeHand) {
            info.cancel();
        }
    }

}
