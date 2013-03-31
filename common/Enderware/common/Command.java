package Enderware.common;

import java.util.List;

import net.minecraft.command.ICommandSender;
import Enderware.api.IEnderCommand;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

public class Command{
	  public static void initCommands(FMLServerStartingEvent event) {
		 
	        event.registerServerCommand(new TestCommand() );
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
