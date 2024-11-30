package com.starfish_studios.seasons_greetings.mixin;

import com.google.common.collect.Maps;
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
    public void snowSound(SoundEvent soundEvent) {
        float f = 1.0F;
        float g = 1.0F;
        this.level().playSound(null, this, soundEvent, this.getSoundSource(), f, g);
    }

    // This makes them drop all of their equipment.
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

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    public void mobInteract(Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        SnowGolem snowGolem = (SnowGolem)(Object)this;
        DyeColor dyeColor = getDyeColor(snowGolem.getItemBySlot(EquipmentSlot.BODY));


        // Head Item Placement
        if (itemStack.is(SGTags.SeasonsGreetingsItemTags.SNOW_GOLEM_NOSES) && !snowGolem.hasPumpkin() && snowGolem.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
            snowSound(SoundEvents.SNOW_PLACE);
            snowGolem.setItemSlot(EquipmentSlot.HEAD, new ItemStack(itemStack.getItem()));
            cir.setReturnValue(InteractionResult.SUCCESS);
        }

        // Pumpkin Re-Placement
        if (itemStack.is(Items.CARVED_PUMPKIN) && !snowGolem.hasPumpkin() && snowGolem.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
            snowSound(SoundEvents.SNOW_PLACE);
            snowGolem.setPumpkin(true);
            cir.setReturnValue(InteractionResult.SUCCESS);
        }

        // Shearing
        if (itemStack.getItem() instanceof ShearsItem) {
            if (dyeColor != null) {
                snowGolem.setItemSlot(EquipmentSlot.BODY, ItemStack.EMPTY);
                this.level().playSound(null, this, SoundEvents.SNOW_GOLEM_SHEAR, this.getSoundSource(), 1.0F, 1.0F);
                ItemEntity itemEntity = new ItemEntity(this.level(), this.getX(), this.getY() + 1, this.getZ(), new ItemStack(ITEM_BY_DYE.get(dyeColor)));
                this.level().addFreshEntity(itemEntity);
                cir.setReturnValue(InteractionResult.SUCCESS);
            } else if (!snowGolem.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
                this.level().playSound(null, this, SoundEvents.SNOW_GOLEM_SHEAR, this.getSoundSource(), 1.0F, 1.0F);
                ItemEntity itemEntity = new ItemEntity(this.level(), this.getX(), this.getY() + 1.5, this.getZ(), snowGolem.getItemBySlot(EquipmentSlot.HEAD));
                this.level().addFreshEntity(itemEntity);
                snowGolem.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
                cir.setReturnValue(InteractionResult.SUCCESS);
            }
        }

        if (itemStack.is(ItemTags.WOOL_CARPETS) && snowGolem.getItemBySlot(EquipmentSlot.BODY).isEmpty()) {
            snowGolem.setItemSlot(EquipmentSlot.BODY, new ItemStack(itemStack.getItem()));
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }
}
