package Enderware.config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.SERVER)
public class SettingsAdapterServer extends SettingsAdapter{
	public boolean handlePacket(Packet250CustomPayload packet){
		HashMap<String , Object> map = null;
		if(Worldconfigs.containsKey(packet.channel))map = Worldconfigs;
		if(Chatconfigs.containsKey(packet.channel))map =Chatconfigs;
		if( Serverconfigs.containsKey(packet.channel))map = Serverconfigs;
		if( Playerconfigs.containsKey(packet.channel))map = Playerconfigs;
					
				if(map != null)
		if(packet.data.length == 1){
			map.put(packet.channel,readBoolean(packet));
			if(!Vanillasettings.containsKey(packet.channel)){
				update(packet.channel);
				}else{
					updateVanilla(packet.channel);
				}
			return true;
		}else if(packet.data.length == Integer.SIZE){
			map.put(packet.channel,readInt(packet));
			if(!Vanillasettings.containsKey(packet.channel)){
				update(packet.channel);
				}else{
					updateVanilla(packet.channel);
				}
			return true;
			
		}else{
			map.put(packet.channel,readString(packet));
			if(!Vanillasettings.containsKey(packet.channel)){
			update(packet.channel);
			}else{
				updateVanilla(packet.channel);
			}
			return true;
		}
		return false;
		
	}
	private boolean readBoolean(Packet250CustomPayload packet){
		 DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		 
		 try {
			return inputStream.readBoolean();
			
		
		} catch (IOException e) {
		
			e.printStackTrace();
			return false;
		}
		
		
	}
	private int readInt(Packet250CustomPayload packet){
		 DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		 try {
			return inputStream.readInt();
			
		
		} catch (IOException e) {
		
			e.printStackTrace();
			return 0;
		}
		
		
	}
	private String readString(Packet250CustomPayload packet){
		 DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		 try {
			return inputStream.readUTF();
			
		
		} catch (IOException e) {
		
			e.printStackTrace();
			return "";
		}
		
		
	}
	public void updateVanilla(String toset){
		ServerSettingsInterface.update(toset);
		HashMap<String , Object> map = null;
		if(Worldconfigs.containsKey(toset))map = Worldconfigs;
		if(Chatconfigs.containsKey(toset))map =Chatconfigs;
		if( Serverconfigs.containsKey(toset))map = Serverconfigs;
		if( Playerconfigs.containsKey(toset))map = Playerconfigs;
		if(map != null){
			int size = 0;
		
			
		if(map.get(toset) == Boolean.FALSE ||map.get(toset) == Boolean.TRUE )size=1;
		else {
			boolean isint = false;
			try{
				Integer.parseInt(map.get(toset).toString());
				isint = true;
			}catch(NumberFormatException exeption){
				isint = false;
			}
			if(isint)size = Integer.SIZE;
		}
		
		if(size == 0&&map.get(toset) instanceof String){
			size=((String)map.get(toset)).length();
			   ByteArrayOutputStream bos = new ByteArrayOutputStream(size);
			     DataOutputStream outputStream = new DataOutputStream(bos);
			     try {
			             outputStream.writeUTF(((String)map.get(toset)));
			            
			     } catch (Exception ex) {
			             ex.printStackTrace();
			     }
			    Packet250CustomPayload packet = new Packet250CustomPayload();
			    packet.channel = toset;
			    packet.data = bos.toByteArray();
			    packet.length = bos.size();
			    
			    Side side = FMLCommonHandler.instance().getEffectiveSide();
			    if (side == Side.SERVER) 
			      
			  	  PacketDispatcher.sendPacketToAllPlayers(packet);
			    return;
			}
		
		   ByteArrayOutputStream bos = new ByteArrayOutputStream(size);
	     DataOutputStream outputStream = new DataOutputStream(bos);
	     try {
	    	 if(size == 0)
	          return;
	    	 if(size == 1)outputStream.writeBoolean(map.get(toset) == Boolean.TRUE?true:false);
	    	
	    	 if(size==Integer.SIZE)outputStream.writeInt(((Integer)map.get(toset)));
	     } catch (Exception ex) {
	             ex.printStackTrace();
	     }
	    Packet250CustomPayload packet = new Packet250CustomPayload();
	    packet.channel = toset;
	    packet.data = bos.toByteArray();
	    packet.length = bos.size();
	    
	    Side side = FMLCommonHandler.instance().getEffectiveSide();
	    if (side == Side.SERVER) 
	      
	  	  PacketDispatcher.sendPacketToAllPlayers(packet);
	}
	}
	public void read(){
	Iterator<String> i = Config.configs.keySet().iterator();
	
	while(i.hasNext()){
		String temp =((String)i.next());
		if(this.Chatconfigs.containsKey(temp)){
			this.Chatconfigs.put(temp, Config.configs.get(temp));
			continue;
		}
		if(this.Serverconfigs.containsKey(temp)){
			this.Serverconfigs.put(temp, Config.configs.get(temp));
			continue;
		}
		if(this.Playerconfigs.containsKey(temp)){
			this.Playerconfigs.put(temp, Config.configs.get(temp));
			continue;
		}
		if(this.Worldconfigs.containsKey(temp)){
			this.Worldconfigs.put(temp, Config.configs.get(temp));
			continue;
		}
	}
	if(MinecraftServer.getServer() instanceof DedicatedServer){
		Worldconfigs.put("command-block",  ((DedicatedServer)MinecraftServer.getServer()).isCommandBlockEnabled());
		Worldconfigs.put("allow-nether", ((DedicatedServer)MinecraftServer.getServer()).getAllowNether());
		Serverconfigs.put("allow-flight", ((DedicatedServer)MinecraftServer.getServer()).isFlightAllowed());
		Worldconfigs.put("spawn-npcs", ((DedicatedServer)MinecraftServer.getServer()).getCanSpawnNPCs());
		Serverconfigs.put("enable-withlist", ((DedicatedServer)MinecraftServer.getServer()).getConfigurationManager().isWhiteListEnabled());
		Worldconfigs.put("spawn-animals", ((DedicatedServer)MinecraftServer.getServer()).getCanSpawnAnimals());
		Serverconfigs.put("hardcore", ((DedicatedServer)MinecraftServer.getServer()).isHardcore());
		Serverconfigs.put("texturepacke", ((DedicatedServer)MinecraftServer.getServer()).getTexturePack());
		Serverconfigs.put("online-mode", ((DedicatedServer)MinecraftServer.getServer()).isServerInOnlineMode());
		Serverconfigs.put("pvp", ((DedicatedServer)MinecraftServer.getServer()).isPVPEnabled());
		Worldconfigs.put("diffuculty", ((DedicatedServer)MinecraftServer.getServer()).getDifficulty());
		Playerconfigs.put("gamemode", ((DedicatedServer)MinecraftServer.getServer()).getGameType().getID());
		Chatconfigs.put("max-players", ((DedicatedServer)MinecraftServer.getServer()).getMaxPlayers());
		Worldconfigs.put("spawn-monsters", ((DedicatedServer)MinecraftServer.getServer()).allowSpawnMonsters());
		Worldconfigs.put("structures", ((DedicatedServer)MinecraftServer.getServer()).canStructuresSpawn());
		Chatconfigs.put("view-distance", ((DedicatedServer)MinecraftServer.getServer()).getConfigurationManager().getViewDistance());
		Chatconfigs.put("motd",((DedicatedServer)MinecraftServer.getServer()).getMOTD());
	}
	}
	
