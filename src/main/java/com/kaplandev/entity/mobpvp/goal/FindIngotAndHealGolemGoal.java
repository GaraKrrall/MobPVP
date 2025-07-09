package com.kaplandev.entity.mobpvp.goal;

import com.kaplandev.entity.mobpvp.MiniIronGolemEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;

import java.util.EnumSet;
import java.util.List;

public class FindIngotAndHealGolemGoal extends Goal {

    private final MiniIronGolemEntity miniGolem;
    private ItemEntity targetIngot;
    private IronGolemEntity targetGolem;
    private Phase phase = Phase.SEARCH;

    private enum Phase {
        SEARCH, PICKUP, MOVE_TO_GOLEM, HEAL
    }

    public FindIngotAndHealGolemGoal(MiniIronGolemEntity golem) {
        this.miniGolem = golem;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        return true;  // Sürekli kontrol etsin
    }

    @Override
    public void tick() {
        switch (phase) {
            case SEARCH -> {
                List<ItemEntity> items = miniGolem.getWorld().getEntitiesByClass(ItemEntity.class,
                        miniGolem.getBoundingBox().expand(10), e -> e.getStack().isOf(Items.IRON_INGOT));

                if (!items.isEmpty()) {
                    targetIngot = items.get(0);
                    phase = Phase.PICKUP;
                }
            }
            case PICKUP -> {
                if (targetIngot != null && targetIngot.isAlive()) {

                    // Hedef demir golemi ŞİMDİ bul, çünkü boşa külçe almak istemiyoruz
                    List<IronGolemEntity> golems = miniGolem.getWorld().getEntitiesByClass(IronGolemEntity.class,
                            miniGolem.getBoundingBox().expand(10),
                            g -> !(g instanceof MiniIronGolemEntity) && g.getHealth() < g.getMaxHealth());

                    if (golems.isEmpty()) {
                        // Gerek yok, külçeyi alma
                        reset();
                        return;
                    }

                    targetGolem = golems.get(0);  // Hedefi daha burada belirle

                    miniGolem.getNavigation().startMovingTo(targetIngot, 0.35);
                    if (miniGolem.squaredDistanceTo(targetIngot) < 1.5) {
                        ItemStack stack = targetIngot.getStack();
                        if (stack.getCount() > 1) {
                            stack.decrement(1);
                            targetIngot.setStack(stack);
                        } else {
                            targetIngot.discard();
                        }

                        miniGolem.setStackInHand(Hand.MAIN_HAND, new ItemStack(Items.IRON_INGOT));
                        phase = Phase.MOVE_TO_GOLEM;
                    }
                } else {
                    reset();
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
                if (targetGolem != null && targetGolem.isAlive() && targetGolem.getHealth() < targetGolem.getMaxHealth()) {
                    targetGolem.heal(4.0F);
                    miniGolem.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
                    miniGolem.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 0.4F, 1.1F);
                } else {
                    // Golem son anda iyileştirilmişse külçeyi harcama
                    miniGolem.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
                }
                reset();
            }

        }
    }

    private void reset() {
        targetIngot = null;
        targetGolem = null;
        phase = Phase.SEARCH;
    }

    @Override
    public boolean shouldContinue() {
        return true;
    }
}
