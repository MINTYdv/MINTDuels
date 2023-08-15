package xyz.mintydev.duels.core;

import org.bukkit.entity.Player;

public class DuelInvite {

	private final Player sender, target;
	private final Kit kit;
	
	public DuelInvite(Player sender, Player target, Kit kit) {
		this.sender = sender;
		this.target = target;
		this.kit = kit;
	}
	
	public Player getSender() {
		return sender;
	}
	
	public Player getTarget() {
		return target;
	}
	
	public Kit getKit() {
		return kit;
	}
	
}
