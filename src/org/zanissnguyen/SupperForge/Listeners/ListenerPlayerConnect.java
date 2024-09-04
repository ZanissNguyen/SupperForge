package org.zanissnguyen.SupperForge.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.zanissnguyen.SupperForge.SAttributeUtils;
import org.zanissnguyen.SupperForge.SupperForge;
import org.zanissnguyen.SupperForge.Utils;
import org.zanissnguyen.SupperForge.API.PlayerStat;

public class ListenerPlayerConnect implements Listener
{
	@SuppressWarnings("unused")
	private SupperForge plugin;
	@SuppressWarnings("unused")
	private Utils utils;
	
	public ListenerPlayerConnect(SupperForge plugin, Utils utils) 
	{
		this.plugin = plugin;
		this.utils = utils;
		
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event)
	{
		PlayerStat.updateStat(event.getPlayer());
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event)
	{
		SAttributeUtils.statStorage.remove(event.getPlayer());
	}
}

