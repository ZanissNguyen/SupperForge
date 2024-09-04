package org.zanissnguyen.SupperForge.API;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.zanissnguyen.SupperForge.SAttributeUtils;
import org.zanissnguyen.SupperForge.SupperForge;
import org.zanissnguyen.SupperForge.FIles.InventoryStorage;

public class PlayerStat 
{
	final private static double default_speed = 0.2;
	// attack
	public double physic_damage;
	public double physic_penetration;
	public double accurate;
	public double crit_chance;
	public double crit_damage;
	public double attack_speed;
	  
	public double magic_damage;
	public double magic_penetration;
	public double enhance_chance;
	  
	public double physic_defense;
	public double magic_defense;
	public double block_chance;
	public double parry_chance;
	public double dodge_chance;
	public double absorp_chance;
	public double resistance;
	  
	public double speed;
	public double health;
	public double heal;
	public double xp_boost;
	
	public PlayerStat()
	{
		this.crit_damage = SupperForge.getInstance().config.getBaseCritDamage();
		this.health = 20;
		this.attack_speed = 1;
	}
	
	public PlayerStat(PlayerStat copy)
	{
		this.physic_damage = copy.physic_damage;
		this.physic_penetration = copy.physic_penetration;
		this.accurate = copy.accurate;
		this.crit_chance = copy.crit_chance;
		this.crit_damage = copy.crit_damage;
		this.attack_speed = copy.attack_speed;
		  
		this.magic_damage = copy.magic_damage;
		this.magic_penetration = copy.magic_penetration;
		this.enhance_chance = copy.enhance_chance;
		  
		this.physic_defense = copy.physic_defense;
		this.magic_defense = copy.magic_defense;
		this.block_chance = copy.block_chance;
		this.parry_chance = copy.parry_chance;
		this.dodge_chance = copy.dodge_chance;
		this.absorp_chance = copy.absorp_chance;
		this.resistance = copy.resistance;
		  
		this.speed = copy.speed;
		this.health = copy.health;
		this.heal = copy.heal;
		this.xp_boost = copy.xp_boost;
	}
	
	public static List<String> percent()
	{
		List<String> result = new ArrayList<>();
		result.add("crit_chance");
		result.add("crit_damage");
		result.add("accurate");
		result.add("enhance_chance");
		result.add("block_chance");
		result.add("parry_chance");
		result.add("absorp_chance");
		result.add("dodge_chance");
		result.add("speed");
		result.add("resistance");
		result.add("heal");
		result.add("xp_boost");
		return result;
	}
	
	public String toString()
	{
		String result = "";
		
		for (String s: SupperForge.getInstance().locale.getAllStat())
		{
			result += SupperForge.psUtils.getStatDisplay(s.toString()) + ":" + get(s, true) +"\n";
		}
		
		return result;
	}
	
	// other
	
	// method
	public static double getScale(ItemStack item, Player p, String slot, boolean init)
	{
		SAttributeUtils attrUtils = SupperForge.psUtils;
		if (item==null || item.getType()==Material.AIR) return 1;
		
		boolean unequipArmor = SupperForge.getInstance().config.getUnequipArmor();
		boolean unequipAccessory = SupperForge.getInstance().config.getUnequipAccessory();
		double broke_modi = SupperForge.getInstance().config.getBrokenModifier();
		double off_modi = SupperForge.getInstance().config.getOffhandModifier();
		
		String type = attrUtils.getType(item);
		Material mat = item.getType();
		double scale = 1;
		
		switch (slot)
		{
		case "mainhand":
			if (!unequipArmor && (attrUtils.isArmorTypes(type) || SupperForge.utils.isArmor(mat))
					|| (!unequipAccessory && attrUtils.isAccessoryTypes(type))) scale *= 0;
			
			scale *= SupperForge.psUtils.isBroke(item, p, slot, init) ? broke_modi : 1.0;
			break;
		case "offhand":
			if (!unequipArmor && (attrUtils.isArmorTypes(type) || SupperForge.utils.isArmor(mat))
					|| (!unequipAccessory && attrUtils.isAccessoryTypes(type))) scale *= 0;
			if (!attrUtils.isOffHandTypes(type)) scale *= off_modi;
			
			scale *= SupperForge.psUtils.isBroke(item, p, slot, init) ? broke_modi : 1.0;
			break;
		case "ring":
		case "artifact":
		case "belt":
		case "gauntlet":
		case "necklace":
		case "helmet":
		case "chesplate":
		case "leggings":
		case "boots":
		default:
			scale *= SupperForge.psUtils.isBroke(item, p, slot, init) ? broke_modi : 1.0;
			break;
		}
		
		return scale;
	}
	
