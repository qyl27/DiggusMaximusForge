package cx.rain.mc.diggus_maximus_forge.excavate;

import cx.rain.mc.diggus_maximus_forge.ExcavateHolder;
import cx.rain.mc.diggus_maximus_forge.config.ConfigHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayDeque;
import java.util.Deque;

public class Excavate {
    private final BlockPos startPos;
    private final ServerPlayer player;
    private final ResourceKey<Block> startId;
    private final Item startTool;
    private int mined = 0;
    private final Level level;

    private final Holder.Reference<Block> startBlock;
    private final Deque<BlockPos> points = new ArrayDeque<>();

    private final Direction facing;
    private int shapeSelection = -1;

    public Excavate(BlockPos pos, ResourceLocation startId, ServerPlayer player, Direction facing) {
        this.startPos = pos;
        this.player = player;
        this.level = player.getCommandSenderWorld();
        this.startId = ResourceKey.create(Registries.BLOCK, startId);

        this.startBlock = ForgeRegistries.BLOCKS.getDelegate(this.startId).orElse(null);

        this.startTool = player.getMainHandItem().getItem();
        this.facing = facing;
    }

    public void startExcavate(int shapeSelection) {
        this.shapeSelection = shapeSelection;
        forceExcavateAt(startPos);
        if (startBlock == null
                || (startBlock.is(startId) && ExcavateHelper.isBlockBlocked(startBlock))) {
            return;
        }

        ExcavateHolder.startExcavate(player);
        while (!points.isEmpty()) {
            spread(points.remove());
        }
        ExcavateHolder.stopExcavate(player);
    }

    private void spread(BlockPos pos) {
        for (BlockPos dirPos : ExcavateTypes.getSpreadType(shapeSelection, facing, startPos, pos)) {
            if (ExcavateHelper.isValidPos(dirPos)) {
                excavateAt(pos.offset(dirPos));
            }
        }
    }

    private void excavateAt(BlockPos pos) {
        if (mined >= ConfigHelper.getConfig().maxMineBlocks.get()) {
            return;
        }
        var block = ExcavateHelper.getBlockAt(level, pos);
        if (!block.isAir()
                && ExcavateHelper.isTheSameBlock(startBlock, block, shapeSelection)
                && ExcavateHelper.canMine(player, startTool, level, startPos, pos)
                && isExcavatingAllowed(pos)) {
            forceExcavateAt(pos);
        }
    }

    private boolean isExcavatingAllowed(BlockPos pos) {
        return player.gameMode.destroyBlock(pos);
    }

    private void forceExcavateAt(BlockPos pos) {
        points.add(pos);
        mined++;
        if (ConfigHelper.getConfig().autoPickup.get()) {
            ExcavateHelper.pickupDrops(level, pos, player);
        }
    }
}
