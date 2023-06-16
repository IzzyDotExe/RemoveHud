package ca.blutopia.removehud;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;

public class HUDManager {

    public ModConfig ConfigInstance;

    public HUDManager() {
        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
        ConfigInstance = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }

    public void toggleHud() {
        ConfigInstance.removeHud = !ConfigInstance.removeHud;
    }

    public int getHpYOffset() {
        int yoffset = ConfigInstance.HpYOffset;
        return yoffset < ConfigInstance.OffsetSnappingStrength && yoffset > -ConfigInstance.OffsetSnappingStrength ? 0 : yoffset;
    }

    public int getHpXOffset() {
        int xoffset = ConfigInstance.HpXOffset;
        return xoffset < ConfigInstance.OffsetSnappingStrength && xoffset > -ConfigInstance.OffsetSnappingStrength ? 0 : xoffset;
    }

    public int[] calculateHpOriginPoints(int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, float lastHealth, int health, int absorption) {
        int xorigin = x;
        int yorigin = y;

        // Manipulate origin points here

        switch (ConfigInstance.HpBarOrigin) {

            case ORIGIN -> {

            }

            case TOPLEFT -> {

            }

            case BOTTOMLEFFT -> {

            }

            case TOPRIGHT -> {

            }

            case BOTTONRIGHT -> {

            }
        }


        return new int[] {xorigin, yorigin};
    }
}
