package com.kaplandev.level.player.event;


import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import java.util.UUID;

public class PlayerLevelEvents {

    public static void onLevelUp(ServerPlayerEntity player, int newLevel) {
        player.sendMessageToClient(
                Text.literal("§aSEVİYE ATLADIN! §eYeni seviye: " + newLevel),
                true
        );
    }



    public static void applyStatBonus(PlayerEntity player, int level) {
        if (level > 120) {
            throw new RuntimeException("Oyuncu 120 seviyeden fazla olamaz! (" + level + ")");
        }

        // Maksimum can: 20 (default) + 12 (her 10 seviye 2 can) = 32
        double bonusHearts = (level / 10) * 2; // her 10 seviyeye 1 kalp = 2 can
        double totalHealth = 20.0 + bonusHearts;

        var healthAttr = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (healthAttr != null) {
            healthAttr.setBaseValue(totalHealth);
            if (player.getHealth() > totalHealth) {
                player.setHealth((float) totalHealth);
            }
        }

        // Hasar bonusu: her seviyeye 0.2 damage (başlangıç 2.0)
        var attackAttr = player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        if (attackAttr != null) {
            double damage = 2.0 + level * 0.2;
            attackAttr.setBaseValue(damage);
        }
    }

}
