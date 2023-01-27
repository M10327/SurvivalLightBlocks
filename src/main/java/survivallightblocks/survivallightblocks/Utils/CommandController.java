package survivallightblocks.survivallightblocks.Utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import survivallightblocks.survivallightblocks.SurvivalLightBlocks;

public class CommandController implements CommandExecutor {
    private final SurvivalLightBlocks plugin;
    public CommandController(SurvivalLightBlocks plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && sender.isOp()) {
            plugin.configManager.reload();
        }
        return false;
    }
}
