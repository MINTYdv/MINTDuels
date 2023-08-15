package xyz.mintydev.duels.core;

import java.util.List;

import org.bukkit.entity.Player;

public class DuelGame {

	private final Arena arena;
	private final Kit kit;
	private final List<Player> players;
	
	private GameState state;
	
	public DuelGame(Arena arena, Kit kit, List<Player> players) {
		this.arena = arena;
		this.kit = kit;
		this.players = players;
		
		state = GameState.STARTING;
	}
	
	public GameState getState() {
		return state;
	}
	
	public Arena getArena() {
		return arena;
	}
	
	public Kit getKit() {
		return kit;
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
}
