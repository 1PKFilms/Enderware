package Enderware.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiRestart extends GuiScreen{
    public String reason = "";
    public GuiRestart(String reason){
        this.reason = reason;
    }
@Override
public void drawScreen(int par1, int par2, float par3) {
  
    super.drawScreen(par1, par2, par3);
    drawBackground(0);

    fontRenderer.drawStringWithShadow("\u00a7lStopping Because: "+reason, this.width/2-fontRenderer.getStringWidth("\u00a7lStopping Because: "+reason)/2, height/2-fontRenderer.FONT_HEIGHT, 0xFF5555);

    fontRenderer.drawStringWithShadow("This is a controlled Shoutdown", this.width/2-fontRenderer.getStringWidth("This is a controlled Shoutdown")/2, height/2+fontRenderer.FONT_HEIGHT/4, 0xFF5555);
    
}
@SideOnly(Side.CLIENT)
//Just for CLient/Server compatily (without this method this won't work in the packet handler 'cause the packet handler must be compiled for the Server AND the Client and You can't import GUIScreen in a Server compiled class
public static void displayGuiRestart(String reason){
    Minecraft.getMinecraft().displayGuiScreen(new GuiRestart(reason));
}
}
