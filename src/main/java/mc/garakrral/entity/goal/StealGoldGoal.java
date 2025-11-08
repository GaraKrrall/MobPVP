package mc.garakrral.entity.goal;

import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.List;

public class StealGoldGoal extends Goal {
    private final PathAwareEntity mob;
    private final double speed;
    private BlockPos targetBlock;
    private Vec3d targetItemPos;
    private ItemEntity targetItem;
    private int cooldown = 0;

    public StealGoldGoal(PathAwareEntity mob, double speed) {
        this.mob = mob;
        this.speed = speed;
        // Sadece MOVE kontrolünü al, LOOK’u alma — diğer goal’lar çatışmasın
        this.setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        if (cooldown > 0) {
            cooldown--;
            return false;
        }

        World world = mob.getWorld();

        // 🔸 Öncelikle yerdeki altın eşyalar (bar veya blok)
        List<ItemEntity> items = world.getEntitiesByClass(
                ItemEntity.class,
                new Box(mob.getBlockPos()).expand(8.0D),
                item -> item.getStack().isOf(Items.GOLD_INGOT) || item.getStack().isOf(Items.GOLD_BLOCK)
        );

        if (!items.isEmpty()) {
            targetItem = items.get(0);
            targetItemPos = targetItem.getPos();
            targetBlock = null;
            return true;
        }


        // 🔸 Yakındaki altın bloklara bak
        BlockPos mobPos = mob.getBlockPos();
        for (BlockPos pos : BlockPos.iterateOutwards(mobPos, 5, 2, 5)) {
            if (world.getBlockState(pos).isOf(Blocks.GOLD_BLOCK)) {
                targetBlock = pos.toImmutable();
                targetItemPos = null;
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean shouldContinue() {
        return (targetItemPos != null || targetBlock != null);
    }

    @Override
    public void start() {
        moveToTarget();
    }

    private void moveToTarget() {
        if (targetItemPos != null) {
            mob.getNavigation().startMovingTo(targetItemPos.x, targetItemPos.y, targetItemPos.z, speed);
        } else if (targetBlock != null) {
            mob.getNavigation().startMovingTo(targetBlock.getX(), targetBlock.getY(), targetBlock.getZ(), speed);
        }
    }

    @Override
    public void tick() {
        if (targetItem != null && targetItem.isAlive()) {
            double distance = mob.getPos().distanceTo(targetItem.getPos());
            if (distance < 1.5D) {
                mob.swingHand(Hand.MAIN_HAND);
                mob.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);

                // 🔸 Item’ı gerçekten “çal”
                targetItem.discard(); // itemi yok et (çalındı)
                stopStealing();
            }
        } else if (targetBlock != null) {
            double distance = mob.getPos().distanceTo(Vec3d.ofCenter(targetBlock));
            if (distance < 2.0D) {
                mob.swingHand(Hand.MAIN_HAND);
                mob.playSound(SoundEvents.BLOCK_METAL_BREAK, 1.0F, 1.0F);
                mob.getWorld().breakBlock(targetBlock, true, mob);
                stopStealing();
            }
        }

    }

    private void stopStealing() {
        targetItem = null;
        targetItemPos = null;
        targetBlock = null;
        cooldown = 20 + mob.getRandom().nextBetween(0, 20); // 1–2 saniye bekleme
        mob.getNavigation().stop();
    }

    @Override
    public void stop() {
        mob.getNavigation().stop();
        targetItemPos = null;
        targetBlock = null;
    }
}
