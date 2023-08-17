package xyz.mintydev.duels.managers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

import xyz.mintydev.duels.MINTDuels;
import xyz.mintydev.duels.core.Arena;
import xyz.mintydev.duels.core.DuelGame;
import xyz.mintydev.duels.core.DuelInvite;
import xyz.mintydev.duels.core.DuelPlayer;
import xyz.mintydev.duels.core.EndReason;
import xyz.mintydev.duels.core.GameState;
import xyz.mintydev.duels.core.Kit;

public class DuelManager {

	private final MINTDuels main;
	
	private Set<DuelGame> games = new HashSet<>();
	private Set<DuelInvite> invites = new HashSet<>();
	
	public DuelManager(MINTDuels main) {
		this.main = main;
	}
	
	public void sendInvite(Player sender, Player target, Kit kit) {
		if(getInvite(sender, target, true) != null) {
			sender.sendMessage(LangManager.getMessage("commands.invite.errors.already-invited"));
			return;
		}
		
		if(!(target.isOnline()) || target.equals(sender)) return;
		
		DuelInvite invite = new DuelInvite(sender, target, kit);
		invites.add(invite);
		
		sender.sendMessage(invite.replacePlaceholders(LangManager.getMessage("commands.invite.sent")));
		target.sendMessage(invite.replacePlaceholders(LangManager.getMessage("commands.invite.received")));
	}
	
	public void acceptInvite(DuelInvite invite) {
		final Player sender = invite.getSender();
		final Player target = invite.getTarget();
		
		final Arena available = main.getArenaManager().getAvailableArena();
		invite.setAccepted(true);
		if(available == null || main.getQueueManager().getArenaQueue().size() > 0) {
			main.getQueueManager().addToQueue(invite);
			target.sendMessage(invite.replacePlaceholders(LangManager.getMessage("commands.accept.successfully.queue")));
			sender.sendMessage(invite.replacePlaceholders(LangManager.getMessage("commands.invite.accepted.queue")));
		} else {
			loadGame(invite, available);
			target.sendMessage(invite.replacePlaceholders(LangManager.getMessage("commands.accept.successfully.game")));
			sender.sendMessage(invite.replacePlaceholders(LangManager.getMessage("commands.invite.accepted.game")));
		}
	}
	
	public void loadGame(DuelInvite invite, Arena arena) {
		final Player sender = invite.getSender();
		final Player target = invite.getTarget();
		arena.setUsed(true);
		
		List<Player> players = new ArrayList<>();
		players.add(sender);
		players.add(target);
		
		final DuelGame game = new DuelGame(arena, invite.getKit(), players);
		games.add(game);
		
		for(Player player : players) {
			
			DuelPlayer dPlayer = main.getPlayerManager().getPlayer(player);
			dPlayer.setPreviousGameMode(player.getGameMode());
			dPlayer.setPreviousInventory(player.getInventory().getContents());
			dPlayer.setPreviousLocation(player.getLocation());
			
			AttributeInstance inst = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
			player.setHealth(inst.getBaseValue());
			player.setFoodLevel(20);
			player.getInventory().clear();
			game.getKit().give(player);
			player.setGameMode(GameMode.SURVIVAL);
		}
		sender.teleport(arena.getSpawn1());
		target.teleport(arena.getSpawn2());
		
		invites.remove(invite);
	}
	
	public void gameWon(DuelGame game, Player winner) {
		if(game.getState() == GameState.FINISHED) return;
		game.setState(GameState.FINISHED);
		game.setWinner(winner);
		
		final DuelPlayer dWinner = main.getPlayerManager().getPlayer(winner);
		dWinner.setWins(dWinner.getWins()+1);
		dWinner.setStreak(dWinner.getStreak()+1);
		dWinner.setKills(dWinner.getKills()+1);
		
		final DuelPlayer dOpponent = main.getPlayerManager().getPlayer(game.getOpponent(winner));
		dOpponent.setLoss(dOpponent.getLoss()+1);
		dOpponent.setStreak(0);
		
		String msg = LangManager.getMessage("duel.winner");
		msg = msg.replaceAll("%winner%", winner.getName());
		msg = msg.replaceAll("%looser%", game.getOpponent(winner).getName());
		msg = msg.replaceAll("%kit%", game.getKit().getDisplayName());
		
		game.broadcast(msg, false);
	}
	
	public void endGame(DuelGame game, EndReason reason, Player winner) {
		
		for(Player player : game.getPlayers()) {
			final DuelPlayer dPlayer = main.getPlayerManager().getPlayer(player);
			
			if(player != null && player.isOnline()) {
				player.setGameMode(dPlayer.getPreviousGameMode());
				player.getInventory().setContents(dPlayer.getPreviousInventory());
				player.teleport(dPlayer.getPreviousLocation());
				AttributeInstance inst = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
				player.setHealth(inst.getBaseValue());
				player.setFoodLevel(20);
			}
		}
		
		game.getArena().setUsed(false);
		
		game.getTask().cancel();
	}
	
	public void shutdown() {
		for(DuelGame game : getGames()) {
			endGame(game, EndReason.SERVER_STOP, null);
		}
	}
	
	public DuelGame getGame(Player player) {
		for(DuelGame game : games) {
			if(game.getPlayers().contains(player)) return game;
		}
		return null;
	}
	
	public DuelInvite getInvite(Player sender, Player target, boolean onlyActive) {
		for(DuelInvite invite : invites) {
			if(onlyActive && invite.isAccepted()) continue;
			if(invite.getSender().equals(sender) && invite.getTarget().equals(target)) return invite;
		}
		return null;
	}
	
	public Set<DuelGame> getGames() {
		return games;
	}
	
	public Set<DuelInvite> getInvites() {
		return invites;
	}
	
}