	public static void removeStat(ItemStack item, PlayerStat stats, double scale)
	{
		if (item==null || item.getType()==Material.AIR) return;
		for (String stat: SupperForge.getInstance().locale.getAllStat())
		{
			double value = SupperForge.psUtils.getStat(item, stat) * scale;
			
			if (stat.equalsIgnoreCase("physic_damage"))
			{
				stats.physic_damage-=value;
			}
			else if (stat.equalsIgnoreCase("physic_defense"))
			{
				stats.physic_defense-=value;
			}
			else if (stat.equalsIgnoreCase("physic_penetration"))
			{
				stats.physic_penetration-=value;
			}
			else if (stat.equalsIgnoreCase("accurate"))
			{
				stats.accurate-=value;
			}
			else if (stat.equalsIgnoreCase("crit_chance"))
			{
				stats.crit_chance-=value;
			}
			else if (stat.equalsIgnoreCase("crit_damage"))
			{
				stats.crit_damage-=value;
			}
			else if (stat.equalsIgnoreCase("magic_damage"))
			{
				stats.magic_damage-=value;
			}
			else if (stat.equalsIgnoreCase("magic_defense"))
			{
				stats.magic_defense-=value;
			}
			else if (stat.equalsIgnoreCase("enhance_chance"))
			{
				stats.enhance_chance-=value;
			}
			else if (stat.equalsIgnoreCase("magic_penetration"))
			{
				stats.magic_penetration-=value;
			}
			else if (stat.equalsIgnoreCase("block_chance"))
			{
				stats.block_chance-=value;
			}
			else if (stat.equalsIgnoreCase("parry_chance"))
			{
				stats.parry_chance-=value;
			}
			else if (stat.equalsIgnoreCase("dodge_chance"))
			{
				stats.dodge_chance-=value;
			}
			else if (stat.equalsIgnoreCase("absorp_chance"))
			{
				stats.absorp_chance-=value;
			}
			else if (stat.equalsIgnoreCase("resistance"))
			{
				stats.resistance-=value;
			}
			else if (stat.equalsIgnoreCase("speed"))
			{
				stats.speed-=value;
			}
			else if (stat.equalsIgnoreCase("health"))
			{
				stats.health-=value;
			}
			else if (stat.equalsIgnoreCase("heal"))
			{
				stats.heal-=value;
			}
			else if (stat.equalsIgnoreCase("xp_boost"))
			{
				stats.xp_boost-=value;
			}
			else if (stat.equalsIgnoreCase("attack_speed"))
			{
				stats.attack_speed-=value;
			}
		}
	}
	
