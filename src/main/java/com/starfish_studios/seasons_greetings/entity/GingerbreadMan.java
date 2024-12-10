package com.starfish_studios.seasons_greetings.entity;

import com.starfish_studios.seasons_greetings.registry.SGItems;
import com.starfish_studios.seasons_greetings.registry.SGSoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.PathType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Objects;
import java.util.UUID;

public class GingerbreadMan extends TamableAnimal implements GeoEntity, NeutralMob {

    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(GingerbreadMan.class, EntityDataSerializers.INT);
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    @Nullable
    private UUID persistentAngerTarget;
    private static final EntityDataAccessor<Boolean> CANT_CATCH_ME =
            SynchedEntityData.defineId(GingerbreadMan.class, EntityDataSerializers.BOOLEAN);

    protected static final RawAnimation IDLE = RawAnimation.begin().thenLoop("animation.gingerbread_man.idle");
    protected static final RawAnimation WALK = RawAnimation.begin().thenLoop("animation.gingerbread_man.walk");
    protected static final RawAnimation WALK_ITEM = RawAnimation.begin().thenLoop("animation.gingerbread_man.walk_item");
    protected static final RawAnimation ATTACK = RawAnimation.begin().then("animation.gingerbread_man.attack", Animation.LoopType.PLAY_ONCE);
    protected static final RawAnimation ATTACK2 = RawAnimation.begin().thenPlay("animation.gingerbread_man.attack2");
    protected static final RawAnimation DIE = RawAnimation.begin().thenPlayAndHold("animation.gingerbread_man.die");
    protected static final RawAnimation SIT = RawAnimation.begin().thenLoop("animation.gingerbread_man.sit");

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    public GingerbreadMan(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
//        this.setCanPickUpLoot(true);
        this.setTame(false, false);
        this.setPathfindingMalus(PathType.POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(PathType.DANGER_POWDER_SNOW, -1.0F);
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_REMAINING_ANGER_TIME, 0);
        builder.define(CANT_CATCH_ME, false);
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        if (this.persistentAngerTarget != null) {
            compoundTag.putUUID("AngerTarget", this.persistentAngerTarget);
        }
        compoundTag.putInt("AngerTime", this.getRemainingPersistentAngerTime());
        compoundTag.putBoolean("CantCatchMe", this.isCantCatchMe(false));
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.persistentAngerTarget = compoundTag.hasUUID("AngerTarget") ? compoundTag.getUUID("AngerTarget") : null;
        if (compoundTag.contains("AngerTime")) {
            this.setRemainingPersistentAngerTime(compoundTag.getInt("AngerTime"));
        }
        this.isCantCatchMe(compoundTag.getBoolean("CantCatchMe"));
    }

    public boolean isCantCatchMe(boolean truefalse) {
        return this.entityData.get(CANT_CATCH_ME);
    }

    public void setCantCatchMe(boolean value) {
        this.entityData.set(CANT_CATCH_ME, value);
    }

    private void removeInteractionItem(Player player, ItemStack itemStack) {
        itemStack.consume(1, player);
    }

