package org.zanissnguyen.SupperForge.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseArmorEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.zanissnguyen.SupperForge.SupperForge;
import org.zanissnguyen.SupperForge.Utils;
import org.zanissnguyen.SupperForge.API.PlayerStat;

public class ListenerEquip implements Listener
{
	private SupperForge plugin;
	private Utils utils;
	private String cdText;
	
	public ListenerEquip(SupperForge plugin, Utils utils) 
	{
		this.plugin = plugin;
		this.utils = utils;
		
		cdText = plugin.locale.get("locale.attribute.stat", "on_cooldown");
		
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onSwapHand(PlayerSwapHandItemsEvent event)
	{
		Player p = event.getPlayer();
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
	
	@EventHandler
	public void onChangeSlot(PlayerItemHeldEvent event)
	{
		Player p = event.getPlayer();
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
//				System.out.println("ChangeSlot");
			}
		}, 2);
	}
	
	@EventHandler
	public void onEquipArmor(PlayerInteractEvent event)
	{
		Player p = event.getPlayer();
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
		
		if (!event.hasItem()) return;
		if (event.getAction()==Action.RIGHT_CLICK_AIR || utils.isArmor(event.getItem().getType()))
		{
			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
				
				@Override
				public void run() {
					PlayerStat.updateStat(p);
				}
			}, 2);
		}
	}
	
	@EventHandler
	public void onDragAndClick(InventoryClickEvent event)
	{
		if (event.getSlotType()==SlotType.OUTSIDE) return;
		
		Player p = (Player) event.getWhoClicked();
		
		boolean shift = event.getClick().toString().contains("SHIFT");
		ItemStack clicked = utils.createItem(Material.AIR, 1, 0);
		Inventory inv = event.getClickedInventory();
		if (inv.getItem(event.getSlot())!=null)
		{
			clicked = inv.getItem(event.getSlot());
		}
//		p.sendMessage(clicked.getType().toString());
		
		if (event.getSlot()==p.getInventory().getHeldItemSlot() && event.getSlotType()==SlotType.QUICKBAR)
		{
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
		}
		
		if ((event.getSlotType()==SlotType.ARMOR && (event.getAction()==InventoryAction.PLACE_ALL || event.getAction()==InventoryAction.PICKUP_ALL))
				|| (event.getSlot()==40 && event.getSlotType()==SlotType.QUICKBAR)
				|| (event.getSlot()==p.getInventory().getHeldItemSlot() && event.getSlotType()==SlotType.QUICKBAR
				|| (event.getSlotType()==SlotType.ARMOR && shift)
				|| (utils.isArmor(clicked.getType()) && shift))
			)
		{
			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
				
				@Override
				public void run() {
					PlayerStat.updateStat(p);
//					System.out.println("DragAndClick");
				}
			}, 2);
		}
	}
	
	@EventHandler
	public void dispenseArmorEvent(BlockDispenseArmorEvent event){
		ItemStack item = event.getItem();
		
		if(item != null)
		{
			if(event.getTargetEntity() instanceof Player)
			{
				Player p = (Player) event.getTargetEntity();
				
				Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
					
					@Override
					public void run() {
						PlayerStat.updateStat(p);
//						System.out.println("DragAndClick");
					}
				}, 2);
			}
		}
	}
	
}
