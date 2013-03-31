package Enderware.config;

import java.util.ArrayList;
import java.util.HashMap;



public class SettingsAdapter {
	public HashMap<String, Object> Worldconfigs = new HashMap<String, Object>();
	public HashMap<String, Object> Chatconfigs = new HashMap<String, Object>();
	public HashMap<String, Object> Serverconfigs = new HashMap<String, Object>();
	public HashMap<String, Object> Playerconfigs = new HashMap<String, Object>();
	public HashMap<String, Object> Allconfigs = new HashMap<String, Object>();
	public ArrayList<HashMap<String, Object>> allsettings = new ArrayList<HashMap<String, Object>>();
	public HashMap<String, Object> Vanillasettings = new HashMap<String, Object>();
	private static SettingsAdapter instance;
public SettingsAdapter() {
	Worldconfigs.put("allow-nether", true);
	Serverconfigs.put("allow-flight", false);
	Serverconfigs.put("port", 25565);
	Worldconfigs.put("max-build-height", 256);
	Worldconfigs.put("spawn-npcs", true);
	Serverconfigs.put("enable-withlist", false);
	Worldconfigs.put("spawn-animals", true);
	Serverconfigs.put("hardcore", false);
	Worldconfigs.put("spawn-animals", true);
	Serverconfigs.put("texturepacke", "");
	Serverconfigs.put("online-mode", true);
	Serverconfigs.put("pvp", true);
	Worldconfigs.put("diffuculty", 1);
	Playerconfigs.put("gamemode", 0);
	Chatconfigs.put("max-players", 20);
	Worldconfigs.put("spawn-monsters", true);
	Worldconfigs.put("structures", true);
	Chatconfigs.put("view-distance", 10);
	Chatconfigs.put("motd", "A Minecraft Server");
	Worldconfigs.put("command-block", false);
	allsettings.add(Worldconfigs);
	allsettings.add(Chatconfigs);
	allsettings.add(Serverconfigs);
	allsettings.add(Playerconfigs);
	Allconfigs.putAll(Chatconfigs);
	Allconfigs.putAll(Playerconfigs);
	Allconfigs.putAll(Serverconfigs);
	Allconfigs.putAll(Worldconfigs);
	Vanillasettings.put("command-block", false);
	Vanillasettings.put("allow-nether", true);
	Vanillasettings.put("allow-flight", false);
	Vanillasettings.put("port", 25565);
	Vanillasettings.put("max-build-height", 256);
	Vanillasettings.put("spawn-npcs", true);
	Vanillasettings.put("enable-withlist", false);
	Vanillasettings.put("spawn-animals", true);
	Vanillasettings.put("hardcore", false);
	Vanillasettings.put("spawn-animals", true);
	Vanillasettings.put("texturepacke", "");
	Vanillasettings.put("online-mode", true);
	Vanillasettings.put("pvp", true);
	Vanillasettings.put("diffuculty", 1);
	Vanillasettings.put("gamemode", 0);
	Vanillasettings.put("max-players", 20);
	Vanillasettings.put("spawn-monsters", true);
	Vanillasettings.put("structures", true);
	Vanillasettings.put("view-distance", 10);
	Vanillasettings.put("motd", "A Minecraft Server");
	instance = this;
	
}public static SettingsAdapter instance(){
	return instance;
}

}
