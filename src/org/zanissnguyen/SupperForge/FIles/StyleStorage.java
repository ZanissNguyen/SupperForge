package org.zanissnguyen.SupperForge.FIles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.inventory.ItemStack;
import org.zanissnguyen.SupperForge.SupperForge;
import org.zanissnguyen.SupperForge.API.SMaterial;
import org.zanissnguyen.SupperForge.API.Style;

public class StyleStorage extends SFile
{
	public static List<String> allStyle = new ArrayList<>();
	
	public StyleStorage(SupperForge plugin)
	{
		super(plugin, "style.yml");
		this.getStyleList();
	}
	
	public Boolean isIDExist(String id)
	{
		return (allStyle.contains(id));
	}
	
	public void getStyleList()
	{
		Set<String> list = fileConfig.getConfigurationSection("style.").getKeys(false);
		allStyle.clear();
		for (String s: list)
		{
			allStyle.add(s);
		}
		allStyle.sort(null);
	}
	
	public Boolean isStyle(String id)
	{
		if (id.equalsIgnoreCase("")) return false;
		
		for (String s: allStyle)
		{
			if (s.equalsIgnoreCase(id)) return true;
		}
		return false;
	}
	
	public Style getStyle(String id)
	{
		int rows = getRow(id);
		int reward = getReward(id);
		int craft = getUniqueSlot(id, "craft");
		int info = getUniqueSlot(id, "info");
		int back = getUniqueSlot(id, "back");
		List<Integer> materials = getMaterialsSlot(id);
		String default_fill = getDefaultFill(id);
		
		//
		List<String> icons = getIconList(id);
		Map<String, ItemStack> icon = new HashMap<>();
		Map<String, List<Integer>> iconSlots = new HashMap<>();
		
		for (String s: icons)
		{
			icon.put(s, getIcon(id+".icons."+s));
			iconSlots.put(s, getIconSlot(id, s));
		}
		//
	
		Style result = new Style(id, rows, reward, craft, info, back, materials, default_fill, icon, iconSlots);
		
		return result;
	}
	
	public void saveStyle(Style style)
	{
//		SItem toSave = new SItem(id, item);
//		fileConfig.set("recipes."+id+".type", toSave.type);
//		fileConfig.set("recipes."+id+".display", toSave.name);
//		fileConfig.set("recipes."+id+".data", toSave.data);
//		fileConfig.set("recipes."+id+".lore", toSave.lore);
//		fileConfig.set("recipes."+id+".enchants", toSave.enchants);
//		fileConfig.set("recipes."+id+".flags", toSave.flags);
//		fileConfig.set("recipes."+id+".unbreakable", toSave.unbreakable);
		
		this.save();
		this.getStyleList();
	}
	
	// path: style. + path + .display
	// path: <id>."craft/back/info"
	// path: <id>.icons.<icon id>
	public ItemStack getIcon(String path)
	{
		ItemStack result = null;
		String type = getIconType(path);
		String name = getIconDisplayName(path);
		int data = getIconData(path);
		boolean glow = getIconGlow(path);
		List<String> lore = getIconLore(path);
		
		SMaterial toGet = new SMaterial(path, type, data, name, lore, glow, false);
		result = toGet.convertToItemStack(true);
		
		return result;
	}
	
	private String getIconDisplayName(String path)
	{
		if (fileConfig.getString("style."+path+".display")==null) return "Not Found";
		return fileConfig.getString("style."+path+".display");
	}
	private String getIconType(String path)
	{
		if (fileConfig.getString("style."+path+".type")==null) return "Not Found";
		return fileConfig.getString("style."+path+".type");
	}
	private int getIconData(String path)
	{
		return Math.max(0, fileConfig.getInt("style."+path+".data"));
	}
	private List<String> getIconLore(String path)
	{
		if (fileConfig.getStringList("style."+path+".lore")==null) return new ArrayList<>();
		return fileConfig.getStringList("style."+path+".lore");
	}
	private Boolean getIconGlow(String path)
	{
		return fileConfig.getBoolean("style."+path+".glow");
	}
	
	private List<String> getIconList(String id)
	{
		if (fileConfig.getConfigurationSection("style."+id+".icons").getKeys(false)==null) return new ArrayList<>();
		else 
		{
			List<String> result = new ArrayList<>();
			Set<String> set = fileConfig.getConfigurationSection("style."+id+".icons").getKeys(false);
			for (String s: set)
			{
				result.add(s);
			}
			return result;
		}
	}
	private List<Integer> getMaterialsSlot(String id)
	{
		if (fileConfig.getIntegerList("style."+id+".materials_slots")==null) return new ArrayList<>();
		return fileConfig.getIntegerList("style."+id+".materials_slots");
	}
	
	private List<Integer> getIconSlot(String id, String icon_id)
	{
		if (fileConfig.getIntegerList("style."+id+".icons."+icon_id+".slot")==null) return new ArrayList<>();
		return fileConfig.getIntegerList("style."+id+".icons."+icon_id+".slot");
	}
	private int getReward(String id)
	{
		return fileConfig.getInt("style."+id+".reward_slot");
	}
	private int getUniqueSlot(String id, String unique)
	{
		return fileConfig.getInt("style."+id+"."+unique+".slot");
	}
	private int getRow(String id)
	{
		return fileConfig.getInt("style."+id+".rows");
	}
	private String getDefaultFill(String id)
	{
		if (fileConfig.getString("recipes."+id+".type")==null) return "Not Found";
		return fileConfig.getString("recipes."+id+".type");
	}
}

