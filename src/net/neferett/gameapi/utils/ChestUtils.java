package net.neferett.gameapi.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockAction;
import net.minecraft.server.v1_8_R3.WorldServer;
import net.neferett.gameapi.Game;

public class ChestUtils {

	static int	visibleDistance	= Bukkit.getServer().getViewDistance() * 16;

	
	public static void playOpenChestAnimation(Location location) {
		WorldServer ws = ((CraftWorld) location.getWorld()).getHandle();
		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();
		BlockPosition position = new BlockPosition(x, y, z);
		IBlockData block = ws.getType(position);
		Packet<?> chestOpen = new PacketPlayOutBlockAction(position, block.getBlock(), 1, 54);
		for (Player player : LocationUtils.getClosestPlayersFromLocation(location, visibleDistance)) {
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(chestOpen);
		}
	}

	public static void playOpenChestAnimation(final Location location, final int ticks) {
		@SuppressWarnings("unused")
		final String taskName = "chestOpenLock_" + location.toVector();
		WorldServer ws = ((CraftWorld) location.getWorld()).getHandle();
		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();
		BlockPosition position = new BlockPosition(x, y, z);
		IBlockData block = ws.getType(position);
		final Packet<?> chestOpen = new PacketPlayOutBlockAction(position, block.getBlock(), 1, 54);
		final int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(Game.getGame(), new Runnable() {
			@Override
			public void run() {
				for (Player player : LocationUtils.getClosestPlayersFromLocation(location, visibleDistance)) {
					((CraftPlayer) player).getHandle().playerConnection.sendPacket(chestOpen);
				}
			}
		}, 0, 5);
		Bukkit.getScheduler().runTaskLater(Game.getGame(),new Runnable() {
			@Override
			public void run() {
				Bukkit.getScheduler().cancelTask(id);
			}
		}, ticks);
	}
}
