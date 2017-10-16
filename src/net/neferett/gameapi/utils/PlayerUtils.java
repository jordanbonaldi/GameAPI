package net.neferett.gameapi.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand.EnumClientCommand;
import net.neferett.gameapi.Game;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class PlayerUtils {

	public static void sendActionMessage(Player p, String msg) {
		IChatBaseComponent message = ChatSerializer.a("{\"text\": \"" + msg + "\"}");
		PacketPlayOutChat packet = new PacketPlayOutChat(message, (byte) 2);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}

	public static void setEntityCollision(Player player, Boolean value) {
		((CraftPlayer) player).getHandle().collidesWithEntities = value;
	}

	public static void setArrowsInBody(Player player, byte amount) {
		((CraftPlayer) player).getHandle().getDataWatcher().watch(9, new Byte(amount));
	}

	public static void razPlayer(Player player) {
		player.setMaxHealth(20);
		player.setExp(0);
		player.setLevel(0);
		player.setFallDistance(0);
		player.setFireTicks(0);
		clearFullInventory(player);
		resetPlayer(player);
	}

	public static void resetPlayer(Player player) {
		removePotionEffects(player);
		player.setHealth(player.getMaxHealth());
		player.setFoodLevel(20);
		player.setSaturation(20);
	}

	public static void clearFullInventory(Player player) {
		player.closeInventory();
		player.getInventory().clear();
		player.setItemInHand(new ItemStack(Material.AIR));
		player.getInventory().setHelmet(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setBoots(null);
	}

	public static void clearInventory(Player player) {
		player.closeInventory();
		player.getInventory().clear();
		player.setItemInHand(new ItemStack(Material.AIR));
	}
	
	public static void removePotionEffects(Player player) {
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
	}

	public static void resetArmorDurability(Player player) {
		for (ItemStack itemStack : player.getInventory().getArmorContents()) {
			if (itemStack != null && itemStack.getType() != Material.AIR) itemStack.setDurability((short) -1);
		}
	}

	public static void resetItemInHandDurability(Player player) {
		ItemStack itemStack = player.getItemInHand();
		if (ItemStackUtils.isValid(itemStack)) {
			itemStack.setDurability((short) 1);
			player.updateInventory(); 
		}
	}

	public static void consumItemInHand(Player player) {
		PlayerInventory inv = player.getInventory();
		int amount = inv.getItemInHand().getAmount();
		if (amount == 1) {
			inv.setItemInHand(new ItemStack(Material.AIR));
			return;
		}
		amount--;
		inv.getItemInHand().setAmount(amount);
	}

	public static void sendForceRespawn(Player player, int ticks) {
		final String playerName = player.getName();
		Bukkit.getScheduler().runTaskLater(Game.getGame(), new Runnable() {
			@Override
			public void run() {
				Player player = Bukkit.getPlayer(playerName);
				if (player != null && player.isDead() && player.isOnline()) ;
				PacketPlayInClientCommand packet = new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN);
				EntityPlayer ep = ((CraftPlayer) player).getHandle();
				if (ep.playerConnection != null && !ep.playerConnection.isDisconnected()) ep.playerConnection.a(packet);
			}
		}, ticks);
	}

}
