package com.starfish_studios.seasons_greetings.registry;

import com.starfish_studios.seasons_greetings.common.conditions.RecipeLoadCondition;
import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.minecraft.resources.ResourceLocation;

public class SGConditions {
    private static final ResourceLocation BLOCKS_ENABLED = SeasonsGreetings.id("blocks_enabled");
    public static final ResourceConditionType<RecipeLoadCondition> BLOCKS = ResourceConditionType.create(BLOCKS_ENABLED, RecipeLoadCondition.CODEC);
}
