package xyz.mintydev.duels.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import xyz.mintydev.duels.MINTDuels;
import xyz.mintydev.duels.core.Arena;
import xyz.mintydev.duels.util.config.ConfigUtil;

public class ArenaManager extends ConfigFileHandler {

	private final MINTDuels main;
	
	private Set<Arena> arenas = new HashSet<>();
	
	public ArenaManager(MINTDuels main) {
		super(main, "arenas.yml");
		this.main = main;
		
		loadArenas();
	}
	
	public Arena getAvailableArena() {
		
		List<Arena> toCheck = new ArrayList<>(arenas);
		Collections.shuffle(toCheck);
		
		for(final Arena arena : toCheck) {
			if(!arena.isUsed()) return arena;
		}
		return null;
	}
	
	private void loadArenas() {
		final ConfigurationSection sec = getDataFile().getConfigurationSection("arenas");
		if(sec == null || sec.getKeys(false) == null) return;
		
		for(String arenaID : sec.getKeys(false)) {
			
			if(getArena(arenaID) != null) {
				main.getLogger().severe("The ID " + arenaID + " is already used by another duel arena, please check your config.");
				continue;
			}
			
			final ConfigurationSection sub = sec.getConfigurationSection(arenaID);
			
			final String displayName = sub.getString("name").replaceAll("&", "§");
			
			final String worldName = sub.getString("world");
			final World world = Bukkit.getWorld(worldName);
			if(world == null) {
				main.getLogger().severe("Could not find world with the name of " + worldName + " while loading arena " + arenaID + "..");
				continue;
			}
			
			final Location firstBound = ConfigUtil.getLocationFromSection(sub.getConfigurationSection("boundaries.1"), true);
			final Location secondBound = ConfigUtil.getLocationFromSection(sub.getConfigurationSection("boundaries.2"), true);
			final Location firstSpawn = ConfigUtil.getLocationFromSection(sub.getConfigurationSection("spawns.1"), false);
			final Location secondSpawn = ConfigUtil.getLocationFromSection(sub.getConfigurationSection("spawns.2"), false);
			
			final Arena arena = new Arena(arenaID, displayName, world, firstBound, secondBound, firstSpawn, secondSpawn);
			addArena(arena);
		}
	}
	
	public Arena getArena(String ID) {
		if(arenas == null || arenas.size() == 0) return null;
		for(Arena arena : arenas) {
			if(arena.getId().equalsIgnoreCase(ID)) return arena;
		}
		return null;
	}
	
	public void addArena(Arena arena) {
		this.arenas.add(arena);
		main.getLogger().info("[Arena] Loaded arena " + arena.getId() + " successfully.");
	}
	
    /* Getters & Setters */
    
    public Set<Arena> getArenas() {
		return arenas;
	}
    
}
