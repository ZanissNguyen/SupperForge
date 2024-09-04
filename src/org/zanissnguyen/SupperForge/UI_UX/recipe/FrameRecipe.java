package org.zanissnguyen.SupperForge.UI_UX.recipe;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.zanissnguyen.SupperForge.SupperForge;
import org.zanissnguyen.SupperForge.Utils;
import org.zanissnguyen.SupperForge.API.SRecipe;
import org.zanissnguyen.SupperForge.API.SUserInterface_Recipe;


public class FrameRecipe extends SUserInterface_Recipe
{
	public FrameRecipe(SupperForge plugin, Utils utils)
	{
		super(SupperForge.getInstance(), utils, 5);	
		
		materialsSlot.add(3);
		materialsSlot.add(11);
		materialsSlot.add(19);
		materialsSlot.add(29);
		materialsSlot.add(39);
		
		materialsSlot.add(5);
		materialsSlot.add(15);
		materialsSlot.add(25);
		materialsSlot.add(33);
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
		int type = 0;
		if (recipe.item.getType().toString().contains("SWORD")) type =1;
		else if (recipe.item.getType().toString().contains("BOW")) type = 2;
		else if (recipe.item.getType().toString().contains("HELMET")) type = 3;
		else if (recipe.item.getType().toString().contains("CHESTPLATE")) type = 4;
		else if (recipe.item.getType().toString().contains("LEGGINGS")) type = 5;
		else if (recipe.item.getType().toString().contains("BOOTS")) type = 6;
		else if (recipe.item.getType().toString().contains("SHIELD")) type = 7;
		else if (recipe.item.getType().toString().contains("_AXE")) type = 8;
		else if (recipe.item.getType().toString().contains("PICKAXE") || recipe.item.getType().toString().contains("SHOVEL") ||
			recipe.item.getType().toString().contains("HOE")) type = 9;	
		else type = 0;
		
		List<ItemStack> progress = new ArrayList<>();
		for (int i = 0; i<8; i++)
		{
			if (type == 0)
			{
				progress.add(utils.createItem(Material.LIME_STAINED_GLASS_PANE, 1, 0, false, " "));
			}
			else
			{
				progress.add(utils.createItem(Material.LIME_STAINED_GLASS_PANE, 1, 100+type*10+i, false, " "));
			}
		}
		
		inv.setItem(0, laner);
		inv.setItem(1, fill());
		inv.setItem(2, fill());
		inv.setItem(3, fill_slot);
		inv.setItem(4, fill());
		inv.setItem(5, fill_slot);
		inv.setItem(6, fill());
		inv.setItem(7, fill());
		inv.setItem(8, laner);
		
		inv.setItem(9, laner);
		inv.setItem(10, fill());
		inv.setItem(11, fill_slot);
		inv.setItem(12, progress.get(0));
		inv.setItem(13, progress.get(1));
		inv.setItem(14, progress.get(2));
		inv.setItem(15, fill_slot);
		inv.setItem(16, fill());
		inv.setItem(17, laner);
		
		inv.setItem(18, laner);
		inv.setItem(19, fill_slot);
		inv.setItem(20, info(p, recipe));
		inv.setItem(21, progress.get(3));
		inv.setItem(22, recipe.item);
		inv.setItem(23, progress.get(4));
		inv.setItem(24, craft());
		inv.setItem(25, fill_slot);
		inv.setItem(26, laner);
		
		inv.setItem(27, laner);
		inv.setItem(28, fill());
		inv.setItem(29, fill_slot);
		inv.setItem(30, progress.get(5));
		inv.setItem(31, progress.get(6));
		inv.setItem(32, progress.get(7));
		inv.setItem(33, fill_slot);
		inv.setItem(34, fill());
		inv.setItem(35, laner);
		
		inv.setItem(36, laner);
		inv.setItem(37, fill());
		inv.setItem(38, fill());
		inv.setItem(39, fill_slot);
		inv.setItem(40, back());
		inv.setItem(41, fill_slot);
		inv.setItem(42, fill());
		inv.setItem(43, fill());
		inv.setItem(44, laner);
		
		setUpMaterial(p, recipe);
	}

}
