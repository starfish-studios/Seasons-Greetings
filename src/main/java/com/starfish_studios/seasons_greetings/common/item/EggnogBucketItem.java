package com.starfish_studios.seasons_greetings.common.item;

import com.starfish_studios.seasons_greetings.SGConfig;
import com.starfish_studios.seasons_greetings.registry.SGSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EggnogBucketItem extends Item {
    public EggnogBucketItem(Properties properties) {
        super(properties);
    }

    private static final int DRINK_DURATION = 40;

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        if (SGConfig.eggnogEffects) {
            if (SGConfig.negativeEggnogEffects) {
                tooltip.add(Component.translatable("effect.minecraft.nausea")
                        .append(" (00:30)")
                        .withStyle(ChatFormatting.RED));
            }
            tooltip.add(Component.translatable("effect.minecraft.strength")
                    .append(" (01:30)")
                    .withStyle(ChatFormatting.BLUE));

        }
    }

    public @NotNull ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        super.finishUsingItem(itemStack, level, livingEntity);
        if (livingEntity instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, itemStack);
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
            if (SGConfig.eggnogEffects) {
                serverPlayer.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1800, 0));
                if (SGConfig.negativeEggnogEffects) {
                    serverPlayer.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 600, 0));
                }
            }
        }

        if (itemStack.isEmpty()) {
            return new ItemStack(Items.GLASS_BOTTLE);
        } else {
            if (livingEntity instanceof Player player) {
                if (!player.hasInfiniteMaterials()) {
                    itemStack.shrink(1);
                    ItemStack itemStack2 = new ItemStack(Items.BUCKET);
                    if (!player.getInventory().add(itemStack2)) {
                        player.drop(itemStack2, false);
                    }
                }
            }

            return itemStack;
        }
    }

    public int getUseDuration(ItemStack itemStack, LivingEntity livingEntity) {
        return DRINK_DURATION;
    }

    public @NotNull UseAnim getUseAnimation(ItemStack itemStack) {
        return UseAnim.DRINK;
    }

    public @NotNull SoundEvent getDrinkingSound() {
        return SGSoundEvents.DRINK.get();
    }

    public @NotNull SoundEvent getEatingSound() {
        return SGSoundEvents.DRINK.get();
    }

    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        return ItemUtils.startUsingInstantly(level, player, interactionHand);
    }
}
