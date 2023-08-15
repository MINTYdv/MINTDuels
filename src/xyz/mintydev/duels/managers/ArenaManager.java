package xyz.mintydev.duels.managers;

import java.util.HashSet;
import java.util.Set;

import xyz.mintydev.duels.MINTDuels;
import xyz.mintydev.duels.core.Arena;

public class ArenaManager extends ConfigFileHandler {

	private final MINTDuels main;
	
	private Set<Arena> arenas = new HashSet<>();
	
	public ArenaManager(MINTDuels main) {
		super(main, "arenas.yml");
		this.main = main;
	}
	
	private void loadArenas() {
		
	}
	
    /* Getters & Setters */
    
    public Set<Arena> getArenas() {
		return arenas;
	}
    
}
