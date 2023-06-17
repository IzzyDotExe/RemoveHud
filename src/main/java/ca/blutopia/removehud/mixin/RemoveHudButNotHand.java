package ca.blutopia.removehud.mixin;

import ca.blutopia.removehud.HUDManager;
import ca.blutopia.removehud.config.ModConfig;
import ca.blutopia.removehud.RemoveHud;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.JumpingMount;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class RemoveHudButNotHand {

    private static final ModConfig CONFIG = RemoveHud.HudManagerInstance.ConfigInstance;
    private static final HUDManager HUD_MANAGER = RemoveHud.HudManagerInstance;

    @Shadow protected abstract void renderVignetteOverlay(DrawContext context, Entity entity);

    @Shadow protected abstract void renderOverlay(DrawContext context, Identifier texture, float opacity);

    @Shadow protected abstract void renderHotbar(float tickDelta, DrawContext context);

    @Shadow public abstract void renderExperienceBar(DrawContext context, int x);

    @Shadow protected abstract void renderCrosshair(DrawContext context);

    @Shadow protected abstract void renderPortalOverlay(DrawContext context, float nauseaStrength);

    @Shadow protected abstract void renderSpyglassOverlay(DrawContext context, float scale);

    @Shadow protected abstract void renderStatusBars(DrawContext context);

    @Shadow public abstract void renderMountJumpBar(JumpingMount mount, DrawContext context, int x);

    @Shadow protected abstract void renderMountHealth(DrawContext context);

    @Shadow public abstract void renderHeldItemTooltip(DrawContext context);

    @Shadow protected abstract void renderStatusEffectOverlay(DrawContext context);

    @Shadow protected abstract void renderScoreboardSidebar(DrawContext context, ScoreboardObjective objective);

    @Shadow protected abstract void renderAutosaveIndicator(DrawContext context);

    @Shadow protected abstract void renderHealthBar(DrawContext context, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking);

    @Shadow private int scaledHeight;

    @Shadow private int scaledWidth;

    @Shadow protected abstract PlayerEntity getCameraPlayer();

    @Shadow @Final private static Identifier ICONS;

    @Shadow private int renderHealthValue;

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHotbar(FLnet/minecraft/client/gui/DrawContext;)V"))
    public void renderHotBarMix(InGameHud instance, float tickDelta, DrawContext context) {
        if (CONFIG.HotBar) {
            renderHotbar(tickDelta, context);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderCrosshair(Lnet/minecraft/client/gui/DrawContext;)V"))
    public void renderCrossHairsMix(InGameHud instance, DrawContext context) {
        if (CONFIG.Crosshairs) {
            renderCrosshair(context);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderVignetteOverlay(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/entity/Entity;)V"))
    public void renderVignette(InGameHud instance, DrawContext context, Entity entity) {
        if (CONFIG.Vignette) {
            renderVignetteOverlay(context, entity);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderOverlay(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/util/Identifier;F)V"))
    public void renderOverlays(InGameHud instance, DrawContext context, Identifier texture, float opacity) {
        if (CONFIG.OtherOverlays) {
            renderOverlay(context, texture, opacity);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderPortalOverlay(Lnet/minecraft/client/gui/DrawContext;F)V"))
    public void renderPortalOverlays(InGameHud instance, DrawContext context, float nauseaStrength) {
        if (CONFIG.PortalOverlay) {
            renderPortalOverlay(context, nauseaStrength);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderSpyglassOverlay(Lnet/minecraft/client/gui/DrawContext;F)V"))
    public void renderSpyglassOverlays(InGameHud instance, DrawContext context, float scale) {
        if (CONFIG.SpyglassOverlay) {
            renderSpyglassOverlay(context, scale);
        }
    }

    @Redirect(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHealthBar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/entity/player/PlayerEntity;IIIIFIIIZ)V"))
    private void inj(InGameHud instance, DrawContext context, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking) {


        if (CONFIG.HpBar) {

            int xoffset = HUD_MANAGER.getHpXOffset();
            int yoffset = HUD_MANAGER.getHpYOffset();

            int[] origin = HUD_MANAGER.calculateHpOriginPoints(x, y, lines, regeneratingHeartIndex, maxHealth, lastHealth, health, absorption, scaledWidth, scaledHeight);

            int xorigin = origin[0];
            int yorigin = origin[1];

            renderHealthBar(

                    context, player,

                    xorigin + xoffset,
                    yorigin + yoffset,

                    lines, regeneratingHeartIndex, maxHealth,
                    lastHealth, health, absorption, blinking

            );

        }
    }

    @Inject(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;push(Ljava/lang/String;)V", ordinal = 0))
    private void inject(DrawContext context, CallbackInfo ci) {

        if (CONFIG.ArmorBar) {

            int xoffset = HUD_MANAGER.getArmorXOffset();
            int yoffset = HUD_MANAGER.getArmorYOffset();

            int[] origin = HUD_MANAGER.calculateArmorOriginPoints(scaledWidth, scaledHeight, getCameraPlayer(), renderHealthValue);

            int xorigin = origin[0];
            int yorigin = origin[1];

            renderArmorBar(context, getCameraPlayer(), xorigin + xoffset, yorigin + yoffset, ICONS);
        }

    }

    @Redirect(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", ordinal = 0))
    private void inj(DrawContext instance, Identifier texture, int x, int y, int u, int v, int width, int height) {

    }

    @Redirect(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", ordinal = 1))
    private void inj1(DrawContext instance, Identifier texture, int x, int y, int u, int v, int width, int height) {

    }

    @Redirect(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", ordinal = 2))
    private void inj2(DrawContext instance, Identifier texture, int x, int y, int u, int v, int width, int height) {

    }

    public void renderArmorBar(DrawContext context, PlayerEntity playerEntity, int x, int y, Identifier icons) {
        int armor = playerEntity.getArmor();
        int xrigid;
        for(int w = 0; w < 10; ++w) {
            if (armor > 0) {
                xrigid = x + w * 8;
                if (w * 2 + 1 < armor) {
                    context.drawTexture(icons, xrigid, y, 34, 9, 9, 9);
                }

                if (w * 2 + 1 == armor) {
                    context.drawTexture(icons, xrigid, y, 25, 9, 9, 9);
                }

                if (w * 2 + 1 > armor) {
                    context.drawTexture(icons, xrigid, y, 16, 9, 9, 9);
                }
            }
        }
    }


    @Redirect(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", ordinal = 3))
    private void inj3(DrawContext instance, Identifier texture, int x, int y, int u, int v, int width, int height) {

        if (CONFIG.HungerBar) {
            instance.drawTexture(texture, x + CONFIG.FoodXOffset, y + CONFIG.FoodYOffset, u, v, width, height);
        }
    }

    @Redirect(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", ordinal = 4))
    private void inj4(DrawContext instance, Identifier texture, int x, int y, int u, int v, int width, int height) {

        if (CONFIG.HungerBar) {
            instance.drawTexture(texture, x + CONFIG.FoodXOffset, y + CONFIG.FoodYOffset, u, v, width, height);
        }
    }

    @Redirect(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", ordinal = 5))
    private void inj5(DrawContext instance, Identifier texture, int x, int y, int u, int v, int width, int height) {

        if (CONFIG.HungerBar) {
            instance.drawTexture(texture, x + CONFIG.FoodXOffset, y + CONFIG.FoodYOffset, u, v, width, height);
        }
    }

    @Redirect(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", ordinal = 6))
    private void inj6(DrawContext instance, Identifier texture, int x, int y, int u, int v, int width, int height) {

        if (CONFIG.AirBar) {
            instance.drawTexture(texture, x + CONFIG.AirXOffset, y + CONFIG.AirYOffset, u, v, width, height);
        }
    }
    @Redirect(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", ordinal = 7))
    private void inj7(DrawContext instance, Identifier texture, int x, int y, int u, int v, int width, int height) {

        if (CONFIG.AirBar) {
            instance.drawTexture(texture, x + CONFIG.AirXOffset, y + CONFIG.AirYOffset, u, v, width, height);
        }

    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderMountHealth(Lnet/minecraft/client/gui/DrawContext;)V"))
    public void renderMountHealthMix(InGameHud instance, DrawContext context) {

        if (CONFIG.MountHealth) {
            renderMountHealth(context);
        }

    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderMountJumpBar(Lnet/minecraft/entity/JumpingMount;Lnet/minecraft/client/gui/DrawContext;I)V"))
    public void renderMountJumpMix(InGameHud instance, JumpingMount mount, DrawContext context, int x) {
        if (CONFIG.MountJumpbar) {
            renderMountJumpBar(mount, context, x);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHeldItemTooltip(Lnet/minecraft/client/gui/DrawContext;)V"))
    public void renderHeldItemTooltipMix(InGameHud instance, DrawContext context) {
        if (CONFIG.HeldItemTooltip) {
            renderHeldItemTooltip(context);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/SpectatorHud;renderSpectatorMenu(Lnet/minecraft/client/gui/DrawContext;)V"))
    public void renderSpectatorMenu(SpectatorHud instance, DrawContext context) {
        if (CONFIG.SpectatorMenu) {
            instance.renderSpectatorMenu(context);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/SpectatorHud;render(Lnet/minecraft/client/gui/DrawContext;)V"))
    public void renderSpectatorHud(SpectatorHud instance, DrawContext context) {
        if (CONFIG.SpectatorHud) {
            instance.render(context);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/BossBarHud;render(Lnet/minecraft/client/gui/DrawContext;)V"))
    public void renderBossBar(BossBarHud instance, DrawContext context) {
        if (CONFIG.BossBar) {
            instance.render(context);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderStatusEffectOverlay(Lnet/minecraft/client/gui/DrawContext;)V"))
    public void renderStatusEffectOverlay(InGameHud instance, DrawContext context) {
        if (CONFIG.StatusEffectOverlay) {
            renderStatusEffectOverlay(context);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderScoreboardSidebar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/scoreboard/ScoreboardObjective;)V"))
    public void renderScoreBoard(InGameHud instance, DrawContext context, ScoreboardObjective objective) {
        if (CONFIG.ScoreBoard) {
            renderScoreboardSidebar(context, objective);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/DebugHud;render(Lnet/minecraft/client/gui/DrawContext;)V"))
    public void renderDebugHud(DebugHud instance, DrawContext context) {
        if (CONFIG.DebugHud) {
            instance.render(context);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/PlayerListHud;render(Lnet/minecraft/client/gui/DrawContext;ILnet/minecraft/scoreboard/Scoreboard;Lnet/minecraft/scoreboard/ScoreboardObjective;)V"))
    public void renderPlayerList(PlayerListHud instance, DrawContext context, int scaledWindowWidth, Scoreboard scoreboard, ScoreboardObjective objective) {
        if (CONFIG.PlayerList) {
            instance.render(context, scaledWindowWidth, scoreboard, objective);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/ChatHud;render(Lnet/minecraft/client/gui/DrawContext;III)V"))
    public void renderChatHud(ChatHud instance, DrawContext context, int currentTick, int mouseX, int mouseY) {
        if (CONFIG.ChatHud) {
            instance.render(context, currentTick, mouseX, mouseY);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderAutosaveIndicator(Lnet/minecraft/client/gui/DrawContext;)V"))
    public void renderAutosave(InGameHud instance, DrawContext context) {
        if (CONFIG.Autosave) {
            renderAutosaveIndicator(context);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderExperienceBar(Lnet/minecraft/client/gui/DrawContext;I)V"))
    public void renderAutosave(InGameHud instance, DrawContext context, int x) {
        if (CONFIG.ExpBar) {
            renderExperienceBar(context, x);
        }
    }

    @Inject(at=@At("HEAD"), method = "render", cancellable = true)
    public void render(CallbackInfo info) {
        if (CONFIG.removeHud) {
            info.cancel();
        }
    }

}
