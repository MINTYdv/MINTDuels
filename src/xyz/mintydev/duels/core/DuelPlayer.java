package xyz.mintydev.duels.core;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import xyz.mintydev.duels.MINTDuels;

public class DuelPlayer {

	private Player player;
	private Location previousLocation;
	private ItemStack[] previousInventory;
	private GameMode previousGameMode;
	
	public DuelPlayer(Player player, Location previousLocation, ItemStack[] previousInventory, GameMode previousGameMode) {
		this.player = player;
		this.previousLocation = previousLocation;
		this.previousInventory = previousInventory;
		this.previousGameMode = previousGameMode;
	}
	
	public boolean isInGame() {
		return MINTDuels.get().getDuelManager().getGame(player) != null;
	}
	
	public Player getPlayer() {
		return player;
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
