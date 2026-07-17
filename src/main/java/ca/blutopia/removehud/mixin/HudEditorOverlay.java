package ca.blutopia.removehud.mixin;

import ca.blutopia.removehud.HudEditorState;
import ca.blutopia.removehud.ModConfig;
import ca.blutopia.removehud.config.HudElement;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Draws a highlight box + label around the currently selected HUD element,
 * plus a controls cheat-sheet, while the in-game editor (F6) is active.
 * Runs at the tail of the normal HUD render pass, so unlike the old
 * Screen-based editor, every element remains fully visible while editing.
 */
@Mixin(Hud.class)
public abstract class HudEditorOverlay {

    private static final String[] CONTROLS = {
            "HUD Editor",
            "Tab: next element   Shift+Tab: next anchor",
            "Arrows: move (Shift = big step)",
            "N: toggle snapping   R: reset element",
            "H: hide this   F6: exit"
    };

    @Inject(method = "extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/DeltaTracker;)V", at = @At("TAIL"))
    private void removehud$renderEditorOverlay(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (!HudEditorState.active) {
            return;
        }

        Font font = Minecraft.getInstance().font;

        HudElement selected = HudEditorState.selected;
        int[] bounds = selected.currentBounds();
        int x = bounds[0];
        int y = bounds[1];
        int w = bounds[2];
        int h = bounds[3];

        graphics.outline(x - 2, y - 2, w + 4, h + 4, 0xFFFFFF00);

        String label = selected.label + "  (" + selected.getX() + ", " + selected.getY() + ")  " + selected.getOrigin();
        // Draw above the box normally, but flip below it if there isn't room (element anchored near the top).
        int labelY = y - 12 >= 0 ? y - 12 : y + h + 4;
        graphics.text(font, label, x - 2, labelY, 0xFFFFFF00);

        if (HudEditorState.showControls) {
            removehud$renderControlsHint(graphics, font);
        }
    }

    private void removehud$renderControlsHint(GuiGraphicsExtractor graphics, Font font) {
        int padding = 4;
        int lineHeight = font.lineHeight + 1;
        int width = 0;
        for (String line : CONTROLS) {
            width = Math.max(width, font.width(line));
        }
        int height = CONTROLS.length * lineHeight;

        int left = 4;
        int top = 4;
        graphics.fill(left, top, left + width + padding * 2, top + height + padding * 2, 0x90000000);

        for (int i = 0; i < CONTROLS.length; i++) {
            int color = i == 0 ? 0xFFFFFF00 : 0xFFFFFFFF;
            graphics.text(font, CONTROLS[i], left + padding, top + padding + i * lineHeight, color);
        }

        String snapping = "Snapping: " + (ModConfig.INSTANCE.OffsetSnapping ? "on (" + ModConfig.INSTANCE.OffsetSnappingStrength + "px)" : "off");
        graphics.text(font, snapping, left + padding, top + padding + CONTROLS.length * lineHeight + 2, 0xFFAAAAAA);
    }
}
