package com.starfish_studios.seasons_greetings.mixin;

import com.google.common.collect.Maps;
import com.starfish_studios.seasons_greetings.item.GiftBoxItem;
import com.starfish_studios.seasons_greetings.registry.SGItems;
import com.starfish_studios.seasons_greetings.registry.SGTags;
import net.minecraft.Util;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WoolCarpetBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(SnowGolem.class)
public class SnowGolemMixin extends AbstractGolem {
    protected SnowGolemMixin(EntityType<? extends AbstractGolem> entityType, Level level) {
        super(entityType, level);
    }

    @Unique
    private static final Map<DyeColor, ItemLike> ITEM_BY_DYE = Util.make(Maps.newEnumMap(DyeColor.class), (enumMap) -> {
        enumMap.put(DyeColor.WHITE, Blocks.WHITE_CARPET);
        enumMap.put(DyeColor.ORANGE, Blocks.ORANGE_CARPET);
        enumMap.put(DyeColor.MAGENTA, Blocks.MAGENTA_CARPET);
        enumMap.put(DyeColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_CARPET);
        enumMap.put(DyeColor.YELLOW, Blocks.YELLOW_CARPET);
        enumMap.put(DyeColor.LIME, Blocks.LIME_CARPET);
        enumMap.put(DyeColor.PINK, Blocks.PINK_CARPET);
        enumMap.put(DyeColor.GRAY, Blocks.GRAY_CARPET);
        enumMap.put(DyeColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_CARPET);
        enumMap.put(DyeColor.CYAN, Blocks.CYAN_CARPET);
        enumMap.put(DyeColor.PURPLE, Blocks.PURPLE_CARPET);
        enumMap.put(DyeColor.BLUE, Blocks.BLUE_CARPET);
        enumMap.put(DyeColor.BROWN, Blocks.BROWN_CARPET);
        enumMap.put(DyeColor.GREEN, Blocks.GREEN_CARPET);
        enumMap.put(DyeColor.RED, Blocks.RED_CARPET);
        enumMap.put(DyeColor.BLACK, Blocks.BLACK_CARPET);
    });


    @Unique
    @Nullable
    private static DyeColor getDyeColor(ItemStack itemStack) {
        Block block = Block.byItem(itemStack.getItem());
        return block instanceof WoolCarpetBlock ? ((WoolCarpetBlock)block).getColor() : null;
    }

    @Unique
    public void sg$playSound(SoundEvent soundEvent) {
        float f = 1.0F;
        float g = 1.0F;
        this.level().playSound(null, this, soundEvent, this.getSoundSource(), f, g);
    }

