package ca.blutopia.removehud.mixin;

import ca.blutopia.removehud.ModConfig;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.contextualbar.ContextualBar;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ContextualBar.class)
public interface RemoveExperienceLevel {

    @Inject(method = "extractExperienceLevel(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/gui/Font;I)V", at = @At("HEAD"), cancellable = true, remap = true)
    private static void renderExperienceLevel(GuiGraphicsExtractor graphics, Font font, int experienceLevel, CallbackInfo ci) {
        if (!ModConfig.INSTANCE.ExpBar) {
            ci.cancel();
            return;
        }
        graphics.pose().pushMatrix();
        graphics.pose().translate((float) ModConfig.INSTANCE.ExpBarXOffset, (float) ModConfig.INSTANCE.ExpBarYOffset);
    }

    @Inject(method = "extractExperienceLevel(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/gui/Font;I)V", at = @At("RETURN"))
    private static void renderExperienceLevelEnd(GuiGraphicsExtractor graphics, Font font, int experienceLevel, CallbackInfo ci) {
        if (ModConfig.INSTANCE.ExpBar) {
            graphics.pose().popMatrix();
        }
    }
}
