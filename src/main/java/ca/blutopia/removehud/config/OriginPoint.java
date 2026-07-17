package ca.blutopia.removehud.config;

import net.minecraft.client.Minecraft;

public enum OriginPoint {
    ORIGIN,
    TOP_LEFT,
    BOTTOM_LEFT,
    TOP_RIGHT,
    BOTTOM_RIGHT;

    public int resolveX(int vanillaX, int width) {
        int screenWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        return switch (this) {
            case ORIGIN -> vanillaX;
            case TOP_LEFT, BOTTOM_LEFT -> 0;
            case TOP_RIGHT, BOTTOM_RIGHT -> screenWidth - width;
        };
    }

    public int resolveY(int vanillaY, int height) {
        int screenHeight = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        return switch (this) {
            case ORIGIN -> vanillaY;
            case TOP_LEFT, TOP_RIGHT -> 0;
            case BOTTOM_LEFT, BOTTOM_RIGHT -> screenHeight - height;
        };
    }
}
