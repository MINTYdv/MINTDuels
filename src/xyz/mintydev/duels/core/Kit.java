package xyz.mintydev.duels.core;

import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import xyz.mintydev.duels.util.ArmorPiece;

public class Kit {

	private final String id, displayName;
	private final Map<ArmorPiece, ItemStack> armorContents;
	private final Map<Integer, ItemStack> inventoryContents;
	private final boolean isDefault;
	
	public Kit(String id, String displayName, Map<ArmorPiece, ItemStack> armorContents, Map<Integer, ItemStack> inventoryContents, boolean isDefault) {
		this.id = id;
		this.displayName = displayName;
		this.armorContents = armorContents;
		this.inventoryContents = inventoryContents;
		this.isDefault = isDefault;
	}

	public void give(Player player) {
		
		for(Entry<Integer, ItemStack> itemEntry : inventoryContents.entrySet()) {
			final Integer slot = itemEntry.getKey();
			final ItemStack item = itemEntry.getValue();
			
			player.getInventory().setItem(slot, item);
		}
		
		if(armorContents.get(ArmorPiece.HELMET) != null) {
			player.getInventory().setHelmet(armorContents.get(ArmorPiece.HELMET));
		}
		if(armorContents.get(ArmorPiece.CHESTPLATE) != null) {
			player.getInventory().setChestplate(armorContents.get(ArmorPiece.CHESTPLATE));
		}
		if(armorContents.get(ArmorPiece.LEGGINGS) != null) {
			player.getInventory().setLeggings(armorContents.get(ArmorPiece.LEGGINGS));
		}
		if(armorContents.get(ArmorPiece.BOOTS) != null) {
			player.getInventory().setBoots(armorContents.get(ArmorPiece.BOOTS));
		}
	}
	
	public String getId() {
		return id;
	}
	
	public boolean isDefault() {
		return isDefault;
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
