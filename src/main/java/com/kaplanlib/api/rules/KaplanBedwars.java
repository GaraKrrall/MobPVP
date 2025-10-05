package com.kaplanlib.api.rules;



public class KaplanBedwars {
    public static void validateKaplanInternal(Class<?> clazz) {
        if (clazz.isAnnotationPresent(com.kaplanlib.api.annotation.KaplanBedwars.class)) {
            if (!clazz.getPackageName().startsWith("com.kaplandev")) {
                throw new RuntimeException("HATA: @KaplanBedwars yalnızca com.kaplandev paketinde kullanılabilir: " + clazz.getName());
            }
        }
    }

}
