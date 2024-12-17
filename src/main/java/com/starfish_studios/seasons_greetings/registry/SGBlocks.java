package com.starfish_studios.seasons_greetings.registry;

import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import com.starfish_studios.seasons_greetings.common.block.EggnogCauldronBlock;
import com.starfish_studios.seasons_greetings.common.block.GiftBoxBlock;
import com.starfish_studios.seasons_greetings.common.block.GingerbreadDoorBlock;
import com.starfish_studios.seasons_greetings.common.block.GumdropBlock;
import com.starfish_studios.seasons_greetings.common.block.GumdropButtonBlock;
import com.starfish_studios.seasons_greetings.common.block.HotCocoaCauldronBlock;
import com.starfish_studios.seasons_greetings.common.block.IcicleBlock;
import com.starfish_studios.seasons_greetings.common.block.IcingBlock;
import com.starfish_studios.seasons_greetings.common.block.MilkCauldronBlock;
import com.starfish_studios.seasons_greetings.common.block.WrappedBlock;
import com.starfish_studios.seasons_greetings.common.block.WreathBlock;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SGBlocks {
    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(SeasonsGreetings.MOD_ID);

    // WHITE, LIGHT GRAY, GRAY, BLACK, BROWN, RED, ORANGE, YELLOW, LIME, GREEN, CYAN, LIGHT BLUE, BLUE, PURPLE, MAGENTA, PINK

    public static final DeferredBlock<Block> MILK_CAULDRON = BLOCKS.register("milk_cauldron", () -> new MilkCauldronBlock(Block.Properties.ofFullCopy(Blocks.CAULDRON), null));
    public static final DeferredBlock<Block> HOT_COCOA_CAULDRON = BLOCKS.register("hot_cocoa_cauldron", () -> new HotCocoaCauldronBlock(Block.Properties.ofFullCopy(Blocks.CAULDRON), null));
    public static final DeferredBlock<Block> EGGNOG_CAULDRON = BLOCKS.register("eggnog_cauldron", () -> new EggnogCauldronBlock(Block.Properties.ofFullCopy(Blocks.CAULDRON), null));

    public static final DeferredBlock<Block> WREATH = BLOCKS.register("wreath", () -> new WreathBlock(Block.Properties.of().instabreak().noCollission().noOcclusion().sound(SoundType.AZALEA_LEAVES).lightLevel(WreathBlock.litBlockEmission(10))));

    public static final BlockBehaviour.Properties lightProperties = Block.Properties.of().sound(SoundType.STONE).instabreak().noCollission().noOcclusion().emissiveRendering((state, world, pos) -> true).lightLevel(WrappedBlock.emission(10));
    public static final DeferredBlock<Block> WHITE_LIGHTS = BLOCKS.register("white_lights", () -> new WrappedBlock(lightProperties));
    public static final DeferredBlock<Block> RED_LIGHTS = BLOCKS.register("red_lights", () -> new WrappedBlock(lightProperties));
    public static final DeferredBlock<Block> ORANGE_LIGHTS = BLOCKS.register("orange_lights", () -> new WrappedBlock(lightProperties));
    public static final DeferredBlock<Block> YELLOW_LIGHTS = BLOCKS.register("yellow_lights", () -> new WrappedBlock(lightProperties));
    public static final DeferredBlock<Block> GREEN_LIGHTS = BLOCKS.register("green_lights", () -> new WrappedBlock(lightProperties));
    public static final DeferredBlock<Block> BLUE_LIGHTS = BLOCKS.register("blue_lights", () -> new WrappedBlock(lightProperties));
    public static final DeferredBlock<Block> PURPLE_LIGHTS = BLOCKS.register("purple_lights", () -> new WrappedBlock(lightProperties));
    public static final DeferredBlock<Block> MULTICOLOR_LIGHTS = BLOCKS.register("multicolor_lights", () -> new WrappedBlock(lightProperties));

    // Snow Block Set: Packed Snow, Snow Bricks, Snow Brick Stairs, Snow Brick Slabs

    public static final BlockBehaviour.Properties snowProperties = Block.Properties.of().strength(0.2F).sound(SGSoundEvents.PACKED_SNOW);
    public static final DeferredBlock<Block> PACKED_SNOW = BLOCKS.register("packed_snow", () -> new Block(snowProperties));
    public static final DeferredBlock<Block> SNOW_BRICKS = BLOCKS.register("snow_bricks", () -> new Block(snowProperties));
    public static final DeferredBlock<Block> SNOW_BRICK_STAIRS = BLOCKS.register("snow_brick_stairs", () -> new StairBlock(SNOW_BRICKS.get().defaultBlockState(), snowProperties));
    public static final DeferredBlock<Block> SNOW_BRICK_SLAB = BLOCKS.register("snow_brick_slab", () -> new SlabBlock(snowProperties));
    public static final DeferredBlock<Block> CHISELED_SNOW = BLOCKS.register("chiseled_snow", () -> new Block(snowProperties));

    public static final DeferredBlock<Block> ICICLE = BLOCKS.register("icicle", () -> new IcicleBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.ICE)
            .noOcclusion()
            .sound(SoundType.GLASS)
            .randomTicks()
            .strength(1.5F, 3.0F)
            .dynamicShape()
            .offsetType(BlockBehaviour.OffsetType.XZ)
            .pushReaction(PushReaction.DESTROY)
            .isRedstoneConductor((state, world, pos) -> false)));


    // region Gingerbread House Blocks

    // Gingerbread Block Set: Gingerbread, Gingerbread Stairs, Gingerbread Slabs, Gingerbread Bricks, Gingerbread Brick Stairs, Gingerbread Brick Slabs,
    // Gingerbread Shingles, Gingerbread Shingle Stairs, Gingerbread Shingle Slabs, and Gingerbread Doors.

    public static final Block.Properties gingerbreadProperties = Block.Properties.of().strength(0.3F).sound(SoundType.WOOL);
    public static final DeferredBlock<Block> GINGERBREAD_BLOCK = BLOCKS.register("gingerbread_block", () -> new Block(gingerbreadProperties));
    public static final DeferredBlock<Block> GINGERBREAD_STAIRS = BLOCKS.register("gingerbread_stairs", () -> new StairBlock(GINGERBREAD_BLOCK.get().defaultBlockState(), gingerbreadProperties));
    public static final DeferredBlock<Block> GINGERBREAD_SLAB = BLOCKS.register("gingerbread_slab", () -> new SlabBlock(gingerbreadProperties));
    public static final DeferredBlock<Block> GINGERBREAD_BRICKS = BLOCKS.register("gingerbread_bricks", () -> new Block(gingerbreadProperties));
    public static final DeferredBlock<Block> GINGERBREAD_BRICK_STAIRS = BLOCKS.register("gingerbread_brick_stairs", () -> new StairBlock(GINGERBREAD_BRICKS.get().defaultBlockState(), gingerbreadProperties));
    public static final DeferredBlock<Block> GINGERBREAD_BRICK_SLAB = BLOCKS.register("gingerbread_brick_slab", () -> new SlabBlock(gingerbreadProperties));
    public static final DeferredBlock<Block> GINGERBREAD_SHINGLES = BLOCKS.register("gingerbread_shingles", () -> new Block(gingerbreadProperties));
    public static final DeferredBlock<Block> GINGERBREAD_SHINGLE_STAIRS = BLOCKS.register("gingerbread_shingle_stairs", () -> new StairBlock(GINGERBREAD_SHINGLES.get().defaultBlockState(), gingerbreadProperties));
    public static final DeferredBlock<Block> GINGERBREAD_SHINGLE_SLAB = BLOCKS.register("gingerbread_shingle_slab", () -> new SlabBlock(gingerbreadProperties));
    public static final DeferredBlock<Block> GINGERBREAD_DOOR = BLOCKS.register("gingerbread_door", () -> new GingerbreadDoorBlock(BlockSetType.OAK, gingerbreadProperties.noOcclusion().pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> GINGERBREAD_TRAPDOOR = BLOCKS.register("gingerbread_trapdoor", () -> new TrapDoorBlock(BlockSetType.OAK, gingerbreadProperties.noOcclusion().pushReaction(PushReaction.DESTROY)));

    public static final BlockBehaviour.Properties icingProperties = Block.Properties.of().strength(0.3F).sound(SGSoundEvents.CANDY_BLOCK);
    public static final DeferredBlock<Block> ICING_BLOCK = BLOCKS.register("icing_block", () -> new Block(icingProperties));
    public static final DeferredBlock<Block> ICING_STAIRS = BLOCKS.register("icing_stairs", () -> new StairBlock(ICING_BLOCK.get().defaultBlockState(), icingProperties));
    public static final DeferredBlock<Block> ICING_SLAB = BLOCKS.register("icing_slab", () -> new SlabBlock(icingProperties));
    public static final DeferredBlock<Block> ICING = BLOCKS.register("icing", () -> new IcingBlock(icingProperties.noOcclusion().instabreak()));

    public static final BlockBehaviour.Properties chocolateProperties = Block.Properties.of().strength(0.3F).sound(SGSoundEvents.CANDY_BLOCK);
    public static final DeferredBlock<Block> CHOCOLATE_BLOCK = BLOCKS.register("chocolate_block", () -> new Block(chocolateProperties));
    public static final DeferredBlock<Block> CHOCOLATE_STAIRS = BLOCKS.register("chocolate_stairs", () -> new StairBlock(CHOCOLATE_BLOCK.get().defaultBlockState(), chocolateProperties));
    public static final DeferredBlock<Block> CHOCOLATE_SLAB = BLOCKS.register("chocolate_slab", () -> new SlabBlock(chocolateProperties));

    // Peppermint Block Set: Peppermint Block, Peppermint Stairs, Peppermint Slabs

    public static final BlockBehaviour.Properties peppermintProperties = Block.Properties.of().strength(0.5F).sound(SoundType.STONE);
    public static final DeferredBlock<Block> PEPPERMINT_BLOCK = BLOCKS.register("peppermint_block", () -> new RotatedPillarBlock(peppermintProperties));
    public static final DeferredBlock<Block> PEPPERMINT_STAIRS = BLOCKS.register("peppermint_stairs", () -> new StairBlock(PEPPERMINT_BLOCK.get().defaultBlockState(), peppermintProperties));
    public static final DeferredBlock<Block> PEPPERMINT_SLAB = BLOCKS.register("peppermint_slab", () -> new SlabBlock(peppermintProperties));

    // Gumdrop Blocks: Red, Orange, Yellow, Green, Purple

    public static final BlockBehaviour.Properties gumdropProperties = Block.Properties.of().strength(0.3F).sound(SGSoundEvents.CANDY_BLOCK).speedFactor(0.6F);
    public static final DeferredBlock<Block> RED_GUMDROP_BLOCK = BLOCKS.register("red_gumdrop_block", () -> new GumdropBlock(gumdropProperties));
    public static final DeferredBlock<Block> ORANGE_GUMDROP_BLOCK = BLOCKS.register("orange_gumdrop_block", () -> new GumdropBlock(gumdropProperties));
    public static final DeferredBlock<Block> YELLOW_GUMDROP_BLOCK = BLOCKS.register("yellow_gumdrop_block", () -> new GumdropBlock(gumdropProperties));
    public static final DeferredBlock<Block> GREEN_GUMDROP_BLOCK = BLOCKS.register("green_gumdrop_block", () -> new GumdropBlock(gumdropProperties));
    public static final DeferredBlock<Block> PURPLE_GUMDROP_BLOCK = BLOCKS.register("purple_gumdrop_block", () -> new GumdropBlock(gumdropProperties));

    public static final DeferredBlock<Block> RED_GUMDROP_BUTTON = BLOCKS.register("red_gumdrop_button", () -> new GumdropButtonBlock(BlockSetType.OAK, 60, gumdropProperties.instabreak()));
    public static final DeferredBlock<Block> ORANGE_GUMDROP_BUTTON = BLOCKS.register("orange_gumdrop_button", () -> new GumdropButtonBlock(BlockSetType.OAK, 60, gumdropProperties.instabreak()));
    public static final DeferredBlock<Block> YELLOW_GUMDROP_BUTTON = BLOCKS.register("yellow_gumdrop_button", () -> new GumdropButtonBlock(BlockSetType.OAK, 60, gumdropProperties.instabreak()));
    public static final DeferredBlock<Block> GREEN_GUMDROP_BUTTON = BLOCKS.register("green_gumdrop_button", () -> new GumdropButtonBlock(BlockSetType.OAK, 60, gumdropProperties.instabreak()));
    public static final DeferredBlock<Block> PURPLE_GUMDROP_BUTTON = BLOCKS.register("purple_gumdrop_button", () -> new GumdropButtonBlock(BlockSetType.OAK, 60, gumdropProperties.instabreak()));

    // endregion

    // region Gifts

    public static final Block.Properties giftBoxProperties = Block.Properties.of().pushReaction(PushReaction.DESTROY).instabreak().noOcclusion().sound(SGSoundEvents.GIFT_BOX);
    public static final DeferredBlock<Block> WHITE_GIFT_BOX = BLOCKS.register("white_gift_box", () -> new GiftBoxBlock(DyeColor.WHITE, giftBoxProperties));
    public static final DeferredBlock<Block> LIGHT_GRAY_GIFT_BOX = BLOCKS.register("light_gray_gift_box", () -> new GiftBoxBlock(DyeColor.LIGHT_GRAY, giftBoxProperties));
    public static final DeferredBlock<Block> GRAY_GIFT_BOX = BLOCKS.register("gray_gift_box", () -> new GiftBoxBlock(DyeColor.GRAY, giftBoxProperties));
    public static final DeferredBlock<Block> BLACK_GIFT_BOX = BLOCKS.register("black_gift_box", () -> new GiftBoxBlock(DyeColor.BLACK, giftBoxProperties));
    public static final DeferredBlock<Block> BROWN_GIFT_BOX = BLOCKS.register("brown_gift_box", () -> new GiftBoxBlock(DyeColor.BROWN, giftBoxProperties));
    public static final DeferredBlock<Block> RED_GIFT_BOX = BLOCKS.register("red_gift_box", () -> new GiftBoxBlock(DyeColor.RED, giftBoxProperties));
    public static final DeferredBlock<Block> ORANGE_GIFT_BOX = BLOCKS.register("orange_gift_box", () -> new GiftBoxBlock(DyeColor.ORANGE, giftBoxProperties));
    public static final DeferredBlock<Block> YELLOW_GIFT_BOX = BLOCKS.register("yellow_gift_box", () -> new GiftBoxBlock(DyeColor.YELLOW, giftBoxProperties));
    public static final DeferredBlock<Block> LIME_GIFT_BOX = BLOCKS.register("lime_gift_box", () -> new GiftBoxBlock(DyeColor.LIME, giftBoxProperties));
    public static final DeferredBlock<Block> GREEN_GIFT_BOX = BLOCKS.register("green_gift_box", () -> new GiftBoxBlock(DyeColor.GREEN, giftBoxProperties));
    public static final DeferredBlock<Block> CYAN_GIFT_BOX = BLOCKS.register("cyan_gift_box", () -> new GiftBoxBlock(DyeColor.CYAN, giftBoxProperties));
    public static final DeferredBlock<Block> LIGHT_BLUE_GIFT_BOX = BLOCKS.register("light_blue_gift_box", () -> new GiftBoxBlock(DyeColor.LIGHT_BLUE, giftBoxProperties));
    public static final DeferredBlock<Block> BLUE_GIFT_BOX = BLOCKS.register("blue_gift_box", () -> new GiftBoxBlock(DyeColor.BLUE, giftBoxProperties));
    public static final DeferredBlock<Block> PURPLE_GIFT_BOX = BLOCKS.register("purple_gift_box", () -> new GiftBoxBlock(DyeColor.PURPLE, giftBoxProperties));
    public static final DeferredBlock<Block> MAGENTA_GIFT_BOX = BLOCKS.register("magenta_gift_box", () -> new GiftBoxBlock(DyeColor.MAGENTA, giftBoxProperties));
    public static final DeferredBlock<Block> PINK_GIFT_BOX = BLOCKS.register("pink_gift_box", () -> new GiftBoxBlock(DyeColor.PINK, giftBoxProperties));

    // endregion

    public static void registerBlocks(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}