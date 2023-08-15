package xyz.mintydev.duels.managers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.entity.Player;

import xyz.mintydev.duels.MINTDuels;
import xyz.mintydev.duels.core.Arena;
import xyz.mintydev.duels.core.DuelGame;
import xyz.mintydev.duels.core.DuelInvite;
import xyz.mintydev.duels.core.Kit;

public class DuelManager {

	private final MINTDuels main;
	
	private Set<DuelGame> games = new HashSet<>();
	private Set<DuelInvite> invites = new HashSet<>();
	
	private List<DuelInvite> arenaQueue = new ArrayList<>();
	
	public DuelManager(MINTDuels main) {
		this.main = main;
	}
	
	public void sendInvite(Player sender, Player target, Kit kit) {
		if(getInvite(sender, target) != null) {
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
		
		target.sendMessage(invite.replacePlaceholders(LangManager.getMessage("commands.accept.successfully")));
		sender.sendMessage(invite.replacePlaceholders(LangManager.getMessage("commands.invite.accepted")));
		
		invite.setAccepted(true);
		
		final Arena available = main.getArenaManager().getAvailableArena();
		if(available == null) {
			addToQueue(invite);
		} else {
			loadGame(invite, available);
		}
	}
	
	public void loadGame(DuelInvite invite, Arena arena) {
		final Player sender = invite.getSender();
		final Player target = invite.getTarget();
		
		arena.setUsed(true);
		
		sender.teleport(arena.getSpawn1());
		target.teleport(arena.getSpawn2());
		
		List<Player> players = new ArrayList<>();
		players.add(sender);
		players.add(target);
		
		final DuelGame game = new DuelGame(arena, invite.getKit(), players);
		games.add(game);
		
		for(Player player : players) {
			player.getInventory().clear();
			game.getKit().give(player);
		}
	}
	
	public void addToQueue(DuelInvite invite) {
		arenaQueue.add(invite);
	}
	
	public DuelGame getGame(Player player) {
		for(DuelGame game : games) {
			if(game.getPlayers().contains(player)) return game;
		}
		return null;
	}
	
	public DuelInvite getInvite(Player sender, Player target) {
		for(DuelInvite invite : invites) {
			if(invite.getSender().equals(sender) && invite.getTarget().equals(target)) return invite;
		}
		return null;
	}
	
	public List<DuelInvite> getArenaQueue() {
		return arenaQueue;
	}
	
	public Set<DuelGame> getGames() {
		return games;
	}
	
	public Set<DuelInvite> getInvites() {
		return invites;
	}
	
}
