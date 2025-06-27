package com.kaplandev.entity.boss;

import com.kaplandev.entity.boss.goal.FireballAttackGoal;
import com.kaplandev.entity.boss.goal.SummonZombieGoal;
import com.kaplandev.entity.boss.goal.TntSpawnGoal;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.world.World;

public class BulwarkEntity extends PathAwareEntity {
    private final ServerBossBar bossBar = new ServerBossBar(
            Text.literal("§c🔥 BOSS: Bulwark 🔥"),
            BossBar.Color.RED,
            BossBar.Style.PROGRESS
    );

    public BulwarkEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        super.initGoals();

        this.goalSelector.add(0, new MeleeAttackGoal(this, 1.2, false)); // Vurmayı öğrendi
        this.goalSelector.add(1, new FireballAttackGoal(this));          // Fireball'ı unutma
        this.goalSelector.add(2, new TntSpawnGoal(this));
        this.goalSelector.add(3, new SummonZombieGoal(this));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(6, new LookAroundGoal(this));

        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true)); // Sana yönelsin
    }

    @Override
    public void tick() {
        super.tick();
        System.out.println("Boss tickliyor! Health: " + this.getHealth());
        bossBar.setPercent(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public void onStartedTrackingBy(ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);
        bossBar.addPlayer(player);
    }

    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        bossBar.removePlayer(player);
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    public ServerBossBar getBossBar() {
        return bossBar;
    }
    public static DefaultAttributeContainer.Builder createAttributes() {
        return ZombieEntity.createZombieAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 80.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3);
    }
}
