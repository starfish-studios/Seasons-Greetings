package com.starfish_studios.seasons_greetings.event;

import com.starfish_studios.seasons_greetings.block.WreathBlock;
import com.starfish_studios.seasons_greetings.registry.SGItems;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;

public class WreathInteractions implements UseBlockCallback {
    public static BlockState getBlockstateForDye(Item item, BlockState blockState) {
        return blockState.setValue(WreathBlock.BOW, DYE_MAP.get(item));
    }

    public static BlockState getBlockstateForCarpet(Item item, BlockState blockState) {
        return blockState.setValue(WreathBlock.BOW, CARPET_MAP.get(item));
    }

    public static BlockState getBlockstateForGarland(Item item, BlockState blockState) {
        return blockState.setValue(WreathBlock.GARLAND, GARLAND_MAP.get(item));
    }


    @Override
    public InteractionResult interact(Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        BlockPos pos = hitResult.getBlockPos();
        BlockState blockState = level.getBlockState(pos);
        Block block = blockState.getBlock();
        ItemStack itemStack = player.getItemInHand(hand);
        Item item = itemStack.getItem();


        if (item instanceof ShearsItem && block instanceof WreathBlock) {
            if (blockState.getValue(WreathBlock.BELL)) {
                level.setBlockAndUpdate(pos, blockState.setValue(WreathBlock.BELL, false));
                level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, new ItemStack(Items.BELL, 1)));
                return InteractionResult.SUCCESS;
            }
            else if (blockState.getValue(WreathBlock.BOW) != WreathBlock.WreathBowColors.EMPTY) {
                WreathBlock.WreathBowColors cushion = blockState.getValue(WreathBlock.BOW);
                Item carpet = SHEAR_MAP.get(cushion);
                if (carpet != null) {
                    level.setBlockAndUpdate(pos, blockState.setValue(WreathBlock.BOW, WreathBlock.WreathBowColors.EMPTY));
                    level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, new ItemStack(carpet, 1)));
                    return InteractionResult.SUCCESS;
                }
            }
            else if (blockState.getValue(WreathBlock.GARLAND) != WreathBlock.WreathGarland.EMPTY) {
                WreathBlock.WreathGarland garland = blockState.getValue(WreathBlock.GARLAND);
                Item garlandItem = GARLAND_SHEAR_MAP.get(garland);
                if (garlandItem != null) {
                    level.setBlockAndUpdate(pos, blockState.setValue(WreathBlock.GARLAND, WreathBlock.WreathGarland.EMPTY));
                    level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, new ItemStack(garlandItem, 1)));
                    return InteractionResult.SUCCESS;
                }
            }

            if (InteractionResult.SUCCESS.equals(interact(player, level, hand, hitResult))) {
                level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, player.getSoundSource(), 1.0F, 1.0F);
            }
        }
        else if (GARLAND_MAP.containsKey(item) && block instanceof WreathBlock && blockState.getValue(WreathBlock.GARLAND) == WreathBlock.WreathGarland.EMPTY) {
            BlockState newState = getBlockstateForGarland(item, blockState);
            level.setBlockAndUpdate(pos, newState);
            level.playSound(null, pos, SoundEvents.WOOL_PLACE, player.getSoundSource(), 1.0F, 1.0F);
            if (!player.isCreative()) {
                itemStack.shrink(1);
            }
            return InteractionResult.SUCCESS;
        }

        if (item == Items.BELL && !blockState.getValue(WreathBlock.BELL)) {
            level.setBlockAndUpdate(pos, blockState.setValue(WreathBlock.BELL, true));
            level.playSound(null, pos, SoundEvents.WOOL_PLACE, player.getSoundSource(), 1.0F, 1.0F);
            if (!player.isCreative()) {
                itemStack.shrink(1);
            }
            return InteractionResult.SUCCESS;
        }

        else

        if (item instanceof ShearsItem && block instanceof WreathBlock && blockState.getValue(WreathBlock.GARLAND) != WreathBlock.WreathGarland.EMPTY) {
            WreathBlock.WreathGarland garland = blockState.getValue(WreathBlock.GARLAND);
            Item garlandItem = GARLAND_SHEAR_MAP.get(garland);
            if (garlandItem != null) {
                level.setBlockAndUpdate(pos, blockState.setValue(WreathBlock.GARLAND, WreathBlock.WreathGarland.EMPTY));
                level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, new ItemStack(garlandItem, 1)));
                return InteractionResult.SUCCESS;
            }
        }

        else if (item instanceof DyeItem && block instanceof WreathBlock && blockState.getValue(WreathBlock.BOW) != WreathBlock.WreathBowColors.EMPTY) {
            BlockState newState = getBlockstateForDye(item, blockState);
            level.setBlockAndUpdate(pos, newState);
            DyeColor color = ((DyeItem) item).getDyeColor();
            level.playSound(null, pos, SoundEvents.DYE_USE, player.getSoundSource(), 1.0F, 1.0F);
            for (int j = 0; j < 5; ++j) {
                double g = level.random.nextGaussian() * 0.2;
                double h = level.random.nextGaussian() * 0.1;
                double i = level.random.nextGaussian() * 0.2;

                DustParticleOptions dustParticleOptions = new DustParticleOptions(Vec3.fromRGB24(color.getTextColor()).toVector3f(), 1.0F);

                if (!level.isClientSide) {
                    ServerLevel serverLevel = (ServerLevel) level;
                    serverLevel.sendParticles(dustParticleOptions, (double) pos.getX() + 0.5, WreathBlock.dyeHeight(), (double) pos.getZ() + 0.5, 1, g, h, i, 0.0D);
                }
            }

            return InteractionResult.SUCCESS;
        } else if (itemStack.is(ItemTags.WOOL_CARPETS) && block instanceof WreathBlock && blockState.getValue(WreathBlock.BOW) == WreathBlock.WreathBowColors.EMPTY) {
            BlockState newState = getBlockstateForCarpet(item, blockState);
            level.setBlockAndUpdate(pos, newState.setValue(BlockStateProperties.HORIZONTAL_FACING, blockState.getValue(BlockStateProperties.HORIZONTAL_FACING)));
            level.playSound(null, pos, SoundEvents.WOOL_PLACE, player.getSoundSource(), 1.0F, 1.0F);
            if (!player.isCreative()) {
                itemStack.shrink(1);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    private static final Map<WreathBlock.WreathGarland, Item> GARLAND_SHEAR_MAP = Util.make(new HashMap<>(), (map) -> {
        map.put(WreathBlock.WreathGarland.EMPTY, Items.AIR);
        map.put(WreathBlock.WreathGarland.MULTICOLOR_LIGHTS, SGItems.STRING_LIGHTS);
        map.put(WreathBlock.WreathGarland.GLOW_BERRIES, Items.GLOW_BERRIES);
        map.put(WreathBlock.WreathGarland.SWEET_BERRIES, Items.SWEET_BERRIES);
    });
    private static final Map<Item, WreathBlock.WreathGarland> GARLAND_MAP = Util.make(new HashMap<>(), (map) -> {
        map.put(Items.AIR, WreathBlock.WreathGarland.EMPTY);
        map.put(SGItems.STRING_LIGHTS, WreathBlock.WreathGarland.MULTICOLOR_LIGHTS);
        map.put(Items.GLOW_BERRIES, WreathBlock.WreathGarland.GLOW_BERRIES);
        map.put(Items.SWEET_BERRIES, WreathBlock.WreathGarland.SWEET_BERRIES);
    });

    private static final Map<WreathBlock.WreathBowColors, Item> SHEAR_MAP = Util.make(new HashMap<>(), (map) -> {
        map.put(WreathBlock.WreathBowColors.WHITE, Items.WHITE_CARPET);
        map.put(WreathBlock.WreathBowColors.LIGHT_GRAY, Items.LIGHT_GRAY_CARPET);
        map.put(WreathBlock.WreathBowColors.GRAY, Items.GRAY_CARPET);
        map.put(WreathBlock.WreathBowColors.BLACK, Items.BLACK_CARPET);
        map.put(WreathBlock.WreathBowColors.BROWN, Items.BROWN_CARPET);
        map.put(WreathBlock.WreathBowColors.RED, Items.RED_CARPET);
        map.put(WreathBlock.WreathBowColors.ORANGE, Items.ORANGE_CARPET);
        map.put(WreathBlock.WreathBowColors.YELLOW, Items.YELLOW_CARPET);
        map.put(WreathBlock.WreathBowColors.LIME, Items.LIME_CARPET);
        map.put(WreathBlock.WreathBowColors.GREEN, Items.GREEN_CARPET);
        map.put(WreathBlock.WreathBowColors.CYAN, Items.CYAN_CARPET);
        map.put(WreathBlock.WreathBowColors.LIGHT_BLUE, Items.LIGHT_BLUE_CARPET);
        map.put(WreathBlock.WreathBowColors.BLUE, Items.BLUE_CARPET);
        map.put(WreathBlock.WreathBowColors.PURPLE, Items.PURPLE_CARPET);
        map.put(WreathBlock.WreathBowColors.MAGENTA, Items.MAGENTA_CARPET);
        map.put(WreathBlock.WreathBowColors.PINK, Items.PINK_CARPET);
    });
    private static final Map<Item, WreathBlock.WreathBowColors> DYE_MAP = Util.make(new HashMap<>(), (map) -> {
        map.put(Items.WHITE_DYE, WreathBlock.WreathBowColors.WHITE);
        map.put(Items.ORANGE_DYE, WreathBlock.WreathBowColors.ORANGE);
        map.put(Items.MAGENTA_DYE, WreathBlock.WreathBowColors.MAGENTA);
        map.put(Items.LIGHT_BLUE_DYE, WreathBlock.WreathBowColors.LIGHT_BLUE);
        map.put(Items.YELLOW_DYE, WreathBlock.WreathBowColors.YELLOW);
        map.put(Items.LIME_DYE, WreathBlock.WreathBowColors.LIME);
        map.put(Items.PINK_DYE, WreathBlock.WreathBowColors.PINK);
        map.put(Items.GRAY_DYE, WreathBlock.WreathBowColors.GRAY);
        map.put(Items.LIGHT_GRAY_DYE, WreathBlock.WreathBowColors.LIGHT_GRAY);
        map.put(Items.CYAN_DYE, WreathBlock.WreathBowColors.CYAN);
        map.put(Items.PURPLE_DYE, WreathBlock.WreathBowColors.PURPLE);
        map.put(Items.BLUE_DYE, WreathBlock.WreathBowColors.BLUE);
        map.put(Items.BROWN_DYE, WreathBlock.WreathBowColors.BROWN);
        map.put(Items.GREEN_DYE, WreathBlock.WreathBowColors.GREEN);
        map.put(Items.RED_DYE, WreathBlock.WreathBowColors.RED);
        map.put(Items.BLACK_DYE, WreathBlock.WreathBowColors.BLACK);
    });
    private static final Map<Item, WreathBlock.WreathBowColors> CARPET_MAP = Util.make(new HashMap<>(), (map) -> {
        map.put(Items.WHITE_CARPET, WreathBlock.WreathBowColors.WHITE);
        map.put(Items.LIGHT_GRAY_CARPET, WreathBlock.WreathBowColors.LIGHT_GRAY);
        map.put(Items.GRAY_CARPET, WreathBlock.WreathBowColors.GRAY);
        map.put(Items.BLACK_CARPET, WreathBlock.WreathBowColors.BLACK);
        map.put(Items.BROWN_CARPET, WreathBlock.WreathBowColors.BROWN);
        map.put(Items.RED_CARPET, WreathBlock.WreathBowColors.RED);
        map.put(Items.ORANGE_CARPET, WreathBlock.WreathBowColors.ORANGE);
        map.put(Items.YELLOW_CARPET, WreathBlock.WreathBowColors.YELLOW);
        map.put(Items.LIME_CARPET, WreathBlock.WreathBowColors.LIME);
        map.put(Items.GREEN_CARPET, WreathBlock.WreathBowColors.GREEN);
        map.put(Items.CYAN_CARPET, WreathBlock.WreathBowColors.CYAN);
        map.put(Items.LIGHT_BLUE_CARPET, WreathBlock.WreathBowColors.LIGHT_BLUE);
        map.put(Items.BLUE_CARPET, WreathBlock.WreathBowColors.BLUE);
        map.put(Items.PURPLE_CARPET, WreathBlock.WreathBowColors.PURPLE);
        map.put(Items.MAGENTA_CARPET, WreathBlock.WreathBowColors.MAGENTA);
        map.put(Items.PINK_CARPET, WreathBlock.WreathBowColors.PINK);
    });
}
