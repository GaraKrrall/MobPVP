package com.kaplandev;


import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.registry.Registries;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;
import net.minecraft.item.*;
import net.minecraft.loot.*;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.*;



public final class mobpvp implements ModInitializer {

    public static final Map<String, Integer[]> LEVEL_RANGES = new HashMap<>();

    public static final String MOD_ID = "mobpvp";

    static {
        LEVEL_RANGES.put("zombie", new Integer[]{1, 5});
        LEVEL_RANGES.put("skeleton", new Integer[]{1, 5});
        LEVEL_RANGES.put("husk", new Integer[]{1, 5});
        LEVEL_RANGES.put("ghast", new Integer[]{5, 10});
        LEVEL_RANGES.put("zombie_villager", new Integer[]{2, 6});
        LEVEL_RANGES.put("piglin", new Integer[]{5, 12});
        LEVEL_RANGES.put("creeper", new Integer[]{4, 10});
        LEVEL_RANGES.put("witch", new Integer[]{10, 20});
        LEVEL_RANGES.put("magma_cube", new Integer[]{8, 16});
        LEVEL_RANGES.put("piglin_brute", new Integer[]{10, 20});
        LEVEL_RANGES.put("wither_skeleton", new Integer[]{40, 64});
        LEVEL_RANGES.put("wither", new Integer[]{100, 800});
        LEVEL_RANGES.put("warden", new Integer[]{500, 520});
        LEVEL_RANGES.put("ender_dragon", new Integer[]{2000, 2001});
        LEVEL_RANGES.put("enderman", new Integer[]{2, 11});
        LEVEL_RANGES.put("spider", new Integer[]{3, 6});
        LEVEL_RANGES.put("cave_spider", new Integer[]{4, 8});

        LEVEL_RANGES.put("sheep", new Integer[]{2, 5});
        LEVEL_RANGES.put("cow", new Integer[]{1, 5});
        LEVEL_RANGES.put("pig", new Integer[]{1, 5});
        LEVEL_RANGES.put("chicken", new Integer[]{1, 5});
    }

