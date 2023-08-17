package xyz.mintydev.duels.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.mintydev.duels.MINTDuels;
import xyz.mintydev.duels.core.Kit;
import xyz.mintydev.duels.managers.LangManager;
import xyz.mintydev.duels.util.command.Command;
import xyz.mintydev.duels.util.command.PermissionRequirement;
import xyz.mintydev.duels.util.command.PlayerRequirement;

public class DuelCommand extends Command {

	private final MINTDuels main;
	
	
	public DuelCommand(MINTDuels main, String... aliases) {
		super(aliases);
		this.main = main;
		
		/* Setup command */
		this.setMinArgs(1);
		this.setMaxArgs(2);
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
		
		if(main.getQueueManager().isInQueue(player)) {
			player.sendMessage(LangManager.getMessage("commands.errors.inqueue"));
			return false;
		}
		
		final Player target = Bukkit.getPlayer(playerName);
		
		if(main.getDuelManager().getInvite(player, target, true) != null) {
			player.sendMessage(LangManager.getMessage("commands.invite.errors.already-invited"));
			return false;
		}
		
		if(player.equals(target)) {
			player.sendMessage(LangManager.getMessage("commands.invite.errors.yourself"));
			return false;
		}
		
		Kit kit = null;
		
		if(args.length >= 2) {
			final String kitID = args[1];
			kit = main.getKitManager().getByID(kitID);
			if(kit == null) {
				player.sendMessage(LangManager.getMessage("commands.invite.errors.invalid-kit"));
				return false;
			}
		}
		
		if(kit == null) kit = main.getKitManager().getByID("default");
		
		main.getDuelManager().sendInvite(player, target, kit);
		return true;
	}

	@Override
	public void wrongUsage(CommandSender sender, String label) {
		String res = String.format("§cUsage: /" + label + " <player> [kit]");
		sender.sendMessage(res);
	}

}