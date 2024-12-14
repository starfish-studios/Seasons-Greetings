package com.starfish_studios.seasons_greetings.common.conditions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.starfish_studios.seasons_greetings.SGConfig;
import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

// This resource condition is used by the config, via MidnightLib, to check if blocks are enabled in the config.
// If they are not, it will disable their recipes alongside MidnightLib hiding them from the creative tab.
// It's not a full implementation of disabling blocks since they can't be "unregistered", but it's a workaround.
// In Forge, this should utilize IConditions instead.

public record RecipeLoadCondition(String configValue) implements ResourceCondition {

    public static final MapCodec<RecipeLoadCondition> CODEC = RecordCodecBuilder.mapCodec(b -> b.group(
            Codec.STRING.fieldOf("block_enabled").forGetter(RecipeLoadCondition::configValue)
    ).apply(b, RecipeLoadCondition::new));


    private static final ResourceLocation ID = SeasonsGreetings.id("block_enabled");
    private static final ResourceConditionType<RecipeLoadCondition> TYPE = ResourceConditionType.create(ID, CODEC);

    public static void register() {
        ResourceConditions.register(TYPE);
    }

    @Override
    public ResourceConditionType<?> getType() {
        return TYPE;
    }

    @Override
    public boolean test(@Nullable HolderLookup.Provider registryLookup) {
        return SGConfig.isConfigEnabled(configValue);
    }
}
