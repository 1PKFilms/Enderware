package Enderware.listener;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import Enderware.config.SettingsAdapterServer;
import cpw.mods.fml.common.IPlayerTracker;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class PlayerTracker implements IPlayerTracker{

	
	@Override
	public void onPlayerLogin(EntityPlayer player) {
	    Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = "shoutdown";
        packet.data = null;
        packet.length =0;
        PacketDispatcher.sendPacketToPlayer(packet, (Player) player);
		 ((SettingsAdapterServer)SettingsAdapterServer.instance()).updateAll(player);
		 ((SettingsAdapterServer)SettingsAdapterServer.instance()).updateAllMods(player);
		 
		
	}

	@Override
	public void onPlayerLogout(EntityPlayer player) {

      
        	
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
