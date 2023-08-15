package xyz.mintydev.duels.command;

import org.bukkit.command.CommandSender;

import xyz.mintydev.duels.util.command.Command;
import xyz.mintydev.duels.util.command.PermissionRequirement;

public class DuelCommand extends Command {

	public DuelCommand(String... aliases) {
		super(aliases);
		
		/* Setup command */
		this.setMinArgs(1);
		this.setMaxArgs(2);
		this.addRequirement(new PermissionRequirement("mintpunishment.history"));
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, String label) throws Exception {
		return true;
	}

	@Override
	public void wrongUsage(CommandSender sender, String label) {
		String res = String.format("§cUsage: /" + label + " <player> [page]");
		sender.sendMessage(res);
	}

}