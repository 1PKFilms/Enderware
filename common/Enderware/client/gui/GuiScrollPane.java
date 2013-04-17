package Enderware.client.gui;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.src.ModLoader;
import cpw.mods.fml.client.GuiScrollingList;

public class GuiScrollPane extends GuiScrollingList{
    public ArrayList<String> points = new ArrayList<String>();
    public int selcted = 0;
    public GuiScreen screen;
    public GuiScreen screen2;
    public GuiScrollPane(int width, int height, int top,
            int bottom, int left, GuiScreen screen,ArrayList<String> names,GuiScreen nextscreen) {
        super(ModLoader.getMinecraftInstance(), width, height, top, bottom, left, RenderManager.instance.getFontRenderer().FONT_HEIGHT*2);
        points.addAll(names);
        this.screen2 = nextscreen;
        this.screen = screen;
    }

    @Override
    protected int getSize() {
        return points.size();
    }

    @Override
    protected void elementClicked(int index, boolean doubleClick) {
        if(doubleClick&&screen2 != null)Minecraft.getMinecraft().displayGuiScreen(screen2);
        selcted = index;
    }
    
    public void setEntrys(ArrayList<String> points) {
        this.points = points;
    }
    @Override
    protected boolean isSelected(int index) {
        return index == selcted;
    }
    
    @Override
    protected void drawBackground() {
        
    }
    
    @Override
    protected void drawSlot(int var1, int var2, int var3, int var4,
            Tessellator var5) {
        
    
        if(points.get(var1) == null||points.get(var1) == ""||points.get(var1) == " "||points.get(var1) == "null")return;
        RenderManager.instance.getFontRenderer().drawString(RenderManager.instance.getFontRenderer().trimStringToWidth(points.get(var1), listWidth - 10), this.left + 3 , var3 + 2, 0xFFFFFF);
        
    }
    
}