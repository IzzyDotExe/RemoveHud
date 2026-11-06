package ca.blutopia.removehud.mixin.compat;

import ca.blutopia.removehud.ModConfig;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Litematica draws its HUD-space elements (info HUD, tool HUD, hover info,
 * schematic preview frame) through malilib's RenderEventHandler, which is
 * dispatched from a TAIL injection into Gui.extractRenderState - outside the
 * vanilla Hud extraction that the master toggle (F7) already cancels.
 *
 * Cancelling litematica's gui-overlay hook here hides those elements with F7
 * while leaving its world-space rendering (schematic blocks, selection boxes)
 * untouched. The compat mixin config is non-required and the target is
 * referenced by name, so this silently no-ops when litematica isn't installed.
 */
@Mixin(targets = "fi.dy.masa.litematica.event.RenderHandler")
public abstract class LitematicaHudCompat {

    @Inject(
            method = "onExtractGuiOverlayPost(Lfi/dy/masa/malilib/render/GuiContext;FLnet/minecraft/util/profiling/ProfilerFiller;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void removehud$hideLitematicaHud(@Coerce Object guiContext, float partialTick, ProfilerFiller profiler, CallbackInfo ci) {
        if (ModConfig.INSTANCE.removeHud) {
            ci.cancel();
        }
    }
}