    @Override
    public void onInitialize() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(entity instanceof LivingEntity living) || entity instanceof ServerPlayerEntity) return;
            if (living.hasCustomName()) return;





            String mobId = Registries.ENTITY_TYPE.getId(entity.getType()).getPath();
            Integer[] range = LEVEL_RANGES.getOrDefault(mobId, new Integer[]{1, 40});
            int level = range[0] + entity.getRandom().nextInt(range[1] - range[0] + 1);

            // Zombi özel varyantlar
            if (entity instanceof ZombieEntity zombie) {
                double roll = entity.getRandom().nextDouble();

                if (roll < 0.01) {
                    level = 500;
                    living.setCustomName(Text.literal("§c[Seviye: 500] §5Nadir Zombi"));
                } else if (roll < 0.015) {
                    level = 450;
                    living.setCustomName(Text.literal("§c[Seviye: 450] §4Delio"));
                } else if (roll < 0.022) {
                    level = 400;
                    living.setCustomName(Text.literal("§c[Seviye: 400] §dMinik Miran"));
                    zombie.setBaby(true);
                } else if (roll < 0.03) {
                    level = 350;
                    living.setCustomName(Text.literal("§c[Seviye: 350] §dMiran Baba"));
                } else if (roll < 0.04) {
                    level = 300;
                    living.setCustomName(Text.literal("§c[Seviye: 300] §6Çılgın Dayı"));
                } else if (roll < 0.05) {
                    level = 280;
                    living.setCustomName(Text.literal("§c[Seviye: 280] §cManyak Zombi"));
                } else if (roll < 0.062) {
                    level = 260;
                    living.setCustomName(Text.literal("§c[Seviye: 260] §cKemikçi Zombi"));
                } else if (roll < 0.075) {
                    level = 240;
                    living.setCustomName(Text.literal("§c[Seviye: 240] §cAlevli Zombi"));
                } else if (roll < 0.09) {
                    level = 220;
                    living.setCustomName(Text.literal("§c[Seviye: 220] §cÇamurlu Zombi"));
                } else if (roll < 0.105) {
                    level = 200;
                    living.setCustomName(Text.literal("§c[Seviye: 200] §aZombi Köylü Asker"));
                } else if (roll < 0.12) {
                    level = 180;
                    living.setCustomName(Text.literal("§c[Seviye: 180] §eTaktikçi Zombi"));
                } else if (roll < 0.14) {
                    level = 160;
                    living.setCustomName(Text.literal("§c[Seviye: 160] §2Zehirli Zombi"));
                } else if (roll < 0.16) {
                    level = 140;
                    living.setCustomName(Text.literal("§c[Seviye: 140] §bSu Altı Zombi"));
                } else if (roll < 0.18) {
                    level = 130;
                    living.setCustomName(Text.literal("§c[Seviye: 130] §5Gece Zombisi"));
                } else if (roll < 0.20) {
                    level = 120;
                    living.setCustomName(Text.literal("§c[Seviye: 120] §9Kara Zombi"));
                } else if (roll < 0.22) {
                    level = 110;
                    living.setCustomName(Text.literal("§c[Seviye: 110] §8Çürük Kafa"));
                } else if (roll < 0.25) {
                    level = 100;
                    living.setCustomName(Text.literal("§c[Seviye: 100] §3Sessiz Zombi"));
                } else if (roll < 0.28) {
                    level = 90;
                    living.setCustomName(Text.literal("§c[Seviye: 90] §fBuzlu Zombi"));
                } else if (roll < 0.31) {
                    level = 80;
                    living.setCustomName(Text.literal("§c[Seviye: 80] §dSinsi Zombi"));
                } else if (roll < 0.34) {
                    level = 70;
                    living.setCustomName(Text.literal("§c[Seviye: 70] §7Klasik Zombi"));
                } else if (roll < 0.38) {
                    level = 60;
                    living.setCustomName(Text.literal("§c[Seviye: 60] §fTahta Kafa"));
                } else if (roll < 0.42) {
                    level = 50;
                    living.setCustomName(Text.literal("§c[Seviye: 50] §6Mezarlıkçı"));
                } else if (roll < 0.47) {
                    level = 40;
                    living.setCustomName(Text.literal("§c[Seviye: 40] §5Yaralı Zombi"));
                } else if (roll < 0.53) {
                    level = 30;
                    living.setCustomName(Text.literal("§c[Seviye: 30] §cKuduz Zombi"));
                } else if (roll < 0.60) {
                    level = 25;
                    living.setCustomName(Text.literal("§c[Seviye: 25] §fHantal Zombi"));
                } else if (roll < 0.68) {
                    level = 20;
                    living.setCustomName(Text.literal("§c[Seviye: 20] §eAcemi Zombi"));
                } else if (roll < 0.76) {
                    level = 15;
                    living.setCustomName(Text.literal("§c[Seviye: 15] §aYeni Yetme"));
                } else {
                    level = 10;
                    living.setCustomName(Text.literal("§c[Seviye: 10] §fÇaylak Zombi"));
                }


                living.setCustomNameVisible(true);
            } else {
                living.setCustomName(Text.literal("§c[Seviye: " + level + "] §f" + toTr(mobId)));
                living.setCustomNameVisible(true);
            }



            // "SİNİRLİ MİRAN BABA" zombisi - %10 ihtimal
            if (entity instanceof ZombieEntity zombie && zombie.getRandom().nextFloat() < 0.06f) {
                zombie.setCustomName(Text.literal("§4SİNİRLİ MİRAN BABA"));
                zombie.setCustomNameVisible(true);

                level = 38;
                living.setCustomName(Text.literal("§c[Lv." + level + "] §4SİNİRLİ MİRAN BABA"));

                if (living.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH) != null) {
                    double base = living.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getBaseValue();
                    double extra = level * 1.2;
                    double total = base + extra;
                    living.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(total);
                    living.setHealth((float) total);
                }

                if (living.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE) != null) {
                    double dmg = living.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).getBaseValue();
                    living.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(dmg + level * 0.2);
                }

                // Netherite zırh giydir (düşmesin)
                zombie.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.NETHERITE_HELMET));
                zombie.equipStack(EquipmentSlot.CHEST, new ItemStack(Items.NETHERITE_CHESTPLATE));
                zombie.equipStack(EquipmentSlot.LEGS, new ItemStack(Items.NETHERITE_LEGGINGS));
                zombie.equipStack(EquipmentSlot.FEET, new ItemStack(Items.NETHERITE_BOOTS));

                ItemStack sword = new ItemStack(Items.NETHERITE_SWORD);
                RegistryEntry<Enchantment> sharpnessEntry = world.getRegistryManager()
                        .get(RegistryKeys.ENCHANTMENT)
                        .getEntry(Identifier.of("minecraft", "sharpness")).orElseThrow();

                RegistryEntry<Enchantment> knockbackEntry = world.getRegistryManager()
                        .get(RegistryKeys.ENCHANTMENT)
                        .getEntry(Identifier.of("minecraft", "knockback")).orElseThrow();

                  // Kılıca büyü ekle
                sword.addEnchantment(sharpnessEntry, 9);
                sword.addEnchantment(knockbackEntry, 40);
                zombie.equipStack(EquipmentSlot.MAINHAND, sword);

                // Eşyaların düşmesini engelle
                for (EquipmentSlot slot : EquipmentSlot.values()) {
                    zombie.setEquipmentDropChance(slot, 0f);
                }
                return;
            }


            //Easter egg
            // "SİNİRLİ MİRAN BABA" zombisi - %5 ihtimal
            if (entity instanceof ZombieEntity zombie && zombie.getRandom().nextFloat() <  0.05f) {
                zombie.setCustomName(Text.literal("§4ARABİC TOAST BOSS"));
                zombie.setCustomNameVisible(true);

                level = 29;
                living.setCustomName(Text.literal("§c[Seviye: " + level + "] §4ARABİC TOAST BOSS"));

                if (living.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH) != null) {
                    double base = living.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getBaseValue();
                    double extra = level * 1.2;
                    double total = base + extra;
                    living.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(total);
                    living.setHealth((float) total);
                }

                if (living.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE) != null) {
                    double dmg = living.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).getBaseValue();
                    living.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(dmg + level * 0.2);
                }


                zombie.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.DIAMOND_HELMET));
                zombie.equipStack(EquipmentSlot.CHEST, new ItemStack(Items.DIAMOND_CHESTPLATE));
                zombie.equipStack(EquipmentSlot.LEGS, new ItemStack(Items.DIAMOND_LEGGINGS));
                zombie.equipStack(EquipmentSlot.FEET, new ItemStack(Items.DIAMOND_BOOTS));

                ItemStack sword = new ItemStack(Items.NETHERITE_SWORD);
                RegistryEntry<Enchantment> sharpnessEntry = world.getRegistryManager()
                        .get(RegistryKeys.ENCHANTMENT)
                        .getEntry(Identifier.of("minecraft", "sharpness")).orElseThrow();

                RegistryEntry<Enchantment> knockbackEntry = world.getRegistryManager()
                        .get(RegistryKeys.ENCHANTMENT)
                        .getEntry(Identifier.of("minecraft", "knockback")).orElseThrow();

                // Kılıca büyü ekle
                sword.addEnchantment(sharpnessEntry, 9);
                sword.addEnchantment(knockbackEntry, 20);
                zombie.equipStack(EquipmentSlot.MAINHAND, sword);



                return;
            }

            // "SİNİRLİ MİRAN BABA" zombisi - %5 ihtimal
            if (entity instanceof ZombieEntity zombie && zombie.getRandom().nextFloat() < 0.09f) {
                zombie.setCustomName(Text.literal("§4MİNİ BOSS"));
                zombie.setCustomNameVisible(true);

                level = 12;
                living.setCustomName(Text.literal("§c[Seviye: " + level + "] §4MİNİ BOSS"));

                if (living.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH) != null) {
                    double base = living.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getBaseValue();
                    double extra = level * 1.2;
                    double total = base + extra;
                    living.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(total);
                    living.setHealth((float) total);
                }

                if (living.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE) != null) {
                    double dmg = living.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).getBaseValue();
                    living.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(dmg + level * 0.2);
                }


                zombie.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
                zombie.equipStack(EquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
                zombie.equipStack(EquipmentSlot.LEGS, new ItemStack(Items.IRON_LEGGINGS));
                zombie.equipStack(EquipmentSlot.FEET, new ItemStack(Items.IRON_BOOTS));

                ItemStack sword = new ItemStack(Items.IRON_SWORD);
                RegistryEntry<Enchantment> sharpnessEntry = world.getRegistryManager()
                        .get(RegistryKeys.ENCHANTMENT)
                        .getEntry(Identifier.of("minecraft", "sharpness")).orElseThrow();

                RegistryEntry<Enchantment> knockbackEntry = world.getRegistryManager()
                        .get(RegistryKeys.ENCHANTMENT)
                        .getEntry(Identifier.of("minecraft", "knockback")).orElseThrow();

                // Kılıca büyü ekle
                sword.addEnchantment(sharpnessEntry, 9);
                sword.addEnchantment(knockbackEntry, 2);
                zombie.equipStack(EquipmentSlot.MAINHAND, sword);



                return;
            }

            // "SİNİRLİ MİRAN BABA" zombisi - %5 ihtimal
            if (entity instanceof ZombieEntity zombie && zombie.getRandom().nextFloat() < 0.01f) {
                zombie.setCustomName(Text.literal("§4GİANT BOSS"));
                zombie.setCustomNameVisible(true);
                

                level = 1000;

                living.setCustomName(Text.literal("§c[Seviye: " + level + "] §4GİANT BOSS"));

                if (living.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH) != null) {
                    double base = living.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getBaseValue();
                    double extra = level * 1.2;
                    double total = base + extra;
                    living.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(total);
                    living.setHealth((float) total);
                }

                if (living.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE) != null) {
                    double dmg = living.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).getBaseValue();
                    living.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(dmg + level * 0.2);
                }


                zombie.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.NETHERITE_HELMET));
                zombie.equipStack(EquipmentSlot.CHEST, new ItemStack(Items.NETHERITE_CHESTPLATE));
                zombie.equipStack(EquipmentSlot.LEGS, new ItemStack(Items.NETHERITE_LEGGINGS));
                zombie.equipStack(EquipmentSlot.FEET, new ItemStack(Items.NETHERITE_BOOTS));

                ItemStack sword = new ItemStack(Items.NETHERITE_SWORD);
                RegistryEntry<Enchantment> sharpnessEntry = world.getRegistryManager()
                        .get(RegistryKeys.ENCHANTMENT)
                        .getEntry(Identifier.of("minecraft", "sharpness")).orElseThrow();

                RegistryEntry<Enchantment> knockbackEntry = world.getRegistryManager()
                        .get(RegistryKeys.ENCHANTMENT)
                        .getEntry(Identifier.of("minecraft", "knockback")).orElseThrow();

                // Kılıca büyü ekle
                sword.addEnchantment(sharpnessEntry, 9);
                sword.addEnchantment(knockbackEntry, 20);
                zombie.equipStack(EquipmentSlot.MAINHAND, sword);





                return;
            }




            if (living.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH) != null) {
                double base = living.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getBaseValue();
                double extra = level * 1.5;
                double total = base + extra;
                living.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(total);
                living.setHealth((float) total);
            }

            if (living.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE) != null) {
                double dmg = living.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).getBaseValue();
                living.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(dmg + level * 0.3);
            }

            if (level >= 1 && level < 5) {
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, Integer.MAX_VALUE, 0));
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, Integer.MAX_VALUE, 0));
            } else if (level >= 5 && level < 10) {
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, Integer.MAX_VALUE, 0));
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, Integer.MAX_VALUE, 0));
            } else if (level >= 10 && level < 15) {
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, Integer.MAX_VALUE, 0));
              //  living.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, Integer.MAX_VALUE, 0));
            } else if (level >= 15 && level < 20) {
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, Integer.MAX_VALUE, 1));
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, Integer.MAX_VALUE, 0));
            } else if (level >= 20 && level < 30) {
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, Integer.MAX_VALUE, 1));
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, Integer.MAX_VALUE, 1));
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, Integer.MAX_VALUE, 0));
            } else if (level >= 30 && level < 40) {
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, Integer.MAX_VALUE, 0));
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, Integer.MAX_VALUE, 0));
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, Integer.MAX_VALUE, 0));
            } else if (level >= 40 && level < 50) {
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, Integer.MAX_VALUE, 1));
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, Integer.MAX_VALUE, 1));
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, Integer.MAX_VALUE, 0));
            } else if (level >= 50 && level < 60) {
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, Integer.MAX_VALUE, 0));
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.CONDUIT_POWER, Integer.MAX_VALUE, 0));
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, Integer.MAX_VALUE, 0));
            } else if (level >= 60 && level < 70) {
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, Integer.MAX_VALUE, 2));
               // living.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, Integer.MAX_VALUE, 1));
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, Integer.MAX_VALUE, 1));
            } else if (level >= 70 && level < 80) {
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, Integer.MAX_VALUE, 0));
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, Integer.MAX_VALUE, 0));
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.HERO_OF_THE_VILLAGE, Integer.MAX_VALUE, 0));
            } else if (level >= 80 && level < 90) {
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.BAD_OMEN, Integer.MAX_VALUE, 2));
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, Integer.MAX_VALUE, 0));
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.UNLUCK, Integer.MAX_VALUE, 2));
            } else if (level >= 90 && level < 100) {
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, Integer.MAX_VALUE, 2));
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, Integer.MAX_VALUE, 2));
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.LUCK, Integer.MAX_VALUE, 2));
            } else if (level >= 100 && level < 150) {
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, Integer.MAX_VALUE, 3));
               // living.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, Integer.MAX_VALUE, 1));
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, Integer.MAX_VALUE, 2));
            } else if (level >= 150 && level < 200) {
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, Integer.MAX_VALUE, 1));
              //  living.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, Integer.MAX_VALUE, 1));
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, Integer.MAX_VALUE, 3));
            } else if (level >= 200 && level < 300) {
           //     living.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, Integer.MAX_VALUE, 4));
            //    living.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, Integer.MAX_VALUE, 4));
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, Integer.MAX_VALUE, 3));
            } else if (level >= 300 && level < 400) {
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, Integer.MAX_VALUE, 1));
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, Integer.MAX_VALUE, 1));
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.CONDUIT_POWER, Integer.MAX_VALUE, 1));
            } else if (level >= 400 && level < 500) {
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, Integer.MAX_VALUE, 1));
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, Integer.MAX_VALUE, 1));
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, Integer.MAX_VALUE, 3));
            } else if (level >= 500) {
            //    living.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, Integer.MAX_VALUE, 5));
              //  living.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, Integer.MAX_VALUE, 5));
              //  living.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, Integer.MAX_VALUE, 3));
               // living.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, Integer.MAX_VALUE, 4));
                //living.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, Integer.MAX_VALUE, 2));
               // living.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, Integer.MAX_VALUE, 2));
            }

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
                        if (!finalEntity.isRemoved() && finalEntity.getWorld() instanceof ServerWorld serverWorld) {
                            serverWorld.createExplosion(finalEntity, finalEntity.getX(), finalEntity.getY(), finalEntity.getZ(), 2.5f, World.ExplosionSourceType.MOB);
                            finalEntity.discard();
                        }
                    } catch (InterruptedException ignored) {}
                }).start();
            }
        });

        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, attacker, victim) -> {
            if (!(world instanceof ServerWorld serverWorld)) return;
            if (!(attacker instanceof ServerPlayerEntity player)) return;
            serverWorld.getPlayers().forEach(p -> p.sendMessage(Text.literal(player.getName().getString() + " → " + victim.getName().getString() + " öldürdü!"), false));
            if (Math.random() <= 0.01) player.sendMessage(Text.literal("§6§lBİNGO! §fNadir ganimet kazandın!"), false);
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
                if (level >= 2) common.add(ItemEntry.builder(Items.IRON_INGOT).build());
                if (level >= 4) common.add(ItemEntry.builder(Items.BOW).build());
                if (level >= 6) common.add(ItemEntry.builder(Items.GOLD_INGOT).build());
                if (level >= 10) common.add(ItemEntry.builder(Items.DIAMOND).build());
                if (level >= 15) common.add(ItemEntry.builder(Items.DIAMOND_CHESTPLATE).build());
                if (level >= 50) common.add(ItemEntry.builder(Items.NETHERITE_SCRAP).build());

                if (level >= 10) {
                    rare.add(ItemEntry.builder(Items.ENCHANTED_GOLDEN_APPLE).build());
                    rare.add(ItemEntry.builder(Items.NETHERITE_INGOT).build());
                    rare.add(ItemEntry.builder(Items.TOTEM_OF_UNDYING).build());
                }
                if (level >= 100) {
                    rare.add(ItemEntry.builder(Items.NETHER_STAR).build());
                    rare.add(ItemEntry.builder(Items.NETHERITE_CHESTPLATE).build());
                }
                if (level >= 200) {
                    rare.add(ItemEntry.builder(Items.NETHERITE_SWORD).build());
                    rare.add(ItemEntry.builder(Items.SCULK_SENSOR).build());
                }

                if (level == 1000) {
                    guaranteed.add(ItemEntry.builder(Items.NETHERITE_AXE).build());
                    guaranteed.add(ItemEntry.builder(Items.NETHERITE_INGOT).build());
                    guaranteed.add(ItemEntry.builder(Items.NETHERITE_CHESTPLATE).build());
                    guaranteed.add(ItemEntry.builder(Items.NETHERITE_SWORD).build());
                }
                if (mobId.equals("ender_dragon")) {
                    guaranteed.add(ItemEntry.builder(Items.DRAGON_EGG).build());
                    guaranteed.add(ItemEntry.builder(Items.ELYTRA).build());
                    guaranteed.add(ItemEntry.builder(Items.NETHERITE_BLOCK).build());
                    guaranteed.add(ItemEntry.builder(Items.BEACON).build());
                }
                if (mobId.equals("warden")) {
                    guaranteed.add(ItemEntry.builder(Items.SCULK_SHRIEKER).build());
                    guaranteed.add(ItemEntry.builder(Items.NETHERITE_INGOT).build());
                    guaranteed.add(ItemEntry.builder(Items.NETHERITE_AXE).build());
                    guaranteed.add(ItemEntry.builder(Items.NETHERITE_BOOTS).build());
                }

                veryCommon.add(ItemEntry.builder(Items.IRON_NUGGET).build());
                veryCommon.add(ItemEntry.builder(Items.GOLD_NUGGET).build());
                veryCommon.add(ItemEntry.builder(Items.GUNPOWDER).build());
            } else {
                if (mobId.equals("sheep")) common.add(ItemEntry.builder(Items.COOKED_MUTTON).build());
                if (mobId.equals("cow")) common.add(ItemEntry.builder(Items.COOKED_BEEF).build());
                if (mobId.equals("pig")) common.add(ItemEntry.builder(Items.COOKED_PORKCHOP).build());
                if (mobId.equals("chicken")) common.add(ItemEntry.builder(Items.COOKED_CHICKEN).build());
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

    private static String cap(String s) {
        return s.isEmpty() ? s : s.substring(0, 1).toUpperCase(Locale.ROOT) + s.substring(1);
    }

    private static String toTr(String en) {
        return switch (en.toLowerCase(Locale.ROOT)) {
            case "zombie" -> "Zombi";
            case "husk" -> "Husk";
            case "skeleton" -> "İskelet";
            case "ghast" -> "Ghast";
            case "zombie_villager" -> "Köylü Zombi";
            case "piglin" -> "Piglin";
            case "creeper" -> "Creeper";
            case "witch" -> "Cadı";
            case "magma_cube" -> "Magma Küpü";
            case "piglin_brute" -> "Piglin Brute";
            case "wither_skeleton" -> "Wither İskelet";
            case "wither" -> "Wither";
            case "warden" -> "Warden";
            case "ender_dragon" -> "Ender Ejderhası";
            case "enderman" -> "Enderman";
            case "sheep" -> "Koyun";
            case "cow" -> "İnek";
            case "chicken" -> "Tavuk";
            case "pig" -> "Domuz";
            case "spider" -> "Örümcek";
            case "cave_spider" -> "Mağara Örümceği";
            default -> cap(en);
        };
    }
}