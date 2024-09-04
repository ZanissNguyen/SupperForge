package org.zanissnguyen.SupperForge.FIles;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.zanissnguyen.SupperForge.SupperForge;

public class InventoryStorage extends SFile 
{
	public InventoryStorage(SupperForge plugin) {
		super(plugin, "storage.yml");
		// TODO Auto-generated constructor stub
	}

	public ItemStack getRingSlot(Player p, int slot)
	{
		return SupperForge.itmStorage.getSItem("ring_"+slot, true, p).convertToItemStack(false, true);
	}
	
	public void setRingSlot(Player p, int slot, ItemStack item)
	{
		SupperForge.itmStorage.saveItem("ring_"+slot, item, true, p);
	}
	
	public ItemStack getBeltSlot(Player p)
	{
		return SupperForge.itmStorage.getSItem("belt", true, p).convertToItemStack(false, true);
	}
	
	public void setBeltSlot(Player p, ItemStack item)
	{
		SupperForge.itmStorage.saveItem("belt", item, true, p);
	}
	
	public ItemStack getGauntletSlot(Player p)
	{
		return SupperForge.itmStorage.getSItem("gauntlet", true, p).convertToItemStack(false, true);
	}
	
	public void setGauntletSlot(Player p, ItemStack item)
	{
		SupperForge.itmStorage.saveItem("gauntlet", item, true, p);
	}
	
	public ItemStack getNecklaceSlot(Player p)
	{
		return SupperForge.itmStorage.getSItem("necklace", true, p).convertToItemStack(false, true);
	}
	
	public void setNecklaceSlot(Player p, ItemStack item)
	{
		SupperForge.itmStorage.saveItem("necklace", item, true, p);
	}
	
	public ItemStack getArtifactSlot(Player p, int slot)
	{
		return SupperForge.itmStorage.getSItem("artifact_"+slot, true, p).convertToItemStack(false, true);
	}
	
	public void setArtifactSlot(Player p, int slot, ItemStack item)
	{
		SupperForge.itmStorage.saveItem("artifact_"+slot, item, true, p);
	}

}
