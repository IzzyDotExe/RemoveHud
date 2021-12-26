package ca.blutopia.removehud;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name="removehud")
public class ModConfig implements ConfigData {

    @ConfigEntry.Gui.Excluded
    public static ModConfig INSTANCE;

    public static void init() {

        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
        INSTANCE = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }

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
    @Comment("Health, Hunger, and Armor")
    public boolean StatusBars = true;

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

}