	public void update(String  toset){
		
		HashMap<String , Object> map = null;
		if(Worldconfigs.containsKey(toset))map = Worldconfigs;
		if(Chatconfigs.containsKey(toset))map =Chatconfigs;
		if( Serverconfigs.containsKey(toset))map = Serverconfigs;
		if( Playerconfigs.containsKey(toset))map = Playerconfigs;
		if(map != null){
			int size = 0;
	
			if(map.get(toset).getClass() ==  Boolean.TYPE)size=1;
			if(map.get(toset).getClass() == Integer.TYPE)size=Integer.SIZE;
			
			if(map.get(toset) instanceof String){
			size=((String)map.get(toset)).length();
			   ByteArrayOutputStream bos = new ByteArrayOutputStream(size);
			     DataOutputStream outputStream = new DataOutputStream(bos);
			     try {
			             outputStream.writeUTF(((String)map.get(toset)));
			            
			     } catch (Exception ex) {
			             ex.printStackTrace();
			     }
			    Packet250CustomPayload packet = new Packet250CustomPayload();
			    packet.channel = toset;
			    packet.data = bos.toByteArray();
			    packet.length = bos.size();
			    
			    Side side = FMLCommonHandler.instance().getEffectiveSide();
			    if (side == Side.SERVER) 
			      
			  	  PacketDispatcher.sendPacketToAllPlayers(packet);
			}
		
		   ByteArrayOutputStream bos = new ByteArrayOutputStream(size);
	     DataOutputStream outputStream = new DataOutputStream(bos);
	     try {
	    	 if(size == 0)
	          return;
	    	 if(size == 1)outputStream.writeBoolean(((Boolean)map.get(toset)));
	    	 if(size==Integer.SIZE)outputStream.writeInt(((Integer)map.get(toset)));
	     } catch (Exception ex) {
	             ex.printStackTrace();
	     }
	    Packet250CustomPayload packet = new Packet250CustomPayload();
	    packet.channel = toset;
	    packet.data = bos.toByteArray();
	    packet.length = bos.size();
	    
	    Side side = FMLCommonHandler.instance().getEffectiveSide();
	    if (side == Side.SERVER) 
	      
	  	  PacketDispatcher.sendPacketToAllPlayers(packet);
	}
	}
	public void updateMod(String file,String category,String entry){

        ByteArrayOutputStream bos = new ByteArrayOutputStream((file.length()+category.length()+entry.length()
                )*2);
        DataOutputStream outputStream = new DataOutputStream(bos);
        
        Packet250CustomPayload packet = new Packet250CustomPayload();
    

       
        
      
        try {
            outputStream.writeUTF(file);
            outputStream.writeUTF(category);
            outputStream.writeUTF(entry);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
       
        
        try{
        outputStream.writeUTF(ModSetting.AllSettings.get(file).categorys.get(category).get(entry));
        }catch(Throwable t){
            t.printStackTrace();
            return;
        }
        packet.channel = "ModSettings";
        packet.data = bos.toByteArray();
        packet.length = bos.size();
       PacketDispatcher.sendPacketToAllPlayers(packet);
       
	}
public void updateAll(){
	Iterator<HashMap<String, Object>> i = allsettings.iterator();
	while(i.hasNext()){
		@SuppressWarnings("rawtypes")
		Iterator y = ((HashMap<String, Object>)i.next()).keySet().iterator();
		while(y.hasNext()){
			String temp =((String)y.next());
			if(Vanillasettings.containsKey(temp))updateVanilla(temp);
			else update(temp);
		}
	}
}
public void updateAllMods(){
    Iterator<String> files = ModSetting.AllSettings.keySet().iterator();
    while(files.hasNext()){
        String filetemp = files.next();
        Iterator<String> categorys = ModSetting.AllSettings.get(filetemp).categorys.keySet().iterator();
            while(categorys.hasNext()){
                String categorytemp = categorys.next();
                Iterator<String> entrys = ModSetting.AllSettings.get(filetemp).categorys.get(categorytemp).keySet().iterator();
                    while(entrys.hasNext()){
                        updateMod(filetemp, categorytemp, entrys.next());
                    }
            }
    }
}
public void update(String  toset,EntityPlayer player){
    
    HashMap<String , Object> map = null;
    if(Worldconfigs.containsKey(toset))map = Worldconfigs;
    if(Chatconfigs.containsKey(toset))map =Chatconfigs;
    if( Serverconfigs.containsKey(toset))map = Serverconfigs;
    if( Playerconfigs.containsKey(toset))map = Playerconfigs;
    if(map != null){
        int size = 0;

        if(map.get(toset).getClass() ==  Boolean.TYPE)size=1;
        if(map.get(toset).getClass() == Integer.TYPE)size=Integer.SIZE;
        
        if(map.get(toset) instanceof String){
        size=((String)map.get(toset)).length();
           ByteArrayOutputStream bos = new ByteArrayOutputStream(size);
             DataOutputStream outputStream = new DataOutputStream(bos);
             try {
                     outputStream.writeUTF(((String)map.get(toset)));
                    
             } catch (Exception ex) {
                     ex.printStackTrace();
             }
            Packet250CustomPayload packet = new Packet250CustomPayload();
            packet.channel = toset;
            packet.data = bos.toByteArray();
            packet.length = bos.size();
            
            Side side = FMLCommonHandler.instance().getEffectiveSide();
            if (side == Side.SERVER) 
              
                PacketDispatcher.sendPacketToPlayer(packet,(Player)player);
        }
    
       ByteArrayOutputStream bos = new ByteArrayOutputStream(size);
     DataOutputStream outputStream = new DataOutputStream(bos);
     try {
         if(size == 0)
          return;
         if(size == 1)outputStream.writeBoolean(((Boolean)map.get(toset)));
         if(size==Integer.SIZE)outputStream.writeInt(((Integer)map.get(toset)));
     } catch (Exception ex) {
             ex.printStackTrace();
     }
    Packet250CustomPayload packet = new Packet250CustomPayload();
    packet.channel = toset;
    packet.data = bos.toByteArray();
    packet.length = bos.size();
    
    Side side = FMLCommonHandler.instance().getEffectiveSide();
    if (side == Side.SERVER) 
      
        PacketDispatcher.sendPacketToPlayer(packet,(Player)player);
}
}
public void updateVanilla(String toset,EntityPlayer player){
    ServerSettingsInterface.update(toset);
    HashMap<String , Object> map = null;
    if(Worldconfigs.containsKey(toset))map = Worldconfigs;
    if(Chatconfigs.containsKey(toset))map =Chatconfigs;
    if( Serverconfigs.containsKey(toset))map = Serverconfigs;
    if( Playerconfigs.containsKey(toset))map = Playerconfigs;
    if(map != null){
        int size = 0;
    
        
    if(map.get(toset) == Boolean.FALSE ||map.get(toset) == Boolean.TRUE )size=1;
    else {
        boolean isint = false;
        try{
            Integer.parseInt(map.get(toset).toString());
            isint = true;
        }catch(NumberFormatException exeption){
            isint = false;
        }
        if(isint)size = Integer.SIZE;
    }
    
    if(size == 0&&map.get(toset) instanceof String){
        size=((String)map.get(toset)).length();
           ByteArrayOutputStream bos = new ByteArrayOutputStream(size);
             DataOutputStream outputStream = new DataOutputStream(bos);
             try {
                     outputStream.writeUTF(((String)map.get(toset)));
                    
             } catch (Exception ex) {
                     ex.printStackTrace();
             }
            Packet250CustomPayload packet = new Packet250CustomPayload();
            packet.channel = toset;
            packet.data = bos.toByteArray();
            packet.length = bos.size();
            
            Side side = FMLCommonHandler.instance().getEffectiveSide();
            if (side == Side.SERVER) 
              
                PacketDispatcher.sendPacketToPlayer(packet,(Player)player);
            return;
        }
    
       ByteArrayOutputStream bos = new ByteArrayOutputStream(size);
     DataOutputStream outputStream = new DataOutputStream(bos);
     try {
         if(size == 0)
          return;
         if(size == 1)outputStream.writeBoolean(map.get(toset) == Boolean.TRUE?true:false);
        
         if(size==Integer.SIZE)outputStream.writeInt(((Integer)map.get(toset)));
     } catch (Exception ex) {
             ex.printStackTrace();
     }
    Packet250CustomPayload packet = new Packet250CustomPayload();
    packet.channel = toset;
    packet.data = bos.toByteArray();
    packet.length = bos.size();
    
    Side side = FMLCommonHandler.instance().getEffectiveSide();
    if (side == Side.SERVER) 
      
        PacketDispatcher.sendPacketToPlayer(packet,(Player)player);
}
}

public void updateMod(String file,String category,String entry,EntityPlayer player){

    ByteArrayOutputStream bos = new ByteArrayOutputStream((file.length()+category.length()+entry.length()
            )*2);
    DataOutputStream outputStream = new DataOutputStream(bos);
    
    Packet250CustomPayload packet = new Packet250CustomPayload();


   
    
  
    try {
        outputStream.writeUTF(file);
        outputStream.writeUTF(category);
        outputStream.writeUTF(entry);
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    
   
    
    try{
    outputStream.writeUTF(ModSetting.AllSettings.get(file).categorys.get(category).get(entry));
    }catch(Throwable t){
        t.printStackTrace();
        return;
    }
    packet.channel = "ModSettings";
    packet.data = bos.toByteArray();
    packet.length = bos.size();
   PacketDispatcher.sendPacketToPlayer(packet,(Player) player);
   
}
public void updateAll(EntityPlayer player){
Iterator<HashMap<String, Object>> i = allsettings.iterator();
while(i.hasNext()){
    @SuppressWarnings("rawtypes")
    Iterator y = ((HashMap<String, Object>)i.next()).keySet().iterator();
    while(y.hasNext()){
        String temp =((String)y.next());
        if(Vanillasettings.containsKey(temp))updateVanilla(temp,player);
        else update(temp,player);
    }
}
}
public void updateAllMods(EntityPlayer player){
    Iterator<String> files = ModSetting.AllSettings.keySet().iterator();
    while(files.hasNext()){
        String filetemp = files.next();
        Iterator<String> categorys = ModSetting.AllSettings.get(filetemp).categorys.keySet().iterator();
            while(categorys.hasNext()){
                String categorytemp = categorys.next();
                Iterator<String> entrys = ModSetting.AllSettings.get(filetemp).categorys.get(categorytemp).keySet().iterator();
                    while(entrys.hasNext()){
                        updateMod(filetemp, categorytemp, entrys.next(),player);
                    }
            }
    }
}
}

