package xyz.mintydev.duels.core;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import xyz.mintydev.duels.MINTDuels;
import xyz.mintydev.duels.runnable.GameRunnable;

public class DuelGame {

	private final Arena arena;
	private final Kit kit;
	private final List<Player> players;
	
	private GameState state;
	private BukkitTask task;
	private Player winner;
	
	public DuelGame(Arena arena, Kit kit, List<Player> players) {
		this.arena = arena;
		this.kit = kit;
		this.players = players;
		
		state = GameState.STARTING;
		
		task = new GameRunnable(MINTDuels.get(), this).runTaskTimer(MINTDuels.get(), 0, 20);
	}
	
	public Player getOpponent(Player target) {
		for(Player player : players) if(!(player.equals(target))) return player;
		return null;
	}
	
	public void broadcast(String msg) {
		for(Player player : players) player.sendMessage(msg);
	}
	
	public Player getWinner() {
		return winner;
	}
	
	public void setWinner(Player winner) {
		this.winner = winner;
	}
	
	public BukkitTask getTask() {
		return task;
	}
	
	public void setState(GameState state) {
		this.state = state;
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
