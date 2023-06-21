package ca.blutopia.removehud;

import ca.blutopia.removehud.config.ModConfig;
import ca.blutopia.removehud.gui.HudEditorScreen;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

public class RemoveHud implements ClientModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LogManager.getLogger("removehud");
	public static HUDManager HudManagerInstance;
	private KeyBinding keynmap;
	private KeyBinding keynmap2;
	private KeyBinding keynmap3;
	@Override
	public void onInitializeClient() {

		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		HudManagerInstance = new HUDManager();
		// ModConfig.init();

		keynmap = new KeyBinding("key.removehud.toggle_mod", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F1,"key.category.removehud");
		keynmap2 = new KeyBinding("key.removehud.open_settings", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F7, "key.category.removehud");
		keynmap3 = new KeyBinding("key.removehud.open_editor", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F8, "key.category.removehud");

		KeyBindingHelper.registerKeyBinding(keynmap);
		KeyBindingHelper.registerKeyBinding(keynmap2);
		KeyBindingHelper.registerKeyBinding(keynmap3);

		ClientTickEvents.END_CLIENT_TICK.register(this::removeHudToggleListener);

		ClientTickEvents.END_CLIENT_TICK.register(this::settingsMenuListener);

		ClientTickEvents.END_CLIENT_TICK.register(this::editorMenuListener);

	}
	private void editorMenuListener(MinecraftClient client) {

		while (keynmap3.wasPressed()) {
			Screen editor = new HudEditorScreen();
			client.setScreen(editor);
		}

	}
	private void settingsMenuListener(MinecraftClient client) {
		while (keynmap2.wasPressed()) {
			Screen settings = AutoConfig.getConfigScreen(ModConfig.class, null).get();
			client.setScreen(settings);
		}
	}
	private void removeHudToggleListener(MinecraftClient client) {

		while (keynmap.wasPressed()) {
			client.options.hudHidden = false;
			HudManagerInstance.toggleHud();
		}
	}

}
