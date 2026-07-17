package ca.blutopia.removehud.config;

import ca.blutopia.removehud.ModConfig;
import net.minecraft.client.Minecraft;

import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

/**
 * Every HUD element the in-game editor can select, nudge and anchor.
 * vanillaX/vanillaY/width/height describe the element's default (un-offset)
 * screen-space bounding box, mirroring the position math in the mixins that
 * actually render it. They're approximations for the two variable-width
 * text elements (held item tooltip, overlay message) but exact for the
 * fixed-size sprite elements.
 */
public enum HudElement {

    HOTBAR("Hotbar",
            () -> ModConfig.INSTANCE.HotBarXOffset, v -> ModConfig.INSTANCE.HotBarXOffset = v,
            () -> ModConfig.INSTANCE.HotBarYOffset, v -> ModConfig.INSTANCE.HotBarYOffset = v,
            () -> ModConfig.INSTANCE.HotBarOrigin, v -> ModConfig.INSTANCE.HotBarOrigin = v,
            () -> screenWidth() / 2 - 91, () -> screenHeight() - 22,
            182, 22),

    HP("HP Bar",
            () -> ModConfig.INSTANCE.HpXOffset, v -> ModConfig.INSTANCE.HpXOffset = v,
            () -> ModConfig.INSTANCE.HpYOffset, v -> ModConfig.INSTANCE.HpYOffset = v,
            () -> ModConfig.INSTANCE.HpOrigin, v -> ModConfig.INSTANCE.HpOrigin = v,
            () -> screenWidth() / 2 - 91, () -> screenHeight() - 39,
            81, 9),

    ARMOR("Armor Bar",
            () -> ModConfig.INSTANCE.ArmorXOffset, v -> ModConfig.INSTANCE.ArmorXOffset = v,
            () -> ModConfig.INSTANCE.ArmorYOffset, v -> ModConfig.INSTANCE.ArmorYOffset = v,
            () -> ModConfig.INSTANCE.ArmorOrigin, v -> ModConfig.INSTANCE.ArmorOrigin = v,
            () -> screenWidth() / 2 - 91, () -> screenHeight() - 49,
            81, 9),

    FOOD("Food Bar",
            () -> ModConfig.INSTANCE.FoodXOffset, v -> ModConfig.INSTANCE.FoodXOffset = v,
            () -> ModConfig.INSTANCE.FoodYOffset, v -> ModConfig.INSTANCE.FoodYOffset = v,
            () -> ModConfig.INSTANCE.FoodOrigin, v -> ModConfig.INSTANCE.FoodOrigin = v,
            () -> screenWidth() / 2 + 10, () -> screenHeight() - 39,
            81, 9),

    AIR("Air Bar",
            () -> ModConfig.INSTANCE.AirXOffset, v -> ModConfig.INSTANCE.AirXOffset = v,
            () -> ModConfig.INSTANCE.AirYOffset, v -> ModConfig.INSTANCE.AirYOffset = v,
            () -> ModConfig.INSTANCE.AirOrigin, v -> ModConfig.INSTANCE.AirOrigin = v,
            () -> screenWidth() / 2 + 10, () -> screenHeight() - 49,
            81, 9),

    EXP_BAR("Exp Bar",
            () -> ModConfig.INSTANCE.ExpBarXOffset, v -> ModConfig.INSTANCE.ExpBarXOffset = v,
            () -> ModConfig.INSTANCE.ExpBarYOffset, v -> ModConfig.INSTANCE.ExpBarYOffset = v,
            () -> ModConfig.INSTANCE.ExpBarOrigin, v -> ModConfig.INSTANCE.ExpBarOrigin = v,
            () -> screenWidth() / 2 - 91, () -> screenHeight() - 29,
            182, 5),

    HELD_ITEM_TOOLTIP("Held Item Tooltip",
            () -> ModConfig.INSTANCE.HeldItemTooltipXOffset, v -> ModConfig.INSTANCE.HeldItemTooltipXOffset = v,
            () -> ModConfig.INSTANCE.HeldItemTooltipYOffset, v -> ModConfig.INSTANCE.HeldItemTooltipYOffset = v,
            () -> ModConfig.INSTANCE.HeldItemTooltipOrigin, v -> ModConfig.INSTANCE.HeldItemTooltipOrigin = v,
            () -> (screenWidth() - 120) / 2, () -> screenHeight() - 59,
            120, 12),

