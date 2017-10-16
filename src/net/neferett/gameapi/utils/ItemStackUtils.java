package net.neferett.gameapi.utils;

import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;

public class ItemStackUtils {

	public static ItemStack addGlow(ItemStack item) {
		net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
		NBTTagCompound tag = null;
		if (!nmsStack.hasTag()) {
			tag = new NBTTagCompound();
			nmsStack.setTag(tag);
		}
		if (tag == null)
			tag = nmsStack.getTag();
		NBTTagList ench = new NBTTagList();
		tag.set("ench", ench);
		nmsStack.setTag(tag);
		return CraftItemStack.asCraftMirror(nmsStack);
	}
	
	 public static ItemStack createItem(Material leatherPiece, String displayName, Color color) {
		   ItemStack item = new ItemStack(leatherPiece);
		   LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		   meta.setDisplayName(displayName);
		   meta.setColor(Color.fromRGB(color.asRGB()));
		   item.setItemMeta(meta);
		   return item;
	}

	public static Boolean hasDisplayName(ItemStack itemStack) {
		if (!isValid(itemStack)) {
			return Boolean.valueOf(false);
		}
		if (!itemStack.hasItemMeta()) {
			return Boolean.valueOf(false);
		}
		if (!itemStack.getItemMeta().hasDisplayName()) {
			return Boolean.valueOf(false);
		}
		return Boolean.valueOf(true);
	}

	public static String getDisplayName(ItemStack itemStack) {
		if (hasDisplayName(itemStack).booleanValue()) {
			return itemStack.getItemMeta().getDisplayName();
		}
		return null;
	}

	public static boolean isValid(ItemStack itemStack) {
		return (itemStack != null) && (itemStack.getType() != Material.AIR)
				&& (itemStack.getItemMeta().hasDisplayName());
	}

	public static boolean isArmor(ItemStack item) {

		Material type = item.getType();

		String name = type.name();

		if (name.contains("CHESTPLATE") || name.contains("BOOTS") || name.contains("HELMET")
				|| name.contains("LEGGINGS"))
			return true;

		return false;

	}

	public static ItemStack create(Material material, byte data, int nb, String name, List<String> lore) {
		ItemStack item = new ItemStack(material, nb, data);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(name);
		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);
		item.setAmount(nb);
		return item;
	}

	@SuppressWarnings("deprecation")
	public static ItemStack create(int id, byte data, int nb, String name, List<String> lore) {
		ItemStack item = new ItemStack(id, nb, data);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(name);
		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);
		item.setAmount(nb);
		return item;
	}

}
