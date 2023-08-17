package xyz.mintydev.duels;

import org.bukkit.plugin.java.JavaPlugin;

import xyz.mintydev.duels.listener.GameListener;
import xyz.mintydev.duels.listener.QueueListener;
import xyz.mintydev.duels.managers.ArenaManager;
import xyz.mintydev.duels.managers.CommandManager;
import xyz.mintydev.duels.managers.ConfigManager;
import xyz.mintydev.duels.managers.DuelManager;
import xyz.mintydev.duels.managers.KitManager;
import xyz.mintydev.duels.managers.LangManager;
import xyz.mintydev.duels.managers.PlayerManager;
import xyz.mintydev.duels.managers.QueueManager;

public class MINTDuels extends JavaPlugin {

	private static MINTDuels instance;
	
	private LangManager langManager;
	private ArenaManager arenaManager;
	private KitManager kitManager;
	private DuelManager duelManager;
	private ConfigManager configManager;
	private CommandManager commandManager;
	private PlayerManager playerManager;
	private QueueManager queueManager;
	
	@Override
	public void onEnable() {
		instance = this;
		
		registerManagers();
		registerListeners();
		
		getLogger().info("Plugin enabled !");
	}
	
	@Override
	public void onDisable() {
		getDuelManager().shutdown();
		
		getLogger().info("Plugin disabled !");
	}
	private void registerListeners() {
		getServer().getPluginManager().registerEvents(new GameListener(instance), instance);
		getServer().getPluginManager().registerEvents(new QueueListener(instance), instance);
	}

	private void registerManagers() {
		this.arenaManager = new ArenaManager(instance); 
		this.kitManager = new KitManager(instance);
		this.duelManager = new DuelManager(instance); 
		this.langManager = new LangManager(instance);
		this.playerManager = new PlayerManager();
		this.configManager = new ConfigManager(instance);
		this.commandManager = new CommandManager(instance);
		this.queueManager = new QueueManager(instance);
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
	
	public QueueManager getQueueManager() {
		return queueManager;
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
