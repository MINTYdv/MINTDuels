package xyz.mintydev.duels.managers;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import xyz.mintydev.duels.MINTDuels;

public abstract class ConfigFileHandler {

	private final String fileName;
	
	private static File customConfigFile;
	private static FileConfiguration dataFile;
	
	private final MINTDuels main;
	
	public ConfigFileHandler(MINTDuels main, String fileName) {
		this.main = main;
		this.fileName = fileName;
		
		createCustomConfig();
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
	
    public static FileConfiguration getDataFile() {
		return dataFile;
	}
    
    public static File getCustomConfigFile() {
		return customConfigFile;
	}
    
}
