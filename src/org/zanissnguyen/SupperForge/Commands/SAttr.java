package org.zanissnguyen.SupperForge.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.zanissnguyen.SupperForge.SupperForge;
import org.zanissnguyen.SupperForge.Utils;
import org.zanissnguyen.SupperForge.API.PlayerStat;

public class SAttr  implements CommandExecutor, TabCompleter
{
	private Utils utils;
	private SupperForge plugin;
	
	public SAttr(SupperForge plugin, Utils utils)
	{
		this.utils = utils;
		this.plugin = plugin;
		this.plugin.getCommand("sattr").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{	
		
		String prefix = plugin.locale.get("locale", "attribute", "prefix");
		
		if (!utils.isPlayer(sender, true)) return false;
		Player p = (Player) sender;
		if (!utils.hasPermission(p, "supper.edit.attribute", true)) return false;
			
		if (!utils.isHoldItem(p)) return false;
		ItemStack item = p.getInventory().getItemInMainHand().clone();
		
		if (args.length==0) 
		{
			help(sender);
			return true;
		}
		
		if (args.length==1)
		{
			if (args[0].equalsIgnoreCase("help"))
			{
				help(sender);
				return true;
			}
		}
		
		if (args.length==2)
		{
			if (args[0].equalsIgnoreCase("durability"))
			{
				if (utils.isNumberCmd(sender, args[1]))
				{
					item = SupperForge.psUtils.setMaxDurability(item, (int) Double.parseDouble(args[1]));
					p.getInventory().setItemInMainHand(item);
					return true;
				}
			}
			
			if (args[0].equalsIgnoreCase("type"))
			{
				if (!plugin.locale.getAllType().contains(args[1]))
				{
					String msg = plugin.locale.get("locale", "error", "no-type").replace("<t>", args[1]);
					p.sendMessage(msg);
					return false;
				}
				
				item = SupperForge.psUtils.setType(item, args[1]);
				p.getInventory().setItemInMainHand(item);
				String msg = plugin.locale.get("locale", "attribute", "type").replace("<t>", args[1]);
				p.sendMessage(prefix + " " + msg);
				return true;
			}
		}
		
		if (args.length==3)
		{
			if (args[0].equalsIgnoreCase("stat"))
			{
				if (!plugin.locale.getAllStat().contains(args[1]))
				{
					String msg = plugin.locale.get("locale", "error", "no-stat").replace("<s>", args[1]);
					p.sendMessage(msg);
					return false;
				}
				
				if (utils.isNumberCmd(p, args[2]))
				{
					item = SupperForge.psUtils.setStat(item, args[1], Double.parseDouble(args[2]));
					p.getInventory().setItemInMainHand(item);
					String msg = plugin.locale.get("locale", "attribute", "stat", "set").replace("<s>", args[1]).replace("<n>", args[2]);
					p.sendMessage(prefix + " " + msg);
					PlayerStat.updateStat(p);
					return true;
				} else return false;
				
			}
		}
		
		p.sendMessage(utils.color(plugin.locale.get("locale", "error", "wrong-synax").replace("<cmd>", "/sattr help")));
		help(p);

		return true;	
	}
	
	private void help(CommandSender sender)
	{
		sender.sendMessage(utils.color("&9-----=====< &6/SATTR HELP &9>=====-----"));
		if (utils.isPlayer(sender, true))
		{
			if (utils.hasPermission((Player) sender, "supper.edit.attribute", false))
			{
				sender.sendMessage(utils.color("&e/sattr stat &b<stat> <amount>"));
				sender.sendMessage(utils.color("&e/sattr type &b<type>"));
				sender.sendMessage(utils.color("&e/sattr help"));
			}
		}
		sender.sendMessage(utils.color("&9-----=====< &6/SATTR HELP &9>=====-----"));
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> result = new ArrayList<>();
		
		if (!utils.isPlayer(sender, false)) return result;
		Player p = (Player) sender;
		
		if (!utils.hasPermission(p, "supper.edit.attribute", false)) return result;
		
		if (args.length==1)
		{
			result.add("stat");
			result.add("type");
			result.add("durability");
			result.sort(null);
		}
		
		if (args.length==2)
		{
			if (args[0].equalsIgnoreCase("stat"))
			{
				result.addAll(plugin.locale.getAllStat());
				result.sort(null);
			}
			
			if (args[0].equalsIgnoreCase("type"))
			{
				result.addAll(plugin.locale.getAllType());
				result.sort(null);
			}
		}
		
		
		return result;
	}
	
}
