package cx.rain.mc.diggus_maximus_forge.mixin;

import cx.rain.mc.diggus_maximus_forge.ExcavateHolder;
import cx.rain.mc.diggus_maximus_forge.config.ConfigHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public class MixinCancelDurability {

    @Inject(method = "hurtAndBreak", at = @At(value = "HEAD"), cancellable = true)
    private <T extends LivingEntity> void beforeHurtAndBreak(int amount, T entity, Consumer<T> onBroken, CallbackInfo ci) {
        if (entity instanceof Player player
                && ExcavateHolder.isExcavating(player)
                && !ConfigHelper.getConfig().damagesTool.get()) {
            ci.cancel();
        }
    }
}
