package Enderware.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;


public class GuiConfigButton extends GuiButton{
public String current;
    public GuiConfigButton(int par1, int par2, int par3, int par4, int par5,
            String par6Str , String config) {
        super(par1, par2, par3, par4, par5, par6Str);
        current = config;
        // TODO Auto-generated constructor stub
    }
    public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        if (this.drawButton)
        {
            FontRenderer var4 = par1Minecraft.fontRenderer;
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, par1Minecraft.renderEngine.getTexture("/gui/gui.png"));
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
            int var5 = this.getHoverState(this.field_82253_i);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + var5 * 20, this.width / 2, this.height);
            this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + var5 * 20, this.width / 2, this.height);
            this.mouseDragged(par1Minecraft, par2, par3);
            int var6 = 14737632;

            if (!this.enabled)
            {
                var6 = -6250336;
            }
            else if (this.field_82253_i)
            {
                var6 = 16777120;
            }

            
            this.drawString(var4, "\u00a7l"+this.displayString+":\u00a7r ", this.xPosition + this.width / 10, this.yPosition + (this.height - 8) / 2, var6);
            this.drawString(var4, this.current, this.xPosition + this.width / 10+var4.getStringWidth("\u00a7l"+this.displayString+":\u00a7r "), this.yPosition + (this.height - 8) / 2, var6);
        }
    }

}
