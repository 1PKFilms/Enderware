package Enderware.protection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Level;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.PropertyManager;
import Enderware.network.Packehandler;
import Enderware.permissions.PermissionManager;

import com.google.common.annotations.Beta;

@Beta
public class ProtectionManger {
public static ProtectionManger instance = new ProtectionManger();

private ProtectionManger(){
    try {
       new File(MinecraftServer.getServer().getFile("").getCanonicalPath()+File.separatorChar+"protections").mkdirs();
       new File(MinecraftServer.getServer().getFile("").getCanonicalPath()+File.separatorChar+"protections"+File.separatorChar+"blocks").mkdirs();
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

  

}
public boolean canPlaceOrDig(EntityPlayer player,int xCoord,int yCoord,int zCoord,int dimension){
    File blockfile = null;
    try {
        blockfile = new File(MinecraftServer.getServer().getFile("").getCanonicalPath()+File.separatorChar+"protections"+File.separatorChar+"blocks"+File.separatorChar+""+dimension+""+xCoord+""+yCoord+""+zCoord+".block");
    } catch (IOException e1) {
        
        e1.printStackTrace();
        return false;
    }
    if(!blockfile.exists()){
        System.out.println("File "+blockfile+" is missing (THIS IS NOT ERROR IT'S A PART OF MY SYSTEM)");
        return true;
    }
        BufferedReader reader = null;
        int id = 0;
            try {
                reader = new BufferedReader(new FileReader(blockfile));
                id = Integer.valueOf(reader.readLine());
            } catch (Exception e) {

            MinecraftServer.logger.log(Level.WARNING, "An execption was throwen while trying to open a file stream to:"+dimension+xCoord+yCoord+zCoord+".block .That happend because the player: "+player.username+" want to break the blog at : x:"+xCoord+" y: "+yCoord+" z: "+zCoord+" the filefor the block existst but Enderware can't open a stream to it the could be a hack or a bug be careful with that player but in most kinds it's a silly", e);
         return Packehandler.isAdmin(player.username);
            }finally{
                if(reader != null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                       
                    }
                }
            
            }
    File protectionfile = null;
    try {
        protectionfile = new File(MinecraftServer.getServer().getFile("").getCanonicalPath()+File.separatorChar+"protections"+File.separatorChar+id+".protection");
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    if(!protectionfile.exists()){
        MinecraftServer.logger.log(Level.WARNING, "The Block file for the block at: x"+xCoord+" y"+dimension+yCoord+" z"+zCoord+" exist but the protection file is missing ");
        return Packehandler.isAdmin(player.username);
    }
    PropertyManager protection = new PropertyManager(protectionfile);
    if(protection.getProperty("Owner", "missing!").equalsIgnoreCase("missing!")||protection.getProperty("Owner", "missing!").equalsIgnoreCase(player.username)){
        System.out.println("Owner");
        return true;
    }
    if(protection.getIntProperty("minepermissionlevel", Integer.MAX_VALUE) <= PermissionManager.instance.getPlayerPermissionLevel(player.username)){
        System.out.println("permissions");
        return true;
    }
    
  
    String[] friends = protection.getProperty("Friends:", "alone :(").split(",");
    Iterator<String> friendsi = Arrays.asList(friends).iterator();
    while(friendsi.hasNext()){
        if(friendsi.next().equalsIgnoreCase(player.username)){
            System.out.println("friend"); 

            return true;
        }
    }
    return Packehandler.isAdmin(player.username);
}

public boolean canUse(EntityPlayer player,int xCoord,int yCoord,int zCoord,int dimension){
    File blockfile = null;
    try {
        blockfile = new File(MinecraftServer.getServer().getFile("").getCanonicalPath()+File.separatorChar+"protections"+File.separatorChar+"blocks"+File.separatorChar+""+dimension+""+xCoord+""+yCoord+""+zCoord+".block");
    } catch (IOException e1) {
        
        e1.printStackTrace();
        return false;
    }
    if(!blockfile.exists()){
        System.out.println("File "+blockfile+" is missing (THIS IS NOT ERROR IT'S A PART OF MY SYSTEM)");
        return true;
    }
        BufferedReader reader = null;
        int id = 0;
            try {
                reader = new BufferedReader(new FileReader(blockfile));
                id = Integer.valueOf(reader.readLine());
            } catch (Exception e) {

            MinecraftServer.logger.log(Level.WARNING, "An execption was throwen while trying to open a file stream to:"+dimension+xCoord+yCoord+zCoord+".block .That happend because the player: "+player.username+" want to break the blog at : x:"+xCoord+" y: "+yCoord+" z: "+zCoord+" the filefor the block existst but Enderware can't open a stream to it the could be a hack or a bug be careful with that player but in most kinds it's a silly", e);
         return Packehandler.isAdmin(player.username);
            }finally{
                if(reader != null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                       
                    }
                }
            
            }
    File protectionfile = null;
    try {
        protectionfile = new File(MinecraftServer.getServer().getFile("").getCanonicalPath()+File.separatorChar+"protections"+File.separatorChar+id+".protection");
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    if(!protectionfile.exists()){
        MinecraftServer.logger.log(Level.WARNING, "The Block file for the block at: x"+xCoord+" y"+dimension+yCoord+" z"+zCoord+" exist but the protection file is missing ");
        return Packehandler.isAdmin(player.username);
    }
    PropertyManager protection = new PropertyManager(protectionfile);
    if(protection.getProperty("Owner", "missing!").equalsIgnoreCase("missing!")||protection.getProperty("Owner", "missing!").equalsIgnoreCase(player.username)){
        System.out.println("Owner");
        return true;
    }
    if(protection.getIntProperty("minepermissionlevel", Integer.MAX_VALUE) <= PermissionManager.instance.getPlayerPermissionLevel(player.username)){
        System.out.println("permissions");
        return true;
    }
    
  
    String[] friends = protection.getProperty("Friends:", "alone :(").split(",");
    Iterator<String> friendsi = Arrays.asList(friends).iterator();
    while(friendsi.hasNext()){
        if(friendsi.next().equalsIgnoreCase(player.username)){
            System.out.println("friend"); 

            return true;
        }
    }
    return protection.getBooleanProperty("Allow_Open_interfaces", false);
}

public boolean protect(EntityPlayer player  ,double LeftBackUpx,double LeftBackUpy,double LeftBackUpz,double  RightFrontDownx,double  RightFrontDowny,double RightFrontDownz,int dimension,boolean allowUse,int minepermissionLevel,String[] friends){
    LeftBackUpx = (double)((int)LeftBackUpx);
    LeftBackUpy = (double)((int)LeftBackUpy);
    LeftBackUpz = (double)((int)LeftBackUpz);
    RightFrontDownx = (double)((int)RightFrontDownx);
    RightFrontDowny = (double)((int)RightFrontDowny);
    RightFrontDownz = (double)((int)RightFrontDownz);
   

        if(LeftBackUpx > RightFrontDownx){
            double lol = RightFrontDownx;
            RightFrontDownx = LeftBackUpx;
            LeftBackUpx =lol;
        }
        if(LeftBackUpy > RightFrontDowny){
            double lol = RightFrontDowny;
            RightFrontDowny = LeftBackUpy;
            LeftBackUpy =lol;
        }
        if(LeftBackUpz > RightFrontDownz){
            double lol = RightFrontDownz;
            RightFrontDownz = LeftBackUpz;
            LeftBackUpz =lol;
        }
        int tempx = (int) LeftBackUpx;
        int tempy = (int) LeftBackUpy;
        int tempz = (int) LeftBackUpz;
        File temp = null;
        BufferedWriter writer = null;
        int id = nextid();
        if(id == -1)return false;
        while(tempx <= RightFrontDownx){
            tempy = (int) LeftBackUpy;
            while(tempy <= RightFrontDowny){
                tempz = (int) LeftBackUpz;
                while(tempz <= RightFrontDownz){
                    try {

                        temp = new File(MinecraftServer.getServer().getFile("").getCanonicalPath()+File.separatorChar+"protections"+File.separatorChar+"blocks"+File.separatorChar+dimension+""+tempx+""+tempy+""+tempz+".block");
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    if(temp.exists()){
                        if(tempz == LeftBackUpz){
                            tempy--;
                            tempz = (int) RightFrontDownz;
                        }else{
                            tempz--;
                        }
                        boolean firsty = true;
                        boolean firstz = true;
                        while(tempx>= LeftBackUpx){
                            if(!firsty)
                            tempy = (int) RightFrontDowny;
                            while(tempy >= LeftBackUpy){
                                if(!firstz)
                                    tempz = (int) RightFrontDownz;
                                if(firstz)
                                firsty =false;
                                    while(tempz >= LeftBackUpz){
                                        if(firstz)
                                        firstz =false;
                                        try {
                                            temp = new File(MinecraftServer.getServer().getFile("").getCanonicalPath()+File.separatorChar+"protections"+File.separatorChar+"blocks"+File.separatorChar+dimension+tempx+""+tempy+""+tempz+".block");
                                        } catch (Exception e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                        temp.delete();
                                        tempz--;
                                    }
                                    tempy--;
                            }
                            tempx--;
                        }
                        return false;
                    }
                    //System.out.println("[Enderware] protecting block: "+temp);
                    try {
                        temp.createNewFile();
                        
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    try{
                      writer = new BufferedWriter(new FileWriter(temp));
                      writer.write(""+id);
                    }catch(Exception e){
                        
                    }finally{
                        if(writer != null){
                            try {
                                writer.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                               
                            }
                        }
                    
                    }
                    tempz++;
                }
                tempy++;
            }
            tempx++;
        }
       
        try {
            PropertyManager manager = new PropertyManager(new File(MinecraftServer.getServer().getFile("").getCanonicalPath()+File.separatorChar+File.separatorChar+"protections"+File.separatorChar+id+".protection"));
            manager.getProperty("Owner", player.username);
            manager.getIntProperty("minepermissionlevel", minepermissionLevel);
            manager.getBooleanProperty("Allow_Open_interfaces",allowUse);
            String friendsasstring = "";
            int i = 0;
            while(i< friends.length){
                if(i+1 < friends.length)
                friendsasstring = friendsasstring+friends[i]+",";
                else{
                    friendsasstring = friendsasstring+friends[i];
                }
                i++;
            }
            manager.getProperty("Friends", friendsasstring);
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
}
public boolean canAnythingBuild(int x,int y,int z,int dimension){
    File blockfile = null;
    try {
        blockfile = new File(MinecraftServer.getServer().getFile("").getCanonicalPath()+File.separatorChar+"protections"+File.separatorChar+"blocks"+File.separatorChar+""+dimension+""+x+""+y+""+z+".block");
    } catch (IOException e1) {
        
        e1.printStackTrace();
        return false;
    }
    if(!blockfile.exists()){

        return true;
    }
    return false;
}
public int nextid(){
    BufferedReader reader = null;
    BufferedWriter writer = null;
    int id = -1;
       try{
        File file = new File(MinecraftServer.getServer().getFile("").getCanonicalPath()+File.separatorChar+"protections"+File.separatorChar+"id");
        if(file.exists()){
            reader = new BufferedReader(new FileReader(file));
          id = Integer.valueOf(Integer.valueOf(reader.readLine()));
          try{
              reader.close();
              reader = null;
          }catch(Exception e){
              e.printStackTrace();
          }
        }else{
            
            id = 0;
        }
        file.createNewFile();
        writer = new BufferedWriter(new FileWriter(file));
        writer.write(""+(++id));
       

       
        
        
        
    }catch(Exception e){
        
    }
       finally{
           if(writer != null){
               try {
                   writer.close();
               } catch (IOException e) {
                   e.printStackTrace();
                  
               }
           }
           if(reader != null){
               try {
                   reader.close();
               } catch (IOException e) {
                   e.printStackTrace();
                  
               }
           }
       
       }
    return id;
}

}
