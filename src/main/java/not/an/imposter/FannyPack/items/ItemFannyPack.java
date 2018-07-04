/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package not.an.imposter.FannyPack.items;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
import java.util.List;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

/**
 *
 * @author logan
 */
public class ItemFannyPack extends ItemThing implements IBauble {

	public ItemFannyPack() {
		super("fanny_pack");
		setCreativeTab(CreativeTabs.MISC);
		maxStackSize = 1;
	}
	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.BELT; //widen the scope of that shit later
	}
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		super.onCreated(stack, worldIn, playerIn);
		dontNeedTheBoy(stack,worldIn);
	}
	private void dontNeedTheBoy(ItemStack stack, World worldIn) {
		//this used to be in onCreated but I wanted to be able to call it
		//from addInformation which no longer has the player argument
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null) {
			nbt = new NBTTagCompound();
			NBTTagList hotbars = new NBTTagList();
			NBTTagList bar = new NBTTagList();
			for (int i=0;i<9;i++) bar.appendTag(new NBTTagCompound());
			hotbars.appendTag(bar);
			nbt.setTag("hotbars", hotbars);
			stack.setTagCompound(nbt);
		}
	}
	private static String getRoman(int n) {
		return String.format("%0" + n + "d", 0).replace("0","I")
			.replace("IIIII", "V").replace("IIII", "IV")
			.replace("VV", "X").replace("VIV", "IX")
			.replace("XXXXX", "L").replace("XXXX", "XL")
			.replace("LL", "C").replace("LXL", "XC")
			.replace("CCCCC", "D").replace("CCCC", "CD")
			.replace("DD", "M").replace("DCD", "CM");
	}
	@Override
	public void addInformation(ItemStack stack, World world, List list, ITooltipFlag par4) {
		NBTTagCompound beltNBT = stack.getTagCompound();
		int itemLevel = 1;
		if (beltNBT != null) {
			NBTTagList hotbars = (NBTTagList) beltNBT.getTag("hotbars");
			if (hotbars != null) {
				itemLevel = hotbars.tagCount();
			}
		} else {
			dontNeedTheBoy(stack,world);
		}
		list.add("Extra Hotbar "+getRoman(itemLevel));
		list.add("Upgrade With a Nether Star");
	}
	
	@Override
	public void handleKey(EntityPlayer player) {
		IBaublesItemHandler baubs = BaublesApi.getBaublesHandler(player);
		ItemStack beltCopy = baubs.getStackInSlot(BaubleType.BELT.getValidSlots()[0]).copy(); //maybe should iterate?
		NBTTagCompound beltNBT = beltCopy.getTagCompound();
		if (beltNBT == null) {
			onCreated(beltCopy,player.world,player);
			beltNBT = beltCopy.getTagCompound();
		}
		NBTTagList hotbars = (NBTTagList) beltNBT.getTag("hotbars");
		if (hotbars == null) hotbars = new NBTTagList();
		NBTTagList bar0 = (NBTTagList) hotbars.get(0).copy();
		NBTTagList barN = new NBTTagList();
		for (int i=0;i<9;i++) {
			ItemStack is = player.inventory.getStackInSlot(i);
			NBTTagCompound itemNBT;
			if (is != null) {
				itemNBT = new NBTTagCompound();
				is.writeToNBT(itemNBT);
				barN.appendTag(itemNBT);
			} else barN.appendTag(new NBTTagCompound());
			itemNBT = bar0.getCompoundTagAt(i);
			if (itemNBT != null) {
				is = new ItemStack(itemNBT);
				player.inventory.setInventorySlotContents(i,is);
			} else {
				player.inventory.setInventorySlotContents(i,null);
			}
		}
		for (int i=0;i<hotbars.tagCount()-1;i++) {
			hotbars.set(i, hotbars.get(i+1));
		}
		hotbars.set(hotbars.tagCount()-1, barN);
		
		beltNBT.setTag("hotbars", hotbars);
		beltCopy.setTagCompound(beltNBT);
		baubs.setStackInSlot(BaubleType.BELT.getValidSlots()[0], beltCopy);
	}
}
