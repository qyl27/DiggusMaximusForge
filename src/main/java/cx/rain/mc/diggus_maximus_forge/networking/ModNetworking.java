package cx.rain.mc.diggus_maximus_forge.networking;

import cx.rain.mc.diggus_maximus_forge.ModConstants;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModNetworking {
    public static final String PROTOCOL_VERSION = "1";

    private static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(ModConstants.MOD_ID, "start_excavate_packet"), () -> PROTOCOL_VERSION, s -> true, s -> true);

    private static int id = 0;

    private static int nextId() {
        return id++;
    }

    static {
        CHANNEL.registerMessage(nextId(), ExcavatePacket.class, ExcavatePacket::write, ExcavatePacket::from, ExcavatePacket::handle);
    }

    public static void register() {
    }

    public static void sendToServer(ExcavatePacket packet) {
        CHANNEL.sendToServer(packet);
    }
}
