package net.neferett.gameapi.utils;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;

import net.neferett.gameapi.Game;

public class FloatingTextUtils {

	static int	fakeID	= -100;

	public static void displayDamageTaken(final Player player) {
		Bukkit.getScheduler().runTaskLater(Game.getGame(), new Runnable() {
			double	life	= player.getHealth();
			@Override
			public void run() {
				if (player.isValid()) {
					double damages = life - player.getHealth();
					if (damages > 0) displayFloatingTextAtLocation(player.getEyeLocation().add(0, 1, 0), "§c-" + String.format("%.2f", damages), 0.5D, 0.20D);
				}
			}
		}, 1);
	}

	public static void displayHealReceived(final Player player) {
		Bukkit.getScheduler().runTaskLater(Game.getGame(), new Runnable() {
			double	life	= player.getHealth();
			@Override
			public void run() {
				if (player.isValid()) {
					double health = player.getHealth() - life;
					if (health > 0) displayFloatingTextAtLocation(player.getEyeLocation().add(0, 1, 0), "§a+" + String.format("%.2f", health), 1.5, 0.20D);
				}
			}
		}, 1);
	}

	public static void displayFloatingTextForPlayerAtLocation(Player player, Location location, String text, double timer, double offset) {
		if (offset > 0) {
			location.add(MathUtils.doubleRandomInclusive(offset, -offset), MathUtils.doubleRandomInclusive(offset, -offset), MathUtils.doubleRandomInclusive(offset, -offset));
		}
		sendFloatingTextToPlayer(location, text, player, timer);
	}

	public static void displayFloatingTextAtLocation(Location location, String text, double timer, double offset) {
		if (offset > 0) {
			location.add(MathUtils.doubleRandomInclusive(offset, -offset), MathUtils.doubleRandomInclusive(offset, -offset), MathUtils.doubleRandomInclusive(offset, -offset));
		}
		sendFloatingTextToPlayers(location, text, Bukkit.getOnlinePlayers(), timer);
	}

	@SuppressWarnings("deprecation")
	private static void sendFloatingTextToPlayers(Location location, String text, Collection<? extends Player> collection, double timer) {
		location.add(0, -1.2, 0);
		final int id = fakeID--;
		PacketContainer armorStand = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY_LIVING);
		armorStand.getModifier().writeDefaults();
		armorStand.getIntegers().write(0, Integer.valueOf(Integer.valueOf(id))); // ID
		armorStand.getIntegers().write(1, Integer.valueOf(Integer.valueOf(EntityType.ARMOR_STAND.getTypeId()))); // ID
		armorStand.getIntegers().write(2, Integer.valueOf((int) Math.floor(location.getX() * 32.0D)));
		armorStand.getIntegers().write(3, Integer.valueOf((int) Math.floor(location.getY() * 32.0D)));
		armorStand.getIntegers().write(4, Integer.valueOf((int) Math.floor(location.getZ() * 32.0D)));
		// Sinon crash
		WrappedDataWatcher watcher = new WrappedDataWatcher();
		// Invisible ?
		watcher.setObject(0, Byte.valueOf((byte) 32)); // Invisible
		watcher.setObject(6, Float.valueOf(0.5F)); // Vie
		watcher.setObject(2, String.valueOf(text)); // name
		watcher.setObject(3, Byte.valueOf((byte) 01)); // name visible ?
		watcher.setObject(10, Byte.valueOf((byte) 1)); // Small stand

		armorStand.getDataWatcherModifier().write(0, watcher); // DataWatcher

		for (final Player player : collection) {
			if (location.distanceSquared(player.getLocation()) > 400) /* 20blocs */continue;
			else {
				PLib.sendPacket(player, armorStand);
				Bukkit.getScheduler().runTaskLater(Game.getGame(), new Runnable() {
					@Override
					public void run() {
						if (player.isValid()) {
							PacketContainer destroy = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
							destroy.getIntegerArrays().write(0, new int[]{id});
							PLib.sendPacket(player, destroy);
						}
					}
				}, (long) (20 * timer));
			}
		}
	}
	/**
	 * Affiche un texte flottant Ã  un seul joueur
	 * @param location
	 * @param text
	 * @param player
	 * @param timer
	 */
	@SuppressWarnings("deprecation")
	private static void sendFloatingTextToPlayer(Location location, final String text, final Player player, double timer) {
		location.add(0, -2.5, 0);
		final int id = fakeID--;
		PacketContainer armorStand = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY_LIVING);
		armorStand.getModifier().writeDefaults();
		armorStand.getIntegers().write(0, Integer.valueOf(Integer.valueOf(id))); // ID
		armorStand.getIntegers().write(1, Integer.valueOf(Integer.valueOf(EntityType.ARMOR_STAND.getTypeId()))); // ID
		armorStand.getIntegers().write(2, Integer.valueOf((int) Math.floor(location.getX() * 32.0D)));
		armorStand.getIntegers().write(3, Integer.valueOf((int) Math.floor(location.getY() * 32.0D)));
		armorStand.getIntegers().write(4, Integer.valueOf((int) Math.floor(location.getZ() * 32.0D)));
		// Sinon crash
		WrappedDataWatcher watcher = new WrappedDataWatcher();
		// Invisible ?
		watcher.setObject(0, Byte.valueOf((byte) 32)); // Invisible
		watcher.setObject(6, Float.valueOf(0.5F)); // Vie
		watcher.setObject(2, String.valueOf(text)); // name
		watcher.setObject(3, Byte.valueOf((byte) 01)); // name visible ?
		watcher.setObject(10, Byte.valueOf((byte) 1)); // Small stand

		armorStand.getDataWatcherModifier().write(0, watcher); // DataWatcher

		PLib.sendPacket(player, armorStand);
		// Destroy
		Bukkit.getScheduler().runTaskLater(Game.getGame(), new Runnable() {
			@Override
			public void run() {
				if (player.isValid()) {
					PacketContainer destroy = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
					destroy.getIntegerArrays().write(0, new int[]{id});
					PLib.sendPacket(player, destroy);
				}
			}
		}, (long) (20 * timer));
	}
}
