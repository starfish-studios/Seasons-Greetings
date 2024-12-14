package com.starfish_studios.seasons_greetings.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

import java.util.function.ToIntFunction;

public class WrappedBlock extends MultifaceBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<WrappedBlock> CODEC = simpleCodec(WrappedBlock::new);
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private final MultifaceSpreader spreader = new MultifaceSpreader(this);

    public @NotNull MapCodec<WrappedBlock> codec() {
        return CODEC;
    }

    public WrappedBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false));
    }

    public static ToIntFunction<BlockState> emission(int i) {
        return (blockState) -> {
            return MultifaceBlock.hasAnyFace(blockState) ? i : 0;
        };
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED);
    }

    protected @NotNull BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        if (blockState.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }

        return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
    }

    protected boolean canBeReplaced(BlockState blockState, BlockPlaceContext blockPlaceContext) {
        return blockPlaceContext.getItemInHand().getItem() == this.asItem();
    }

    protected @NotNull FluidState getFluidState(BlockState blockState) {
        return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    protected boolean propagatesSkylightDown(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return blockState.getFluidState().isEmpty();
    }

    public @NotNull MultifaceSpreader getSpreader() {
        return this.spreader;
    }
}
