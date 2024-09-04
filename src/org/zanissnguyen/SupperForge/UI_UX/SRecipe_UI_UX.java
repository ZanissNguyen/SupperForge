package org.zanissnguyen.SupperForge.UI_UX;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.zanissnguyen.SupperForge.SupperForge;
import org.zanissnguyen.SupperForge.Utils;
import org.zanissnguyen.SupperForge.API.SRecipe;
import org.zanissnguyen.SupperForge.API.SUserInterface;
import org.zanissnguyen.SupperForge.API.SUserInterface_List;
import org.zanissnguyen.SupperForge.UI_UX.recipe.CustomRecipe;

@SuppressWarnings("deprecation")
public class SRecipe_UI_UX extends SUserInterface implements SUserInterface_List, Listener
{
	public List<Player> onSearch = new ArrayList<>();
	
	public static String name_prefix;

	public SRecipe_UI_UX(SupperForge plugin, Utils utils) 
	{
		super(plugin, utils, plugin.locale.get("locale", "forge", "recipe", "ui-name"), 6);
		
		name_prefix = plugin.locale.get("locale", "forge", "recipe", "ui-name");
		
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public Inventory setUp(Player p, String search, int openPage)
	{
		List<ItemStack> items = loadList(p, search, openPage);
		
		int maxPage = SupperForge.repStorage.searchFor(search).size()/36+1;
//		int maxPage = items.size()/36+1;
		String new_name = name_prefix + utils.color(" &2["+search+"] &f| &2["+openPage+"/"+maxPage+"]");
		Inventory result = Bukkit.createInventory(null, this.rows*9, new_name);
		
		ItemStack laner = utils.createItem(Material.YELLOW_STAINED_GLASS_PANE, 1, 0, false, " ");
		ItemStack fill_slot = utils.createItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1, 0, false, " ");
		
		
		//basic frame
		for (int i = 0; i<36; i++)
		{
			result.setItem(i, fill_slot);
		}
		
		// fifth row
		for (int i = 36; i<45; i++)
		{
			result.setItem(i, laner);
		}
		
		// sixth row
		result.setItem(45, fill());
		result.setItem(46, fill());
		result.setItem(47, fill());
		result.setItem(48, refresh());
		result.setItem(49, addNew());
		result.setItem(50, find(search, UI_TYPE.RECIPE_UI));
		result.setItem(51, fill());
		result.setItem(52, fill());
		result.setItem(53, fill());
		
		
		for (int i = 0 ; i<items.size(); i++) result.setItem(i, items.get(i));
		
		if (openPage != 1) result.setItem(45, previousPage());
		if (openPage != maxPage) result.setItem(53, nextPage());
		
		return result;
	}

	@Override
	public void open(Player p, String search, int page) 
	{
		p.openInventory(this.setUp(p, search,page));
	}

	@Override
	public List<ItemStack> loadList(Player p, String search, int page)
	{
		List<ItemStack> result = new ArrayList<>();
		
		List<ItemStack> items = new ArrayList<>();
		for (String s: SupperForge.repStorage.searchFor(search))
		{
			SRecipe recipe = SupperForge.repStorage.getSRecipe(s);
			ItemStack toAdd = recipe.inList();
			List<String> tips = plugin.locale.getList("locale", "forge", "recipe-tips");
			toAdd = utils.setName(toAdd, utils.getName(toAdd)+" &e(#"+recipe.id+")");
			if ((recipe.permission && utils.hasPermission(p, "supper.recipe.edit", false)) || !recipe.permission)
				toAdd= utils.addAllLore(toAdd, tips);
			
			items.add(toAdd);
		}
		
		for (int i = 0 ;i<items.size(); i++)
		{
			if (i>= (page-1)*36 && i<page*36)
			{
				result.add(items.get(i));
			}
		}
		
		return result;
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
		
		if (clicked.getTitle().startsWith(name_prefix) && event.getRawSlot()<rows*9 )
		{
			String title = clicked.getTitle();
			String pageStr = title.substring(title.lastIndexOf("[")+1, title.lastIndexOf("/"));
//			String maxPageStr = title.substring(title.lastIndexOf("/")+1, title.lastIndexOf("]"));
			
			int page = Integer.parseInt(pageStr);
//			int maxPage = Integer.parseInt(maxPageStr);
			
			String s = title.substring(0, title.lastIndexOf("|"));			
			String keyword = s.substring(s.lastIndexOf("[")+1, s.lastIndexOf("]"));
			
			int slot = event.getRawSlot();
			if (clicked.getItem(slot)==null) return;
			if (slot==45 && clicked.getItem(slot).isSimilar(previousPage()))
			{
				p.closeInventory();
				open(p, keyword, page-1);
			}
			
			if (slot==48 && clicked.getItem(slot).isSimilar(refresh()))
			{
				p.closeInventory();
				open(p, "", 1);
			}
			
			if (slot==49 && clicked.getItem(slot).isSimilar(addNew()))
			{
				//coming soon
			}
			
			if (slot==50 && clicked.getItem(slot).isSimilar(find(keyword, UI_TYPE.RECIPE_UI)))
			{
				p.closeInventory();
				onSearch.add(p);
				p.sendMessage(plugin.locale.get("locale", "forge", "search"));
			}
			
			if (slot==53 && clicked.getItem(slot).isSimilar(nextPage()))
			{
				p.closeInventory();
				open(p, keyword, page+1);
			}
			
			if (slot>=0 && slot<=35)
			{
				ItemStack toCheck = clicked.getItem(slot);
				ItemMeta meta = toCheck.getItemMeta();
				String name = meta.getDisplayName();
				if (name.contains("#"))
				{
					String id = name.substring(name.lastIndexOf("#")+1, name.lastIndexOf(")"));
					SRecipe recipe = SupperForge.repStorage.getSRecipe(id);
					if (recipe.type.equalsIgnoreCase("vertical"))
					{
						SupperForge.verticalStyle.open(p, id);
					} else if (recipe.type.equalsIgnoreCase("horizontal"))
					{
						SupperForge.horizontalStyle.open(p, id);
					} else if (recipe.type.equalsIgnoreCase("corner"))
					{
						SupperForge.cornerStyle.open(p, id);
					} else if (recipe.type.equalsIgnoreCase("frame"))
					{
						SupperForge.frameStyle.open(p, id);
					} else
					{
						(new CustomRecipe(plugin, utils)).open(p, id);
					}
				}
			}
			event.setCancelled(true);	
		}
	}
	
	@EventHandler
	public void onTyping(PlayerChatEvent event)
	{
		Player p = event.getPlayer();
		String mess = event.getMessage();
		if (onSearch.contains(p))
		{
			if (mess.equalsIgnoreCase("cancel"))
			{
				onSearch.remove(p);
				open(p, "", 1);
				event.setCancelled(true);
				return;
			}
			onSearch.remove(p);
			open(p, mess, 1);
			event.setCancelled(true);
			return;
		}
	}
}
