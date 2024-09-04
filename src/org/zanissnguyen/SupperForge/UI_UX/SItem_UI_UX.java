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
import org.zanissnguyen.SupperForge.API.SItem;
import org.zanissnguyen.SupperForge.API.SUserInterface;
import org.zanissnguyen.SupperForge.API.SUserInterface_List;

@SuppressWarnings("deprecation")
public class SItem_UI_UX extends SUserInterface implements SUserInterface_List, Listener
{
	public List<Player> onSearch = new ArrayList<>();
	
	public static String name_prefix;
	private ItemStack laner = null;
	private ItemStack fill_slot = null;

	public SItem_UI_UX(SupperForge plugin, Utils utils) 
	{
		super(plugin, utils, plugin.locale.get("locale", "forge", "item", "ui-name"), 6);
		
		name_prefix = plugin.locale.get("locale", "forge", "item", "ui-name");
		laner = utils.createItem(Material.YELLOW_STAINED_GLASS_PANE, 1, 0, false, " ");
		fill_slot = utils.createItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1, 0, false, " ");
		
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public Inventory setUp(Player p, String search, int openPage)
	{
		List<ItemStack> items = loadList(p, search, openPage);
		
		int maxPage = SupperForge.itmStorage.searchFor(search).size()/36+1;
//		int maxPage = items.size()/36+1;
		String new_name = name_prefix + utils.color(" &2["+search+"] &f| &2["+openPage+"/"+maxPage+"]");
		Inventory result = Bukkit.createInventory(null, this.rows*9, new_name);
		
		
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
		result.setItem(50, find(search, UI_TYPE.ITEM_UI));
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
		boolean showTips = utils.hasPermission(p, "supper.item.give", false);
		
		List<SItem> items = new ArrayList<>();
		for (String s: SupperForge.itmStorage.searchFor(search))
		{
			items.add(SupperForge.itmStorage.getSItem(s, false, null));
		}
		
		List<ItemStack> itemStacks = new ArrayList<>();
		for (SItem item: items)
		{
			itemStacks.add(loadIcon(item, showTips));
		}
		
		for (int i = 0 ;i<itemStacks.size(); i++)
		{
			if (i>= (page-1)*36 && i<page*36)
			{
				result.add(itemStacks.get(i));
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
		
		if (clicked.getTitle().startsWith(name_prefix) && event.getRawSlot()<rows*9)
		{
			String title = clicked.getTitle();
			String pageStr = title.substring(title.lastIndexOf("[")+1, title.lastIndexOf("/"));
//			String maxPageStr = title.substring(title.lastIndexOf("/")+1, title.lastIndexOf("]"));
			
			int page = Integer.parseInt(pageStr);
//			int maxPage = Integer.parseInt(maxPageStr);
			
			String s = title.substring(0, title.lastIndexOf("|"));
			String keyword = s.substring(s.lastIndexOf("[")+1, s.lastIndexOf("]"));
			
			int slot = event.getRawSlot();
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
			
			if (slot==50 && clicked.getItem(slot).isSimilar(find(keyword, UI_TYPE.ITEM_UI)))
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
			
			ItemStack item = clicked.getItem(slot);
			if ((slot>=0 && slot<=35) && !( item.isSimilar(fill()) || item.isSimilar(fill_slot) || item.isSimilar(laner) ))
			{
				if (utils.hasPermission(p, "supper.item.give", false)) 
				{
					ItemStack toCheck = clicked.getItem(slot);
					ItemMeta meta = toCheck.getItemMeta();
					String name = meta.getDisplayName();
					if (name.contains("#"))
					{
						String id = name.substring(name.lastIndexOf("#")+1, name.lastIndexOf(")"));
						SItem got = SupperForge.itmStorage.getSItem(id, false, null);
						p.getInventory().addItem(got.convertToItemStack(false, false));
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
