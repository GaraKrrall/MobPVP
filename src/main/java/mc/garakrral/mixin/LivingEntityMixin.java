package mc.garakrral.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Inject(method = "damage", at = @At("HEAD"))
    private void mobpvp$onZombieHit(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (!(entity instanceof ServerPlayerEntity player)) return;
        if (!(source.getAttacker() instanceof ZombieEntity)) return;

        // Geçici yeşil açlık barı efekti
        player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.HUNGER,  // efekt
                20,                    // süre (tick) = 1 saniye
                1                     // seviye (0 = level I)
        ));

        // 1 saniye sonra açlık azalt
        scheduler.schedule(() -> {
            if (player.isAlive()) {
                int current = player.getHungerManager().getFoodLevel();
                player.getHungerManager().setFoodLevel(Math.max(0, current - 3));
            }
        }, 1, TimeUnit.SECONDS);
    }
}
