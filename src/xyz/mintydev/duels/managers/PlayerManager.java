package xyz.mintydev.duels.managers;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import xyz.mintydev.duels.core.DuelPlayer;

public class PlayerManager {

	private Map<Player, DuelPlayer> players = new HashMap<>();
	
	public DuelPlayer getPlayer(Player player) {
		if(!players.containsKey(player)) {
			players.put(player, new DuelPlayer(player, player.getLocation(), player.getInventory().getContents(), player.getGameMode()));
		}
		
		return players.get(player);
	}
	
	public Map<Player, DuelPlayer> getPlayers() {
		return players;
	}
	
}
