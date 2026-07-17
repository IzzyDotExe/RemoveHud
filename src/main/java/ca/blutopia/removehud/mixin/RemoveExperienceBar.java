package ca.blutopia.removehud.mixin;

import ca.blutopia.removehud.ModConfig;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.contextualbar.ExperienceBar;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ExperienceBar.class)
public abstract class RemoveExperienceBar {

    @Inject(method = "extractBackground(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/DeltaTracker;)V", at = @At("HEAD"), cancellable = true)
    public void renderExperienceBar(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (!ModConfig.INSTANCE.ExpBar) {
            ci.cancel();
            return;
        }
        int vanillaX = graphics.guiWidth() / 2 - 91;
        int vanillaY = graphics.guiHeight() - 29;
        int targetX = ModConfig.INSTANCE.ExpBarOrigin.resolveX(vanillaX, 182);
        int targetY = ModConfig.INSTANCE.ExpBarOrigin.resolveY(vanillaY, 5);
        graphics.pose().pushMatrix();
        graphics.pose().translate((float) (targetX - vanillaX + ModConfig.INSTANCE.ExpBarXOffset), (float) (targetY - vanillaY + ModConfig.INSTANCE.ExpBarYOffset));
    }

    @Inject(method = "extractBackground(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/DeltaTracker;)V", at = @At("RETURN"))
    public void renderExperienceBarEnd(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (ModConfig.INSTANCE.ExpBar) {
            graphics.pose().popMatrix();
        }
    }
}
