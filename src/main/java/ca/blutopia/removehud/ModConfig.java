package ca.blutopia.removehud;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.annotation.SerializedName;

@Config(name="removehud")
public class ModConfig implements ConfigData {

    @ConfigEntry.Gui.Excluded
    public static ModConfig INSTANCE;

    public static void init() {

        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
        INSTANCE = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }

    @Comment("If true blocks will highlighted when you look at them")
    public boolean highlightBlocks = true;

    @Comment("If true the hud will be invisible")
    @SerializedName("Remove Hud but not Hand")
    public boolean toggleMod = false;

    @Comment("If true the hand will be invisible")
    public boolean removeHand = false;

}
