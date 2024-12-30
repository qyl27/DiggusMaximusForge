package cx.rain.mc.diggus_maximus_forge.config;

import com.mojang.datafixers.util.Either;
import cx.rain.mc.diggus_maximus_forge.ModConstants;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber(modid = ModConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModConfigTransients {
    public static final Set<Either<ResourceKey<Item>, TagKey<Item>>> tools = new HashSet<>();
    public static final Set<Either<ResourceKey<Block>, TagKey<Block>>> blockedBlocks = new HashSet<>();
    public static final List<Set<Either<ResourceKey<Block>, TagKey<Block>>>> blockGroups = new ArrayList<>();

    public static void reload() {
        tools.clear();
        blockedBlocks.clear();
        blockGroups.clear();

        for (var s : ModConfigSpec.SPEC.getKey().tools.get()) {
            tools.add(ConfigHelper.parseItemOrTag(s));
        }

        for (var s : ModConfigSpec.SPEC.getKey().blockList.get()) {
            blockedBlocks.add(ConfigHelper.parseBlockOrTag(s));
        }

        for (var g : ModConfigSpec.SPEC.getKey().groups.get()) {
            var set = new HashSet<Either<ResourceKey<Block>, TagKey<Block>>>();
            for (var s : g.split(",")) {
                set.add(ConfigHelper.parseBlockOrTag(s));
            }
            blockGroups.add(set);
        }
    }

    @SubscribeEvent
    public static void onLoad(ModConfigEvent.Loading event) {
        reload();
    }


    @SubscribeEvent
    public static void onReload(ModConfigEvent.Reloading event) {
        reload();
    }
}
