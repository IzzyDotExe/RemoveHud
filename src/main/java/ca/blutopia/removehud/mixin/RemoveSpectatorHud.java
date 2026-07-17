package ca.blutopia.removehud.mixin;

import ca.blutopia.removehud.ModConfig;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.spectator.SpectatorGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpectatorGui.class)
public abstract class RemoveSpectatorHud {

    @Inject(method = "extractHotbar(Lnet/minecraft/client/gui/GuiGraphicsExtractor;)V", at = @At("HEAD"), cancellable = true)
    public void renderSpectatorHotbar(GuiGraphicsExtractor graphics, CallbackInfo ci) {
        if (!ModConfig.INSTANCE.SpectatorHud) {
            ci.cancel();
        }
    }

    @Inject(method = "extractAction(Lnet/minecraft/client/gui/GuiGraphicsExtractor;)V", at = @At("HEAD"), cancellable = true)
    public void renderSpectatorAction(GuiGraphicsExtractor graphics, CallbackInfo ci) {
        if (!ModConfig.INSTANCE.SpectatorMenu) {
            ci.cancel();
        }
    }
}
