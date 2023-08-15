package xyz.mintydev.duels.managers;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import xyz.mintydev.duels.MINTDuels;
import xyz.mintydev.duels.core.Arena;

public class ArenaManager {

	private final MINTDuels main;
	
	private static File customConfigFile;
	private static FileConfiguration dataFile;
	
	private final String fileName = "arenas.yml";
	
	private Set<Arena> arenas = new HashSet<>();
	
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
    
    public Set<Arena> getArenas() {
		return arenas;
	}
    
    public static FileConfiguration getDataFile() {
		return dataFile;
	}
    
    public static File getCustomConfigFile() {
		return customConfigFile;
	}	
	
}
