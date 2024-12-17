package com.starfish_studios.seasons_greetings.common.conditions;

import com.mojang.serialization.MapCodec;
import com.starfish_studios.seasons_greetings.SGConfig;
import com.starfish_studios.seasons_greetings.registry.SGConditions;
import net.neoforged.neoforge.common.conditions.ICondition;

public record StructureSpawnCondition() implements ICondition {

    public static final MapCodec<StructureSpawnCondition> CODEC = MapCodec.unit(StructureSpawnCondition::new);

    @Override
    public MapCodec<? extends ICondition> codec() {
        return SGConditions.STRUCTURE_SPAWN.get();
    }

    @Override
    public boolean test(IContext context) {
        boolean enabled = SGConfig.witchHouse;
        System.out.println("[DEBUG] Witch House condition is set to: " + enabled);
        return enabled;
    }
}
