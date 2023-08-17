package xyz.mintydev.duels.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import xyz.mintydev.duels.MINTDuels;
import xyz.mintydev.duels.core.DuelInvite;
import xyz.mintydev.duels.managers.LangManager;

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
	public void onRightClick(PlayerInteractEvent e) {
		final Player player = e.getPlayer();
		
		if(!(main.getQueueManager().isInQueue(player))) return;
		if(e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if(e.getItem() == null) return;
		if(e.getItem().getType() != Material.OAK_DOOR) return;
		
		final DuelInvite invite = main.getQueueManager().getQueueInvite(player);
		if(invite == null) return;
		
		// cancel queue
		main.getQueueManager().cancelQueue(invite);
		player.sendMessage(LangManager.getMessage("duel.queue.leave"));
		final Player opponent = invite.getOpponent(player);
		if(opponent != null && opponent.isOnline()) {
			opponent.sendMessage(LangManager.getMessage("duel.queue.player-left"));
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		final Player player = e.getPlayer();

		if(main.getQueueManager().isInQueue(player)) {
			final DuelInvite invite = main.getQueueManager().getQueueInvite(player);
			if(invite == null) return;
			
			// cancel queue
			main.getQueueManager().cancelQueue(invite);
			final Player opponent = invite.getOpponent(player);
			if(opponent != null && opponent.isOnline()) {
				opponent.sendMessage(LangManager.getMessage("duel.queue.player-left"));
			}
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
