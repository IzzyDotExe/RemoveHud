package ca.blutopia.removehud;

import ca.blutopia.removehud.config.ModConfig;
import ca.blutopia.removehud.config.OriginPoint;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

public class HUDManager {

    public ModConfig ConfigInstance;

    public HUDManager() {
        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
        ConfigInstance = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }

    public void toggleHud() {
        ConfigInstance.removeHud = !ConfigInstance.removeHud;
    }


    /* HP Methods */
    public int getHpYOffset() {
        return clampOffset(ConfigInstance.HpYOffset);
    }

    public int getHpXOffset() {
        return clampOffset(ConfigInstance.HpXOffset);
    }

    private int clampOffset(int offset) {
        return offset < ConfigInstance.OffsetSnappingStrength && offset > -ConfigInstance.OffsetSnappingStrength ? 0 : offset;
    }

    public int[] calculateHpOriginPoints(int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, float lastHealth, int health, int absorption, int width, int height) {
        int xorigin = x;
        int yorigin = y;

        // Manipulate origin points here
        int hearts = MathHelper.ceil(maxHealth / 2);
        int abshearts = MathHelper.ceil(absorption / 2);
        int allhearts =  hearts + abshearts - 1;
        int heartslines = allhearts / 10;
        allhearts = allhearts % 10;
        var heartslen = allhearts * 9;
        var heartsheight = 9 + (heartslines*lines) * 9;

        switch (ConfigInstance.HpBarOrigin) {

            case ORIGIN -> {

            }

            case TOPLEFT -> {
                xorigin = 0;
                yorigin = 0;
            }

            case BOTTOMLEFFT -> {
                xorigin = 0;
                yorigin = height - heartsheight;
            }

            case TOPRIGHT -> {
                xorigin = width - heartslen;
                yorigin = 0;
            }

            case BOTTONRIGHT -> {
                xorigin = width - heartslen;
                yorigin = height - heartsheight;
            }
        }


        return new int[] {xorigin, yorigin};
    }

    public void setHpXOffset(int hpXOffset) {
        ConfigInstance.HpXOffset += hpXOffset;
    }

    public void setHpYOffset(int hpYOffset) {
        ConfigInstance.HpYOffset += hpYOffset;
    }

    public void setHpOrigin(OriginPoint origin) {
        ConfigInstance.HpBarOrigin = origin;
    }

    public OriginPoint getHpOrigin() {
        return ConfigInstance.HpBarOrigin;
    }

    public int getArmorXOffset() {
        return clampOffset(ConfigInstance.ArmorXOffset);
    }

    public int getArmorYOffset() {
        return clampOffset(ConfigInstance.ArmorYOffset);
    }

    public int[] calculateArmorOriginPoints(int scaledWidth, int scaledHeight, PlayerEntity playerEntity, int renderHealthValue) {

        int i = MathHelper.ceil(playerEntity.getHealth());
        int j = renderHealthValue;
        float f = Math.max((float)playerEntity.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH), (float)Math.max(j, i));
        int p = MathHelper.ceil(playerEntity.getAbsorptionAmount());
        int q = MathHelper.ceil((f + (float)p) / 2.0F / 10.0F);
        int r = Math.max(10 - (q - 2), 3);
        int o = scaledHeight - 39;
        int s = o - (q - 1) * r - 10;
        int xorigin = scaledWidth / 2 - 91;
        int yorigin = s;


        return new int[] {xorigin, yorigin};
    }
}
