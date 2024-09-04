package org.zanissnguyen.SupperForge.API;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.zanissnguyen.SupperForge.SupperForge;
import org.zanissnguyen.SupperForge.Utils;

public class SUserInterface 
{
	public enum UI_TYPE {ITEM_UI, MATERIAL_UI, RECIPE_UI};
	
	public SupperForge plugin;
	public Utils utils;
	
	public Inventory inv;
	public String name;
	public int rows;
	public Map<Integer, ItemStack> setup;
	
	public ItemStack fill()
	{
		ItemStack fill = createIcon(Material.YELLOW_STAINED_GLASS_PANE, 1, 101, " ", false);
		
		return fill;
	}
	public ItemStack refresh()
	{
		ItemStack refresh = createIcon(Material.PAPER, 1, 101, " ", false);
		
		ItemMeta meta = refresh.getItemMeta();
		String name = plugin.locale.get("locale", "forge", "refresh-icon", "name");
		List<String> lore = plugin.locale.getList("locale", "forge", "refresh-icon", "lore");
		meta.setDisplayName(name);
		meta.setLore(lore);
		refresh.setItemMeta(meta);
		
		return refresh;
	}
	public ItemStack find(String toFind, UI_TYPE type)
	{
		ItemStack find = createIcon(Material.PAPER, 1, 102, " ", false);
		
		ItemMeta meta = find.getItemMeta();
		String name = plugin.locale.get("locale", "forge", "search-icon", "name");
		List<String> got = plugin.locale.getList("locale", "forge", "search-icon", "lore");
		List<String> lore = new ArrayList<>();
		meta.setDisplayName(name);
		String toReplace = "";
		switch(type)
		{
		case ITEM_UI:
			toReplace += SupperForge.itmStorage.searchFor(toFind).size();
			break;
		case MATERIAL_UI:
			toReplace += SupperForge.matStorage.searchFor(toFind).size();
			break;
		case RECIPE_UI:
			toReplace += SupperForge.repStorage.searchFor(toFind).size();
			break;
		default:
			break;
		}
		
		for (String s: got)
		{
			lore.add(s.replace("<w>", toFind).replace("<n>", toReplace));
		}
		meta.setLore(lore);
		find.setItemMeta(meta);
		
		return find;
	}
	public ItemStack addNew()
	{
		ItemStack addNew = createIcon(Material.PAPER, 1, 103, " ", false);
		
//		ItemMeta meta = addNew.getItemMeta();
//		String name = plugin.locale.get("locale", "forge", "add-icon", "name");
//		List<String> lore = plugin.locale.getList("locale", "forge", "add-icon", "lore");
//		meta.setDisplayName(name);
//		for (String s: lore)
//		{
//			
//		}
//		meta.setLore(lore);
//		addNew.setItemMeta(meta);
		
		return addNew;
	}
	public ItemStack nextPage()
	{
		ItemStack nextPage = createIcon(Material.ARROW, 1, 101, " ", false);
		
		ItemMeta meta = nextPage.getItemMeta();
		String name = plugin.locale.get("locale", "forge", "next-icon", "name");
		List<String> lore = plugin.locale.getList("locale", "forge", "next-icon", "lore");
		meta.setDisplayName(name);
		meta.setLore(lore);
		nextPage.setItemMeta(meta);
		
		return nextPage;
	}
	public ItemStack previousPage()
	{
		ItemStack previousPage = createIcon(Material.ARROW, 1, 102, " ", false);
		
		ItemMeta meta = previousPage.getItemMeta();
		String name = plugin.locale.get("locale", "forge", "previous-icon", "name");
		List<String> lore = plugin.locale.getList("locale", "forge", "previous-icon", "lore");
		meta.setDisplayName(name);
		meta.setLore(lore);
		previousPage.setItemMeta(meta);
		
		return previousPage;
	}
	
	public SUserInterface(SupperForge plugin, Utils utils, String name, int rows)
	{
		this.plugin = plugin;
		this.utils = utils;
		
		this.name = utils.color(name);
		this.rows = rows;
		this.inv = Bukkit.createInventory(null, this.rows*9, this.name);
	}
	
	public void open(Player p)
	{
		p.openInventory(this.inv);
	}
	
	public ItemStack createIcon(Material material, int amount, int data, String name, Boolean glow, String... args)
	{
		ItemStack result = new ItemStack(material, amount);
		
		ItemMeta meta = result.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		
		meta.setCustomModelData(data);
		meta.setDisplayName(utils.color(name));
		List<String> lore = new ArrayList<>();
		for(String s: args)
		{
			lore.add(utils.color(s));
		}
		meta.setLore(lore);
		if (glow)
		{
			meta.addEnchant(Enchantment.LUCK, 1, false);
		}
		result.setItemMeta(meta);
	
		return result;
	}
	
	public ItemStack loadIcon(SItem item, boolean tips)
	{
		ItemStack result = item.convertToItemStack(true, false);
		
		if (tips)
		{
			ItemMeta meta = result.getItemMeta();
			meta.setDisplayName(utils.color(item.name+" &e(#"+item.id+")"));
			List<String> lore = utils.getLore(result);
			
			List<String> toAdd = plugin.locale.fileConfig.getStringList("locale.forge.list-tips");
			for (String s: toAdd) lore.add(utils.color(s));
			meta.setLore(lore);
			result.setItemMeta(meta);
		}
		
		return result;
	}
	
	public ItemStack loadIcon(SMaterial item, boolean tips)
	{
		ItemStack result = item.convertToItemStack(true);
		
		if (tips)
		{
			ItemMeta meta = result.getItemMeta();
			List<String> lore = utils.getLore(result);
			
			List<String> toAdd = plugin.locale.getList("locale", "forge", "list-tips");
			lore.addAll(toAdd);
			meta.setLore(lore);
			result.setItemMeta(meta);
		}
		
		return result;
	}
}
