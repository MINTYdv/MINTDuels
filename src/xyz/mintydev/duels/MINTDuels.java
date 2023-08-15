package xyz.mintydev.duels;

import org.bukkit.plugin.java.JavaPlugin;

import xyz.mintydev.duels.managers.CommandManager;
import xyz.mintydev.duels.managers.ConfigManager;
import xyz.mintydev.duels.managers.LangManager;

public class MINTDuels extends JavaPlugin {

	private static MINTDuels instance;
	
	private LangManager langManager;
	private ConfigManager configManager;
	private CommandManager commandManager;
	
	@Override
	public void onEnable() {
		instance = this;
		
		registerManagers();
		registerListeners();
		registerCommands();
	}
	
	private void registerCommands() {

	}

	private void registerListeners() {

	}

	private void registerManagers() {
		this.langManager = new LangManager(instance);
		this.configManager = new ConfigManager(instance);
		this.commandManager = new CommandManager(instance);
	}

	public static MINTDuels get() {
		return instance;
	}
	
	public LangManager getLangManager() {
		return langManager;
	}
	
	public ConfigManager getConfigManager() {
		return configManager;
	}
	
	public CommandManager getCommandManager() {
		return commandManager;
	}
}
