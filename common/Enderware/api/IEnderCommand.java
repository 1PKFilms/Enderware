package Enderware.api;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;


public abstract class IEnderCommand extends CommandBase{
public abstract String getPermisssion();
@Override
	public final boolean canCommandSenderUseCommand(ICommandSender var1) {
		// TODO Auto-generated method stub
		return true;
	}
}
