package org.zanissnguyen.SupperForge.FIles;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.inventory.ItemStack;
import org.zanissnguyen.SupperForge.SupperForge;
import org.zanissnguyen.SupperForge.API.SMaterial;

public class MaterialStorage extends SFile 
{
	public static List<String> allSMaterials = new ArrayList<>();
	
	public MaterialStorage(SupperForge plugin)
	{
		super(plugin, "materials.yml");
		this.getSMaterialList();
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
		return (allSMaterials.contains(id));
	}
	
	public void getSMaterialList()
	{
		Set<String> SItems = fileConfig.getConfigurationSection("materials.").getKeys(false);
		allSMaterials.clear();
		for (String s: SItems)
		{
			allSMaterials.add(s);
		}
		allSMaterials.sort(null);
	}
	
	public List<String> searchFor(String keyword)
	{
		if (keyword.equalsIgnoreCase("")) return allSMaterials;
		
		List<String> result = new ArrayList<>();
		
		for (String s: allSMaterials)
		{
			if (s.startsWith(keyword)) result.add(s);
		}
		
		return result;
	}
	
	public Boolean isSMatereial(String id)
	{
		if (id.equalsIgnoreCase("")) return false;
		
		for (String s: allSMaterials)
		{
			if (s.equalsIgnoreCase(id)) return true;
		}
		return false;
	}
	
	public SMaterial getSMaterial(String id)
	{
		String type = getType(id);
		String name = getDisplayName(id);
		int data = getData(id);
		List<String> lore = getLore(id);
		Boolean glow = getGlow(id);
		Boolean unbreakable = getUnbreak(id);
	
		SMaterial result = new SMaterial(id, type, data, name, lore, glow, unbreakable);
		
		return result;
	}
	
	public void saveMaterial(String id, ItemStack item)
	{
		SMaterial toSave = new SMaterial(id, item);
		fileConfig.set("materials."+id+".type", toSave.type);
		fileConfig.set("materials."+id+".display", toSave.name);
		fileConfig.set("materials."+id+".data", toSave.data);
		fileConfig.set("materials."+id+".lore", toSave.lore);
		fileConfig.set("materials."+id+".glow", toSave.glow);
		fileConfig.set("materials."+id+".unbreakable", toSave.unbreakable);
		
		this.save();
		this.getSMaterialList();
	}
	
	private String getDisplayName(String id)
	{
		if (fileConfig.getString("materials."+id+".display")==null) return "Not Found";
		return fileConfig.getString("materials."+id+".display");
	}
	private String getType(String id)
	{
		if (fileConfig.getString("materials."+id+".type")==null) return "Not Found";
		return fileConfig.getString("materials."+id+".type");
	}
	private int getData(String id)
	{
		return Math.max(0, fileConfig.getInt("materials."+id+".data"));
	}
	private List<String> getLore(String id)
	{
		if (fileConfig.getStringList("materials."+id+".lore")==null) return new ArrayList<>();
		return fileConfig.getStringList("materials."+id+".lore");
	}
	private Boolean getGlow(String id)
	{
		return fileConfig.getBoolean("materials."+id+".glow");
	}
	private Boolean getUnbreak(String id)
	{
		return fileConfig.getBoolean("materials."+id+".unbreakable");
	}
}

