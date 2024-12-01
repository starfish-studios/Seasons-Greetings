package com.starfish_studios.seasons_greetings.block;

import com.google.common.collect.Maps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.starfish_studios.seasons_greetings.block.entity.GiftBoxBlockEntity;
import com.starfish_studios.seasons_greetings.registry.SGBlockEntityType;
import com.starfish_studios.seasons_greetings.registry.SGBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GiftBoxBlock extends BaseEntityBlock {
    public static final MapCodec<GiftBoxBlock> CODEC = RecordCodecBuilder.mapCodec((instance) 
            -> instance.group(DyeColor.CODEC.optionalFieldOf("color").forGetter((giftBoxBlock) 
            -> Optional.ofNullable(giftBoxBlock.color)), propertiesCodec()).apply(instance, (optional, properties) 
            -> new GiftBoxBlock(optional.orElse(null), properties)));
    
    private static final Component UNKNOWN_CONTENTS = Component.translatable("container.giftBox.unknownContents");
    private static final float OPEN_AABB_SIZE = 1.0F;
    private static final VoxelShape UP_OPEN_AABB = Block.box(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape DOWN_OPEN_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);
    private static final VoxelShape WES_OPEN_AABB = Block.box(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
    private static final VoxelShape EAST_OPEN_AABB = Block.box(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape NORTH_OPEN_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
    private static final VoxelShape SOUTH_OPEN_AABB = Block.box(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
    private static final Map<Direction, @Nullable Object> OPEN_SHAPE_BY_DIRECTION = Util.make(Maps.newEnumMap(Direction.class), (enumMap) -> {
        enumMap.put(Direction.NORTH, NORTH_OPEN_AABB);
        enumMap.put(Direction.EAST, EAST_OPEN_AABB);
        enumMap.put(Direction.SOUTH, SOUTH_OPEN_AABB);
        enumMap.put(Direction.WEST, WES_OPEN_AABB);
        enumMap.put(Direction.UP, UP_OPEN_AABB);
        enumMap.put(Direction.DOWN, DOWN_OPEN_AABB);
    });
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final ResourceLocation CONTENTS;
    public static final EnumProperty<DyeColor> BOW = EnumProperty.create("bow", DyeColor.class);
    @Nullable
    private final DyeColor color;

    public MapCodec<GiftBoxBlock> codec() {
        return CODEC;
    }

    public GiftBoxBlock(@Nullable DyeColor dyeColor, BlockBehaviour.Properties properties) {
        super(properties);
        this.color = dyeColor;
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(BOW, DyeColor.WHITE)
        );
    }

    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new GiftBoxBlockEntity(this.color, blockPos, blockState);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, SGBlockEntityType.GIFT_BOX, GiftBoxBlockEntity::tick);
    }

//    protected RenderShape getRenderShape(BlockState blockState) {
//        return RenderShape.ENTITYBLOCK_ANIMATED;
//    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }


    protected @NotNull InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);


        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else if (player.isSpectator()) {
            return InteractionResult.CONSUME;
        } else {
            if (itemStack.getItem() instanceof DyeItem dyeItem) {
                DyeColor dyeColor = dyeItem.getDyeColor();
                if (dyeColor != blockState.getValue(BOW)) {
                    level.setBlock(blockPos, blockState.setValue(BOW, dyeColor), 3);
                    if (!player.isCreative()) {
                        itemStack.shrink(1);
                    }
                    return InteractionResult.SUCCESS;
                }
            } else {
                BlockEntity blockEntity = level.getBlockEntity(blockPos);
                if (blockEntity instanceof GiftBoxBlockEntity giftBoxBlockEntity) {
                    if (canOpen(blockState, level, blockPos, giftBoxBlockEntity)) {
                        player.openMenu(giftBoxBlockEntity);
                        player.awardStat(Stats.OPEN_SHULKER_BOX);
                        PiglinAi.angerNearbyPiglins(player, true);
                    }

                    return InteractionResult.CONSUME;
                } else {
                    return InteractionResult.PASS;
                }
            }
        }
        return InteractionResult.PASS;
    }

    private static boolean canOpen(BlockState blockState, Level level, BlockPos blockPos, GiftBoxBlockEntity giftBoxBlockEntity) {
        return true;
    }

    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, BOW);
    }

    public @NotNull BlockState playerWillDestroy(Level level, BlockPos blockPos, BlockState blockState, Player player) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity instanceof GiftBoxBlockEntity giftBoxBlockEntity) {
            if (!level.isClientSide && player.isCreative() && !giftBoxBlockEntity.isEmpty()) {
                ItemStack itemStack = getColoredItemStack(this.getColor());
                itemStack.applyComponents(blockEntity.collectComponents());
                ItemEntity itemEntity = new ItemEntity(level, (double) blockPos.getX() + 0.5, (double) blockPos.getY() + 0.5, (double) blockPos.getZ() + 0.5, itemStack);
                itemEntity.setDefaultPickUpDelay();
                level.addFreshEntity(itemEntity);
            } else {
                giftBoxBlockEntity.unpackLootTable(player);
            }
        }

        return super.playerWillDestroy(level, blockPos, blockState, player);
    }

    protected List<ItemStack> getDrops(BlockState blockState, LootParams.Builder builder) {
        BlockEntity blockEntity = (BlockEntity) builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof GiftBoxBlockEntity giftBoxBlockEntity) {
            builder = builder.withDynamicDrop(CONTENTS, (consumer) -> {
                for (int i = 0; i < giftBoxBlockEntity.getContainerSize(); ++i) {
                    consumer.accept(giftBoxBlockEntity.getItem(i));
                }

            });
        }

        return super.getDrops(blockState, builder);
    }



    protected void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!blockState.is(blockState2.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            super.onRemove(blockState, level, blockPos, blockState2, bl);
            if (blockEntity instanceof GiftBoxBlockEntity) {
                level.updateNeighbourForOutputSignal(blockPos, blockState.getBlock());
            }

        }
    }

    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
        if (itemStack.has(DataComponents.CONTAINER_LOOT)) {
            list.add(UNKNOWN_CONTENTS);
        }

        int i = 0;
        int j = 0;
        Iterator var7 = ((ItemContainerContents) itemStack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY)).nonEmptyItems().iterator();

        while (var7.hasNext()) {
            ItemStack itemStack2 = (ItemStack) var7.next();
            ++j;
            if (i <= 4) {
                ++i;
                list.add(Component.translatable("container.giftBox.itemCount", new Object[]{itemStack2.getHoverName(), itemStack2.getCount()}));
            }
        }

        if (j - i > 0) {
            list.add(Component.translatable("container.giftBox.more", new Object[]{j - i}).withStyle(ChatFormatting.ITALIC));
        }

    }

    protected VoxelShape getBlockSupportShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        BlockEntity blockEntity = blockGetter.getBlockEntity(blockPos);
        if (blockEntity instanceof GiftBoxBlockEntity giftBoxBlockEntity) {
            if (!giftBoxBlockEntity.isClosed()) {
                return (VoxelShape) OPEN_SHAPE_BY_DIRECTION.get(((Direction) blockState.getValue(FACING)).getOpposite());
            }
        }

        return Shapes.block();
    }

    protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return Shapes.block();
