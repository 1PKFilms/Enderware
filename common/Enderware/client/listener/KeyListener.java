package Enderware.client.listener;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import Enderware.client.gui.GuiAdminScreen;

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
	    
        if(Minecraft.getMinecraft().currentScreen == null &&  tickEnd&& kb.keyCode == temp[0].keyCode&&Minecraft.getMinecraft().theWorld.isRemote){
            
            Minecraft.getMinecraft().displayGuiScreen(new GuiAdminScreen());
        }
	}
	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
	  
	}
	@Override
	public EnumSet<TickType> ticks() {
		// TODO Auto-generated method stub
		return EnumSet.of(TickType.CLIENT);
	}

	
}
