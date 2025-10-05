package com.kaplanlib.util;

import java.util.Locale;

public class NameUtils {

    public static String toTr(String en) {
        return switch (en.toLowerCase(Locale.ROOT)) {
            /*case "zombie" -> "Zombi";
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
            case "custom_skeleton" -> "Deli İskelet";
            case "spider" -> "Örümcek";
            case "cave_spider" -> "Mağara Örümceği";
            case "mini_iron_golem" -> "Minik Demir Golem";*/
            default -> cap(en.replace("_", " "));
        };
    }

    public static String cap(String str) {
        String[] words = str.split(" ");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");
            }
        }
        return result.toString().trim();
    }
}
