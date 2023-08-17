package xyz.mintydev.duels.command;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.mintydev.duels.MINTDuels;
import xyz.mintydev.duels.core.DuelPlayer;
import xyz.mintydev.duels.managers.LangManager;
import xyz.mintydev.duels.util.UUIDFetcher;
import xyz.mintydev.duels.util.command.Command;
import xyz.mintydev.duels.util.command.PermissionRequirement;
import xyz.mintydev.duels.util.command.PlayerRequirement;

public class StatsCommand extends Command {

	private final MINTDuels main;
	
	
	public StatsCommand(MINTDuels main, String... aliases) {
		super(aliases);
		this.main = main;
		
		/* Setup command */
		this.setMaxArgs(1);
		this.addRequirement(new PermissionRequirement("mintduels.command.stats"));
		this.addRequirement(new PlayerRequirement());
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, String label) throws Exception {
		
		final Player player = (Player) sender;
		
		DuelPlayer target = main.getPlayerManager().getPlayer(player);
		
		if(args != null && args.length > 0) {
			final String playerName = args[0];
			
			final Player targetPlayer = Bukkit.getPlayer(playerName);
			if(targetPlayer == null) {
				player.sendMessage(LangManager.getMessage("commands.errors.no-player"));
				return false;
			}
			
			target = main.getPlayerManager().getPlayer(targetPlayer);
		}
		
		for(String str : LangManager.getMessageList("commands.stats.format")) {
			str = str.replaceAll("%player%", target.getName());
			str = str.replaceAll("%wins%", target.getWins()+"");
			str = str.replaceAll("%loss%", target.getLoss()+"");
			str = str.replaceAll("%kills%", target.getKills()+"");
			str = str.replaceAll("%deaths%", target.getDeaths()+"");
			str = str.replaceAll("%streak%", target.getStreak()+"");
			player.sendMessage(str);
		}
		return true;
	}

	@Override
	public void wrongUsage(CommandSender sender, String label) {
		String res = String.format("§cUsage: /" + label + " [player]");
		sender.sendMessage(res);
	}

}