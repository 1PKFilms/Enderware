package Enderware.client.gui.Adminscreen;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import cpw.mods.fml.common.network.PacketDispatcher;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.packet.Packet250CustomPayload;
import Enderware.client.gui.GuiConfigbuttonScrollpane;
import Enderware.client.gui.GuiScrollPane;
import Enderware.config.ModSetting;
import Enderware.network.Packehandler;


public class GuiModSettings extends GuiScreen{
    public GuiScrollPane pane ;
    public GuiScrollPane pane2 ;
    public GuiConfigbuttonScrollpane pane3;

@Override
public void initGui() {
   

    String[] temp = {"lol"};
   temp = (String[]) ModSetting.AllSettings.keySet().toArray(temp);
   Arrays.sort(temp);


    pane = new GuiScrollPane(width/4, height-40, 20, height-20, 10, this, new ArrayList<String>(Arrays.asList(temp)), null);
    String[] temp2 =  {"Select a File!"};
  
   
    pane2 = new GuiScrollPane(width/4, height-40, 20, height-20, 15+width/4, this, new ArrayList<String>(Arrays.asList(temp2)), null);
    HashMap<String, String> temp3 = new HashMap<String,String>();
    temp3.put("Select a Category!", "");
    pane3 = new GuiConfigbuttonScrollpane(width/2-25, height-40, 20, height-20, 20+width/2,temp3);

}
private int lastselected = 0;
private int lastselected2 = 0;

@Override
public void drawScreen(int par1, int par2, float par3) {
   this.drawBackground(0);
   
    if(lastselected != pane.selcted){
        onGuiClosed();
        String[] temp = {"lol"};
        temp =  ModSetting.AllSettings.get(pane.points.get(pane.selcted)).categorys.keySet().toArray(temp);

        pane2.setEntrys( new ArrayList<String>(Arrays.asList(temp)));
        
        
    }
   if(lastselected2 != pane2.selcted){
       onGuiClosed();
        lastselected2 = pane2.selcted;
        String[] temp = {"lol"};
        temp =  ModSetting.AllSettings.get(pane.points.get(pane.selcted)).categorys.get(pane2.points.get(pane2.selcted)).keySet().toArray(temp);
        HashMap<String, String> configs = new HashMap<String,String>();
        int i = 0;
        while(i< temp.length){
            configs.put(temp[i], ModSetting.AllSettings.get(pane.points.get(pane.selcted)).categorys.get(pane2.points.get(pane2.selcted)).get(temp[i]));
            i++;
        }
       
        pane3.setEntrys(configs);
        
    }
    pane.drawScreen(par1, par2, par3);
    pane2.drawScreen(par1, par2, par3);
    pane3.drawScreen(par1, par2, par3);
    
    
}
@Override
protected void keyTyped(char par1, int par2) {
    pane3.keyTyped(par1, par2);
    super.keyTyped(par1, par2);
}
@Override
protected void actionPerformed(GuiButton par1GuiButton) {
    pane.actionPerformed(par1GuiButton);
    pane2.actionPerformed(par1GuiButton);
    pane3.actionPerformed(par1GuiButton);
    super.actionPerformed(par1GuiButton);
}
@Override
public void onGuiClosed() {
    Iterator<String> entryi;
    String filetemp;
    String categorytemp;
    try{
        filetemp =  pane.points.get(pane.selcted);
        categorytemp = pane2.points.get(pane2.selcted);
    
           
    entryi =  ModSetting.AllSettings.get(filetemp).categorys.get(categorytemp).keySet().iterator();
           }catch(Throwable e){
               return;
           }
    while (entryi.hasNext()) {
                String entritemp = entryi.next();
               if(ModSetting.AllSettings.get(filetemp).categorys.get(categorytemp).get(entritemp).equals(pane3.values.get(entritemp)))continue;
               try{


                   


           
               ByteArrayOutputStream bos = new ByteArrayOutputStream((filetemp.length()+categorytemp.length()+entritemp.length()
                       )*2);
               DataOutputStream outputStream = new DataOutputStream(bos);
               
               Packet250CustomPayload packet = new Packet250CustomPayload();
           

              
               outputStream.writeUTF(filetemp);
             
               outputStream.writeUTF(categorytemp);
               
               outputStream.writeUTF(entritemp);
               
           
               outputStream.writeUTF(pane3.values.get(entritemp));
              
               packet.channel = "ModSettings";
               packet.data = bos.toByteArray();
               packet.length = bos.size();
              PacketDispatcher.sendPacketToServer(packet);
              ModSetting.AllSettings.get(filetemp).categorys.get(categorytemp).put(entritemp,pane3.values.get(entritemp));
               new Packehandler().onPacketData(null, packet, null);
               }catch(Throwable e){
                  
                 
               }
       }
        
    

 
    super.onGuiClosed();
}

}
