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

        if (roll < 0.002) {
            setName(living, "§c[Seviye: 500] §5Nadir Zombi");
            return 500;
        } else if (roll < 0.004) {
            setName(living, "§c[Seviye: 450] §4Delio");
            return 450;
        } else if (roll < 0.006) {
            setName(living, "§c[Seviye: 400] §dMinik Miran");
            zombie.setBaby(true);
            return 400;
        } else if (roll < 0.008) {
            setName(living, "§c[Seviye: 350] §dMiran Baba");
            return 350;
        } else if (roll < 0.011) {
            setName(living, "§c[Seviye: 300] §6Çılgın Dayı");
            return 300;
        } else if (roll < 0.014) {
            setName(living, "§c[Seviye: 280] §cManyak Zombi");
            return 280;
        } else if (roll < 0.018) {
            setName(living, "§c[Seviye: 260] §cKemikçi Zombi");
            return 260;
        } else if (roll < 0.023) {
            setName(living, "§c[Seviye: 240] §cAlevli Zombi");
            return 240;
        } else if (roll < 0.029) {
            setName(living, "§c[Seviye: 220] §cÇamurlu Zombi");
            return 220;
        } else if (roll < 0.035) {
            setName(living, "§c[Seviye: 200] §aZombi Köylü Asker");
            return 200;
        } else if (roll < 0.045) {
            setName(living, "§c[Seviye: 180] §eTaktikçi Zombi");
            return 180;
        } else if (roll < 0.06) {
            setName(living, "§c[Seviye: 160] §2Zehirli Zombi");
            return 160;
        } else if (roll < 0.08) {
            setName(living, "§c[Seviye: 140] §bSu Altı Zombi");
            return 140;
        } else if (roll < 0.10) {
            setName(living, "§c[Seviye: 130] §5Gece Zombisi");
            return 130;
        } else if (roll < 0.12) {
            setName(living, "§c[Seviye: 120] §9Kara Zombi");
            return 120;
        } else if (roll < 0.14) {
            setName(living, "§c[Seviye: 110] §8Çürük Kafa");
            return 110;
        } else if (roll < 0.17) {
            setName(living, "§c[Seviye: 100] §3Sessiz Zombi");
            return 100;
        } else if (roll < 0.20) {
            setName(living, "§c[Seviye: 90] §fBuzlu Zombi");
            return 90;
        } else if (roll < 0.24) {
            setName(living, "§c[Seviye: 80] §dSinsi Zombi");
            return 80;
        } else if (roll < 0.30) {
            setName(living, "§c[Seviye: 70] §7Klasik Zombi");
            return 70;
        } else if (roll < 0.36) {
            setName(living, "§c[Seviye: 60] §fTahta Kafa");
            return 60;
        } else if (roll < 0.43) {
            setName(living, "§c[Seviye: 50] §6Mezarlıkçı");
            return 50;
        } else if (roll < 0.51) {
            setName(living, "§c[Seviye: 40] §5Yaralı Zombi");
            return 40;
        } else if (roll < 0.60) {
            setName(living, "§c[Seviye: 30] §cKuduz Zombi");
            return 30;
        } else if (roll < 0.70) {
            setName(living, "§c[Seviye: 20] §eAcemi Zombi");
            return 20;
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
