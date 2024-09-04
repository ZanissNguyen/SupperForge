package org.zanissnguyen.SupperForge.UI_UX.recipe;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.zanissnguyen.SupperForge.SupperForge;
import org.zanissnguyen.SupperForge.Utils;
import org.zanissnguyen.SupperForge.API.SRecipe;
import org.zanissnguyen.SupperForge.API.SUserInterface_Recipe;

public class RecipeCraft_UX implements Listener 
{
	private SupperForge plugin;
	private Utils utils;
	
	private String name_prefix;
	private String prefix;
	
	public RecipeCraft_UX(SupperForge plugin, Utils utils) 
	{
		this.plugin = plugin;
		this.utils = utils;
		
		name_prefix = plugin.locale.get("locale", "forge", "recipe", "craft", "ui-name");
		prefix = plugin.locale.get("locale", "forge", "recipe", "craft", "prefix");
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onEsc(InventoryCloseEvent event)
	{
		
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event)
	{
		InventoryView clicked = event.getView();
		Player p = (Player) event.getWhoClicked();
		
		if (clicked.getTitle().startsWith(name_prefix) && event.getSlotType()!=SlotType.OUTSIDE)
		{
			String title = clicked.getTitle();
			String recipeID = title.substring(title.lastIndexOf("#")+1, title.lastIndexOf(")"));
			
			SRecipe recipe = SupperForge.repStorage.getSRecipe(recipeID);
			
			ItemStack craft;
			if (SupperForge.styStorage.isIDExist(recipe.type)) craft = SupperForge.styStorage.getStyle(recipe.type).craft();
			else craft = SUserInterface_Recipe.craft();
			
			ItemStack back;
			if (SupperForge.styStorage.isIDExist(recipe.type)) back = SupperForge.styStorage.getStyle(recipe.type).back();
			else back = SUserInterface_Recipe.back();
			
			int slot = event.getRawSlot();
			if (clicked.getItem(slot)!=null)
			{
				int time = recipe.time;
				String message = plugin.locale.get("locale", "forge", "recipe", "craft", "notice");
				
				if (clicked.getItem(slot).isSimilar(craft))
				{
					if (checkLevel(p, recipe, true) && checkMaterials(p, recipe, true))
					{
						for (ItemStack item: recipe.materials().keySet())
						{
							utils.takeItem(item, recipe.materials().get(item), p);
						}
						
						p.sendMessage(prefix +" "+ message.replace("<item>", utils.getName(recipe.item)).replace("<t>", time+""));
						for (int i=1; i<=Math.min(3, time); i++)
						{
							Bukkit.getScheduler().runTaskLater(plugin, new Runnable() 
							{
								@Override
								public void run() 
								{
									utils.playSound(Sound.BLOCK_ANVIL_USE, p, 3f);
								}
							}, 20*i);
						}
						Bukkit.getScheduler().runTaskLater(plugin, new Runnable() 
						{
							@Override
							public void run() 
							{
								utils.playSound(Sound.ENTITY_PLAYER_LEVELUP, p, 3f);
								p.getInventory().addItem(SupperForge.itmStorage.getSItem(recipeID, false, null).convertToItemStack(false, false));
							}
						}, 20*time);
						
					}
					
					p.closeInventory();
				}
				
				if (clicked.getItem(slot).isSimilar(back))
				{
					p.closeInventory();
					plugin.recipeUI.open(p, "", 1);
				}
			}
						
			event.setCancelled(true);	
		}
	}
	
	public boolean checkLevel(Player p, SRecipe recipe, boolean msg)
	{
		double level = utils.getLevel(p);
		level = utils.fixedDecimal(level, 2);
		double levelRequire = recipe.level;
		
		String message = plugin.locale.get("locale", "forge", "recipe", "craft", "not-level");
		
		boolean result = level>=levelRequire;
		if (!result && msg) p.sendMessage(prefix +" "+ message);
		
		return result;
	}
	
	public boolean checkMaterials(Player p, SRecipe recipe, boolean msg)
	{
		boolean result = SUserInterface_Recipe.enoughMaterial(p, recipe);
		String message = plugin.locale.get("locale", "forge", "recipe", "craft", "not-enough-material");
		if (!result && msg) p.sendMessage(prefix +" "+ message);
		
		return result;
	}
}
