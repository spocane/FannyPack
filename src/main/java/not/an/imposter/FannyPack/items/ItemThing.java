/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package not.an.imposter.FannyPack.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

/**
 *
 * @author logan
 */
public class ItemThing extends Item {
	protected String name;
	public ItemThing(String name) {
		this.name = name;
		setUnlocalizedName(name);
		setRegistryName(name);
	}
	
	public void handleKey(EntityPlayer player) {}
}
