package ca.blutopia.removehud.mixin;

import ca.blutopia.removehud.HUDManager;
import ca.blutopia.removehud.ModConfig;
import ca.blutopia.removehud.RemoveHud;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(GameRenderer.class)
public abstract class RemoveBlockOutline {
    private static final ModConfig CONFIG = RemoveHud.HudManagerInstance.ConfigInstance;
    private static final HUDManager HUD_MANAGER = RemoveHud.HudManagerInstance;
    @Inject(at=@At("TAIL"), method = "shouldRenderBlockOutline", cancellable = true)
    public void renderOutline(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(CONFIG.highlightBlocks);
    }
}
