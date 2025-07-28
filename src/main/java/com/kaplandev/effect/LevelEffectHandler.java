package com.kaplandev.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class LevelEffectHandler {

    public static void applyEffects(LivingEntity living, int level) {
        if (level >= 1 && level < 5) {
            //living.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, Integer.MAX_VALUE, 0));
          //  living.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, Integer.MAX_VALUE, 0));
        } else if (level >= 5 && level < 10) {
           // living.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, Integer.MAX_VALUE, 0));
           // living.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, Integer.MAX_VALUE, 0));
        } else if (level >= 10 && level < 15) {
           // living.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, Integer.MAX_VALUE, 0));
        } else if (level >= 15 && level < 20) {
            //living.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, Integer.MAX_VALUE, 1));
          //  living.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, Integer.MAX_VALUE, 0));
        } else if (level >= 20 && level < 30) {
           // living.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, Integer.MAX_VALUE, 1));
           // living.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, Integer.MAX_VALUE, 1));
        } else if (level >= 30 && level < 40) {
          //  living.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, Integer.MAX_VALUE, 0));
           // living.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, Integer.MAX_VALUE, 0));
          //  living.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, Integer.MAX_VALUE, 0));
        } else if (level >= 40 && level < 50) {
           // living.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, Integer.MAX_VALUE, 1));
           // living.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, Integer.MAX_VALUE, 1));
           // living.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, Integer.MAX_VALUE, 0));
        } else if (level >= 50 && level < 60) {
           // living.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, Integer.MAX_VALUE, 0));
           // living.addStatusEffect(new StatusEffectInstance(StatusEffects.CONDUIT_POWER, Integer.MAX_VALUE, 0));
           // living.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, Integer.MAX_VALUE, 0));
        } else if (level >= 60 && level < 70) {
           // living.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, Integer.MAX_VALUE, 2));
            //living.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, Integer.MAX_VALUE, 1));
        } else if (level >= 70 && level < 80) {
           // living.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, Integer.MAX_VALUE, 0));
           // living.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, Integer.MAX_VALUE, 0));
           // living.addStatusEffect(new StatusEffectInstance(StatusEffects.HERO_OF_THE_VILLAGE, Integer.MAX_VALUE, 0));
        } else if (level >= 80 && level < 90) {
           // living.addStatusEffect(new StatusEffectInstance(StatusEffects.BAD_OMEN, Integer.MAX_VALUE, 2));
           // living.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, Integer.MAX_VALUE, 0));
           // living.addStatusEffect(new StatusEffectInstance(StatusEffects.UNLUCK, Integer.MAX_VALUE, 2));
        } else if (level >= 90 && level < 100) {
            //living.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, Integer.MAX_VALUE, 2));
           // living.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, Integer.MAX_VALUE, 2));
            //living.addStatusEffect(new StatusEffectInstance(StatusEffects.LUCK, Integer.MAX_VALUE, 2));
        } else if (level >= 100 && level < 150) {
          //  living.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, Integer.MAX_VALUE, 3));
          //  living.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, Integer.MAX_VALUE, 2));
        } else if (level >= 150 && level < 200) {
           // living.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, Integer.MAX_VALUE, 3));
        } else if (level >= 200 && level < 300) {
            //living.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, Integer.MAX_VALUE, 3));
        } else if (level >= 300 && level < 400) {
           // living.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, Integer.MAX_VALUE, 1));
           // living.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, Integer.MAX_VALUE, 1));
            //living.addStatusEffect(new StatusEffectInstance(StatusEffects.CONDUIT_POWER, Integer.MAX_VALUE, 1));
        } else if (level >= 400 && level < 500) {
           // living.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, Integer.MAX_VALUE, 1));
            //living.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, Integer.MAX_VALUE, 3));
        } else if (level >= 500) {
            // En yüksek seviyedeki efektler buraya eklenebilir
        }
    }
}
