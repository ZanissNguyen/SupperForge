package org.zanissnguyen.SupperForge.API;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface SUserInterface_List 
{
	abstract void open(Player p, String search, int page);
	abstract List<ItemStack> loadList(Player p, String search, int page);
}
