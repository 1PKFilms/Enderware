package Enderware.client.gui;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.src.ModLoader;
import cpw.mods.fml.client.GuiScrollingList;


public class GuiConfigbuttonScrollpane extends GuiScrollingList{
public ArrayList<String> names = new ArrayList<String>();
public HashMap<String, String> values = new HashMap<String, String>();
public int selected = 0;
    public GuiConfigbuttonScrollpane( int width, int height,
            int top, int bottom, int left,HashMap<String, String> configs) {
        super(ModLoader.getMinecraftInstance(), width, height, top, bottom, left, RenderManager.instance.getFontRenderer().FONT_HEIGHT*2);
 
        names.addAll(configs.keySet());
        this.values = configs;
    }

    @Override
    protected int getSize() {
        return names.size();
    }
    public boolean islistening = false;
    @Override
    protected void elementClicked(int index, boolean doubleClick) {
        islistening = true;
        this.selected = index;
    }

    @Override
    protected boolean isSelected(int index) {
        return index == selected;
    }
    public void keyTyped(char par1, int par2) {
        if(!islistening)return;
      
        if(par2 == org.lwjgl.input.Keyboard.KEY_BACK || par2 == org.lwjgl.input.Keyboard.KEY_RETURN||par2 == Keyboard.KEY_TAB){
            switch(par2){
                case org.lwjgl.input.Keyboard.KEY_BACK:
                    if(values.get(names.get(selected)).length() <=0)return;
                    values.put(names.get(selected),values.get(names.get(selected)).substring(0, values.get(names.get(selected)).length()-1));
                    break;
                case org.lwjgl.input.Keyboard.KEY_RETURN :
                    islistening= false;
                    break;
                case Keyboard.KEY_TAB :
                    values.put(names.get(selected),values.get(names.get(selected))+"     ");
                    break;
                    
            }
            
        }else if(Character.isAlphabetic(par1)||Character.isWhitespace(par1)||Character.isLetterOrDigit(par1)){
            values.put(names.get(selected),values.get(names.get(selected))+par1);
        }
    }
    @Override
    protected void drawBackground() {

    }
    private long i = 0;
    private boolean  ison = false;
    @Override
    protected void drawSlot(int var1, int var2, int var3, int var4,
            Tessellator var5) {
        if(isSelected(var1)&&islistening){
            RenderManager.instance.getFontRenderer().drawString(values.get(names.get(var1)), this.left +RenderManager.instance.getFontRenderer().getStringWidth("\u00a7l"+names.get(var1)+": \u00a7r")+1  , var3 + 2, 0xFFFFFF);
            if(ison){
                int temp = this.left /*+RenderManager.instance.getFontRenderer().getStringWidth("\u00a7l"+names.get(var1)*/+(RenderManager.instance.getFontRenderer().getStringWidth(values.get(names.get(selected))))+2;
                RenderManager.instance.getFontRenderer().drawString("_", RenderManager.instance.getFontRenderer().getStringWidth("\u00a7l"+names.get(var1)+": \u00a7r")+temp  , var3 + 2, 0xFFFFFF);
                if(System.currentTimeMillis()-i >= 500){
                    ison = false;
                    i = System.currentTimeMillis();
                }
            }
            else if(System.currentTimeMillis()-i >= 500){
                int temp = this.left /*+RenderManager.instance.getFontRenderer().getStringWidth("\u00a7l"+names.get(var1)*/+(RenderManager.instance.getFontRenderer().getStringWidth(values.get(names.get(selected))))+2;
                 RenderManager.instance.getFontRenderer().drawString("_", RenderManager.instance.getFontRenderer().getStringWidth("\u00a7l"+names.get(var1)+": \u00a7r")+temp  , var3 + 2, 0xFFFFFF);
                 i = System.currentTimeMillis();
                 ison = true;
             }
         }else{
             if(values.get(names.get(var1)).equals("false"))RenderManager.instance.getFontRenderer().drawString(values.get(names.get(var1)), this.left +RenderManager.instance.getFontRenderer().getStringWidth("\u00a7l"+names.get(var1)+": \u00a7r")+1  , var3 + 2, 0xFF5555);
             else if(values.get(names.get(var1)).equals("true"))RenderManager.instance.getFontRenderer().drawString(values.get(names.get(var1)), this.left +RenderManager.instance.getFontRenderer().getStringWidth("\u00a7l"+names.get(var1)+": \u00a7r")+1  , var3 + 2, 0x55FF5);
             else RenderManager.instance.getFontRenderer().drawString(values.get(names.get(var1)), this.left +RenderManager.instance.getFontRenderer().getStringWidth("\u00a7l"+names.get(var1)+": \u00a7r")+1  , var3 + 2, 0x555555);
         }
        RenderManager.instance.getFontRenderer().drawString("\u00a7l"+names.get(var1)+":\u00a7r ", this.left + 1 , var3 + 2, 0xFFFFFF);
        
    
     
    }

    public void setEntrys(HashMap<String, String> configs) {
        names.removeAll(names);
        names.addAll(configs.keySet());
     
        this.values = configs;
    }


}
