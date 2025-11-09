package mc.garakrral.mixin;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.village.raid.Raid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Raid.class)
public interface RaidAccessor {
    @Accessor("world")
    ServerWorld getWorld();
}
