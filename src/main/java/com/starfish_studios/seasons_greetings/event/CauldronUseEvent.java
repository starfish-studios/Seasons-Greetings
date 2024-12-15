package com.starfish_studios.seasons_greetings.event;

import com.starfish_studios.seasons_greetings.registry.SGBlocks;
import com.starfish_studios.seasons_greetings.registry.SGItems;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import static com.starfish_studios.seasons_greetings.common.block.HotCocoaCauldronBlock.giveItem;

public class CauldronUseEvent implements UseBlockCallback {
    @Override
    public InteractionResult interact(Player player, Level level, InteractionHand interactionHand, BlockHitResult blockHitResult) {

        ItemStack itemStack = player.getItemInHand(interactionHand);
        BlockState blockState = level.getBlockState(blockHitResult.getBlockPos());
        BlockPos blockPos = blockHitResult.getBlockPos();

        if (itemStack.is(SGItems.HOT_COCOA_BUCKET)) {
            if (blockState.is(SGBlocks.HOT_COCOA_CAULDRON) && blockState.getValue(LayeredCauldronBlock.LEVEL) < 3 || blockState.is(Blocks.CAULDRON)) {
                level.setBlockAndUpdate(blockPos, SGBlocks.HOT_COCOA_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3));
                giveItem(player, itemStack, new ItemStack(Items.BUCKET));
                player.playSound(SoundEvents.BUCKET_EMPTY, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }
        }

        if (itemStack.is(SGItems.HOT_COCOA)) {
            if (blockState.is(SGBlocks.HOT_COCOA_CAULDRON) && blockState.getValue(LayeredCauldronBlock.LEVEL) < 3 || blockState.is(Blocks.CAULDRON)) {
                if (blockState.is(Blocks.CAULDRON)) {
                    level.setBlockAndUpdate(blockPos, SGBlocks.HOT_COCOA_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 1));
                } else {
                    level.setBlockAndUpdate(blockPos, SGBlocks.HOT_COCOA_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, blockState.getValue(LayeredCauldronBlock.LEVEL) + 1));
                }
                giveItem(player, itemStack, new ItemStack(Items.GLASS_BOTTLE));
                player.playSound(SoundEvents.BOTTLE_EMPTY, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }
        }

        if (itemStack.is(SGItems.EGGNOG_BUCKET)) {
            if (blockState.is(SGBlocks.EGGNOG_CAULDRON) && blockState.getValue(LayeredCauldronBlock.LEVEL) < 3 || blockState.is(Blocks.CAULDRON)) {
                level.setBlockAndUpdate(blockPos, SGBlocks.EGGNOG_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3));
                giveItem(player, itemStack, new ItemStack(Items.BUCKET));
                player.playSound(SoundEvents.BUCKET_EMPTY, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }
        }

        if (itemStack.is(SGItems.EGGNOG)) {
            if (blockState.is(SGBlocks.EGGNOG_CAULDRON) && blockState.getValue(LayeredCauldronBlock.LEVEL) < 3 || blockState.is(Blocks.CAULDRON)) {
                if (blockState.is(Blocks.CAULDRON)) {
                    level.setBlockAndUpdate(blockPos, SGBlocks.EGGNOG_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 1));
                } else {
                    level.setBlockAndUpdate(blockPos, SGBlocks.EGGNOG_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, blockState.getValue(LayeredCauldronBlock.LEVEL) + 1));
                }
                giveItem(player, itemStack, new ItemStack(Items.GLASS_BOTTLE));
                player.playSound(SoundEvents.BOTTLE_EMPTY, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }
        }

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
