package org.zanissnguyen.SupperForge.FIles;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.zanissnguyen.SupperForge.SupperForge;
import org.zanissnguyen.SupperForge.API.SRecipe;

public class RecipeStorage extends SFile
{
	public static List<String> allSRecipes = new ArrayList<>();
	
	public RecipeStorage(SupperForge plugin)
	{
		super(plugin, "recipes.yml");
		this.getSRecipeList();
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
		return (allSRecipes.contains(id));
	}
	
	public void getSRecipeList()
	{
		Set<String> SItems = fileConfig.getConfigurationSection("recipes.").getKeys(false);
		allSRecipes.clear();
		for (String s: SItems)
		{
			allSRecipes.add(s);
		}
		allSRecipes.sort(null);
	}
	
	public List<String> searchFor(String keyword)
	{
		if (keyword.equalsIgnoreCase("")) return allSRecipes;
		
		List<String> result = new ArrayList<>();
		
		for (String s: allSRecipes)
		{
			if (s.contains(keyword)) result.add(s);
		}
		
		return result;
	}
	
	public Boolean isSRecipe(String id)
	{
		if (id.equalsIgnoreCase("")) return false;
		
		for (String s: allSRecipes)
		{
			if (s.equalsIgnoreCase(id)) return true;
		}
		return false;
	}
	
	public SRecipe getSRecipe(String id)
	{
		String type = getType(id);
		int level = getLevel(id);
		int time = getTime(id);
		List<String> description = getDescription(id);
		List<String> material = getMaterials(id);
		Boolean permission = getPermission(id);
	
		SRecipe result = new SRecipe(id, type, material, description, time, level, permission);
		
		return result;
	}
	
	public void saveRecipe(SRecipe recipe)
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
		this.getSRecipeList();
	}
	
	private String getType(String id)
	{
		if (fileConfig.getString("recipes."+id+".type")==null) return "Not Found";
		return fileConfig.getString("recipes."+id+".type");
	}
	private List<String> getDescription(String id)
	{
		if (fileConfig.getStringList("recipes."+id+".description")==null) return new ArrayList<>();
		return fileConfig.getStringList("recipes."+id+".description");
	}
	private List<String> getMaterials(String id)
	{
		if (fileConfig.getStringList("recipes."+id+".material")==null) return new ArrayList<>();
		return fileConfig.getStringList("recipes."+id+".material");
	}
	private int getTime(String id)
	{
		return fileConfig.getInt("recipes."+id+".time");
	}
	private int getLevel(String id)
	{
		return fileConfig.getInt("recipes."+id+".level");
	}
	private Boolean getPermission(String id)
	{
		return fileConfig.getBoolean("recipes."+id+".permission");
	}
}

