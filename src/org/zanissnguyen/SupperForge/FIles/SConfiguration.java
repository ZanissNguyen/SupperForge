package org.zanissnguyen.SupperForge.FIles;

import java.util.ArrayList;
import java.util.List;

import org.zanissnguyen.SupperForge.SupperForge;

public class SConfiguration extends SFile
{
	public SConfiguration(SupperForge plugin)
	{
		super(plugin, "config.yml");
	}
	
	public String get(String... str)
	{
		String toGet = "";
		for (String s: str)
		{
			if (s.equalsIgnoreCase(str[0]))toGet = toGet + s;
			else toGet = toGet + "." + s;
		}
		return this.utils.color(fileConfig.getString(toGet));
	}
	
	public List<String> getList(String... str)
	{
		List<String> result = new ArrayList<>();
		String toGet = "";
		for (String s: str)
		{
			if (s.equalsIgnoreCase(str[0]))toGet = toGet + s;
			else toGet = toGet + "." + s;
		}
		List<String> got = fileConfig.getStringList(toGet);
		for (String s: got)
		{
			result.add(utils.color(s));
		}
		
		return result;
	}
	
	
	public final boolean getIsBreak()
	{
		return fileConfig.getBoolean("config.item_break");
	}
	
	public final boolean getUnequipArmor()
	{
		return fileConfig.getBoolean("config.unequip_armor");
	}
	
	public final boolean getUnequipAccessory()
	{
		return fileConfig.getBoolean("config.unequip_accessory");
	}
	
	public final boolean getCatalogue()
	{
		return fileConfig.getBoolean("config.catalogue");
	}
	
	public final boolean getUpdateChecker()
	{
		return fileConfig.getBoolean("config.update_check");
	}
	
	public final boolean getCooldownMsg()
	{
		return fileConfig.getBoolean("config.combat.cooldown_msg");
	}
	
	public final boolean getActiveSound()
	{
		return fileConfig.getBoolean("config.combat.active_sound");
	}
	
	public final boolean getIsVanilla()
	{
		return fileConfig.getBoolean("config.vanilla_damage");
	}
	
	public final boolean getIsMultipleCrit()
	{
		return fileConfig.getBoolean("config.combat.multiple_critical");
	}
	
	public final int getDigit()
	{
		return fileConfig.getInt("config.digit");
	}
	
	public final int getHologramFPS()
	{
		return fileConfig.getInt("config.hologram_fps");
	}
	
	public final double getBrokenModifier()
	{
		return fileConfig.getDouble("config.broken_item_modifier");
	}
	
	public final double getHologramDuration()
	{
		return fileConfig.getDouble("config.hologram_duration");
	}
	
	public final double getBonusRange()
	{
		return fileConfig.getDouble("config.combat.bonus_range_damage");
	}
	
	public final double getBaseCritDamage()
	{
		return fileConfig.getDouble("config.combat.base_critical_damage");
	}
	
	public final double getBlockPower()
	{
		return fileConfig.getDouble("config.combat.block_power");
	}
	
	public final double getParryPower()
	{
		return fileConfig.getDouble("config.combat.parry_power");
	}
	
	public final double getAbsropPower()
	{
		return fileConfig.getDouble("config.combat.absorp_power");
	}
	
	public final double getMaxDodge()
	{
		return fileConfig.getDouble("config.combat.max_dodge");
	}
	
	public final double getMaxResistance()
	{
		return fileConfig.getDouble("config.combat.max_resistance");
	}
	
	public final double getVanillaArmorConvert()
	{
		return fileConfig.getDouble("config.combat.vanilla_armor_convert");
	}
	
	public final double getVanillaArmorToughnessConvert()
	{
		return fileConfig.getDouble("config.combat.vanilla_armor_toughness_convert");
	}
	
	public final double getVanillaDamageConvert()
	{
		return fileConfig.getDouble("config.combat.vanilla_damage_convert");
	}
	
	public final double getMobPhysicResistance()
	{
		return fileConfig.getDouble("config.combat.mob_physic_resistance");
	}
	
	public final double getMobMagicResistance()
	{
		return fileConfig.getDouble("config.combat.mob_magic_resistance");
	}
	
	public final double getOffhandModifier()
	{
		return fileConfig.getDouble("config.offhand_modifier");
	}
}
