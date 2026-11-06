package ca.blutopia.removehud.mixin.compat;

import ca.blutopia.removehud.ModConfig;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Xaero's Minimap registers its in-game HUD (minimap panel, entity radar,
 * info popups) as a Fabric HUD element attached after VanillaHudElements
 * MISC_OVERLAYS, so it is normally already skipped by the master toggle's
 * cancel of Hud.extractRenderState. This hook is kept as a direct guard on
 * Xaero's overlay handler so the minimap stays hidden with F7 even if that
 * invocation path changes.
 *
 * The compat mixin config is non-required and the target is referenced by
 * name, so this silently no-ops when Xaero's Minimap isn't installed.
 */
@Mixin(targets = "xaero.common.events.ModClientEvents")
public abstract class XaeroMinimapHudCompat {

    @Inject(
            method = "handleRenderModOverlay(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/DeltaTracker;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void removehud$hideXaeroMinimapHud(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (ModConfig.INSTANCE.removeHud) {
            ci.cancel();
        }
    }
}
