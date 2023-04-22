package survivallightblocks.survivallightblocks;

import org.bukkit.plugin.java.JavaPlugin;
import survivallightblocks.survivallightblocks.Handlers.PlayerHandler;
import survivallightblocks.survivallightblocks.Utils.CommandController;
import survivallightblocks.survivallightblocks.Utils.ConfigManager;
import survivallightblocks.survivallightblocks.Utils.RecipeManager;

public final class SurvivalLightBlocks extends JavaPlugin {
    public ConfigManager configManager;
    public RecipeManager recipeManager;
    @Override
    public void onEnable() {
        configManager = new ConfigManager(this);
        recipeManager = new RecipeManager(this);
        this.getCommand("SurvivalLightBlock.reload").setExecutor(new CommandController(this));
        new PlayerHandler(this);
    }

    @Override
    public void onDisable() {
        recipeManager.removeRecipe();
    }
}
