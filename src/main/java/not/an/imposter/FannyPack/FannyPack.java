package not.an.imposter.FannyPack;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import not.an.imposter.FannyPack.items.FannyPackUpgradeRecipe;
import not.an.imposter.FannyPack.items.ItemManager;
import not.an.imposter.FannyPack.network.FannyMessage;

@Mod(modid = FannyPack.MODID, version = FannyPack.VERSION, dependencies="required-after:baubles")
public class FannyPack {
	public static final String MODID = "fannypack";
	public static final String VERSION = "1.0";

	@Mod.Instance(MODID)
	public static FannyPack instance;
	
	public static final SimpleNetworkWrapper networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel("fannypack");
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ItemManager.init();
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		if (FMLCommonHandler.instance().getSide().isClient())
			MinecraftForge.EVENT_BUS.register(new HotkeyHandler());
		networkWrapper.registerMessage(FannyMessage.FannyMessageHandler.class, FannyMessage.class, 0, Side.SERVER);
    }
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
}
