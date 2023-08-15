package xyz.mintydev.duels;

import org.bukkit.plugin.java.JavaPlugin;

import xyz.mintydev.duels.listener.GameListener;
import xyz.mintydev.duels.managers.ArenaManager;
import xyz.mintydev.duels.managers.CommandManager;
import xyz.mintydev.duels.managers.ConfigManager;
import xyz.mintydev.duels.managers.DuelManager;
import xyz.mintydev.duels.managers.KitManager;
import xyz.mintydev.duels.managers.LangManager;
import xyz.mintydev.duels.managers.PlayerManager;

public class MINTDuels extends JavaPlugin {

	private static MINTDuels instance;
	
	private LangManager langManager;
	private ArenaManager arenaManager;
	private KitManager kitManager;
	private DuelManager duelManager;
	private ConfigManager configManager;
	private CommandManager commandManager;
	private PlayerManager playerManager;
	
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
		getServer().getPluginManager().registerEvents(new GameListener(instance), instance);
	}

	private void registerManagers() {
		this.arenaManager = new ArenaManager(instance); 
		this.kitManager = new KitManager(instance);
		this.duelManager = new DuelManager(instance); 
		this.langManager = new LangManager(instance);
		this.playerManager = new PlayerManager();
		this.configManager = new ConfigManager(instance);
		this.commandManager = new CommandManager(instance);
	}

	public static MINTDuels get() {
		return instance;
	}
	
	public PlayerManager getPlayerManager() {
		return playerManager;
	}
	
	public KitManager getKitManager() {
		return kitManager;
	}
	
	public DuelManager getDuelManager() {
		return duelManager;
	}
	
	public ArenaManager getArenaManager() {
		return arenaManager;
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
