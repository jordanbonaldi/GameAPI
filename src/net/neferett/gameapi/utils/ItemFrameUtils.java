package net.neferett.gameapi.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityItemFrame;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.WorldServer;

public class ItemFrameUtils {


	public static List<ItemFrame> surroundWithItemFrame(Location location, ItemStack itemStack) {
		List<ItemFrame> list = new ArrayList<ItemFrame>();
		location.getChunk().load(false);
		// North
		list.add(spawnItemFrame(location, itemStack, BlockFace.NORTH));
		list.add(spawnItemFrame(location, itemStack, BlockFace.SOUTH));
		list.add(spawnItemFrame(location, itemStack, BlockFace.EAST));
		list.add(spawnItemFrame(location, itemStack, BlockFace.WEST));
		return list;
	}

	public static ItemFrame spawnItemFrame(Location location, ItemStack itemStack, BlockFace face) {
		location.getChunk().load(false);

		World world = location.getWorld();
		WorldServer mcWorld = ((CraftWorld) world).getHandle();

		Entity entity = null;

		BlockPosition position = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
		EnumDirection direction = null;

		if (face.equals(BlockFace.NORTH)) {
			position = position.north();
			direction = EnumDirection.NORTH;
		}
		else if (face.equals(BlockFace.SOUTH)) {
			position = position.south();
			direction = EnumDirection.SOUTH;
		}
		else if (face.equals(BlockFace.EAST)) {
			position = position.east();
			direction = EnumDirection.EAST;
		}
		else if (face.equals(BlockFace.WEST)) {
			position = position.west();
			direction = EnumDirection.WEST;
		}

		if (direction == null) {
			Bukkit.getLogger().info("Erreur d'itemFrame, BlockFace impossible > " + face);
			return null;
		}

		entity = new EntityItemFrame(mcWorld, position, direction);
		mcWorld.addEntity(entity, SpawnReason.CUSTOM);
		ItemFrame itemFrame = (ItemFrame) entity.getBukkitEntity();
		itemFrame.setItem(itemStack);
		return itemFrame;
	}
}
