package Enderware.common;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import net.minecraftforge.oredict.ShapedOreRecipe;
import Enderware.client.config.SettingsAdabterClient;
import Enderware.config.Config;
import Enderware.config.SettingsAdapter;
import Enderware.config.SettingsAdapterServer;
import Enderware.modsupport.computercraft.blocks.CommanBlockModem;
import Enderware.network.Packehandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid="Enderware", name="Enderware", version="0.0.3")

@NetworkMod(clientSideRequired=true, serverSideRequired=false,channels={"ModSettings"}, packetHandler = Packehandler.class)
public class Enderware {
        public static Block commandBlock = null;
        // The instance of your mod that Forge uses.
        @Instance("Enderware")
        public static Enderware instance;
     
        @SidedProxy(clientSide="Enderware.client.ClientProxy", serverSide="Enderware.common.CommonProxy")
        public static CommonProxy proxy;
        
        @PreInit
        public void preInit(FMLPreInitializationEvent event) {
          	if(event.getSide() == Side.SERVER){
          
            
                Configuration config = new Configuration(new File("server.settings"));
                config.load();
                config.addCustomCategoryComment("Chat", "");
                Config.configs.put("coloredAdminnames",config.get("Chat", "ColoredAdminNames", true).getBoolean(true) ) ;
                Config.configs.put("ChatColors",config.get("Chat", "ChatColors", true).getBoolean(true) ) ;
               Property chatcatigurations = config.get("Chat", "showRankinChat", true);
               chatcatigurations.comment = "if enabled <Admin|1PKFilms>message\n if not <1PKFilms>message";
               Config.configs.put("Chatcategories",chatcatigurations.getBoolean(true) );
               Config.configs.put("ChatRange",config.get("Chat", "ChatRange",25).getInt(25) );
                config.save();
               
            

            	}
            Configuration config = new Configuration(event.getSuggestedConfigurationFile());

           
            config.load();
            commandBlock = new CommanBlockModem(config.getBlock("PlayerCommandBlock", 1999).getInt()                                                                                                                                                );
            
            config.save();
        	}
        
        @SuppressWarnings({ "rawtypes", "unchecked" })
		@Init
        public void load(FMLInitializationEvent event) {
            
            
        	Iterator i = new SettingsAdapter().allsettings.iterator();
			while(i.hasNext()){
	Iterator y = ((HashMap<String, Object>) i.next()).keySet().iterator();
	
	while(y.hasNext()){
		String temp = (String) y.next();
		if(temp != null && temp != "")
		NetworkRegistry.instance().registerChannel(new Packehandler(), temp);
	}
	NetworkRegistry.instance().registerChannel(new Packehandler(), "shoutdown");
			}
			if(event.getSide() == Side.CLIENT)new SettingsAdabterClient();
			if(event.getSide() == Side.SERVER)new SettingsAdapterServer();
			proxy.registerHandlers();
            proxy.registerRenders();
            proxy.registerPermissions();
            registerBlocks();
            registerRecipys();
           
        }
        
        @PostInit
        public void postInit(FMLPostInitializationEvent event) {
            
          
        }
        @ServerStarting
        public void serverStarting(FMLServerStartingEvent event) {

          
            Command.initCommands(event);
        }
        private void registerBlocks(){
            GameRegistry.registerBlock(commandBlock, "PlayerCommandBlock");
            LanguageRegistry.addName(commandBlock, "PlayerCommandBlock");
            
        }
        private void registerRecipys(){
          GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(commandBlock), new Object[]{
              "sSs",
              "whw",
              "wrw",
              Character.valueOf('w'),"plankWood",
              Character.valueOf('S'),Block.stoneSingleSlab,
             Character.valueOf('s'),"slabWood",
             Character.valueOf('h'),new ItemStack(Item.skull,1,3),
             Character.valueOf('r'),Item.redstone
             
          }));
        }
}