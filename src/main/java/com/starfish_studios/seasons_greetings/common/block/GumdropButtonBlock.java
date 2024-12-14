package com.starfish_studios.seasons_greetings.common.block;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import org.jetbrains.annotations.NotNull;

public class GumdropButtonBlock extends ButtonBlock {

    public GumdropButtonBlock(BlockSetType blockSetType, int i, Properties properties) {
        super(blockSetType, i, properties);
    }

    @Override
    protected @NotNull SoundEvent getSound(boolean bl) {
        return bl ? SoundEvents.SLIME_SQUISH_SMALL : SoundEvents.SLIME_JUMP_SMALL;
    }

}
