package xyz.mintydev.duels.core;

import java.util.Map;

import org.bukkit.inventory.ItemStack;

import xyz.mintydev.duels.util.ArmorPiece;

public class Kit {

	private final String id, displayName;
	private final Map<ArmorPiece, ItemStack> armorContents;
	private final Map<Integer, ItemStack> inventoryContents;
	
	public Kit(String id, String displayName, Map<ArmorPiece, ItemStack> armorContents, Map<Integer, ItemStack> inventoryContents) {
		this.id = id;
		this.displayName = displayName;
		this.armorContents = armorContents;
		this.inventoryContents = inventoryContents;
	}

	public String getId() {
		return id;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public Map<ArmorPiece, ItemStack> getArmorContents() {
		return armorContents;
	}
	
	public Map<Integer, ItemStack> getInventoryContents() {
		return inventoryContents;
	}
	
}
