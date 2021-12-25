package ca.blutopia.removehud.mixin;

import ca.blutopia.removehud.ModConfig;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.render.item.HeldItemRenderer;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public abstract class RemoveHandButNotHud {

    @Inject(at=@At("HEAD"), cancellable = true, method = "renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V")
    public void render(CallbackInfo info) {
        if (ModConfig.INSTANCE.removeHand) {
            info.cancel();
        }

    }

}
