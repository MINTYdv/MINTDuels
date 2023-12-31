package xyz.mintydev.duels.managers;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;

import xyz.mintydev.duels.MINTDuels;
import xyz.mintydev.duels.command.AcceptCommand;
import xyz.mintydev.duels.command.DuelCommand;
import xyz.mintydev.duels.command.StatsCommand;
import xyz.mintydev.duels.util.command.Command;
import xyz.mintydev.duels.util.command.CommandRequirement;
import xyz.mintydev.duels.util.command.FakeCommand;

/** 
 * Command manager class
 * 
 * @author MINTY
 * */
public class CommandManager implements CommandExecutor {

	private final MINTDuels main;
	private Set<Command> commands = new HashSet<>();
	private CommandMap commandMap;
	
	public CommandManager(MINTDuels main) {
		this.main = main;
	
		retrieveCommandMap();
		
		/* Register the plugin's commands */
		addCommand(new DuelCommand(main, "duel", "fight", "duels"));
		addCommand(new AcceptCommand(main, "accept"));
		addCommand(new StatsCommand(main, "stats", "duelstats"));
		
		registerCommands();
	}
	
	private void retrieveCommandMap() {
        try {
            Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            f.setAccessible(true);
            commandMap = (CommandMap) f.get(Bukkit.getServer());
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
	}

	public void addCommand(Command cmd) {
		this.commands.add(cmd);
	}
	
	/** 
	 * Register the commands into the command manager system
	 * */
	public void registerCommands() {
		for (Command c : this.getCommands()) {
			for (String l : c.getAliases()) {
				/* Register the command into the plugin command map */
				commandMap.register(l, new FakeCommand(l));
			}
		}
	}

	/** 
	 * Main Bukkit function that tries all the possible registered commands w/their aliases
	 * */
	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {

		for(Command cmd : commands) {
			if(cmd.getAliases().contains(label)) {
				// Check if the player fills all the requirements
				if(!validRequirements(sender, cmd)) {
					return false;
				}
				
				// Check if the args are correctly provided
				if(cmd.getMinArgs() > 0 && (args == null || args.length < cmd.getMinArgs())) {
					cmd.wrongUsage(sender, label);
					return false;
				}
				if(args != null && cmd.getMaxArgs() > 0 && args.length > cmd.getMaxArgs()) {
					cmd.wrongUsage(sender, label);
					return false;
				}

				// Execute the command
				try {
					cmd.execute(sender, args, label);
				}catch(Exception e) {
					sender.sendMessage(LangManager.getMessage("commands.errors.unknown"));
					e.printStackTrace();
					return false;
				}
			}
		}
		
		return false;
	}

	/** 
	 * Check if the player has all the valid requirements for a certain command
	 * 
	 * @param CommandSender the command sender (console / player)
	 * @param Command the command object
	 * */
	public boolean validRequirements(CommandSender sender, Command cmd) {
		for(CommandRequirement rq : cmd.getRequirements()) {
			if(!rq.isValid(sender)) {
				rq.sendError(sender);
				return false;
			}
		}
		return true;
	}
	
	/* 
	 * Getters & Setters
	 * */
	
	public Set<Command> getCommands() {
		return commands;
	}
	
}
