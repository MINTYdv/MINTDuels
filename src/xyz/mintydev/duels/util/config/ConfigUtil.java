package xyz.mintydev.duels.util.config;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class ConfigUtil {
	
	public static Location getLocationFromSection(ConfigurationSection sec, boolean round) {
		final String worldName = sec.getString("world");
		final double x = round ? sec.getInt("x") : sec.getDouble("x");
		final double y = round ? sec.getInt("y") : sec.getDouble("y");
		final double z = round ? sec.getInt("z") : sec.getDouble("z");
		
		float yaw = 0;
		float pitch = 0;
		if(sec.contains("yaw")) yaw = (float)sec.getDouble("yaw");
		if(sec.contains("pitch")) pitch = (float)sec.getDouble("pitch");
		
		return new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
	}
	
	public static ItemStack getItemFromSection(ConfigurationSection sec) {
		final String materialName = sec.getString("material");
		final Material material = Material.valueOf(materialName);
		
		int amount = 1;
		if(sec.contains("amount")) amount = sec.getInt("amount");
		if(amount < 0 || amount > 64) amount = 1;
		
		return new ItemStack(material, amount);
	}
	
}