package org.zanissnguyen.SupperForge.FIles;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.zanissnguyen.SupperForge.SupperForge;

public class Locale extends SFile 
{	
	public Locale(SupperForge plugin)
	{
		super(plugin, "locale.yml");
	}
	
	public List<String> getAllStat()
	{
		Set<String> Stats = fileConfig.getConfigurationSection("stat.").getKeys(false);
		List<String> result = new ArrayList<>();
		for (String s: Stats)
		{
			result.add(s);
		}
		result.sort(null);
		return result;
	}
	
	public List<String> getAllType()
	{
		Set<String> Types = fileConfig.getConfigurationSection("item-type.").getKeys(false);
		List<String> result = new ArrayList<>();
		for (String s: Types)
		{
			result.add(s);
		}
		result.sort(null);
		return result;
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
	
	public List<String> getList(String... str)
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
}
