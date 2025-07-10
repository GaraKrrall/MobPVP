package com.kaplandev.entity.mobpvp.goal;

import com.kaplandev.entity.mobpvp.MiniIronGolemEntity;

import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;
import java.util.List;

public class FindIngotAndHealGolemGoal extends Goal {

    private final MiniIronGolemEntity miniGolem;
    private ItemEntity targetIngotEntity;
    private IronGolemEntity targetGolem;
    private BlockPos targetChest;
    private boolean usingChest = false;
    private float followStartHealth = -1;


    private Phase phase = Phase.SEARCH;

    private enum Phase {
        SEARCH, PICKUP, MOVE_TO_GOLEM, HEAL, FOLLOW
    }

    public FindIngotAndHealGolemGoal(MiniIronGolemEntity golem) {
        this.miniGolem = golem;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        return true;
    }

    @Override
    public boolean shouldContinue() {
        return true;
    }

    @Override
    public void tick() {
        switch (phase) {
            case SEARCH -> {
                targetIngotEntity = findNearbyIngot();
                if (targetIngotEntity != null) {
                    usingChest = false;
                    phase = Phase.PICKUP;
                    return;
                }

                targetChest = findChestWithIngot();
                if (targetChest != null) {
                    usingChest = true;
                    phase = Phase.PICKUP;
                    return;
                }

                // Yeni: Yakındaki HERHANGİ bir demir golem varsa onu takip et
                List<IronGolemEntity> golems = miniGolem.getWorld().getEntitiesByClass(IronGolemEntity.class,
                        miniGolem.getBoundingBox().expand(10),
                        g -> !(g instanceof MiniIronGolemEntity)); // minikleri alma

                if (!golems.isEmpty()) {
                    targetGolem = golems.get(0);
                    phase = Phase.FOLLOW;
                }
            }

            case FOLLOW -> {
                if (targetGolem != null && targetGolem.isAlive()) {
                    if (followStartHealth < 0) {
                        followStartHealth = targetGolem.getHealth(); // Başlangıç sağlığı kaydet
                    }

                    // Golemin canı azaldıysa yeniden külçe ara!
                    if (targetGolem.getHealth() < followStartHealth) {
                        phase = Phase.SEARCH;
                        return;
                    }

                    if (miniGolem.squaredDistanceTo(targetGolem) > 4.0) {
                        miniGolem.getNavigation().startMovingTo(targetGolem, 0.35);
                    } else {
                        miniGolem.getNavigation().stop();
                    }

                } else {
                    phase = Phase.SEARCH;
                }
            }




            case PICKUP -> {
                List<IronGolemEntity> golems = miniGolem.getWorld().getEntitiesByClass(IronGolemEntity.class,
                        miniGolem.getBoundingBox().expand(10),
                        g -> !(g instanceof MiniIronGolemEntity) && g.getHealth() < g.getMaxHealth());

                if (golems.isEmpty()) {
                    reset();
                    return;
                }

                targetGolem = golems.get(0);

                if (usingChest) {
                    miniGolem.getNavigation().startMovingTo(targetChest.getX() + 0.5, targetChest.getY(), targetChest.getZ() + 0.5, 0.35);
                    if (miniGolem.getBlockPos().isWithinDistance(targetChest, 1.5)) {
                        BlockEntity be = miniGolem.getWorld().getBlockEntity(targetChest);
                        if (be instanceof ChestBlockEntity chest) {
                            Inventory inv = chest;
                            for (int i = 0; i < inv.size(); i++) {
                                ItemStack stack = inv.getStack(i);
                                if (stack.isOf(Items.IRON_INGOT)) {
                                    stack.decrement(1); // külçeyi azalt
                                    inv.setStack(i, stack.isEmpty() ? ItemStack.EMPTY : stack); // boşsa temizle
                                    miniGolem.setStackInHand(Hand.MAIN_HAND, new ItemStack(Items.IRON_INGOT));
                                    phase = Phase.MOVE_TO_GOLEM;
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    if (targetIngotEntity != null && targetIngotEntity.isAlive()) {
                        miniGolem.getNavigation().startMovingTo(targetIngotEntity, 0.35);
                        if (miniGolem.squaredDistanceTo(targetIngotEntity) < 1.5) {
                            ItemStack stack = targetIngotEntity.getStack();
                            if (stack.getCount() > 1) {
                                stack.decrement(1);
                                targetIngotEntity.setStack(stack);
                            } else {
                                targetIngotEntity.discard();
                            }
                            miniGolem.setStackInHand(Hand.MAIN_HAND, new ItemStack(Items.IRON_INGOT));
                            phase = Phase.MOVE_TO_GOLEM;
                        }
                    } else {
                        reset();
                    }
                }
            }

            case MOVE_TO_GOLEM -> {
                if (targetGolem != null && targetGolem.isAlive()) {
                    miniGolem.getNavigation().startMovingTo(targetGolem, 0.35);
                    if (miniGolem.squaredDistanceTo(targetGolem) < 2.5) {
                        phase = Phase.HEAL;
                    }
                } else {
                    miniGolem.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
                    reset();
                }
            }

            case HEAL -> {
                if (targetGolem != null && targetGolem.isAlive()) {
                    // Golemin canı tam değilse iyileştir
                    if (targetGolem.getHealth() < targetGolem.getMaxHealth()) {
                        targetGolem.heal(8.0F);
                        miniGolem.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
                        miniGolem.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 0.4F, 1.1F);

                        // Eğer hala canı tam değilse, tekrar külçe aramaya gönder
                        if (targetGolem.getHealth() < targetGolem.getMaxHealth()) {
                            phase = Phase.SEARCH;
                            return;
                        }
                    }
                    // Canı tam ise takip et
                    phase = Phase.FOLLOW;
                    followStartHealth = targetGolem.getHealth();
                } else {
                    reset(); // Golem ölmüşse sıfırla
                }
            }

        }
    }

    private ItemEntity findNearbyIngot() {
        List<ItemEntity> items = miniGolem.getWorld().getEntitiesByClass(ItemEntity.class,
                miniGolem.getBoundingBox().expand(8),
                e -> e.getStack().isOf(Items.IRON_INGOT));
        return items.isEmpty() ? null : items.get(0);
    }

    private BlockPos findChestWithIngot() {
        BlockPos origin = miniGolem.getBlockPos();
        BlockPos.Mutable check = new BlockPos.Mutable();

        for (int x = -8; x <= 8; x++) {
            for (int y = -2; y <= 2; y++) {
                for (int z = -8; z <= 8; z++) {
                    check.set(origin.getX() + x, origin.getY() + y, origin.getZ() + z);
                    if (miniGolem.getWorld().getBlockState(check).isOf(Blocks.TRAPPED_CHEST)) {
                        BlockEntity be = miniGolem.getWorld().getBlockEntity(check);
                        if (be instanceof ChestBlockEntity chest) {
                            for (int i = 0; i < chest.size(); i++) {
                                if (chest.getStack(i).isOf(Items.IRON_INGOT)) {
                                    return check.toImmutable();
                                }
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    private void reset() {
        targetIngotEntity = null;
        targetGolem = null;
        targetChest = null;
        usingChest = false;
        phase = Phase.SEARCH;
        followStartHealth = -1;

    }
}
