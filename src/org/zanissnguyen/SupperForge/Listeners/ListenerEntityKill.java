package org.zanissnguyen.SupperForge.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.zanissnguyen.SupperForge.SAttributeUtils;
import org.zanissnguyen.SupperForge.SupperForge;
import org.zanissnguyen.SupperForge.Utils;

public class ListenerEntityKill implements Listener 
{
	@SuppressWarnings("unused")
	private SupperForge plugin;
	@SuppressWarnings("unused")
	private Utils utils;
	
	public ListenerEntityKill(SupperForge plugin, Utils utils) 
	{
		this.plugin = plugin;
		this.utils = utils;
		
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onKill(EntityDeathEvent event)
	{
		if (event.getEntity().getKiller() instanceof Player)
		{
			Player p = event.getEntity().getKiller();
			double boost = SAttributeUtils.statStorage.get(p).get("xp_boost", false);
			event.setDroppedExp((int) Math.round(event.getDroppedExp()*(1+boost)));
		}
	}
}