    OVERLAY_MESSAGE("Overlay Message",
            () -> ModConfig.INSTANCE.OverlayMessageXOffset, v -> ModConfig.INSTANCE.OverlayMessageXOffset = v,
            () -> ModConfig.INSTANCE.OverlayMessageYOffset, v -> ModConfig.INSTANCE.OverlayMessageYOffset = v,
            () -> ModConfig.INSTANCE.OverlayMessageOrigin, v -> ModConfig.INSTANCE.OverlayMessageOrigin = v,
            () -> (screenWidth() - 120) / 2, () -> screenHeight() - 72,
            120, 12);

    public final String label;
    private final IntSupplier xGet;
    private final IntConsumer xSet;
    private final IntSupplier yGet;
    private final IntConsumer ySet;
    private final Supplier<OriginPoint> originGet;
    private final Consumer<OriginPoint> originSet;
    private final IntSupplier vanillaX;
    private final IntSupplier vanillaY;
    public final int width;
    public final int height;

    HudElement(String label,
               IntSupplier xGet, IntConsumer xSet,
               IntSupplier yGet, IntConsumer ySet,
               Supplier<OriginPoint> originGet, Consumer<OriginPoint> originSet,
               IntSupplier vanillaX, IntSupplier vanillaY,
               int width, int height) {
        this.label = label;
        this.xGet = xGet;
        this.xSet = xSet;
        this.yGet = yGet;
        this.ySet = ySet;
        this.originGet = originGet;
        this.originSet = originSet;
        this.vanillaX = vanillaX;
        this.vanillaY = vanillaY;
        this.width = width;
        this.height = height;
    }

    private static int screenWidth() {
        return Minecraft.getInstance().getWindow().getGuiScaledWidth();
    }

    private static int screenHeight() {
        return Minecraft.getInstance().getWindow().getGuiScaledHeight();
    }

    private static int snap(int value) {
        if (ModConfig.INSTANCE.OffsetSnapping && Math.abs(value) < ModConfig.INSTANCE.OffsetSnappingStrength) {
            return 0;
        }
        return value;
    }

    public int getX() {
        return xGet.getAsInt();
    }

    public void setX(int value) {
        xSet.accept(snap(value));
    }

    /**
     * Sets X without applying the snap-to-zero clamp. Used only to jump an
     * element clear of the snap dead zone once a double-tap has confirmed
     * the player actually wants to move away from center (see
     * HudEditorState#attemptNudge) - a plain setX() there would just get
     * immediately snapped straight back to 0.
     */
    public void setXRaw(int value) {
        xSet.accept(value);
    }

    public void nudgeX(int delta) {
        setX(getX() + delta);
    }

    public int getY() {
        return yGet.getAsInt();
    }

    public void setY(int value) {
        ySet.accept(snap(value));
    }

    public void setYRaw(int value) {
        ySet.accept(value);
    }

    public void nudgeY(int delta) {
        setY(getY() + delta);
    }

    public OriginPoint getOrigin() {
        return originGet.get();
    }

    public void cycleOrigin() {
        OriginPoint[] values = OriginPoint.values();
        int next = (getOrigin().ordinal() + 1) % values.length;
        originSet.accept(values[next]);
    }

    /**
     * Resets this element back to the vanilla position: zero offset, no anchor.
     */
    public void reset() {
        setXRaw(0);
        setYRaw(0);
        originSet.accept(OriginPoint.ORIGIN);
    }

    /**
     * Current on-screen bounding box {x, y, width, height}, accounting for
     * anchor corner and manual offset. Formula-based rather than recorded
     * from the last frame, so it stays correct even for elements that are
     * conditionally invisible (e.g. the air bar while not underwater) or
     * currently toggled off.
     */
    public int[] currentBounds() {
        int x = getOrigin().resolveX(vanillaX.getAsInt(), width) + getX();
        int y = getOrigin().resolveY(vanillaY.getAsInt(), height) + getY();
        return new int[]{x, y, width, height};
    }

    public static HudElement next(HudElement current) {
        HudElement[] values = values();
        return values[(current.ordinal() + 1) % values.length];
    }
}
