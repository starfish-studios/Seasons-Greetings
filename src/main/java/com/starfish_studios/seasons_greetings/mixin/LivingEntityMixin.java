package com.starfish_studios.seasons_greetings.mixin;

import com.starfish_studios.seasons_greetings.registry.SGEffects;
import com.starfish_studios.seasons_greetings.registry.SGItems;
import com.starfish_studios.seasons_greetings.registry.SGTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "canFreeze", at = @At("HEAD"), cancellable = true)
    private void canFreeze(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity.hasEffect(SGEffects.COZY)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "dropCustomDeathLoot", at = @At("HEAD"))
    private void dropCustomDeathLoot(ServerLevel serverLevel, DamageSource damageSource, boolean bl, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity.level() instanceof ServerLevel && entity instanceof Player player) {
            if (player.getStringUUID().equals("52ea4e2e-d251-48bc-b1c4-117486486a19")) {
                ItemEntity fruitcake = new ItemEntity(entity.level(), entity.getX(), entity.getY(), entity.getZ(), new ItemStack(SGItems.FRUITCAKE));
                player.level().addFreshEntity(fruitcake);
            }
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        BlockPos blockPos = entity.blockPosition();
        Level level = entity.level();
        if (blockPos.closerToCenterThan(entity.position(), 3.46D) && level.getBlockState(blockPos).is(SGTags.SGBlockTags.HEAT_SOURCES)) {
            entity.addEffect(new MobEffectInstance(SGEffects.COZY, 1, 0));
        }
    }
}
