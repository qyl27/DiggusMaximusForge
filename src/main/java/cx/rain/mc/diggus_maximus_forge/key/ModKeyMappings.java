package cx.rain.mc.diggus_maximus_forge.key;

import cx.rain.mc.diggus_maximus_forge.ModConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = ModConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModKeyMappings {
    public static final KeyMapping EXCAVATE = new KeyMapping(ModConstants.KEY_EXCAVATE, GLFW.GLFW_KEY_GRAVE_ACCENT, ModConstants.KEY_CATEGORY);
    public static final KeyMapping SHAPED = new KeyMapping(ModConstants.KEY_SHAPED, GLFW.GLFW_KEY_UNKNOWN, ModConstants.KEY_CATEGORY);
    public static final KeyMapping CYCLE = new KeyMapping(ModConstants.KEY_CYCLE_SHAPE, GLFW.GLFW_KEY_UNKNOWN, ModConstants.KEY_CATEGORY);

    @SubscribeEvent
    public static void register(RegisterKeyMappingsEvent event) {
        event.register(EXCAVATE);
        event.register(SHAPED);
        event.register(CYCLE);
    }
}
