package cx.rain.mc.diggus_maximus_forge.mixin.client;

import cx.rain.mc.diggus_maximus_forge.config.ConfigHelper;
import cx.rain.mc.diggus_maximus_forge.key.ModKeyMappings;
import cx.rain.mc.diggus_maximus_forge.networking.ExcavatePacket;
import cx.rain.mc.diggus_maximus_forge.networking.ModNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public abstract class MixinClientPlayerInteractionManager {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "destroyBlock", at = @At(value = "HEAD"))
    private void beforeDestroyBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        var config = ConfigHelper.getConfig();
        if (!config.enabled.get()) {
            return;
        }

        {
            var pressed = config.invertActivation.get() ^ ModKeyMappings.EXCAVATE.isDown();
            if (pressed) {
                ModNetworking.sendToServer(new ExcavatePacket(pos,
                        ForgeRegistries.BLOCKS.getKey(minecraft.level.getBlockState(pos).getBlock()), null, -1));
                return;
            }
        }

        if (config.shapeEnabled.get()) {
            var pressed = config.invertActivation.get() ^ ModKeyMappings.SHAPED.isDown();
            if (pressed) {
                Direction facing = null;
                HitResult result = minecraft.player.pick(10, 0, false);
                if (result.getType() == HitResult.Type.BLOCK) {
                    facing = ((BlockHitResult) result).getDirection();
                }
                int shape = config.currentShape.get().ordinal();
                ModNetworking.sendToServer(new ExcavatePacket(pos,
                        ForgeRegistries.BLOCKS.getKey(minecraft.level.getBlockState(pos).getBlock()), facing, shape));
            }
        }
    }
}