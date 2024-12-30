package cx.rain.mc.diggus_maximus_forge.config;

import cx.rain.mc.diggus_maximus_forge.excavate.ExcavateTypes;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class ModConfigSpec {
    public static Pair<ModConfigSpec, ForgeConfigSpec> SPEC;

    static {
        SPEC = new ForgeConfigSpec.Builder()
                .configure(ModConfigSpec::new);
    }

    public ForgeConfigSpec.BooleanValue enabled;
    public ForgeConfigSpec.BooleanValue invertActivation;
    public ForgeConfigSpec.BooleanValue sneakToEnable;
    public ForgeConfigSpec.BooleanValue diagonally;
    public ForgeConfigSpec.IntValue maxMineBlocks;
    public ForgeConfigSpec.IntValue maxMineDistance;
    public ForgeConfigSpec.BooleanValue autoPickup;
    public ForgeConfigSpec.BooleanValue requiresTool;
    public ForgeConfigSpec.BooleanValue dontBreakTool;
    public ForgeConfigSpec.BooleanValue stopOnToolBroken;
    public ForgeConfigSpec.BooleanValue damagesTool;
    public ForgeConfigSpec.BooleanValue playerExhaustion;
    public ForgeConfigSpec.DoubleValue exhaustionMultiplier;
    public ForgeConfigSpec.ConfigValue<List<? extends String>> tools;

    public ForgeConfigSpec.BooleanValue useAsAllowList;
    public ForgeConfigSpec.ConfigValue<List<? extends String>> blockList;

    public ForgeConfigSpec.BooleanValue useCustomGrouping;
    public ForgeConfigSpec.ConfigValue<List<? extends String>> groups;

    public ForgeConfigSpec.BooleanValue shapeEnabled;
    public ForgeConfigSpec.BooleanValue ignoreBlockType;
    public ForgeConfigSpec.EnumValue<ExcavateTypes.Shape> currentShape;

    public ModConfigSpec(ForgeConfigSpec.Builder builder) {
        builder.push("config");
        enabled = builder.comment("Mod enabled or disabled").define("enabled", true);
        invertActivation = builder.comment("Activations when un-hold").define("invertActivation", false);
        sneakToEnable = builder.comment("Sneak to excavate (can work serverside only)").define("sneakToEnable", false);
        diagonally = builder.comment("Should mine diagonally, excludes shape excavating").define("diagonally", true);
        maxMineBlocks = builder.comment("Maximum number of blocks to mine").defineInRange("maxMineBlocks", 40, 0, 4096);
        maxMineDistance = builder.comment("Maximum distance from start to mine").defineInRange("maxMineDistance", 10, 0, 128);
        autoPickup = builder.comment("Automatically pick up drops").define("autoPickup", true);
        requiresTool = builder.comment("Requires a tool to excavate").define("requiresTool", false);
        dontBreakTool = builder.comment("Save the last durability of your tool").define("dontBreakTool", true);
        stopOnToolBroken = builder.comment("Stop excavating when tool broken").define("stopOnToolBroken", true);
        damagesTool = builder.comment("Should tool take durability").define("damagesTool", true);
        playerExhaustion = builder.comment("Should player get exhaustion").define("playerExhaustion", true);
        exhaustionMultiplier = builder.comment("Multiply exhaustion when excavating").defineInRange("exhaustionMultiplier", 1.0, 0, Float.MAX_VALUE);
        tools = builder.comment("Other items to be considered tools ie: \"minecraft:stick\"").defineListAllowEmpty("tools", new ArrayList<>(), ConfigHelper::validate);
        builder.pop();

        builder.push("blocklist");
        useAsAllowList = builder.comment("Only allow blocks in list being mined").define("useAsAllowList", false);
        blockList = builder.comment("Prevent block IDs or tags from being mined").defineListAllowEmpty("blockList", new ArrayList<>(), ConfigHelper::validate);
        builder.pop();

        builder.push("grouping");
        useCustomGrouping = builder.comment("Enable custom block grouping").define("useCustomGrouping", false);
        groups = builder.comment("Block IDs or tags to be considered as the same block when excavating (one line per group, separated by commas)").defineListAllowEmpty("groups", new ArrayList<>(), ConfigHelper::validate);
        builder.pop();

        builder.push("shapes");
        shapeEnabled = builder.comment("Should shape excavating be enabled").define("shapeEnabled", true);
        ignoreBlockType = builder.comment("Should shape excavating include different blocks").define("ignoreBlockType", true);
        currentShape = builder.comment("Currently selected shape").defineEnum("selectedShape", ExcavateTypes.Shape.LAYER);
        builder.pop();
    }
}
