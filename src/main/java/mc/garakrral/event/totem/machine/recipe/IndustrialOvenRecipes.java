package mc.garakrral.event.totem.machine.recipe;

import com.google.gson.Gson;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class IndustrialOvenRecipes {
    private static final Gson GSON = new Gson();
    private static final Map<String, RecipeData> RECIPES = new HashMap<>();

    public static class RecipeData {
        public int time;
        public List<Input> inputs;
        public Output output;
    }

    public static class Input {
        public String item;
        public int count;
    }

    public static class Output {
        public String item;
        public int count;
    }

    public static void loadRecipes() {
        RECIPES.clear();

        try {
            ClassLoader loader = IndustrialOvenRecipes.class.getClassLoader();
            // /data/mobpvp/totem_machine/industrial_oven içindeki dosyaları listele
            // (Java ClassLoader doğrudan dizin listeleyemez, bu yüzden sabit liste veya resource tanımı gerekiyor)
            // ama mod build edilirken bu dizin sabit olduğundan hepsi okunur:
            String basePath = "data/mobpvp/totem_machine/industrial_oven/";
            String[] recipeFiles = {
                    "iron_to_golden_apple.json",
                    "test.json"
            };

            for (String file : recipeFiles) {
                try (InputStream stream = loader.getResourceAsStream(basePath + file)) {
                    if (stream == null) {
                        continue;
                    }
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
                        RecipeData recipe = GSON.fromJson(reader, RecipeData.class);
                        RECIPES.put(file, recipe);
                    }
                }
            }

        } catch (Exception e) {

        }
    }

    public static RecipeData matchRecipe(net.minecraft.block.entity.ChestBlockEntity chest) {
        List<ItemStack> chestItems = new ArrayList<>();
        for (int i = 0; i < chest.size(); i++) {
            chestItems.add(chest.getStack(i));
        }

        for (RecipeData recipe : RECIPES.values()) {
            boolean matches = true;

            for (Input input : recipe.inputs) {
                Item item = Registries.ITEM.get( Identifier.of(input.item));
                int totalCount = 0;
                for (ItemStack stack : chestItems) {
                    if (stack.isOf(item)) totalCount += stack.getCount();
                }
                if (totalCount < input.count) {
                    matches = false;
                    break;
                }
            }

            if (matches) return recipe;
        }
        return null;
    }

    public static ItemStack getOutput(RecipeData recipe) {
        Item item = Registries.ITEM.get(Identifier.of(recipe.output.item));
        return new ItemStack(item, recipe.output.count);
    }
    public static RecipeData getRecipeById(String id) {
        return RECIPES.get(id);
    }

}
