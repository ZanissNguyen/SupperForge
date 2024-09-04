package org.zanissnguyen.SupperForge.API;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.zanissnguyen.SupperForge.SupperForge;
import org.zanissnguyen.SupperForge.Utils;

public class SMaterial 
{
	private Utils utils;
	
	public String id;
	public String type;
	public String name;
	public int data;
	public List<String> lore;
	public Boolean unbreakable;
	public Boolean glow;
	
	public SMaterial(String id, String type, int data, String name, List<String> lore, Boolean glow, Boolean unbreakable)
	{
		utils = SupperForge.utils;
		
		this.id = id;
		this.type = type;
		this.data = data;
		this.name = name;
		this.lore = lore;
		this.glow = glow;
		this.unbreakable = unbreakable;
	}

	public SMaterial(String id, ItemStack item)
	{
		this.id = id;
		this.type = item.getType().toString();
		
		if (!item.hasItemMeta())
		{
			this.name = null;
			this.glow = false;
			this.data = 0;
			this.unbreakable = false;
			this.lore = new ArrayList<>();
		}
		
		ItemMeta meta = item.getItemMeta();
		
		//name
		if (meta.hasDisplayName())
		{
			this.name = meta.getDisplayName();
		}
		else
		{
			this.name = null;
		}
		
		//lore
		if (meta.hasLore())
		{
			this.lore = meta.getLore();
		}
		else
		{
			this.lore = new ArrayList<>();
		}
		
		//data
		if (meta.hasCustomModelData())
		{
			this.data = meta.getCustomModelData();
		}
		else
		{
			this.data = 0;
		}
		
		//enchants
		if (meta.hasEnchants())
		{
			this.glow = true;
		}
		else 
		{
			this.glow = false;
		}
		
		//unbreakable
		this.unbreakable = meta.isUnbreakable();
		
	}
	
	public ItemStack convertToItemStack(boolean icon)
	{
		String name = this.name;
		String type = this.type;
		
		List<String> error = new ArrayList<>();
		error.add(utils.color("&4Error(s):"));
		
		// type exception!
		if (this.type.equalsIgnoreCase("Not Found") || !utils.isMaterial(this.type))
		{
			error.add(utils.color("&4+ type: &c"+type+"&4 is not a vanilla type."));
			type = "BARRIER";
		}
		
		ItemStack result = utils.createItem(Material.valueOf(type), 1, this.data, unbreakable, utils.color(name));
		
		ItemMeta meta = result.getItemMeta();
		
		//name
		if (name.equalsIgnoreCase("Not Found"))
		{
			meta.setDisplayName(null);
		}
		//lore
		List<String> toAdd = new ArrayList<>();
		for (String s: lore)
		{
			toAdd.add(utils.color(s));
		}
		meta.setLore(toAdd);
		//glow
		if (glow)
		{
			meta.addEnchant(Enchantment.LUCK, 1, false);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		
		List<String> currentLore = meta.getLore();
		if (error.size()>=2)
		{
			currentLore.addAll(error);
			meta.setLore(currentLore);
		}
		
		result.setItemMeta(meta);
		
		return result;
	}
	
	
	
}

