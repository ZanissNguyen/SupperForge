package org.zanissnguyen.SupperForge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.zanissnguyen.SupperForge.API.PlayerStat;
import org.zanissnguyen.SupperForge.FIles.InventoryStorage;

public class SAttributeUtils 
{
	private SupperForge plugin;
	private Utils utils;
	public static Map<Player, PlayerStat> statStorage = new HashMap<>();
	
	public SAttributeUtils(SupperForge plugin, Utils utils)
	{
		this.plugin = plugin;
		this.utils = utils;
	}
	
	// STAT UTILS
	public boolean isStat(String stat)
	{
		return plugin.locale.getAllStat().contains(stat);
	}
	
	public boolean isType(String type)
	{
		return plugin.locale.getAllType().contains(type);
	}
	
	public double getStat(ItemStack item, String stat)
	{
		String percentSymbol = plugin.locale.get("locale", "symbol", "percent");
		if (isItemHasStat(item, stat)==-1) return 0;
		else
		{
			String format = SupperForge.format.getStatFormat();
			String lore = utils.getLore(item).get(isItemHasStat(item, stat));
			String display = getStatDisplay(stat);
			
			String over = format.substring(Math.max(format.lastIndexOf("<value>")+7, format.lastIndexOf("<stat>")+6));
			
			int statIndex = format.indexOf("<stat>");
			int valueIndex = format.indexOf("<value>");
			String value_str = "";
			
			if (statIndex<valueIndex)
			{
				int argumentDistance = valueIndex - (statIndex+6);
				String temp = lore.substring(statIndex+display.length()+argumentDistance, lore.length()-over.length());
				value_str = temp.substring(Math.max(temp.indexOf("+"), temp.indexOf("-")));
			}
			else
			{
				int argumentDistance = statIndex - (valueIndex+7);
				String temp = lore.substring(0, lore.indexOf(display)-argumentDistance);
				value_str = temp.substring(Math.max(temp.lastIndexOf("+"), temp.lastIndexOf("-")));
			}
			
			if (PlayerStat.percent().contains(stat))
			{
				return (Double.parseDouble(value_str.substring(0, value_str.length()-percentSymbol.length()))/100.0);
			}
			else return Double.parseDouble(value_str);
		}
	}
	
	public String getType(ItemStack item)
	{
		if (isItemHasType(item)==-1) return "";
		else
		{
			String lore = utils.getLore(item).get(isItemHasType(item));
			
			for (String s: plugin.locale.getAllType())
			{
				String display = getLoreType(s);
				
				if (lore.equalsIgnoreCase(display)) return s;
			}
		}
		return "";
	}
	
	public int isItemHasStat(ItemStack item, String stat)
	{
		List<String> lore = utils.getLore(item);
		
		return isLoreHasStat(lore, stat); 
	}
	
	public int isLoreHasStat(List<String> lore, String stat)
	{
		for (String s: lore)
		{
			String display = getStatDisplay(stat);
			if (!s.contains(display)) continue;
			
			String format = SupperForge.format.getStatFormat();
			String over = format.substring(Math.max(format.lastIndexOf("<value>")+7, format.lastIndexOf("<stat>")+6));
			
			int statIndex = format.indexOf("<stat>");
			int valueIndex = format.indexOf("<value>");
			String value_str = "";
			
			if (statIndex<valueIndex)
			{
				if (statIndex==s.indexOf(display))
				{
					int argumentDistance = valueIndex - (statIndex+6);
					String temp = s.substring(statIndex+display.length()+argumentDistance, s.length()-over.length());
					value_str = temp.substring(Math.max(temp.indexOf("+"), temp.indexOf("-")));
					
					if (value_str.length()+2>=temp.length()) return lore.indexOf(s);
					else continue;
				}
				else continue;
			}
			else
			{
				int argumentDistance = statIndex - (valueIndex+7);
				String temp = s.substring(0, s.indexOf(display)-argumentDistance);
				value_str = temp.substring(Math.max(temp.lastIndexOf("+"), temp.lastIndexOf("-")));
	
				if (utils.isNumber(value_str)) return lore.indexOf(s);
				else continue;
			}
		}
		
		return -1; // mean no stat
	}
	
	public String getStatDisplay(String stat)
	{
		return  plugin.locale.get("stat", stat);
	}
	
	public String getLoreStat(String stat, String value, boolean icon)
	{
		final String positive = utils.color("&a")+"+";
		final String negative = utils.color("&c");
		String format = SupperForge.format.getStatFormat();
		String percentSymbol = plugin.locale.get("locale", "symbol", "percent");
		
		String loreDisplay = getStatDisplay(stat);
		String prefix = "";
		String setValue = "";
		if (icon)
		{
			prefix = "&e";
			setValue = value;
		}
		else
		{
			double valueDb = utils.fixedDecimal(Double.parseDouble(value), plugin.config.getDigit());
			prefix = (valueDb>=0) ? positive : negative;
			setValue = valueDb+"";
		}
		
		if (PlayerStat.percent().contains(stat))
		{
			setValue+=percentSymbol;
		}
		String result = format.replace("<stat>", loreDisplay).replace("<value>", utils.color(prefix+setValue));
				
		return result;
	}
	
