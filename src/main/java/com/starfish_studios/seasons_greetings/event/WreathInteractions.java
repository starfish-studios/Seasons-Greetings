package com.starfish_studios.seasons_greetings.event;

import com.starfish_studios.seasons_greetings.common.block.WreathBlock;
import com.starfish_studios.seasons_greetings.registry.SGItems;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;

import java.util.HashMap;
import java.util.Map;

public class WreathInteractions {

    private static void shearDamageSound(Level level, BlockPos pos, Player player, InteractionHand hand) {
        level.playSound(null, pos, SoundEvents.SNOW_GOLEM_SHEAR, player.getSoundSource(), 1.0F, 1.0F);
        player.getItemInHand(hand).hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
    }

    @SubscribeEvent
    public void onBlockInteract(RightClickBlock event) {
        BlockHitResult hitResult = event.getHitVec();
        Level level = event.getLevel();
        Player player = event.getEntity();
        InteractionHand hand = event.getHand();
        BlockPos pos = hitResult.getBlockPos();
        BlockState blockState = level.getBlockState(pos);
        ItemStack itemStack = player.getItemInHand(hand);
        Item item = itemStack.getItem();

        if (!(blockState.getBlock() instanceof WreathBlock)) {
            return;
        }

        // Shears interaction
        if (item instanceof ShearsItem) {
            InteractionResult result = handleShearsInteraction(level, pos, blockState, player, hand);
            if (result.consumesAction()) {
                event.setCancellationResult(result);
            }
        }

        // Garland Interactions (Sweet Berries, Glow Berries, Lights, etc.)
        if (InteractionMaps.GARLAND_MAP.containsKey(item) && blockState.getValue(WreathBlock.GARLAND) == WreathBlock.WreathGarland.EMPTY) {
            BlockState newState = InteractionMaps.getBlockstateForGarland(item, blockState);
            level.setBlockAndUpdate(pos, newState);
            consumeItemIfNotCreative(player, itemStack);
            if (newState.getValue(WreathBlock.GARLAND) != WreathBlock.WreathGarland.EMPTY) {
                playSound(level, pos, SoundEvents.AZALEA_PLACE, player);
            }
            event.setCancellationResult(InteractionResult.SUCCESS);
        }

        // Bell Interaction
        if (item == Items.BELL && !blockState.getValue(WreathBlock.BELL)) {
            level.setBlockAndUpdate(pos, blockState.setValue(WreathBlock.BELL, true));
            playSound(level, pos, SoundEvents.AZALEA_PLACE, player);
            consumeItemIfNotCreative(player, itemStack);
            event.setCancellationResult(InteractionResult.SUCCESS);
        }

        // Dye Interaction
        if (item instanceof DyeItem) {
            DyeItem dye = (DyeItem) itemStack.getItem();
            WreathBlock.WreathBowColors currentBow = blockState.getValue(WreathBlock.BOW);
            WreathBlock.WreathBowColors newBow = InteractionMaps.DYE_MAP.get(dye);

            if (newBow != null && currentBow != newBow && currentBow != WreathBlock.WreathBowColors.EMPTY) {
                BlockState newState = InteractionMaps.getBlockstateForDye(dye, blockState);
                level.setBlockAndUpdate(pos, newState);
                playSound(level, pos, SoundEvents.DYE_USE, player);
                spawnDyeParticles(level, pos, dye.getDyeColor());
                consumeItemIfNotCreative(player, itemStack);
                event.setCancellationResult(InteractionResult.SUCCESS);
            }
        }

        // Carpets -> Bows
        if (itemStack.is(ItemTags.WOOL_CARPETS) && blockState.getValue(WreathBlock.BOW) == WreathBlock.WreathBowColors.EMPTY) {
            BlockState newState = InteractionMaps.getBlockstateForCarpet(item, blockState)
                    .setValue(BlockStateProperties.HORIZONTAL_FACING, blockState.getValue(BlockStateProperties.HORIZONTAL_FACING));
            level.setBlockAndUpdate(pos, newState);
            playSound(level, pos, SoundEvents.AZALEA_PLACE, player);
            consumeItemIfNotCreative(player, itemStack);
            event.setCancellationResult(InteractionResult.SUCCESS);
        }

        event.setCancellationResult(InteractionResult.FAIL);
    }

