package survivallightblocks.survivallightblocks.Handlers;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.block.data.type.Light;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import survivallightblocks.survivallightblocks.SurvivalLightBlocks;

public class PlayerHandler implements Listener {
    public PlayerHandler(SurvivalLightBlocks plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void playerSwapItemEvent(PlayerItemHeldEvent event){
        Player p = event.getPlayer();
        if (p.isSneaking()){
            if (p.getInventory().getItem(event.getPreviousSlot()) != null){
                ItemStack item = p.getInventory().getItem(event.getPreviousSlot());
                assert item != null;
                if (item.getType().equals(Material.LIGHT)){
                    event.setCancelled(true);
                    if (item.getItemMeta() == null){
                        return;
                    }
                    int level;
                    NBTItem nbti = new NBTItem(item);

                    NBTCompound comp = nbti.getCompound("BlockStateTag");
                    if (comp == null){
                        nbti.mergeCompound(new NBTContainer("{BlockStateTag: {level: 15}}"));
                        p.getInventory().setItemInMainHand(nbti.getItem());
                    }
                    else{
                        int newSlot = event.getNewSlot();
                        int lastSlot = event.getPreviousSlot();
                        level = comp.getInteger("level");
                        if (lastSlot == 0 && newSlot == 8){
                            newSlot = -1;
                        }
                        if (newSlot == 0 && lastSlot == 8){
                            newSlot = 9;
                        }

                        if (newSlot > lastSlot){
                            if (level == 0){
                                level = 15;
                            }
                            else{
                                level--;
                            }
                        }
                        else{
                            if (level == 15){
                                level = 0;
                            }
                            else{
                                level++;
                            }
                        }
                        String nbtString = "{BlockStateTag: {level: " + level + "}}";
                        nbti.mergeCompound(new NBTContainer(nbtString));
                        p.getInventory().setItemInMainHand(nbti.getItem());
                    }
                }
            }
        }
    }

    @EventHandler
    private void PlayerLeftClick(PlayerInteractEvent event){
        Block block = event.getClickedBlock();
        Player player = event.getPlayer();
        if (block == null){
            return;
        }
        if (event.getAction() != Action.LEFT_CLICK_BLOCK){
            return;
        }
        if (event.getHand() != EquipmentSlot.HAND){
            return;
        }
        if (event.getItem() == null){
            return;
        }
        if (event.getItem().getType() != Material.LIGHT){
            return;
        }
        if (event.getPlayer().getGameMode() != GameMode.SURVIVAL){
            return;
        }
        if (block.getType() == Material.LIGHT){
            if (CanBreakBlock(block, player)){
                block.getWorld().playSound(block.getLocation(), Sound.BLOCK_GLASS_BREAK, 0.5F, 1.0F);
                block.getWorld().playEffect(block.getLocation(), Effect.EXTINGUISH, 0);
                // item that drops when broken
                Levelled light = (Levelled) block.getBlockData();
                ItemStack item = new ItemStack(Material.LIGHT,1);
                NBTItem nbti = new NBTItem(item);
                nbti.mergeCompound(new NBTContainer("{BlockStateTag: {level: " + light.getLevel() + "}}"));
                block.getWorld().dropItemNaturally(block.getLocation(), nbti.getItem());
                // end item
                block.breakNaturally();
            }
        }
    }

    private boolean CanBreakBlock(Block block, Player player){
        BlockBreakEvent event = new BlockBreakEvent(block, player);
        Bukkit.getPluginManager().callEvent(event);
        return !event.isCancelled();
    }
}
