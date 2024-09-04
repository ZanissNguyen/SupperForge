package org.zanissnguyen.SupperForge.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.zanissnguyen.SupperForge.SupperForge;
import org.zanissnguyen.SupperForge.Utils;

public class SEnch implements CommandExecutor, TabCompleter
{
	private Utils utils;
	private SupperForge plugin;
	
	public SEnch(SupperForge plugin, Utils utils)
	{
		this.utils = utils;
		this.plugin = plugin;
		this.plugin.getCommand("sench").setExecutor(this);
		this.plugin.getCommand("sench").setTabCompleter(this); 
		
	}
	
	/*
	 * Player: add (2), remove (1), clear (0)
	 * Console: 
	 * 
	 * Line require: set, insert, remove
	 * Index limit: remove
	 */
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{	
		String prefix = plugin.locale.get("locale", "ench", "prefix");
		
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
		if (!utils.hasPermission(p, "supper.edit.ench", true)) return false;
			
		if (!utils.isHoldItem(p)) return false;
		ItemStack item = p.getInventory().getItemInMainHand().clone();
		
		// sench remove <enchantment>
		if (args[0].equalsIgnoreCase("remove") && args.length>1)
		{
			if (!utils.isEnchantment(args[1]))
			{
				p.sendMessage(plugin.locale.get("loacle", "error", "no-enchantment").replace("<e>", args[1]));
				return false;
			}
			
			item = utils.removeEnchant(item, Enchantment.getByName(args[1]));
			p.getInventory().setItemInMainHand(item);
			p.sendMessage(prefix + plugin.locale.get("locale", "ench", "remove").replace("<e>", utils.color(args[1])));
			return true;	
		}
		// sench add <enchantment> <level>
		if (args[0].equalsIgnoreCase("add") && args.length>2)
		{
			if (!utils.isEnchantment(args[1]))
			{
				p.sendMessage(plugin.locale.get("locale", "error", "no-enchantment").replace("<e>", args[1]));
				return false;
			}
			
			if (!utils.isNumberCmd(sender, args[2])) return false;
			
			int lvl = Integer.parseInt(args[2]);
			
			if (lvl<0)
			{
				p.sendMessage(plugin.locale.get("locale", "error", "out-index").replace("<n>", args[2]).replace("<r>", ">0"));
				return false;
			}
			
			item = utils.addEnchant(item, Enchantment.getByName(args[1]), lvl);
			
			p.getInventory().setItemInMainHand(item);
			p.sendMessage(prefix + plugin.locale.get("locale", "ench", "add").replace("<e>", utils.color(args[1])).replace("<l>", args[2]));
			return true;	
		}
		// sench clear
		if (args[0].equalsIgnoreCase("clear"))
		{
			item = utils.clearEnchant(item);
			p.getInventory().setItemInMainHand(item);
			p.sendMessage(prefix + plugin.locale.get("locale", "ench", "clear"));
			return true;
		}
		
		p.sendMessage(utils.color(plugin.locale.get("locale", "error", "wrong-synax").replace("<cmd>", "/sench help")));
		help(p);
		
		return false;
	}
	
	private void help(CommandSender sender)
	{
		sender.sendMessage(utils.color("&9-----=====< &6/SENCH HELP &9>=====-----"));
		if (utils.isPlayer(sender, true))
		{
			if (utils.hasPermission(sender, "supper.edit.ench", false))
			{
				sender.sendMessage(utils.color("&e/sench add &b<enchantment> <level>"));
				sender.sendMessage(utils.color("&e/sench remove &b<enchantment>"));
				sender.sendMessage(utils.color("&e/sench clear"));
			}
		}
		sender.sendMessage(utils.color("&9-----=====< &6/SENCH HELP &9>=====-----"));
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) 
	{
		List<String> result = new ArrayList<>();
		
		if (!utils.isPlayer(sender, false)) return result;
		Player p = (Player) sender;
		
		if (!utils.hasPermission(p, "supper.edit.ench", false)) return result;
		
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
				for (Enchantment ench: Enchantment.values())
				{
					if (ench.getName().startsWith(current)) result.add(ench.getName());
				}
			}
			if (args[0].equalsIgnoreCase("remove"))
			{
				ItemStack item = p.getInventory().getItemInMainHand();
				if (item!=null && item.getType()!=Material.AIR)
				{
					for (Enchantment ench: item.getItemMeta().getEnchants().keySet())
					{
						if (ench.getName().startsWith(current)) result.add(ench.getName());
					}
					
				}
			}
		}
		
		result.sort(null);
		return result;
	}
	
}
