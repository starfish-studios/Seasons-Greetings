package com.starfish_studios.seasons_greetings.entity;

import com.starfish_studios.seasons_greetings.registry.SGSoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class GingerbreadMan extends PathfinderMob implements GeoEntity {
    protected static final RawAnimation IDLE = RawAnimation.begin().thenLoop("animation.gingerbread_man.idle");
    protected static final RawAnimation WALK = RawAnimation.begin().thenLoop("animation.gingerbread_man.walk");
    protected static final RawAnimation ATTACK = RawAnimation.begin().thenPlay("animation.gingerbread_man.attack");
    protected static final RawAnimation DIE = RawAnimation.begin().thenPlayAndHold("animation.gingerbread_man.die");

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);


    public GingerbreadMan(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SGSoundEvents.GINGERBREAD_MAN_HURT;
    }


    public boolean cantCatchMe(boolean b) {
        return b;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 4.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new CantCatchMeGoal(this, 2.0));
        this.goalSelector.addGoal(2, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2, (itemStack) -> itemStack.is(ItemTags.PIG_FOOD), false));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 2, this::animController));
        controllerRegistrar.add(new AnimationController<>(this, "attack_controller", 2, this::attackController));
    }

    private <E extends GingerbreadMan> PlayState attackController(final AnimationState<E> event) {
        if (this.swinging) {
            event.setAnimation(ATTACK);
        }
        return PlayState.CONTINUE;
    }

    private <E extends GingerbreadMan> PlayState animController(final AnimationState<E> event) {

//        if (this.isDeadOrDying()) {
//            event.setAnimation(DIE);
//        }
//        else
        if (event.isMoving()) {
            if (this.getAttributeValue(Attributes.MOVEMENT_SPEED) == 0.25 * 1.25) {
                event.setControllerSpeed(1.25F);
            } else {
                event.setAnimation(WALK);
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
}
