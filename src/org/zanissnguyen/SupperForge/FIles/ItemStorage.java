package org.zanissnguyen.SupperForge.FIles;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.zanissnguyen.SupperForge.SupperForge;
import org.zanissnguyen.SupperForge.API.SItem;

public class ItemStorage extends SFile 
{
	public static List<String> allSItems = new ArrayList<>();
	
	public ItemStorage(SupperForge plugin)
	{
		super(plugin, "items.yml");
		this.getSItemList();
	}
	
	public String genarteID()
	{
		String prefix = "auto_generate_";
		String result = prefix;
		int number = 0;
		while (isIDExist(result))
		{
			result = prefix + number++;
		}

		return result;
	}
	
	public Boolean isIDExist(String id)
	{
		return (allSItems.contains(id));
	}
	
	public void getSItemList()
	{
		Set<String> SItems = fileConfig.getConfigurationSection("items.").getKeys(false);
		allSItems.clear();
		for (String s: SItems)
		{
			allSItems.add(s);
		}
		allSItems.sort(null);
	}
	
	public List<String> searchFor(String keyword)
	{
		if (keyword.equalsIgnoreCase("")) return allSItems;
		
		List<String> result = new ArrayList<>();
		
		for (String s: allSItems)
		{
			if (s.contains(keyword)) result.add(s);
		}
		
		return result;
	}
	
	public Boolean isSItem(String id)
	{
		if (id.equalsIgnoreCase("")) return false;
		
		for (String s: allSItems)
		{
			if (s.equalsIgnoreCase(id)) return true;
		}
		return false;
	}
	
	public SItem getSItem(String id, boolean inv, Player p)
	{
		String type = getType(id, inv, p);
		String name = getDisplayName(id, inv, p);
		int data = getData(id, inv, p);
		List<String> lore = getLore(id, inv, p);
		List<String> enchants = getEnchants(id, inv, p);
		List<String> flags = getFlags(id, inv, p);
		List<String> stats = getStats(id, inv, p);
		stats.sort(null);
		Boolean unbreakable = getUnbreak(id, inv, p);
		String item_type = getItemType(id, inv, p);
	
		SItem result = new SItem(id, type, name, data, lore, enchants, flags, stats, item_type, unbreakable);
		
		return result;
	}
	
	public void saveItem(String id, ItemStack item, boolean inv, Player p)
	{
		SItem toSave = new SItem(id, item);
		
		String path = (!inv) ? "items."+id+".<attr>" : "storage."+p.getUniqueId().toString()+"."+id+".<attr>";
		FileConfiguration config = (!inv) ? fileConfig : plugin.storage.fileConfig;
		
		config.set(path.replace("<attr>", "type"), toSave.type);
		config.set(path.replace("<attr>", "display"), toSave.name);
		config.set(path.replace("<attr>", "data"), toSave.data);
		config.set(path.replace("<attr>", "lore"), toSave.lore);
		config.set(path.replace("<attr>", "enchants"), toSave.enchants);
		config.set(path.replace("<attr>", "flags"), toSave.flags);
		config.set(path.replace("<attr>", "unbreakable"), toSave.unbreakable);
		config.set(path.replace("<attr>", "stats"), toSave.stats);
		config.set(path.replace("<attr>", "item-type"), toSave.item_type);
		
		if (!inv)
		{
			this.save();
			this.getSItemList();
		} else
		{
			plugin.storage.save();
		}
		
	}
	
	private String getDisplayName(String id, boolean inv, Player p)
	{
		String path = (!inv) ? "items."+id+".display" : "storage."+p.getUniqueId().toString()+"."+id+".display";
		FileConfiguration config = (!inv) ? fileConfig : plugin.storage.fileConfig;
		if (config.getString(path)==null) return "Not Found";
		return config.getString(path);
	}
	private String getType(String id, boolean inv, Player p)
	{
		String path = (!inv) ? "items."+id+".type" : "storage."+p.getUniqueId().toString()+"."+id+".type";
		FileConfiguration config = (!inv) ? fileConfig : plugin.storage.fileConfig;
		if (config.getString(path)==null) return "Not Found";
		return config.getString(path);
	}
	private String getItemType(String id, boolean inv, Player p)
	{
		String path = (!inv) ? "items."+id+".item-type" : "storage."+p.getUniqueId().toString()+"."+id+".item-type";
		FileConfiguration config = (!inv) ? fileConfig : plugin.storage.fileConfig;
		if (config.getString(path)==null) return "";
		return config.getString(path);
	}
	private int getData(String id, boolean inv, Player p)
	{
		String path = (!inv) ? "items."+id+".data" : "storage."+p.getUniqueId().toString()+"."+id+".data";
		FileConfiguration config = (!inv) ? fileConfig : plugin.storage.fileConfig;
		return Math.max(0, config.getInt(path));
	}
	private List<String> getLore(String id, boolean inv, Player p)
	{
		String path = (!inv) ? "items."+id+".lore" : "storage."+p.getUniqueId().toString()+"."+id+".lore";
		FileConfiguration config = (!inv) ? fileConfig : plugin.storage.fileConfig;
		if (config.getStringList(path)==null) return new ArrayList<>();;
		return config.getStringList(path);
	}
	private List<String> getEnchants(String id, boolean inv, Player p)
	{
		String path = (!inv) ? "items."+id+".enchants" : "storage."+p.getUniqueId().toString()+"."+id+".enchants";
		FileConfiguration config = (!inv) ? fileConfig : plugin.storage.fileConfig;
		if (config.getStringList(path)==null) return new ArrayList<>();;
		return config.getStringList(path);
	}
	private List<String> getFlags(String id, boolean inv, Player p)
	{
		String path = (!inv) ? "items."+id+".flags" : "storage."+p.getUniqueId().toString()+"."+id+".flags";
		FileConfiguration config = (!inv) ? fileConfig : plugin.storage.fileConfig;
		if (config.getStringList(path)==null) return new ArrayList<>();;
		return config.getStringList(path);
	}
	private List<String> getStats(String id, boolean inv, Player p)
	{
		String path = (!inv) ? "items."+id+".stats" : "storage."+p.getUniqueId().toString()+"."+id+".stats";
		FileConfiguration config = (!inv) ? fileConfig : plugin.storage.fileConfig;
		if (config.getStringList(path)==null) return new ArrayList<>();
		return config.getStringList(path);
	}
	private Boolean getUnbreak(String id, boolean inv, Player p)
	{
		String path = (!inv) ? "items."+id+".unbreakable" : "storage."+p.getUniqueId().toString()+"."+id+".unbreakable";
		FileConfiguration config = (!inv) ? fileConfig : plugin.storage.fileConfig;
		return config.getBoolean(path);
	}
	
}
