package xyz.mintydev.duels.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.mintydev.duels.MINTDuels;
import xyz.mintydev.duels.core.DuelInvite;
import xyz.mintydev.duels.core.Kit;
import xyz.mintydev.duels.managers.LangManager;
import xyz.mintydev.duels.util.command.Command;
import xyz.mintydev.duels.util.command.PermissionRequirement;
import xyz.mintydev.duels.util.command.PlayerRequirement;

public class AcceptCommand extends Command {

	private final MINTDuels main;
	
	
	public AcceptCommand(MINTDuels main, String... aliases) {
		super(aliases);
		this.main = main;
		
		/* Setup command */
		this.setMinArgs(1);
		this.setMaxArgs(1);
		this.addRequirement(new PermissionRequirement("mintduels.command.duel"));
		this.addRequirement(new PlayerRequirement());
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, String label) throws Exception {
		
		final Player player = (Player) sender;
		
		final String playerName = args[0];
		if(Bukkit.getPlayer(playerName) == null) {
			player.sendMessage(LangManager.getMessage("commands.errors.no-player"));
			return false;
		}
		
		if(main.getDuelManager().getGame(player) != null) {
			player.sendMessage(LangManager.getMessage("commands.errors.ingame"));
			return false;
		}
		
		final Player target = Bukkit.getPlayer(playerName);
		
		if(main.getDuelManager().getInvite(target, player, true) == null) {
			player.sendMessage(LangManager.getMessage("commands.accept.errors.no-invite"));
			return false;
		}
		
		final DuelInvite invite = main.getDuelManager().getInvite(target, player, true);
		main.getDuelManager().acceptInvite(invite);
		return true;
	}

	@Override
	public void wrongUsage(CommandSender sender, String label) {
		String res = String.format("§cUsage: /" + label + " <player>");
		sender.sendMessage(res);
	}

}