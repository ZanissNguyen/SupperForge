package org.zanissnguyen.SupperForge.Commands.Forge;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.zanissnguyen.SupperForge.SupperForge;
import org.zanissnguyen.SupperForge.Utils;

public class ForgeCmd implements CommandExecutor, TabCompleter
{
	private Utils utils;
	private SupperForge plugin;
	
	public ForgeCmd(SupperForge plugin, Utils utils)
	{
		this.utils = utils;
		this.plugin = plugin;
		this.plugin.getCommand("forge").setExecutor(this);
		this.plugin.getCommand("forge").setTabCompleter(this);
	}
	

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) 
	{
		List<String> result = new ArrayList<>();
		
		List<String> args_0 = new ArrayList<>();
		args_0.add("item");
		args_0.add("material");
		args_0.add("recipe");
		args_0.add("stat");
		args_0.add("reload");
		if (args.length==1)
		{
			String current = args[0];
			for (String s: args_0)
			{
				if (s.startsWith(current)) result.add(s);
			}
		}
		
		if (args.length>=2)
		{
			if (args[0].equalsIgnoreCase("item"))
			{
				result = SItemCmd.ItemTabComplete(plugin, utils, sender, cmd, label, args);
			}
			if (args[0].equalsIgnoreCase("material"))
			{
				result = SMaterialCmd.MaterialTabComplete(plugin, utils, sender, cmd, label, args);
			}
		}
		
		result.sort(null);
		return result;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if (args.length==0)
		{
			return true;
		}
		
		if (args.length>=1)
		{
			if (args[0].equalsIgnoreCase("reload"))
			{
				if (utils.hasPermission(sender, "supper.reload", true)) 
				{
					SupperForge.reload();
					sender.sendMessage(plugin.locale.get("locale.reload")
							.replace("<plugin_name>", plugin.getName()).replace("<version>", plugin.getDescription().getVersion()));
				}
				return true;
			}
			
			if (args[0].equalsIgnoreCase("item"))
			{
				// item menu, command
				SItemCmd.onItemCommand(plugin, utils, sender, cmd, label, args);
				return true;
			}
			
			if (args[0].equalsIgnoreCase("material"))
			{
				// material menu, command
				SMaterialCmd.onMaterialCommand(plugin, utils, sender, cmd, label, args);
				return true;
			}
			
			if (args[0].equalsIgnoreCase("recipe"))
			{
				// recipe menu, command
				if (!utils.isPlayer(sender, true)) return false;
				Player p = (Player) sender;
				plugin.recipeUI.open(p, "", 1);
				return true;
			}
			
			if (args[0].equalsIgnoreCase("blueprint"))
			{
				// blueprint menu, command
			}
			
			if (args[0].equalsIgnoreCase("stat"))
			{
				// stat menu
				if (!utils.isPlayer(sender, true)) return false;
				Player p = (Player) sender;
				plugin.statUI.open(p);
				return true;
			}
			
			if (args[0].equalsIgnoreCase("gem"))
			{
				// gem menu
			}
		}
		
		return true;
	}

}
