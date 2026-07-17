package ca.blutopia.removehud.mixin;

import ca.blutopia.removehud.HudEditorState;
import ca.blutopia.removehud.ModConfig;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Hud.class)
public abstract class RemoveHudButNotHand {

    // ---- Hotbar (background, selection, offhand slot and items move together) ----

    @Inject(method = "extractItemHotbar(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/DeltaTracker;)V", at = @At("HEAD"), cancellable = true)
    private void renderHotBar(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (!ModConfig.INSTANCE.HotBar) {
            ci.cancel();
            return;
        }
        int vanillaX = graphics.guiWidth() / 2 - 91;
        int vanillaY = graphics.guiHeight() - 22;
        int targetX = ModConfig.INSTANCE.HotBarOrigin.resolveX(vanillaX, 182);
        int targetY = ModConfig.INSTANCE.HotBarOrigin.resolveY(vanillaY, 22);
        graphics.pose().pushMatrix();
        graphics.pose().translate((float) (targetX - vanillaX + ModConfig.INSTANCE.HotBarXOffset), (float) (targetY - vanillaY + ModConfig.INSTANCE.HotBarYOffset));
    }

    @Inject(method = "extractItemHotbar(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/DeltaTracker;)V", at = @At("RETURN"))
    private void renderHotBarEnd(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (ModConfig.INSTANCE.HotBar) {
            graphics.pose().popMatrix();
        }
    }

    // ---- Crosshair ----

    @Inject(method = "extractCrosshair(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/DeltaTracker;)V", at = @At("HEAD"), cancellable = true)
    public void renderCrosshair(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (!ModConfig.INSTANCE.Crosshairs) {
            ci.cancel();
        }
    }

    // ---- Vignette ----

    @Inject(method = "extractVignette(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/world/entity/Entity;)V", at = @At("HEAD"), cancellable = true)
    public void renderVignette(GuiGraphicsExtractor graphics, Entity entity, CallbackInfo ci) {
        if (!ModConfig.INSTANCE.Vignette) {
            ci.cancel();
        }
    }

    // ---- Misc camera overlays (frost, pumpkin, nausea, etc.) ----

    @Inject(method = "extractTextureOverlay(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/resources/Identifier;F)V", at = @At("HEAD"), cancellable = true)
    public void renderOverlays(GuiGraphicsExtractor graphics, Identifier texture, float opacity, CallbackInfo ci) {
        if (!ModConfig.INSTANCE.OtherOverlays) {
            ci.cancel();
        }
    }

    @Inject(method = "extractConfusionOverlay(Lnet/minecraft/client/gui/GuiGraphicsExtractor;F)V", at = @At("HEAD"), cancellable = true)
    public void renderConfusionOverlay(GuiGraphicsExtractor graphics, float strength, CallbackInfo ci) {
        if (!ModConfig.INSTANCE.OtherOverlays) {
            ci.cancel();
        }
    }

    @Inject(method = "extractPortalOverlay(Lnet/minecraft/client/gui/GuiGraphicsExtractor;F)V", at = @At("HEAD"), cancellable = true)
    public void renderPortalOverlay(GuiGraphicsExtractor graphics, float nauseaStrength, CallbackInfo ci) {
        if (!ModConfig.INSTANCE.PortalOverlay) {
            ci.cancel();
        }
    }

    @Inject(method = "extractSpyglassOverlay(Lnet/minecraft/client/gui/GuiGraphicsExtractor;F)V", at = @At("HEAD"), cancellable = true)
    public void renderSpyglassOverlay(GuiGraphicsExtractor graphics, float scale, CallbackInfo ci) {
        if (!ModConfig.INSTANCE.SpyglassOverlay) {
            ci.cancel();
        }
    }

    // ---- Health / armor / food / air (each offset moves that whole element) ----

    @Inject(method = "extractHearts(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/world/entity/player/Player;IIIIFIIIZ)V", at = @At("HEAD"), cancellable = true)
    public void renderHealthBar(GuiGraphicsExtractor graphics, Player player, int xLeft, int yLineBase, int healthRowHeight, int heartOffsetIndex, float maxHealth, int currentHealth, int oldHealth, int absorption, boolean blink, CallbackInfo ci) {
        if (!ModConfig.INSTANCE.HpBar) {
            ci.cancel();
        }
    }

