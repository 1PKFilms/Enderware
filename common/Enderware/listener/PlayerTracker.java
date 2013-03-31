package Enderware.listener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Iterator;

import Enderware.config.SettingsAdapterServer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IPlayerTracker;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

public class PlayerTracker implements IPlayerTracker{

	@SuppressWarnings("rawtypes")
	@Override
	public void onPlayerLogin(EntityPlayer player) {
		 ((SettingsAdapterServer)SettingsAdapterServer.instance()).updateAll();
 	   ByteArrayOutputStream bos = new ByteArrayOutputStream(1);
        DataOutputStream outputStream = new DataOutputStream(bos);
        try {
        	Iterator i = MinecraftServer.getServerConfigurationManager(MinecraftServer.getServer()).getOps().iterator();
        	boolean admin = false;
        	while(i.hasNext()&&!admin){
        	 admin = admin||i.next() ==player.username;
        	}
                outputStream.writeBoolean(admin);
               ;
        } catch (Exception ex) {
                ex.printStackTrace();
        }
       Packet250CustomPayload packet = new Packet250CustomPayload();
       packet.channel = "Admin";
       packet.data = bos.toByteArray();
       packet.length = bos.size();
       
       Side side = FMLCommonHandler.instance().getEffectiveSide();
       if (side == Side.SERVER&&packet.data!= null) 
         
     	  PacketDispatcher.sendPacketToPlayer(packet, (Player)player);
		
	}

	@Override
	public void onPlayerLogout(EntityPlayer player) {
		 
      
        	   ByteArrayOutputStream bos = new ByteArrayOutputStream(1);
               DataOutputStream outputStream = new DataOutputStream(bos);
               try {
                       outputStream.writeBoolean(false);
                      ;
               } catch (Exception ex) {
                       ex.printStackTrace();
               }
              Packet250CustomPayload packet = new Packet250CustomPayload();
              packet.channel = "Admin";
              packet.data = bos.toByteArray();
              packet.length = bos.size();
              
              Side side = FMLCommonHandler.instance().getEffectiveSide();
              if (side == Side.SERVER) 
                
            	  PacketDispatcher.sendPacketToPlayer(packet, (Player)player);
              
           }
		
	

	@Override
	public void onPlayerChangedDimension(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayerRespawn(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

}
