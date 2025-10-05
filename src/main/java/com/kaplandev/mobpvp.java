package com.kaplandev;

import com.kaplandev.block.Blocks;
import com.kaplandev.command.ModCommands;
import com.kaplandev.effect.LevelEffectHandler;
import com.kaplandev.enchantment.EnchantmentGet;
import com.kaplandev.enchantment.EnchantmentsAndEffects;
import com.kaplandev.enchantment.effect.MagmatizationEffect;
import com.kaplandev.entity.EntityRegister;
import com.kaplandev.entity.boss.BulwarkEntity;
import com.kaplandev.event.level.player.PlayerLevelEvents;
import com.kaplandev.event.totem.IRON_BLOCK;
import com.kaplandev.gen.WorldGen;
import com.kaplandev.item.Items;
import com.kaplandev.item.group.ItemGroups;
import com.kaplandev.level.BossVariants;
import com.kaplandev.level.LevelAssigner;
import com.kaplandev.level.MobLevelRegistry;
import com.kaplandev.level.player.PlayerLevelData;
import com.kaplandev.level.player.PlayerLevelSaveHandler;
import com.kaplandev.trade.Trades;
import com.kaplandev.villager.Villagers;
import com.kaplanlib.api.PluginRegistry;
import com.kaplanlib.api.annotation.KaplanBedwars;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.kaplanlib.util.path.Paths.MOBPVP;
import static com.kaplanlib.util.path.Paths.STARTUP_SOUND_EVENT;
import static com.kaplanlib.util.path.Paths.STARTUP_SOUND_ID;

@KaplanBedwars
@Mod(MOBPVP)
public final class mobpvp {

    public static final Map<String, Integer[]> LEVEL_RANGES = new HashMap<>();

    public mobpvp() {
        MinecraftForge.EVENT_BUS.register(this);
        onInitialize();
    }

