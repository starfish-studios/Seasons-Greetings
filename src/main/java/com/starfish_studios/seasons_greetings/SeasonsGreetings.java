package com.starfish_studios.seasons_greetings;

import com.starfish_studios.seasons_greetings.client.SeasonsGreetingsClient;
import com.starfish_studios.seasons_greetings.event.CauldronUseEvent;
import com.starfish_studios.seasons_greetings.event.WreathInteractions;
import com.starfish_studios.seasons_greetings.registry.SGRegistry;
import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

@Mod(SeasonsGreetings.MOD_ID)
public class SeasonsGreetings {
	public static final String MOD_ID = "seasonsgreetings";

	public static ResourceLocation id(String name) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
	}

	public static int getColor(int rgb) {
		return (255 << 24) | rgb;
	}

	public SeasonsGreetings(IEventBus eventBus, Dist dist, ModContainer container) {

		MidnightConfig.init(MOD_ID, SGConfig.class);

		SGRegistry.registerAll(eventBus);

//		UseBlockCallback.EVENT.register(new CauldronUseEvent());
//		UseBlockCallback.EVENT.register(new WreathInteractions());

		NeoForge.EVENT_BUS.register(new CauldronUseEvent());
		NeoForge.EVENT_BUS.register(new WreathInteractions());

		if (dist.isClient()) {
			SeasonsGreetingsClient.init(eventBus);
		}
	}
}