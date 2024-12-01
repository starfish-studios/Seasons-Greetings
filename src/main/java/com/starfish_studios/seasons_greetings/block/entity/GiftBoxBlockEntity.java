//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.starfish_studios.seasons_greetings.block.entity;

import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

import com.starfish_studios.seasons_greetings.block.GiftBoxBlock;
import com.starfish_studios.seasons_greetings.inventory.GiftBoxMenu;
import com.starfish_studios.seasons_greetings.registry.SGBlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GiftBoxBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
    public static final int COLUMNS = 9;
    public static final int ROWS = 3;
    public static final int CONTAINER_SIZE = 27;
    public static final int EVENT_SET_OPEN_COUNT = 1;
    public static final int OPENING_TICK_LENGTH = 10;
    public static final float MAX_LID_HEIGHT = 0.5F;
    public static final float MAX_LID_ROTATION = 270.0F;
    private static final int[] SLOTS = IntStream.range(0, 27).toArray();
    private NonNullList<ItemStack> itemStacks;
    private int openCount;
    private AnimationStatus animationStatus;
    private float progress;
    private float progressOld;
    @Nullable
    private final DyeColor color;

    public GiftBoxBlockEntity(@Nullable DyeColor dyeColor, BlockPos blockPos, BlockState blockState) {
        super(SGBlockEntityType.GIFT_BOX, blockPos, blockState);
        this.itemStacks = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
        this.animationStatus = GiftBoxBlockEntity.AnimationStatus.CLOSED;
        this.color = dyeColor;
    }

    public GiftBoxBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(SGBlockEntityType.GIFT_BOX, blockPos, blockState);
        this.itemStacks = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
        this.animationStatus = GiftBoxBlockEntity.AnimationStatus.CLOSED;
        this.color = GiftBoxBlock.getColorFromBlock(blockState.getBlock());
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, GiftBoxBlockEntity giftBoxBlockEntity) {
        giftBoxBlockEntity.updateAnimation(level, blockPos, blockState);
    }

    private void updateAnimation(Level level, BlockPos blockPos, BlockState blockState) {
        this.progressOld = this.progress;
        switch (this.animationStatus.ordinal()) {
            case 0:
                this.progress = 0.0F;
                break;
            case 1:
                this.progress += 0.1F;
                if (this.progressOld == 0.0F) {
                    doNeighborUpdates(level, blockPos, blockState);
                }

                if (this.progress >= 1.0F) {
                    this.animationStatus = GiftBoxBlockEntity.AnimationStatus.OPENED;
                    this.progress = 1.0F;
                    doNeighborUpdates(level, blockPos, blockState);
                }

//                this.moveCollidedEntities(level, blockPos, blockState);
                break;
            case 2:
                this.progress = 1.0F;
                break;
            case 3:
                this.progress -= 0.1F;
                if (this.progressOld == 1.0F) {
                    doNeighborUpdates(level, blockPos, blockState);
                }

                if (this.progress <= 0.0F) {
                    this.animationStatus = GiftBoxBlockEntity.AnimationStatus.CLOSED;
                    this.progress = 0.0F;
                    doNeighborUpdates(level, blockPos, blockState);
                }
        }

    }

    public AnimationStatus getAnimationStatus() {
        return this.animationStatus;
    }

//    public AABB getBoundingBox(BlockState blockState) {
//        return Shulker.getProgressAabb(1.0F, blockState.getValue(GiftBoxBlock.FACING), 0.5F * this.getProgress(1.0F));
//    }

