package ca.blutopia.removehud.config;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name="removehud")
public class ModConfig implements ConfigData {

    // region General Settings
    @Comment("If false block outlines will not be visible")
    public boolean highlightBlocks = true;

    @Comment("Setting will toggle all of the HUD")
    public boolean removeHud = false;

    @Comment("If true the hand will be invisible")
    public boolean removeHand = false;

    // endregion

    // region Uneditable
        // TODO: MAKE THESE EDITABLE
    @ConfigEntry.Category("HUD")
    public boolean ChatHud = true;

    @ConfigEntry.Category("HUD")
    public boolean PlayerList = true;

    @ConfigEntry.Category("HUD")
    public boolean DebugHud = true;

    @ConfigEntry.Category("HUD")
    public boolean SpectatorHud = true;

    @ConfigEntry.Category("HUD")
    public boolean SpectatorMenu = true;

    // endregion

    // region Autosave
    @ConfigEntry.Category("OFFSETS")
    public OriginPoint AutosaveOrigin = OriginPoint.ORIGIN;

    @ConfigEntry.Category("HUD")
    public boolean Autosave = true;

    @ConfigEntry.Category("OFFSETS")
    public int AutosaveXOffset = 0;

    @ConfigEntry.Category("OFFSETS")
    public int AutosaveYOffset = 0;

    // endregion

    // region Scoreboard
    @ConfigEntry.Category("OFFSETS")
    public OriginPoint ScoreboardOrigin = OriginPoint.ORIGIN;

    @ConfigEntry.Category("HUD")
    public boolean Scoreboard = true;

    @ConfigEntry.Category("OFFSETS")
    public int ScoreboardXOffset = 0;

    @ConfigEntry.Category("OFFSETS")
    public int ScoreboardYOffset = 0;

    // endregion

    // region Itemtootip
    @ConfigEntry.Category("OFFSETS")
    public OriginPoint ItemtootipOrigin = OriginPoint.ORIGIN;

    @ConfigEntry.Category("HUD")
    public boolean Itemtooltip = true;

    @ConfigEntry.Category("OFFSETS")
    public int ItemtooltipXOffset = 0;

    @ConfigEntry.Category("OFFSETS")
    public int ItemtooltipYOffset = 0;

    // endregion

    // region Mountjumpbar
    @ConfigEntry.Category("OFFSETS")
    public OriginPoint MountjumpbarOrigin = OriginPoint.ORIGIN;

    @ConfigEntry.Category("HUD")
    public boolean Mountjumpbar = true;

    @ConfigEntry.Category("OFFSETS")
    public int MountjumpbarXOffset = 0;

    @ConfigEntry.Category("OFFSETS")
    public int MountjumpbarYOffset = 0;

    // endregion

    // region Mounthealth
    @ConfigEntry.Category("OFFSETS")
    public OriginPoint MounthealthOrigin = OriginPoint.ORIGIN;

    @ConfigEntry.Category("HUD")
    public boolean Mounthealth = true;

    @ConfigEntry.Category("OFFSETS")
    public int MounthealthXOffset = 0;

    @ConfigEntry.Category("OFFSETS")
    public int MounthealthYOffset = 0;

    // endregion

    // region Bossbar
    @ConfigEntry.Category("OFFSETS")
    public OriginPoint BossbarOrigin = OriginPoint.ORIGIN;

    @ConfigEntry.Category("HUD")
    public boolean Bossbar = true;

    @ConfigEntry.Category("OFFSETS")
    public int BossbarXOffset = 0;

    @ConfigEntry.Category("OFFSETS")
    public int BossbarYOffset = 0;

    // endregion

    // region Hp
    @ConfigEntry.Category("HUD")
    @Comment("Health hearts")
    public boolean Hp = true;

    @ConfigEntry.Category("OFFSETS")
    @Comment("HP bar origin point")
    public OriginPoint HpOrigin = OriginPoint.ORIGIN;

    @ConfigEntry.Category("OFFSETS")
    @Comment("HP bar X offset")
    public int HpXOffset = 0;


