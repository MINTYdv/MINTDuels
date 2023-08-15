package xyz.mintydev.duels.util.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.mintydev.duels.managers.LangManager;

public class PlayerRequirement extends CommandRequirement {
	
	@Override
	public void sendError(CommandSender sender) {
		sender.sendMessage(LangManager.getMessage("commands.errors.not-player"));
	}
	
	@Override
	public boolean isValid(CommandSender sender) {
		return sender instanceof Player;
	}

}
