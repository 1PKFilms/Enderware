package Enderware.config;

import java.io.File;
import java.util.HashMap;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import net.minecraftforge.common.Property.Type;


public class ModSetting {
public static HashMap<String, ModSetting> AllSettings = new HashMap<String,ModSetting>();
public String name = "";
public Configuration config = null;
public File file = null;
public HashMap<String, HashMap<String,String>> categorys = new HashMap<String, HashMap<String,String>>();
public ModSetting(Configuration config,File file){
    this.file = file;
    this.name = file.getName();
    this.config = config;
    AllSettings.put(name, this);
 
 
    
}

public void addEntry(String category,String entry,String value){
    if(!categorys.containsKey(category))categorys.put(category, new HashMap<String,String>());
    categorys.get(category).put(entry, value);
}
public void save(String category,String entry,String value){
    config.getCategory(category).remove(entry);

    config.save();
    
   if(entry.equals("false")||entry.equals("true"))config.getCategory(category).set(entry, new Property(entry,value,Type.BOOLEAN));
    else{
        try{
            Integer.valueOf(value);
         
            config.getCategory(category).set(entry, new Property(entry,value,Type.INTEGER));
        }catch(NumberFormatException e){
            try{
                Double.valueOf(value);
                config.getCategory(category).set(entry, new Property(entry,value,Type.BOOLEAN));
            }catch(NumberFormatException e1){
                config.getCategory(category).set(entry, new Property(entry,value,Type.STRING));
            }
        }
    }
   config.save();
    
}
}