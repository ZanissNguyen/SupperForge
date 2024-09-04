package org.zanissnguyen.SupperForge.API;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.zanissnguyen.SupperForge.SupperForge;

public class Style 
{
	public String id;
	
	public String default_fill;
	public int rows;
	public int reward_slot;
	public int back_slot;
	public int craft_slot;
	public int info_slot;
	public List<Integer> materials;
	
	public Map<String, ItemStack> icon;
	public Map<String, List<Integer>> iconSlots;
	public Style(String id, int rows, int reward, int craft, int info, int back
			, List<Integer> materials, String default_fill
			, Map<String, ItemStack> icon, Map<String, List<Integer>> iconSlots)
	{
		this.id = id;
		this.rows = rows;
		this.reward_slot = reward;
		this.craft_slot =craft;
		this.info_slot = info;
		this.back_slot = back;
		this.materials = materials;
		this.default_fill = default_fill;
		this.icon = icon;
		this.iconSlots = iconSlots;
		
//		for (int i: materials) System.out.println(i);
	}
	
	public ItemStack craft() {return SupperForge.styStorage.getIcon(id+".craft");}
	public ItemStack back() {return SupperForge.styStorage.getIcon(id+".back");}
	public ItemStack info(Player p, SRecipe recipe)
	{
		ItemStack info = SupperForge.styStorage.getIcon(id+".info");
		
		List<String> current = SupperForge.utils.getLore(info);
		List<String> lore = new ArrayList<>();
		String yesIcon = SupperForge.getInstance().locale.get("locale", "symbol", "tick");
		String noIcon = SupperForge.getInstance().locale.get("locale", "symbol", "cross");

		for (String s: current)
		{
			if (s.contains("<lvl>"))
			{
				String colorCode = "";
				double level = SupperForge.utils.getLevel(p);
				level = SupperForge.utils.fixedDecimal(level, 2);
				double levelRequire = recipe.level;
				
				if (level<levelRequire)
				{
					colorCode = "&c";
					lore.add(SupperForge.utils.color(s.replace("<lvl>", colorCode +level+"/"+levelRequire+" "+noIcon)));
				}
				else
				{
					colorCode = "&a";
					lore.add(SupperForge.utils.color(s.replace("<lvl>", colorCode +level+"/"+levelRequire+" "+yesIcon)));
				}
			}
			else if (s.contains("<time>"))
			{
				lore.add(SupperForge.utils.color(s.replace("<time>", recipe.time+"")));
			}
			else if (s.contains("<material_status>"))
			{
				String status = "";
				if (SUserInterface_Recipe.enoughMaterial(p, recipe))
				{
					status = SupperForge.getInstance().locale.get("locale", "forge", "info-icon", "material-status", "enough");
					lore.add(SupperForge.utils.color(s.replace("<material_status>", status+" "+yesIcon)));
				}
				else
				{
					status = SupperForge.getInstance().locale.get("locale", "forge", "info-icon", "material-status", "not-enough");
					lore.add(SupperForge.utils.color(s.replace("<material_status>", status+" "+noIcon)));
				}
			} else
			{
				lore.add(SupperForge.utils.color(s));
			}
		}
		ItemMeta meta = info.getItemMeta();
		meta.setLore(lore);
		info.setItemMeta(meta);
		return info;
	}
}
