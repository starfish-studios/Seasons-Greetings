package com.starfish_studios.seasons_greetings.block;

import com.starfish_studios.seasons_greetings.registry.SGItems;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class WreathBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<WreathGarland> GARLAND = EnumProperty.create("garland", WreathGarland.class);
    public static final EnumProperty<WreathBowColors> BOW = EnumProperty.create("bow", WreathBowColors.class);
    public static final BooleanProperty BELL = BooleanProperty.create("bell");

    public static final VoxelShape NORTH_AABB = Block.box(2, 2, 12, 14, 14, 16);
    public static final VoxelShape SOUTH_AABB = Block.box(2, 2, 0, 14, 14, 4);
    public static final VoxelShape EAST_AABB = Block.box(0, 2, 2, 4, 14, 14);
    public static final VoxelShape WEST_AABB = Block.box(12, 2, 2, 16, 14, 14);

    public WreathBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(FACING, Direction.NORTH)
                .setValue(GARLAND, WreathGarland.EMPTY)
                .setValue(BOW, WreathBowColors.EMPTY)
                .setValue(BELL, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateDefinition) {
        stateDefinition.add(FACING, GARLAND, BOW, BELL);
    }

    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return switch (blockState.getValue(FACING)) {
            case SOUTH -> SOUTH_AABB;
            case EAST -> EAST_AABB;
            case WEST -> WEST_AABB;
            default -> NORTH_AABB;
        };
    }

    public static double dyeHeight() {
        return 1;
    }

    public enum WreathGarland implements StringRepresentable {
        EMPTY("empty"),
        MULTICOLOR_LIGHTS("multicolor_lights"),
        GLOW_BERRIES("glow_berries"),
        SWEET_BERRIES("sweet_berries");

        private final String name;

        WreathGarland(String name) {
            this.name = name;
        }

        public @NotNull String getSerializedName() {
            return this.name;
        }
    }

    public enum WreathBowColors implements StringRepresentable {
        EMPTY("empty"),
        WHITE("white"),
        ORANGE("orange"),
        MAGENTA("magenta"),
        LIGHT_BLUE("light_blue"),
        YELLOW("yellow"),
        LIME("lime"),
        PINK("pink"),
        GRAY("gray"),
        LIGHT_GRAY("light_gray"),
        CYAN("cyan"),
        PURPLE("purple"),
        BLUE("blue"),
        BROWN("brown"),
        GREEN("green"),
        RED("red"),
        BLACK("black");

        private final String name;

        WreathBowColors(String string2) {
            this.name = string2;
        }

        public String toString() {
            return this.getSerializedName();
        }

        public @NotNull String getSerializedName() {
            return this.name;
        }
    }
}
