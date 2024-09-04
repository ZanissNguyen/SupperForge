package org.zanissnguyen.SupperForge.FIles;

import java.util.ArrayList;
import java.util.List;

import org.zanissnguyen.SupperForge.SupperForge;

public class Format extends SFile
{
//	private String name;
	
	public Format(SupperForge plugin)
	{
		super(plugin, "format.yml");
//		name = "format.yml";
//		autoUpdate();
	}
	
	public String get(String... str)
	{
		String toGet = "";
		for (String s: str)
		{
			if (s.equalsIgnoreCase(str[0]))toGet = toGet + s;
			else toGet = toGet + "." + s;
		}
		return this.utils.color(fileConfig.getString(toGet));
	}
	
	private List<String> getList(String... str)
	{
		List<String> result = new ArrayList<>();
		String toGet = "";
		for (String s: str)
		{
			if (s.equalsIgnoreCase(str[0]))toGet = toGet + s;
			else toGet = toGet + "." + s;
		}
		List<String> got = fileConfig.getStringList(toGet);
		for (String s: got)
		{
			result.add(utils.color(s));
		}
		
		return result;
	}
	
//	private void autoUpdate()
//	{
//		if (!file.exists()) {
//            plugin.saveResource(name, false);
//        }
//
//        File File = this.file;
//        YamlConfiguration externalYamlConfig = YamlConfiguration.loadConfiguration(File);
//        externalYamlConfig.options().copyDefaults(true);
//
//        InputStreamReader defConfigStream = new InputStreamReader(SupperForge.getInstance().getResource("resources/"+name), StandardCharsets.UTF_8);
//        YamlConfiguration internalConfig = YamlConfiguration.loadConfiguration(defConfigStream);
//        internalConfig.options().copyDefaults(true);
//
//        // Gets all the keys inside the internal file and iterates through all of it's key pairs
//        for (String string : internalConfig.getKeys(true)) {
//            // Checks if the external file contains the key already.
//            if (!externalYamlConfig.contains(string)) {
//                // If it doesn't contain the key, we set the key based off what was found inside the plugin jar
//                externalYamlConfig.set(string, internalConfig.get(string));
//            }
//        }
//        try {
//           externalYamlConfig.save(File);
//        } catch (IOException io) {
//            io.printStackTrace();
//        }
//	}
	
	public final String getDurabilityFormat()
	{
		return get("format", "durability");
	}
	
	public final String getTypeFormat()
	{
		return get("format", "type");
	}
	
	public final String getStatFormat()
	{
		return get("format", "stat");
	}
	
	public final String getSRecipeMaterial()
	{
		return get("format", "recipe", "material");
	}
	
	public final List<String> getSRecipeFormat()
	{
		return getList("format", "recipe", "general");
	}
	
	public final List<String> getSRecipeRequirement()
	{
		return getList("format", "recipe", "requirement");
	}
	
	public final List<String> getSItemFormat()
	{
		return getList("format", "item", "general");
	}
	
	public final List<String> getUIPhysicFormat()
	{
		return getList("format", "stat-inventory", "physic");
	}
	
	public final List<String> getUIMagicFormat()
	{
		return getList("format", "stat-inventory", "magic");
	}
	
	public final List<String> getUIDefenseFormat()
	{
		return getList("format", "stat-inventory", "defense");
	}
	
	public final List<String> getUIMiscFormat()
	{
		return getList("format", "stat-inventory", "misc");
	}
}
