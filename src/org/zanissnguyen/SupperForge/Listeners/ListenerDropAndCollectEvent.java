package org.zanissnguyen.SupperForge.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.zanissnguyen.SupperForge.SupperForge;
import org.zanissnguyen.SupperForge.Utils;
import org.zanissnguyen.SupperForge.API.PlayerStat;

@SuppressWarnings("deprecation")
public class ListenerDropAndCollectEvent  implements Listener 
{
	private SupperForge plugin;
	@SuppressWarnings("unused")
	private Utils utils;
	private String cdText;
	
	public ListenerDropAndCollectEvent(SupperForge plugin, Utils utils) 
	{
		this.plugin = plugin;
		this.utils = utils;
		
		cdText = plugin.locale.get("locale.attribute.stat", "on_cooldown");
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent event)
	{
		Player p = event.getPlayer();
		ItemStack droped = event.getItemDrop().getItemStack();
		
		
		
		if (droped.hasItemMeta() && p.getInventory().getItemInMainHand().getType()==Material.AIR)
		{
//			p.sendMessage(droped.toString());
//			p.sendMessage(p.getInventory().getItemInMainHand().toString());
//			
//			p.sendMessage((droped.isSimilar(p.getInventory().getItemInMainHand())?"Yess":"No"));
			
			if (ListenerDamageEvent.isOnCooldown(p)) 
			{
				if (ListenerDamageEvent.cooldownMsg && !ListenerDamageEvent.onCooldown.get(p)) 
				{
					ListenerDamageEvent.onCooldown.put(p, true);
					p.sendMessage(cdText);
				}
				event.setCancelled(true);
				return;
			}
			
			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
				
				@Override
				public void run() {
					PlayerStat.updateStat(p);
				}
			}, 2);
		}
	}
	
	@EventHandler
	public void onPick(PlayerPickupItemEvent event)
	{
		Player p =event.getPlayer();
		ItemStack picked = event.getItem().getItemStack();
		ItemStack mainhand = p.getInventory().getItemInMainHand();
		
		if (mainhand!=null && mainhand.getType()!=Material.AIR) return;
		
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			
			@Override
			public void run() {
				if (picked.isSimilar(p.getInventory().getItemInMainHand()))
				{
//					p.sendMessage("yes");
					PlayerStat.updateStat(p);
				}
			}
		}, 2);
	}
}
