package org.zanissnguyen.SupperForge.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.zanissnguyen.SupperForge.SupperForge;
import org.zanissnguyen.SupperForge.Utils;

public class SFlag implements CommandExecutor, TabCompleter
{
	private Utils utils;
	private SupperForge plugin;
	
	public SFlag(SupperForge plugin, Utils utils)
	{
		this.utils = utils;
		this.plugin = plugin;
		this.plugin.getCommand("sflag").setExecutor(this);
		this.plugin.getCommand("sflag").setTabCompleter(this); 
		
	}
	
	/*
	 * Player: add (2), remove (1), clear (0)
	 * Console: 
	 * 
	 * Line require: set, insert, remove
	 * Index limit: remove
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{	
		String prefix = plugin.locale.get("locale", "flag", "prefix");
		
		if (!utils.isPlayer(sender, true)) return false;
		
		if (args.length==0) 
		{
			help(sender);
			return true;
		}
		
		if (args[0].equalsIgnoreCase("help"))
		{
			help(sender);
			return true;
		}
		
		Player p = (Player) sender;
		if (!utils.hasPermission(p, "supper.edit.flag", true)) return false;
			
		if (!utils.isHoldItem(p)) return false;
		ItemStack item = p.getInventory().getItemInMainHand().clone();
		
		// sflag remove <flag>
		if (args[0].equalsIgnoreCase("remove") && args.length>1)
		{
			if (!utils.isFlag(args[1]))
			{
				p.sendMessage(plugin.locale.get("loacle", "error", "no-flag").replace("<f>", args[1]));
				return false;
			}
			
			item = utils.removeFlag(item, ItemFlag.valueOf(args[1]));
			p.getInventory().setItemInMainHand(item);
			p.sendMessage(prefix + plugin.locale.get("locale", "flag", "remove").replace("<f>", utils.color(args[1])));
			return true;	
		}
		// sflag add <flag>
		if (args[0].equalsIgnoreCase("add") && args.length>1)
		{
			if (!utils.isFlag(args[1]))
			{
				p.sendMessage(plugin.locale.get("locale", "error", "no-flag").replace("<f>", args[1]));
				return false;
			}
			
			item = utils.addFlag(item, ItemFlag.valueOf(args[1]));
			
			p.getInventory().setItemInMainHand(item);
			p.sendMessage(prefix + plugin.locale.get("locale", "flag", "add").replace("<f>", utils.color(args[1])));
			return true;	
		}
		// sflag clear
		if (args[0].equalsIgnoreCase("clear"))
		{
			item = utils.clearFlag(item);
			p.getInventory().setItemInMainHand(item);
			p.sendMessage(prefix + plugin.locale.get("locale", "flag", "clear"));
			return true;
		}
		
		p.sendMessage(utils.color(plugin.locale.get("locale", "error", "wrong-synax").replace("<cmd>", "/sflag help")));
		help(p);
		
		return false;
	}
	
	private void help(CommandSender sender)
	{
		sender.sendMessage(utils.color("&9-----=====< &6/SFLAG HELP &9>=====-----"));
		if (utils.isPlayer(sender, true))
		{
			if (utils.hasPermission(sender, "supper.edit.flag", false))
			{
				sender.sendMessage(utils.color("&e/sflag add &b<flag>"));
				sender.sendMessage(utils.color("&e/sflag remove &b<flag>"));
				sender.sendMessage(utils.color("&e/sflag clear"));
			}
		}
		sender.sendMessage(utils.color("&9-----=====< &6/SFLAG HELP &9>=====-----"));
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) 
	{
		List<String> result = new ArrayList<>();
		
		if (!utils.isPlayer(sender, false)) return result;
		Player p = (Player) sender;
		
		if (!utils.hasPermission(p, "supper.edit.flag", false)) return result;
		
		List<String> args_0 = new ArrayList<>();
		args_0.add("add");
		args_0.add("remove");
		args_0.add("clear");
		args_0.add("help");
		if (args.length==1)
		{
			String current = args[0];
			for (String s: args_0)
			{
				if (s.startsWith(current)) result.add(s);
			}
			
		}
		
		if (args.length==2)
		{
			String current = args[1];
			if (args[0].equalsIgnoreCase("add"))
			{
				for (ItemFlag fl: ItemFlag.values())
				{
					if (fl.name().startsWith(current)) result.add(fl.name());
				}
			}
			if (args[0].equalsIgnoreCase("remove"))
			{
				ItemStack item = p.getInventory().getItemInMainHand();
				if (item!=null && item.getType()!=Material.AIR)
				{
					for (ItemFlag fl: item.getItemMeta().getItemFlags())					
					{
						if (fl.name().startsWith(current))result.add(fl.name());
					}
				}
			}
		}
		
		result.sort(null);
		return result;
	}
	
}
