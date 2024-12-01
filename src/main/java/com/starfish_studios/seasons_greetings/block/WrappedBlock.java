package com.starfish_studios.seasons_greetings.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.MapCodec;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WrappedBlock extends Block {

    public static final MapCodec<WrappedBlock> CODEC = simpleCodec(WrappedBlock::new);
    public static final BooleanProperty NORTH = PipeBlock.NORTH;
    public static final BooleanProperty EAST = PipeBlock.EAST;
    public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
    public static final BooleanProperty WEST = PipeBlock.WEST;
    public static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = ImmutableMap.copyOf((Map) Util.make(Maps.newEnumMap(Direction.class), (enumMap) -> {
        enumMap.put(Direction.NORTH, NORTH);
        enumMap.put(Direction.EAST, EAST);
        enumMap.put(Direction.SOUTH, SOUTH);
        enumMap.put(Direction.WEST, WEST);
    }));
    private static final VoxelShape WEST_AABB = Block.box(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
    private static final VoxelShape EAST_AABB = Block.box(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape NORTH_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
    private static final VoxelShape SOUTH_AABB = Block.box(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
    private final Map<BlockState, VoxelShape> shapesCache;

    public WrappedBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(NORTH, false)
                .setValue(EAST, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false));
        this.shapesCache = ImmutableMap.copyOf((Map)this.stateDefinition.getPossibleStates().stream().collect(Collectors.toMap(Function.identity(), WrappedBlock::calculateShape)));
    }

    private static VoxelShape calculateShape(BlockState blockState) {
        VoxelShape voxelShape = Shapes.empty();

        if (blockState.getValue(NORTH)) {
            voxelShape = Shapes.or(voxelShape, NORTH_AABB);
        }

        if (blockState.getValue(SOUTH)) {
            voxelShape = Shapes.or(voxelShape, SOUTH_AABB);
        }

        if (blockState.getValue(EAST)) {
            voxelShape = Shapes.or(voxelShape, EAST_AABB);
        }

        if (blockState.getValue(WEST)) {
            voxelShape = Shapes.or(voxelShape, WEST_AABB);
        }

        return voxelShape.isEmpty() ? Shapes.block() : voxelShape;
    }

    protected @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return this.shapesCache.get(blockState);
    }

    protected boolean propagatesSkylightDown(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return true;
    }

    protected boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        return this.hasFaces(this.getUpdatedState(blockState, levelReader, blockPos));
    }

    private boolean hasFaces(BlockState blockState) {
        return this.countFaces(blockState) > 0;
    }

    private int countFaces(BlockState blockState) {
        int i = 0;
        for (BooleanProperty booleanProperty : PROPERTY_BY_DIRECTION.values()) {
            if (blockState.getValue(booleanProperty)) {
                ++i;
            }
        }

        return i;
    }

    private boolean canSupportAtFace(BlockGetter blockGetter, BlockPos blockPos, Direction direction) {
            BlockPos blockPos2 = blockPos.relative(direction);
            if (isAcceptableNeighbour(blockGetter, blockPos2, direction)) {
                return true;
            } else if (direction.getAxis() == Direction.Axis.Y) {
                return false;
            } else {
                BooleanProperty booleanProperty = PROPERTY_BY_DIRECTION.get(direction);
                BlockState blockState = blockGetter.getBlockState(blockPos.above());
                return blockState.is(this) && blockState.getValue(booleanProperty);
            }
    }

    public static boolean isAcceptableNeighbour(BlockGetter blockGetter, BlockPos blockPos, Direction direction) {
        return MultifaceBlock.canAttachTo(blockGetter, direction, blockPos, blockGetter.getBlockState(blockPos));
    }

    private BlockState getUpdatedState(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.above();

        BlockState blockState2 = null;
        Iterator<Direction> var6 = Direction.Plane.HORIZONTAL.iterator();

        while(true) {
            Direction direction;
            BooleanProperty booleanProperty;
            do {
                if (!var6.hasNext()) {
                    return blockState;
                }

                direction = var6.next();
                booleanProperty = getPropertyForFace(direction);
            } while(!(Boolean)blockState.getValue(booleanProperty));

            boolean bl = this.canSupportAtFace(blockGetter, blockPos, direction);
            if (!bl) {
                if (blockState2 == null) {
                    blockState2 = blockGetter.getBlockState(blockPos2);
                }

                bl = blockState2.is(this) && blockState2.getValue(booleanProperty);
            }

            blockState = blockState.setValue(booleanProperty, bl);
        }
    }

    protected @NotNull BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        BlockState blockState3 = this.getUpdatedState(blockState, levelAccessor, blockPos);
        return !this.hasFaces(blockState3) ? Blocks.AIR.defaultBlockState() : blockState3;
    }

    protected boolean canBeReplaced(BlockState blockState, BlockPlaceContext blockPlaceContext) {
        BlockState blockState2 = blockPlaceContext.getLevel().getBlockState(blockPlaceContext.getClickedPos());
        if (blockState2.is(this)) {
            return this.countFaces(blockState2) < PROPERTY_BY_DIRECTION.size();
        } else {
            return super.canBeReplaced(blockState, blockPlaceContext);
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockState blockState = blockPlaceContext.getLevel().getBlockState(blockPlaceContext.getClickedPos());
        boolean bl = blockState.is(this);
        BlockState blockState2 = super.getStateForPlacement(blockPlaceContext);
        if (blockState2 != null && !blockState2.is(Blocks.AIR)) {
            blockState2 = blockState2.setValue(NORTH, this.canSupportAtFace(blockPlaceContext.getLevel(), blockPlaceContext.getClickedPos(), Direction.NORTH));
            blockState2 = blockState2.setValue(EAST, this.canSupportAtFace(blockPlaceContext.getLevel(), blockPlaceContext.getClickedPos(), Direction.EAST));
            blockState2 = blockState2.setValue(SOUTH, this.canSupportAtFace(blockPlaceContext.getLevel(), blockPlaceContext.getClickedPos(), Direction.SOUTH));
            blockState2 = blockState2.setValue(WEST, this.canSupportAtFace(blockPlaceContext.getLevel(), blockPlaceContext.getClickedPos(), Direction.WEST));
            return blockState2;
        } else {
            return null;
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST);
    }

    protected @NotNull BlockState rotate(BlockState blockState, Rotation rotation) {
        switch (rotation) {
            case CLOCKWISE_180 -> {
                return blockState.setValue(NORTH, blockState.getValue(SOUTH)).setValue(EAST, blockState.getValue(WEST)).setValue(SOUTH, blockState.getValue(NORTH)).setValue(WEST, blockState.getValue(EAST));
            }
            case COUNTERCLOCKWISE_90 -> {
                return blockState.setValue(NORTH, blockState.getValue(EAST)).setValue(EAST, blockState.getValue(SOUTH)).setValue(SOUTH, blockState.getValue(WEST)).setValue(WEST, blockState.getValue(NORTH));
            }
            case CLOCKWISE_90 -> {
                return blockState.setValue(NORTH, blockState.getValue(WEST)).setValue(EAST, blockState.getValue(NORTH)).setValue(SOUTH, blockState.getValue(EAST)).setValue(WEST, blockState.getValue(SOUTH));
            }
            default -> {
                return blockState;
            }
        }
    }

    protected @NotNull BlockState mirror(BlockState blockState, Mirror mirror) {
        switch (mirror) {
            case LEFT_RIGHT -> {
                return blockState.setValue(NORTH, blockState.getValue(SOUTH)).setValue(SOUTH, blockState.getValue(NORTH));
            }
            case FRONT_BACK -> {
                return blockState.setValue(EAST, blockState.getValue(WEST)).setValue(WEST, blockState.getValue(EAST));
            }
            default -> {
                return super.mirror(blockState, mirror);
            }
        }
    }

    public static BooleanProperty getPropertyForFace(Direction direction) {
        return PROPERTY_BY_DIRECTION.get(direction);
    }
}
