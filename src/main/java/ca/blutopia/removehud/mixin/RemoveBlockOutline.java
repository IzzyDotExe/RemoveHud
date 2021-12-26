package ca.blutopia.removehud.mixin;

import ca.blutopia.removehud.ModConfig;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(GameRenderer.class)
public abstract class RemoveBlockOutline {

    @Inject(at=@At("TAIL"), method = "shouldRenderBlockOutline", cancellable = true)
    public void renderOutline(CallbackInfoReturnable<Boolean> cir) {
        if (!ModConfig.INSTANCE.highlightBlocks) {
            cir.setReturnValue(false);
        } else {
            cir.setReturnValue(true);
        }

    }
}
