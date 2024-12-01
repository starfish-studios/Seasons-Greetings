package com.starfish_studios.seasons_greetings.item;

import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import com.starfish_studios.seasons_greetings.block.GiftBoxBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.Objects;

public class GiftBoxItem extends BlockItem {
    public GiftBoxItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        CustomData data = stack.get(DataComponents.BLOCK_ENTITY_DATA);
        if (data != null && !data.isEmpty()) {
            CompoundTag tag = data.copyTag();

            if (tag.contains("bow")) {
                tooltip.add(
                        Component.translatable("color.minecraft." + tag.getString("bow"))
                                .withStyle(ChatFormatting.GRAY)
                                .append(" ")
                                .append(Component.translatable("tooltip.seasons_greetings.bow")));
            }
        }
    }
}
