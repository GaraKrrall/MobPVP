package mc.garakrral;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import mc.garakrral.block.Blocks;
import mc.garakrral.command.CommandManager;
import mc.garakrral.effect.LevelEffectHandler;
import mc.garakrral.enchantment.EnchantmentGet;
import mc.garakrral.enchantment.EnchantmentsAndEffects;
import mc.garakrral.enchantment.effect.MagmatizationEffect;
import mc.garakrral.entity.EntityRegister;
import mc.garakrral.entity.boss.BulwarkEntity;
import mc.garakrral.event.Events;
import mc.garakrral.event.level.player.PlayerLevelEvents;
import mc.garakrral.gen.WorldGen;
import mc.garakrral.item.ItemType;
import mc.garakrral.item.Items;
import mc.garakrral.item.group.ItemGroups;
import mc.garakrral.level.BossVariants;
import mc.garakrral.level.LevelAssigner;
import mc.garakrral.level.MobLevelRegistry;
import mc.garakrral.level.player.PlayerLevelData;
import mc.garakrral.level.player.PlayerLevelSaveHandler;
import mc.garakrral.registry.DataRegister;
import mc.garakrral.registry.HandlerRegister;
import mc.garakrral.scheduler.ServerScheduler;
import mc.garakrral.trade.Trades;
import mc.garakrral.villager.Villagers;

import com.kaplanlib.api.PluginRegistry;
import com.kaplanlib.api.annotation.KaplanBedwars;

import static com.kaplanlib.util.path.Paths.MOBPVP;
import static com.kaplanlib.util.path.Paths.STARTUP_SOUND_EVENT;
import static com.kaplanlib.util.path.Paths.STARTUP_SOUND_ID;


@KaplanBedwars
public final class mobpvp implements ModInitializer {

    public static final Map<String, Integer[]> LEVEL_RANGES = new HashMap<>();
    public static final String MOD_ID = MOBPVP;
    public static final String MODID = MOBPVP;