    @Override
    public void aiStep() {
        if (!this.level().isClientSide) {
            if (this.isOrderedToSit()) {
                this.setDeltaMovement(0.0D, 0.0D, 0.0D);
                this.navigation.stop();
                this.setTarget(null);
            }
        }
        super.aiStep();
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        ItemStack itemStack2 = this.getMainHandItem();
        ItemEntity itemEntity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), new ItemStack(SGItems.GINGERBREAD_COOKIE));

        InteractionResult interactionResult = super.mobInteract(player, hand);

        if (isCantCatchMe(false)) {
            if (!level().isClientSide) {
                ItemStack cookie = new ItemStack(SGItems.GINGERBREAD_COOKIE);
                itemEntity.setPos(this.getX(), this.getY(), this.getZ());
                itemEntity.setItem(cookie);
                itemEntity.setDeltaMovement(0.0D, 0.2D, 0.0D);
                level().addFreshEntity(itemEntity);
                this.discard();
            }
            return InteractionResult.sidedSuccess(level().isClientSide);
        }

        if (!interactionResult.consumesAction() && player.isCrouching() || !interactionResult.consumesAction() && !player.isCrouching() && itemStack2.isEmpty() && itemStack.isEmpty()) {
            if (this.isOwnedBy(player)) {
                this.setOrderedToSit(!this.isOrderedToSit());
                this.jumping = false;
                this.navigation.stop();
                this.setTarget(null);
                return InteractionResult.SUCCESS_NO_ITEM_USED;
            }
        }

        if (this.isOwnedBy(player)) {
            if (itemStack2.isEmpty() && !itemStack.isEmpty()) {
                ItemStack itemStack3 = itemStack.copyWithCount(1);
                this.setItemInHand(InteractionHand.MAIN_HAND, itemStack3);
                this.removeInteractionItem(player, itemStack);
                this.level().playSound(player, this, SoundEvents.CHICKEN_EGG, SoundSource.NEUTRAL, 2.0F, 1.0F);
                return InteractionResult.sidedSuccess(this.level().isClientSide());
            } else if (!itemStack2.isEmpty() && hand == InteractionHand.MAIN_HAND && itemStack.isEmpty()) {
                this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                this.level().playSound(player, this, SoundEvents.ITEM_PICKUP, SoundSource.NEUTRAL, 2.0F, 1.0F);
                player.addItem(itemStack2);
                return InteractionResult.sidedSuccess(this.level().isClientSide());
            }
        }
        return interactionResult;
    }

    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    public void setRemainingPersistentAngerTime(int i) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, i);
    }

    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @Nullable
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    public void setPersistentAngerTarget(@Nullable UUID uUID) {
        this.persistentAngerTarget = uUID;
    }


    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SGSoundEvents.GINGERBREAD_MAN_HURT;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData) {
        this.populateDefaultEquipmentSlots(random, difficultyInstance);
        if (mobSpawnType == MobSpawnType.SPAWN_EGG) {
            this.setOwnerUUID(Objects.requireNonNull(serverLevelAccessor.getNearestPlayer(this, 8.0D)).getUUID());
        }
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 4.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.BLOCK_INTERACTION_RANGE, 4.0D)
                .add(Attributes.BLOCK_BREAK_SPEED, 0.1D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new CantCatchMeGoal(this, 2.5));
        this.goalSelector.addGoal(1, new TamableAnimal.TamableAnimalPanicGoal(1.5));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(3, new AvoidEatingPlayerGoal(this, 8.0, 1.0, 1.2));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2, (itemStack) -> itemStack.is(Items.COOKIE), false));
        this.goalSelector.addGoal(5, new CopyOwnerBreakGoal(this, 1.0));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0, 10.0F, 2.0F));
        this.goalSelector.addGoal(8, new LeapAtTargetGoal(this, 0.3F));
        this.goalSelector.addGoal(9, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(11, new WaterAvoidingRandomStrollGoal(this, 0.8, 1.0000001E-5F));
        this.goalSelector.addGoal(12, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(13, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(8, new ResetUniversalAngerTargetGoal<>(this, true));
    }

    public boolean hurt(DamageSource damageSource, float f) {
        if (this.isInvulnerableTo(damageSource)) {
            return false;
        } else {
            if (!this.level().isClientSide) {
                this.setOrderedToSit(false);
            }

            return super.hurt(damageSource, f);
        }
    }

    // region GeckoLib

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 2, this::predicate));
        controllerRegistrar.add(new AnimationController<>(this, "attackController", 2, this::attackPredicate));
    }

    private PlayState attackPredicate(AnimationState<?> event) {

        if (this.swinging && event.getController().getAnimationState().equals(AnimationController.State.STOPPED)) {
            event.setControllerSpeed(1.0F);
            event.getController().forceAnimationReset();
            event.getController().setAnimation(ATTACK);
//            event.getController().setAnimation(random.nextBoolean() ? ATTACK : ATTACK2);
            this.swinging = false;
        }

        return PlayState.CONTINUE;
    }

    private <E extends GingerbreadMan> PlayState predicate(final AnimationState<E> event) {
        if (this.isOrderedToSit()) {
            event.setAnimation(SIT);
        } else if (event.isMoving()) {
            if (this.getMainHandItem().isEmpty()) {
                event.setAnimation(WALK);
            } else {
                event.setAnimation(WALK_ITEM);
            }
        } else {
            event.setAnimation(IDLE);
        }

        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }

    // endregion

    @Override
    public boolean isFood(ItemStack itemStack) {
        return false;
    }

    public boolean wantsToAttack(LivingEntity livingEntity, LivingEntity livingEntity2) {
        if (!(livingEntity instanceof Creeper) && !(livingEntity instanceof Ghast) && !(livingEntity instanceof ArmorStand)) {
            if (livingEntity instanceof GingerbreadMan gingerbreadMan) {
                return !gingerbreadMan.isTame() || gingerbreadMan.getOwner() != livingEntity2;
            } else {
                if (livingEntity instanceof Player player) {
                    if (livingEntity2 instanceof Player player2) {
                        if (!player2.canHarmPlayer(player)) {
                            return false;
                        }
                    }
                }

                if (livingEntity instanceof AbstractHorse abstractHorse) {
                    if (abstractHorse.isTamed()) {
                        return false;
                    }
                }

                if (livingEntity instanceof TamableAnimal tamableAnimal) {
                    return !tamableAnimal.isTame();
                }
                return true;
            }
        } else {
            return false;
        }
    }

    public static class AvoidEatingPlayerGoal extends AvoidEntityGoal<Player> {
        public AvoidEatingPlayerGoal(GingerbreadMan gingerbreadMan, double distance, double walkSpeedMod, double sprintSpeedMod) {
            super(gingerbreadMan, Player.class, (float) distance, walkSpeedMod, sprintSpeedMod, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test);
        }

        public boolean canUse() {
            return this.mob.isAlive() && super.canUse()
                    && this.mob.level().getEntitiesOfClass(Player.class, this.mob.getBoundingBox().inflate(8.0D, 4.0D, 8.0D),
                    EntitySelector.NO_CREATIVE_OR_SPECTATOR).stream().anyMatch((player) -> player.isUsingItem() && player.getUseItem().is(Items.COOKIE));
        }

        public void start() {
            super.start();
        }

        public void tick() {
            super.tick();
        }
    }
}