	public ItemStack setStat(ItemStack item, String stat, double value)
	{
		ItemStack result = item.clone();
		
		String loreStat = getLoreStat(stat, value+"", false);
		
		int index = isItemHasStat(item, stat);
		if (index==-1)
		{
			if (value!=0) result = utils.addLore(result, -1, loreStat);
		} else 
		{
			if (value!=0) result = utils.setLore(result, index, loreStat);
			else result = utils.removeLore(result, index);
		}
		
		return result;
	}
	
	// TYPE UTILS
	public int isItemHasType(ItemStack item)
	{
		List<String> lore = utils.getLore(item);
		
		return isLoreTypeSetted(lore);
	}
	
	public int isLoreTypeSetted(List<String> lore)
	{
		for (String s: lore)
		{
			String format = SupperForge.format.getTypeFormat().replace("<type>", "");
			
			if (s.startsWith(format)) 
			{
				for (String type: plugin.locale.getAllType())
				{
					String display = getLoreType(type);
					if (s.equalsIgnoreCase(display)) return lore.indexOf(s);
				}
			}
		}
		
		return -1; // mean no type setted
	}
	
	public String getTypeDisplay(String type)
	{
		return  plugin.locale.get("item-type", type);
	}
	
	public String getLoreType(String type)
	{
		String format = SupperForge.format.getTypeFormat();
		String loreDisplay = getTypeDisplay(type);
		
		return format.replace("<type>", loreDisplay);
	}
	
	public ItemStack setType(ItemStack item, String type)
	{
		ItemStack result = item.clone();
		
		String loreType = getLoreType(type);
		int index = isItemHasType(item);
		if (index==-1)
		{
			result = utils.addLore(result, -1, loreType);
		} else result = utils.setLore(result, index, loreType);
		
		return result;
	}
	
	public List<String> offHandTypes()
	{
		List<String> result = new ArrayList<>();
		result.add("offhand");
		result.add("shield");
		result.addAll(rangeTypes());
		return result;
	}
	
	public List<String> rangeTypes()
	{
		List<String> result = new ArrayList<>();
		result.add("bow");
		result.add("crossbow");
		return result;
	}
	
	public boolean isRangeTypes(String type)
	{
		return rangeTypes().contains(type);
	}
	
	public boolean isOffHandTypes(String type)
	{
		return offHandTypes().contains(type);
	}
	
	public List<String> armorTypes()
	{
		List<String> result = new ArrayList<>();
		result.add("helmet");
		result.add("chestplate");
		result.add("leggings");
		result.add("boots");
		return result;
	}
	
	public boolean isArmorTypes(String type)
	{
		return armorTypes().contains(type);
	}
	
	public List<String> accessoryTypes()
	{
		List<String> result = new ArrayList<>();
		result.add("ring");
		result.add("belt");
		result.add("necklace");
		result.add("gaunlet");
		result.add("artifact");
		return result;
	}
	
	public boolean isAccessoryTypes(String type)
	{
		return accessoryTypes().contains(type);
	}
	
	// TODO Durability Method
	private String durability_display() {return plugin.locale.get("durability", "lore-display");}
	private String durability_format() {return SupperForge.format.getDurabilityFormat();}
	
	public ItemStack setMaxDurability(ItemStack item, int value)
	{
		ItemStack result = item.clone();
		
		String toAdd = durability_format().replace("<display>", durability_display()).replace("<value>", "["+value+"/"+value+"]");
		int index = isItemSetDurability(item);
		if (index==-1)
		{
			if (value!=0) result = utils.addLore(result, -1, toAdd);
		} else 
		{
			if (value!=0) result = utils.setLore(result, index, toAdd);
			else result = utils.removeLore(result, index);
		}
		
		return result;
	}
	
	public ItemStack setCurrentDurability(ItemStack item, int value)
	{
		ItemStack result = item.clone();
		
		int index = isItemSetDurability(item);
		if (index==-1)
		{
			if (value!=0) return setMaxDurability(item, value);
		} else 
		{
			int maxDurability = getMaxDurability(item);
			
			value = Math.min(value, maxDurability);
			value = Math.max(0, value);
			
			String toAdd = durability_format().replace("<display>", durability_display()).replace("<value>", "["+value+"/"+maxDurability+"]");
			
			if (value>=0) result = utils.setLore(result, index, toAdd);
		}
		
		return result;
	}
	
