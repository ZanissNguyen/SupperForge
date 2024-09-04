package org.zanissnguyen.SupperForge.UI_UX.recipe;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.zanissnguyen.SupperForge.SupperForge;
import org.zanissnguyen.SupperForge.Utils;
import org.zanissnguyen.SupperForge.API.SRecipe;
import org.zanissnguyen.SupperForge.API.SUserInterface_Recipe;
import org.zanissnguyen.SupperForge.API.Style;

public class CustomRecipe extends SUserInterface_Recipe
{
	private ItemStack fill = utils.createItem(Material.WHITE_STAINED_GLASS_PANE, 1, 0, " ");
	
	public CustomRecipe(SupperForge plugin, Utils utils)
	{
		super(SupperForge.getInstance(), utils, 1);	
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
		String styleStr = recipe.type;
		Style sty = SupperForge.styStorage.getStyle(styleStr);
		rows = sty.rows;
		materialsSlot.addAll(sty.materials);
		
		//is default_fill valid
		if (sty.icon.keySet().contains(sty.default_fill)) fill = SupperForge.styStorage.getIcon(sty.id+".icons."+sty.default_fill);
		
		inv = Bukkit.createInventory(null, rows*9, name_prefix+utils.color(" &e(#"+recipe.id+")"));
		
		//default
		for (int i = 0; i<rows*9; i++) inv.setItem(i, fill);
		
		//icons
		for (String s: sty.icon.keySet())
		{
			for (int i: sty.iconSlots.get(s))
			{
				inv.setItem(i, sty.icon.get(s));
			}
		}
		
		// materials
		ItemStack fill_slot = utils.createItem(Material.BLACK_STAINED_GLASS_PANE, 1, 0, false, " ");
		for (int i = 0; i<materialsSlot.size(); i++)
		{
			inv.setItem(materialsSlot.get(i), fill_slot);
		}
		setUpMaterial(p, recipe);
		
		// reward
		inv.setItem(sty.reward_slot, recipe.item);
		
		// craft, back, info
		inv.setItem(sty.back_slot, sty.back());
		inv.setItem(sty.craft_slot, sty.craft());
		inv.setItem(sty.info_slot, sty.info(p, recipe));
	}

}
