package com.starfish_studios.seasons_greetings.registry;

import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import com.starfish_studios.seasons_greetings.block.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.PushReaction;

public class SGBlocks {
    // WHITE, LIGHT GRAY, GRAY, BLACK, BROWN, RED, ORANGE, YELLOW, LIME, GREEN, CYAN, LIGHT BLUE, BLUE, PURPLE, MAGENTA, PINK


    public static final Block HOT_COCOA_CAULDRON = registerBlock("hot_cocoa_cauldron", new HotCocoaCauldronBlock(Block.Properties.ofFullCopy(Blocks.CAULDRON), null));
    public static final Block MILK_CAULDRON = registerBlock("milk_cauldron", new MilkCauldronBlock(Block.Properties.ofFullCopy(Blocks.CAULDRON), null));

    public static final Block STRING_LIGHTS = registerBlock("string_lights", new WrappedBlock(Block.Properties.of().instabreak().noCollission().noOcclusion()));
    public static final Block WREATH = registerBlock("wreath", new WrappedBlock(Block.Properties.of().instabreak().noCollission().noOcclusion()));

    // region Gingerbread House Blocks

    // Gingerbread Block Set : Gingerbread, Gingerbread Stairs, Gingerbread Slabs, Gingerbread Bricks, Gingerbread Brick Stairs, Gingerbread Brick Slabs,
    // Gingerbread Shingles, Gingerbread Shingle Stairs, Gingerbread Shingle Slabs, and Gingerbread Doors.
    public static final Block.Properties gingerbreadProperties = Block.Properties.of().strength(0.3F).sound(SoundType.WOOL);

    public static final Block GINGERBREAD_BLOCK = registerBlock("gingerbread_block", new Block(gingerbreadProperties));
    public static final Block GINGERBREAD_STAIRS = registerBlock("gingerbread_stairs", new StairBlock(GINGERBREAD_BLOCK.defaultBlockState(), gingerbreadProperties));
    public static final Block GINGERBREAD_SLAB = registerBlock("gingerbread_slab", new SlabBlock(gingerbreadProperties));
    public static final Block GINGERBREAD_BRICKS = registerBlock("gingerbread_bricks", new Block(gingerbreadProperties));
    public static final Block GINGERBREAD_BRICK_STAIRS = registerBlock("gingerbread_brick_stairs", new StairBlock(GINGERBREAD_BRICKS.defaultBlockState(), gingerbreadProperties));
    public static final Block GINGERBREAD_BRICK_SLAB = registerBlock("gingerbread_brick_slab", new SlabBlock(gingerbreadProperties));
    public static final Block GINGERBREAD_SHINGLES = registerBlock("gingerbread_shingles", new Block(gingerbreadProperties));
    public static final Block GINGERBREAD_SHINGLE_STAIRS = registerBlock("gingerbread_shingle_stairs", new StairBlock(GINGERBREAD_SHINGLES.defaultBlockState(), gingerbreadProperties));
    public static final Block GINGERBREAD_SHINGLE_SLAB = registerBlock("gingerbread_shingle_slab", new SlabBlock(gingerbreadProperties));
//    public static final Block GINGERBREAD_DOOR = registerBlock("gingerbread_door", new DoorBlock(null, gingerbreadProperties.noOcclusion().pushReaction(PushReaction.DESTROY)));

    public static final BlockBehaviour.Properties chocolateProperties = Block.Properties.of().strength(0.3F).sound(SoundType.WOOL);
    public static final Block CHOCOLATE_BLOCK = registerBlock("chocolate_block", new Block(chocolateProperties));
    public static final Block CHOCOLATE_STAIRS = registerBlock("chocolate_stairs", new StairBlock(CHOCOLATE_BLOCK.defaultBlockState(), chocolateProperties));
    public static final Block CHOCOLATE_SLAB = registerBlock("chocolate_slab", new SlabBlock(chocolateProperties));

    // Peppermint Block Set : Peppermint Block, Peppermint Stairs, Peppermint Slabs
    public static final BlockBehaviour.Properties peppermintProperties = Block.Properties.of().strength(0.5F).sound(SoundType.STONE);

    public static final Block PEPPERMINT_BLOCK = registerBlock("peppermint_block", new RotatedPillarBlock(peppermintProperties));
    public static final Block PEPPERMINT_STAIRS = registerBlock("peppermint_stairs", new StairBlock(PEPPERMINT_BLOCK.defaultBlockState(), peppermintProperties));
    public static final Block PEPPERMINT_SLAB = registerBlock("peppermint_slab", new SlabBlock(peppermintProperties));

    // Gumdrop Blocks : Red, Orange, Yellow, Green, Purple
    public static final BlockBehaviour.Properties gumdropProperties = Block.Properties.of().strength(0.3F).sound(SoundType.MUD).speedFactor(0.6F);

