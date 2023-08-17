package xyz.mintydev.duels.runnable;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import xyz.mintydev.duels.MINTDuels;
import xyz.mintydev.duels.core.Arena;
import xyz.mintydev.duels.core.DuelInvite;
import xyz.mintydev.duels.managers.LangManager;

public class QueueRunnable extends BukkitRunnable {

	private final MINTDuels main;
	
	public QueueRunnable(MINTDuels main) {
		this.main = main;
	}
	
	@Override
	public void run() {
		
		final List<DuelInvite> copy = new ArrayList<>(main.getQueueManager().getArenaQueue());
		
		for(int i = 0; i < copy.size(); i++) {
			
			final DuelInvite invite = copy.get(i);
			
			Arena available = main.getArenaManager().getAvailableArena();
			if(available != null) {
				
				invite.setAccepted(true);
				
				main.getDuelManager().loadGame(invite, available);
				main.getQueueManager().getArenaQueue().remove(invite);
			} else {
				String msg = LangManager.getMessage("duel.queue.actionbar");
				msg = msg.replaceAll("%position%", (i+1) + "");
				msg = msg.replaceAll("%size%", copy.size() + "");
				
				for(Player player : invite.getPlayers()) {
					player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(msg));
				}
			}
		}
	}

}
