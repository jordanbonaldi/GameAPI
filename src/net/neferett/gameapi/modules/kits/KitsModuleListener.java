package net.neferett.gameapi.modules.kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import net.neferett.gameapi.modules.ModuleListener;
import net.neferett.gameapi.utils.ItemStackUtils;
import net.neferett.gameapi.utils.NBTItem;
import net.neferett.gameapi.utils.gui.GuiManager;
import net.neferett.linaris.BukkitAPI;
import net.neferett.linaris.api.PlayerLocal;
import net.neferett.linaris.api.SettingsManager;

public class KitsModuleListener extends ModuleListener {

	public static ItemStack getItem() {
		return ItemStackUtils.create(Material.NAME_TAG, (byte) 0, 1, "§6Choisir un kit §7(Clic-droit)", null);
	}

	public KitsModuleListener() {
		super("kits");
	}

	@EventHandler
	public void Interact(final PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		if (!ItemStackUtils.isValid(p.getItemInHand())) return;
		if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if (p.getItemInHand().isSimilar(getItem())) GuiManager.openGui(new KitsGui(p));
	}

	@EventHandler
	public void onDrop(final PlayerDropItemEvent e) {
		if (ItemStackUtils.isValid(e.getItemDrop().getItemStack())) ;
		if (new NBTItem(e.getItemDrop().getItemStack()).hasKey("kit")) e.setCancelled(true);
	}

	@EventHandler
	public void onPlayer(final PlayerDeathEvent e) {
		final PlayerLocal pl = PlayerLocal.getPlayer(e.getEntity().getName());
		final List<ItemStack> items = new ArrayList<>();
		items.addAll(e.getDrops());
		for (final ItemStack item : e.getDrops())
			if (new NBTItem(item).hasKey("kit")) items.remove(item);
		e.getDrops().clear();
		for (final ItemStack item : items)
			e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), item);
		if (!pl.contains("kit")) return;
		final Kit kit = this.getGame().getKitsManager().getKitByID(pl.get("kit"));
		if (kit != null) kit.onDead(e.getEntity());
	}

	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent e) {
		BukkitAPI.get().getTasksManager().addTask(() -> {

			if (SettingsManager.isSetting(e.getPlayer().getName(), this.getGame().getGameID(), "kit"))
				PlayerLocal.getPlayer(e.getPlayer().getName()).set("kit",
						SettingsManager.getSetting(e.getPlayer().getName(), this.getGame().getGameID(), "kit"));

			final Player p = e.getPlayer();
			final PlayerLocal pl = PlayerLocal.getPlayer(p.getName());
			if (pl.contains("kit")) {
				final Kit kit = this.getGame().getKitsManager().getKitByID(pl.get("kit"));
				if (kit != null)
					p.sendMessage("§6Votre kit : §e" + kit.getName() + ".");
				else {
					pl.remove("kit");
					SettingsManager.removeSetting(p.getName(), this.getGame().getGameID(), "kit");
				}
			}
		});
	}

	@EventHandler
	public void onPlayerRespawn(final PlayerRespawnEvent e) {
		final Player p = e.getPlayer();
		final PlayerLocal pl = PlayerLocal.getPlayer(p.getName());
		if (this.getGame().getGameManager().isSpectator(p)) return;
		if (pl.contains("kit")) {
			final Kit kit = this.getGame().getKitsManager().getKitByID(pl.get("kit"));
			if (kit != null) kit.giveKit(p);
		}

		p.updateInventory();

	}

}
