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

public class SLore implements CommandExecutor, TabCompleter
{
	private Utils utils;
	private SupperForge plugin;
	
	public SLore(SupperForge plugin, Utils utils)
	{
		this.utils = utils;
		this.plugin = plugin;
		this.plugin.getCommand("slore").setExecutor(this);
		this.plugin.getCommand("slore").setTabCompleter(this); 
		
	}
	
	/*
	 * Player: add (1), set (2), insert (2), remove (2), clear (0)
	 * Console: 
	 * 
	 * Line require: set, insert, remove
	 * Index limit: remove
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{	
		String prefix = plugin.locale.get("locale", "lore", "prefix");
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
		if (!utils.hasPermission(p, "supper.edit.lore", true)) return false;
			
		if (!utils.isHoldItem(p)) return false;
		ItemStack item = p.getInventory().getItemInMainHand().clone();
		
		// slore add <string>
		if (args[0].equalsIgnoreCase("add"))
		{
			String toAdd = "";
			for (int i=1; i<=args.length-1; i++)
				if (i>1) toAdd = toAdd+" "+args[i];
				else toAdd = toAdd+args[i];
			
			item = utils.addLore(item, -1, toAdd);
			p.getInventory().setItemInMainHand(item);
			p.sendMessage(prefix + plugin.locale.get("locale", "lore", "add").replace("<s>", utils.color(toAdd)));
			return true;	
		}
		// slore set <line> <string>
		if (args[0].equalsIgnoreCase("set") && args.length>2)
		{
			if (!utils.isNumberCmd(sender, args[1])) return false;
			
			int line = Integer.parseInt(args[1]);
			
			if (line<0)
			{
				p.sendMessage(plugin.locale.get("locale", "error", "out-index").replace("<n>", args[1]).replace("<r>", ">0"));
				return false;
			}
			
			String toAdd = "";
			for (int i=2; i<=args.length-1; i++)
				if (i>2) toAdd = toAdd+" "+args[i];
				else toAdd = toAdd+args[i];
			item = utils.setLore(item, line, toAdd);
			
			p.getInventory().setItemInMainHand(item);
			p.sendMessage(prefix + plugin.locale.get("locale", "lore", "set").replace("<s>", utils.color(toAdd)).replace("<l>", args[1]));
			return true;	
		}
		// slore insert <line> <string>
		if (args[0].equalsIgnoreCase("insert") && args.length>2)
		{
			if (!utils.isNumberCmd(sender, args[1])) return false;
			
			int line = Integer.parseInt(args[1]);
			
			if (line<0)
			{
				p.sendMessage(plugin.locale.get("locale", "error", "out-index").replace("<n>", args[1]).replace("<r>", ">0"));
				return false;
			}
			
			String toAdd = "";
			for (int i=2; i<=args.length-1; i++)
				if (i>2) toAdd = toAdd+" "+args[i];
				else toAdd = toAdd+args[i];
			item = utils.addLore(item, line, toAdd);
			
			p.getInventory().setItemInMainHand(item);
			p.sendMessage(prefix + plugin.locale.get("locale", "lore", "insert").replace("<s>", toAdd).replace("<l>", args[1]));
			return true;	
		}
		// slore remove <line>
		if (args[0].equalsIgnoreCase("remove") && args.length>1)
		{
			if (!utils.isNumberCmd(sender, args[1])) return false;
			
			int line = Integer.parseInt(args[1]);
			
			if (line<0 || line>=utils.getLore(item).size())
			{
				p.sendMessage(plugin.locale.get("locale", "error", "out-index").replace("<n>", args[1]).replace("<r>", "0-"+utils.getLore(item).size()));
				return false;
			}
			
			item = utils.removeLore(item, line);
			
			p.getInventory().setItemInMainHand(item);
			p.sendMessage(prefix + plugin.locale.get("locale", "lore", "remove").replace("<l>", args[1]));
			return true;	
		}
		// slore clear
		if (args[0].equalsIgnoreCase("clear"))
		{
			item = utils.clearLore(item);
			p.getInventory().setItemInMainHand(item);
			p.sendMessage(prefix + plugin.locale.get("locale", "lore", "clear"));
			return true;
		}
		
		p.sendMessage(utils.color(plugin.locale.get("locale", "error", "wrong-synax").replace("<cmd>", "/slore help")));
		help(p);
		
		return false;
	}
	
	private void help(CommandSender sender)
	{
		sender.sendMessage(utils.color("&9-----=====< &6/SLORE HELP &9>=====-----"));
		if (utils.isPlayer(sender, true))
		{
			if (utils.hasPermission(sender, "supper.edit.lore", false))
			{
				sender.sendMessage(utils.color("&e/slore add &b<string>"));
				sender.sendMessage(utils.color("&e/slore set &b<line> <string>"));
				sender.sendMessage(utils.color("&e/slore insert &b<line> <string>"));
				sender.sendMessage(utils.color("&e/slore remove &b<line>"));
				sender.sendMessage(utils.color("&e/slore clear"));
			}
		}
		sender.sendMessage(utils.color("&9-----=====< &6/SLORE HELP &9>=====-----"));
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) 
	{
		List<String> result = new ArrayList<>();
		
		if (!utils.isPlayer(sender, false)) return result;
		Player p = (Player) sender;
		
		if (!utils.hasPermission(p, "supper.edit.lore", false)) return result;
		
		List<String> args_0 = new ArrayList<>();
		args_0.add("add");
		args_0.add("insert");
		args_0.add("set");
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
		
		result.sort(null);
		return result;
	}
	
}
