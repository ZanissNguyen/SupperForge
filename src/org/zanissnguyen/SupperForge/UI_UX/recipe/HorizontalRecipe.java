package org.zanissnguyen.SupperForge.UI_UX.recipe;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.zanissnguyen.SupperForge.SupperForge;
import org.zanissnguyen.SupperForge.Utils;
import org.zanissnguyen.SupperForge.API.SRecipe;
import org.zanissnguyen.SupperForge.API.SUserInterface_Recipe;


public class HorizontalRecipe extends SUserInterface_Recipe
{
	public HorizontalRecipe(SupperForge plugin, Utils utils)
	{
		super(SupperForge.getInstance(), utils, 5);	
		
		materialsSlot.add(10);
		materialsSlot.add(11);
		materialsSlot.add(12);
		
		materialsSlot.add(19);
		materialsSlot.add(20);
		materialsSlot.add(21);
		
		materialsSlot.add(28);
		materialsSlot.add(29);
		materialsSlot.add(30);
		
		materialsSlot.add(22);
	}

	public void open(Player p, String id)
	{
		SRecipe recipe = SupperForge.repStorage.getSRecipe(id);
		setUp(p, recipe);
		p.openInventory(inv);
	}
	
	@Override
	public void setUp(Player p, SRecipe recipe) 
	{
		inv = Bukkit.createInventory(null, rows*9, name_prefix+utils.color(" &e(#"+recipe.id+")"));
		
		ItemStack laner = utils.createItem(Material.YELLOW_STAINED_GLASS_PANE, 1, 0, false, " ");
		ItemStack fill_slot = utils.createItem(Material.BLACK_STAINED_GLASS_PANE, 1, 0, false, " ");
		ItemStack progress = utils.createItem(Material.LIME_STAINED_GLASS_PANE, 1, 102, false, " ");
		
		inv.setItem(0, laner);
		inv.setItem(1, laner);
		inv.setItem(2, laner);
		inv.setItem(3, laner);
		inv.setItem(4, laner);
		inv.setItem(5, laner);
		inv.setItem(6, laner);
		inv.setItem(7, laner);
		inv.setItem(8, laner);
		
		inv.setItem(9, laner);
		inv.setItem(10, fill_slot);
		inv.setItem(11, fill_slot);
		inv.setItem(12, fill_slot);
		inv.setItem(13, fill());
		inv.setItem(14, fill());
		inv.setItem(15, info(p, recipe));
		inv.setItem(16, fill());
		inv.setItem(17, laner);
		
		inv.setItem(18, laner);
		inv.setItem(19, fill_slot);
		inv.setItem(20, fill_slot);
		inv.setItem(21, fill_slot);
		inv.setItem(22, fill_slot);
		inv.setItem(23, progress);
		inv.setItem(24, recipe.item);
		inv.setItem(25, craft());
		inv.setItem(26, laner);
		
		inv.setItem(27, laner);
		inv.setItem(28, fill_slot);
		inv.setItem(29, fill_slot);
		inv.setItem(30, fill_slot);
		inv.setItem(31, fill());
		inv.setItem(32, fill());
		inv.setItem(33, back());
		inv.setItem(34, fill());
		inv.setItem(35, laner);
		
		inv.setItem(36, laner);
		inv.setItem(37, laner);
		inv.setItem(38, laner);
		inv.setItem(39, laner);
		inv.setItem(40, laner);
		inv.setItem(41, laner);
		inv.setItem(42, laner);
		inv.setItem(43, laner);
		inv.setItem(44, laner);
		
		setUpMaterial(p, recipe);
	}

}
