package com.starfish_studios.seasons_greetings;

import com.starfish_studios.seasons_greetings.client.SeasonsGreetingsClient;
import com.starfish_studios.seasons_greetings.event.SnowCauldronUseEvent;
import com.starfish_studios.seasons_greetings.event.WreathInteractions;
import com.starfish_studios.seasons_greetings.registry.SGRegistry;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
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

	@Override
	public void onInitialize() {

		MidnightConfig.init(MOD_ID, SGConfig.class);

		SGRegistry.registerAll();

		UseBlockCallback.EVENT.register(new SnowCauldronUseEvent());
		UseBlockCallback.EVENT.register(new WreathInteractions());
	}
}