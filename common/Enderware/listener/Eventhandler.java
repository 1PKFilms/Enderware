package Enderware.listener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import com.pahimar.ee3.event.ActionRequestEvent;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.PlayerSelector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet3Chat;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import Enderware.api.IEnderCommand;
import Enderware.api.events.setBlockByPlayerEvent;
import Enderware.api.events.setBlockEvent;
import Enderware.config.Config;
import Enderware.permissions.PermissionManager;
import Enderware.protection.ProtectionManger;

public class Eventhandler {
	@ForgeSubscribe
public void onChat(ServerChatEvent event){ 
		boolean admin = false;
				Iterator<?> it = MinecraftServer.getServer().getConfigurationManager().getOps().iterator();
		while(!admin&&it.hasNext()){
			admin = admin||it.next().toString().equalsIgnoreCase(event.player.username);
		}
		String name = event.player.username;
		String message = event.message;
	if(admin){
		 message = "\u00a7e"+event.message;
		if((Boolean) Config.configs.get("coloredAdminnames")){
    		
    		name = "";
    		
    		
    		int i = 0;
    		Random rand = new Random();
    		while(i < event.player.username.length()){
    			name = name+"\u00a7"+rand.nextInt(9)+event.player.username.charAt(i);
    			i++;
    			}
    		
		}

		
		
	}

	if((Boolean) Config.configs.get("Chatcategories")){
		 event.line= "\u00a7f<"+"\u00a7"+PermissionManager.instance.getPlayerGroup(event.player.username)+"\u00a78|\u00a7f"+name+"\u00a7f>" + "\u00a7f"+message;
	}else{
		event.line= "\u00a7f<"+name+"\u00a7f>" + "\u00a7f"+event.message;
	}
	if(((Boolean)(Config.configs.get("ChatColors"))&&PermissionManager.instance.hasPlayerPermission(event.player.username, "Colors"))){
		message = "";
		
		
		int i = 0;
		
		while(i < event.line.length()){
			if(event.line.charAt(i) == '#'&&event.line.length() > i) {
				
				message = message+"\u00a7"+event.line.charAt(++i);
			}else{
			message = message+event.line.charAt(i);
			}
			i++;
			}
		event.line = message;
	}
	event.setCanceled(true);
	
	List<?> heard = sendCHatPackages(event.player.posX,event.player.posY, event.player.posZ, Double.valueOf((""+Config.configs.get("ChatRange"))), event.player.dimension, new Packet3Chat(event.line));
	if(heard.size() -1 == 0){
		event.player.sendChatToPlayer("\u00a79[EnderWare]\u00a76No one heard you!");
		return;
	}
	int i = 0;
	String hear = "";
	if(heard.size() == 2){
	while(i<heard.size()){
		if(((EntityPlayer)heard.get(i)).username!= event.player.username){
			hear = hear+((EntityPlayer)heard.get(i)).username;
		}
		i++;
	}
	}else{
		while(i<heard.size()){
			if(((EntityPlayer)heard.get(i)).username!= event.player.username){
				hear = hear+((EntityPlayer)heard.get(i)).username+", ";
			}
			i++;
		}
	}
	if(heard.size() == 2){
	event.player.sendChatToPlayer("\u00a79[EnderWare]\u00a76"+hear+" heard you!");
	return;
	}
	event.player.sendChatToPlayer("\u00a79[EnderWare]\u00a76The players: "+hear+" heard you!");

	}

@ForgeSubscribe
public void onCommand(CommandEvent event){
	if(event.command.getCommandName() == "op" && event.command.canCommandSenderUseCommand(event.sender))PermissionManager.instance.setPlayerGroup(event.parameters[0], "Admin", 10);
	if(event.command.getCommandName() == "deop"&& event.command.canCommandSenderUseCommand(event.sender))PermissionManager.instance.setPlayerGroup(event.parameters[0], "user", 0);
			if(!(event.command instanceof IEnderCommand))return;
	event.setCanceled(true);
	if(!(PermissionManager.instance.hasPlayerPermission(event.sender.getCommandSenderName(), ((IEnderCommand)event.command).getPermisssion()))){
		event.sender.sendChatToPlayer("\u00a74You are not permitted to use that Command");
		MinecraftServer.logger.log(Level.INFO, event.sender.getCommandSenderName()+" try to use the "+event.command.getCommandName()+" command "+" without Permissions ( group = "+PermissionManager.instance.getPlayerGroup(event.sender.getCommandSenderName()));
		return;
	}
	int var6 = 0;
	   if (event.command== null)
       {
          var6=-1;
       }
       else	
       {
           for (int var3 = 0; var3 < event.parameters.length; ++var3)
           {
               if (event.command.isUsernameIndex(var3) && PlayerSelector.matchesMultiplePlayers(event.parameters[var3]))
               {
            	   var6= var3;
               }
           }

           var6= -1;
       }
	   String[] var3 = event.parameters;
	  ICommand var5 = event.command;
	  ICommandSender par1ICommandSender = event.sender;
	   
	 if (var6 > -1)
     {
         EntityPlayerMP[] var7 = PlayerSelector.matchPlayers(event.sender, event.parameters[var6]);
         String var8 = var3[var6];
         EntityPlayerMP[] var9 = var7;
         int var10 = var7.length;

         for (int var11 = 0; var11 < var10; ++var11)
         {
             EntityPlayerMP var12 = var9[var11];
             var3[var6] = var12.getEntityName();
             
             try
             {
                 var5.processCommand(par1ICommandSender, var3);
             }
             catch (PlayerNotFoundException var14)
             {
                 par1ICommandSender.sendChatToPlayer("\u00a7c" + par1ICommandSender.translateString(var14.getMessage(), var14.getErrorOjbects()));
             }
         }

         var3[var6] = var8;
     }
     else
     {
         var5.processCommand(par1ICommandSender, var3);
     }
 }
 

	
    
public List<EntityPlayer> sendCHatPackages(double x, double y, double z, double maxdistance, int dimension, Packet par11Packet)
    {
    	List<EntityPlayer> return_ = new ArrayList<EntityPlayer>();
    	EntityPlayerMP target = null;
        for (int i = 0; i < MinecraftServer.getServerConfigurationManager(MinecraftServer.getServer()).playerEntityList.size(); ++i)
        {
            target = (EntityPlayerMP)MinecraftServer.getServerConfigurationManager(MinecraftServer.getServer()).playerEntityList.get(i);

            if (target.dimension == dimension)
            {
                double xdistance = x - target.posX;
                double ydistance = y - target.posY;
                double zdistance = z - target.posZ;

                if ( xdistance *  xdistance +  ydistance * ydistance + zdistance * zdistance < maxdistance* maxdistance)
                {
                	return_.add(target);
                    target.playerNetServerHandler.sendPacketToPlayer(par11Packet);
                }
            }
        }
        return return_;
    }
@ForgeSubscribe
public void onHarvest(net.minecraftforge.event.entity.player.PlayerInteractEvent event){
    int x = event.x;
    int y = event.y;
    int z = event.z;
    if(x < 0)x++;
    if(z < 0)z++;
    if(event.action.compareTo(Action.RIGHT_CLICK_BLOCK) == 0){

      switch (event.face) {
        case 0:
            y--;
            break;
        case 1:
            y++;
            break;
        case 2:
            z--;
            break;
        case 3:
            z++;
            break;
        case 4:
            x--;
            break;
        case 5:
            x++;
            break;
        default:
            break;
    }
    }

 /*if((event.action.compareTo(Action.LEFT_CLICK_BLOCK) == 0)&&!ProtectionManger.instance.canPlaceOrDig(event.entityPlayer, x, event.y, event.z, event.entityPlayer.dimension)){
     event.setCanceled(true);
   
     
 }*/
 if(event.action.compareTo(Action.RIGHT_CLICK_BLOCK) == 0&&!ProtectionManger.instance.canUse(event.entityPlayer, x, y, z, event.entityPlayer.dimension)){
     event.setCanceled(true);
     event.entityPlayer.sendChatToPlayer("\u00a74You are not allowed to Interact with blocks here!");
 }
}
@ForgeSubscribe
public void onBlockSet(setBlockEvent event){
    
   
    event.setCanceled( !ProtectionManger.instance.canAnythingBuild(event.x, event.y, event.z, event.dimension));

  
    

}
@ForgeSubscribe
public void onBlockSetByPlayer(setBlockByPlayerEvent event){
   if( !ProtectionManger.instance.canPlaceOrDig(event.player, event.x, event.y, event.z, event.dimension)){
       event.player.sendChatToPlayer("\u00a74You are not allowed to Build here!");
       event.setCanceled(true);
   }

  
    

}
public void onEE3Action(ActionRequestEvent event){
    if(event.entityPlayer != null){
        if(!ProtectionManger.instance.canPlaceOrDig(event.entityPlayer, event.x, event.y, event.z, event.entityPlayer.dimension)){
            event.setCanceled(true);
            event.entityPlayer.sendChatToPlayer("\u00a74You are not allowed to practis magic here!");
        }
    }
}
} 
