package xyz.mintydev.duels.managers;

import java.util.HashSet;
import java.util.Set;

import xyz.mintydev.duels.MINTDuels;
import xyz.mintydev.duels.core.Kit;

public class KitManager extends ConfigFileHandler {

	private final MINTDuels main;
	
	private Set<Kit> kits = new HashSet<>();
	
	public KitManager(MINTDuels main) {
		super(main, "kits.yml");
		this.main = main;
	}
	
	public Set<Kit> getKits() {
		return kits;
	}
	
}
