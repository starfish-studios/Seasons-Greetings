package com.starfish_studios.seasons_greetings.common.block;

import com.mojang.serialization.MapCodec;
import com.starfish_studios.seasons_greetings.SGConfig;
import com.starfish_studios.seasons_greetings.registry.SGBlocks;
import com.starfish_studios.seasons_greetings.registry.SGItems;
import com.starfish_studios.seasons_greetings.registry.SGParticles;
import com.starfish_studios.seasons_greetings.registry.SGSoundEvents;
import com.starfish_studios.seasons_greetings.registry.SGTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class MilkCauldronBlock extends AbstractCauldronBlock {

    @SuppressWarnings("all")
    @Override
    protected @NotNull MapCodec<? extends AbstractCauldronBlock> codec() {
        return null;
    }

    public MilkCauldronBlock(Properties properties, CauldronInteraction.InteractionMap interactionMap) {
        super(properties, interactionMap);
        this.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        return InteractionResult.PASS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateDefinition) {
        stateDefinition.add(LayeredCauldronBlock.LEVEL);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        return new ItemStack((Items.CAULDRON));
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        if (blockState.is(SGBlocks.MILK_CAULDRON) && level.getBlockState(blockPos.below()).is(SGTags.SGBlockTags.HEAT_SOURCES)) {
            if (blockState.getValue(LayeredCauldronBlock.LEVEL) == 3) {
                for (int i = 0; i < 2; ++i) {
                    double x = (double) blockPos.getX() + 0.3 + ((level.random.nextDouble() * 0.8D) - 0.2D);
                    double y = (double) blockPos.getY() + 1 + (level.random.nextDouble() * 0.2D - 0.1D);
                    double z = (double) blockPos.getZ() + 0.3 + ((level.random.nextDouble() * 0.8D) - 0.2D);

                    level.addParticle(SGParticles.MILK_BUBBLE.get(), x, y, z, 0.0D, 0.0D, 0.0D);
                }
            } else if (blockState.getValue(LayeredCauldronBlock.LEVEL) == 2) {
                for (int i = 0; i < 2; ++i) {
                    double x = (double) blockPos.getX() + 0.3 + ((level.random.nextDouble() * 0.8D) - 0.2D);
                    double y = (double) blockPos.getY() + 0.8 + (level.random.nextDouble() * 0.2D - 0.1D);
                    double z = (double) blockPos.getZ() + 0.3 + ((level.random.nextDouble() * 0.8D) - 0.2D);

                    level.addParticle(SGParticles.MILK_BUBBLE.get(), x, y, z, 0.0D, 0.0D, 0.0D);
                }
            } else if (blockState.getValue(LayeredCauldronBlock.LEVEL) == 1) {
                for (int i = 0; i < 2; ++i) {
                    double x = (double) blockPos.getX() + 0.3 + ((level.random.nextDouble() * 0.8D) - 0.2D);
                    double y = (double) blockPos.getY() + 0.6 + (level.random.nextDouble() * 0.2D - 0.1D);
                    double z = (double) blockPos.getZ() + 0.3 + ((level.random.nextDouble() * 0.8D) - 0.2D);

                    level.addParticle(SGParticles.MILK_BUBBLE.get(), x, y, z, 0.0D, 0.0D, 0.0D);
                }
            }


            if (randomSource.nextInt(10) == 0) {
                level.playLocalSound((double) blockPos.getX() + 0.5, (double) blockPos.getY() + 0.5, (double) blockPos.getZ() + 0.5,
                SGSoundEvents.MILK_CAULDRON_BUBBLE.get(), SoundSource.BLOCKS, 0.3F, 1.0F + level.random.nextFloat() * 0.2F, false);
            }
        }
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {

        if (itemStack.is(Items.BUCKET) && blockState.getValue(LayeredCauldronBlock.LEVEL) == 3 && level.getBlockState(blockPos.below()).is(SGTags.SGBlockTags.HEAT_SOURCES)) {
            if (SGConfig.warningPings && !SGConfig.calmerWarnings) {
                player.playSound(SoundEvents.ARROW_HIT_PLAYER, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);

                player.displayClientMessage(Component.literal("[!] ")
                        .append(Component.translatable("block.seasons_greetings.warm_milk_cauldron.bucket"))
                        .withStyle(ChatFormatting.RED).withStyle(ChatFormatting.ITALIC), true);
                player.swing(interactionHand);
            } else if (SGConfig.calmerWarnings) {
                player.displayClientMessage(Component.translatable("block.seasons_greetings.warm_milk_cauldron.bucket"), true);
            } else {
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            }
            player.swing(interactionHand);
        }

        else if (itemStack.is(Items.BUCKET) && blockState.getValue(LayeredCauldronBlock.LEVEL) == 3 && !level.getBlockState(blockPos.below()).is(SGTags.SGBlockTags.HEAT_SOURCES)) {
            level.setBlockAndUpdate(blockPos, Blocks.CAULDRON.defaultBlockState());
            if (!player.isCreative()) {
                itemStack.shrink(1);
                if (!player.getInventory().add(new ItemStack(Items.MILK_BUCKET))) {
                    player.drop(new ItemStack(Items.MILK_BUCKET), false);
                }
            } else
            if (player.isCreative() && !player.getInventory().contains(new ItemStack(Items.MILK_BUCKET))) {
                if (!player.getInventory().add(new ItemStack(Items.MILK_BUCKET))) {
                    player.drop(new ItemStack(Items.MILK_BUCKET), false);
                }
            }

            player.playSound(SoundEvents.BUCKET_FILL, 1.0F, 1.0F);
            return ItemInteractionResult.SUCCESS;
        }

        if (itemStack.is(Items.MILK_BUCKET) && blockState.getValue(LayeredCauldronBlock.LEVEL) < 3) {
            level.setBlockAndUpdate(blockPos, blockState.setValue(LayeredCauldronBlock.LEVEL, 3));
            if (!player.isCreative()) {
                itemStack.shrink(1);
                if (!player.getInventory().add(new ItemStack(Items.BUCKET))) {
                    player.drop(new ItemStack(Items.BUCKET), false);
                }
            }
            player.playSound(SoundEvents.BUCKET_EMPTY, 1.0F, 1.0F);
            return ItemInteractionResult.SUCCESS;
        }

        else if (itemStack.is(Items.GLASS_BOTTLE) && !level.getBlockState(blockPos.below()).is(SGTags.SGBlockTags.HEAT_SOURCES)) {
            if (SGConfig.warningPings && !SGConfig.calmerWarnings) {
                player.playSound(SoundEvents.ARROW_HIT_PLAYER, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);

                player.displayClientMessage(Component.literal("[!] ")
                        .append(Component.translatable("block.seasons_greetings.warm_milk_cauldron.bottle"))
                        .withStyle(ChatFormatting.RED).withStyle(ChatFormatting.ITALIC), true);
                player.swing(interactionHand);
            }
            else if (SGConfig.calmerWarnings) {
                player.displayClientMessage(Component.translatable("block.seasons_greetings.warm_milk_cauldron.bottle"), true);
            } else {
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            }
            player.swing(interactionHand);
        }

        else if (itemStack.is(Items.GLASS_BOTTLE) && level.getBlockState(blockPos.below()).is(SGTags.SGBlockTags.HEAT_SOURCES)) {
            if (blockState.getValue(LayeredCauldronBlock.LEVEL) == 1) {
                level.setBlockAndUpdate(blockPos, Blocks.CAULDRON.defaultBlockState());
                if (!player.isCreative()) {
                    itemStack.shrink(1);
                }
                if (!player.getInventory().add(new ItemStack(SGItems.WARM_MILK.asItem()))) {
                    player.drop(new ItemStack(SGItems.WARM_MILK.asItem()), false);
                }
                player.playSound(SoundEvents.BOTTLE_FILL, 1.0F, 1.0F);
                return ItemInteractionResult.SUCCESS;
            } else if (blockState.getValue(LayeredCauldronBlock.LEVEL) > 1) {
                level.setBlockAndUpdate(blockPos, blockState.setValue(LayeredCauldronBlock.LEVEL, blockState.getValue(LayeredCauldronBlock.LEVEL) - 1));
                if (!player.isCreative()) {
                    itemStack.shrink(1);
                    if (!player.getInventory().add(new ItemStack(SGItems.WARM_MILK.asItem()))) {
                        player.drop(new ItemStack(SGItems.WARM_MILK.asItem()), false);
                    }
                }
                player.playSound(SoundEvents.BOTTLE_FILL, 1.0F, 1.0F);
                return ItemInteractionResult.SUCCESS;
            }

            else if (itemStack.is(SGItems.WARM_MILK)) {
                if (blockState.getValue(LayeredCauldronBlock.LEVEL) < 3) {
                    level.setBlockAndUpdate(blockPos, blockState.setValue(LayeredCauldronBlock.LEVEL, blockState.getValue(LayeredCauldronBlock.LEVEL) + 1));
                    if (!player.isCreative()) {
                        itemStack.shrink(1);
                        if (!player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE))) {
                            player.drop(new ItemStack(Items.GLASS_BOTTLE), false);
                        }
                    }
                    player.playSound(SoundEvents.BOTTLE_EMPTY, 1.0F, 1.0F);
                    return ItemInteractionResult.SUCCESS;
                }
            }

//            else if (itemStack.is(SGItems.WARM_MILK_BUCKET)) {
//                if (blockState.getValue(LayeredCauldronBlock.LEVEL) < 3) {
//                    level.setBlockAndUpdate(blockPos, blockState.setValue(LayeredCauldronBlock.LEVEL, 3));
//                    if (!player.isCreative()) {
//                        itemStack.shrink(1);
//                        if (!player.getInventory().add(new ItemStack(Items.BUCKET))) {
//                            player.drop(new ItemStack(Items.BUCKET), false);
//                        }
//                    }
//                    player.playSound(SoundEvents.BUCKET_EMPTY, 1.0F, 1.0F);
//                    return ItemInteractionResult.SUCCESS;
//                }
//            }
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public boolean isFull(BlockState blockState) {
        return true;
    }

}