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
import org.zanissnguyen.SupperForge.FIles.MaterialStorage;

public class SMaterialCmd 
{
	public static List<String> MaterialTabComplete(SupperForge plugin, Utils utils, CommandSender sender, Command cmd, String label, String[] args) 
	{
		List<String> result = new ArrayList<>();
		
		if (args.length==2)// forge material ...
		{
			if (utils.isPlayer(sender, false))
			{
				Player p = (Player) sender;
				if (utils.hasPermission(p, "supper.material.save", false)) result.add("save");
				if (utils.hasPermission(p, "supper.material.give", false)) 
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
				if (args.length==3) // forge material give/get ...
				{
					String current = args[2];
					if ((utils.isPlayer(sender, false) && utils.hasPermission(sender, "forge.material.give", false)) 
							|| !utils.isPlayer(sender, false))
					{
						for (String item: MaterialStorage.allSMaterials)
						{
							if (item.startsWith(current)) result.add(item);
						}
					}
				}
				
				if (args.length==4) // forge material give/get <id> ...
				{
					String current = args[3];
					if ((utils.isPlayer(sender, false) && utils.hasPermission(sender, "forge.material.give", false)) 
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

	public static boolean onMaterialCommand(SupperForge plugin, Utils utils, CommandSender sender, Command cmd, String label, String[] args) 
	{
		String prefixMsg = plugin.locale.get("locale", "forge", "material", "prefix");
		String console = plugin.locale.get("locale", "console");
		
		if (args.length==1)// forge item
		{
			if (!utils.isPlayer(sender, true)) return false;
			Player p = (Player) sender;
			plugin.materialUI.open(p, "", 1);
			return true;
		}
		else
		{
			if (args[1].equalsIgnoreCase("open"))
			{
				if (!utils.isPlayer(sender, true)) return false;
				Player p = (Player) sender;
				plugin.materialUI.open(p, "", 1);
				return true;
			}
			
			if (args[1].equalsIgnoreCase("save"))
			{
				if (!utils.isPlayer(sender, true)) return false;
				Player p = (Player) sender;
				if (!utils.hasPermission(p, "supper.material.save", true)) return false;
				if (!utils.isHoldItem(p)) return false;
				ItemStack inHand = p.getInventory().getItemInMainHand();
				
				if (args.length==2) // forge item save
				{
					String toCreate = SupperForge.matStorage.genarteID();
					SupperForge.matStorage.saveMaterial(toCreate, inHand);
					p.sendMessage(prefixMsg + plugin.locale.get("locale", "forge", "material", "save").replace("<id>", toCreate));
					return true;
				}
				
				if (args.length==3) // forge item save <id>
				{
					String prefix = args[2];
					int number = 0;
					String id = args[2];
					while (SupperForge.matStorage.isIDExist(id)) id = prefix + number++;
					SupperForge.matStorage.saveMaterial(id, inHand);
					p.sendMessage(prefixMsg + plugin.locale.get("locale", "forge", "material", "save").replace("<id>", id));
					return true;
				}
			}
			
			if (args[1].equalsIgnoreCase("give") || args[1].equalsIgnoreCase("get"))
			{
				if (args.length==3) // forge material give/get <id> 
				{
					if (!utils.isPlayer(sender, true)) return false;
					Player p = (Player) sender;
					if (!utils.hasPermission(p, "supper.material.save", true)) return false;
					
					ItemStack item = SupperForge.matStorage.getSMaterial(args[2]).convertToItemStack(false);
					p.getInventory().addItem(item);
					p.sendMessage(prefixMsg + plugin.locale.get("locale", "forge", "material", "give").replace("<id>", args[2]).replace("<p>", p.getName()));
					return true;
				}
				
				if (args.length==4) // forge item give/get <id> <player>
				{
					if (!utils.isPlayer(sender, false))
					{
						if (!utils.isOnlinePlayer(sender, args[3])) return false;
						Player reciever = Bukkit.getPlayer(args[3]);
						
						ItemStack item = SupperForge.matStorage.getSMaterial(args[2]).convertToItemStack(false);
						reciever.getInventory().addItem(item);
						sender.sendMessage(prefixMsg + plugin.locale.get("locale", "forge", "material", "give").replace("<id>", args[2]).replace("<p>", args[3])
								.replace("<n>", 1+""));
						reciever.sendMessage(prefixMsg + plugin.locale.get("locale", "forge", "material", "gain").replace("<id>", args[2]).replace("<p>", console)
								.replace("<n>", 1+""));
						return true;
					}
					else
					{
						Player p = (Player) sender;
						if (!utils.hasPermission(p, "supper.material.give", true)) return false;
						if (!utils.isOnlinePlayer(sender ,args[3])) return false;
						Player reciever = Bukkit.getPlayer(args[3]);
						
						ItemStack item = SupperForge.matStorage.getSMaterial(args[2]).convertToItemStack(false);
						reciever.getInventory().addItem(item);
						p.sendMessage(prefixMsg + plugin.locale.get("locale", "forge", "material", "give").replace("<id>", args[2]).replace("<p>", args[3])
								.replace("<n>", 1+""));
						if (p!=reciever) reciever.sendMessage(prefixMsg + plugin.locale.get("locale", "forge", "material", "give").replace("<id>", args[2]).replace("<p>", p.getName())
								.replace("<n>", 1+""));
						return true;
					}
				}
				
				if (args.length==5) // forge item give/get <id> <player> <amount>
				{
					if (!utils.isPlayer(sender, false))
					{
						if (!utils.isOnlinePlayer(sender, args[3])) return false;
						Player reciever = Bukkit.getPlayer(args[3]);
						if (!utils.isNumberCmd(sender, args[4])) return false;
						
						ItemStack item = SupperForge.matStorage.getSMaterial(args[2]).convertToItemStack(false);
						int toGive = Math.min(Integer.parseInt(args[4]), 64);
						item.setAmount(toGive);
						reciever.getInventory().addItem(item);
						sender.sendMessage(prefixMsg + plugin.locale.get("locale", "forge", "material", "give").replace("<id>", args[2]).replace("<p>", args[3])
								.replace("<n>", toGive+""));
						reciever.sendMessage(prefixMsg + plugin.locale.get("locale", "forge", "material", "gain").replace("<id>", args[2]).replace("<p>", console)
								.replace("<n>", toGive+""));
						return true;
					}
					else
					{
						Player p = (Player) sender;
						if (!utils.hasPermission(p, "supper.material.give", true)) return false;
						if (!utils.isOnlinePlayer(sender ,args[3])) return false;
						if (!utils.isNumberCmd(p, args[4])) return false;
						Player reciever = Bukkit.getPlayer(args[3]);
						
						ItemStack item = SupperForge.matStorage.getSMaterial(args[2]).convertToItemStack(false);
						int toGive = Math.min(Integer.parseInt(args[4]), 64);
						item.setAmount(toGive);
						reciever.getInventory().addItem(item);
						p.sendMessage(prefixMsg + plugin.locale.get("locale", "forge", "material", "give").replace("<id>", args[2]).replace("<p>", args[3])
								.replace("<n>", toGive+""));
						if (p!=reciever) reciever.sendMessage(prefixMsg + plugin.locale.get("locale", "forge", "material", "give").replace("<id>", args[2]).replace("<p>", p.getName())
								.replace("<n>", toGive+""));
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
		
		sender.sendMessage(utils.color(plugin.locale.get("locale", "error", "wrong-synax").replace("<cmd>", "/forge material help")));
		help(utils, sender);
		
		return false;
	}
	
	private static void help(Utils utils, CommandSender sender)
	{
		sender.sendMessage(utils.color("&9-----=====< &6/FORGE MATERIAL HELP &9>=====-----"));
		if (utils.isPlayer(sender, false)) // player
		{
			Player p = (Player) sender;
			if (utils.hasPermission(p, "supper.material.save", false))
			{
				sender.sendMessage(utils.color("&e/forge material save [<id>]"));
			}
			if (utils.hasPermission(p, "supper.materials.give", false))
			{
				p.sendMessage(utils.color("&e/forge material give/get <id> [<player>] [<amount>]"));
			}
			p.sendMessage(utils.color("&e/forge material"));
			p.sendMessage(utils.color("&e/forge material open"));
		}
		else // console
		{
			sender.sendMessage(utils.color("&e/forge material give/get <id> <player>"));
		}
		sender.sendMessage(utils.color("&e/forge material help"));
		sender.sendMessage(utils.color("&9-----=====< &6/FORGE MATERIAL HELP &9>=====-----"));
	}
}
