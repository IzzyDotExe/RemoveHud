package ca.blutopia.removehud.mixin;

import ca.blutopia.removehud.ModConfig;
import net.minecraft.client.gui.hud.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.JumpingMount;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(InGameHud.class)
public abstract class RemoveHudButNotHand {

    @Shadow protected abstract void renderVignetteOverlay(Entity entity);

    @Shadow protected abstract void renderOverlay(Identifier texture, float opacity);

    @Shadow protected abstract void renderHotbar(float tickDelta, MatrixStack matrices);

    @Shadow public abstract void renderExperienceBar(MatrixStack matrices, int x);

    @Shadow protected abstract void renderCrosshair(MatrixStack matrices);

    @Shadow protected abstract void renderPortalOverlay(float nauseaStrength);

    @Shadow protected abstract void renderSpyglassOverlay(float scale);

    @Shadow protected abstract void renderStatusBars(MatrixStack matrices);

    @Shadow public abstract void renderMountJumpBar(JumpingMount mount, MatrixStack matrices, int x);

    @Shadow protected abstract void renderMountHealth(MatrixStack matrices);

    @Shadow public abstract void renderHeldItemTooltip(MatrixStack matrices);

    @Shadow protected abstract void renderStatusEffectOverlay(MatrixStack matrices);

    @Shadow protected abstract void renderScoreboardSidebar(MatrixStack matrices, ScoreboardObjective objective);

    @Shadow protected abstract void renderAutosaveIndicator(MatrixStack matrices);

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHotbar(FLnet/minecraft/client/util/math/MatrixStack;)V"))
    public void renderHotBarMix(InGameHud instance, float tickDelta, MatrixStack matrices) {
        if (ModConfig.INSTANCE.HotBar) {
            renderHotbar(tickDelta, matrices);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderCrosshair(Lnet/minecraft/client/util/math/MatrixStack;)V"))
    public void renderCrossHairsMix(InGameHud instance, MatrixStack matrices) {
        if (ModConfig.INSTANCE.Crosshairs) {
            renderCrosshair(matrices);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderVignetteOverlay(Lnet/minecraft/entity/Entity;)V"))
    public void renderVignette(InGameHud instance, Entity entity) {
        if (ModConfig.INSTANCE.Vignette) {
            renderVignetteOverlay(entity);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderOverlay(Lnet/minecraft/util/Identifier;F)V"))
    public void renderOverlays(InGameHud instance, Identifier texture, float opacity) {
        if (ModConfig.INSTANCE.OtherOverlays) {
            renderOverlay(texture, opacity);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderPortalOverlay(F)V"))
    public void renderPortalOverlays(InGameHud instance, float nauseaStrength) {
        if (ModConfig.INSTANCE.PortalOverlay) {
            renderPortalOverlay(nauseaStrength);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderSpyglassOverlay(F)V"))
    public void renderSpyglassOverlays(InGameHud instance, float scale) {
        if (ModConfig.INSTANCE.SpyglassOverlay) {
            renderSpyglassOverlay(scale);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderStatusBars(Lnet/minecraft/client/util/math/MatrixStack;)V"))
    public void renderStatusBarsMix(InGameHud instance, MatrixStack matrices) {
        if (ModConfig.INSTANCE.StatusBars) {
            renderStatusBars(matrices);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/BossBarHud;render(Lnet/minecraft/client/util/math/MatrixStack;)V"))
    public void renderBossBarHud(BossBarHud instance, MatrixStack matrices) {
        if (ModConfig.INSTANCE.BossBar) {
            instance.render(matrices);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderMountHealth(Lnet/minecraft/client/util/math/MatrixStack;)V"))
    public void renderMountHealthMix(InGameHud instance, MatrixStack matrices) {
        if (ModConfig.INSTANCE.MountHealth) {
            renderMountHealth(matrices);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderMountJumpBar(Lnet/minecraft/entity/JumpingMount;Lnet/minecraft/client/util/math/MatrixStack;I)V"))
    public void renderMountJumpMix(InGameHud instance, JumpingMount mount, MatrixStack matrices, int x) {
        if (ModConfig.INSTANCE.MountJumpbar) {
            renderMountJumpBar(mount, matrices, x);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHeldItemTooltip(Lnet/minecraft/client/util/math/MatrixStack;)V"))
    public void renderHeldItemTooltipMix(InGameHud instance, MatrixStack matrices) {
        if (ModConfig.INSTANCE.HeldItemTooltip) {
            renderHeldItemTooltip(matrices);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/SpectatorHud;renderSpectatorMenu(Lnet/minecraft/client/util/math/MatrixStack;)V"))
    public void renderSpectatorMenu(SpectatorHud instance, MatrixStack matrices) {
        if (ModConfig.INSTANCE.SpectatorMenu) {
            instance.renderSpectatorMenu(matrices);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/SpectatorHud;render(Lnet/minecraft/client/util/math/MatrixStack;)V"))
    public void renderSpectatorHud(SpectatorHud instance, MatrixStack matrices) {
        if (ModConfig.INSTANCE.SpectatorHud) {
            instance.render(matrices);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderStatusEffectOverlay(Lnet/minecraft/client/util/math/MatrixStack;)V"))
    public void renderStatusEffectOverlay(InGameHud instance, MatrixStack matrices) {
        if (ModConfig.INSTANCE.StatusEffectOverlay) {
            renderStatusEffectOverlay(matrices);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderScoreboardSidebar(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/scoreboard/ScoreboardObjective;)V"))
    public void renderScoreBoard(InGameHud instance, MatrixStack matrices, ScoreboardObjective objective) {
        if (ModConfig.INSTANCE.ScoreBoard) {
            renderScoreboardSidebar(matrices, objective);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/DebugHud;render(Lnet/minecraft/client/util/math/MatrixStack;)V"))
    public void renderDebugHud(DebugHud instance, MatrixStack matrices) {
        if (ModConfig.INSTANCE.DebugHud) {
            instance.render(matrices);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/PlayerListHud;render(Lnet/minecraft/client/util/math/MatrixStack;ILnet/minecraft/scoreboard/Scoreboard;Lnet/minecraft/scoreboard/ScoreboardObjective;)V"))
    public void renderPlayerList(PlayerListHud instance, MatrixStack matrices, int scaledWindowWidth, Scoreboard scoreboard, ScoreboardObjective objective) {
        if (ModConfig.INSTANCE.PlayerList) {
            instance.render(matrices, scaledWindowWidth, scoreboard, objective);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/ChatHud;render(Lnet/minecraft/client/util/math/MatrixStack;III)V"))
    public void renderChatHud(ChatHud instance, MatrixStack matrices, int currentTick, int mouseX, int mouseY) {
        if (ModConfig.INSTANCE.ChatHud) {
            instance.render(matrices, currentTick, mouseX, mouseY);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderAutosaveIndicator(Lnet/minecraft/client/util/math/MatrixStack;)V"))
    public void renderAutosave(InGameHud instance, MatrixStack matrices) {
        if (ModConfig.INSTANCE.Autosave) {
            renderAutosaveIndicator(matrices);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderExperienceBar(Lnet/minecraft/client/util/math/MatrixStack;I)V"))
    public void renderAutosave(InGameHud instance, MatrixStack matrices, int x) {
        if (ModConfig.INSTANCE.ExpBar) {
            renderExperienceBar(matrices, x);
        }
    }

    @Inject(at=@At("HEAD"), method = "render", cancellable = true)
    public void render(CallbackInfo info) {
        if (ModConfig.INSTANCE.removeHud) {
            info.cancel();
        }
    }

}
