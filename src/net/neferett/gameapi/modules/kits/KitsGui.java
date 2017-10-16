package net.neferett.gameapi.modules.kits;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.neferett.gameapi.Game;
import net.neferett.gameapi.utils.ItemStackUtils;
import net.neferett.gameapi.utils.gui.GuiScreen;
import net.neferett.linaris.BukkitAPI;
import net.neferett.linaris.api.PlayerLocal;
import net.neferett.linaris.api.SettingsManager;

public class KitsGui extends GuiScreen {

	public KitsGui(Player p) {
		super("", 1, p, false);
		PlayerLocal pl = PlayerLocal.getPlayer(getPlayer().getName());
		if (pl.contains("kit")) {
			Kit kit = Game.getGame().getKitsManager().getKitByID(pl.get("kit"));
			if (kit != null)
				setName("Sélection: §e" + kit.getName());
			else {
				setName("Sélection: §eChoisir");
				pl.remove("kit");
				BukkitAPI.get().getTasksManager().addTask(() -> {
					SettingsManager.removeSetting(p.getName(), Game.getGame().getGameID() , "kit");
				});
			}
		} else {
			setName("Sélection: §eChoisir");
		}

		build();
	}

	@Override
	public void drawScreen() {

		buildKits(getPlayer());

	}

	public void buildKits(Player player) {
		List<Kit> kits = Game.getGame().getKitsManager().getKits();
		for (Kit kit : kits)
			addItem(kit.getItemUI(player));
	}

	@Override
	public void onOpen() {
	}

	@Override
	public void onClick(ItemStack item, InventoryClickEvent event) {
		if (!ItemStackUtils.isValid(item))
			return;
		event.setCancelled(true);

		Kit kit = Game.getGame().getKitsManager().getKit(item, getPlayer());

		if (!kit.haveKit(getPlayer()))
			return;

		PlayerLocal pl = PlayerLocal.getPlayer(getPlayer().getName());
		pl.set("kit", kit.getUuid());

		BukkitAPI.get().getTasksManager().addTask(() -> {

			SettingsManager.setSetting(getPlayer().getName(), Game.getGame().getGameID(), "kit", kit.getUuid());

		});

		getPlayer().sendMessage("§6Votre kit : §e" + kit.getName() + ".");

		close();
	}

	@Override
	public void onClose() {
	}

}
