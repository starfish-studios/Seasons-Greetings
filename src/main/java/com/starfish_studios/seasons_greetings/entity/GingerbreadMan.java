package com.starfish_studios.seasons_greetings.entity;

import com.starfish_studios.seasons_greetings.registry.SGSoundEvents;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.pathfinder.PathType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.Objects;

public class GingerbreadMan extends TamableAnimal implements GeoEntity {
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

//    @Override
//    public boolean canPickUpLoot() {
//        return true;
//    }

//    @Override
//    public void onItemPickup(ItemEntity itemEntity) {
//        ItemStack itemStack = itemEntity.getItem();
//        if (this.getMainHandItem().isEmpty()) {
//            this.setItemInHand(InteractionHand.MAIN_HAND, itemStack.copy());
//            itemEntity.discard();
//        }
//        super.onItemPickup(itemEntity);
//    }

    private void removeInteractionItem(Player player, ItemStack itemStack) {
        itemStack.consume(1, player);
    }

    // mobInteract
    @Override
    public @NotNull InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        ItemStack itemStack2 = this.getMainHandItem();
        InteractionResult interactionResult = super.mobInteract(player, hand);

        if (!interactionResult.consumesAction() && this.isOwnedBy(player)) {
            this.setOrderedToSit(!this.isOrderedToSit());
            this.jumping = false;
            this.navigation.stop();
            this.setTarget(null);
            return InteractionResult.SUCCESS_NO_ITEM_USED;
        }

        if (itemStack2.isEmpty() && !itemStack.isEmpty()) {
            ItemStack itemStack3 = itemStack.copyWithCount(1);
            this.setItemInHand(InteractionHand.MAIN_HAND, itemStack3);
            this.removeInteractionItem(player, itemStack);
            this.level().playSound(player, this, SoundEvents.CHICKEN_EGG, SoundSource.NEUTRAL, 2.0F, 1.0F);
            return InteractionResult.SUCCESS;
        } else if (!itemStack2.isEmpty() && hand == InteractionHand.MAIN_HAND && itemStack.isEmpty()) {
            this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
            this.level().playSound(player, this, SoundEvents.ITEM_PICKUP, SoundSource.NEUTRAL, 2.0F, 1.0F);
            player.addItem(itemStack2);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }


    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SGSoundEvents.GINGERBREAD_MAN_HURT;
    }


    public boolean cantCatchMe(boolean b) {
        return b;
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
        this.goalSelector.addGoal(0, new FloatGoal(this));

        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0, true));

        this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1.25, 10.0F, 2.0F));
        this.goalSelector.addGoal(5, new AvoidEatingPlayerGoal(this, 8.0, 1.0, 1.2));

        this.goalSelector.addGoal(6, new TemptGoal(this, 1.2, (itemStack) -> itemStack.is(Items.COOKIE), false));
        this.goalSelector.addGoal(7, new CopyOwnerBreakGoal(this, 1.0));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
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
            if (this.getAttributeValue(Attributes.MOVEMENT_SPEED) == 0.25 * 1.25) {
                event.setControllerSpeed(1.25F);
            } else {
                if (this.getMainHandItem().isEmpty()) {
                    event.setAnimation(WALK);
                } else {
                    event.setAnimation(WALK_ITEM);
                }
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
