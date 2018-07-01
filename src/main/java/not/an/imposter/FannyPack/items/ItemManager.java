/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package not.an.imposter.FannyPack.items;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeRepairItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.RecipeSorter;
import not.an.imposter.FannyPack.FannyPack;

/**
 *
 * @author logan
 */
public class ItemManager {
	public static ItemThing fanny_pack;
	
	private static List<Item> items = new ArrayList<>();
	
	public static void init() {
		fanny_pack = register(new ItemFannyPack());
		GameRegistry.addShapedRecipe(
				new ResourceLocation("fannypack:fprecipe"),
				new ResourceLocation("fannypack:fpgroup"),
				new ItemStack(ItemManager.fanny_pack),
				"LLL",
				"ICI",
				"LLL",
				'L', Items.LEATHER, 'I', Items.IRON_INGOT, 'C', Blocks.CHEST
		);
		GameRegistry.findRegistry(IRecipe.class).register(new FannyPackUpgradeRecipe());
		//GameRegistry.addRecipe(new FannyPackUpgradeRecipe());
		
		//RecipeSorter.register("fannypack:fannypackupgrade", FannyPackUpgradeRecipe.class, RecipeSorter.Category.UNKNOWN, "after:minecraft:shapeless");
	}
	
	private static <T extends Item> T register(T item) {
		GameRegistry.findRegistry(Item.class).register(item);
		if (FMLCommonHandler.instance().getSide().isClient() && item instanceof ItemThing) {
			ModelLoader.setCustomModelResourceLocation(
				item,
				0,
				new ModelResourceLocation(FannyPack.MODID+":"+((ItemThing) item).name,"inventory")
			);
		}
		return item;
	}
}
