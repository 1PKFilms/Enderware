package Enderware.client;

import net.minecraftforge.client.MinecraftForgeClient;
import Enderware.client.listener.KeyListener;
import Enderware.common.CommonProxy;
import cpw.mods.fml.client.registry.KeyBindingRegistry;

public class ClientProxy extends CommonProxy{
	public static Boolean ShowOpScreen = false;
@Override
public void registerRenders() {
    MinecraftForgeClient.preloadTexture("/Enderware/resources/mods/computercraft.png");
   
}
@Override
	public void registerHandlers() {
    KeyBindingRegistry.registerKeyBinding(new KeyListener());
	}
@Override
public void registerPermissions() {}
}
