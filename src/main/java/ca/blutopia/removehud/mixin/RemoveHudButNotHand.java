package ca.blutopia.removehud.mixin;

import ca.blutopia.removehud.ModConfig;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.JumpingMount;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;


@Mixin(InGameHud.class)
public abstract class RemoveHudButNotHand {

    @Shadow protected abstract void renderVignetteOverlay(MatrixStack matrices, Entity entity);

    @Shadow protected abstract void renderOverlay(MatrixStack matrices, Identifier texture, float opacity);

    @Shadow protected abstract void renderHotbar(float tickDelta, MatrixStack matrices);

    @Shadow public abstract void renderExperienceBar(MatrixStack matrices, int x);

    @Shadow protected abstract void renderCrosshair(MatrixStack matrices);

    @Shadow protected abstract void renderPortalOverlay(MatrixStack matrices, float nauseaStrength);

    @Shadow protected abstract void renderSpyglassOverlay(MatrixStack matrices, float scale);

    @Shadow protected abstract void renderStatusBars(MatrixStack matrices);

    @Shadow public abstract void renderMountJumpBar(JumpingMount mount, MatrixStack matrices, int x);

    @Shadow protected abstract void renderMountHealth(MatrixStack matrices);

    @Shadow public abstract void renderHeldItemTooltip(MatrixStack matrices);

    @Shadow protected abstract void renderStatusEffectOverlay(MatrixStack matrices);

    @Shadow protected abstract void renderScoreboardSidebar(MatrixStack matrices, ScoreboardObjective objective);

    @Shadow protected abstract void renderAutosaveIndicator(MatrixStack matrices);

    @Shadow protected abstract void renderHealthBar(MatrixStack matrices, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking);

    @Shadow private int scaledHeight;

    @Shadow private int scaledWidth;

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

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderVignetteOverlay(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/entity/Entity;)V"))
    public void renderVignette(InGameHud instance, MatrixStack matrices, Entity entity) {
        if (ModConfig.INSTANCE.Vignette) {
            renderVignetteOverlay(matrices, entity);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderOverlay(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/Identifier;F)V"))
    public void renderOverlays(InGameHud instance, MatrixStack matrices, Identifier texture, float opacity) {
        if (ModConfig.INSTANCE.OtherOverlays) {
            renderOverlay(matrices, texture, opacity);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderPortalOverlay(Lnet/minecraft/client/util/math/MatrixStack;F)V"))
    public void renderPortalOverlays(InGameHud instance, MatrixStack matrices, float nauseaStrength) {
        if (ModConfig.INSTANCE.PortalOverlay) {
            renderPortalOverlay(matrices, nauseaStrength);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderSpyglassOverlay(Lnet/minecraft/client/util/math/MatrixStack;F)V"))
    public void renderSpyglassOverlays(InGameHud instance, MatrixStack matrices, float scale) {
        if (ModConfig.INSTANCE.SpyglassOverlay) {
            renderSpyglassOverlay(matrices, scale);
        }
    }

    @Redirect(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHealthBar(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/entity/player/PlayerEntity;IIIIFIIIZ)V"))
    private void inj(InGameHud instance, MatrixStack matrices, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking) {

//        switch (ModConfig.INSTANCE.HpBarOrigin) {
//            case TOPLEFT -> {
//                x = 0;
//                y = 0;
//            }
//            case TOPRIGHT -> {
//                x = scaledWidth;
//                y = 0;
//            }
//            case BOTTOMLEFFT -> {
//                x =0;
//                y = scaledHeight;
//            }
//
//            case BOTTONRIGHT -> {
//                x = scaledWidth;
//                y = scaledHeight;
//            }
//            default -> {
//            }
//        }

        if (ModConfig.INSTANCE.HpBar) {
            renderHealthBar(matrices, player, x + ModConfig.INSTANCE.HpXOffset, y + ModConfig.INSTANCE.HpYOffset, lines, regeneratingHeartIndex, maxHealth, lastHealth, health, absorption, blinking);
        }
    }

    @Redirect(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V", ordinal = 0))
    private void inj(MatrixStack matrixStack, int x, int y, int z, int a, int b, int c) {

        if (ModConfig.INSTANCE.ArmorBar) {
            DrawableHelper.drawTexture(matrixStack, x + ModConfig.INSTANCE.ArmorXOffset, y + ModConfig.INSTANCE.ArmorYOffset, z, a,b,c);
        }
    }

    @Redirect(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V", ordinal = 1))
    private void inj1(MatrixStack matrixStack, int x, int y, int z, int a, int b, int c) {

        if (ModConfig.INSTANCE.ArmorBar) {
            DrawableHelper.drawTexture(matrixStack, x + ModConfig.INSTANCE.ArmorXOffset, y + ModConfig.INSTANCE.ArmorYOffset, z, a,b,c);
        }
    }

    @Redirect(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V", ordinal = 2))
    private void inj2(MatrixStack matrixStack, int x, int y, int z, int a, int b, int c) {

        if (ModConfig.INSTANCE.ArmorBar) {
            DrawableHelper.drawTexture(matrixStack, x + ModConfig.INSTANCE.ArmorXOffset, y + ModConfig.INSTANCE.ArmorYOffset, z, a,b,c);
        }
    }


    @Redirect(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V", ordinal = 3))
    private void inj3(MatrixStack matrixStack, int x, int y, int z, int a, int b, int c) {

        if (ModConfig.INSTANCE.HungerBar) {
            DrawableHelper.drawTexture(matrixStack, x + ModConfig.INSTANCE.FoodXOffset, y + ModConfig.INSTANCE.FoodYOffset, z, a,b,c);
        }
    }

    @Redirect(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V", ordinal = 4))
    private void inj4(MatrixStack matrixStack, int x, int y, int z, int a, int b, int c) {

        if (ModConfig.INSTANCE.HungerBar) {
            DrawableHelper.drawTexture(matrixStack, x + ModConfig.INSTANCE.FoodXOffset, y + ModConfig.INSTANCE.FoodYOffset, z, a,b,c);
        }
    }

    @Redirect(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V", ordinal = 5))
    private void inj5(MatrixStack matrixStack, int x, int y, int z, int a, int b, int c) {

        if (ModConfig.INSTANCE.HungerBar) {
            DrawableHelper.drawTexture(matrixStack, x + ModConfig.INSTANCE.FoodXOffset, y + ModConfig.INSTANCE.FoodYOffset, z, a,b,c);
        }
    }

    @Redirect(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V", ordinal = 6))
    private void inj6(MatrixStack matrixStack, int x, int y, int z, int a, int b, int c) {

        if (ModConfig.INSTANCE.AirBar) {
            DrawableHelper.drawTexture(matrixStack, x + ModConfig.INSTANCE.AirXOffset, y + ModConfig.INSTANCE.AirYOffset, z, a,b,c);
        }
    }
    @Redirect(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V", ordinal = 7))
    private void inj7(MatrixStack matrixStack, int x, int y, int z, int a, int b, int c) {

        if (ModConfig.INSTANCE.AirBar) {
            DrawableHelper.drawTexture(matrixStack, x + ModConfig.INSTANCE.AirXOffset, y + ModConfig.INSTANCE.AirYOffset, z, a,b,c);
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

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/BossBarHud;render(Lnet/minecraft/client/util/math/MatrixStack;)V"))
    public void renderBossBar(BossBarHud instance, MatrixStack matrices) {
        if (ModConfig.INSTANCE.BossBar) {
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
