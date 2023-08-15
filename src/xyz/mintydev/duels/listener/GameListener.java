package xyz.mintydev.duels.listener;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import xyz.mintydev.duels.MINTDuels;
import xyz.mintydev.duels.core.DuelGame;
import xyz.mintydev.duels.core.GameState;

public class GameListener implements Listener {

	private final MINTDuels main;
	
	public GameListener(MINTDuels main) {
		this.main = main;
	}
	
	private void cancelIfInGame(Player player, Cancellable event) {
		final DuelGame game = main.getDuelManager().getGame(player);
		if(game == null) return;
		
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		final Player player = e.getEntity();
		final DuelGame game = main.getDuelManager().getGame(player);
		if(game == null) return;
		e.setDroppedExp(0);
		e.getDrops().clear();
		e.setDeathMessage(null);
		
		new BukkitRunnable() {

			@Override
			public void run() {
				player.spigot().respawn();
				player.setGameMode(GameMode.SPECTATOR);
			}
			
		};
		
		final Player winner = game.getOpponent(player);
		main.getDuelManager().gameWon(game, winner);
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		cancelIfInGame(e.getPlayer(), e);
	}
	
	@EventHandler
	public void onMiscDamage(EntityDamageEvent e) {
		
		if(!(e.getEntity() instanceof Player)) return;
		final Player player = (Player) e.getEntity();
		if(e.getCause() != DamageCause.FALL && e.getCause() != DamageCause.ENTITY_EXPLOSION && e.getCause() != DamageCause.BLOCK_EXPLOSION) return;
		
		cancelIfInGame(player, e);
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		cancelIfInGame(e.getPlayer(), e);
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		cancelIfInGame(e.getPlayer(), e);
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		final Player player = e.getPlayer();
		
		final DuelGame game = main.getDuelManager().getGame(player);
		if(game == null) return;
		if(game.getState() != GameState.STARTING) return;
		
		e.setCancelled(true);
	}
	
}
