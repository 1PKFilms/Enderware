package Enderware.client.listener;

import java.util.EnumSet;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;

public class KeyListener extends KeyHandler{
	static KeyBinding[] temp = {new KeyBinding("admin Config", Keyboard.KEY_C)};
	static boolean[] lol = {false};
	public KeyListener() {
		
		super(temp, lol);
		// TODO Auto-generated constructor stub
	}
	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return "Enderware KeyBindings";
	}
	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb,
			boolean tickEnd, boolean isRepeat) {
		
	}
	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
		if(tickEnd&&FMLClientHandler.instance().getClient().currentScreen== null&& kb.keyCode == temp[0].keyCode){
			
		}
	}
	@Override
	public EnumSet<TickType> ticks() {
		// TODO Auto-generated method stub
		return null;
	}

}
