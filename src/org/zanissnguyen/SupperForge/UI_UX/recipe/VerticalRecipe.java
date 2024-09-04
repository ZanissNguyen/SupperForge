package org.zanissnguyen.SupperForge.UI_UX.recipe;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.zanissnguyen.SupperForge.SupperForge;
import org.zanissnguyen.SupperForge.Utils;
import org.zanissnguyen.SupperForge.API.SRecipe;
import org.zanissnguyen.SupperForge.API.SUserInterface_Recipe;


public class VerticalRecipe extends SUserInterface_Recipe
{
	public VerticalRecipe(SupperForge plugin, Utils utils)
	{
		super(SupperForge.getInstance(), utils, 6);	
		
		materialsSlot.add(29);
		materialsSlot.add(30);
		materialsSlot.add(31);
		materialsSlot.add(32);
		materialsSlot.add(33);
		
		materialsSlot.add(38);
		materialsSlot.add(39);
		materialsSlot.add(40);
		materialsSlot.add(41);
		materialsSlot.add(42);
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
		ItemStack progress = utils.createItem(Material.LIME_STAINED_GLASS_PANE, 1, 101, false, " ");
		
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
		inv.setItem(10, fill());
		inv.setItem(11, info(p, recipe));
		inv.setItem(12, fill());
		inv.setItem(13, recipe.item);
		inv.setItem(14, fill());
		inv.setItem(15, craft());
		inv.setItem(16, fill());
		inv.setItem(17, laner);
		
		inv.setItem(18, laner);
		inv.setItem(19, fill());
		inv.setItem(20, fill());
		inv.setItem(21, fill());
		inv.setItem(22, progress);
		inv.setItem(23, fill());
		inv.setItem(24, fill());
		inv.setItem(25, fill());
		inv.setItem(26, laner);
		
		inv.setItem(27, laner);
		inv.setItem(28, fill());
		inv.setItem(29, fill_slot);
		inv.setItem(30, fill_slot);
		inv.setItem(31, fill_slot);
		inv.setItem(32, fill_slot);
		inv.setItem(33, fill_slot);
		inv.setItem(34, fill());
		inv.setItem(35, laner);
		
		inv.setItem(36, laner);
		inv.setItem(37, fill());
		inv.setItem(38, fill_slot);
		inv.setItem(39, fill_slot);
		inv.setItem(40, fill_slot);
		inv.setItem(41, fill_slot);
		inv.setItem(42, fill_slot);
		inv.setItem(43, fill());
		inv.setItem(44, laner);
		
		inv.setItem(45, laner);
		inv.setItem(46, laner);
		inv.setItem(47, laner);
		inv.setItem(48, laner);
		inv.setItem(49, back());
		inv.setItem(50, laner);
		inv.setItem(51, laner);
		inv.setItem(52, laner);
		inv.setItem(53, laner);
		
		setUpMaterial(p, recipe);
	}

}