    @Override
    public void onInitialize() {
        DataRegister.registerAllData();
        ServerScheduler.init();
        HandlerRegister.registerHandlers();
        EnchantmentsAndEffects.registerModEnchantmentEffects();
        EnchantmentGet.init();
        WorldGen.register();
        Blocks.init();
        Villagers.registerVillagers();
        Trades.init();
        EntityRegister.register();
        Items.init();
        ItemGroups.init();
        Registry.register(Registries.SOUND_EVENT, STARTUP_SOUND_ID, STARTUP_SOUND_EVENT);
        PluginRegistry.callOnLoad();
        Events.register();
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            CommandManager.register(dispatcher);
        });
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            Trades.register(); // enchantment registry bu noktada hazır
            PlayerLevelSaveHandler.init(server);
        });

        ServerLifecycleEvents.SERVER_STOPPING.register(server -> PlayerLevelSaveHandler.save());
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            MagmatizationEffect.tick(world);
            for (var player : world.getPlayers()) {
                UUID uuid = player.getUuid();
                int level = PlayerLevelData.getLevel(uuid);
                PlayerLevelEvents.applyStatBonus(player, level);
            }
        });

        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(entity instanceof LivingEntity living) || entity instanceof ServerPlayerEntity || entity instanceof BulwarkEntity)
                return;
            if (living.hasCustomName()) return;


            String mobId = Registries.ENTITY_TYPE.getId(entity.getType()).getPath();
            Integer[] range = MobLevelRegistry.getLevelRangeOrDefault(mobId, new Integer[]{1, 40});
            int level = range[0] + entity.getRandom().nextInt(range[1] - range[0] + 1);


            LevelAssigner.assignLevel(living, mobId, level);


            if (entity instanceof ZombieEntity zombie) {
                if (BossVariants.tryAssignCustomZombieBoss(zombie, living, world)) return;
            }

            if (living.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH) != null) {
                double base = living.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getBaseValue();
                double extra = Math.log(level + 1) * 10; // Daha dengeli
                double total = base + extra;
                living.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(total);
                living.setHealth((float) total);
            }


            if (living.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE) != null) {
                double dmg = living.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).getBaseValue();
                living.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(dmg + level * 0.3);
            }

            LevelEffectHandler.applyEffects(living, level);


            if (entity instanceof EndermanEntity enderman && level > 8) {
                List<ServerPlayerEntity> players = ((ServerWorld) entity.getWorld()).getPlayers();
                if (!players.isEmpty()) {
                    ServerPlayerEntity nearest = players.stream().min(Comparator.comparingDouble(p -> p.squaredDistanceTo(entity))).orElse(null);
                    if (nearest != null) enderman.setTarget(nearest);
                }
            }

            if (entity instanceof ZombieEntity zombie && zombie.getRandom().nextFloat() < 0.05f) {
                LivingEntity finalEntity = zombie;
                new Thread(() -> {
                    try {
                        Thread.sleep(3000);

                        // Minecraft ana thread’ine güvenli şekilde iş teslimi
                        if (!finalEntity.isRemoved() && finalEntity.getWorld() instanceof ServerWorld serverWorld) {
                            serverWorld.getServer().execute(() -> {
                                serverWorld.createExplosion(finalEntity, finalEntity.getX(), finalEntity.getY(), finalEntity.getZ(), 2.5f, World.ExplosionSourceType.MOB);
                                finalEntity.discard();
                            });
                        }
                    } catch (InterruptedException ignored) {
                    }
                }).start();

            }
        });

        LootTableEvents.MODIFY.register((RegistryKey<LootTable> id, LootTable.Builder builder, LootTableSource source) -> {
            if (!"minecraft".equals(id.getValue().getNamespace())) return;
            if (!id.getValue().getPath().startsWith("entities/")) return;
            String mobId = id.getValue().getPath().replace("entities/", "");
            Integer[] range = LEVEL_RANGES.getOrDefault(mobId, new Integer[]{1, 1});
            int level = range[1];
            List<LootPoolEntry> common = new ArrayList<>();
            List<LootPoolEntry> rare = new ArrayList<>();
            List<LootPoolEntry> guaranteed = new ArrayList<>();
            List<LootPoolEntry> veryCommon = new ArrayList<>();

            if (!mobId.equals("sheep") && !mobId.equals("cow") && !mobId.equals("chicken") && !mobId.equals("pig")) {
                if (level >= 2) common.add(ItemEntry.builder(net.minecraft.item.Items.IRON_INGOT).build());
                if (level >= 4) common.add(ItemEntry.builder(net.minecraft.item.Items.BOW).build());
                if (level >= 6) common.add(ItemEntry.builder(net.minecraft.item.Items.GOLD_INGOT).build());
                if (level >= 10) common.add(ItemEntry.builder(net.minecraft.item.Items.DIAMOND).build());
                if (level >= 15) common.add(ItemEntry.builder(net.minecraft.item.Items.DIAMOND_CHESTPLATE).build());
                if (level >= 50) common.add(ItemEntry.builder(net.minecraft.item.Items.NETHERITE_SCRAP).build());

                if (level >= 10) {
                    rare.add(ItemEntry.builder(net.minecraft.item.Items.ENCHANTED_GOLDEN_APPLE).build());
                    rare.add(ItemEntry.builder(net.minecraft.item.Items.NETHERITE_INGOT).build());
                    rare.add(ItemEntry.builder(net.minecraft.item.Items.TOTEM_OF_UNDYING).build());
                }
                if (level >= 100) {
                    rare.add(ItemEntry.builder(net.minecraft.item.Items.NETHER_STAR).build());
                    rare.add(ItemEntry.builder(net.minecraft.item.Items.NETHERITE_CHESTPLATE).build());
                }
                if (level >= 200) {
                    rare.add(ItemEntry.builder(net.minecraft.item.Items.NETHERITE_SWORD).build());
                    rare.add(ItemEntry.builder(net.minecraft.item.Items.SCULK_SENSOR).build());
                }

                if (level == 1000) {
                    guaranteed.add(ItemEntry.builder(net.minecraft.item.Items.NETHERITE_AXE).build());
                    guaranteed.add(ItemEntry.builder(net.minecraft.item.Items.NETHERITE_INGOT).build());
                    guaranteed.add(ItemEntry.builder(net.minecraft.item.Items.NETHERITE_CHESTPLATE).build());
                    guaranteed.add(ItemEntry.builder(net.minecraft.item.Items.NETHERITE_SWORD).build());
                }
                if (mobId.equals("ender_dragon")) {
                    guaranteed.add(ItemEntry.builder(net.minecraft.item.Items.DRAGON_EGG).build());
                    guaranteed.add(ItemEntry.builder(net.minecraft.item.Items.ELYTRA).build());
                    guaranteed.add(ItemEntry.builder(net.minecraft.item.Items.NETHERITE_BLOCK).build());
                    guaranteed.add(ItemEntry.builder(net.minecraft.item.Items.BEACON).build());
                }
                if (mobId.equals("zombie")) {
                    rare.add(ItemEntry.builder(ItemType.KALP_ITEM).build());
                    guaranteed.add(ItemEntry.builder(ItemType.HEARTH_PART).build());
                }
                if (mobId.equals("warden")) {
                    guaranteed.add(ItemEntry.builder(net.minecraft.item.Items.SCULK_SHRIEKER).build());
                    guaranteed.add(ItemEntry.builder(net.minecraft.item.Items.NETHERITE_INGOT).build());
                    guaranteed.add(ItemEntry.builder(net.minecraft.item.Items.NETHERITE_AXE).build());
                    guaranteed.add(ItemEntry.builder(net.minecraft.item.Items.NETHERITE_BOOTS).build());
                }

                veryCommon.add(ItemEntry.builder(net.minecraft.item.Items.IRON_NUGGET).build());
                veryCommon.add(ItemEntry.builder(net.minecraft.item.Items.GOLD_NUGGET).build());
                veryCommon.add(ItemEntry.builder(net.minecraft.item.Items.GUNPOWDER).build());
            } else {
                if (mobId.equals("sheep"))
                    common.add(ItemEntry.builder(net.minecraft.item.Items.COOKED_MUTTON).build());
                if (mobId.equals("cow")) common.add(ItemEntry.builder(net.minecraft.item.Items.COOKED_BEEF).build());
                if (mobId.equals("pig"))
                    common.add(ItemEntry.builder(net.minecraft.item.Items.COOKED_PORKCHOP).build());
                if (mobId.equals("chicken"))
                    common.add(ItemEntry.builder(net.minecraft.item.Items.COOKED_CHICKEN).build());
            }

            if (!guaranteed.isEmpty()) {
                LootPool.Builder gpool = LootPool.builder().rolls(ConstantLootNumberProvider.create(guaranteed.size()));
                guaranteed.forEach(gpool::with);
                builder.pool(gpool);
            }

            if (!common.isEmpty()) {
                LootPool.Builder pool = LootPool.builder().rolls(ConstantLootNumberProvider.create(2)).conditionally(RandomChanceLootCondition.builder(Math.min(0.75f, level * 0.015f)));
                common.forEach(pool::with);
                builder.pool(pool);
            }

            if (!rare.isEmpty()) {
                LootPool.Builder rarePool = LootPool.builder().rolls(ConstantLootNumberProvider.create(2)).conditionally(RandomChanceLootCondition.builder(Math.min(0.35f, level * 0.01f)));
                rare.forEach(rarePool::with);
                builder.pool(rarePool);
            }

            if (!veryCommon.isEmpty()) {
                LootPool.Builder vcPool = LootPool.builder().rolls(ConstantLootNumberProvider.create(1)).conditionally(RandomChanceLootCondition.builder(0.7f));
                veryCommon.forEach(vcPool::with);
                builder.pool(vcPool);
            }
        });
    }

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            PluginRegistry.callOnClose();
            System.out.println("[MobPVP] Mod kapanıyor...");
        }));
    }

    private static String cap(String s) {
        return s.isEmpty() ? s : s.substring(0, 1).toUpperCase(Locale.ROOT) + s.substring(1);
    }
}