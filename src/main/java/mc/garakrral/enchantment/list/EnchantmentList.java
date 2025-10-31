package mc.garakrral.enchantment.list;


import java.util.Arrays;

public enum EnchantmentList {
    THUNDERING("thundering"), CERTAINTY("certainty"), MAGMATIZATION("magmatization");

    private final String name;

    EnchantmentList(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static String[] getAllNames() {
        return Arrays.stream(values()).map(EnchantmentList::getName).toArray(String[]::new);
    }
}