package ca.loushunt.simpletrash;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	@Override
	public void onEnable() {
		saveDefaultConfig();
		getConfig().options().copyDefaults(true);
		saveConfig();
		System.out.println("SimpleTrash v1 by LouShunt");
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void OnInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			String[] item = getConfig().getString("trash.block").split(":");
			int id = Integer.parseInt(item[0]);
			int durability = Integer.parseInt(item[1]);
			if(p.isSneaking()) return;
			if(e.getClickedBlock().getTypeId() == id) {
				if(e.getClickedBlock().getData() == durability) {
					ItemStack hand = p.getItemInHand();
					if(!(getConfig().getStringList("trash.blacklist-item").contains(hand.getTypeId()+":"+hand.getDurability())) && p.getItemInHand().getType() != Material.AIR) {
						e.setCancelled(true);
						p.setItemInHand(new ItemStack(Material.AIR, 1));
						String message = ChatColor.translateAlternateColorCodes('&', getConfig().getString("trash.message"));
						p.sendMessage(message);
					}else return;
				}else return;
			}else return;
		}
	}
}
