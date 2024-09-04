package org.zanissnguyen.SupperForge.API;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.zanissnguyen.SupperForge.SAttributeUtils;
import org.zanissnguyen.SupperForge.SupperForge;
import org.zanissnguyen.SupperForge.Utils;

public class SItem 
{
	private Utils utils;
	private SAttributeUtils psUtils = SupperForge.psUtils;
	private SupperForge plugin = SupperForge.getInstance();
	
	public String id;
	public String type;
	public String name;
	public int data;
	public List<String> lore;
	public List<String> enchants;
	public List<String> flags;
	public List<String> stats;
	public String item_type;
	public Boolean unbreakable;
	
	public SItem(String id, String type, String name, int data, List<String> lore, List<String> enchants, List<String> flags
			, List<String> stats, String item_type, Boolean unbreakable)
	{
		utils = SupperForge.utils;
		
		this.id = id;
		this.type = type;
		this.name = name;
		this.data = data;
		this.enchants = enchants;
		this.lore = lore;
		this.flags = flags;
		this.stats = stats;
		this.item_type = item_type;
		this.unbreakable = unbreakable;
	}
	
	@SuppressWarnings("deprecation")
	public SItem(String id, ItemStack item)
	{
		this.id = id;
		this.type = item.getType().toString();
		
		if (type.equalsIgnoreCase("AIR") || !item.hasItemMeta())
		{
			this.name = null;
			this.data = 0;
			this.enchants = new ArrayList<>();
			this.flags = new ArrayList<>();
			this.unbreakable = false;
			this.lore = new ArrayList<>();
			this.stats = new ArrayList<>();
			this.item_type = "";
			return;
		}
		
		ItemMeta meta = item.getItemMeta();
		
		//name
		if (meta.hasDisplayName())
		{
			this.name = meta.getDisplayName();
		}
		else
		{
			this.name = null;
		}
		
		//lore
		if (meta.hasLore())
		{
			List<String> lore = meta.getLore();
			
			List<String> stats = new ArrayList<>();
			for (int i = plugin.locale.getAllStat().size()-1; i>=0; i--)
			{
				String stat = plugin.locale.getAllStat().get(i);
				if (psUtils.isItemHasStat(item, stat)!=-1)
				{	
					double value = psUtils.getStat(item, stat);
					int scale = PlayerStat.percent().contains(stat) ? 100 : 1;
					if (value != 0);
					{
						stats.add(stat+":"+value*scale);
					}
					lore.remove(psUtils.isItemHasStat(item, stat));
				}
			}
			this.stats = stats;
			
			this.item_type = psUtils.getType(item);
			lore.remove(psUtils.isItemHasType(item));
			
//			System.out.println(lore.toString());
			
			this.lore = lore;
		}
		else
		{
			this.item_type = "";
			this.stats = new ArrayList<>();
			this.lore = new ArrayList<>();
		}
		
		//data
		if (meta.hasCustomModelData())
		{
			this.data = meta.getCustomModelData();
		}
		else
		{
			this.data = 0;
		}
		
		//enchants
		if (meta.hasEnchants())
		{
			List<String> addEnchant = new ArrayList<>();
			for (Enchantment ench: meta.getEnchants().keySet())
			{
				addEnchant.add(ench.getName()+":"+meta.getEnchantLevel(ench));
			}
			this.enchants = addEnchant;
		}
		else 
		{
			this.enchants = new ArrayList<>();
		}
		
		//flags
		if (meta.getItemFlags()!=null || meta.getItemFlags().size()!=0)
		{
			List<String> addFlag = new ArrayList<>();
			for (ItemFlag fl: meta.getItemFlags())
			{
				addFlag.add(fl.name());
			}
			this.flags = addFlag;
		}
		else
		{
			this.flags = new ArrayList<>();
		}
		
		//unbreakable
		this.unbreakable = meta.isUnbreakable();
		
	}
	
