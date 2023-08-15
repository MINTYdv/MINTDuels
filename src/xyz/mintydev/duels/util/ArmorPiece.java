package xyz.mintydev.duels.util;

import org.bukkit.inventory.ItemStack;

public enum ArmorPiece {

	HELMET("helmet"),
	CHESTPLATE("chestplate"),
	LEGGINGS("leggings"),
	BOOTS("boots");
	
	private final String id;
	
	ArmorPiece(String id){
		this.id =id;
	}
	
	public static ArmorPiece get(ItemStack item) {
		final String mat = item.getType().name().toString().toLowerCase();
		if(mat.contains("helmet")) return HELMET;
		if(mat.contains("chestplate")) return CHESTPLATE;
		if(mat.contains("leggings")) return LEGGINGS;
		if(mat.contains("boots")) return BOOTS;
		return null;
	}
	
	public String getId() {
		return id;
	}
	
}
