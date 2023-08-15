package xyz.mintydev.duels.managers;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import xyz.mintydev.duels.MINTDuels;

public class ArenaManager {

	private final MINTDuels main;
	
	private static File customConfigFile;
	private static FileConfiguration dataFile;
	
	private final String fileName = "arenas.yml";
	
	public ArenaManager(MINTDuels main) {
		this.main = main;
		createCustomConfig();
	}
	
	private void loadArenas() {
		
	}
	
    private void createCustomConfig() {
        customConfigFile = new File(main.getDataFolder(), fileName);
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            main.saveResource(fileName, false);
         }

        dataFile= new YamlConfiguration();
        try {
        	dataFile.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
    
    /* Getters & Setters */
    
    public static FileConfiguration getDataFile() {
		return dataFile;
	}
    
    public static File getCustomConfigFile() {
		return customConfigFile;
	}	
	
}
