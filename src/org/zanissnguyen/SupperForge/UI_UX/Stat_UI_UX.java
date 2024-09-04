package org.zanissnguyen.SupperForge.UI_UX;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.zanissnguyen.SupperForge.SAttributeUtils;
import org.zanissnguyen.SupperForge.SupperForge;
import org.zanissnguyen.SupperForge.Utils;
import org.zanissnguyen.SupperForge.API.PlayerStat;
import org.zanissnguyen.SupperForge.API.SUserInterface;

public class Stat_UI_UX extends SUserInterface implements Listener
{
	private SupperForge plugin;
	private Utils utils;
	private SAttributeUtils psUtils;
	
	private String nameFormat = "";
	private ItemStack nothing;
	
	public Stat_UI_UX(SupperForge plugin, Utils utils) {
		super(plugin, utils, "", 6);
		this.plugin = plugin;
		this.utils = utils;
		this.psUtils = SupperForge.psUtils;
		
		nothing = utils.createItem(Material.AIR, 1, 0);
		nameFormat = plugin.locale.get("locale", "forge", "stat", "ui-name");
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	private ItemStack physic(Player p)
	{
		ItemStack result = utils.createItem(Material.IRON_SWORD, 1, 101, "");
		String name = plugin.locale.get("locale", "forge", "stat", "physic-name");
		List<String> format = SupperForge.format.getUIPhysicFormat();
		format = unformatted(p, format);
		
		ItemMeta meta = result.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(format);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		result.setItemMeta(meta);
		
		return result;
	}
	
	private ItemStack magic(Player p)
	{
		ItemStack result = utils.createItem(Material.BLAZE_POWDER, 1, 101, "");
		String name = plugin.locale.get("locale", "forge", "stat", "magic-name");
		List<String> format = SupperForge.format.getUIMagicFormat();
		format = unformatted(p, format);
		
		ItemMeta meta = result.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(format);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		result.setItemMeta(meta);
		
		return result;
	}
	
	private ItemStack defense(Player p)
	{
		ItemStack result = utils.createItem(Material.IRON_CHESTPLATE, 1, 101, "");
		String name = plugin.locale.get("locale", "forge", "stat", "defense-name");
		List<String> format = SupperForge.format.getUIDefenseFormat();
		format = unformatted(p, format);
		
		ItemMeta meta = result.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(format);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		result.setItemMeta(meta);
		
		return result;
	}
	
	private ItemStack misc(Player p)
	{
		ItemStack result = utils.createItem(Material.ENDER_PEARL, 1, 101, "");
		String name = plugin.locale.get("locale", "forge", "stat", "misc-name");
		List<String> format = SupperForge.format.getUIMiscFormat();
		format = unformatted(p, format);
		
		ItemMeta meta = result.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(format);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		result.setItemMeta(meta);
		
		return result;
	}
	
	private ItemStack buff(Player p)
	{
		ItemStack result = utils.createItem(Material.GLASS_BOTTLE, 1, 101, "?");
//		String name = plugin.locale.get("locale", "forge", "stat", "misc-name");
//		List<String> format = SupperForge.format.getUIMiscFormat();
//		format = unformatted(p, format);
//		
//		ItemMeta meta = result.getItemMeta();
//		meta.setDisplayName(name);
//		meta.setLore(format);
//		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
//		result.setItemMeta(meta);
		
		return result;
	}
	
	private ItemStack skill(Player p)
	{
		ItemStack result = utils.createItem(Material.BOOK, 1, 101, "?");
//		String name = plugin.locale.get("locale", "forge", "stat", "misc-name");
//		List<String> format = SupperForge.format.getUIMiscFormat();
//		format = unformatted(p, format);
//		
//		ItemMeta meta = result.getItemMeta();
//		meta.setDisplayName(name);
//		meta.setLore(format);
//		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
//		result.setItemMeta(meta);
		
		return result;
	}
	
	private ItemStack balance(Player p)
	{	
		ItemStack result = utils.createItem(Material.CHEST, 1, 101, "?");
//		String name = plugin.locale.get("locale", "forge", "stat", "misc-name");
//		List<String> format = SupperForge.format.getUIMiscFormat();
//		format = unformatted(p, format);
//		
//		ItemMeta meta = result.getItemMeta();
//		meta.setDisplayName(name);
//		meta.setLore(format);
//		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
//		result.setItemMeta(meta);
		
		return result;
	}
	
	private ItemStack helmetSlot(Player p)
	{
		if (p.getEquipment().getHelmet()!=null)
		{
			return p.getEquipment().getHelmet();
		}
		
		ItemStack result = utils.createItem(Material.BLACK_STAINED_GLASS_PANE, 1, 101, " ");
		String name = plugin.locale.get("locale", "forge", "stat", "empty-slot", "name");
		List<String> format = plugin.locale.getList("locale", "forge", "stat", "empty-slot", "lore");
		List<String> lore = new ArrayList<>();
		
		for (String s: format)
		{
			lore.add(s.replace("<type>", getSlotType("helmet")));
		}
		
		ItemMeta meta = result.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lore);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		result.setItemMeta(meta);
		
		return result;
	}
	