//        return blockEntity instanceof GiftBoxBlockEntity ? Shapes.create(((GiftBoxBlockEntity) blockEntity).getBoundingBox(blockState)) : Shapes.block();
    }

    protected boolean propagatesSkylightDown(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return true;
    }

    protected boolean hasAnalogOutputSignal(BlockState blockState) {
        return true;
    }

    protected int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos blockPos) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(blockPos));
    }

    public @NotNull ItemStack getCloneItemStack(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        ItemStack itemStack = super.getCloneItemStack(levelReader, blockPos, blockState);
        levelReader.getBlockEntity(blockPos, SGBlockEntityType.GIFT_BOX).ifPresent((giftBoxBlockEntity) -> {
            giftBoxBlockEntity.saveToItem(itemStack, levelReader.registryAccess());
        });
        return itemStack;
    }



    @Nullable
    public static DyeColor getColorFromItem(Item item) {
        return getColorFromBlock(Block.byItem(item));
    }

    @Nullable
    public static DyeColor getColorFromBlock(Block block) {
        return block instanceof GiftBoxBlock ? ((GiftBoxBlock) block).getColor() : null;
    }

    public static Block getBlockByColor(@Nullable DyeColor dyeColor) {
        if (dyeColor == null) {
            return SGBlocks.RED_GIFT_BOX;
        } else {
            Block var10000;
            switch (dyeColor) {
                // WHITE, LIGHT GRAY, GRAY, BLACK, BROWN, RED, ORANGE, YELLOW, LIME, GREEN, CYAN, LIGHT BLUE, BLUE, PURPLE, MAGENTA, PINK
                case WHITE -> var10000 = SGBlocks.WHITE_GIFT_BOX;
                case LIGHT_GRAY -> var10000 = SGBlocks.LIGHT_GRAY_GIFT_BOX;
                case GRAY -> var10000 = SGBlocks.GRAY_GIFT_BOX;
                case BLACK -> var10000 = SGBlocks.BLACK_GIFT_BOX;
                case BROWN -> var10000 = SGBlocks.BROWN_GIFT_BOX;
                case RED -> var10000 = SGBlocks.RED_GIFT_BOX;
                case ORANGE -> var10000 = SGBlocks.ORANGE_GIFT_BOX;
                case YELLOW -> var10000 = SGBlocks.YELLOW_GIFT_BOX;
                case LIME -> var10000 = SGBlocks.LIME_GIFT_BOX;
                case GREEN -> var10000 = SGBlocks.GREEN_GIFT_BOX;
                case CYAN -> var10000 = SGBlocks.CYAN_GIFT_BOX;
                case LIGHT_BLUE -> var10000 = SGBlocks.LIGHT_BLUE_GIFT_BOX;
                case BLUE -> var10000 = SGBlocks.BLUE_GIFT_BOX;
                case PURPLE -> var10000 = SGBlocks.PURPLE_GIFT_BOX;
                case MAGENTA -> var10000 = SGBlocks.MAGENTA_GIFT_BOX;
                case PINK -> var10000 = SGBlocks.PINK_GIFT_BOX;
                default -> throw new MatchException(null, null);
            }
            return var10000;
        }
    }

    @Nullable
    public DyeColor getColor() {
        return this.color;
    }

    public static ItemStack getColoredItemStack(@Nullable DyeColor dyeColor) {
        return new ItemStack(getBlockByColor(dyeColor));
    }

    protected @NotNull BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
    }

    protected @NotNull BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.getRotation(blockState.getValue(FACING)));
    }

    static {
        CONTENTS = ResourceLocation.withDefaultNamespace("contents");
    }
}
