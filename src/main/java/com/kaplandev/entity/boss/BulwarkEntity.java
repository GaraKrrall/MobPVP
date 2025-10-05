package com.kaplandev.entity.boss;

import com.kaplandev.entity.goal.boss.BreakBlockGoal;
import com.kaplandev.entity.goal.boss.FireballAttackGoal;
import com.kaplandev.entity.goal.boss.SummonZombieGoal;
import com.kaplandev.entity.goal.boss.TntSpawnGoal;
import com.kaplandev.entity.mob.MadZombieEntity;


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

        // Öncelikli olarak yüzmeyi öğreniyor
        this.goalSelector.add(0, new SwimGoal(this));

        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.2, false));
        this.goalSelector.add(2, new FireballAttackGoal(this));
        this.goalSelector.add(3, new TntSpawnGoal(this));
        this.goalSelector.add(4, new SummonZombieGoal(this));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(7, new LookAroundGoal(this));
        this.goalSelector.add(8, new BreakBlockGoal(this));

        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PathAwareEntity.class, 10, true, true,
                entity -> entity != this && !(entity instanceof MadZombieEntity) && !(entity instanceof BulwarkEntity)));
        this.targetSelector.add(3, new RevengeGoal(this));
    }


    @Override
    public void tick() {
        super.tick();
        // System.out.println("Boss tickliyor! Health: " + this.getHealth());
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
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 300.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3);
    }


    @Override
    protected void dropLoot(DamageSource source, boolean causedByPlayer) {
        super.dropLoot(source, causedByPlayer);

        if (!this.getWorld().isClient()) {
            this.dropStack(new ItemStack(Items.DIAMOND, 8));
            this.dropStack(new ItemStack(Items.NETHERITE_SCRAP, 1));
            this.dropStack(new ItemStack(com.kaplandev.item.Items.COPPER_STICK, 2));
            this.dropStack(new ItemStack(com.kaplandev.item.Items.KALP_ITEM, 2));
        }

    }


}
