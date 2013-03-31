package Enderware.client.config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
@SideOnly(Side.CLIENT)
public class SettingsAdabterClient extends Enderware.config.SettingsAdapter{

public boolean handlePacket(Packet250CustomPayload packet){
	HashMap<String , Object> map = null;
	if(Worldconfigs.containsKey(packet.channel))map = Worldconfigs;
	if(Chatconfigs.containsKey(packet.channel))map =Chatconfigs;
	if( Serverconfigs.containsKey(packet.channel))map = Serverconfigs;
	if( Playerconfigs.containsKey(packet.channel))map = Playerconfigs;
				
			if(map != null)
	if(packet.data.length == 1){
		
		map.put(packet.channel,readBoolean(packet));
		return true;
	}else if(packet.data.length == Integer.SIZE){
		map.put(packet.channel,readInt(packet));
		return true;
		
	}else{
		map.put(packet.channel,readString(packet));
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
public void set(String toset,Object tosetto){
	HashMap<String , Object> map = null;
	if(Worldconfigs.containsKey(toset))map = Worldconfigs;
	else  if(Chatconfigs.containsKey(toset))map =Chatconfigs;
	else  if( Serverconfigs.containsKey(toset))map = Serverconfigs;
	else if( Playerconfigs.containsKey(toset))map = Playerconfigs;
	
	if(map != null){

			map.put(toset, tosetto);
			sendChanges(toset);

	}
	}
public void sendChanges(String toset){
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
		    if (side == Side.CLIENT) 
		      
		  	  PacketDispatcher.sendPacketToAllPlayers(packet);
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
    if (side == Side.CLIENT) 
      
  	  PacketDispatcher.sendPacketToServer(packet);
}
}
public void sendAllChanges(){
	Iterator<?> i = allsettings.iterator();
			while(i.hasNext()){
	@SuppressWarnings("unchecked")
	Iterator<String> y = ((HashMap<String, Object>) i.next()).keySet().iterator();
	while(y.hasNext()){
		sendChanges((String)y.next());
	}
			}
}
}
