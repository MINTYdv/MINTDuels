package xyz.mintydev.duels.core;

import org.bukkit.Location;
import org.bukkit.World;

import xyz.mintydev.duels.util.Cuboid;

public class Arena {

	private final String id, displayName;
	private final World world;
	private final Location spawn1, spawn2;
	
	private final Cuboid area;
	
	public Arena(String id, String displayName, World world, Location point1, Location point2, Location spawn1, Location spawn2) {
		this.id = id;
		this.displayName = displayName;
		this.world = world;
		this.spawn1 = spawn1;
		this.spawn2 = spawn2;
		
		this.area = new Cuboid(point1, point2);
	}

	public String getId() {
		return id;
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public Cuboid getArea() {
		return area;
	}
	
	public Location getSpawn1() {
		return spawn1;
	}
	
	public Location getSpawn2() {
		return spawn2;
	}
	
	public World getWorld() {
		return world;
	}
	
}
