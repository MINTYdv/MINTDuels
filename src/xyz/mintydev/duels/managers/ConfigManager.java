package xyz.mintydev.duels.managers;

import org.bukkit.configuration.ConfigurationSection;

import xyz.mintydev.duels.MINTDuels;
import xyz.mintydev.duels.managers.database.DatabaseCredentials;

public class ConfigManager {

	private final MINTDuels main;
	private static ConfigManager instance;
	
	public ConfigManager(MINTDuels main) {
		this.main = main;
		instance = this;
		main.saveDefaultConfig();
	}
	
	/** 
	 * Function called to load database credentials from the config.yml file
	 * */
	public DatabaseCredentials loadCredentials() {
		final ConfigurationSection sec = main.getConfig().getConfigurationSection("mysql");
		final String ip = sec.getString("ip"); 
		final String database = sec.getString("database"); 
		final String user = sec.getString("user"); 
		final String password = sec.getString("password"); 
		
		final int port = sec.getInt("port");
		
		return new DatabaseCredentials(ip, database, user, password, port);
	}
	
	public static ConfigManager get() {
		return instance;
	}
	
}
