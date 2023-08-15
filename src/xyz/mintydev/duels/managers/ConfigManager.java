package xyz.mintydev.duels.managers;

import xyz.mintydev.duels.MINTDuels;

public class ConfigManager {

	private final MINTDuels main;
	private static ConfigManager instance;
	
	public ConfigManager(MINTDuels main) {
		this.main = main;
		instance = this;
	}
	
	public static ConfigManager get() {
		return instance;
	}
	
}
