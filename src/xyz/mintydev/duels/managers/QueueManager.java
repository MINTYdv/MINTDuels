package xyz.mintydev.duels.managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import xyz.mintydev.duels.MINTDuels;
import xyz.mintydev.duels.core.DuelInvite;
import xyz.mintydev.duels.core.DuelPlayer;

public class QueueManager {

	private final MINTDuels main;
	
	private List<DuelInvite> arenaQueue = new ArrayList<>();
	
	public QueueManager(MINTDuels main) {
		this.main = main;
	}
	
	public void addToQueue(DuelInvite invite) {
		if(arenaQueue.contains(invite)) return;
		
		arenaQueue.add(invite);
		
		for(Player player : invite.getPlayers()) {
			final DuelPlayer dPlayer = main.getPlayerManager().getPlayer(player);
			dPlayer.setPreviousGameMode(player.getGameMode());
			dPlayer.setPreviousInventory(player.getInventory().getContents());
			
			player.getInventory().clear();
			player.setGameMode(GameMode.SURVIVAL);
		}
	}
	
	public void cancelQueue(DuelInvite invite) {
		if(!(arenaQueue.contains(invite))) return;
		
		arenaQueue.remove(invite);
		
		for(Player player : invite.getPlayers()) {
			final DuelPlayer dPlayer = main.getPlayerManager().getPlayer(player);
			player.setGameMode(dPlayer.getPreviousGameMode());
			player.getInventory().clear();
			new BukkitRunnable() {
				@Override
				public void run() {
					player.getInventory().setContents(dPlayer.getPreviousInventory());;
				}
			};
		}
	}
	
	public boolean isInQueue(Player player) {
		return getQueueInvite(player) != null;
	}
	
	public DuelInvite getQueueInvite(Player player) {
		for(DuelInvite invite : arenaQueue) {
			if(invite.getPlayers().contains(player)) return invite;
		}
		return null;
	}

	public List<DuelInvite> getArenaQueue() {
		return arenaQueue;
	}
	
}
