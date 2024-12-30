package cx.rain.mc.diggus_maximus_forge.mixin;

import cx.rain.mc.diggus_maximus_forge.ExcavateHolder;
import cx.rain.mc.diggus_maximus_forge.config.ConfigHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Block.class)
public class MixinBlock {

    @Redirect(method = "playerDestroy", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;causeFoodExhaustion(F)V"))
    private void diggus$redirect$playerDestroy$causeFoodExhaustion(Player player, float exhaustion) {
        if (!ExcavateHolder.isExcavating(player)) {
            player.causeFoodExhaustion(exhaustion);
            return;
        }
        var config = ConfigHelper.getConfig();
        if (!config.playerExhaustion.get()) {
            return;
        }
        player.causeFoodExhaustion(exhaustion * config.exhaustionMultiplier.get().floatValue());
    }
}