//    private void moveCollidedEntities(Level level, BlockPos blockPos, BlockState blockState) {
//        if (blockState.getBlock() instanceof GiftBoxBlock) {
//            Direction direction = blockState.getValue(GiftBoxBlock.FACING);
//            AABB aABB = Shulker.getProgressDeltaAabb(1.0F, direction, this.progressOld, this.progress).move(blockPos);
//            List<Entity> list = level.getEntities((Entity)null, aABB);
//            if (!list.isEmpty()) {
//                Iterator var7 = list.iterator();
//
//                while(var7.hasNext()) {
//                    Entity entity = (Entity)var7.next();
//                    if (entity.getPistonPushReaction() != PushReaction.IGNORE) {
//                        entity.move(MoverType.SHULKER_BOX, new Vec3((aABB.getXsize() + 0.01) * (double)direction.getStepX(), (aABB.getYsize() + 0.01) * (double)direction.getStepY(), (aABB.getZsize() + 0.01) * (double)direction.getStepZ()));
//                    }
//                }
//
//            }
//        }
//    }

    public int getContainerSize() {
        return this.itemStacks.size();
    }

    public boolean triggerEvent(int i, int j) {
        if (i == 1) {
            this.openCount = j;
            if (j == 0) {
                this.animationStatus = GiftBoxBlockEntity.AnimationStatus.CLOSING;
            }

            if (j == 1) {
                this.animationStatus = GiftBoxBlockEntity.AnimationStatus.OPENING;
            }

            return true;
        } else {
            return super.triggerEvent(i, j);
        }
    }

    private static void doNeighborUpdates(Level level, BlockPos blockPos, BlockState blockState) {
        blockState.updateNeighbourShapes(level, blockPos, 3);
        level.updateNeighborsAt(blockPos, blockState.getBlock());
    }

    public void startOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            if (this.openCount < 0) {
                this.openCount = 0;
            }

            ++this.openCount;
            assert this.level != null;
            this.level.blockEvent(this.worldPosition, this.getBlockState().getBlock(), 1, this.openCount);
            if (this.openCount == 1) {
                this.level.gameEvent(player, GameEvent.CONTAINER_OPEN, this.worldPosition);
                this.level.playSound(null, this.worldPosition, SoundEvents.SHULKER_BOX_OPEN, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
            }
        }

    }

    public void stopOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            --this.openCount;
            assert this.level != null;
            this.level.blockEvent(this.worldPosition, this.getBlockState().getBlock(), 1, this.openCount);
            if (this.openCount <= 0) {
                this.level.gameEvent(player, GameEvent.CONTAINER_CLOSE, this.worldPosition);
                this.level.playSound(null, this.worldPosition, SoundEvents.SHULKER_BOX_CLOSE, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
            }
        }

    }

    protected @NotNull Component getDefaultName() {
        return Component.translatable("container.giftBox");
    }



    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.loadAdditional(compoundTag, provider);
        this.loadFromTag(compoundTag, provider);

        if (compoundTag.contains("bow")) {
            assert this.level != null;
            this.level.setBlock(this.worldPosition, this.getBlockState()
                    .setValue(GiftBoxBlock.BOW, DyeColor.byName(compoundTag.getString("bow"), DyeColor.WHITE)), 3);
        }
    }


    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.saveAdditional(compoundTag, provider);
        if (!this.trySaveLootTable(compoundTag)) {
            ContainerHelper.saveAllItems(compoundTag, this.itemStacks, false, provider);
        }

        if (this.getBlockState().getBlock().defaultBlockState().hasProperty(GiftBoxBlock.BOW)) {
            compoundTag.putString("bow", this.getBlockState().getValue(GiftBoxBlock.BOW).getName());
        }

    }

    public void loadFromTag(CompoundTag compoundTag, HolderLookup.Provider provider) {
        this.itemStacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(compoundTag) && compoundTag.contains("Items", 9)) {
            ContainerHelper.loadAllItems(compoundTag, this.itemStacks, provider);
        }

    }

    protected NonNullList<ItemStack> getItems() {
        return this.itemStacks;
    }

    protected void setItems(NonNullList<ItemStack> nonNullList) {
        this.itemStacks = nonNullList;
    }

    public int[] getSlotsForFace(Direction direction) {
        return SLOTS;
    }

    public boolean canPlaceItemThroughFace(int i, ItemStack itemStack, @Nullable Direction direction) {
        return !(Block.byItem(itemStack.getItem()) instanceof GiftBoxBlock);
    }

    public boolean canTakeItemThroughFace(int i, ItemStack itemStack, Direction direction) {
        return true;
    }

    public float getProgress(float f) {
        return Mth.lerp(f, this.progressOld, this.progress);
    }

    @Nullable
    public DyeColor getColor() {
        return this.color;
    }

    protected @NotNull AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new GiftBoxMenu(i, inventory, this);
    }

    public boolean isClosed() {
        return this.animationStatus == GiftBoxBlockEntity.AnimationStatus.CLOSED;
    }

    public static enum AnimationStatus {
        CLOSED,
        OPENING,
        OPENED,
        CLOSING;

        private AnimationStatus() {
        }
    }
}
