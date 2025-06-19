package com.kaplandev.commands.dashSys;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.HashMap;
import java.util.UUID;

public class DashCommand {
    private static final HashMap<UUID, Long> cooldowns = new HashMap<>();
    private static final int COOLDOWN_MILLIS = 2 * 60 * 1000;

    public static int run(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();
        UUID id = player.getUuid();
        long now = System.currentTimeMillis();

        if (cooldowns.containsKey(id)) {
            long last = cooldowns.get(id);
            long remaining = COOLDOWN_MILLIS - (now - last);
            if (remaining > 0) {
                player.sendMessage(Text.literal("§cDash hazır değil. Bekleme: " + (remaining / 1000) + " saniye"), true);
                return 0;
            }
        }

        // Açlık azalt (minimuma düşmesin diye kontrol edebilirsin istersen)
        player.getHungerManager().add(-12, 0.0f);

        // 1 saniyelik Speed
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 20, 8));
        cooldowns.put(id, now);

        player.sendMessage(Text.literal("§aDash aktif!"), false);
        return 1;
    }
}
