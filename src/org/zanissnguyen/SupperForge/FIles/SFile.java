package org.zanissnguyen.SupperForge.FIles;

import java.io.File;
import java.io.IOException;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.zanissnguyen.SupperForge.SupperForge;
import org.zanissnguyen.SupperForge.Utils;

public class SFile 
{
	public File file;
	public FileConfiguration fileConfig;
	
	public SupperForge plugin;
	public Utils utils;
	public ItemStack unknow;
	
	public SFile(SupperForge plugin, String name)
	{	
		this.plugin = plugin;
		this.utils = SupperForge.utils;
		file = new File(plugin.getDataFolder(), "resources/"+name);
		if (!file.exists()) plugin.saveResource("resources/"+name, true);
		fileConfig = YamlConfiguration.loadConfiguration(file);
		
		unknow = utils.createItem(Material.BARRIER, 1, 0, "&fNot Found");
	}
	
	public void save()
	{
		try 
		{
			fileConfig.save(file);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
