package Enderware.config;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.src.ModLoader;

public class ServerSettingsInterface {
	static int i = 0;
public static void update(String toUpdate){
	i++;
	MinecraftServer mc = ModLoader.getMinecraftServerInstance();
	if(toUpdate.equals("allow-nether")){
		if(!(mc instanceof DedicatedServer))return;
		((DedicatedServer) mc).setProperty("allow-nether", SettingsAdapter.instance().Worldconfigs.get("allow-nether"));
	}

	    if(toUpdate.equals("allow-flight")){
		if(!(mc instanceof DedicatedServer))return;
		((DedicatedServer) mc).setProperty("allow-flight", SettingsAdapter.instance().Serverconfigs.get("allow-flight"));
}
	    if(toUpdate.equals("port")){
		if(!(mc instanceof DedicatedServer))return;
		((DedicatedServer) mc).setProperty("server-port", SettingsAdapter.instance().Serverconfigs.get("port"));
}
	    if(toUpdate.equals("max-build-height")){
		mc.setBuildLimit(Integer.valueOf(SettingsAdapter.instance().Worldconfigs.get("max-build-height").toString()));
		}
	if (i >= 5){
		if(!(mc instanceof DedicatedServer))return;
		((DedicatedServer) mc).saveProperties();
		i=0;
	}
	
	
	
}
}
