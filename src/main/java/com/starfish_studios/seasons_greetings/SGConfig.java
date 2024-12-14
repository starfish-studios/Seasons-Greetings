package com.starfish_studios.seasons_greetings;

import eu.midnightdust.lib.config.MidnightConfig;

public class SGConfig extends MidnightConfig {
    public static final String MAIN = "main";
    public static final String CONTENT = "content_toggles";

    @Comment(category = MAIN, centered = true) public static Comment itemsMisc;

    @Entry(category = MAIN) public static boolean eggnogEffects = true;
    @Entry(category = MAIN) public static boolean negativeEggnogEffects = true;
    @Entry(category = MAIN) public static boolean crazyGingerbreadMen = true;
    @Entry(category = MAIN) public static boolean gingerbreadManCopyBreak = true;

    @Comment(category = MAIN, centered = true) public static Comment snowGolems;

    @Entry(category = MAIN) public static boolean snowGolemsEquippable = true;
    @Entry(category = MAIN) public static boolean snowGolemItemHats = false;
    @Entry(category = MAIN) public static boolean stationarySnowGolems = true;
    @Entry(category = MAIN) public static boolean meltedSnowGolems = true;

    @Comment(category = CONTENT) public static Comment configReload;

    @Entry(category = CONTENT) public static boolean witchHouse = true;

    // Does NOT work with REI. REI will still show items, but not the recipes.
    // TODO: Find a way to disable items in REI when these are toggled, similar to the creative tab.
    @Entry(category = CONTENT) public static boolean gingerbreadBlocks = true;
    @Entry(category = CONTENT) public static boolean stringLights = true;
    @Entry(category = CONTENT) public static boolean snowBlocks = true;
    @Entry(category = CONTENT) public static boolean wreathBlock = true;
    @Entry(category = CONTENT) public static boolean icicle = true;
    @Entry(category = CONTENT) public static boolean peppermint = true;
    @Entry(category = CONTENT) public static boolean chocolate = true;
    @Entry(category = CONTENT) public static boolean icing = true;
    @Entry(category = CONTENT) public static boolean gumdrops = true;
    @Entry(category = CONTENT) public static boolean gifts = true;

    public static boolean isConfigEnabled(String key) {
        return switch (key) {
            case "gingerbreadBlocks" -> gingerbreadBlocks;
            case "stringLights" -> stringLights;
            case "snowBlocks" -> snowBlocks;
            case "wreathBlock" -> wreathBlock;
            case "icicle" -> icicle;
            case "peppermint" -> peppermint;
            case "chocolate" -> chocolate;
            case "icing" -> icing;
            case "gumdrops" -> gumdrops;
            case "gifts" -> gifts;
            default -> false;
        };
    }
}
