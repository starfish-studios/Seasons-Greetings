package com.starfish_studios.seasons_greetings;

import com.starfish_studios.seasons_greetings.client.SeasonsGreetingsClient;
import com.starfish_studios.seasons_greetings.event.SnowCauldronUseEvent;
import com.starfish_studios.seasons_greetings.registry.SGRegistry;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeasonsGreetings implements ModInitializer {
	public static final String MOD_ID = "seasonsgreetings";

	public static ResourceLocation id(String name) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
	}


	public static int getColor(int rgb) {
		return (255 << 24) | rgb;
	}

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		SGRegistry.registerAll();

		LOGGER.info("Hello Fabric world!");


		UseBlockCallback.EVENT.register(new SnowCauldronUseEvent());
	}
}