package com.starfish_studios.seasons_greetings.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.Objects;

public class GiftBoxItem extends BlockItem {
    public GiftBoxItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public boolean canFitInsideContainerItems() {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        if (stack.has(DataComponents.BASE_COLOR)) {
            tooltip.add(Component.translatable("color.minecraft." + Objects.requireNonNull(stack.get(DataComponents.BASE_COLOR)).getName())
                    .withStyle(ChatFormatting.GRAY)
                    .append(" ")
                    .append(Component.translatable("tooltip.seasons_greetings.bow")));
        }
        tooltip.add(Component.translatable("item.dyeable").withStyle(ChatFormatting.DARK_GRAY).withStyle(ChatFormatting.ITALIC));
    }
}
