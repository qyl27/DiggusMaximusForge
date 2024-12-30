package cx.rain.mc.diggus_maximus_forge;

import cx.rain.mc.diggus_maximus_forge.networking.ExcavatePacket;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ExcavateHolder {
    private static final Map<UUID, Boolean> excavating = new HashMap<>();

    public static void startExcavate(Player player) {
        excavating.put(player.getUUID(), true);
    }

    public static void stopExcavate(Player player) {
        excavating.put(player.getUUID(), false);
    }

    public static boolean isExcavating(Player player) {
        return excavating.computeIfAbsent(player.getUUID(), u -> false);
    }
}
