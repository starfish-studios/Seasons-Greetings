package com.starfish_studios.seasons_greetings.common.conditions;

import com.mojang.serialization.MapCodec;
import com.starfish_studios.seasons_greetings.SGConfig;
import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public record StructureSpawnCondition() implements ResourceCondition {

    public static final MapCodec<StructureSpawnCondition> CODEC = MapCodec.unit(StructureSpawnCondition::new);

    private static final ResourceLocation ID = SeasonsGreetings.id("witch_house_enabled");

    private static final ResourceConditionType<StructureSpawnCondition> TYPE = ResourceConditionType.create(ID, CODEC);

    public static void register() {
        ResourceConditions.register(TYPE);
    }

    @Override
    public ResourceConditionType<?> getType() {
        return TYPE;
    }

    @Override
    public boolean test(@Nullable HolderLookup.Provider registryLookup) {
        boolean enabled = SGConfig.witchHouse;
        System.out.println("[DEBUG] Witch House condition is set to: " + enabled);
        return enabled;
    }
}