    protected void dropCustomDeathLoot(ServerLevel serverLevel, DamageSource damageSource, boolean bl) {
        super.dropCustomDeathLoot(serverLevel, damageSource, bl);
        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            ItemStack itemStack = this.getItemBySlot(equipmentSlot);
            if (!itemStack.isEmpty()) {
                this.spawnAtLocation(itemStack);
                this.setItemSlot(equipmentSlot, ItemStack.EMPTY);
            }
        }
    }

    @Unique
    protected void playSoundAndShrinkItem(ItemStack itemStack) {
        this.sg$playSound(SoundEvents.SNOW_PLACE);
        itemStack.shrink(1);
    }

    @Unique
    protected void shearSnowGolem(Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (itemStack.getItem() instanceof ShearsItem) {
            this.gameEvent(GameEvent.SHEAR, player);
            if (!this.level().isClientSide && !player.isCreative()) {
                itemStack.hurtAndBreak(1, player, getSlotForHand(interactionHand));
            }
        }
        this.sg$playSound(SoundEvents.SNOW_GOLEM_SHEAR);

    }

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    public void mobInteract(Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        SnowGolem snowGolem = (SnowGolem) (Object) this;
        DyeColor dyeColor = getDyeColor(snowGolem.getItemBySlot(EquipmentSlot.BODY));
        if (!itemStack.isEmpty()) {

            // Body / Scarf Accessory Item Placement (BODY)
            if (itemStack.is(ItemTags.WOOL_CARPETS) && snowGolem.getItemBySlot(EquipmentSlot.BODY).isEmpty()) {
                snowGolem.setItemSlot(EquipmentSlot.BODY, itemStack.copyWithCount(1));
                playSoundAndShrinkItem(itemStack);
                cir.setReturnValue(InteractionResult.SUCCESS);
            }

            // Head Accessory Item Placement (LEGS)
            if (itemStack.is(SGTags.SGItemTags.SNOW_GOLEM_NOSES) && !snowGolem.hasPumpkin() && snowGolem.getItemBySlot(EquipmentSlot.LEGS).isEmpty()) {
                snowGolem.setItemSlot(EquipmentSlot.LEGS, new ItemStack(itemStack.getItem()));
                playSoundAndShrinkItem(itemStack);
                cir.setReturnValue(InteractionResult.SUCCESS);
            }
            // Pumpkin Re-Placement + Head Items (HEAD)
            else if (!snowGolem.hasPumpkin() && snowGolem.getItemBySlot(EquipmentSlot.HEAD).isEmpty() && itemStack.getItem() instanceof BlockItem) {
                if ((itemStack.getItem() instanceof ShearsItem) || itemStack.is(SGTags.SGItemTags.SNOW_GOLEM_NOSES)) {
                    cir.setReturnValue(InteractionResult.PASS);
                } else if (itemStack.is(Items.CARVED_PUMPKIN)) {
                    snowGolem.setPumpkin(true);
                    playSoundAndShrinkItem(itemStack);
                    cir.setReturnValue(InteractionResult.SUCCESS);
                } else {
                    snowGolem.setItemSlot(EquipmentSlot.HEAD, itemStack.copyWithCount(1));
                    playSoundAndShrinkItem(itemStack);
                    cir.setReturnValue(InteractionResult.SUCCESS);
                }
            }

            // Shearing - Head -> Face -> Scarf
            else if (itemStack.getItem() instanceof ShearsItem) {
                if (snowGolem.hasPumpkin()) {
                    snowGolem.setPumpkin(false);
                    this.shearSnowGolem(player, interactionHand);
                    cir.setReturnValue(InteractionResult.SUCCESS);
                } else if (!snowGolem.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
                    ItemEntity itemEntity = new ItemEntity(this.level(), this.getX(), this.getY() + 1.5, this.getZ(), snowGolem.getItemBySlot(EquipmentSlot.HEAD));
                    this.level().addFreshEntity(itemEntity);
                    snowGolem.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
                    cir.setReturnValue(InteractionResult.SUCCESS);
                } else if (!snowGolem.getItemBySlot(EquipmentSlot.LEGS).isEmpty()) {
                    ItemEntity itemEntity = new ItemEntity(this.level(), this.getX(), this.getY() + 1.5, this.getZ(), snowGolem.getItemBySlot(EquipmentSlot.LEGS));
                    this.level().addFreshEntity(itemEntity);
                    snowGolem.setItemSlot(EquipmentSlot.LEGS, ItemStack.EMPTY);
                    cir.setReturnValue(InteractionResult.SUCCESS);
                } else if (dyeColor != null) {
                    snowGolem.setItemSlot(EquipmentSlot.BODY, ItemStack.EMPTY);
                    ItemEntity itemEntity = new ItemEntity(this.level(), this.getX(), this.getY() + 1, this.getZ(), new ItemStack(ITEM_BY_DYE.get(dyeColor)));
                    this.level().addFreshEntity(itemEntity);
                    cir.setReturnValue(InteractionResult.SUCCESS);
                }
                if (InteractionResult.SUCCESS.equals(cir.getReturnValue())) {
                    this.shearSnowGolem(player, interactionHand);
                }
            }
        }
    }
}
