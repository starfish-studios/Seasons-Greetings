package com.starfish_studios.seasons_greetings.common.conditions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.starfish_studios.seasons_greetings.SGConfig;
import com.starfish_studios.seasons_greetings.registry.SGConditions;
import net.neoforged.neoforge.common.conditions.ICondition;

// This resource condition is used by the config, via MidnightLib, to check if blocks are enabled in the config.
// If they are not, it will disable their recipes alongside MidnightLib hiding them from the creative tab.
// It's not a full implementation of disabling blocks since they can't be "unregistered", but it's a workaround.
// In Forge, this should utilize IConditions instead.

public record RecipeLoadCondition(String configValue) implements ICondition {

    public static final MapCodec<RecipeLoadCondition> CODEC = RecordCodecBuilder.mapCodec(b -> b.group(
            Codec.STRING.fieldOf("block_enabled").forGetter(RecipeLoadCondition::configValue)
    ).apply(b, RecipeLoadCondition::new));

    @Override
    public MapCodec<? extends ICondition> codec() {
        return SGConditions.BLOCKS.get();
    }

    @Override
    public boolean test(IContext context) {
        return SGConfig.isConfigEnabled(configValue);
    }
}
