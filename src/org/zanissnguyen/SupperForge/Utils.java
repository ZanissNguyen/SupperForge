package org.zanissnguyen.SupperForge;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Utils 
{
	public SupperForge plugin = (SupperForge) Bukkit.getPluginManager().getPlugin("SupperForge-Reborn");
	
	public Utils(SupperForge plugin)
	{
		this.plugin = plugin;
	}
	
	public Boolean isMaterial(String str)
	{
		for (Material mat: Material.values())
		{
			if (mat.toString().equalsIgnoreCase(str)) return true;
		}
		
		return false;
	}
	
	public String color(String s)
	{
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	public static ItemStack createItem(Material type, int amount)
	{
		return new ItemStack(type, amount);
	}
	
	public ItemStack createItem(Material type, int amount, int model_data)
	{
		ItemStack item = createItem(type, amount);
		ItemMeta meta = item.getItemMeta();
		
		if (model_data!=0) meta.setCustomModelData(model_data);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack createItem(Material type, int amount, int model_data, String name, String... lore)
	{		
		ItemStack item = createItem(type, amount, model_data);
//		if (item.hasItemMeta()) 
//		{
			ItemMeta meta = item.getItemMeta();
			if (name!=null) meta.setDisplayName(color(name));
			List<String> loreItem = new ArrayList<>();
			if (lore!=null) for (String s : lore)
			{
				loreItem.add(color(s));
			}
			meta.setLore(loreItem);
			item.setItemMeta(meta);
//		}
		
		return item;
	}
	
	public ItemStack createItem(Material type, int amount,int model_data, Boolean unbreak, String name, String... lore)
	{
		ItemStack item = createItem(type, amount, model_data, name, lore);
//		if (item.hasItemMeta()) 
//		{
			ItemMeta meta = item.getItemMeta();
			
			meta.setUnbreakable(unbreak);
			item.setItemMeta(meta);
//		}
		
		return item;
	}
	
	// LORE method
	public ItemStack addLore(ItemStack item, int index, String str)
	{
		ItemStack result = item.clone();
		ItemMeta meta = result.getItemMeta();
		List<String> lore = new ArrayList<>();
		if (meta.hasLore())
		{
			lore = getLore(result);
		}
		
		if (index==-1)
		{
			lore.add(color(str));
		} 
		/*
		 * if index is really in index just add
		 * else we add empty line until reach that index
		 */
		else // insert
		{
			int size = lore.size();
			if (index >=0 && index<size)
			{
				lore.add(index, color(str));
			}
			else
			{
				while (index!=size)
				{
					lore.add("");
					size = lore.size();
				}
				lore.add(index, color(str));	
			}
		}
		meta.setLore(lore);
		result.setItemMeta(meta);
		
		return result;
	}
	
	public ItemStack setLore(ItemStack item, int index, String str)
	{
		ItemStack result = item.clone();
		ItemMeta meta = result.getItemMeta();
		List<String> lore = new ArrayList<>();
		if (meta.hasLore())
		{
			lore = getLore(result);
		}
		
		int size = lore.size();
		if (index >=0 && index<=size)
		{
			lore.set(index, color(str));
		}
		else
		{
			while (index!=size)
			{
				lore.add("");
				size = lore.size();
			}
			lore.add(index, color(str));
			
		}
		
		meta.setLore(lore);
		result.setItemMeta(meta);
		
		return result;
	}
	
	public ItemStack addAllLore(ItemStack item, List<String> toAdd)
	{
		ItemStack result = item.clone();
		ItemMeta meta = result.getItemMeta();
		List<String> lore = new ArrayList<>();
		if (meta.hasLore())
		{
			lore = getLore(result);
		}
		
		for (String s: toAdd) lore.add(color(s));
		
		meta.setLore(lore);
		result.setItemMeta(meta);
		
		return result;
	}
	
	public ItemStack removeLore(ItemStack item, int index)
	{
		ItemStack result = item.clone();
		ItemMeta meta = result.getItemMeta();
		List<String> lore = new ArrayList<>();
		if (meta.hasLore())
		{
			lore = getLore(result);
		}
		
		int size = lore.size();
		if (index >=0 && index<=size)
		{
			lore.remove(index);
		}
		
		meta.setLore(lore);
		result.setItemMeta(meta);
		
		return result;
	}
	
	public ItemStack clearLore(ItemStack item)
	{
		ItemStack result = item.clone();
		ItemMeta meta = result.getItemMeta();
		
		meta.setLore(new ArrayList<>());
		result.setItemMeta(meta);
		
		return result;
	}
	
	public List<String> getLore(ItemStack item)
	{
		List<String> result = new ArrayList<>();
		
		if (item == null || item.getType()==Material.AIR) return new ArrayList<>();
		
		if (item.getItemMeta()!=null)
		{
			if (item.getItemMeta().getLore()!=null) 
			{
				result = item.getItemMeta().getLore();
			}
		}
		
		return result;
	}
	
	// NAME method
	public ItemStack setName(ItemStack item, String name)
	{
		ItemStack result = item.clone();
		ItemMeta meta = result.getItemMeta();
		
		meta.setDisplayName(color(name));
		
		result.setItemMeta(meta);
		return result;
	}
	
	public ItemStack clearName(ItemStack item)
	{
		ItemStack result = item.clone();
		ItemMeta meta = result.getItemMeta();
		
		meta.setDisplayName(null);
		
		result.setItemMeta(meta);
		return result;
	}
	
	public String getName(ItemStack item)
	{
		if (!item.getItemMeta().hasDisplayName()) return "";
		else 
		{
			return item.getItemMeta().getDisplayName();
		}
	}
	
	//DATA method
	public ItemStack updateData(ItemStack item, int data)
	{
		ItemStack result = item.clone();
		ItemMeta meta = result.getItemMeta();
		
		meta.setCustomModelData(data);
		result.setItemMeta(meta);
		
		return result;
	}
	
	// ENCHANTMENT method
	public ItemStack removeEnchant(ItemStack item, Enchantment enchant)
	{
		ItemStack result = item.clone();
		ItemMeta meta = result.getItemMeta();
		
		meta.removeEnchant(enchant);
		result.setItemMeta(meta);
		
		return result;
	}
	
	public ItemStack addEnchant(ItemStack item, Enchantment enchant, int level)
	{
		ItemStack result = item.clone();
		ItemMeta meta = result.getItemMeta();
		
		meta.addEnchant(enchant, level, true);
		result.setItemMeta(meta);
		
		return result;
	}
	
	public ItemStack clearEnchant(ItemStack item)
	{
		ItemStack result = item.clone();
		ItemMeta meta = result.getItemMeta();
		
		for (Enchantment ench: meta.getEnchants().keySet())
		{
			meta.removeEnchant(ench);
		}
		result.setItemMeta(meta);
		
		return result;
	}
	
	@SuppressWarnings("deprecation")
	public Boolean isEnchantment(String str)
	{
		for (Enchantment ench : Enchantment.values())
		{
			if (ench.getName().equalsIgnoreCase(str)) return true;
		}
		
		return false;
	}
	
	// FLAG method
	public ItemStack removeFlag(ItemStack item, ItemFlag flag)
	{
		ItemStack result = item.clone();
		ItemMeta meta = result.getItemMeta();
			
		meta.removeItemFlags(flag);
		result.setItemMeta(meta);
			
		return result;
	}
		
	public ItemStack addFlag(ItemStack item, ItemFlag flag)
	{
		ItemStack result = item.clone();
		ItemMeta meta = result.getItemMeta();
			
		meta.addItemFlags(flag);
		result.setItemMeta(meta);
			
		return result;
	}
		
	public ItemStack clearFlag(ItemStack item)
	{
		ItemStack result = item.clone();
		ItemMeta meta = result.getItemMeta();
			
		for (ItemFlag fl: meta.getItemFlags())
		{
			meta.removeItemFlags(fl);
		}
		result.setItemMeta(meta);
			
		return result;
	}
		
	public Boolean isFlag(String str)
	{
		for (ItemFlag fl : ItemFlag.values())
		{
			if (fl.name().equalsIgnoreCase(str)) return true;
		}
		return false;
	}
	
	// Command Utils
	public boolean hasPermission(CommandSender p, String permission, boolean msg)
	{
		if (p.hasPermission(permission)) return true;
		else
		{
			if (msg) p.sendMessage(plugin.locale.get("locale", "error", "no-permission"));
			return false;
		}
	}
	
	public boolean isPlayer(CommandSender sender, boolean msg)
	{
		if (sender instanceof Player) return true;
		else
		{
			if (msg) sender.sendMessage(plugin.locale.get("locale", "error", "only-player"));
			return false;
		}
	}
	
	public boolean isHoldItem(Player p)
	{
		ItemStack item = p.getInventory().getItemInMainHand();
		if (!(item.getType()==Material.AIR || item == null)) return true;
		else
		{
			p.sendMessage(plugin.locale.get("locale", "error", "hand-nothing"));
			return false;
		}
	}
	
	public boolean isNumberCmd(CommandSender sender, String str)
	{
		try 
		{
			Double.parseDouble(str);
	        return true;
	    } 
		catch (NumberFormatException e) 
		{
			sender.sendMessage(plugin.locale.get("locale", "error", "not-number").replace("<n>", str));
	    	return false;
	    }
	}
	
	public boolean isNumber(String str)
	{
		try 
		{
			Double.parseDouble(str);
	        return true;
	    } 
		catch (NumberFormatException e) 
		{
	    	return false;
	    }
	}
	
	public boolean isOnlinePlayer(CommandSender sender, String player)
	{
		if (Bukkit.getPlayer(player)==null)
		{
			sender.sendMessage(plugin.locale.get("locale", "error", "no-player").replace("<p>", player));
			return false;
		}
		
		Player p = Bukkit.getPlayer(player);
		
		if (!Bukkit.getOnlinePlayers().contains(p))
		{
			sender.sendMessage(plugin.locale.get("locale", "error", "player-not-online").replace("<p>", player));
			return false;
		}
		
		return true;
	}
	
	public int getAmount(ItemStack item, Player p)
	{
		int result = 0;
		for (ItemStack i: p.getInventory().getContents())
		{
			if (i==null) continue;
//			System.out.println(item.getType().toString());
			
			ItemStack toCheck = i.clone();
			toCheck.setAmount(1);
			if (item.isSimilar(toCheck))
			{
				result+=i.getAmount();
			}
		}
		return result;
	}
	
	public double fixedDecimal(Double number, int amount)
	{
		int temp = 1;
		for (int i = 0; i<amount; i++)
		{
			temp*=10;
		}
		
		double result = 0;
		number*=temp;
		int IntRs = (int) Math.ceil(number);
		result = IntRs/(double) temp;
		
		return result;
	}
	
	public double getLevel(Player p)
	{
		double result = 0;
		
		result = p.getLevel() + p.getExp();
		
		return result;
	}
	
	public void takeItem(ItemStack item, int amount, Player p)
	{
		
		for (ItemStack i: p.getInventory().getContents())
		{
			if (i==null) continue;
			ItemStack inSlot = i.clone();
			inSlot.setAmount(1);
			if (inSlot.isSimilar(item))
			{
				if (amount-i.getAmount()>0)
				{
					amount -= i.getAmount();
					i.setAmount(0);
				}
				else
				{
					i.setAmount(i.getAmount()-amount);
					amount = 0;
				}
			}
			if (amount ==0) return;
		}
	}
	
	public String standardStr(String str)
	{
		str = str.toLowerCase();
		String result = "";
		
		boolean startWord = true;
		for (int i = 0 ;i<str.length(); i++)
		{
			if (str.charAt(i)=='_')
			{
				result+=" ";
				startWord = true;
			}
			else if (startWord)
			{
				result+=(char)(str.charAt(i)-32);
				startWord=false;
			}
			else result+=str.charAt(i);
		}
		return result;
	}
	
	public void playSound(Sound s, Player p, float volume)
	{
		p.playSound(p.getLocation(), s, volume, volume);
	}
	
	public boolean isArmor(Material type)
	{
		String str = type.toString();
		if (str.contains("CHESTPLATE")
				|| str.contains("HELMET")
				|| str.contains("LEGGINGS")
				|| str.contains("BOOTS")
				|| str.contains("_SKULL")
				|| str.contains("_HEAD")
				|| str.contains("ELYTRA")) return true;
		return false;
	}
	
	public boolean isRange(Material type)
	{
		String str = type.toString();
		if (str.contains("BOW")
				|| str.contains("CROSSBOW")) return true;
		return false;
	}
	
	public double getRandom(double start, double end, int digit)
	{
		double range = end - start;
		double result = Math.random()*range+start;
		
		result = fixedDecimal(result, digit);
		
		return result;
	}
	
	public boolean roll(double chance)
	{
		double temp = Math.random();
		if (temp<=chance) return true;
		else return false;
	}
}
