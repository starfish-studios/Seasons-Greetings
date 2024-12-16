package com.starfish_studios.seasons_greetings.common.block;

import com.starfish_studios.seasons_greetings.registry.SGBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IcicleBlock extends Block implements Fallable, SimpleWaterloggedBlock {
    // region Variables
    public static final DirectionProperty TIP_DIRECTION = BlockStateProperties.VERTICAL_DIRECTION;
    public static final EnumProperty<DripstoneThickness> THICKNESS = BlockStateProperties.DRIPSTONE_THICKNESS;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final int DELAY_BEFORE_FALLING = 2;

    private static final double MIN_TRIDENT_VELOCITY_TO_BREAK_DRIPSTONE = 0.6;
    private static final float STALACTITE_DAMAGE_PER_FALL_DISTANCE_AND_SIZE = 1.0F;
    private static final int STALACTITE_MAX_DAMAGE = 40;
    private static final int MAX_STALACTITE_HEIGHT_FOR_DAMAGE_CALCULATION = 6;
    private static final float STALAGMITE_FALL_DISTANCE_OFFSET = 2.0F;
    private static final int STALAGMITE_FALL_DAMAGE_MODIFIER = 2;
    private static final float AVERAGE_DAYS_PER_GROWTH = 5.0F;
    private static final float GROWTH_PROBABILITY_PER_RANDOM_TICK = 0.011377778F;
    private static final int MAX_GROWTH_LENGTH = 7;
    private static final int MAX_STALAGMITE_SEARCH_RANGE_WHEN_GROWING = 10;
    private static final float STALACTITE_DRIP_START_PIXEL = 0.6875F;

    private static final VoxelShape TIP_MERGE_SHAPE = Block.box(5.0, 0.0, 5.0, 11.0, 16.0, 11.0);
    private static final VoxelShape TIP_SHAPE_UP = Block.box(5.0, 0.0, 5.0, 11.0, 11.0, 11.0);
    private static final VoxelShape TIP_SHAPE_DOWN = Block.box(5.0, 5.0, 5.0, 11.0, 16.0, 11.0);
    private static final VoxelShape FRUSTUM_SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);
    private static final VoxelShape MIDDLE_SHAPE = Block.box(3.0, 0.0, 3.0, 13.0, 16.0, 13.0);
    private static final VoxelShape BASE_SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);
    private static final float MAX_HORIZONTAL_OFFSET = 0.125F;
    // endregion

    public IcicleBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(TIP_DIRECTION, Direction.UP)
                .setValue(THICKNESS, DripstoneThickness.TIP)
                .setValue(WATERLOGGED, false));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TIP_DIRECTION, THICKNESS, WATERLOGGED);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        LevelAccessor levelAccessor = blockPlaceContext.getLevel();
        BlockPos blockPos = blockPlaceContext.getClickedPos();
        Direction direction = blockPlaceContext.getNearestLookingVerticalDirection().getOpposite();
        Direction direction2 = calculateTipDirection(levelAccessor, blockPos, direction);
        if (direction2 == null) {
            return null;
        } else {
            boolean bl = !blockPlaceContext.isSecondaryUseActive();
            DripstoneThickness icicleThickness = calculateIceicleThickness(levelAccessor, blockPos, direction2, bl);
            return icicleThickness == null ? null : this.defaultBlockState()
                    .setValue(TIP_DIRECTION, direction2)
                    .setValue(THICKNESS, icicleThickness)
                    .setValue(WATERLOGGED, levelAccessor.getFluidState(blockPos).getType() == Fluids.WATER);
        }
    }

    @Override
    protected @NotNull FluidState getFluidState(BlockState blockState) {
        return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    @Override
    protected @NotNull VoxelShape getOcclusionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return Shapes.empty();
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        DripstoneThickness icicleThickness = blockState.getValue(THICKNESS);
        VoxelShape voxelShape;
        if (icicleThickness == DripstoneThickness.TIP_MERGE) {
            voxelShape = TIP_MERGE_SHAPE;
        } else if (icicleThickness == DripstoneThickness.TIP) {
            if (blockState.getValue(TIP_DIRECTION) == Direction.DOWN) {
                voxelShape = TIP_SHAPE_DOWN;
            } else {
                voxelShape = TIP_SHAPE_UP;
            }
        } else if (icicleThickness == DripstoneThickness.FRUSTUM) {
            voxelShape = FRUSTUM_SHAPE;
        } else if (icicleThickness == DripstoneThickness.MIDDLE) {
            voxelShape = MIDDLE_SHAPE;
        } else {
            voxelShape = BASE_SHAPE;
        }

        Vec3 vec3 = blockState.getOffset(blockGetter, blockPos);
        return voxelShape.move(vec3.x, 0.0, vec3.z);
    }

    protected boolean isCollisionShapeFullBlock(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return false;
    }

    protected float getMaxHorizontalOffset() {
        return 0.125F;
    }

    public void onBrokenAfterFall(Level level, BlockPos blockPos, FallingBlockEntity fallingBlockEntity) {
        if (!fallingBlockEntity.isSilent()) {
            level.playSound(null, blockPos, defaultBlockState().getSoundType().getBreakSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
        }

    }

    @Nullable
    private static Direction calculateTipDirection(LevelReader levelReader, BlockPos blockPos, Direction direction) {
        Direction direction2;
        if (isValidIceiclePlacement(levelReader, blockPos, direction)) {
            direction2 = direction;
        } else {
            if (!isValidIceiclePlacement(levelReader, blockPos, direction.getOpposite())) {
                return null;
            }
            direction2 = direction.getOpposite();
        }
        return direction2;
    }

    protected boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        return isValidIceiclePlacement(levelReader, blockPos, blockState.getValue(TIP_DIRECTION));
    }

    protected @NotNull BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        if (blockState.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }

        if (direction != Direction.UP && direction != Direction.DOWN) {
            return blockState;
        } else {
            Direction direction2 = blockState.getValue(TIP_DIRECTION);
            if (direction2 == Direction.DOWN && levelAccessor.getBlockTicks().hasScheduledTick(blockPos, this)) {
                return blockState;
            } else if (direction == direction2.getOpposite() && !this.canSurvive(blockState, levelAccessor, blockPos)) {
                if (direction2 == Direction.DOWN) {
                    levelAccessor.scheduleTick(blockPos, this, 2);
                } else {
                    levelAccessor.scheduleTick(blockPos, this, 1);
                }

                return blockState;
            } else {
                boolean bl = blockState.getValue(THICKNESS) == DripstoneThickness.TIP_MERGE;
                DripstoneThickness icicleThickness = calculateIceicleThickness(levelAccessor, blockPos, direction2, bl);
                return blockState.setValue(THICKNESS, icicleThickness);
            }
        }
    }

    protected void onProjectileHit(Level level, BlockState blockState, BlockHitResult blockHitResult, Projectile projectile) {
        if (!level.isClientSide) {
            BlockPos blockPos = blockHitResult.getBlockPos();
            if (projectile.mayInteract(level, blockPos) && projectile.mayBreak(level)) {
                level.destroyBlock(blockPos, true);
            }
        }
    }

    public void fallOn(Level level, BlockState blockState, BlockPos blockPos, Entity entity, float f) {
        if (blockState.getValue(TIP_DIRECTION) == Direction.UP && blockState.getValue(THICKNESS) == DripstoneThickness.TIP) {
            entity.causeFallDamage(f + 2.0F, 2.0F, level.damageSources().stalagmite());
        } else {
            super.fallOn(level, blockState, blockPos, entity, f);
        }

    }

    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        if (level.getBiome(blockPos).is(BiomeTags.SPAWNS_WARM_VARIANT_FROGS) && blockState.getValue(TIP_DIRECTION) == Direction.DOWN) {
            float f = randomSource.nextFloat();
            if (!(f > 0.12F) && isTip(blockState, false)) {
                level.addParticle(ParticleTypes.DRIPPING_WATER, (double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.5D, (double) blockPos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    protected void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (isUp(blockState) && !this.canSurvive(blockState, serverLevel, blockPos)) {
            serverLevel.destroyBlock(blockPos, true);
        } else {
            spawnFallingStalactite(blockState, serverLevel, blockPos);
        }

    }

    private static void spawnFallingStalactite(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos) {
        BlockPos.MutableBlockPos mutableBlockPos = blockPos.mutable();

        for(BlockState blockState2 = blockState; isDown(blockState2); blockState2 = serverLevel.getBlockState(mutableBlockPos)) {
            FallingBlockEntity fallingBlockEntity = FallingBlockEntity.fall(serverLevel, mutableBlockPos, blockState2);
            if (isTip(blockState2, true)) {
                int i = Math.max(1 + blockPos.getY() - mutableBlockPos.getY(), 6);
                float f = (float) i;
                fallingBlockEntity.setHurtsEntities(f, 40);
                break;
            }

            mutableBlockPos.move(Direction.DOWN);
        }

    }

    private static DripstoneThickness calculateIceicleThickness(LevelReader levelReader, BlockPos blockPos, Direction direction, boolean bl) {
        Direction direction2 = direction.getOpposite();
        BlockState blockState = levelReader.getBlockState(blockPos.relative(direction));
        if (isIceicleWithDirection(blockState, direction2)) {
            return !bl && blockState.getValue(THICKNESS) != DripstoneThickness.TIP_MERGE ? DripstoneThickness.TIP : DripstoneThickness.TIP_MERGE;
        } else if (!isIceicleWithDirection(blockState, direction)) {
            return DripstoneThickness.TIP;
        } else {
            DripstoneThickness icicleThickness = blockState.getValue(THICKNESS);
            if (icicleThickness != DripstoneThickness.TIP && icicleThickness != DripstoneThickness.TIP_MERGE) {
                BlockState blockState2 = levelReader.getBlockState(blockPos.relative(direction2));
                return !isIceicleWithDirection(blockState2, direction) ? DripstoneThickness.BASE : DripstoneThickness.MIDDLE;
            } else {
                return DripstoneThickness.FRUSTUM;
            }
        }
    }

    private static boolean isDown(BlockState blockState) {
        return isIceicleWithDirection(blockState, Direction.DOWN);
    }

    private static boolean isUp(BlockState blockState) {
        return isIceicleWithDirection(blockState, Direction.UP);
    }

    private static boolean isValidIceiclePlacement(LevelReader levelReader, BlockPos blockPos, Direction direction) {
        BlockPos blockPos2 = blockPos.relative(direction.getOpposite());
        BlockState blockState = levelReader.getBlockState(blockPos2);
        return blockState.isFaceSturdy(levelReader, blockPos2, direction) || isIceicleWithDirection(blockState, direction);
    }

    private static boolean isIceicleWithDirection(BlockState blockState, Direction direction) {
        return blockState.is(SGBlocks.ICICLE) && blockState.getValue(TIP_DIRECTION) == direction;
    }

    private static boolean isTip(BlockState blockState, boolean bl) {
        if (!blockState.is(SGBlocks.ICICLE)) {
            return false;
        } else {
            DripstoneThickness icicleThickness = blockState.getValue(THICKNESS);
            return icicleThickness == DripstoneThickness.TIP || bl && icicleThickness == DripstoneThickness.TIP_MERGE;
        }
    }
}
