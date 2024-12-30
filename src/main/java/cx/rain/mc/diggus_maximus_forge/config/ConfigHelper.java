package cx.rain.mc.diggus_maximus_forge.config;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ConfigHelper {

    public static ModConfigSpec getConfig() {
        return ModConfigSpec.SPEC.getKey();
    }

    public static void save() {
        ModConfigSpec.SPEC.getValue().save();
    }

    public static Either<ResourceKey<Block>, TagKey<Block>> parseBlockOrTag(String s) {
        if (s.startsWith("#")) {
            var rl = ResourceLocation.parse(s.substring(1));
            var key = TagKey.create(Registries.BLOCK, rl);
            return Either.right(key);
        } else {
            var rl = ResourceLocation.parse(s);
            var rk = ResourceKey.create(Registries.BLOCK, rl);
            return Either.left(rk);
        }
    }

    public static Either<ResourceKey<Item>, TagKey<Item>> parseItemOrTag(String s) {
        if (s.startsWith("#")) {
            var rl = ResourceLocation.parse(s.substring(1));
            var key = TagKey.create(Registries.ITEM, rl);
            return Either.right(key);
        } else {
            var rl = ResourceLocation.parse(s);
            var rk = ResourceKey.create(Registries.ITEM, rl);
            return Either.left(rk);
        }
    }

    public static boolean validate(Object o) {
        if (o == null) {
            return false;
        }

        var s = o.toString();
        try {
            parseBlockOrTag(s);
            return true;
        } catch (Throwable ex) {
            return false;
        }
    }
}
