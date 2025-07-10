package com.kaplandev.client.info; // Kendi mod paketinize göre değiştirin

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.Text;

public class dink {

    // Basit bir toast göstermek için
    public static void showToast(String title, String message) {
        MinecraftClient.getInstance().getToastManager().add(
                new SystemToast(
                        SystemToast.Type.PERIODIC_NOTIFICATION, // Toast türü (başarı, uyarı vb.)
                        Text.literal(title),            // Başlık
                        Text.literal(message)          // Mesaj
                )
        );
    }

    // Özel bir toast türü ile göstermek için

    }
