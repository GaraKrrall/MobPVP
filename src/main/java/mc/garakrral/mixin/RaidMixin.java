package mc.garakrral.mixin;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.raid.Raid;

import mc.garakrral.entity.mob.HunterEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Raid.class)
public abstract class RaidMixin {

    @Inject(method = "spawnNextWave", at = @At("TAIL"))
    private void spawnHuntersEveryWave(BlockPos pos, CallbackInfo ci) {
        Raid raid = (Raid) (Object) this;
        ServerWorld level = (ServerWorld) ((RaidAccessor) raid).getWorld(); // dünyayı al
        int wave = raid.getGroupsSpawned();
        int hunterCount = 2 + level.getDifficulty().getId();

        for (int i = 0; i < hunterCount; i++) {
            HunterEntity hunter = mc.garakrral.entity.EntityType.HUNTER.create(level);
            if (hunter != null) {
                hunter.setPos(
                        pos.getX() + level.random.nextInt(6) - 3,
                        pos.getY(),
                        pos.getZ() + level.random.nextInt(6) - 3
                );
                level.spawnEntity(hunter);
                hunter.setRaid(raid);
                raid.addRaider(wave, hunter, pos, true); // joinRaid yerine bu kullanılmalı

                hunter.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.CROSSBOW));
                hunter.getInventory().setStack(0, new ItemStack(Items.ARROW, 64));
                hunter.getInventory().setStack(1, new ItemStack(Items.ARROW, 64));
            }
        }
    }
}
