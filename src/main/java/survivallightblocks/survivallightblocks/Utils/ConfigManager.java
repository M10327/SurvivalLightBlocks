package survivallightblocks.survivallightblocks.Utils;

import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import survivallightblocks.survivallightblocks.SurvivalLightBlocks;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ConfigManager {
    SurvivalLightBlocks plugin;
    FileConfiguration configFile;

    public ConfigManager(SurvivalLightBlocks plugin){
        this.plugin = plugin;
        this.plugin.saveDefaultConfig();
        this.configFile = plugin.getConfig();
    }
    public void reload() {
        File file = new File(plugin.getDataFolder(), "config.yml");
        try {
            configFile.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
        plugin.recipeManager.reloadRecipe();
    }
    public boolean craftIsEnable(){
        return configFile.getBoolean("craftEnable");
    }
    public int getResultAmount(){
        return configFile.getInt("resultAmount");
    }
    public List<String> getRecipeShape(){
        return configFile.getStringList("recipeShape");
    }
    public MemorySection getRecipeIngredients(){
        return (MemorySection) configFile.get("recipeIngredients");
    }
    public Material getRecipeMaterial(String recipeKey){
        return Material.valueOf(configFile.getString("recipeIngredients." + recipeKey));
    }
}
