package Enderware.common;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import Enderware.client.config.SettingsAdabterClient;
import Enderware.config.Config;
import Enderware.config.SettingsAdapter;
import Enderware.config.SettingsAdapterServer;
import Enderware.modsupport.computercraft.blocks.CommanBlockModem;
import Enderware.network.Packehandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid="Enderware", name="Enderware", version="0.0.1")

@NetworkMod(clientSideRequired=true, serverSideRequired=false,channels={"Admin"}, packetHandler = Packehandler.class)
public class Enderware {
        
        // The instance of your mod that Forge uses.
        @Instance("Enderware")
        public static Enderware instance;
     
        @SidedProxy(clientSide="Enderware.client.ClientProxy", serverSide="Enderware.common.CommonProxy")
        public static CommonProxy proxy;
        
        @PreInit
        public void preInit(FMLPreInitializationEvent event) {
          	if(event.getSide() == Side.SERVER){
          	  ModContainer cc = (ModContainer)Loader.instance().getIndexedModList().get("ComputerCraft");
              if (cc != null) Config.configs.put("ComputerCraft", true);else Config.configs.put("ComputerCraft", false);
            
                Configuration config = new Configuration(new File("server.settings"));
                config.load();
                config.addCustomCategoryComment("Chat", "");
                Config.configs.put("coloredAdminnames",config.get("Chat", "ColoredAdminNames", true).getBoolean(true) ) ;
                Config.configs.put("ChatColors",config.get("Chat", "ChatColors", true).getBoolean(true) ) ;
               Property chatcatigurations = config.get("Chat", "showRankinChat", true);
               chatcatigurations.comment = "if enabled <Admin|1PKFilms>message\n if not <1PKFilms>message";
               Config.configs.put("Chatcategories",chatcatigurations.getBoolean(true) );
                config.save();
               
            

            	}
        	}
        
        @SuppressWarnings({ "rawtypes", "unchecked" })
		@Init
        public void load(FMLInitializationEvent event) {
            
            registerBlocks();
        	Iterator i = new SettingsAdapter().allsettings.iterator();
			while(i.hasNext()){
	Iterator y = ((HashMap<String, Object>) i.next()).keySet().iterator();
	
	while(y.hasNext()){
		String temp = (String) y.next();
		if(temp != null && temp != "")
		NetworkRegistry.instance().registerChannel(new Packehandler(), temp);
	}
			}
			if(event.getSide() == Side.CLIENT)new SettingsAdabterClient();
			if(event.getSide() == Side.SERVER)new SettingsAdapterServer();
			proxy.registerHandlers();
            proxy.registerRenders();
            proxy.registerPermissions();
        }
        
        @PostInit
        public void postInit(FMLPostInitializationEvent event) {
            
          
        }
        @ServerStarting
        public void serverStarting(FMLServerStartingEvent event) {

          
            Command.initCommands(event);
        }
        private void registerBlocks(){
            GameRegistry.registerBlock(new CommanBlockModem(1999), "PlayerCommandBlock");
            
        }
}