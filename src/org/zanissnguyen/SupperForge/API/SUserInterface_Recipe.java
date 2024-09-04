package org.zanissnguyen.SupperForge.API;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.zanissnguyen.SupperForge.SupperForge;
import org.zanissnguyen.SupperForge.Utils;

public class SUserInterface_Recipe extends SUserInterface
{		
	public String name_prefix = "";
	
	public SUserInterface_Recipe(SupperForge plugin, Utils utils, int rows) 
	{
		super(plugin, utils, plugin.locale.get("locale", "forge", "recipe", "craft", "ui-name"), rows);	
		name_prefix = plugin.locale.get("locale", "forge", "recipe", "craft", "ui-name");
	}
	
	public void setUp(Player p, SRecipe recipe) {}
	public void setUpMaterial(Player p, SRecipe recipe)
	{
		Map<ItemStack, Integer> materials = recipe.materials();
		List<ItemStack> toShow = new ArrayList<>();
		for (ItemStack i: materials.keySet())
		{
			int current = utils.getAmount(i, p);
			int require = materials.get(i);
			String colorCode = "";
			if (current>=require) colorCode = "&a";
			else if (current>=0.25*require) colorCode = "&e";
			else colorCode = "&c";
			List<String> toAdd = new ArrayList<>();
			toAdd.add("");
			toAdd.add(colorCode+"&m----------"+colorCode+"<"+current+"/"+require+">"+"&m----------");
			
			i = utils.addAllLore(i, toAdd);
			toShow.add(i);
			
		}
		
		for (int i = 0; i<Math.min(toShow.size(),10); i++)
		{
			inv.setItem(materialsSlot.get(i), toShow.get(i));
		}
	}
	
	public static ItemStack craft()
	{
		ItemStack craft = SupperForge.utils.createItem(Material.CRAFTING_TABLE, 1, 101);
		
		ItemMeta meta = craft.getItemMeta();
		String name = SupperForge.getInstance().locale.get("locale", "forge", "craft-icon", "name");
		List<String> lore = SupperForge.getInstance().locale.getList("locale", "forge", "craft-icon", "lore");
		meta.setDisplayName(name);
		meta.setLore(lore);
		craft.setItemMeta(meta);
		
		return craft;
	}
	public ItemStack info(Player p, SRecipe recipe)
	{
		ItemStack info = createIcon(Material.PAPER, 1, 104, " ", false);
		
		String yesIcon = plugin.locale.get("locale", "symbol", "tick");
		String noIcon = plugin.locale.get("locale", "symbol", "cross");
		
		ItemMeta meta = info.getItemMeta();
		String name = plugin.locale.get("locale", "forge", "info-icon", "name");
		List<String> toAdd = plugin.locale.getList("locale", "forge", "info-icon", "lore");
		List<String> lore = new ArrayList<>();
		for (String s: toAdd)
		{
			if (s.contains("<lvl>"))
			{
				String colorCode = "";
				double level = utils.getLevel(p);
				level = utils.fixedDecimal(level, 2);
				double levelRequire = recipe.level;
				
				if (level<levelRequire)
				{
					colorCode = "&c";
					lore.add(utils.color(s.replace("<lvl>", colorCode +level+"/"+levelRequire+" "+noIcon)));
				}
				else
				{
					colorCode = "&a";
					lore.add(utils.color(s.replace("<lvl>", colorCode +level+"/"+levelRequire+" "+yesIcon)));
				}
			}
			else if (s.contains("<time>"))
			{
				lore.add(utils.color(s.replace("<time>", recipe.time+"")));
			}
			else if (s.contains("<material_status>"))
			{
				String status = "";
				if (enoughMaterial(p, recipe))
				{
					status = plugin.locale.get("locale", "forge", "info-icon", "material-status", "enough");
					lore.add(utils.color(s.replace("<material_status>", status+" "+yesIcon)));
				}
				else
				{
					status = plugin.locale.get("locale", "forge", "info-icon", "material-status", "not-enough");
					lore.add(utils.color(s.replace("<material_status>", status+" "+noIcon)));
				}
			} else
			{
				lore.add(utils.color(s));
			}
		}
		
		meta.setDisplayName(name);
		meta.setLore(lore);
		info.setItemMeta(meta);
		
		return info;
	}
	public static ItemStack back()
	{
		ItemStack back = SupperForge.utils.createItem(Material.ARROW, 1, 101);
		
		ItemMeta meta = back.getItemMeta();
		String name = SupperForge.getInstance().locale.get("locale", "forge", "back-icon", "name");
		List<String> lore = SupperForge.getInstance().locale.getList("locale", "forge", "back-icon", "lore");
		meta.setDisplayName(name);
		meta.setLore(lore);
		back.setItemMeta(meta);
		
		return back;
	}
	public List<Integer> materialsSlot = new ArrayList<>();
	
	public static boolean enoughMaterial(Player p, SRecipe recipe)
	{
		Map<ItemStack, Integer> toCheck = recipe.materials();
		for (ItemStack item: toCheck.keySet())
		{
			int playerHave = SupperForge.utils.getAmount(item, p);
			int require = toCheck.get(item);
			
			if (playerHave<require) return false;
		}
	
		
		return true;
	}
}
