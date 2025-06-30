package com.kaplandev.level;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.text.Text;

import static com.kaplandev.util.NameUtils.toTr;

public class ZombieVariantAssigner {

    public static int assign(LivingEntity living, String mobId, int level) {
        if (!(living instanceof ZombieEntity zombie)) {
            setDefaultName(living, mobId, level);
            return level;
        }

        double roll = zombie.getRandom().nextDouble();

        if (roll < 0.01) {
            setName(living, "§c[Seviye: 500] §5Nadir Zombi");
            return 500;
        } else if (roll < 0.015) {
            setName(living, "§c[Seviye: 450] §4Delio");
            return 450;
        } else if (roll < 0.022) {
            setName(living, "§c[Seviye: 400] §dMinik Miran");
            zombie.setBaby(true);
            return 400;
        } else if (roll < 0.03) {
            setName(living, "§c[Seviye: 350] §dMiran Baba");
            return 350;
        } else if (roll < 0.04) {
            setName(living, "§c[Seviye: 300] §6Çılgın Dayı");
            return 300;
        } else if (roll < 0.05) {
            setName(living, "§c[Seviye: 280] §cManyak Zombi");
            return 280;
        } else if (roll < 0.062) {
            setName(living, "§c[Seviye: 260] §cKemikçi Zombi");
            return 260;
        } else if (roll < 0.075) {
            setName(living, "§c[Seviye: 240] §cAlevli Zombi");
            return 240;
        } else if (roll < 0.09) {
            setName(living, "§c[Seviye: 220] §cÇamurlu Zombi");
            return 220;
        } else if (roll < 0.105) {
            setName(living, "§c[Seviye: 200] §aZombi Köylü Asker");
            return 200;
        } else if (roll < 0.12) {
            setName(living, "§c[Seviye: 180] §eTaktikçi Zombi");
            return 180;
        } else if (roll < 0.14) {
            setName(living, "§c[Seviye: 160] §2Zehirli Zombi");
            return 160;
        } else if (roll < 0.16) {
            setName(living, "§c[Seviye: 140] §bSu Altı Zombi");
            return 140;
        } else if (roll < 0.18) {
            setName(living, "§c[Seviye: 130] §5Gece Zombisi");
            return 130;
        } else if (roll < 0.20) {
            setName(living, "§c[Seviye: 120] §9Kara Zombi");
            return 120;
        } else if (roll < 0.22) {
            setName(living, "§c[Seviye: 110] §8Çürük Kafa");
            return 110;
        } else if (roll < 0.25) {
            setName(living, "§c[Seviye: 100] §3Sessiz Zombi");
            return 100;
        } else if (roll < 0.28) {
            setName(living, "§c[Seviye: 90] §fBuzlu Zombi");
            return 90;
        } else if (roll < 0.31) {
            setName(living, "§c[Seviye: 80] §dSinsi Zombi");
            return 80;
        } else if (roll < 0.34) {
            setName(living, "§c[Seviye: 70] §7Klasik Zombi");
            return 70;
        } else if (roll < 0.38) {
            setName(living, "§c[Seviye: 60] §fTahta Kafa");
            return 60;
        } else if (roll < 0.42) {
            setName(living, "§c[Seviye: 50] §6Mezarlıkçı");
            return 50;
        } else if (roll < 0.47) {
            setName(living, "§c[Seviye: 40] §5Yaralı Zombi");
            return 40;
        } else if (roll < 0.53) {
            setName(living, "§c[Seviye: 30] §cKuduz Zombi");
            return 30;
        } else if (roll < 0.60) {
            setName(living, "§c[Seviye: 25] §fHantal Zombi");
            return 25;
        } else if (roll < 0.68) {
            setName(living, "§c[Seviye: 20] §eAcemi Zombi");
            return 20;
        } else if (roll < 0.76) {
            setName(living, "§c[Seviye: 15] §aYeni Yetme");
            return 15;
        } else {
            setName(living, "§c[Seviye: 10] §fÇaylak Zombi");
            return 10;
        }
    }

    private static void setName(LivingEntity entity, String name) {
        entity.setCustomName(Text.literal(name));
        entity.setCustomNameVisible(true);
    }

    private static void setDefaultName(LivingEntity entity, String mobId, int level) {
        entity.setCustomName(Text.literal("§c[Seviye: " + level + "] §f" + toTr(mobId)));
        entity.setCustomNameVisible(true);
    }


}
