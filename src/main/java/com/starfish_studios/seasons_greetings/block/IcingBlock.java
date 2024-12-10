package com.starfish_studios.seasons_greetings.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IcingBlock extends FaceAttachedHorizontalDirectionalBlock {
    public static final IntegerProperty LAYERS = IntegerProperty.create("layers", 0, 4);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<AttachFace> FACE = BlockStateProperties.ATTACH_FACE;

    public static VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 14.0D, 16.0D, 16.0D, 16.0D);
    public static VoxelShape EAST_AABB = Block.box(0.0D, 0.0D, 0.0D, 2.0D, 16.0D, 16.0D);
    public static VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 2.0D);
    public static VoxelShape WEST_AABB = Block.box(14.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public static VoxelShape LAYER_1_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);
    public static VoxelShape LAYER_2_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    public static VoxelShape LAYER_3_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);
    public static VoxelShape LAYER_4_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public static VoxelShape DOWN_AABB = Block.box(2.0, 10.0, 2.0, 14.0, 16.0, 14.0);


    public IcingBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(LAYERS, 0)
                .setValue(FACING, Direction.NORTH)
                .setValue(FACE, AttachFace.FLOOR));
    }

    @Override
    protected MapCodec<? extends FaceAttachedHorizontalDirectionalBlock> codec() {
        return null;
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        VoxelShape shape;
        if (blockState.getValue(LAYERS) > 0) {
            if (blockState.getValue(FACE) == AttachFace.FLOOR) {
                shape = switch (blockState.getValue(LAYERS)) {
                    case 2 -> LAYER_2_AABB;
                    case 3 -> LAYER_3_AABB;
                    case 4 -> LAYER_4_AABB;
                    default -> LAYER_1_AABB;
                };
            } else {
                shape = Shapes.empty();
            }
        } else {
            shape = Shapes.empty();
        }

        return shape;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        VoxelShape shape;
        if (blockState.getValue((FACE)) == AttachFace.CEILING) {
            shape = DOWN_AABB;
        } else if (blockState.getValue((FACE)) == AttachFace.FLOOR) {
            switch (blockState.getValue(LAYERS)) {
                case 2 -> shape = LAYER_2_AABB;
                case 3 -> shape = LAYER_3_AABB;
                case 4 -> shape = LAYER_4_AABB;
                default -> shape = LAYER_1_AABB;
            }
        } else {
            shape = switch (blockState.getValue(FACING)) {
                case NORTH -> NORTH_AABB;
                case SOUTH -> SOUTH_AABB;
                case EAST -> EAST_AABB;
                case WEST -> WEST_AABB;
                default -> Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
            };
        }

        return shape;
    }

    @Override
    protected boolean canBeReplaced(BlockState blockState, BlockPlaceContext blockPlaceContext) {
        Direction clickedFace = blockPlaceContext.getClickedFace();
        return clickedFace == Direction.UP
                && blockPlaceContext.getItemInHand().getItem() == this.asItem()
                && blockState.getValue(LAYERS) >= 1
                && blockState.getValue(LAYERS) < 4
                || super.canBeReplaced(blockState, blockPlaceContext);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockState existingState = blockPlaceContext.getLevel().getBlockState(blockPlaceContext.getClickedPos());

        if (existingState.is(this) && existingState.hasProperty(LAYERS)) {
            int currentLayers = existingState.getValue(LAYERS);
            int newLayers = Math.min(currentLayers + 1, 4);
            return existingState.setValue(LAYERS, newLayers);
        }

        Direction[] var2 = blockPlaceContext.getNearestLookingDirections();

        for (Direction direction : var2) {
            BlockState blockState;
            if (direction.getAxis() == Direction.Axis.Y) {
                blockState = this.defaultBlockState()
                        .setValue(FACE, direction == Direction.UP ? AttachFace.CEILING : AttachFace.FLOOR)
                        .setValue(FACING, blockPlaceContext.getHorizontalDirection());

                if (blockState.getValue(FACE) == AttachFace.FLOOR) {
                    blockState = blockState.setValue(LAYERS, 1);
                }
            } else {
                blockState = this.defaultBlockState()
                        .setValue(FACE, AttachFace.WALL)
                        .setValue(FACING, direction.getOpposite());
            }

            if (blockState.canSurvive(blockPlaceContext.getLevel(), blockPlaceContext.getClickedPos())) {
                return blockState;
            }
        }

        return null;
    }



    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LAYERS, FACING, FACE);
        super.createBlockStateDefinition(builder);
    }
}
