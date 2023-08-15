package xyz.mintydev.duels.util.command;

import org.bukkit.command.CommandSender;

import xyz.mintydev.duels.MINTDuels;
import xyz.mintydev.duels.managers.CommandManager;

public class FakeCommand extends org.bukkit.command.Command {

	public FakeCommand(String name) {
		super(name);
	}

	/** 
	 * Returns the code to the {@link CommandManager}
	 * */
	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		return MINTDuels.get().getCommandManager().onCommand(sender, this, commandLabel, args);
	}

}
