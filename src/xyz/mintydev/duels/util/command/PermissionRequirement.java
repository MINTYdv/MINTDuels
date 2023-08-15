package xyz.mintydev.duels.util.command;

import org.bukkit.command.CommandSender;

import xyz.mintydev.duels.managers.LangManager;

public class PermissionRequirement extends CommandRequirement {

	private final String permission;
	
	public PermissionRequirement(String permission) {
		this.permission = permission;
	}
	
	@Override
	public void sendError(CommandSender sender) {
		sender.sendMessage(LangManager.getMessage("commands.errors.no-permission").replaceAll("%perm%", permission));
	}
	
	@Override
	public boolean isValid(CommandSender sender) {
		return sender.hasPermission(permission);
	}

}
