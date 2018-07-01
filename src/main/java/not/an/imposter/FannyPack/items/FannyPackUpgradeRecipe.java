/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package not.an.imposter.FannyPack.items;

import com.google.gson.JsonObject;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeRepairItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.JsonUtils;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 *
 * @author logan
 */

//thanks DShadowWolf for this shit
public class FannyPackUpgradeRecipe extends RecipeRepairItem {
	public FannyPackUpgradeRecipe() {
		this.setRegistryName("fannypack:fannypackupgrade");
	}
	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		int packs = 0, stars = 0;
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack curItem = inv.getStackInSlot(i);
			if (curItem != null) {
				if (curItem.getItem() instanceof ItemFannyPack) packs++;
				if (curItem.getItem() == Items.NETHER_STAR) stars++;
			}
		}
		return (packs == 1 && stars == 1);
	}
	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack result = null;
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack curItem = inv.getStackInSlot(i);
			if (curItem != null) {
				if (curItem.getItem() instanceof ItemFannyPack) result = curItem.copy();
			}
		}
		if (result == null) return null;
		NBTTagCompound nbt = result.getTagCompound();
		if (nbt != null) {
			NBTTagList hotbars = (NBTTagList) nbt.getTag("hotbars");
			if (hotbars != null) {
				NBTTagList bar = new NBTTagList();
				for (int i=0;i<9;i++) bar.appendTag(new NBTTagCompound());
				hotbars.appendTag(bar);
				nbt.setTag("hotbars", hotbars);
			}
			result.setTagCompound(nbt);
		}
		return result;
	}
	@Override
	public boolean canFit(int width, int height) {
		return width*height >= 2;
	}
}
