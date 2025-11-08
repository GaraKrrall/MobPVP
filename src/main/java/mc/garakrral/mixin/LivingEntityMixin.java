package mc.garakrral.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;

import com.kaplanlib.api.scheduler.ServerScheduler;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "damage", at = @At("HEAD"))
    private void mobpvp$onZombieHit(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (!(entity instanceof ServerPlayerEntity player)) return;
        if (!(source.getAttacker() instanceof ZombieEntity)) return;

        if (player.isUsingItem() && player.getActiveItem().isOf(Items.SHIELD)) {
            return;
        }

        player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.HUNGER,
                20, //1sn
                1
        ));
        ServerScheduler.schedule(20, () -> {
            if (player.isAlive()) {
                int current = player.getHungerManager().getFoodLevel();
                player.getHungerManager().setFoodLevel(Math.max(0, current - 3));
            }
        });
    }
}
