package ca.blutopia.removehud;

import ca.blutopia.removehud.config.HudElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.network.chat.Component;

/**
 * Transient runtime state for the in-game HUD editor overlay. Deliberately
 * not part of ModConfig - it has no reason to persist across a restart.
 *
 * Nudging also guards the offset-snapping dead zone here: if an element is
 * resting at exactly 0 on an axis and snapping is on, a single nudge in that
 * axis would just get clamped straight back to 0 by HudElement's normal
 * setX/setY - there'd be no way to move away from center at all. So the
 * first tap is absorbed (with a hint toast shown), and a second tap of the
 * same direction within the double-tap window jumps the element clear of
 * the dead zone instead.
 */
public class HudEditorState {

    public static boolean active = false;
    public static boolean showControls = true;
    public static HudElement selected = HudElement.HOTBAR;

    private static final long DOUBLE_TAP_WINDOW_MS = 350;
    private static final SystemToast.SystemToastId TOAST_ID = new SystemToast.SystemToastId();

    private static int pendingDx = 0;
    private static int pendingDy = 0;
    private static long pendingTapMillis = 0;

    private HudEditorState() {
    }

    public static void attemptNudge(int dx, int dy) {
        HudElement element = selected;
        boolean snapping = ModConfig.INSTANCE.OffsetSnapping;
        boolean restingX = dx != 0 && snapping && element.getX() == 0;
        boolean restingY = dy != 0 && snapping && element.getY() == 0;

        if (!restingX && !restingY) {
            pendingDx = 0;
            pendingDy = 0;
            if (dx != 0) {
                element.nudgeX(dx);
            }
            if (dy != 0) {
                element.nudgeY(dy);
            }
            return;
        }

        long now = System.currentTimeMillis();
        boolean doubleTap = pendingDx == dx && pendingDy == dy && (now - pendingTapMillis) <= DOUBLE_TAP_WINDOW_MS;

        if (!doubleTap) {
            pendingDx = dx;
            pendingDy = dy;
            pendingTapMillis = now;
            showHint("At center - tap " + directionLabel(dx, dy) + " again to move");
            return;
        }

        // Confirmed: jump clear of the dead zone in one go, rather than one
        // pixel at a time (which would just get snapped back to 0 again).
        pendingDx = 0;
        pendingDy = 0;
        pendingTapMillis = 0;
        int strength = ModConfig.INSTANCE.OffsetSnappingStrength;
        if (restingX) {
            element.setXRaw(Integer.signum(dx) * strength);
        }
        if (restingY) {
            element.setYRaw(Integer.signum(dy) * strength);
        }
    }

    private static String directionLabel(int dx, int dy) {
        if (dy < 0) return "up";
        if (dy > 0) return "down";
        if (dx < 0) return "left";
        return "right";
    }

    public static void showHint(String message) {
        SystemToast.addOrUpdate(
                Minecraft.getInstance().gui.toastManager(),
                TOAST_ID,
                Component.literal("HUD Editor"),
                Component.literal(message)
        );
    }
}
