package xyz.mintydev.duels.managers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import xyz.mintydev.duels.MINTDuels;
import xyz.mintydev.duels.core.Kit;
import xyz.mintydev.duels.util.ArmorPiece;
import xyz.mintydev.duels.util.config.ConfigUtil;

public class KitManager extends ConfigFileHandler {

	private final MINTDuels main;
	
	private Set<Kit> kits = new HashSet<>();
	
	public KitManager(MINTDuels main) {
		super(main, "kits.yml");
		this.main = main;
		
		loadKits();
	}
	
	private void loadKits() {
		final ConfigurationSection section = getDataFile().getConfigurationSection("kits");
		for(String kitID : section.getKeys(false)) {
			final ConfigurationSection sub = section.getConfigurationSection(kitID);
			
			final String displayName = sub.getString("name");
			
			final ConfigurationSection armorSec = sub.getConfigurationSection("armor");
			Map<ArmorPiece, ItemStack> armor = new HashMap<>();
			
			for(String pieceID : armorSec.getKeys(false)) {
				final ArmorPiece piece = ArmorPiece.valueOf(pieceID.toUpperCase());
				if(piece == null) continue;
				
				final ItemStack item = ConfigUtil.getItemFromSection(armorSec.getConfigurationSection(pieceID));
				if(item == null) continue;
				
				armor.put(piece, item);
			}
			
			Map<Integer, ItemStack> inventory = new HashMap<>();
			final ConfigurationSection invSec = sub.getConfigurationSection("inventory");
			for(String itemID : invSec.getKeys(false)) {
				final int slot = invSec.getInt(itemID + ".slot");
				final ItemStack item = ConfigUtil.getItemFromSection(invSec.getConfigurationSection(itemID));
				if(item == null) continue;
				
				inventory.put(slot, item);
			}
			
			final Kit kit = new Kit(kitID, displayName, armor, inventory, kitID.equalsIgnoreCase("default"));
			loadKit(kit);
		}
	}
	
	public void loadKit(Kit kit) {
		kits.add(kit);
		main.getLogger().info("[Kit] Loaded kit " + kit.getId() + " successfully.");
	}

	public Set<Kit> getKits() {
		return kits;
	}
	
}
