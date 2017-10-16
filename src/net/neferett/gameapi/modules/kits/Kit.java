package net.neferett.gameapi.modules.kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.neferett.gameapi.utils.NBTItem;
import net.neferett.linaris.api.ShopItemsManager;

public class Kit {

	private String uuid;
	private String name;
	private List<LevelInfo> levelInfo;
	private boolean firstFree;
	String description;
	ItemStack item;
	
	public Kit(String uuid,String name,ItemStack item,String description,boolean firstFree) {
		this.uuid = uuid;
		this.name = name;
		this.item = item;
		this.levelInfo = new ArrayList<LevelInfo>();
		this.description = description;
		this.firstFree = firstFree;
	}

	public String getUuid() {
		return uuid;
	}
	
	public String getName() {
		return name;
	}

	public List<LevelInfo> getLevelInfos() {
		return levelInfo;
	}
	
	public List<ItemStack> getItems(int i) {
		if (firstFree)
			return levelInfo.get(i).getItems();
		else
			return levelInfo.get(i-1).getItems();
	}
	
	public String getDescription() {
		return description;
	}
	
	public ItemStack getItem() {
		return item;
	}
	
	public void addLevelInfo(LevelInfo lv) {
		levelInfo.add(lv);
	}

	public ItemStack getItemUI(Player p) {
		ItemStack item = getItem().clone();
		ItemMeta meta = item.getItemMeta();
		int level = getLevelKit(p);

		meta.setDisplayName("§e" + getName() + ((levelInfo.size() > 1) ? " §6" + getLevelKit(p) : ""));
		
		List<String> lore = new ArrayList<String>();
		
		lore.add("");
		
		if (firstFree) {
			lore.addAll(getLevelInfos().get(level).getLore());
		} else {
			if (level == 0) {
				lore.add("§cVous ne possédez pas,");
				lore.add("§c ce kit achetez le en");
				lore.add("§c boutique");
			} else {
				lore.addAll(getLevelInfos().get(level-1).getLore());
			}
		}
		
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public boolean haveKit(Player p) {		
		return (firstFree) ? true : ShopItemsManager.haveItem(p.getName(), uuid);
	}
	
	public int getLevelKit(Player p) {
		return ShopItemsManager.getItem(p.getName(), uuid).getLevel();
	}
	
	public void onFirstGive(Player p) {
		
	};
	
	public void onGive(Player p) {
		
	};
	
	public void onDead(Player p) {
		
	};
	
	public void giveKit(Player p) {
		if (!haveKit(p)) return;
		for (ItemStack item : getItems(getLevelKit(p))) {
			NBTItem i = new NBTItem(item);
			i.setBoolean("kit", true);
			p.getInventory().addItem(i.getItem());
		}
		onGive(p);
		
		if (firstFree) {
			getLevelInfos().get(getLevelKit(p)).onGive(p);
		} else {
			getLevelInfos().get(getLevelKit(p)-1).onGive(p);
		}
	}
}
