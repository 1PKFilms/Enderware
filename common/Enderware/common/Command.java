package Enderware.common;

import java.util.List;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import Enderware.api.IEnderCommand;
import Enderware.protection.ProtectionManger;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

public class Command{
	  public static void initCommands(FMLServerStartingEvent event) {
		 
	        event.registerServerCommand(new TestCommand() );
	        event.registerServerCommand(new ProtectCommand());
	    }
}
class TestCommand extends IEnderCommand{

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "test";
	}

	@Override
	public String getCommandUsage(ICommandSender var1) {
		// TODO Auto-generated method stub
		return "test";
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getCommandAliases() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void processCommand(ICommandSender var1, String[] var2) {
		var1.sendChatToPlayer("test");
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List addTabCompletionOptions(ICommandSender var1, String[] var2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUsernameIndex(int var1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getPermisssion() {
		// TODO Auto-generated method stub
		return "test";
	}
	
}
class ProtectCommand extends IEnderCommand{

    @Override
    public String getCommandName() {
        // TODO Auto-generated method stub
        return "protect";
    }

    @Override
    public String getCommandUsage(ICommandSender var1) {
        // TODO Auto-generated method stub
        return "protect";
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List getCommandAliases() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void processCommand(ICommandSender var1, String[] var2) {
     
        var1.sendChatToPlayer("Protecting that can take a while..");
        EntityPlayer player = getCommandSenderAsPlayer(var1);
        long start = System.currentTimeMillis();
        String[] friends = {};
        ProtectionManger.instance.protect(player, player.posX, player.posY, player.posZ, player.posX+10, player.posY-10, player.posZ+10, player.dimension, false, 10, friends);
        long end = System.currentTimeMillis();
        player.sendChatToPlayer("Protecting finished after "+(start-end)+"mil");
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List addTabCompletionOptions(ICommandSender var1, String[] var2) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isUsernameIndex(int var1) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int compareTo(Object o) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getPermisssion() {
        // TODO Auto-generated method stub
        return "make_Protections";
    }

}
