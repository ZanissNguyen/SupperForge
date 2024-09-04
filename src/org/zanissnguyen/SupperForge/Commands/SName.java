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

public class SName implements CommandExecutor, TabCompleter
{
	private Utils utils;
	private SupperForge plugin;
	
	public SName(SupperForge plugin, Utils utils)
	{
		this.utils = utils;
		this.plugin = plugin;
		this.plugin.getCommand("sname").setExecutor(this);
		this.plugin.getCommand("sname").setTabCompleter(this); 
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{	
		String prefix = plugin.locale.get("locale", "name", "prefix");
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
		if (!utils.hasPermission(p, "supper.edit.name", true)) return false;
			
		if (!utils.isHoldItem(p)) return false;
		ItemStack item = p.getInventory().getItemInMainHand().clone();
		
		// sname set <string>
		if (args[0].equalsIgnoreCase("set"))
		{
			String toAdd = "";
			for (int i=1; i<=args.length-1; i++)
				if (i>1) toAdd = toAdd+" "+args[i];
				else toAdd = toAdd+args[i];
			
			item = utils.setName(item, toAdd);
			p.getInventory().setItemInMainHand(item);
			p.sendMessage(prefix + plugin.locale.get("locale", "name", "set").replace("<s>", utils.color(toAdd)));
			return true;	
		}
	
		// sname clear
		if (args[0].equalsIgnoreCase("clear"))
		{
			item = utils.clearName(item);
			p.getInventory().setItemInMainHand(item);
			p.sendMessage(prefix + plugin.locale.get("locale", "name", "clear"));
			return true;
		}
		
		p.sendMessage(utils.color(plugin.locale.get("locale", "error", "wrong-synax").replace("<cmd>", "/sname help")));
		help(p);
		
		return false;
	}
	
	private void help(CommandSender sender)
	{
		sender.sendMessage(utils.color("&9-----=====< &6/SNAME HELP &9>=====-----"));
		if (utils.isPlayer(sender, true))
		{
			if (utils.hasPermission((Player) sender, "supper.edit.name", false))
			{
				sender.sendMessage(utils.color("&e/sname set <string>"));
				sender.sendMessage(utils.color("&e/sname clear"));
			}
		}
		sender.sendMessage(utils.color("&9-----=====< &6/SNAME HELP &9>=====-----"));
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) 
	{
		List<String> result = new ArrayList<>();
		
		if (!utils.isPlayer(sender, false)) return result;
		Player p = (Player) sender;
		
		if (!utils.hasPermission(p, "supper.edit.name", false)) return result;
		
		List<String> args_0 = new ArrayList<>();
		args_0.add("set");
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
