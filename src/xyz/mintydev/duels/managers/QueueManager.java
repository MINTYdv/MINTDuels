package xyz.mintydev.duels.managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import xyz.mintydev.duels.MINTDuels;
import xyz.mintydev.duels.core.DuelInvite;
import xyz.mintydev.duels.core.DuelPlayer;
import xyz.mintydev.duels.runnable.QueueRunnable;
import xyz.mintydev.duels.util.ItemBuilder;

public class QueueManager {

	private final MINTDuels main;
	
	private List<DuelInvite> arenaQueue = new ArrayList<>();
	
	private BukkitTask queueTask;
	
	public QueueManager(MINTDuels main) {
		this.main = main;
		queueTask = new QueueRunnable(main).runTaskTimer(main, 0, 20);
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
			
			player.getInventory().setItem(8, ItemBuilder.createItem(Material.OAK_DOOR, 1, LangManager.getMessage("duel.queue.leave-item.name"), LangManager.getMessageList("duel.queue.leave-item.lore")));
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
	
	public void shutdown() {
		final List<DuelInvite> copy = new ArrayList<>(getArenaQueue());
		
		for(DuelInvite invite : copy) {
			cancelQueue(invite);
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

	public BukkitTask getQueueTask() {
		return queueTask;
	}
	
	public List<DuelInvite> getArenaQueue() {
		return arenaQueue;
	}

}