	@SuppressWarnings("deprecation")
	public ItemStack convertToItemStack(boolean icon, boolean inv)
	{
		String name = this.name;
		String type = this.type;
		int digit = plugin.config.getDigit();
		String rangeSymbol = plugin.locale.get("locale", "symbol", "range");
		
		List<String> error = new ArrayList<>();
		error.add(utils.color("&4Error(s):"));
		
		// type exception!
		if (this.type.equalsIgnoreCase("Not Found") || !utils.isMaterial(this.type) || this.type.equalsIgnoreCase("AIR"))
		{
			if (inv)
			{
				return utils.createItem(Material.AIR, 1, 0);
			}
			error.add(utils.color("&4+ type: &c"+type+"&4 is not a vanilla type."));
			type = "BARRIER";
		}
		
		ItemStack result = utils.createItem(Material.valueOf(type), 1, data, unbreakable, " ");
		
		ItemMeta meta = result.getItemMeta();
		
		//name
		if (name.equalsIgnoreCase("Not Found"))
		{
			meta.setDisplayName(null);
		} else meta.setDisplayName(utils.color(name));
		//enchants
		for (String s: enchants)
		{
			String ench_str = s.substring(0, s.indexOf(":"));;
			String ench_lvl_str = s.substring(s.indexOf(":")+1);
			
			if (!utils.isEnchantment(ench_str))
			{
				error.add(utils.color("&4+ enchants: &c"+ench_str+"&4 is not an enchantment"));
				continue;
			}
			
			if (!utils.isNumber(ench_lvl_str))
			{
				error.add(utils.color("&4+ enchants: &c"+ench_lvl_str+"&4 is not a number"));
				continue;
			}
			
			int ench_lvl = Integer.valueOf(ench_lvl_str);
			
			meta.addEnchant(Enchantment.getByName(ench_str), ench_lvl, true);
		}
		//flags
		for (String s: flags)
		{
			if (!utils.isFlag(s))
			{
				error.add(utils.color("&4+ flags: &c"+s+"&4 is not a flag"));
				continue;
			}
			
			meta.addItemFlags(ItemFlag.valueOf(s));
		}
		
		//lore
		List<String> itemFormat = SupperForge.format.getSItemFormat();
		List<String> toAdd = new ArrayList<>();
		for (String s: itemFormat)
		{
			if (s.contains("<type>"))
			{
				if (!item_type.equalsIgnoreCase(""))
				{
					if (!psUtils.isType(item_type))
					{
						error.add(utils.color("&4+ item type: &c"+item_type+"&4 is not an item type"));
					}
					else toAdd.add(psUtils.getLoreType(item_type));
				}
				
			}
			else if (s.contains("<stat>"))
			{
				for (String stat: stats)
				{
					String key = stat.substring(0, stat.indexOf(":"));;
					String value = stat.substring(stat.indexOf(":")+1);
					
					if (!psUtils.isStat(key))
					{
						error.add(utils.color("&4+ stats: &c"+key+"&4 is not a stat"));
						continue;
					}
					
					if (value.contains("~") && !icon)
					{
						String start_str = value.substring(0, value.indexOf("~"));
						String end_str = value.substring(value.indexOf("~")+1);
						
						if (!utils.isNumber(start_str))
						{
							error.add(utils.color("&4+ stats: &c"+start_str+"&4 is not a number"));
							continue;
						}
						if (!utils.isNumber(end_str))
						{
							error.add(utils.color("&4+ stats: &c"+end_str+"&4 is not a number"));
							continue;
						}
						double toSetValue = utils.getRandom(Double.parseDouble(start_str), Double.parseDouble(end_str), digit);
						toAdd.add(psUtils.getLoreStat(key, toSetValue+"", icon));
					}
					else if (icon)
					{
						toAdd.add(psUtils.getLoreStat(key, value.replace("~", rangeSymbol), icon));
					} 
					else
					{
						if (!utils.isNumber(value))
						{
							error.add(utils.color("&4+ stats: &c"+value+"&4 is not a number"));
							continue;
						}
						toAdd.add(psUtils.getLoreStat(key, Double.parseDouble(value)+"", icon));
					}
					
				}
			}
			else if (s.contains("<lore>"))
			{
				for (String str: lore)
				{
					toAdd.add(utils.color(str));
				}
				
			}
		}
		
		meta.setLore(toAdd);
		
		List<String> currentLore = new ArrayList<>();
		if (meta.hasLore()) currentLore = meta.getLore();
		if (error.size()>=2)
		{
			currentLore.addAll(error);
			meta.setLore(currentLore);
		}
		
		result.setItemMeta(meta);
		
		return result;
	}
	
	
	
}
