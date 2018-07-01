/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package not.an.imposter.FannyPack;


import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import not.an.imposter.FannyPack.network.FannyMessage;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author logan
 */
public class HotkeyHandler {
	public static final int FANNY_PACK_NEXT = 0;
	
	private static final String[] desc = {"key.fanny_pack_next.desc"};
	private static final int[] keyValues = {Keyboard.KEY_BACKSLASH};
	private static KeyBinding[] keys;
	public HotkeyHandler() {
		keys = new KeyBinding[keyValues.length];
		for (int i=0;i<keyValues.length;i++) {
			keys[i] = new KeyBinding(desc[i],keyValues[i],"key.fannypack.category");
			ClientRegistry.registerKeyBinding(keys[i]);
		}
	}
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) {
		if (FMLClientHandler.instance().getClient().inGameHasFocus) {
			if (keys[FANNY_PACK_NEXT].isPressed()) {
				FannyPack.networkWrapper.sendToServer(new FannyMessage(FANNY_PACK_NEXT));
			}
		}
	}
}
