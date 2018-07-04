/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package not.an.imposter.FannyPack.network;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import not.an.imposter.FannyPack.HotkeyHandler;
import not.an.imposter.FannyPack.items.ItemThing;

/**
 *
 * @author logan
 */
public class FannyMessage implements IMessage {
	public FannyMessage(){}
	public int msg;
	public FannyMessage(int toSend) {
		msg = toSend;
	}
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(msg);
	}
	@Override
	public void fromBytes(ByteBuf buf) {
		msg = buf.readInt();
	}
	public static class FannyMessageHandler implements IMessageHandler<FannyMessage,IMessage> {
		@Override
		public IMessage onMessage(FannyMessage message, MessageContext ctx) {
			EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
			int keyIndex = message.msg;
			serverPlayer.getServerWorld().addScheduledTask(() -> {
				switch(keyIndex) {
					case HotkeyHandler.FANNY_PACK_NEXT:
						IBaublesItemHandler baubs = BaublesApi.getBaublesHandler(serverPlayer);
						ItemStack beltMaybe = baubs.getStackInSlot(BaubleType.TRINKET.getValidSlots()[0]); //maybe should iterate?
						if (beltMaybe != null && beltMaybe.getItem() instanceof ItemThing) {
							((ItemThing) beltMaybe.getItem()).handleKey(serverPlayer); //but on the server
						}
						break;
				}
			});
			return null;
		}
	}
}
