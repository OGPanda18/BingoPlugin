package bongus.bingo;

import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class BingoEvents implements Listener {

    Bingo plugin;

    public BingoEvents(Bingo plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void inventoryInteract(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();

        if(e.getClickedInventory() == plugin.getPlayerBoards().get(e.getWhoClicked().getUniqueId()).getPersonalBoard()){
            e.setCancelled(true);
        }else{
            if(e.getCurrentItem() == null) return;
            if(plugin.getPlayerBoards().get(p.getUniqueId()).checkOff(e.getCurrentItem().getType())) p.playSound(p, Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
        }
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if(!plugin.getPlayerBoards().containsKey(p.getUniqueId())) {
            plugin.getPlayerBoards().put(p.getUniqueId(), new BingoBoard(p.getUniqueId()));
        }

    }

    @EventHandler
    public void pickupItem(EntityPickupItemEvent e){
        Player p = (Player) e.getEntity();
        if(plugin.getPlayerBoards().get(p.getUniqueId()).checkOff(e.getItem().getItemStack().getType())) p.playSound(p, Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
    }



    @EventHandler
    public void rightClick(PlayerInteractEvent e){
        Player p = e.getPlayer();

        if(e.getItem() == null){ return; }

        if(e.getItem().getType() == Material.KNOWLEDGE_BOOK){
            plugin.getPlayerBoards().get(p.getUniqueId()).openPersonalBoard();
            e.setCancelled(true);
        }else{
            if(plugin.getPlayerBoards().get(p.getUniqueId()).checkOff(e.getItem().getType())) p.playSound(p, Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
        }
    }

    @EventHandler
    public void gameRespawn(PlayerRespawnEvent e){
        if(Bingo.gameActive && plugin.getGameLocation() != null) e.setRespawnLocation(plugin.getGameLocation());
    }



}
