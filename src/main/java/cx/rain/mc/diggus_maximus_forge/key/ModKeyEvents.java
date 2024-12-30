package cx.rain.mc.diggus_maximus_forge.key;

import cx.rain.mc.diggus_maximus_forge.ModConstants;
import cx.rain.mc.diggus_maximus_forge.config.ConfigHelper;
import cx.rain.mc.diggus_maximus_forge.excavate.ExcavateTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Locale;

import static cx.rain.mc.diggus_maximus_forge.key.ModKeyMappings.CYCLE;

@Mod.EventBusSubscriber(modid = ModConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModKeyEvents {
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        var pressed = false;
        while (CYCLE.consumeClick()) {
            pressed = true;
        }

        var config = ConfigHelper.getConfig();
        if (config.shapeEnabled.get() && pressed) {
            var selected = config.currentShape.get().ordinal();
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.isShiftKeyDown()) {
                selected--;
            } else {
                selected++;
            }
            var shapesCount = ExcavateTypes.Shape.values().length;
            while (selected < 0 || selected >= shapesCount) {
                selected += shapesCount;
                selected %= shapesCount;
            }
            config.currentShape.set(ExcavateTypes.Shape.values()[selected]);
            ConfigHelper.save();
            Minecraft.getInstance().player.displayClientMessage(Component.translatable("diggusmaximus.shape." + ExcavateTypes.Shape.values()[selected].toString().toLowerCase(Locale.ROOT)), true);
        }
    }
}
