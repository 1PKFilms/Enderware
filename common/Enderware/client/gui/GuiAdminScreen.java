package Enderware.client.gui;

import Enderware.client.gui.Adminscreen.GuiModSettings;
import Enderware.client.gui.Adminscreen.GuiServerSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;


public class GuiAdminScreen extends GuiScreen{
    @SuppressWarnings("unchecked")
    @Override
        public void initGui() {
        this.controlList.add(new GuiButton(0, 10, 10, 100, 20, "ServerConfigs"));
        this.controlList.add(new GuiButton(1, 10, 35, 100, 20, "Permissions"));
        this.controlList.add(new GuiButton(2, 10, 60, 100, 20, "ModSettings"));
        
        
            super.initGui();
        }
@Override
public void drawScreen(int par1, int par2, float par3) {
    drawDefaultBackground();
    
    super.drawScreen(par1, par2, par3);
}
@Override
protected void actionPerformed(GuiButton par1GuiButton) {
    switch(par1GuiButton.id){
        case 0:
        Minecraft.getMinecraft().displayGuiScreen(new GuiServerSettings());
        break;
        case 2:
            Minecraft.getMinecraft().displayGuiScreen(new GuiModSettings());
            
    }
    super.actionPerformed(par1GuiButton);
}
}
