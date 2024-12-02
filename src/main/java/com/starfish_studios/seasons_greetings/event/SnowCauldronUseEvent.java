package com.starfish_studios.seasons_greetings.event;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.phys.BlockHitResult;

public class SnowCauldronUseEvent implements UseBlockCallback {
    @Override
    public InteractionResult interact(Player player, Level level, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (!player.getMainHandItem().getItem().equals(Items.BUCKET)) {
            if (level.getBlockState(blockHitResult.getBlockPos()).getBlock() == Blocks.POWDER_SNOW_CAULDRON && level.getBlockState(blockHitResult.getBlockPos()).getValue(LayeredCauldronBlock.LEVEL) > 0) {
                if (level.getBlockState(blockHitResult.getBlockPos()).getValue(LayeredCauldronBlock.LEVEL) == 1) {
                level.setBlockAndUpdate(blockHitResult.getBlockPos(), Blocks.CAULDRON.defaultBlockState());
                } else {
                level.setBlockAndUpdate(blockHitResult.getBlockPos(), Blocks.POWDER_SNOW_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL,
                        level.getBlockState(blockHitResult.getBlockPos()).getValue(LayeredCauldronBlock.LEVEL) - 1));
                }
                if (!player.getInventory().add(new ItemStack(Items.SNOWBALL))) {
                    level.addFreshEntity(new ItemEntity(level, blockHitResult.getLocation().x, blockHitResult.getLocation().y, blockHitResult.getLocation().z, new ItemStack(Items.SNOWBALL)));
                }
                level.playSound(null, blockHitResult.getBlockPos(), Blocks.POWDER_SNOW.defaultBlockState().getSoundType().getBreakSound(), player.getSoundSource(), 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
}
