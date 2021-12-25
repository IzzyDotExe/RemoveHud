package ca.blutopia.removehud.mixin;

import ca.blutopia.removehud.ModConfig;
import net.minecraft.client.gui.hud.*;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.annotation.Inherited;

@Mixin(InGameHud.class)
public abstract class RemoveHudButNotHand {

    @Inject(at=@At("HEAD"), method = "render", cancellable = true)
    public void render(CallbackInfo info) {

        if (ModConfig.INSTANCE.toggleMod) {
            info.cancel();
        }

    }


}
