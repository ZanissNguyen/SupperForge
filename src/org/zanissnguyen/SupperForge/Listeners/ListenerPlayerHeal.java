package org.zanissnguyen.SupperForge.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.zanissnguyen.SupperForge.SAttributeUtils;
import org.zanissnguyen.SupperForge.SupperForge;
import org.zanissnguyen.SupperForge.Utils;

public class ListenerPlayerHeal implements Listener 
{
	@SuppressWarnings("unused")
	private SupperForge plugin;
	@SuppressWarnings("unused")
	private Utils utils;
	
	public ListenerPlayerHeal(SupperForge plugin, Utils utils) 
	{
		this.plugin = plugin;
		this.utils = utils;
		
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onHeal(EntityRegainHealthEvent event)
	{
		if (event.getEntityType()==EntityType.PLAYER)
		{
			Player p = (Player) event.getEntity();
			double heal = SAttributeUtils.statStorage.get(p).get("heal", false);
			event.setAmount(event.getAmount()*(1+heal));
		}
	}
}