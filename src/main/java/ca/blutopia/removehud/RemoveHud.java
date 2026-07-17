package ca.blutopia.removehud;

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

	@Override
	public void onInitializeClient() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		ModConfig.init();

		keynmap = KeyMappingHelper.registerKeyMapping(new KeyMapping("key.removehud.toggle_mod", GLFW.GLFW_KEY_F7, CATEGORY));
		keynmap2 = KeyMappingHelper.registerKeyMapping(new KeyMapping("key.removehud.open_settings", GLFW.GLFW_KEY_F8, CATEGORY));

		ClientTickEvents.END_CLIENT_TICK.register(this::removeHudToggleListener);

		ClientTickEvents.END_CLIENT_TICK.register(this::settingsMenuListener);

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

}