    public void onInitialize() {
        EnchantmentsAndEffects.registerModEnchantmentEffects();
        EnchantmentGet.init();
        WorldGen.register();
        Blocks.init();
        Villagers.registerVillagers();
        Trades.init();
        EntityRegister.register();
        Items.init();
        ItemGroups.init();
        PluginRegistry.callOnLoad();
    }

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            PluginRegistry.callOnClose();
            System.out.println("[MobPVP] Mod kapanıyor...");
        }));
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        ModCommands.register(event.getDispatcher());
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        MinecraftServer server = event.getServer();
        Trades.register();
        PlayerLevelSaveHandler.init(server);
    }

    @SubscribeEvent
    public void onServerStopped(ServerStoppedEvent event) {
        PlayerLevelSaveHandler.save();
    }

    @SubscribeEvent
    public void onLevelTick(TickEvent.LevelTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.level instanceof ServerLevel world)) return;

        MagmatizationEffect.tick(world);
        for (var player : world.players()) {
            UUID uuid = player.getUUID();
            int level = PlayerLevelData.getLevel(uuid);
            PlayerLevelEvents.applyStatBonus(player, level);
        }
    }

    @SubscribeEvent
    public void onEntityJoin(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof LivingEntity living)) return;
        if (living instanceof ServerPlayer) return;
        if (living instanceof BulwarkEntity) return;
        if (living.hasCustomName()) return;

        String mobId;
        try {
            ResourceLocation rl = ForgeRegistries.ENTITY_TYPES.getKey(living.getType());
            mobId = rl == null ? living.getType().toString() : rl.getPath();
        } catch (Throwable t) {
            mobId = living.getType().toString();
        }

        Integer[] range = MobLevelRegistry.getLevelRangeOrDefault(mobId, new Integer[]{1, 40});
        int level = range[0] + ThreadLocalRandom.current().nextInt(range[1] - range[0] + 1);

        LevelAssigner.assignLevel(living, mobId, level);

        if (living instanceof Zombie zombie) {
            if (BossVariants.tryAssignCustomZombieBoss(zombie, living, event.getLevel())) return;
        }

        if (living.getAttribute(Attributes.MAX_HEALTH) != null) {
            double base = living.getAttribute(Attributes.MAX_HEALTH).getBaseValue();
            double extra = Math.log(level + 1) * 10;
            double total = base + extra;
            living.getAttribute(Attributes.MAX_HEALTH).setBaseValue(total);
            living.setHealth((float) total);
        }

        if (living.getAttribute(Attributes.ATTACK_DAMAGE) != null) {
            double dmg = living.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue();
            living.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(dmg + level * 0.3);
        }

        LevelEffectHandler.applyEffects(living, level);

        if (living instanceof EnderMan enderman && level > 8) {
            if (event.getLevel() instanceof ServerLevel serverWorld) {
                List<ServerPlayer> players = serverWorld.players();
                if (!players.isEmpty()) {
                    ServerPlayer nearest = players.stream().min(Comparator.comparingDouble(p -> p.distanceToSqr(living))).orElse(null);
                    if (nearest != null) {
                        try {
                            enderman.setTarget(nearest);
                        } catch (Throwable ignored) {}
                    }
                }
            }
        }

        if (living instanceof Zombie z && ThreadLocalRandom.current().nextFloat() < 0.05f) {
            LivingEntity finalEntity = z;
            new Thread(() -> {
                try {
                    Thread.sleep(3000);
                    if (!finalEntity.isRemoved() && finalEntity.level() instanceof ServerLevel serverWorld) {
                        MinecraftServer server = serverWorld.getServer();
                        if (server != null) {
                            server.execute(() -> {
                                try {
                                    serverWorld.explode(null, finalEntity.getX(), finalEntity.getY(), finalEntity.getZ(), 2.5f, ServerLevel.ExplosionInteraction.DESTROY);
                                    finalEntity.discard();
                                } catch (Throwable ignored) {}
                            });
                        }
                    }
                } catch (InterruptedException ignored) {
                }
            }).start();
        }
    }

    @SubscribeEvent
    public void onLevelEndTickForDisplay(TickEvent.LevelTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.level instanceof ServerLevel serverLevel)) return;

        for (var entity : serverLevel.getEntities().getAll()) {
            if (!(entity instanceof LivingEntity living)) continue;
            if (!living.hasCustomName()) continue;
            if (!LevelAssigner.hasLevel(living)) continue;
            LevelAssigner.updateDisplay(living);
        }
    }

    @SubscribeEvent
    public void onLootTableLoad(net.minecraftforge.event.LootTableLoadEvent event) {
        ResourceLocation id = event.getName();
        if (id == null) return;
        if (!"minecraft".equals(id.getNamespace())) return;
        String path = id.getPath();
        if (!path.startsWith("entities/")) return;
        String mobId = path.replaceFirst("entities/", "");
        Integer[] range = LEVEL_RANGES.getOrDefault(mobId, new Integer[]{1, 1});
        int level = range[1];

        List<LootItem.Builder<?>> common = new ArrayList<>();
        List<LootItem.Builder<?>> rare = new ArrayList<>();
        List<LootItem.Builder<?>> guaranteed = new ArrayList<>();
        List<LootItem.Builder<?>> veryCommon = new ArrayList<>();

        boolean isPassiveFood = mobId.equals("sheep") || mobId.equals("cow") || mobId.equals("chicken") || mobId.equals("pig");

        if (!isPassiveFood) {
            if (level >= 2) common.add(LootItem.lootTableItem(net.minecraft.world.item.Items.IRON_INGOT));
            if (level >= 4) common.add(LootItem.lootTableItem(net.minecraft.world.item.Items.BOW));
            if (level >= 6) common.add(LootItem.lootTableItem(net.minecraft.world.item.Items.GOLD_INGOT));
            if (level >= 10) common.add(LootItem.lootTableItem(net.minecraft.world.item.Items.DIAMOND));
            if (level >= 15) common.add(LootItem.lootTableItem(net.minecraft.world.item.Items.DIAMOND_CHESTPLATE));
            if (level >= 50) common.add(LootItem.lootTableItem(net.minecraft.world.item.Items.NETHERITE_SCRAP));

            if (level >= 10) {
                rare.add(LootItem.lootTableItem(net.minecraft.world.item.Items.ENCHANTED_GOLDEN_APPLE));
                rare.add(LootItem.lootTableItem(net.minecraft.world.item.Items.NETHERITE_INGOT));
                rare.add(LootItem.lootTableItem(net.minecraft.world.item.Items.TOTEM_OF_UNDYING));
            }
        }

        try {
            var table = event.getTable();
            if (table != null) {
                if (!common.isEmpty()) {
                    LootPool.Builder pool = LootPool.lootPool();
                    for (var e : common) pool.add(e);
                    pool.when(LootItemRandomChanceCondition.randomChance(0.75f));
                    table.addPool(pool.build());
                }
                if (!rare.isEmpty()) {
                    LootPool.Builder rpool = LootPool.lootPool();
                    for (var e : rare) rpool.add(e);
                    rpool.when(LootItemRandomChanceCondition.randomChance(0.35f));
                    table.addPool(rpool.build());
                }
            }
        } catch (Throwable ignored) {}
    }
}
