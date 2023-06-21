package ca.blutopia.removehud;

import ca.blutopia.removehud.config.HUDItems;
import ca.blutopia.removehud.config.ModConfig;
import ca.blutopia.removehud.config.OriginPoint;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
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

    private int clampOffset(int offset) {
        return offset < ConfigInstance.OffsetSnappingStrength && offset > -ConfigInstance.OffsetSnappingStrength ? 0 : offset;
    }

    public void setElementXOffset(HUDItems element, int offset) throws NoSuchFieldException, IllegalAccessException {
        var field = ConfigInstance.getClass().getField(element.name()+"XOffset");
        int Xoffset = (int)field.get(ConfigInstance);
        field.set(ConfigInstance, Xoffset + offset);
    }

    public void setElementYOffset(HUDItems element, int offset) throws NoSuchFieldException, IllegalAccessException {
        var field = ConfigInstance.getClass().getField(element.name()+"YOffset");
        int Yoffset = (int)field.get(ConfigInstance);
        field.set(ConfigInstance, Yoffset+offset);
    }

    public int getElementXOffset(HUDItems element) throws NoSuchFieldException, IllegalAccessException {
        var field = ConfigInstance.getClass().getField(element.name()+"XOffset");
        int value = (int)field.get(ConfigInstance);
        value = clampOffset(value);
        return value;
    }

    public int getElementYOffset(HUDItems element) throws NoSuchFieldException, IllegalAccessException {
        var field = ConfigInstance.getClass().getField(element.name()+"YOffset");
        int value = (int)field.get(ConfigInstance);
        value = clampOffset(value);
        return value;
    }

    public void setElementOrigin(HUDItems element, OriginPoint origin) throws NoSuchFieldException, IllegalAccessException {
        var field = ConfigInstance.getClass().getField(element.name()+"Origin");
        field.set(ConfigInstance, origin);
    }

    public OriginPoint getElementOrigin(HUDItems element) throws IllegalAccessException, NoSuchFieldException {
        var field = ConfigInstance.getClass().getField(element.name()+"Origin");
        return (OriginPoint)field.get(ConfigInstance);
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

    public int[] calculateHotBarOriginPoints(int scaledWidth, int scaledHeight) {
        int xorigin = 0;
        int yorigin = 0;

        switch (ConfigInstance.HotBarOrigin) {

            case ORIGIN -> {
                xorigin = scaledWidth/2;
                yorigin = scaledHeight;
            }
            case TOPLEFT -> {
                xorigin = 120;
                yorigin = 22;
            }
            case BOTTOMLEFFT -> {
                xorigin = 120;
                yorigin = scaledHeight;
            }
            case TOPRIGHT -> {
                yorigin = 22;
                xorigin = scaledWidth - 90;
            }
            case BOTTONRIGHT -> {
                xorigin = scaledWidth - 90;
                yorigin = scaledHeight;
            }
        }

        return new int[] {xorigin, yorigin};
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

        switch (ConfigInstance.HpOrigin) {

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

    public int[] calculateAutosaveOriginPoints(int x, int y, TextRenderer renderer, int scaledWidth, int scaledHeight, Text saveText) {

        int j = renderer.getWidth(saveText);
        int xorigin = 0;
        int yorigin = 0;

        switch (ConfigInstance.AutosaveOrigin) {

            case ORIGIN, BOTTONRIGHT -> {
                xorigin = scaledWidth - j - 10;
                yorigin = scaledHeight - 15;
            }
            case TOPLEFT -> {
                xorigin = 10;
                yorigin = 15;
            }
            case BOTTOMLEFFT -> {
                xorigin = 10;
                yorigin = scaledHeight - 15;
            }
            case TOPRIGHT -> {
                xorigin = scaledWidth - j - 10;
                yorigin = 15;
            }
        }

        return new int[] {xorigin, yorigin};
    }
}
