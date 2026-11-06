package ca.blutopia.removehud.mixin.compat;

import ca.blutopia.removehud.ModConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Xaero's Minimap draws its world-space elements (waypoint beams, markers and
 * icons rendered into the world) from a HEAD injection into GuiRenderer.render,
 * via ClientEvents#handleRenderGameOverlayEventPre. That happens after the
 * vanilla HUD extraction, so the master toggle's cancel of Hud.extractRenderState
 * never touches it. Cancelling here hides all in-world markers with F7.
 *
 * The compat mixin config is non-required and the target is referenced by name,
 * so this silently no-ops when Xaero's Minimap isn't installed.
 */
@Mixin(targets = "xaero.common.events.ClientEvents")
public abstract class XaeroWaypointMarkerCompat {

    @Inject(method = "handleRenderGameOverlayEventPre()V", at = @At("HEAD"), cancellable = true)
    private void removehud$hideXaeroWorldMarkers(CallbackInfo ci) {
        if (ModConfig.INSTANCE.removeHud) {
            ci.cancel();
        }
    }
}
