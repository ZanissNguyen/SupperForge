package org.zanissnguyen.SupperForge.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.zanissnguyen.SupperForge.SupperForge;
import org.zanissnguyen.SupperForge.Utils;
import org.zanissnguyen.SupperForge.API.PlayerStat;

public class ListenerPlayerDeath implements Listener 
{
	@SuppressWarnings("unused")
	private SupperForge plugin;
	@SuppressWarnings("unused")
	private Utils utils;
	
	public ListenerPlayerDeath(SupperForge plugin, Utils utils) 
	{
		this.plugin = plugin;
		this.utils = utils;
		
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event)
	{
		if (!event.getKeepInventory()) PlayerStat.updateStat(event.getEntity());
	}
}
