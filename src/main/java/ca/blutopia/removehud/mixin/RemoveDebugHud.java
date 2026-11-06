package ca.blutopia.removehud.mixin;

import ca.blutopia.removehud.ModConfig;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DebugScreenOverlay.class)
public abstract class RemoveDebugHud {
    @Inject(method = "extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;)V", at = @At("HEAD"), cancellable = true)
    public void renderDebugOverlay(GuiGraphicsExtractor graphics, CallbackInfo ci) {
        // The debug overlay (including entries/charts persisted from the F3 menu
        // via F3+F6) is extracted separately by Gui, outside the Hud extraction
        // that the master toggle cancels - so it must be guarded here too.
        if (!ModConfig.INSTANCE.DebugHud || ModConfig.INSTANCE.removeHud) {
            ci.cancel();
        }
    }
}
