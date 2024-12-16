package com.starfish_studios.seasons_greetings.common.block.entity;

import java.util.stream.IntStream;

import com.starfish_studios.seasons_greetings.common.block.GiftBoxBlock;
import com.starfish_studios.seasons_greetings.common.inventory.GiftBoxMenu;
import com.starfish_studios.seasons_greetings.registry.SGBlockEntityType;
import com.starfish_studios.seasons_greetings.registry.SGSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GiftBoxBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
    public static final int CONTAINER_SIZE = 27;
    private static final int[] SLOTS = IntStream.range(0, 27).toArray();
    private NonNullList<ItemStack> itemStacks;
    private int openCount;
    private AnimationStatus animationStatus;
    private float progress;
    @Nullable
    private final DyeColor color;

    public GiftBoxBlockEntity(@Nullable DyeColor dyeColor, BlockPos blockPos, BlockState blockState) {
        super(SGBlockEntityType.GIFT_BOX.get(), blockPos, blockState);
        this.itemStacks = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
        this.animationStatus = AnimationStatus.CLOSED;
        this.color = dyeColor;
    }

    public GiftBoxBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(SGBlockEntityType.GIFT_BOX.get(), blockPos, blockState);
        this.itemStacks = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
        this.animationStatus = AnimationStatus.CLOSED;
        this.color = GiftBoxBlock.getColorFromBlock(blockState.getBlock());
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, GiftBoxBlockEntity giftBoxBlockEntity) {
        giftBoxBlockEntity.updateAnimation(level, blockPos, blockState);
    }

    private void updateAnimation(Level level, BlockPos blockPos, BlockState blockState) {
        float progressOld = this.progress;
        switch (this.animationStatus.ordinal()) {
            case 0:
                this.progress = 0.0F;
                break;
            case 1:
                this.progress += 0.1F;
                if (progressOld == 0.0F) {
                    doNeighborUpdates(level, blockPos, blockState);
                }

                if (this.progress >= 1.0F) {
                    this.animationStatus = AnimationStatus.OPENED;
                    this.progress = 1.0F;
                    doNeighborUpdates(level, blockPos, blockState);
                }
                break;
            case 2:
                this.progress = 1.0F;
                break;
            case 3:
                this.progress -= 0.1F;
                if (progressOld == 1.0F) {
                    doNeighborUpdates(level, blockPos, blockState);
                }

                if (this.progress <= 0.0F) {
                    this.animationStatus = AnimationStatus.CLOSED;
                    this.progress = 0.0F;
                    doNeighborUpdates(level, blockPos, blockState);
                }
        }

    }

    public int getContainerSize() {
        return this.itemStacks.size();
    }

    public boolean triggerEvent(int i, int j) {
        if (i == 1) {
            this.openCount = j;
            if (j == 0) {
                this.animationStatus = AnimationStatus.CLOSING;
            }

            if (j == 1) {
                this.animationStatus = AnimationStatus.OPENING;
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
                this.level.playSound(null, this.worldPosition, SGSoundEvents.GIFT_BOX_OPEN.get(), SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
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
                this.level.playSound(null, this.worldPosition, SGSoundEvents.GIFT_BOX_CLOSE.get(), SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
            }
        }

    }

    protected @NotNull Component getDefaultName() {
        return Component.translatable("container.giftBox");
    }



    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.loadAdditional(compoundTag, provider);
        this.loadFromTag(compoundTag, provider);

    }

    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.saveAdditional(compoundTag, provider);
        if (!this.trySaveLootTable(compoundTag)) {
            ContainerHelper.saveAllItems(compoundTag, this.itemStacks, false, provider);
        }
    }

    public void loadFromTag(CompoundTag compoundTag, HolderLookup.Provider provider) {
        this.itemStacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(compoundTag) && compoundTag.contains("Items", 9)) {
            ContainerHelper.loadAllItems(compoundTag, this.itemStacks, provider);
        }

    }

    protected @NotNull NonNullList<ItemStack> getItems() {
        return this.itemStacks;
    }

    protected void setItems(NonNullList<ItemStack> nonNullList) {
        this.itemStacks = nonNullList;
    }

    public int @NotNull [] getSlotsForFace(Direction direction) {
        return SLOTS;
    }

    public boolean canPlaceItemThroughFace(int i, ItemStack itemStack, @Nullable Direction direction) {
        return !(Block.byItem(itemStack.getItem()) instanceof GiftBoxBlock);
    }

    public boolean canTakeItemThroughFace(int i, ItemStack itemStack, Direction direction) {
        return true;
    }

    @Nullable
    public DyeColor getColor() {
        return this.color;
    }

    protected @NotNull AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new GiftBoxMenu(i, inventory, this);
    }

    public boolean isClosed() {
        return this.animationStatus == AnimationStatus.CLOSED;
    }

    public enum AnimationStatus {
        CLOSED,
        OPENING,
        OPENED,
        CLOSING;

        AnimationStatus() {
        }
    }
}