    @ConfigEntry.Category("OFFSETS")
    @Comment("HP bar Y offset")
    public int HpYOffset = 0;

    // endregion

    // region Armor
    @ConfigEntry.Category("OFFSETS")
    @Comment("Armor bar origin point")
    public OriginPoint ArmorOrigin = OriginPoint.ORIGIN;

    @ConfigEntry.Category("HUD")
    @Comment("Armor points")
    public boolean Armor = true;

    @ConfigEntry.Category("OFFSETS")
    @Comment("Armor bar X offset")
    public int ArmorXOffset = 0;

    @ConfigEntry.Category("OFFSETS")
    @Comment("Armor bar Y offset")
    public int ArmorYOffset = 0;

    // endregion

    // region Air
    @ConfigEntry.Category("OFFSETS")
    @Comment("Oxygen bar origin point")
    public OriginPoint AirOrigin = OriginPoint.ORIGIN;

    @ConfigEntry.Category("HUD")
    @Comment("Oxygen bar")
    public boolean Air = true;

    @ConfigEntry.Category("OFFSETS")
    @Comment("Air bar X offset")
    public int AirXOffset = 0;

    @ConfigEntry.Category("OFFSETS")
    @Comment("Air bar Y offset")
    public int AirYOffset = 0;

    // endregion

    // region Food
    @ConfigEntry.Category("OFFSETS")
    @Comment("Hunger bar origin point")
    public OriginPoint FoodOrigin = OriginPoint.ORIGIN;

    @ConfigEntry.Category("HUD")
    @Comment("Hunger bar")
    public boolean Food = true;

    @ConfigEntry.Category("OFFSETS")
    @Comment("Hunger bar X offset")
    public int FoodXOffset = 0;

    @ConfigEntry.Category("OFFSETS")
    @Comment("Hunger bar Y offset")
    public int FoodYOffset = 0;

    // endregion

    // region HotBar
    @ConfigEntry.Category("OFFSETS")
    public OriginPoint HotBarOrigin = OriginPoint.ORIGIN;

    @ConfigEntry.Category("HUD")
    public boolean HotBar = true;

    @ConfigEntry.Category("OFFSETS")
    public int HotBarXOffset = 0;

    @ConfigEntry.Category("OFFSETS")
    public int HotBarYOffset = 0;

    // endregion

    // region Expbar
    @ConfigEntry.Category("OFFSETS")
    public OriginPoint ExpbarOrigin = OriginPoint.ORIGIN;

    @ConfigEntry.Category("HUD")
    public boolean Expbar = true;

    @ConfigEntry.Category("OFFSETS")
    public int ExpbarXOffset = 0;

    @ConfigEntry.Category("OFFSETS")
    public int ExpbarYOffset = 0;

    // endregion

    // region Crosshairs
    @ConfigEntry.Category("OFFSETS")
    public OriginPoint CrosshairsOrigin = OriginPoint.ORIGIN;

    @ConfigEntry.Category("HUD")
    public boolean Crosshairs = true;

    @ConfigEntry.Category("OFFSETS")
    public int CrosshairsXOffset = 0;

    @ConfigEntry.Category("OFFSETS")
    public int CrosshairsYOffset = 0;

    // endregion

    // region Overlays
    @ConfigEntry.Category("Overlays")
    public boolean StatusEffectOverlay = true;
    @ConfigEntry.Category("Overlays")
    public boolean SpyglassOverlay = true;

    @ConfigEntry.Category("Overlays")
    public boolean PortalOverlay = true;

    @ConfigEntry.Category("Overlays")
    @Comment("Frost Overlay, Pumkin head, etc...")
    public boolean OtherOverlays = true;

    @ConfigEntry.Category("Overlays")
    public boolean Vignette = true;

    // endregion

    // region Editor Settings
    @ConfigEntry.Category("Editor")
    public boolean EnableEditor = false;
    @ConfigEntry.Category("Editor")
    public boolean OffsetSnapping = true;
    @ConfigEntry.Category("Editor")
    public int OffsetSnappingStrength = 5;

    // endregion

}
