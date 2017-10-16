package net.neferett.gameapi.modules.kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class LevelInfo {

	private List<String>	lore;
	private List<ItemStack> items;

	public LevelInfo(List<String> lore) {
		this.lore = lore;
		this.items = new ArrayList<ItemStack>();
	}
	
	public List<ItemStack> getItems() {
		return items;
	}
	
	public void addItem(ItemStack item) {
		items.add(item);
	}

	public List<String> getLore() {
		return lore;
	}

	public void setLore(List<String> lore) {
		this.lore = lore;
	}
	
	public void onGive(Player p) {
		
	};

}
