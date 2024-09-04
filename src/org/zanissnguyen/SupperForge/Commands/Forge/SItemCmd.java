package org.zanissnguyen.SupperForge.Commands.Forge;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.zanissnguyen.SupperForge.SupperForge;
import org.zanissnguyen.SupperForge.Utils;
import org.zanissnguyen.SupperForge.FIles.ItemStorage;

public class SItemCmd 
{
	public static List<String> ItemTabComplete(SupperForge plugin, Utils utils, CommandSender sender, Command cmd, String label, String[] args) 
	{
		List<String> result = new ArrayList<>();
		
		if (args.length==2)// forge item ...
		{
			if (utils.isPlayer(sender, false))
			{
				Player p = (Player) sender;
				if (utils.hasPermission(p, "supper.item.save", false)) result.add("save");
				if (utils.hasPermission(p, "supper.item.give", false)) 
				{
					result.add("give");
					result.add("get");
				}
				result.add("open");
			}
			else
			{
				result.add("give");
				result.add("get");
			}
			result.add("help");
			return result;
		}
		else
		{
			
			if ((args.length>=2) && (args[1].equalsIgnoreCase("give") || args[1].equalsIgnoreCase("get")))
			{
				if (args.length==3) // forge item give/get ...
				{
					String current = args[2];
					if ((utils.isPlayer(sender, false) && utils.hasPermission(sender, "forge.item.give", false)) 
							|| !utils.isPlayer(sender, false))
					{
						for (String item: ItemStorage.allSItems)
						{
							if (item.startsWith(current)) result.add(item);
						}
					}
				}
				
				if (args.length==4) // forge item give/get <id> ...
				{
					String current = args[3];
					if ((utils.isPlayer(sender, false) && utils.hasPermission(sender, "forge.item.give", false)) 
							|| !utils.isPlayer(sender, false))
					{
						for (Player p: Bukkit.getOnlinePlayers())
						{
							if (p.getName().startsWith(current)) result.add(p.getName());
						}
					}
				}
			}
		}

		return result;
	}

	public static boolean onItemCommand(SupperForge plugin, Utils utils, CommandSender sender, Command cmd, String label, String[] args) 
	{
		String prefixMsg = plugin.locale.get("locale", "forge", "item", "prefix");
		
		if (args.length==1)// forge item
		{
			if (!utils.isPlayer(sender, true)) return false;
			Player p = (Player) sender;
			plugin.itemUI.open(p, "", 1);
			return true;
		}
		else
		{
			if (args[1].equalsIgnoreCase("open"))
			{
				if (!utils.isPlayer(sender, true)) return false;
				Player p = (Player) sender;
				plugin.itemUI.open(p, "", 1);
				return true;
			}
			
			if (args[1].equalsIgnoreCase("save"))
			{
				if (!utils.isPlayer(sender, true)) return false;
				Player p = (Player) sender;
				if (!utils.hasPermission(p, "supper.item.save", true)) return false;
				if (!utils.isHoldItem(p)) return false;
				ItemStack inHand = p.getInventory().getItemInMainHand();
				
				if (args.length==2) // forge item save
				{
					String toCreate = SupperForge.itmStorage.genarteID();
					SupperForge.itmStorage.saveItem(toCreate, inHand, false, null);
					p.sendMessage(prefixMsg + plugin.locale.get("locale", "forge", "item", "save").replace("<id>", toCreate));
					return true;
				}
				
				if (args.length==3) // forge item save <id>
				{
					String prefix = args[2];
					int number = 0;
					String id = args[2];
					while (SupperForge.itmStorage.isIDExist(id)) id = prefix + number++;
					SupperForge.itmStorage.saveItem(id, inHand, false, null);
					p.sendMessage(prefixMsg + plugin.locale.get("locale", "forge", "item", "save").replace("<id>", id));
					return true;
				}
			}
			
			if (args[1].equalsIgnoreCase("give") || args[1].equalsIgnoreCase("get"))
			{
				if (args.length==3) // forge item give/get <id> 
				{
					if (!utils.isPlayer(sender, true)) return false;
					Player p = (Player) sender;
					if (!utils.hasPermission(p, "supper.item.save", true)) return false;
					
					ItemStack item = SupperForge.itmStorage.getSItem(args[2], false, null).convertToItemStack(false, false);
					p.getInventory().addItem(item);
					p.sendMessage(prefixMsg + plugin.locale.get("locale", "forge", "item", "give").replace("<id>", args[2]).replace("<p>", p.getName()));
					return true;
				}
				
				if (args.length==4) // forge item give/get <id> <player>
				{
					if (!utils.isPlayer(sender, true))
					{
						if (!utils.isOnlinePlayer(sender, args[3])) return false;
						Player reciever = Bukkit.getPlayer(args[3]);
						
						ItemStack item = SupperForge.itmStorage.getSItem(args[2], false, null).convertToItemStack(false, false);
						reciever.getInventory().addItem(item);
						sender.sendMessage(prefixMsg + plugin.locale.get("locale", "forge", "item", "give").replace("<id>", args[2]).replace("<p>", args[3]));
						return true;
					}
					else
					{
						Player p = (Player) sender;
						if (!utils.hasPermission(p, "supper.item.give", true)) return false;
						if (!utils.isOnlinePlayer(sender ,args[3])) return false;
						Player reciever = Bukkit.getPlayer(args[3]);
						
						ItemStack item = SupperForge.itmStorage.getSItem(args[2], false, null).convertToItemStack(false, false);
						reciever.getInventory().addItem(item);
						p.sendMessage(prefixMsg + plugin.locale.get("locale", "forge", "item", "give").replace("<id>", args[2]).replace("<p>", args[3]));
						return true;
					}
				}
			}
			
			if (args[1].equalsIgnoreCase("help"))
			{
				help(utils, sender);
				return true;
			}
		}
		
		sender.sendMessage(utils.color(plugin.locale.get("locale", "error", "wrong-synax").replace("<cmd>", "/forge item help")));
		help(utils, sender);
		
		return false;
	}
	
	private static void help(Utils utils, CommandSender sender)
	{
		sender.sendMessage(utils.color("&9-----=====< &6/FORGE ITEM HELP &9>=====-----"));
		if (utils.isPlayer(sender, false)) // player
		{
			Player p = (Player) sender;
			if (utils.hasPermission(p, "supper.item.save", false))
			{
				sender.sendMessage(utils.color("&e/forge item save [<id>]"));
			}
			if (utils.hasPermission(p, "supper.item.give", false))
			{
				p.sendMessage(utils.color("&e/forge item give/get <id> [<player>]"));
			}
			p.sendMessage(utils.color("&e/forge item"));
			p.sendMessage(utils.color("&e/forge item open"));
		}
		else // console
		{
			sender.sendMessage(utils.color("&e/forge item give/get <id> <player>"));
		}
		sender.sendMessage(utils.color("&e/forge item help"));
		sender.sendMessage(utils.color("&9-----=====< &6/FORGE ITEM HELP &9>=====-----"));
	}
}