	public static void increaseStat(ItemStack item, PlayerStat stats, double scale)
	{
		if (item==null || item.getType()==Material.AIR) return;
		for (String stat: SupperForge.getInstance().locale.getAllStat())
		{
			double value = SupperForge.psUtils.getStat(item, stat)*scale;
			
			if (stat.equalsIgnoreCase("physic_damage"))
			{
				stats.physic_damage+=value;
			}
			else if (stat.equalsIgnoreCase("physic_defense"))
			{
				stats.physic_defense+=value;
			}
			else if (stat.equalsIgnoreCase("physic_penetration"))
			{
				stats.physic_penetration+=value;
			}
			else if (stat.equalsIgnoreCase("accurate"))
			{
				stats.accurate+=value;
			}
			else if (stat.equalsIgnoreCase("crit_chance"))
			{
				stats.crit_chance+=value;
			}
			else if (stat.equalsIgnoreCase("crit_damage"))
			{
				stats.crit_damage+=value;
			}
			else if (stat.equalsIgnoreCase("magic_damage"))
			{
				stats.magic_damage+=value;
			}
			else if (stat.equalsIgnoreCase("magic_defense"))
			{
				stats.magic_defense+=value;
			}
			else if (stat.equalsIgnoreCase("enhance_chance"))
			{
				stats.enhance_chance+=value;
			}
			else if (stat.equalsIgnoreCase("magic_penetration"))
			{
				stats.magic_penetration+=value;
			}
			else if (stat.equalsIgnoreCase("block_chance"))
			{
				stats.block_chance+=value;
			}
			else if (stat.equalsIgnoreCase("parry_chance"))
			{
				stats.parry_chance+=value;
			}
			else if (stat.equalsIgnoreCase("dodge_chance"))
			{
				stats.dodge_chance+=value;
			}
			else if (stat.equalsIgnoreCase("absorp_chance"))
			{
				stats.absorp_chance+=value;
			}
			else if (stat.equalsIgnoreCase("resistance"))
			{
				stats.resistance+=value;
			}
			else if (stat.equalsIgnoreCase("speed"))
			{
				stats.speed+=value;
			}
			else if (stat.equalsIgnoreCase("health"))
			{
				stats.health+=value;
			}
			else if (stat.equalsIgnoreCase("heal"))
			{
				stats.heal+=value;
			}
			else if (stat.equalsIgnoreCase("xp_boost"))
			{
				stats.xp_boost+=value;
			}
			else if (stat.equalsIgnoreCase("attack_speed"))
			{
				stats.attack_speed+=value;
			}
		}
	}
	
	private static void updateSlot(PlayerStat stats, Player p, String slot, int intSlot, boolean initialize, ItemStack in_item, boolean isbreak)
	{
		InventoryStorage storage = SupperForge.getInstance().storage;
		PlayerInventory inv = p.getInventory();
		
		if (stats == null) stats = new PlayerStat();
		
		ItemStack oldItem = null;
		ItemStack newItem = initialize ? null : in_item;
		double oldScale = 1;
		double newScale = 1;
		
		// get Old Item
		switch (slot)
		{
		case "ring":
			oldItem = storage.getRingSlot(p, intSlot);
			break;
		case "artifact":
			oldItem = storage.getArtifactSlot(p, intSlot);
			break;
		case "belt":
			oldItem = storage.getBeltSlot(p);
			break;
		case "gauntlet":
			oldItem = storage.getGauntletSlot(p);
			break;
		case "necklace":
			oldItem = storage.getNecklaceSlot(p);
			break;
		case "helmet":
			oldItem = inv.getHelmet();
			break;
		case "chesplate":
			oldItem = inv.getChestplate();
			break;
		case "leggings":
			oldItem = inv.getLeggings();
			break;
		case "boots":
			oldItem = inv.getBoots();
			break;
		case "mainhand":
			oldItem = inv.getItemInMainHand();
			break;
		case "offhand":
			oldItem = inv.getItemInOffHand();
			break;
		default:
			break;
		}
		
		// get scale
		if (!isbreak) oldScale = getScale(oldItem, p, slot, false);
		if (!initialize) newScale = getScale(newItem, p, slot, true);
		
		if (initialize)
		{
			if (oldItem!=null && oldItem.getType()!=Material.AIR && oldScale !=0) increaseStat(oldItem, stats, oldScale);
		}
		else
		{
			// remove old stat in slot 
			if (oldItem!=null && oldItem.getType()!=Material.AIR && oldScale != 0) removeStat(oldItem, stats, oldScale);
			
			// update new Item
			if (newItem!=null && newItem.getType()!=Material.AIR && newScale != 0) increaseStat(newItem, stats, newScale);
			
			switch (slot)
			{
			case "ring":
				storage.setRingSlot(p, intSlot, newItem);
				break;
			case "artifact":
				storage.setArtifactSlot(p, intSlot, newItem);
				break;
			case "belt":
				storage.setBeltSlot(p, newItem);
				break;
			case "gauntlet":
				storage.setGauntletSlot(p, newItem);
				break;
			case "necklace":
				storage.setNecklaceSlot(p, newItem);
				break;
			case "helmet":
				inv.setHelmet(newItem);
				break;
			case "chesplate":
				inv.setChestplate(newItem);
				break;
			case "leggings":
				inv.setLeggings(newItem);
				break;
			case "boots":
				inv.setBoots(newItem);
				break;
			case "mainhand":
				inv.setItemInMainHand(newItem);
				break;
			case "offhand":
				inv.setItemInOffHand(newItem);
				break;
			default:
				break;
			}
		}
		
		pushStatIntoStorage(p, stats);
	}
	
