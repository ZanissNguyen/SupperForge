package org.zanissnguyen.SupperForge.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.zanissnguyen.SupperForge.SupperForge;
import org.zanissnguyen.SupperForge.Utils;
import org.zanissnguyen.SupperForge.API.PlayerStat;

public class ListenerDurabilityRunout implements Listener 
{
	private SupperForge plugin;
	@SuppressWarnings("unused")
	private Utils utils;
	
	public ListenerDurabilityRunout(SupperForge plugin, Utils utils) 
	{
		this.plugin = plugin;
		this.utils = utils;
		
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onItemBreak(PlayerItemBreakEvent event)
	{
		Player p = event.getPlayer();
		
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			
			@Override
			public void run() {
				PlayerStat.updateStat(p);
			}
		}, 2);
	}
}