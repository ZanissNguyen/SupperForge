package org.zanissnguyen.SupperForge;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.zanissnguyen.NMS_version.NMS_version;
import org.zanissnguyen.NMS_version.NMS_version_1_12;
import org.zanissnguyen.NMS_version.NMS_version_1_13;
import org.zanissnguyen.NMS_version.NMS_version_1_14;
import org.zanissnguyen.NMS_version.NMS_version_1_15;
import org.zanissnguyen.NMS_version.NMS_version_1_16;
import org.zanissnguyen.NMS_version.NMS_version_1_17;
import org.zanissnguyen.NMS_version.NMS_version_1_18;
import org.zanissnguyen.NMS_version.NMS_version_1_19;
import org.zanissnguyen.NMS_version.NMS_version_1_20;
import org.zanissnguyen.NMS_version.NMS_version_1_21;
import org.zanissnguyen.SupperForge.Commands.SAttr;
import org.zanissnguyen.SupperForge.Commands.SData;
import org.zanissnguyen.SupperForge.Commands.SEnch;
import org.zanissnguyen.SupperForge.Commands.SFlag;
import org.zanissnguyen.SupperForge.Commands.SLore;
import org.zanissnguyen.SupperForge.Commands.SName;
import org.zanissnguyen.SupperForge.Commands.Forge.ForgeCmd;
import org.zanissnguyen.SupperForge.FIles.Format;
import org.zanissnguyen.SupperForge.FIles.InventoryStorage;
import org.zanissnguyen.SupperForge.FIles.ItemStorage;
import org.zanissnguyen.SupperForge.FIles.Locale;
import org.zanissnguyen.SupperForge.FIles.MaterialStorage;
import org.zanissnguyen.SupperForge.FIles.RecipeStorage;
import org.zanissnguyen.SupperForge.FIles.SConfiguration;
import org.zanissnguyen.SupperForge.FIles.StyleStorage;
import org.zanissnguyen.SupperForge.Listeners.ListenerDamageEvent;
import org.zanissnguyen.SupperForge.Listeners.ListenerDropAndCollectEvent;
import org.zanissnguyen.SupperForge.Listeners.ListenerDurabilityRunout;
import org.zanissnguyen.SupperForge.Listeners.ListenerEntityKill;
import org.zanissnguyen.SupperForge.Listeners.ListenerEquip;
import org.zanissnguyen.SupperForge.Listeners.ListenerPlayerConnect;
import org.zanissnguyen.SupperForge.Listeners.ListenerPlayerDeath;
import org.zanissnguyen.SupperForge.Listeners.ListenerPlayerHeal;
import org.zanissnguyen.SupperForge.UI_UX.SItem_UI_UX;
import org.zanissnguyen.SupperForge.UI_UX.SMaterial_UI_UX;
import org.zanissnguyen.SupperForge.UI_UX.SRecipe_UI_UX;
import org.zanissnguyen.SupperForge.UI_UX.Stat_UI_UX;
import org.zanissnguyen.SupperForge.UI_UX.recipe.CornerRecipe;
import org.zanissnguyen.SupperForge.UI_UX.recipe.FrameRecipe;
import org.zanissnguyen.SupperForge.UI_UX.recipe.HorizontalRecipe;
import org.zanissnguyen.SupperForge.UI_UX.recipe.RecipeCraft_UX;
import org.zanissnguyen.SupperForge.UI_UX.recipe.VerticalRecipe;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class SupperForge extends JavaPlugin
{
	public NMS_version nms = null;
	public static Utils utils = null;
	public static SAttributeUtils psUtils = null;
	public Locale locale;
	public SConfiguration config;
	public static Format format;
	public static ItemStorage itmStorage;
	public static MaterialStorage matStorage;
	public static RecipeStorage repStorage;
	public static StyleStorage styStorage;
	public InventoryStorage storage;
	public SItem_UI_UX itemUI;
	public SMaterial_UI_UX materialUI;
	public SRecipe_UI_UX recipeUI;
	public Stat_UI_UX statUI;
	
	public static VerticalRecipe verticalStyle;
	public static HorizontalRecipe horizontalStyle;
	public static CornerRecipe cornerStyle;
	public static FrameRecipe frameStyle;
	
	public static SupperForge getInstance()
	{
		return(SupperForge)  Bukkit.getPluginManager().getPlugin("SupperForge-Reborn");
	}
	
	@Override
	public void onEnable() 
	{
		nms = getVersion();
		utils = new Utils(this);
		psUtils = new SAttributeUtils(this, utils);
		
		FileInitialize();
		
		UIUXRegister();
		
		CommandRegister();
		
		ListenerRegister();
		
		RecipeStyle();
		
		UpdateChecker();
		
		otherPluginAPIHook();
		
		Catalogue(true);

	}
	
	private void UIUXRegister()
	{
		itemUI = new SItem_UI_UX(this, utils);
		materialUI = new SMaterial_UI_UX(this, utils);
		recipeUI = new SRecipe_UI_UX(this, utils);
		statUI = new Stat_UI_UX(this, utils);
		new RecipeCraft_UX(this, utils);
	}
	
	private void CommandRegister()
	{
		new SLore(this, utils);
		new SName(this, utils);
		new SData(this, utils);
		new SEnch(this, utils);
		new SFlag(this, utils);
		new ForgeCmd(this, utils);
		new SAttr(this, utils);
	}
	
	private void FileInitialize()
	{
		locale = new Locale(this);
		itmStorage = new ItemStorage(this);
		matStorage = new MaterialStorage(this);
		format = new Format(this);
		config = new SConfiguration(this);
		repStorage = new RecipeStorage(this);
		styStorage = new StyleStorage(this);
		storage = new InventoryStorage(this);
	}
	
	private void RecipeStyle()
	{
		verticalStyle = new VerticalRecipe(this, utils);
		horizontalStyle = new HorizontalRecipe(this, utils);
		cornerStyle = new CornerRecipe(this, utils);
		frameStyle = new FrameRecipe(this, utils);
	}
	
	private void ListenerRegister()
	{
		new ListenerPlayerConnect(this, utils);
		new ListenerEquip(this, utils);
		new ListenerPlayerHeal(this, utils);
		new ListenerPlayerDeath(this, utils);
		new ListenerDamageEvent(this, utils);
		new ListenerEntityKill(this, utils);
		new ListenerDropAndCollectEvent(this, utils);
		new ListenerDurabilityRunout(this, utils);
	}
	
	private void UpdateChecker()
	{
		if (!config.getUpdateChecker()) return;
		
		new UpdateChecker(this,  97418).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version)) 
            {
            	Bukkit.getConsoleSender().sendMessage(locale.get("locale.update-checker.up-to-date").replace("<plugin_name>", this.getName()));
            } else {
            	Bukkit.getConsoleSender().sendMessage(locale.get("locale.update-checker.out-dated").replace("<plugin_name>", this.getName()));
            	Bukkit.getConsoleSender().sendMessage(utils.color("&6https://www.spigotmc.org/resources/97418/"));
            }
        });
	}
	
	private void Catalogue(boolean enable)
	{
		if (!config.getCatalogue()) return;
		
		String status = enable ? locale.get("locale.catalogue.status.enable") : locale.get("locale.catalogue.status.disable");
		List<String> got = locale.getList("locale.catalogue.general");
		for (String s: got)
		{
			if (s.contains("<plugin_name>"))
			{
				s=s.replace("<plugin_name>", this.getName());
			}
			
			if (s.contains("<status>"))
			{
				s=s.replace("<status>", status);
			}
			
			if (s.contains("<version>"))
			{
				s=s.replace("<version>", this.getDescription().getVersion());
			}
			
			if (s.contains("<sitem_amount>"))
			{
				s=s.replace("<sitem_amount>", ItemStorage.allSItems.size()+"");
			}
			
			if (s.contains("<smaterial_amount>"))
			{
				s=s.replace("<smaterial_amount>", MaterialStorage.allSMaterials.size()+"");
			}
			
			if (s.contains("<srecipe_amount>"))
			{
				s=s.replace("<srecipe_amount>", RecipeStorage.allSRecipes.size()+"");
			}
			
			if (s.contains("<style_amount>"))
			{
				s=s.replace("<style_amount>", StyleStorage.allStyle.size()+"");
			}
			
			Bukkit.getConsoleSender().sendMessage(s);
		}
	}
	
	public static void reload()
	{
		Bukkit.getScheduler().cancelTasks(utils.plugin);
		HandlerList.unregisterAll(utils.plugin);
		utils.plugin.getPluginLoader().disablePlugin(utils.plugin);
		utils.plugin.getPluginLoader().enablePlugin(utils.plugin);
	}
	

	@Override
	public void onDisable()
	{
		Catalogue(false);
	}
	
	NMS_version getVersion()
	{
		String version = this.getServer().getVersion();
		
		if (version.contains("1.12")) return new NMS_version_1_12();
		else if (version.contains("1.13")) return new NMS_version_1_13();
		else if (version.contains("1.14")) return new NMS_version_1_14();
		else if (version.contains("1.15")) return new NMS_version_1_15();
		else if (version.contains("1.16")) return new NMS_version_1_16();
		else if (version.contains("1.17")) return new NMS_version_1_17();
		else if (version.contains("1.18")) return new NMS_version_1_18();
		else if (version.contains("1.19")) return new NMS_version_1_19();
		else if (version.contains("1.20")) return new NMS_version_1_20();
		else if (version.contains("1.21")) return new NMS_version_1_21();
		
		return null;
	}
	
	private void otherPluginAPIHook()
	{
		hookVault();
	}
	
	
	// VAULT HOOKER
	private static Economy econ = null;
	private static Permission perms = null;
	    
	public void hookVault()
	{
		if (!setupEconomy()) 
		{
			this.getLogger().warning("Could not find Vault! This plugin is important. Without this may cause some issue");
	        return;
	    }
		else 
		{
			this.getLogger().info("Vault Hooked");
		}
	    this.setupPermissions();
	}
	
	 private boolean setupEconomy() 
	 {
		 if (Bukkit.getPluginManager().getPlugin("Vault") == null) 
		 {
			 return false;
	     }

	     RegisteredServiceProvider<Economy> rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
	     if (rsp == null) 
	     {
	         return false;
	     }
	     econ = rsp.getProvider();
	     return econ != null;
	    }

	 private boolean setupPermissions() 
	 {
		 RegisteredServiceProvider<Permission> rsp = this.getServer().getServicesManager().getRegistration(Permission.class);
		 if (rsp==null)
		 {
			 return false;
		 }
		 perms = rsp.getProvider();
		 return (perms != null);
	 }

	 public Economy getEconomy()
	 {
		 return econ;
	 }

	 public Permission getPermissions() 
	 {
		 return perms;
	 }
}