	@SuppressWarnings("deprecation")
	private static void pushStatIntoStorage(Player p, PlayerStat stats)
	{
		p.setMaxHealth(stats.health);
		p.setWalkSpeed((float)(default_speed + default_speed*stats.speed));
		
		SAttributeUtils.statStorage.put(p, stats);
	}
	
	public static void updateRingSlot(ItemStack newItem, Player p, int i, boolean isBreak)
	{
		PlayerStat current = SAttributeUtils.statStorage.get(p);
		
		updateSlot(current, p, "ring", i, false, newItem, isBreak);
	}
	
	public static void updateArtifactSlot(ItemStack newItem, Player p, int i, boolean isBreak)
	{	
		PlayerStat current = SAttributeUtils.statStorage.get(p);
		
		updateSlot(current, p, "artifact", i, false, newItem, isBreak);
	}
	
	public static void updateBeltSlot(ItemStack newItem, Player p, boolean isBreak)
	{
		PlayerStat current = SAttributeUtils.statStorage.get(p);
		
		updateSlot(current, p, "belt", -1, false, newItem, isBreak);
	}
	
	public static void updateGauntletSlot(ItemStack newItem, Player p, boolean isBreak)
	{
		PlayerStat current = SAttributeUtils.statStorage.get(p);
		
		updateSlot(current, p, "gauntlet", -1, false, newItem, isBreak);
	}
	
	public static void updateNecklaceSlot(ItemStack newItem, Player p, boolean isBreak)
	{
		PlayerStat current = SAttributeUtils.statStorage.get(p);
		
		updateSlot(current, p, "necklace", -1, false, newItem, isBreak);
	}
	
	public static void updateHelmetSlot(ItemStack newItem, Player p, boolean isBreak)
	{
		PlayerStat current = SAttributeUtils.statStorage.get(p);
		
		updateSlot(current, p, "helmet", -1, false, newItem, isBreak);
	}
	
	public static void updateChestplateSlot(ItemStack newItem, Player p, boolean isBreak)
	{
		PlayerStat current = SAttributeUtils.statStorage.get(p);
		
		updateSlot(current, p, "chestplate", -1, false, newItem, isBreak);
	}
	
	public static void updateLeggingsSlot(ItemStack newItem, Player p, boolean isBreak)
	{
		PlayerStat current = SAttributeUtils.statStorage.get(p);
		
		updateSlot(current, p, "leggings", -1, false, newItem, isBreak);
	}
	
	public static void updateBootsSlot(ItemStack newItem, Player p, boolean isBreak)
	{
		PlayerStat current = SAttributeUtils.statStorage.get(p);
		
		updateSlot(current, p, "boots", -1, false, newItem, isBreak);
	}
	
	public static void updateOffhandSlot(ItemStack newItem, Player p, boolean isBreak)
	{
		PlayerStat current = SAttributeUtils.statStorage.get(p);
		
		updateSlot(current, p, "offhand", -1, false, newItem, isBreak);
	}
	
