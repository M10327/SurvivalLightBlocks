package survivallightblocks.survivallightblocks.Utils;

import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.MemorySection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import survivallightblocks.survivallightblocks.SurvivalLightBlocks;

import java.util.List;

public class RecipeManager {
    NamespacedKey key;
    SurvivalLightBlocks plugin;
    public RecipeManager(SurvivalLightBlocks plugin){
        this.plugin = plugin;
        this.key = new NamespacedKey(plugin, "lightBlock");
        addRecipe();
    }
    public void reloadRecipe(){
        if(plugin.getServer().getRecipe(key) != null){
            removeRecipe();
        }
        addRecipe();
    }
    private void addRecipe(){
        if (plugin.configManager.craftIsEnable()){
            int resultAmount = plugin.configManager.getResultAmount();
            ItemStack result = new ItemStack(Material.LIGHT, resultAmount);
            NBTItem nbti = new NBTItem(result);
            nbti.mergeCompound(new NBTContainer("{BlockStateTag: {level: 15}}"));
            ItemStack resultWithNbt = nbti.getItem();
            ShapedRecipe recipe = new ShapedRecipe(key, resultWithNbt);
            List<String> recipeShape = plugin.configManager.getRecipeShape();
            recipe.shape(recipeShape.toArray(new String[0]));
            MemorySection recipeIngredients = plugin.configManager.getRecipeIngredients();
            assert recipeIngredients != null;
            for(String recipeKey: recipeIngredients.getKeys(false)){
                Material recipeMaterial = plugin.configManager.getRecipeMaterial(recipeKey);
                char newRecipeKey = recipeKey.charAt(0);
                recipe.setIngredient(newRecipeKey, recipeMaterial);
            }
            plugin.getServer().addRecipe(recipe);
        }
    }
    public void removeRecipe(){
        plugin.getServer().removeRecipe(key);
    }
}