	public int isItemSetDurability(ItemStack item)
	{
		String lore = durability_format().replace("<display>", durability_display());
		String toCheck = lore.substring(0, lore.indexOf("<value>"));
		
		if (item == null) return -1;
		if (item.getItemMeta() == null) return -1;
		
		List<String> itemLore = utils.getLore(item);
		
		if (itemLore.isEmpty()) return -1;
		
		for (String s: itemLore)
		{
			if (s.startsWith(toCheck)) return itemLore.indexOf(s);
		}
		
		return -1;
	}
	
	public int getCurrentDurability(ItemStack item)
	{
		int line = isItemSetDurability(item);
		
		if (line == -1) return line;
		
		String lore = utils.getLore(item).get(line);
		String format = durability_format().replace("<display>", durability_display());
		int over = format.length()-7-format.indexOf("<value>");
		String value = lore.substring(format.indexOf("<value>"), lore.length()-over);
		
		String rs_str = value.substring(value.indexOf("[")+1, value.indexOf("/"));
		
		int result = Integer.parseInt(rs_str);
		
		return result;
	}
	
	public int getMaxDurability(ItemStack item)
	{
		int line = isItemSetDurability(item);
		
		if (line == -1) return line;
		
		String lore = utils.getLore(item).get(line);
		String format = durability_format().replace("<display>", durability_display());
		int over = format.length()-7-format.indexOf("<value>");
		String value = lore.substring(format.indexOf("<value>"), lore.length()-over);
		
		String rs_str = value.substring(value.indexOf("/")+1, value.indexOf("]"));
		
		int result = Integer.parseInt(rs_str);
		
		return result;
	}
	
	public boolean isBroke(ItemStack item, Player p, String slot, boolean msg)
	{
		int line = isItemSetDurability(item);
		
		String replaceSlot = plugin.locale.get("item-type", slot);
		boolean isBroke = plugin.config.getIsBreak();
		String message = (isBroke ? plugin.locale.get("durability.break") : plugin.locale.get("durability.run-out")).replace("<slot>", replaceSlot);
		
		if (line == -1) return false;
		int currentDurability = getCurrentDurability(item);
		
		if (currentDurability == 0)
		{
			if (msg) p.sendMessage(message);
			if (isBroke) item.setAmount(item.getAmount()-1);
			return true;
		}
		else return false;
	}
	
	public void durabilityProcess(ItemStack item, Player p, String slot, int intSlot)
	{
		if (item==null || item.getType()==Material.AIR) return;
		InventoryStorage storage = SupperForge.getInstance().storage;
		PlayerInventory inv = p.getInventory();
		ItemStack result = item.clone();
		int current = getCurrentDurability(result);
		
		if (current == -1) return;
		if (current==0) return;
		
		result = setCurrentDurability(result, current-1);
		
		switch (slot)
		{
		case "ring":
			storage.setRingSlot(p, intSlot, result);
			break;
		case "artifact":
			storage.setArtifactSlot(p, intSlot, result);
			break;
		case "belt":
			storage.setBeltSlot(p, result);
			break;
		case "gauntlet":
			storage.setGauntletSlot(p, result);
			break;
		case "necklace":
			storage.setNecklaceSlot(p, result);
			break;
		case "helmet":
			inv.setHelmet(result);
			break;
		case "chesplate":
			inv.setChestplate(result);
			break;
		case "leggings":
			inv.setLeggings(result);
			break;
		case "boots":
			inv.setBoots(result);
			break;
		case "mainhand":
			inv.setItemInMainHand(result);
			break;
		case "offhand":
			inv.setItemInOffHand(result);
			break;
		default:
			break;
		}
		
		if (isBroke(result,p,slot,false))
		{
			switch (slot)
			{
			case "ring":
				PlayerStat.updateRingSlot(result, p, intSlot, true);
				break;
			case "artifact":
				PlayerStat.updateArtifactSlot(result, p, intSlot, true);
				break;
			case "belt":
				PlayerStat.updateBeltSlot(result, p, true);
				break;
			case "gauntlet":
				PlayerStat.updateGauntletSlot(result, p, true);
				break;
			case "necklace":
				PlayerStat.updateNecklaceSlot(result, p, true);
				break;
			case "helmet":
				PlayerStat.updateHelmetSlot(result, p, true);
				break;
			case "chesplate":
				PlayerStat.updateChestplateSlot(result, p, true);
				break;
			case "leggings":
				PlayerStat.updateLeggingsSlot(result, p, true);
				break;
			case "boots":
				PlayerStat.updateBootsSlot(result, p, true);
				break;
			case "mainhand":
				PlayerStat.updateMainhandSlot(result, p, true);
				break;
			case "offhand":
				PlayerStat.updateOffhandSlot(result, p, true);
				break;
			default:
				break;
			}
		}
	}
}