	private ItemStack chestplateSlot(Player p)
	{
		if (p.getEquipment().getChestplate()!=null)
		{
			return p.getEquipment().getChestplate();
		}
		
		ItemStack result = utils.createItem(Material.BLACK_STAINED_GLASS_PANE, 1, 102, " ");
		String name = plugin.locale.get("locale", "forge", "stat", "empty-slot", "name");
		List<String> format = plugin.locale.getList("locale", "forge", "stat", "empty-slot", "lore");
		List<String> lore = new ArrayList<>();
		
		for (String s: format)
		{
			lore.add(s.replace("<type>", getSlotType("chestplate")));
		}
		
		ItemMeta meta = result.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lore);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		result.setItemMeta(meta);
		
		return result;
	}
	
	private ItemStack leggingsSlot(Player p)
	{
		if (p.getEquipment().getLeggings()!=null)
		{
			return p.getEquipment().getLeggings();
		}
		
		ItemStack result = utils.createItem(Material.BLACK_STAINED_GLASS_PANE, 1, 103, " ");
		String name = plugin.locale.get("locale", "forge", "stat", "empty-slot", "name");
		List<String> format = plugin.locale.getList("locale", "forge", "stat", "empty-slot", "lore");
		List<String> lore = new ArrayList<>();
		
		for (String s: format)
		{
			lore.add(s.replace("<type>", getSlotType("leggings")));
		}
		
		ItemMeta meta = result.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lore);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		result.setItemMeta(meta);
		
		return result;
	}
	
	private ItemStack bootsSlot(Player p)
	{
		if (p.getEquipment().getBoots()!=null)
		{
			return p.getEquipment().getBoots();
		}
		
		ItemStack result = utils.createItem(Material.BLACK_STAINED_GLASS_PANE, 1, 104, " ");
		String name = plugin.locale.get("locale", "forge", "stat", "empty-slot", "name");
		List<String> format = plugin.locale.getList("locale", "forge", "stat", "empty-slot", "lore");
		List<String> lore = new ArrayList<>();
		
		for (String s: format)
		{
			lore.add(s.replace("<type>", getSlotType("boots")));
		}
		
		ItemMeta meta = result.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lore);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		result.setItemMeta(meta);
		
		return result;
	}
	
	private ItemStack ringSlot(Player p, int slot)
	{
		ItemStack ring = plugin.storage.getRingSlot(p, slot);
		if (ring==null || ring.getType()==Material.AIR)
		{
			ItemStack result = utils.createItem(Material.BLACK_STAINED_GLASS_PANE, 1, 105, " ");
			String name = plugin.locale.get("locale", "forge", "stat", "empty-slot", "name");
			List<String> format = plugin.locale.getList("locale", "forge", "stat", "empty-slot", "lore");
			List<String> lore = new ArrayList<>();
			
			for (String s: format)
			{
				lore.add(s.replace("<type>", getSlotType("ring")));
			}
			
			ItemMeta meta = result.getItemMeta();
			meta.setDisplayName(name);
			meta.setLore(lore);
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			result.setItemMeta(meta);
			
			return result;
		}
		else return ring;
		
	}
	
	private ItemStack beltSlot(Player p)
	{
		ItemStack belt = plugin.storage.getBeltSlot(p);
		if (belt==null || belt.getType()==Material.AIR)
		{
			ItemStack result = utils.createItem(Material.BLACK_STAINED_GLASS_PANE, 1, 106, " ");
			String name = plugin.locale.get("locale", "forge", "stat", "empty-slot", "name");
			List<String> format = plugin.locale.getList("locale", "forge", "stat", "empty-slot", "lore");
			List<String> lore = new ArrayList<>();
			
			for (String s: format)
			{
				lore.add(s.replace("<type>", getSlotType("belt")));
			}
			
			ItemMeta meta = result.getItemMeta();
			meta.setDisplayName(name);
			meta.setLore(lore);
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			result.setItemMeta(meta);
			
			return result;
		}
		else return belt;
	}
	
	private ItemStack mainHandSlot(Player p)
	{
		if (p.getEquipment().getItemInMainHand()!=null && p.getEquipment().getItemInMainHand().getType()!=Material.AIR)
		{
			return p.getEquipment().getItemInMainHand();
		}
		
		ItemStack result = utils.createItem(Material.BLACK_STAINED_GLASS_PANE, 1, 107, " ");
		String name = plugin.locale.get("locale", "forge", "stat", "empty-slot", "name");
		List<String> format = plugin.locale.getList("locale", "forge", "stat", "empty-slot", "lore");
		List<String> lore = new ArrayList<>();
		
		for (String s: format)
		{
			lore.add(s.replace("<type>", getSlotType("mainhand")));
		}
		
		ItemMeta meta = result.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lore);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		result.setItemMeta(meta);
		
		return result;
	}
	
	private ItemStack gauntletSlot(Player p)
	{
		ItemStack gauntlet = plugin.storage.getGauntletSlot(p);
		if (gauntlet==null || gauntlet.getType()==Material.AIR)
		{
			ItemStack result = utils.createItem(Material.BLACK_STAINED_GLASS_PANE, 1, 108, " ");
			String name = plugin.locale.get("locale", "forge", "stat", "empty-slot", "name");
			List<String> format = plugin.locale.getList("locale", "forge", "stat", "empty-slot", "lore");
			List<String> lore = new ArrayList<>();
			
			for (String s: format)
			{
				lore.add(s.replace("<type>", getSlotType("gauntlet")));
			}
			
			ItemMeta meta = result.getItemMeta();
			meta.setDisplayName(name);
			meta.setLore(lore);
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			result.setItemMeta(meta);
			
			return result;
		}
		else return gauntlet;
	}
	
	private ItemStack artifactSlot(Player p, int slot)
	{
		ItemStack artifact = plugin.storage.getArtifactSlot(p, slot);
		if (artifact==null || artifact.getType()==Material.AIR)
		{
			ItemStack result = utils.createItem(Material.BLACK_STAINED_GLASS_PANE, 1, 109, " ");
			String name = plugin.locale.get("locale", "forge", "stat", "empty-slot", "name");
			List<String> format = plugin.locale.getList("locale", "forge", "stat", "empty-slot", "lore");
			List<String> lore = new ArrayList<>();
			
			for (String s: format)
			{
				lore.add(s.replace("<type>", getSlotType("artifact")));
			}
			
			ItemMeta meta = result.getItemMeta();
			meta.setDisplayName(name);
			meta.setLore(lore);
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			result.setItemMeta(meta);
			
			return result;
		}
		else return artifact;
	}
	
	private ItemStack necklaceSlot(Player p)
	{
		ItemStack necklace = plugin.storage.getNecklaceSlot(p);
		if (necklace==null || necklace.getType()==Material.AIR)
		{
			ItemStack result = utils.createItem(Material.BLACK_STAINED_GLASS_PANE, 1, 110, " ");
			String name = plugin.locale.get("locale", "forge", "stat", "empty-slot", "name");
			List<String> format = plugin.locale.getList("locale", "forge", "stat", "empty-slot", "lore");
			List<String> lore = new ArrayList<>();
			
			for (String s: format)
			{
				lore.add(s.replace("<type>", getSlotType("necklace")));
			}
			
			ItemMeta meta = result.getItemMeta();
			meta.setDisplayName(name);
			meta.setLore(lore);
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			result.setItemMeta(meta);
			
			return result;
		}
		else return necklace;
	}
	
	private ItemStack offHandSlot(Player p)
	{
		if (p.getEquipment().getItemInOffHand()!=null && p.getEquipment().getItemInOffHand().getType()!=Material.AIR)
		{
//			p.sendMessage(p.getEquipment().getItemInOffHand().toString());
			return p.getEquipment().getItemInOffHand();
		}
		
		ItemStack result = utils.createItem(Material.BLACK_STAINED_GLASS_PANE, 1, 111, " ");
		String name = plugin.locale.get("locale", "forge", "stat", "empty-slot", "name");
		List<String> format = plugin.locale.getList("locale", "forge", "stat", "empty-slot", "lore");
		List<String> lore = new ArrayList<>();
		
		for (String s: format)
		{
			lore.add(s.replace("<type>", getSlotType("offhand")));
		}
		
		ItemMeta meta = result.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lore);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		result.setItemMeta(meta);
		
		return result;
	}
	
	private String getSlotType(String type)
	{
		String toReplace = plugin.locale.get("item-type", type);
		String typeFormat = SupperForge.format.getTypeFormat().replace("<type>", toReplace);
		
		return typeFormat;
	}
	
	private List<String> unformatted(Player p, List<String> gotFormat)
	{
		List<String> result = new ArrayList<>();
		for (String s: gotFormat)
		{
			if (s.contains("<") && s.contains(">"))
			{
				String key = s.substring(s.indexOf("<")+1, (s.indexOf(">")-s.indexOf("<")));
//				p.sendMessage(key);
				if (psUtils.isStat(key))
				{
					result.add(psUtils.getLoreStat(key, SAttributeUtils.statStorage.get(p).get(key, true)+"", false));
				}
				else
				{
					result.add(utils.color("&c+ "+key +" is not a stat. Change in format.yml"));
				}
			} else result.add(utils.color(s));
		}
		
		return result;
	}
	
	public Inventory setUp(Player p)
	{
		String new_name = nameFormat.replace("<player>", p.getName());
		Inventory result = Bukkit.createInventory(null, this.rows*9, new_name);
		
		//basic frame
		for (int i = 0; i<rows*9; i++)
		{
			result.setItem(i, fill());
		}
		
		result.setItem(1, ringSlot(p, 1));
		result.setItem(3, artifactSlot(p, 1));
		result.setItem(4, helmetSlot(p));
		result.setItem(5, artifactSlot(p, 2));
		result.setItem(7, physic(p));
		
		result.setItem(10, ringSlot(p, 2));
		result.setItem(12, gauntletSlot(p));
		result.setItem(13, chestplateSlot(p));
		result.setItem(14, necklaceSlot(p));
		result.setItem(16, magic(p));
		
		result.setItem(19, ringSlot(p, 3));
		result.setItem(21, mainHandSlot(p));
		result.setItem(22, leggingsSlot(p));
		result.setItem(23, offHandSlot(p));
		result.setItem(25, defense(p));
		
		result.setItem(28, ringSlot(p, 4));
		result.setItem(31, bootsSlot(p));
		result.setItem(34, misc(p));
		
		result.setItem(37, ringSlot(p, 5));
		result.setItem(43, buff(p));
		
		result.setItem(46, beltSlot(p));
		result.setItem(49, balance(p));
		result.setItem(52, skill(p));
		
		return result;
	}

	public void open(Player p) 
	{
		p.openInventory(this.setUp(p));
	}
	
	@EventHandler
	public void onEsc(InventoryCloseEvent event)
	{
		
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event)
	{
		InventoryView clicked = event.getView();
		Player p = (Player) event.getWhoClicked();
		String format = plugin.locale.get("locale.forge.stat.ui-name").replace("<player>", p.getName());
		
		if (clicked.getTitle().equalsIgnoreCase(format) && !(event.getSlotType()==SlotType.OUTSIDE))
		{
			ItemStack cursor = event.getCursor();
			ItemStack click = event.getCurrentItem();
			int slot = event.getRawSlot();
			
			if (slot>=rows*9 && slot == 81+p.getInventory().getHeldItemSlot())
			{
				event.setCancelled(true);
				return;
			}
			
			if (slot<rows*9 && 
					(click==null || click.isSimilar(fill()) ||
					slot==7 || slot == 16 || slot == 25 || slot == 34 || slot == 43
					|| slot == 52 || slot ==49)
				)
			{
				event.setCancelled(true);
				return;
			}
			
			// hold something
			if (slot>=rows*9) return;
			
			if (cursor!=null && cursor.getType()!=Material.AIR)
			{
				// is Valid Cursor for slot
				if (isValidCursor(slot, cursor))
				{
					// check if slot have item
					if (isEmptySlot(click))
					{
//						p.sendMessage("filled valid cursor and empty slot");
						swapItem(p, slot, clicked, cursor, nothing);
					}
					else
					{
//						p.sendMessage("filled valid cursor and filled slot");
						swapItem(p, slot, clicked, cursor, click);
					}
				}
			}
			else
			{
				if (!isEmptySlot(click))
				{
//					p.sendMessage("empty cursor and slot filled");
					tookItem(p, slot, clicked);
				}
			}
				
			event.setCancelled(true);
			return;
		}
	}
	
	private void tookItem(Player p, int takenSlot, InventoryView inv)
	{
		ItemStack empty = nothing;
				
		switch (takenSlot) 
		{
		case 4:
//			p.getInventory().setHelmet(nothing);
			PlayerStat.updateHelmetSlot(nothing, p, false);
			empty = helmetSlot(p);
			break;
					
		case 13:
//			p.getInventory().setChestplate(nothing);
			PlayerStat.updateChestplateSlot(nothing, p, false);
			empty = chestplateSlot(p);
			break;
					
		case 22:
//			p.getInventory().setLeggings(nothing);
			PlayerStat.updateLeggingsSlot(nothing, p, false);
			empty = leggingsSlot(p);
			break;
					
		case 23:
//			p.getInventory().setItemInOffHand(nothing);
			PlayerStat.updateOffhandSlot(nothing, p, false);
			empty = offHandSlot(p);
			break;
				
		case 31:
//			p.getInventory().setBoots(nothing);
			PlayerStat.updateBootsSlot(nothing, p, false);
			empty = bootsSlot(p);
			break;
		
		case 21:
			PlayerStat.updateMainhandSlot(nothing, p, false);
			empty = mainHandSlot(p);
			break;
	
		case 46: 
//			plugin.storage.setBeltSlot(p, nothing);
			PlayerStat.updateBeltSlot(nothing, p, false);
			empty = beltSlot(p);
			break;
			
		case 12: 
//			plugin.storage.setGauntletSlot(p, nothing);
			PlayerStat.updateGauntletSlot(nothing, p, false);
			empty = gauntletSlot(p);
			break;
			
		case 14: 
//			plugin.storage.setNecklaceSlot(p, nothing);
			PlayerStat.updateNecklaceSlot(nothing, p, false);
			empty = necklaceSlot(p);
			break;
			
		case 1: 
		case 10:
		case 19:
		case 28:
		case 37:
			PlayerStat.updateRingSlot(nothing, p, (takenSlot/9)+1, false);
//			plugin.storage.setRingSlot(p, (takenSlot/9)+1, nothing);
			empty = ringSlot(p, (takenSlot/9)+1);
			break;
			
		case 3:
		case 5:
			PlayerStat.updateArtifactSlot(nothing, p, (takenSlot==3)?1:2, false);
//			plugin.storage.setArtifactSlot(p, (takenSlot==3) ? 1 : 2, nothing);
			empty = artifactSlot(p, (takenSlot==3)?1:2 );
			break;
			
		default:
			break;
		}
		p.setItemOnCursor(inv.getItem(takenSlot));
		inv.setItem(takenSlot, empty);
		
//		PlayerStat.updateStat(p);
		updateStat(inv, p);
	}
	
	private void swapItem(Player p, int swapSlot, InventoryView inv, ItemStack cursor, ItemStack clicked)
	{
		switch (swapSlot) 
		{
		case 4:
			PlayerStat.updateHelmetSlot(cursor, p, false);
			break;
					
		case 13:
			PlayerStat.updateChestplateSlot(cursor, p, false);
			break;
					
		case 22:
			PlayerStat.updateLeggingsSlot(cursor, p, false);
			break;
					
		case 23:
			PlayerStat.updateOffhandSlot(cursor, p, false);
			break;
				
		case 31:
			PlayerStat.updateBootsSlot(cursor, p, false);
			break;
			
		case 21:
			PlayerStat.updateMainhandSlot(cursor, p, false);
			break;
			
		case 46: 
			PlayerStat.updateBeltSlot(cursor, p, false);
//			plugin.storage.setBeltSlot(p, cursor);
			break;
			
		case 12: 
			PlayerStat.updateGauntletSlot(cursor, p, false);
//			plugin.storage.setGauntletSlot(p, cursor);
			break;
			
		case 14: 
			PlayerStat.updateNecklaceSlot(cursor, p, false);
//			plugin.storage.setNecklaceSlot(p, cursor);
			break;
			
		case 1: 
		case 10:
		case 19:
		case 28:
		case 37:
			PlayerStat.updateRingSlot(cursor, p, (swapSlot/9)+1, false);
//			plugin.storage.setRingSlot(p, (swapSlot/9)+1, cursor);
			break;
			
		case 3:
		case 5:
			PlayerStat.updateArtifactSlot(cursor, p, (swapSlot==3)?1:2, false);
//			plugin.storage.setArtifactSlot(p, (swapSlot==3) ? 1 : 2, cursor);
			break;
			
		default:
			break;
		}
		
		inv.setItem(swapSlot, cursor);
		p.setItemOnCursor(clicked);
		
//		PlayerStat.updateStat(p);
		updateStat(inv, p);
	}
	
	private boolean isEmptySlot(ItemStack item)
	{
		if (item==null) return false;
		if (item.getType()==Material.AIR) return false;
		if (item.getItemMeta()==null) return false;
		if (item.getItemMeta().getDisplayName()==null) return false;
		
		String name = plugin.locale.get("locale", "forge", "stat", "empty-slot", "name");
		return (item.getItemMeta().getDisplayName().equalsIgnoreCase(name));
	}
	
	private void updateStat(InventoryView inv, Player p)
	{
		inv.setItem(7, physic(p));
		inv.setItem(16, magic(p));
		inv.setItem(25, defense(p));
		inv.setItem(34, misc(p));
		inv.setItem(43, buff(p));
		inv.setItem(49, balance(p));
		inv.setItem(52, skill(p));
	}
	
	private boolean isValidCursor(int rawSlot, ItemStack toCheck)
	{
		switch (rawSlot) 
		{
		case 4:
			return toCheck.getType().toString().contains("HELMET") || psUtils.getType(toCheck).equalsIgnoreCase("helmet");
		case 13:
			return toCheck.getType().toString().contains("CHESTPLATE") || psUtils.getType(toCheck).equalsIgnoreCase("chestplate");
		case 22:
			return toCheck.getType().toString().contains("CHESTPLATE") || psUtils.getType(toCheck).equalsIgnoreCase("leggings");
		case 21:
		case 23:
			return true;
		case 31:
			return toCheck.getType().toString().contains("BOOTS") || psUtils.getType(toCheck).equalsIgnoreCase("boots");
		case 46: 
			return psUtils.getType(toCheck).equalsIgnoreCase("belt");
		case 12: 
			return psUtils.getType(toCheck).equalsIgnoreCase("gauntlet");
		case 14: 
			return psUtils.getType(toCheck).equalsIgnoreCase("necklace");
		case 1: 
		case 10:
		case 19:
		case 28:
		case 37:
			return psUtils.getType(toCheck).equalsIgnoreCase("ring");
		case 3:
		case 5:
			return psUtils.getType(toCheck).equalsIgnoreCase("artifact");
		
		default:
			break;
		}
		
		return false;
	}
	
}