    public static final Block RED_GUMDROP_BLOCK = registerBlock("red_gumdrop_block", new GumdropBlock(gumdropProperties));
    public static final Block ORANGE_GUMDROP_BLOCK = registerBlock("orange_gumdrop_block", new GumdropBlock(gumdropProperties));
    public static final Block YELLOW_GUMDROP_BLOCK = registerBlock("yellow_gumdrop_block", new GumdropBlock(gumdropProperties));
    public static final Block GREEN_GUMDROP_BLOCK = registerBlock("green_gumdrop_block", new GumdropBlock(gumdropProperties));
    public static final Block PURPLE_GUMDROP_BLOCK = registerBlock("purple_gumdrop_block", new GumdropBlock(gumdropProperties));

    public static final Block RED_GUMDROP_BUTTON = registerBlock("red_gumdrop_button", new GumdropButtonBlock(BlockSetType.OAK, 60, gumdropProperties));
    public static final Block ORANGE_GUMDROP_BUTTON = registerBlock("orange_gumdrop_button", new GumdropButtonBlock(BlockSetType.OAK, 60, gumdropProperties));
    public static final Block YELLOW_GUMDROP_BUTTON = registerBlock("yellow_gumdrop_button", new GumdropButtonBlock(BlockSetType.OAK, 60, gumdropProperties));
    public static final Block GREEN_GUMDROP_BUTTON = registerBlock("green_gumdrop_button", new GumdropButtonBlock(BlockSetType.OAK, 60, gumdropProperties));
    public static final Block PURPLE_GUMDROP_BUTTON = registerBlock("purple_gumdrop_button", new GumdropButtonBlock(BlockSetType.OAK, 60, gumdropProperties));


    // endregion

    // region Gifts

    public static final Block.Properties giftBoxProperties = Block.Properties.of().pushReaction(PushReaction.DESTROY).instabreak().noOcclusion();

    public static final Block WHITE_GIFT_BOX = registerBlock("white_gift_box", new GiftBoxBlock(DyeColor.WHITE, giftBoxProperties));
    public static final Block LIGHT_GRAY_GIFT_BOX = registerBlock("light_gray_gift_box", new GiftBoxBlock(DyeColor.LIGHT_GRAY, giftBoxProperties));
    public static final Block GRAY_GIFT_BOX = registerBlock("gray_gift_box", new GiftBoxBlock(DyeColor.GRAY, giftBoxProperties));
    public static final Block BLACK_GIFT_BOX = registerBlock("black_gift_box", new GiftBoxBlock(DyeColor.BLACK, giftBoxProperties));
    public static final Block BROWN_GIFT_BOX = registerBlock("brown_gift_box", new GiftBoxBlock(DyeColor.BROWN, giftBoxProperties));
    public static final Block RED_GIFT_BOX = registerBlock("red_gift_box", new GiftBoxBlock(DyeColor.RED, giftBoxProperties));
    public static final Block ORANGE_GIFT_BOX = registerBlock("orange_gift_box", new GiftBoxBlock(DyeColor.ORANGE, giftBoxProperties));
    public static final Block YELLOW_GIFT_BOX = registerBlock("yellow_gift_box", new GiftBoxBlock(DyeColor.YELLOW, giftBoxProperties));
    public static final Block LIME_GIFT_BOX = registerBlock("lime_gift_box", new GiftBoxBlock(DyeColor.LIME, giftBoxProperties));
    public static final Block GREEN_GIFT_BOX = registerBlock("green_gift_box", new GiftBoxBlock(DyeColor.GREEN, giftBoxProperties));
    public static final Block CYAN_GIFT_BOX = registerBlock("cyan_gift_box", new GiftBoxBlock(DyeColor.CYAN, giftBoxProperties));
    public static final Block LIGHT_BLUE_GIFT_BOX = registerBlock("light_blue_gift_box", new GiftBoxBlock(DyeColor.LIGHT_BLUE, giftBoxProperties));
    public static final Block BLUE_GIFT_BOX = registerBlock("blue_gift_box", new GiftBoxBlock(DyeColor.BLUE, giftBoxProperties));
    public static final Block PURPLE_GIFT_BOX = registerBlock("purple_gift_box", new GiftBoxBlock(DyeColor.PURPLE, giftBoxProperties));
    public static final Block MAGENTA_GIFT_BOX = registerBlock("magenta_gift_box", new GiftBoxBlock(DyeColor.MAGENTA, giftBoxProperties));
    public static final Block PINK_GIFT_BOX = registerBlock("pink_gift_box", new GiftBoxBlock(DyeColor.PINK, giftBoxProperties));

    public static final Block[] GIFT_BOXES = new Block[DyeColor.values().length];

    // endregion

    // Registry

    @SuppressWarnings("all")
    private static Block registerBlock(String id, Block block) {
        return Registry.register(BuiltInRegistries.BLOCK, SeasonsGreetings.id(id), block);
    }

    public static void registerItems() {}
}
