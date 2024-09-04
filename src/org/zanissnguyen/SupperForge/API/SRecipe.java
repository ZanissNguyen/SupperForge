package org.zanissnguyen.SupperForge.API;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.zanissnguyen.SupperForge.SupperForge;
import org.zanissnguyen.SupperForge.Utils;

public class SRecipe 
{
	private Utils utils;
	
	public String id;
	public SItem reward;
	public List<String> material;
	public List<String> description;
	public Boolean permission;
	public double level;
	public int time;
	public String type;

	public SUserInterface_Recipe ui;
	public ItemStack item;
	public List<String> error = new ArrayList<>();
	
	public ItemStack inList()
	{
		ItemStack result = item.clone();
		ItemMeta meta = result.getItemMeta();
		
		List<String> format = SupperForge.format.getSRecipeFormat();
		List<String> description = SupperForge.format.getSRecipeRequirement();
		String material = SupperForge.format.getSRecipeMaterial();
		
		List<String> lore = new ArrayList<>();
		for (String s: format)
		{
			if (s.equalsIgnoreCase("<requirement>"))
			{
				for (String s1: description)
				{
					if (s1.contains("<lvl>") && level!=0) lore.add(utils.color(s1.replace("<lvl>", level+"")));
					if (s1.contains("<t>") && time!=0) lore.add(utils.color(s1.replace("<t>", time+"")));
				}
			}
			else if (s.equalsIgnoreCase("<description>"))
			{
				for (String s1: this.description)
				{
					lore.add(utils.color(s1));
				}
			}
			else if (s.equalsIgnoreCase("<material>"))
			{
				Map<ItemStack, Integer> materials = materials();
				for (ItemStack mat: materials.keySet())
				{
					int number = 0;
					String display = "";
					if (mat.getItemMeta()==null || !mat.getItemMeta().hasDisplayName())
					{
						number = materials.get(mat);
						display = utils.standardStr(mat.getType().toString());
					}
					else
					{
						number = materials.get(mat);
						display = mat.getItemMeta().getDisplayName();
					}
					lore.add(utils.color(material.replace("<amount>", number+"").replace("<display>", display)));
				}
			}
			else if (s.equalsIgnoreCase("<reward_lore>"))
			{
				for (String s1: reward.lore)
				{
					lore.add(utils.color(s1));
				}
			}
			else lore.add(utils.color(s));
		}
		meta.setLore(lore);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		result.setItemMeta(meta);
		
		return result;
	}
	
	public Map<ItemStack, Integer> materials()
	{	
		Map<ItemStack, Integer> materials = new HashMap<>();
		for (String s: material)
		{
			ItemStack item;
			int amount = 1;
			int data = 0;
			String data_str = "";
			
			String id_data = s.substring(0, s.indexOf(":"));
			String amount_str = s.substring(s.indexOf(":")+1);
			
			if (!utils.isNumber(amount_str)) 
			{
				error.add(utils.color("&4+ amount: &c"+amount_str+"&4 is not a number"));
			} else amount = Integer.parseInt(amount_str);
			
			String itemCode = id_data;
			
			if (id_data.contains("-")) 
			{
				itemCode = id_data.substring(0, id_data.indexOf("-"));
				data_str = id_data.substring(id_data.indexOf("-")+1);
			}
			
			if (!utils.isNumber(data_str))
			{
				error.add(utils.color("&4+ data: &c"+data_str+"&4 is not a number"));
			} else data = Integer.parseInt(data_str);
			
			if (SupperForge.matStorage.isIDExist(itemCode)) // material in materials.yml
			{
				item = SupperForge.matStorage.getSMaterial(itemCode).convertToItemStack(false);
			}
			else // vanilla
			{
				if (!utils.isMaterial(itemCode))
				{
					item = utils.createItem(Material.BARRIER, 1, 0, utils.color("&c"+itemCode+" &4is not valid!"));
					error.add(utils.color("&4+ material: &c"+itemCode+"&4 is not a vanilla material."));
				}
				else
				{
					item =utils.createItem(Material.valueOf(itemCode), 1, data);
				}
			}
			
			materials.put(item, amount);
		}
		return materials;
	}
	
	public SRecipe(String id, String type, List<String> material, List<String> description, int time, int level, boolean permission)
	{
		this.utils = SupperForge.utils;
		
		this.id = id;
		reward = SupperForge.itmStorage.getSItem(id, false, null);
		this.description = description;
		this.material = material;
		this.time = time;
		this.level = level;
		this.type = type;
		this.permission = permission;
		
		error.add(utils.color("&4Error:"));
		item = SupperForge.itmStorage.getSItem(id, false, null).convertToItemStack(true, false);
	}
	
	
}
