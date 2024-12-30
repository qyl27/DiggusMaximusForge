package cx.rain.mc.diggus_maximus_forge;

import cx.rain.mc.diggus_maximus_forge.config.ModConfigSpec;
import cx.rain.mc.diggus_maximus_forge.networking.ModNetworking;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ModConstants.MOD_ID)
public class ModMain {
    public ModMain() {
        var context = FMLJavaModLoadingContext.get();
        context.registerConfig(ModConfig.Type.COMMON, ModConfigSpec.SPEC.getRight());

        ModNetworking.register();
    }
}
