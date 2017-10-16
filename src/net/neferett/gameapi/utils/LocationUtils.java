package net.neferett.gameapi.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class LocationUtils {


	public static boolean isSafeLocation(Location location) {
		Block block = location.getBlock();
		return !block.getType().isSolid() || !block.getRelative(0, 1, 0).getType().isSolid();
	}


	public static ArrayList<Location> getCircle(Location center, double radius, int amount) {
		World world = center.getWorld();
		double increment = (2 * Math.PI) / amount;
		ArrayList<Location> locations = new ArrayList<Location>();
		for (int i = 0; i < amount; i++) {
			double angle = i * increment;
			double x = center.getX() + (radius * Math.cos(angle));
			double z = center.getZ() + (radius * Math.sin(angle));
			locations.add(new Location(world, x, center.getY(), z));
		}
		return locations;
	}

	public static ArrayList<Location> getCircle(Location center, double radius, int amount, String fixedCoord) {
		World world = center.getWorld();
		double increment = (2 * Math.PI) / amount;
		ArrayList<Location> locations = new ArrayList<Location>();
		double x, y, z;
		for (int i = 0; i < amount; i++) {
			double angle = i * increment;
			if (fixedCoord.equalsIgnoreCase("x")) {
				x = center.getX();
				y = center.getY() + (radius * Math.cos(angle));
				z = center.getZ() + (radius * Math.sin(angle));
			}
			else if (fixedCoord.equalsIgnoreCase("z")) {
				x = center.getX() + (radius * Math.cos(angle));
				y = center.getY() + (radius * Math.sin(angle));
				z = center.getZ();
			}
			else {
				x = center.getX() + (radius * Math.cos(angle));
				y = center.getY();
				z = center.getZ() + (radius * Math.sin(angle));
			}
			locations.add(new Location(world, x, y, z));
		}
		return locations;
	}

	public static List<Entity> getClosestEntitiesFromLocation(Location location, double radius) {
		double chunkRadius = radius < 16 ? 1 : (radius - (radius % 16)) / 16;
		List<Entity> radiusEntities = new ArrayList<Entity>();
		for (double chX = 0 - chunkRadius; chX <= chunkRadius; chX++) {
			for (double chZ = 0 - chunkRadius; chZ <= chunkRadius; chZ++) {
				int x = (int) location.getX(), y = (int) location.getY(), z = (int) location.getZ();
				for (Entity e : new Location(location.getWorld(), x + (chX * 16), y, z + (chZ * 16)).getChunk().getEntities()) {
					if (e.getLocation().distance(location) <= radius && e.getLocation().getBlock() != location.getBlock()) if (e instanceof Entity) radiusEntities.add((Entity) e);
				}
			}
		}
		return radiusEntities;
	}

	public static List<Player> getClosestPlayersFromLocation(Location location, double distance) {
		List<Player> result = new ArrayList<Player>();
		double d2 = distance * distance;
		for (Player player : location.getWorld().getPlayers()) {
			if (player.getLocation().add(0, 0.85D, 0).distanceSquared(location) <= d2) {
				result.add(player);
			}
		}
		return result;
	}

	public static Location lookAt(Location from, Location to, float aiming) {
		
		from = from.clone();
	
		double dx = to.getX() - from.getX();
		double dy = to.getY() - from.getY();
		double dz = to.getZ() - from.getZ();

		if (dx != 0) {
			
			if (dx < 0) {
				from.setYaw((float) (1.5 * Math.PI));
			}
			else {
				from.setYaw((float) (0.5 * Math.PI));
			}
			from.setYaw((float) from.getYaw() - (float) Math.atan(dz / dx));
		}
		else if (dz < 0) {
			from.setYaw((float) Math.PI);
		}

		double dxz = Math.sqrt(Math.pow(dx, 2) + Math.pow(dz, 2));
	
		from.setPitch((float) -Math.atan(dy / dxz));
		
		float yawAiming = 360 - (aiming * 360 / 100);
		if (yawAiming > 0) yawAiming = MathUtils.integerRandomInclusive((int) yawAiming, (int) -yawAiming);
		
		float pitchAiming = 180 - (aiming * 180 / 100);
		if (pitchAiming > 0) pitchAiming = MathUtils.integerRandomInclusive((int) pitchAiming, (int) -pitchAiming);

		from.setYaw(-from.getYaw() * 180f / (float) Math.PI + yawAiming);
		from.setPitch(from.getPitch() * 180f / (float) Math.PI + pitchAiming);
		return from;
	}
	
	public static List<Location> getLinePoints(Location start, Location end, int amount) {
		List<Location> points = new ArrayList<Location>();

		double k = start.getX();
		double j = start.getY();
		double n = start.getZ();

		double l = end.getX() - k;
		double h = end.getY() - j;
		double w = end.getZ() - n;

		double f1 = l / amount;
		double f2 = h / amount;
		double f3 = w / amount;

		for (int i = 0; i < amount; i++) {
			points.add(new Location(start.getWorld(), k + f1 * i, j + f2 * i, n + f3 * i));
		}

		return points;
	}
}