    @ModifyArg(
            method = "extractPlayerHealth(Lnet/minecraft/client/gui/GuiGraphicsExtractor;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Hud;extractHearts(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/world/entity/player/Player;IIIIFIIIZ)V"),
            index = 2)
    private int modifyHealthBarX(int value) {
        return ModConfig.INSTANCE.HpOrigin.resolveX(value, 81) + ModConfig.INSTANCE.HpXOffset;
    }

    @ModifyArg(
            method = "extractPlayerHealth(Lnet/minecraft/client/gui/GuiGraphicsExtractor;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Hud;extractHearts(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/world/entity/player/Player;IIIIFIIIZ)V"),
            index = 3)
    private int modifyHealthBarY(int value) {
        return ModConfig.INSTANCE.HpOrigin.resolveY(value, 9) + ModConfig.INSTANCE.HpYOffset;
    }

    @Inject(method = "extractArmor(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/world/entity/player/Player;IIII)V", at = @At("HEAD"), cancellable = true)
    private static void renderArmor(GuiGraphicsExtractor graphics, Player player, int yLineBase, int numHealthRows, int healthRowHeight, int xLeft, CallbackInfo ci) {
        if (!ModConfig.INSTANCE.ArmorBar) {
            ci.cancel();
        }
    }

    @ModifyArg(
            method = "extractPlayerHealth(Lnet/minecraft/client/gui/GuiGraphicsExtractor;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Hud;extractArmor(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/world/entity/player/Player;IIII)V"),
            index = 2)
    private static int modifyArmorBarY(int value) {
        // extractArmor subtracts a further 10px (single-row case) from whatever we return here
        // before drawing, so anchor against that true rendered position and add it back.
        int targetTrueY = ModConfig.INSTANCE.ArmorOrigin.resolveY(value - 10, 9);
        return targetTrueY + 10 + ModConfig.INSTANCE.ArmorYOffset;
    }

    @ModifyArg(
            method = "extractPlayerHealth(Lnet/minecraft/client/gui/GuiGraphicsExtractor;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Hud;extractArmor(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/world/entity/player/Player;IIII)V"),
            index = 5)
    private static int modifyArmorBarX(int value) {
        return ModConfig.INSTANCE.ArmorOrigin.resolveX(value, 81) + ModConfig.INSTANCE.ArmorXOffset;
    }

    @Inject(method = "extractFood(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/world/entity/player/Player;II)V", at = @At("HEAD"), cancellable = true)
    public void renderFood(GuiGraphicsExtractor graphics, Player player, int yLineBase, int xRight, CallbackInfo ci) {
        if (!ModConfig.INSTANCE.HungerBar) {
            ci.cancel();
        }
    }

    @ModifyArg(
            method = "extractPlayerHealth(Lnet/minecraft/client/gui/GuiGraphicsExtractor;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Hud;extractFood(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/world/entity/player/Player;II)V"),
            index = 2)
    private int modifyFoodBarY(int value) {
        return ModConfig.INSTANCE.FoodOrigin.resolveY(value, 9) + ModConfig.INSTANCE.FoodYOffset;
    }

    @ModifyArg(
            method = "extractPlayerHealth(Lnet/minecraft/client/gui/GuiGraphicsExtractor;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Hud;extractFood(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/world/entity/player/Player;II)V"),
            index = 3)
    private int modifyFoodBarX(int value) {
        // value is xRight (right-referenced); convert to the left edge to anchor, then back.
        int targetLeftX = ModConfig.INSTANCE.FoodOrigin.resolveX(value - 81, 81);
        return targetLeftX + 81 + ModConfig.INSTANCE.FoodXOffset;
    }

    @Inject(method = "extractAirBubbles(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/world/entity/player/Player;III)V", at = @At("HEAD"), cancellable = true)
    public void renderAirBubbles(GuiGraphicsExtractor graphics, Player player, int vehicleHearts, int yLineAir, int xRight, CallbackInfo ci) {
        if (!ModConfig.INSTANCE.AirBar) {
            ci.cancel();
        }
    }

    @ModifyArg(
            method = "extractPlayerHealth(Lnet/minecraft/client/gui/GuiGraphicsExtractor;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Hud;extractAirBubbles(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/world/entity/player/Player;III)V"),
            index = 3)
    private int modifyAirBubblesY(int value) {
        // extractAirBubbles adds a further 10px (no-vehicle case) to whatever we return here
        // before drawing, so anchor against that true rendered position and subtract it back.
        int targetTrueY = ModConfig.INSTANCE.AirOrigin.resolveY(value + 10, 9);
        return targetTrueY - 10 + ModConfig.INSTANCE.AirYOffset;
    }

    @ModifyArg(
            method = "extractPlayerHealth(Lnet/minecraft/client/gui/GuiGraphicsExtractor;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Hud;extractAirBubbles(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/world/entity/player/Player;III)V"),
            index = 4)
    private int modifyAirBubblesX(int value) {
        // value is xRight (right-referenced); convert to the left edge to anchor, then back.
        int targetLeftX = ModConfig.INSTANCE.AirOrigin.resolveX(value - 81, 81);
        return targetLeftX + 81 + ModConfig.INSTANCE.AirXOffset;
    }

    // ---- Mount / vehicle health ----

    @Inject(method = "extractVehicleHealth(Lnet/minecraft/client/gui/GuiGraphicsExtractor;)V", at = @At("HEAD"), cancellable = true)
    public void renderMountHealth(GuiGraphicsExtractor graphics, CallbackInfo ci) {
        if (!ModConfig.INSTANCE.MountHealth) {
            ci.cancel();
        }
    }

    // ---- Held item tooltip (name popup above hotbar) ----

    @Inject(method = "extractSelectedItemName(Lnet/minecraft/client/gui/GuiGraphicsExtractor;)V", at = @At("HEAD"), cancellable = true)
    public void renderHeldItemTooltip(GuiGraphicsExtractor graphics, CallbackInfo ci) {
        if (!ModConfig.INSTANCE.HeldItemTooltip) {
            ci.cancel();
            return;
        }
        int vanillaX = graphics.guiWidth() / 2 - 60;
        int vanillaY = graphics.guiHeight() - 59;
        int targetX = ModConfig.INSTANCE.HeldItemTooltipOrigin.resolveX(vanillaX, 120);
        int targetY = ModConfig.INSTANCE.HeldItemTooltipOrigin.resolveY(vanillaY, 12);
        graphics.pose().pushMatrix();
        graphics.pose().translate((float) (targetX - vanillaX + ModConfig.INSTANCE.HeldItemTooltipXOffset), (float) (targetY - vanillaY + ModConfig.INSTANCE.HeldItemTooltipYOffset));
    }

    @Inject(method = "extractSelectedItemName(Lnet/minecraft/client/gui/GuiGraphicsExtractor;)V", at = @At("RETURN"))
    public void renderHeldItemTooltipEnd(GuiGraphicsExtractor graphics, CallbackInfo ci) {
        if (ModConfig.INSTANCE.HeldItemTooltip) {
            graphics.pose().popMatrix();
        }
    }

    // ---- Status effects ----

    @Inject(method = "extractEffects(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/DeltaTracker;)V", at = @At("HEAD"), cancellable = true)
    public void renderStatusEffectOverlay(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (!ModConfig.INSTANCE.StatusEffectOverlay) {
            ci.cancel();
        }
    }

    // ---- Scoreboard sidebar ----

    @Inject(method = "extractScoreboardSidebar(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/DeltaTracker;)V", at = @At("HEAD"), cancellable = true)
    public void renderScoreboardSidebar(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (!ModConfig.INSTANCE.ScoreBoard) {
            ci.cancel();
        }
    }

    // ---- Tab / player list ----

    @Inject(method = "extractTabList(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/DeltaTracker;)V", at = @At("HEAD"), cancellable = true)
    public void renderPlayerList(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        // Tab also cycles the HUD editor's selected element, so don't let it pop the vanilla player list too.
        if (!ModConfig.INSTANCE.PlayerList || HudEditorState.active) {
            ci.cancel();
        }
    }

    // ---- Chat ----

    @Inject(method = "extractChat(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/DeltaTracker;)V", at = @At("HEAD"), cancellable = true)
    public void renderChat(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (!ModConfig.INSTANCE.ChatHud) {
            ci.cancel();
        }
    }

    // ---- Autosave indicator ----

    @Inject(method = "extractSavingIndicator(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/DeltaTracker;)V", at = @At("HEAD"), cancellable = true)
    public void renderAutosaveIndicator(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (!ModConfig.INSTANCE.Autosave) {
            ci.cancel();
        }
    }

    // ---- Overlay message (e.g. "now playing", block placement hints) ----

    @Inject(method = "extractOverlayMessage(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/DeltaTracker;)V", at = @At("HEAD"), cancellable = true)
    public void renderOverlayMessage(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (!ModConfig.INSTANCE.OverlayMessage) {
            ci.cancel();
            return;
        }
        int vanillaX = graphics.guiWidth() / 2 - 60;
        int vanillaY = graphics.guiHeight() - 72;
        int targetX = ModConfig.INSTANCE.OverlayMessageOrigin.resolveX(vanillaX, 120);
        int targetY = ModConfig.INSTANCE.OverlayMessageOrigin.resolveY(vanillaY, 12);
        graphics.pose().pushMatrix();
        graphics.pose().translate((float) (targetX - vanillaX + ModConfig.INSTANCE.OverlayMessageXOffset), (float) (targetY - vanillaY + ModConfig.INSTANCE.OverlayMessageYOffset));
    }

    @Inject(method = "extractOverlayMessage(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/DeltaTracker;)V", at = @At("RETURN"))
    public void renderOverlayMessageEnd(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (ModConfig.INSTANCE.OverlayMessage) {
            graphics.pose().popMatrix();
        }
    }

    // ---- Master toggle (F7) ----

    @Inject(method = "extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/DeltaTracker;)V", at = @At("HEAD"), cancellable = true)
    public void render(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (ModConfig.INSTANCE.removeHud) {
            ci.cancel();
        }
    }
}
