package xyz.mintydev.duels.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import xyz.mintydev.duels.MINTDuels;
import xyz.mintydev.duels.core.DuelInvite;

public class QueueListener implements Listener {

	private final MINTDuels main;
	
	public QueueListener(MINTDuels main) {
		this.main = main;
	}
	
	private void cancelIfInQueue(Player player, Cancellable event) {
		if(!main.getQueueManager().isInQueue(player)) return;
		
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		final Player player = e.getPlayer();

		if(main.getQueueManager().isInQueue(player)) {
			final DuelInvite invite = main.getQueueManager().getQueueInvite(player);
			if(invite == null) return;
			
			// cancel queue
			main.getQueueManager().cancelQueue(invite);
		}
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		cancelIfInQueue(e.getPlayer(), e);
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(!(e.getEntity() instanceof Player)) return;
		Player player = (Player) e.getEntity();
		
		cancelIfInQueue(player, e);
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		cancelIfInQueue(e.getPlayer(), e);
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		cancelIfInQueue(e.getPlayer(), e);
	}
}
