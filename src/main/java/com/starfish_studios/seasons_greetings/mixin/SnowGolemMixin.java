package com.starfish_studios.seasons_greetings.mixin;

import com.google.common.collect.Maps;
import com.starfish_studios.seasons_greetings.SGConfig;
import com.starfish_studios.seasons_greetings.registry.SGTags;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WoolCarpetBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(SnowGolem.class)
public class SnowGolemMixin extends AbstractGolem {
    @Unique
    private static final EntityDataAccessor<Boolean> STATIONARY = SynchedEntityData.defineId(SnowGolemMixin.class, EntityDataSerializers.BOOLEAN);
    @Unique
    private static final EntityDataAccessor<Boolean> MELTED = SynchedEntityData.defineId(SnowGolemMixin.class, EntityDataSerializers.BOOLEAN);


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

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void sg$defineSynchedData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(STATIONARY, false);
        builder.define(MELTED, false);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void sg$addAdditionalSaveData(CompoundTag compoundTag, CallbackInfo ci) {
        compoundTag.putBoolean("Stationary", this.isStationary());
        compoundTag.putBoolean("Melted", this.isMelted());
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void sg$readAdditionalSaveData(CompoundTag compoundTag, CallbackInfo ci) {
        this.setStationary(compoundTag.getBoolean("Stationary"));
        this.setMelted(compoundTag.getBoolean("Melted"));
    }

    @Inject(method = "aiStep", at = @At("HEAD"), cancellable = true)
    private void sg$aiStep(CallbackInfo ci) {
        super.aiStep();
        if (!this.level().isClientSide) {
            if (this.isStationary()) {
                this.navigation.stop();
                this.setTarget(null);
            }

            if (this.level().getBiome(this.blockPosition()).is(BiomeTags.SNOW_GOLEM_MELTS)) {
                this.hurt(this.damageSources().onFire(), 1.0F);
            }

            if (!this.isMelted()){
                if (!this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                    return;
                }

                BlockState blockState = Blocks.SNOW.defaultBlockState();

                for(int i = 0; i < 4; ++i) {
                    int j = Mth.floor(this.getX() + (double)((float)(i % 2 * 2 - 1) * 0.25F));
                    int k = Mth.floor(this.getY());
                    int l = Mth.floor(this.getZ() + (double)((float)(i / 2 % 2 * 2 - 1) * 0.25F));
                    BlockPos blockPos = new BlockPos(j, k, l);
                    if (this.level().getBlockState(blockPos).isAir() && blockState.canSurvive(this.level(), blockPos)) {
                        this.level().setBlockAndUpdate(blockPos, blockState);
                        this.level().gameEvent(GameEvent.BLOCK_PLACE, blockPos, GameEvent.Context.of(this, blockState));
                    }
                }
            }
        }
        ci.cancel();
    }

    @Inject(method = "registerGoals", at = @At("HEAD"), cancellable = true)
    private void sg$registerGoals(CallbackInfo ci) {
        this.goalSelector.addGoal(1, new RangedAttackGoal((RangedAttackMob) this, 1.25, 20, 10.0F) {
            public boolean canUse() {
                return super.canUse() && !SnowGolemMixin.this.isStationary();
            }
        });
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0, 1.0000001E-5F) {
            public boolean canUse() {
                return super.canUse() && !SnowGolemMixin.this.isStationary();
            }
        });
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this) {
            public boolean canUse() {
                return super.canUse() && !SnowGolemMixin.this.isStationary();
            }
        });
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Mob.class, 10, !this.isStationary(), false, (livingEntity) -> livingEntity instanceof Enemy));
        ci.cancel();
    }


    @Unique
    public boolean isMelted() {
        return this.entityData.get(MELTED);
    }

    @Unique
    public void setMelted(boolean value) {
        this.entityData.set(MELTED, value);
    }

    @Unique
    public boolean isStationary() {
        return this.entityData.get(STATIONARY);
    }

    @Unique
    public void setStationary(boolean stationary) {
        this.entityData.set(STATIONARY, stationary);
    }


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
            player.swing(interactionHand);
            if (!this.level().isClientSide && !player.isCreative()) {
                itemStack.hurtAndBreak(1, player, getSlotForHand(interactionHand));
            }
        }
        this.sg$playSound(SoundEvents.SNOW_GOLEM_SHEAR);

    }

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    public void mobInteract(Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        InteractionResult interactionResult = super.mobInteract(player, interactionHand);
        SnowGolem snowGolem = (SnowGolem) (Object) this;
        DyeColor dyeColor = getDyeColor(snowGolem.getItemBySlot(EquipmentSlot.BODY));

        if (!itemStack.isEmpty()) {
            if (SGConfig.meltedSnowGolems) {
                if (itemStack.is(Items.SNOWBALL) && this.isMelted()) {
                    this.setMelted(false);
                    this.sg$playSound(SoundEvents.SNOW_PLACE);
                    if (!this.isMelted()) {
                        player.displayClientMessage(Component.literal("Snow Golem now leaves Snow"), true);
                    }
                    cir.setReturnValue(InteractionResult.SUCCESS);
                    return;
                }

                if (itemStack.is(Items.FLINT_AND_STEEL) && !this.isMelted()) {
                    this.setMelted(true);
                    this.sg$playSound(SoundEvents.FLINTANDSTEEL_USE);
                    if (!player.isCreative()) {
                        itemStack.hurtAndBreak(1, player, getSlotForHand(interactionHand));
                    }
                    if (this.isMelted()) {
                        player.displayClientMessage(Component.literal("Snow Golem no longer leaves Snow"), true);
                    }
                    cir.setReturnValue(InteractionResult.SUCCESS);
                    return;
                }
            }

            if (SGConfig.snowGolemsEquippable) {
                // Body / Scarf Accessory Item Placement (BODY)
                if (itemStack.is(ItemTags.WOOL_CARPETS) && snowGolem.getItemBySlot(EquipmentSlot.BODY).isEmpty()) {
                    snowGolem.setItemSlot(EquipmentSlot.BODY, itemStack.copyWithCount(1));
                    playSoundAndShrinkItem(itemStack);
                    cir.setReturnValue(InteractionResult.SUCCESS);
                    return;
                }

                // Head Accessory Item Placement (LEGS)
                if (itemStack.is(SGTags.SGItemTags.SNOW_GOLEM_NOSES) && !snowGolem.hasPumpkin() && snowGolem.getItemBySlot(EquipmentSlot.LEGS).isEmpty()) {
                    snowGolem.setItemSlot(EquipmentSlot.LEGS, new ItemStack(itemStack.getItem()));
                    playSoundAndShrinkItem(itemStack);
                    cir.setReturnValue(InteractionResult.SUCCESS);
                    return;
                }

                // Pumpkin Re-Placement + Head Items (HEAD)
                if ((!snowGolem.hasPumpkin() && snowGolem.getItemBySlot(EquipmentSlot.HEAD).isEmpty() && itemStack.getItem() instanceof BlockItem)
                        || (!snowGolem.hasPumpkin() && snowGolem.getItemBySlot(EquipmentSlot.HEAD).isEmpty()
                        && itemStack.getItem() instanceof Item && SGConfig.snowGolemItemHats)) {

                    if ((itemStack.getItem() instanceof ShearsItem) || itemStack.is(SGTags.SGItemTags.SNOW_GOLEM_NOSES)) {
                        cir.setReturnValue(InteractionResult.PASS);
                        return;
                    } else if (itemStack.is(Items.CARVED_PUMPKIN)) {
                        snowGolem.setPumpkin(true);
                        playSoundAndShrinkItem(itemStack);
                        cir.setReturnValue(InteractionResult.SUCCESS);
                        return;
                    } else {
                        snowGolem.setItemSlot(EquipmentSlot.HEAD, itemStack.copyWithCount(1));
                        playSoundAndShrinkItem(itemStack);
                        cir.setReturnValue(InteractionResult.SUCCESS);
                        return;
                    }
                }

                // Shearing - Head -> Face -> Scarf
                if (itemStack.getItem() instanceof ShearsItem) {
                    if (snowGolem.hasPumpkin()) {
                        snowGolem.setPumpkin(false);
                        ItemEntity itemEntity = new ItemEntity(this.level(), this.getX(), this.getY() + 1.5, this.getZ(), new ItemStack(Items.CARVED_PUMPKIN));
                        this.level().addFreshEntity(itemEntity);
                        this.shearSnowGolem(player, interactionHand);
                        cir.setReturnValue(InteractionResult.SUCCESS);
                        return;
                    } else if (!snowGolem.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
                        ItemEntity itemEntity = new ItemEntity(this.level(), this.getX(), this.getY() + 1.5, this.getZ(), snowGolem.getItemBySlot(EquipmentSlot.HEAD));
                        this.level().addFreshEntity(itemEntity);
                        snowGolem.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
                        this.shearSnowGolem(player, interactionHand);
                        cir.setReturnValue(InteractionResult.SUCCESS);
                        return;
                    } else if (!snowGolem.getItemBySlot(EquipmentSlot.LEGS).isEmpty()) {
                        ItemEntity itemEntity = new ItemEntity(this.level(), this.getX(), this.getY() + 1.5, this.getZ(), snowGolem.getItemBySlot(EquipmentSlot.LEGS));
                        this.level().addFreshEntity(itemEntity);
                        snowGolem.setItemSlot(EquipmentSlot.LEGS, ItemStack.EMPTY);
                        this.shearSnowGolem(player, interactionHand);
                        cir.setReturnValue(InteractionResult.SUCCESS);
                        return;
                    } else if (dyeColor != null) {
                        snowGolem.setItemSlot(EquipmentSlot.BODY, ItemStack.EMPTY);
                        ItemEntity itemEntity = new ItemEntity(this.level(), this.getX(), this.getY() + 1, this.getZ(), new ItemStack(ITEM_BY_DYE.get(dyeColor)));
                        this.level().addFreshEntity(itemEntity);
                        cir.setReturnValue(InteractionResult.SUCCESS);
                        return;
                    }

                    if (InteractionResult.SUCCESS.equals(cir.getReturnValue())) {
                        this.shearSnowGolem(player, interactionHand);
                    }
                }
            }
        }

        if (SGConfig.stationarySnowGolems) {
            this.setStationary(!this.isStationary());
            if (this.isStationary()) {
                player.displayClientMessage(Component.literal("Snow Golem is now stationary"), true);
            } else {
                player.displayClientMessage(Component.literal("Snow Golem is no longer stationary"), true);
            }
            if (this.isStationary()) {
                this.navigation.stop();
                this.setTarget(null);
            }
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }

}
