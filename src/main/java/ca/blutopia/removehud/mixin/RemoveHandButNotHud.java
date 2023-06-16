package ca.blutopia.removehud.mixin;

import ca.blutopia.removehud.HUDManager;
import ca.blutopia.removehud.ModConfig;
import ca.blutopia.removehud.RemoveHud;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.render.item.HeldItemRenderer;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public abstract class RemoveHandButNotHud {
    private static final ModConfig CONFIG = RemoveHud.HudManagerInstance.ConfigInstance;
    private static final HUDManager HUD_MANAGER = RemoveHud.HudManagerInstance;
    @Inject(at=@At("HEAD"), cancellable = true, method = "renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V")
    public void render(CallbackInfo info) {
        if (CONFIG.removeHand) {
            info.cancel();
        }
    }

}
