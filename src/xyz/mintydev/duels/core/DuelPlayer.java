package xyz.mintydev.duels.core;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DuelPlayer {

	private UUID uuid;
	private String name;
	private Location previousLocation;
	private ItemStack[] previousInventory;
	private GameMode previousGameMode;
	
	private int wins, loss, kills, deaths, streak;
	
	public DuelPlayer(UUID uuid, String name, int wins, int loss, int kills, int deaths, int streak) {
		this.uuid = uuid;
		this.name = name;
		
		this.previousLocation = null;
		this.previousInventory = null;
		this.previousGameMode = null;
		
		this.wins = wins;
		this.loss = loss;
		this.kills = kills;
		this.deaths = deaths;
		this.streak = streak;
	}
	
	public UUID getUuid() {
		return uuid;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public int getWins() {
		return wins;
	}
	
	public int getLoss() {
		return loss;
	}
	
	public int getKills() {
		return kills;
	}
	
	public int getDeaths() {
		return deaths;
	}
	
	public int getStreak() {
		return streak;
	}
	
	public void setKills(int kills) {
		this.kills = kills;
	}
	
	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}
	
	public void setLoss(int loss) {
		this.loss = loss;
	}
	
	public void setStreak(int streak) {
		this.streak = streak;
	}
	
	public void setWins(int wins) {
		this.wins = wins;
	}
	
	public Player getPlayer() {
		return Bukkit.getPlayer(uuid);
	}
	
	public Location getPreviousLocation() {
		return previousLocation;
	}
	
	public void setPreviousLocation(Location previousLocation) {
		this.previousLocation = previousLocation;
	}
	
	public void setPreviousGameMode(GameMode previousGameMode) {
		this.previousGameMode = previousGameMode;
	}
	
	public void setPreviousInventory(ItemStack[] previousInventory) {
		this.previousInventory = previousInventory;
	}

	public GameMode getPreviousGameMode() {
		return previousGameMode;
	}
	
	public ItemStack[] getPreviousInventory() {
		return previousInventory;
	}
	
}
