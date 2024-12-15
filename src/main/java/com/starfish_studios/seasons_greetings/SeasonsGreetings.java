package com.starfish_studios.seasons_greetings;

import com.starfish_studios.seasons_greetings.event.CauldronUseEvent;
import com.starfish_studios.seasons_greetings.event.WreathInteractions;
import com.starfish_studios.seasons_greetings.registry.SGRegistry;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.resources.ResourceLocation;

public class SeasonsGreetings implements ModInitializer {
	public static final String MOD_ID = "seasonsgreetings";

	public static ResourceLocation id(String name) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
	}

	public static int getColor(int rgb) {
		return (255 << 24) | rgb;
	}

	@Override
	public void onInitialize() {

		MidnightConfig.init(MOD_ID, SGConfig.class);

		SGRegistry.registerAll();

		UseBlockCallback.EVENT.register(new CauldronUseEvent());
		UseBlockCallback.EVENT.register(new WreathInteractions());
	}
}