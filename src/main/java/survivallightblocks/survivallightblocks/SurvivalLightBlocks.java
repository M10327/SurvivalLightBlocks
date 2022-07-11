package survivallightblocks.survivallightblocks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import survivallightblocks.survivallightblocks.Handlers.PlayerHandler;

public final class SurvivalLightBlocks extends JavaPlugin {

    @Override
    public void onEnable() {
        new PlayerHandler(this);

        ItemStack result = new ItemStack(Material.LIGHT, 64);
        NamespacedKey key = new NamespacedKey(this, "lightBlock");
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape(" G ", "GIG", " G ");
        recipe.setIngredient('G', Material.GLOWSTONE);
        recipe.setIngredient('I', Material.IRON_BLOCK);
        Bukkit.addRecipe(recipe);
    }

    @Override
    public void onDisable() {
        Bukkit.removeRecipe(new NamespacedKey(this, "lightBlock"));
    }
}
