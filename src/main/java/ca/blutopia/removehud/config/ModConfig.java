package ca.blutopia.removehud.config;

import ca.blutopia.removehud.config.OriginPoint;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name="removehud")
public class ModConfig implements ConfigData {

    @Comment("If false block outlines will not be visible")
    public boolean highlightBlocks = true;

    @Comment("Setting will toggle all of the HUD")
    public boolean removeHud = false;

    @Comment("If true the hand will be invisible")
    public boolean removeHand = false;

    @ConfigEntry.Category("HUD")
    public boolean Autosave = true;

    @ConfigEntry.Category("HUD")
    public boolean ChatHud = true;

    @ConfigEntry.Category("HUD")
    public boolean PlayerList = true;

    @ConfigEntry.Category("HUD")
    public boolean DebugHud = true;

    @ConfigEntry.Category("HUD")
    public boolean ScoreBoard = true;

    @ConfigEntry.Category("Overlays")
    public boolean StatusEffectOverlay = true;

    @ConfigEntry.Category("HUD")
    public boolean SpectatorHud = true;

    @ConfigEntry.Category("HUD")
    public boolean SpectatorMenu = true;

    @ConfigEntry.Category("HUD")
    public boolean HeldItemTooltip = true;

    @ConfigEntry.Category("HUD")
    public boolean MountJumpbar = true;

    @ConfigEntry.Category("HUD")
    public boolean MountHealth = true;

    @ConfigEntry.Category("HUD")
    public boolean BossBar = true;

    @ConfigEntry.Category("HUD")
    @Comment("Health hearts")
    public boolean HpBar = true;

    @ConfigEntry.Category("OFFSETS")
    @Comment("HP bar origin point")
    public OriginPoint HpBarOrigin = OriginPoint.ORIGIN;

    @ConfigEntry.Category("OFFSETS")
    @Comment("HP bar X offset")
    public int HpXOffset = 0;


    @ConfigEntry.Category("OFFSETS")
    @Comment("HP bar Y offset")
    public int HpYOffset = 0;
//
//    @ConfigEntry.Category("OFFSETS")
//    @Comment("Armor bar origin point")
//    public OriginPoint ArmorBarOrigin = OriginPoint.ORIGIN;

    @ConfigEntry.Category("HUD")
    @Comment("Armor points")
    public boolean ArmorBar = true;

    @ConfigEntry.Category("OFFSETS")
    @Comment("Armor bar X offset")
    public int ArmorXOffset = 0;

    public void setArmorXOffset(int armorXOffset) {
        ArmorXOffset += armorXOffset;
    }

    public void setArmorYOffset(int armorYOffset) {
        ArmorYOffset += armorYOffset;
    }

    @ConfigEntry.Category("OFFSETS")
    @Comment("Armor bar Y offset")
    public int ArmorYOffset = 0;

//    @ConfigEntry.Category("OFFSETS")
//    @Comment("Oxygen bar origin point")
//    public OriginPoint AirBarOrigin = OriginPoint.ORIGIN;

    @ConfigEntry.Category("HUD")
    @Comment("Oxygen bar")
    public boolean AirBar = true;

    @ConfigEntry.Category("OFFSETS")
    @Comment("Air bar X offset")
    public int AirXOffset = 0;

    @ConfigEntry.Category("OFFSETS")
    @Comment("Air bar Y offset")
    public int AirYOffset = 0;

    public void setAirXOffset(int airXOffset) {
        AirXOffset += airXOffset;
    }

    public void setAirYOffset(int airYOffset) {
        AirYOffset += airYOffset;
    }

    //    @ConfigEntry.Category("OFFSETS")
//    @Comment("Hunger bar origin point")
//    public OriginPoint FoodBarOrigin = OriginPoint.ORIGIN;

    @ConfigEntry.Category("HUD")
    @Comment("Hunger bar")
    public boolean HungerBar = true;

    @ConfigEntry.Category("OFFSETS")
    @Comment("Hunger bar X offset")
    public int FoodXOffset = 0;

    @ConfigEntry.Category("OFFSETS")
    @Comment("Hunger bar Y offset")
    public int FoodYOffset = 0;

    public void setFoodXOffset(int foodXOffset) {
        FoodXOffset += foodXOffset;
    }

    public void setFoodYOffset(int foodYOffset) {
        FoodYOffset += foodYOffset;
    }

    @ConfigEntry.Category("Overlays")
    public boolean SpyglassOverlay = true;

    @ConfigEntry.Category("Overlays")
    public boolean PortalOverlay = true;

    @ConfigEntry.Category("Overlays")
    @Comment("Frost Overlay, Pumkin head, etc...")
    public boolean OtherOverlays = true;

    @ConfigEntry.Category("Overlays")
    public boolean Vignette = true;

    @ConfigEntry.Category("HUD")
    public boolean ExpBar = true;

    @ConfigEntry.Category("HUD")
    public boolean Crosshairs = true;

    @ConfigEntry.Category("HUD")
    public boolean HotBar = true;

    @ConfigEntry.Category("Editor")
    public boolean OffsetSnapping = true;
    @ConfigEntry.Category("Editor")
    public int OffsetSnappingStrength = 5;


}
