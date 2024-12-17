package com.starfish_studios.seasons_greetings.registry;

import com.mojang.serialization.MapCodec;
import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import com.starfish_studios.seasons_greetings.common.conditions.RecipeLoadCondition;
import com.starfish_studios.seasons_greetings.common.conditions.StructureSpawnCondition;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class SGConditions {
    private static final DeferredRegister<MapCodec<? extends ICondition>> CONDITION_CODECS = DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, SeasonsGreetings.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends ICondition>, MapCodec<RecipeLoadCondition>> BLOCKS = CONDITION_CODECS.register("block_enabled", () -> RecipeLoadCondition.CODEC);
    public static final DeferredHolder<MapCodec<? extends ICondition>, MapCodec<StructureSpawnCondition>> STRUCTURE_SPAWN = CONDITION_CODECS.register("witch_house_enabled", () -> StructureSpawnCondition.CODEC);

    public static void registerConditions(IEventBus eventBus) {
        CONDITION_CODECS.register(eventBus);
    }
}