    private InteractionResult handleShearsInteraction(Level level, BlockPos pos, BlockState blockState, Player player, InteractionHand hand) {
        if (blockState.getValue(WreathBlock.BOW) != WreathBlock.WreathBowColors.EMPTY) {
            WreathBlock.WreathBowColors bowColor = blockState.getValue(WreathBlock.BOW);
            Item bowItem = InteractionMaps.SHEAR_MAP.get(bowColor);
            if (bowItem != null) {
                level.setBlockAndUpdate(pos, blockState.setValue(WreathBlock.BOW, WreathBlock.WreathBowColors.EMPTY));
                dropItem(level, pos, new ItemStack(bowItem));
                shearDamageSound(level, pos, player, hand);
                return InteractionResult.SUCCESS;
            }
        }

        if (blockState.getValue(WreathBlock.BELL)) {
            level.setBlockAndUpdate(pos, blockState.setValue(WreathBlock.BELL, false));
            dropItem(level, pos, new ItemStack(Items.BELL));
            shearDamageSound(level, pos, player, hand);
            return InteractionResult.SUCCESS;
        }

        if (blockState.getValue(WreathBlock.GARLAND) != WreathBlock.WreathGarland.EMPTY) {
            WreathBlock.WreathGarland garland = blockState.getValue(WreathBlock.GARLAND);
            Item garlandItem = InteractionMaps.GARLAND_SHEAR_MAP.get(garland);
            if (garlandItem != null) {
                level.setBlockAndUpdate(pos, blockState.setValue(WreathBlock.GARLAND, WreathBlock.WreathGarland.EMPTY));
                dropItem(level, pos, new ItemStack(garlandItem));
                shearDamageSound(level, pos, player, hand);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    private InteractionResult handleDyeInteraction(Level level, BlockPos pos, BlockState blockState, Player player, ItemStack itemStack) {
        DyeItem dye = (DyeItem) itemStack.getItem();
        WreathBlock.WreathBowColors currentBow = blockState.getValue(WreathBlock.BOW);
        WreathBlock.WreathBowColors newBow = InteractionMaps.DYE_MAP.get(dye);

        if (newBow == null || currentBow == newBow) {
            return InteractionResult.PASS;
        }

        BlockState newState = InteractionMaps.getBlockstateForDye(dye, blockState);
        level.setBlockAndUpdate(pos, newState);
        playSound(level, pos, SoundEvents.DYE_USE, player);
        spawnDyeParticles(level, pos, dye.getDyeColor());
        return InteractionResult.SUCCESS;
    }


    private void playSound(Level level, BlockPos pos, SoundEvent sound, Player player) {
        level.playSound(null, pos, sound, player.getSoundSource(), 1.0F, 1.0F);
    }

    private void consumeItemIfNotCreative(Player player, ItemStack itemStack) {
        if (!player.isCreative()) {
            itemStack.shrink(1);
        }
    }

    private void dropItem(Level level, BlockPos pos, ItemStack itemStack) {
        level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, itemStack));
    }

    private void spawnDyeParticles(Level level, BlockPos pos, DyeColor color) {
        DustParticleOptions particleOptions = new DustParticleOptions(Vec3.fromRGB24(color.getTextColor()).toVector3f(), 1.0F);
        for (int i = 0; i < 5; ++i) {
            double offsetX = level.random.nextGaussian() * 0.2;
            double offsetY = level.random.nextGaussian() * 0.1;
            double offsetZ = level.random.nextGaussian() * 0.2;
            level.addParticle(particleOptions, pos.getX() + 0.5, WreathBlock.dyeHeight(), pos.getX() + 0.5, offsetX, offsetY, offsetZ);
        }
    }


    private static class InteractionMaps {
        public static BlockState getBlockstateForDye(Item item, BlockState blockState) {
            return blockState.setValue(WreathBlock.BOW, DYE_MAP.get(item));
        }

        public static BlockState getBlockstateForCarpet(Item item, BlockState blockState) {
            return blockState.setValue(WreathBlock.BOW, CARPET_MAP.get(item));
        }

        public static BlockState getBlockstateForGarland(Item item, BlockState blockState) {
            return blockState.setValue(WreathBlock.GARLAND, GARLAND_MAP.get(item));
        }

        private static final Map<WreathBlock.WreathGarland, Item> GARLAND_SHEAR_MAP = Util.make(new HashMap<>(), (map) -> {
            map.put(WreathBlock.WreathGarland.EMPTY, Items.AIR);
            map.put(WreathBlock.WreathGarland.WHITE_LIGHTS, SGItems.WHITE_LIGHTS.asItem());
            map.put(WreathBlock.WreathGarland.RED_LIGHTS, SGItems.RED_LIGHTS.asItem());
            map.put(WreathBlock.WreathGarland.ORANGE_LIGHTS, SGItems.ORANGE_LIGHTS.asItem());
            map.put(WreathBlock.WreathGarland.YELLOW_LIGHTS, SGItems.YELLOW_LIGHTS.asItem());
            map.put(WreathBlock.WreathGarland.GREEN_LIGHTS, SGItems.GREEN_LIGHTS.asItem());
            map.put(WreathBlock.WreathGarland.BLUE_LIGHTS, SGItems.BLUE_LIGHTS.asItem());
            map.put(WreathBlock.WreathGarland.PURPLE_LIGHTS, SGItems.PURPLE_LIGHTS.asItem());
            map.put(WreathBlock.WreathGarland.MULTICOLOR_LIGHTS, SGItems.MULTICOLOR_LIGHTS.asItem());
            map.put(WreathBlock.WreathGarland.GLOW_BERRIES, Items.GLOW_BERRIES);
            map.put(WreathBlock.WreathGarland.SWEET_BERRIES, Items.SWEET_BERRIES);
        });
        private static final Map<Item, WreathBlock.WreathGarland> GARLAND_MAP = Util.make(new HashMap<>(), (map) -> {
            map.put(Items.AIR, WreathBlock.WreathGarland.EMPTY);
            map.put(SGItems.WHITE_LIGHTS.asItem(), WreathBlock.WreathGarland.WHITE_LIGHTS);
            map.put(SGItems.RED_LIGHTS.asItem(), WreathBlock.WreathGarland.RED_LIGHTS);
            map.put(SGItems.ORANGE_LIGHTS.asItem(), WreathBlock.WreathGarland.ORANGE_LIGHTS);
            map.put(SGItems.YELLOW_LIGHTS.asItem(), WreathBlock.WreathGarland.YELLOW_LIGHTS);
            map.put(SGItems.GREEN_LIGHTS.asItem(), WreathBlock.WreathGarland.GREEN_LIGHTS);
            map.put(SGItems.BLUE_LIGHTS.asItem(), WreathBlock.WreathGarland.BLUE_LIGHTS);
            map.put(SGItems.PURPLE_LIGHTS.asItem(), WreathBlock.WreathGarland.PURPLE_LIGHTS);
            map.put(SGItems.MULTICOLOR_LIGHTS.asItem(), WreathBlock.WreathGarland.MULTICOLOR_LIGHTS);

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
}