	public static void updateMainhandSlot(ItemStack newItem, Player p, boolean isBreak)
	{
		PlayerStat current = SAttributeUtils.statStorage.get(p);
		
		updateSlot(current, p, "mainhand", -1, false, newItem, isBreak);
	}
	
	
	public static void updateStat(Player p)
	{
		PlayerStat stats = SAttributeUtils.statStorage.get(p);
		stats = new PlayerStat();
		for (int i = 1; i<=5; i++) 
			updateSlot(stats, p, "ring", i, true, null, false);
		for (int i = 1; i<=2; i++) 
			updateSlot(stats, p, "artifact", i, true, null, false);
		updateSlot(stats, p, "belt", -1, true, null, false);
		updateSlot(stats, p, "gauntlet", -1, true, null, false);
		updateSlot(stats, p, "necklace", -1, true, null, false);
		updateSlot(stats, p, "helmet", -1, true, null, false);
		updateSlot(stats, p, "chestplate", -1, true, null, false);
		updateSlot(stats, p, "leggings", -1, true, null, false);
		updateSlot(stats, p, "boots", -1, true, null, false);
		updateSlot(stats, p, "offhand", -1, true, null, false);
		updateSlot(stats, p, "mainhand", -1, true, null, false);
		
		
	}
	
	public double get(String stat, boolean show)
	{
		double temp = show ? 100.0 : 1.0;
		int digit = SupperForge.getInstance().config.getDigit();
		
		if (stat.equalsIgnoreCase("physic_damage"))
		{
			return SupperForge.utils.fixedDecimal(this.physic_damage, digit);
		}
		else if (stat.equalsIgnoreCase("physic_defense"))
		{
			return SupperForge.utils.fixedDecimal(this.physic_defense, digit);
		}
		else if (stat.equalsIgnoreCase("physic_penetration"))
		{
			return SupperForge.utils.fixedDecimal(this.physic_penetration, digit);
		}
		else if (stat.equalsIgnoreCase("accurate"))
		{
			return SupperForge.utils.fixedDecimal(this.accurate*temp, digit);
		}
		else if (stat.equalsIgnoreCase("crit_chance"))
		{
			return SupperForge.utils.fixedDecimal(this.crit_chance*temp, digit);
		}
		else if (stat.equalsIgnoreCase("crit_damage"))
		{
			return SupperForge.utils.fixedDecimal(this.crit_damage*temp, digit);
		}
		else if (stat.equalsIgnoreCase("magic_damage"))
		{
			return SupperForge.utils.fixedDecimal(this.magic_damage, digit);
		}
		else if (stat.equalsIgnoreCase("magic_defense"))
		{
			return SupperForge.utils.fixedDecimal(this.magic_defense, digit);
		}
		else if (stat.equalsIgnoreCase("enhance_chance"))
		{
			return SupperForge.utils.fixedDecimal(this.enhance_chance*temp, digit);
		}
		else if (stat.equalsIgnoreCase("magic_penetration"))
		{
			return SupperForge.utils.fixedDecimal(this.magic_penetration, digit);
		}
		else if (stat.equalsIgnoreCase("block_chance"))
		{
			return SupperForge.utils.fixedDecimal(this.block_chance*temp, digit);
		}
		else if (stat.equalsIgnoreCase("parry_chance"))
		{
			return SupperForge.utils.fixedDecimal(this.parry_chance*temp, digit);
		}
		else if (stat.equalsIgnoreCase("dodge_chance"))
		{
			return SupperForge.utils.fixedDecimal(this.dodge_chance*temp, digit);
		}
		else if (stat.equalsIgnoreCase("absorp_chance"))
		{
			return SupperForge.utils.fixedDecimal(this.absorp_chance*temp, digit);
		}
		else if (stat.equalsIgnoreCase("resistance"))
		{
			return SupperForge.utils.fixedDecimal(this.resistance*temp, digit);
		}
		else if (stat.equalsIgnoreCase("speed"))
		{
			return SupperForge.utils.fixedDecimal(this.speed*temp, digit);
		}
		else if (stat.equalsIgnoreCase("health"))
		{
			return SupperForge.utils.fixedDecimal(this.health, digit);
		}
		else if (stat.equalsIgnoreCase("heal"))
		{
			return SupperForge.utils.fixedDecimal(this.heal*temp, digit);
		}
		else if (stat.equalsIgnoreCase("xp_boost"))
		{
			return SupperForge.utils.fixedDecimal(this.xp_boost*temp, digit);
		}
		else if (stat.equalsIgnoreCase("attack_speed"))
		{
			return SupperForge.utils.fixedDecimal(this.attack_speed, digit);
		}
		return 0;
	}
}
