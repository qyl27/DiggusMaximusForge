package cx.rain.mc.diggus_maximus_forge.networking;

import cx.rain.mc.diggus_maximus_forge.config.ConfigHelper;
import cx.rain.mc.diggus_maximus_forge.excavate.Excavate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ExcavatePacket(BlockPos pos, ResourceLocation id, Direction facing,
                             int shape) {

    public static ExcavatePacket from(FriendlyByteBuf buf) {
        var pos = buf.readBlockPos();
        var id = buf.readResourceLocation();
        var facingId = buf.readInt();
        var facing = facingId == -1 ? null : Direction.from3DDataValue(facingId);
        var shape = buf.readInt();
        return new ExcavatePacket(pos, id, facing, shape);
    }

    public static void write(ExcavatePacket payload, FriendlyByteBuf buf) {
        buf.writeBlockPos(payload.pos);
        buf.writeResourceLocation(payload.id);
        buf.writeInt(payload.facing == null ? -1 : payload.facing.get3DDataValue());
        buf.writeInt(payload.shape);
    }

    public void handle(Supplier<NetworkEvent.Context> sup) {
        var context = sup.get();
        context.enqueueWork(() -> {
            var player = context.getSender();
            if (ConfigHelper.getConfig().enabled.get() && player != null) {
                if (pos().closerToCenterThan(player.position(), 10)) {
                    new Excavate(pos(), id(), player, facing()).startExcavate(shape());
                }
            }
        });
    }
}
