package Enderware.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import Enderware.client.ClientProxy;
import Enderware.client.config.SettingsAdabterClient;
import Enderware.config.SettingsAdapterServer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

public class Packehandler implements IPacketHandler{

	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packet, Player player) {
		if(packet.channel.equalsIgnoreCase("Admin")&&FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT){
			 DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
			 try {
				 ClientProxy.ShowOpScreen = inputStream.readBoolean();
				
			
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 return;
		}
		
		   Side side = FMLCommonHandler.instance().getEffectiveSide();
		    if (side == Side.SERVER) 
		if(((SettingsAdapterServer) SettingsAdapterServer.instance()).handlePacket(packet)){return;}
		    System.out.println(packet.channel);
		    System.out.println(packet.data);
		    if(side == Side.CLIENT)
		if(((SettingsAdabterClient)SettingsAdabterClient.instance()).handlePacket(packet)){return;}
		
	}

}
