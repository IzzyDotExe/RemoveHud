package ca.blutopia.removehud;

import ca.blutopia.removehud.config.HudElement;
import com.mojang.blaze3d.platform.InputConstants;
import me.shedaniel.autoconfig.AutoConfigClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

public class RemoveHud implements ClientModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LogManager.getLogger("removehud");

	private static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath("removehud", "removehud"));

	private KeyMapping keynmap;
	private KeyMapping keynmap2;
	private KeyMapping toggleEditorKey;
	private KeyMapping cycleKey;
	private KeyMapping toggleSnappingKey;
	private KeyMapping resetElementKey;
	private KeyMapping toggleControlsHintKey;
	private KeyMapping nudgeUpKey;
	private KeyMapping nudgeDownKey;
	private KeyMapping nudgeLeftKey;
	private KeyMapping nudgeRightKey;

	@Override
	public void onInitializeClient() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		ModConfig.init();

		keynmap = KeyMappingHelper.registerKeyMapping(new KeyMapping("key.removehud.toggle_mod", GLFW.GLFW_KEY_F7, CATEGORY));
		keynmap2 = KeyMappingHelper.registerKeyMapping(new KeyMapping("key.removehud.open_settings", GLFW.GLFW_KEY_F8, CATEGORY));
		toggleEditorKey = KeyMappingHelper.registerKeyMapping(new KeyMapping("key.removehud.toggle_editor", GLFW.GLFW_KEY_F6, CATEGORY));
		cycleKey = KeyMappingHelper.registerKeyMapping(new KeyMapping("key.removehud.cycle", GLFW.GLFW_KEY_TAB, CATEGORY));
		toggleSnappingKey = KeyMappingHelper.registerKeyMapping(new KeyMapping("key.removehud.toggle_snapping", GLFW.GLFW_KEY_N, CATEGORY));
		resetElementKey = KeyMappingHelper.registerKeyMapping(new KeyMapping("key.removehud.reset_element", GLFW.GLFW_KEY_R, CATEGORY));
		toggleControlsHintKey = KeyMappingHelper.registerKeyMapping(new KeyMapping("key.removehud.toggle_controls_hint", GLFW.GLFW_KEY_H, CATEGORY));
		nudgeUpKey = KeyMappingHelper.registerKeyMapping(new KeyMapping("key.removehud.nudge_up", GLFW.GLFW_KEY_UP, CATEGORY));
		nudgeDownKey = KeyMappingHelper.registerKeyMapping(new KeyMapping("key.removehud.nudge_down", GLFW.GLFW_KEY_DOWN, CATEGORY));
		nudgeLeftKey = KeyMappingHelper.registerKeyMapping(new KeyMapping("key.removehud.nudge_left", GLFW.GLFW_KEY_LEFT, CATEGORY));
		nudgeRightKey = KeyMappingHelper.registerKeyMapping(new KeyMapping("key.removehud.nudge_right", GLFW.GLFW_KEY_RIGHT, CATEGORY));

		ClientTickEvents.END_CLIENT_TICK.register(this::removeHudToggleListener);

		ClientTickEvents.END_CLIENT_TICK.register(this::settingsMenuListener);

		ClientTickEvents.END_CLIENT_TICK.register(this::editorListener);

	}

	private void settingsMenuListener(Minecraft client) {
		while (keynmap2.consumeClick()) {
			client.setScreenAndShow(AutoConfigClient.getConfigScreen(ModConfig.class, null).get());
		}
	}
	private void removeHudToggleListener(Minecraft client) {
		while (keynmap.consumeClick()) {
			ModConfig.INSTANCE.removeHud = !ModConfig.INSTANCE.removeHud;
		}
	}

	private void editorListener(Minecraft client) {
		while (toggleEditorKey.consumeClick()) {
			HudEditorState.active = !HudEditorState.active;
		}

		if (!HudEditorState.active) {
			return;
		}

		boolean shiftHeld = InputConstants.isKeyDown(client.getWindow(), InputConstants.KEY_LSHIFT)
				|| InputConstants.isKeyDown(client.getWindow(), InputConstants.KEY_RSHIFT);

		while (cycleKey.consumeClick()) {
			if (shiftHeld) {
				HudEditorState.selected.cycleOrigin();
			} else {
				HudEditorState.selected = HudElement.next(HudEditorState.selected);
			}
		}

		while (toggleSnappingKey.consumeClick()) {
			ModConfig.INSTANCE.OffsetSnapping = !ModConfig.INSTANCE.OffsetSnapping;
			HudEditorState.showHint("Offset snapping " + (ModConfig.INSTANCE.OffsetSnapping ? "enabled" : "disabled"));
		}

		while (resetElementKey.consumeClick()) {
			HudEditorState.selected.reset();
			HudEditorState.showHint(HudEditorState.selected.label + " reset");
		}

		while (toggleControlsHintKey.consumeClick()) {
			HudEditorState.showControls = !HudEditorState.showControls;
		}

		int step = shiftHeld ? 5 : 1;

		while (nudgeUpKey.consumeClick()) {
			HudEditorState.attemptNudge(0, -step);
		}
		while (nudgeDownKey.consumeClick()) {
			HudEditorState.attemptNudge(0, step);
		}
		while (nudgeLeftKey.consumeClick()) {
			HudEditorState.attemptNudge(-step, 0);
		}
		while (nudgeRightKey.consumeClick()) {
			HudEditorState.attemptNudge(step, 0);
		}
	}

}
