package org.zanissnguyen.SupperForge.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.zanissnguyen.SupperForge.SupperForge;
import org.zanissnguyen.SupperForge.Utils;

public class SData implements CommandExecutor
{
	private Utils utils;
	private SupperForge plugin;
	
	public SData(SupperForge plugin, Utils utils)
	{
		this.utils = utils;
		this.plugin = plugin;
		this.plugin.getCommand("sdata").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{	
		String prefix = plugin.locale.get("locale", "data", "prefix");
		
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
		if (!utils.hasPermission(p, "supper.edit.data", true)) return false;
			
		if (!utils.isHoldItem(p)) return false;
		ItemStack item = p.getInventory().getItemInMainHand().clone();
		
		// sdata <int>
		if (!utils.isNumberCmd(sender, args[0])) return false;
			
		int data = Integer.parseInt(args[0]);
			
		item = utils.updateData(item, data);
			
		p.getInventory().setItemInMainHand(item);
		p.sendMessage(prefix + plugin.locale.get("locale", "data", "set").replace("<n>", args[0]));
		return true;	
	}
	
	private void help(CommandSender sender)
	{
		sender.sendMessage(utils.color("&9-----=====< &6/SDATA HELP &9>=====-----"));
		if (utils.isPlayer(sender, true))
		{
			if (utils.hasPermission((Player) sender, "supper.edit.data", false))
			{
				sender.sendMessage(utils.color("&e/sdata <number>"));
			}
		}
		sender.sendMessage(utils.color("&9-----=====< &6/SDATA HELP &9>=====-----"));
	}
	
}
