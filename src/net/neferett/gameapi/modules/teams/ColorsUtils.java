package net.neferett.gameapi.modules.teams;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum ColorsUtils {

	ORANGE(ChatColor.GOLD,(byte)14),
	VIOLET_CLAIR(ChatColor.LIGHT_PURPLE,(byte)13),
	AQUA(ChatColor.AQUA,(byte)12),
	JAUNE(ChatColor.YELLOW,(byte)11),
	VERT_CLAIR(ChatColor.GREEN,(byte)10),
	ROUGE_CLAIR(ChatColor.RED,(byte)1),
	GRIS(ChatColor.DARK_GRAY,(byte)8),
	GRIS_CLAIR(ChatColor.GRAY,(byte)7),
	BLEU_CLAIR(ChatColor.BLUE,(byte)4),
	VERT(ChatColor.DARK_GREEN,(byte)2);
	
	
	ChatColor color;
	byte data;
	
	private ColorsUtils(ChatColor color,byte data) {
		this.color = color;
		this.data = data;
	}
	
	public ChatColor getColor() {
		return color;
	}
	
	public byte getData() {
		return data;
	}
	
	public static ColorsUtils get(ChatColor color) {
		for (ColorsUtils bu : ColorsUtils.values()) {
			if (bu.getColor().equals(color)) return bu;
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public ItemStack get() {
		return new ItemStack(425,1,this.data);
	}
	
	@SuppressWarnings("deprecation")
	public ItemStack get(String name) {
		ItemStack item = new ItemStack(425,1,this.data);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(name);
		item.setItemMeta(itemMeta);
		return item;
	}
	
}
