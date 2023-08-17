package xyz.mintydev.duels.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.entity.Player;

public class DuelInvite {

	private final Player sender, target;
	private final Kit kit;
	private final Date date;
	private boolean accepted;
	
	public DuelInvite(Player sender, Player target, Kit kit) {
		this.sender = sender;
		this.target = target;
		this.kit = kit;
		this.date = new Date();
		accepted = false;
	}
	
	public String replacePlaceholders(String base) {
		base = base.replaceAll("%sender%", sender.getName());
		base = base.replaceAll("%target%", target.getName());
		base = base.replaceAll("%kit%", kit.getDisplayName());
		return base;
	}
	
	public List<Player> getPlayers(){
		List<Player> res = new ArrayList<>();
		res.add(sender);
		res.add(target);
		return res;
	}
	
	public boolean isAccepted() {
		return accepted;
	}
	
	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}
	
	public Date getDate() {
		return date;
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
