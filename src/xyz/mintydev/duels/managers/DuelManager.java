package xyz.mintydev.duels.managers;

import java.util.HashSet;
import java.util.Set;

import xyz.mintydev.duels.MINTDuels;
import xyz.mintydev.duels.core.DuelGame;
import xyz.mintydev.duels.core.DuelInvite;

public class DuelManager {

	private final MINTDuels main;
	
	private Set<DuelGame> games = new HashSet<>();
	private Set<DuelInvite> invites = new HashSet<>();
	
	public DuelManager(MINTDuels main) {
		this.main = main;
	}
	
	public Set<DuelGame> getGames() {
		return games;
	}
	
	public Set<DuelInvite> getInvites() {
		return invites;
	}
	
}
