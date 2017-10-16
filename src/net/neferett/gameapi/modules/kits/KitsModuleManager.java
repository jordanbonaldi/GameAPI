package net.neferett.gameapi.modules.kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class KitsModuleManager {
	
	private List<Kit> kits = new ArrayList<Kit>();
	private int slot = 4;
	
	public List<Kit> getKits() {
		return kits;
	}
	
	public List<Kit> getHaveKits(Player p) {
		List<Kit> kits = new ArrayList<Kit>();
		for (Kit kit : this.kits) {
			if (kit.haveKit(p)) kits.add(kit);
		}
		return kits;
	}
	
	public abstract void inits();
	
	public void register(Kit kit) {
		kits.add(kit);
	}
	
	public boolean exist(String name) {
		for (Kit kit : kits) 
			if (kit.getName().equals(name)) return true;
		return false;
	}

	public Kit getKit(String name) {
		for (Kit kit : kits) 
			if (kit.getName().equals(name)) return kit;
		return null;
	}
	
	public Kit getKit(ItemStack item,Player p) {
		for (Kit kit : kits) 
			if (kit.getItemUI(p).isSimilar(item)) return kit;
		return null;
	}
	
	public Kit getKitByID(String uuid) {
		for (Kit kit : kits) 
			if (kit.getUuid().equals(uuid)) return kit;
		return null;
	}
	
	public void setSlot(int slot) {
		this.slot = slot;
	}
	
	public int getSlot() {
		return slot;
	}

}
