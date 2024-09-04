package org.zanissnguyen.SupperForge.UI_UX.recipe;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.zanissnguyen.SupperForge.SupperForge;
import org.zanissnguyen.SupperForge.Utils;
import org.zanissnguyen.SupperForge.API.SRecipe;
import org.zanissnguyen.SupperForge.API.SUserInterface_Recipe;


public class CornerRecipe extends SUserInterface_Recipe
{
	public CornerRecipe(SupperForge plugin, Utils utils)
	{
		super(SupperForge.getInstance(), utils, 5);	
		
		materialsSlot.add(4);
		materialsSlot.add(5);
		materialsSlot.add(6);
		materialsSlot.add(15);
		materialsSlot.add(24);
		
		materialsSlot.add(20);
		materialsSlot.add(29);
		materialsSlot.add(38);
		materialsSlot.add(40);
		materialsSlot.add(41);
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
		ItemStack progress_right = utils.createItem(Material.LIME_STAINED_GLASS_PANE, 1, 102, false, " ");
		ItemStack progress_left = utils.createItem(Material.LIME_STAINED_GLASS_PANE, 1, 103, false, " ");
		
		inv.setItem(0, laner);
		inv.setItem(1, fill());
		inv.setItem(2, fill());
		inv.setItem(3, fill());
		inv.setItem(4, fill_slot);
		inv.setItem(5, fill_slot);
		inv.setItem(6, fill_slot);
		inv.setItem(7, fill());
		inv.setItem(8, laner);
		
		inv.setItem(9, laner);
		inv.setItem(10, fill());
		inv.setItem(11, fill());
		inv.setItem(12, fill());
		inv.setItem(13, fill());
		inv.setItem(14, fill());
		inv.setItem(15, fill_slot);
		inv.setItem(16, fill());
		inv.setItem(17, laner);
		
		inv.setItem(18, laner);
		inv.setItem(19, info(p, recipe));
		inv.setItem(20, fill_slot);
		inv.setItem(21, progress_right);
		inv.setItem(22, recipe.item);
		inv.setItem(23, progress_left);
		inv.setItem(24, fill_slot);
		inv.setItem(25, craft());
		inv.setItem(26, laner);
		
		inv.setItem(27, laner);
		inv.setItem(28, fill());
		inv.setItem(29, fill_slot);
		inv.setItem(30, fill());
		inv.setItem(31, back());
		inv.setItem(32, fill());
		inv.setItem(33, fill());
		inv.setItem(34, fill());
		inv.setItem(35, laner);
		
		inv.setItem(36, laner);
		inv.setItem(37, fill());
		inv.setItem(38, fill_slot);
		inv.setItem(39, fill_slot);
		inv.setItem(40, fill_slot);
		inv.setItem(41, fill());
		inv.setItem(42, fill());
		inv.setItem(43, fill());
		inv.setItem(44, laner);
		
		setUpMaterial(p, recipe);
	}

}
