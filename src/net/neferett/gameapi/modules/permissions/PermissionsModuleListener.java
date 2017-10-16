package net.neferett.gameapi.modules.permissions;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import net.neferett.gameapi.modules.ModuleListener;
import net.neferett.linaris.utils.PlayerUtils;

public class PermissionsModuleListener extends ModuleListener {

	public PermissionsModuleListener() {
		super("Permissions");
	}

	@EventHandler
	public void onBreakBlock(final BlockBreakEvent e) {
		if (!this.getGame().getPermissionsManager().blockBreak) e.setCancelled(true);
	}

	@EventHandler
	public void onCraft(final PrepareItemCraftEvent e) {
		if (!this.getGame().getPermissionsManager().craft) e.getInventory().setResult(null);
	}

	@EventHandler
	public void onDamage(final EntityDamageEvent e) {
		if (!this.getGame().getPermissionsManager().damagePlayer && e.getEntity() instanceof Player)
			e.setCancelled(true);
		if (!this.getGame().getPermissionsManager().damageMob && !(e.getEntity() instanceof Player))
			e.setCancelled(true);
		if (e.getEntity() instanceof Player) {
			final Player p = (Player) e.getEntity();
			if (p.getGameMode() == GameMode.SPECTATOR) e.setCancelled(true);
		}
	}

	@EventHandler
	public void onDamageEntity(final EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player
				&& !this.getGame().getPermissionsManager().pvp)
			e.setCancelled(true);

		if (e.getDamager() instanceof Projectile && !this.getGame().getPermissionsManager().pvp) {

			final Projectile proj = (Projectile) e.getDamager();

			if (proj.getShooter() == null) return;

			if (!(proj.getShooter() instanceof Player)) return;

			proj.getShooter();

			e.setCancelled(true);

		}
	}

	@EventHandler
	public void onEntityTarget(final EntityTargetLivingEntityEvent e) {
		if (!this.getGame().getPermissionsManager().entityTarget) e.setCancelled(true);
	}

	@EventHandler
	public void onFoodChange(final FoodLevelChangeEvent e) {
		if (!this.getGame().getPermissionsManager().food) e.setCancelled(true);
	}

	@EventHandler
	public void onInventoryClick(final InventoryClickEvent e) {
		if (!this.getGame().getPermissionsManager().inventoryClick) e.setCancelled(true);
	}

	@EventHandler
	public void onPlaceBlock(final BlockPlaceEvent e) {
		if (!this.getGame().getPermissionsManager().blockPlace) e.setCancelled(true);
	}

	@EventHandler
	public void onPlayerDead(final PlayerDeathEvent e) {

		if (!this.getGame().getPermissionsManager().deadDrop) e.getDrops().clear();

		if (!this.getGame().getPermissionsManager().autoRespawn) return;

		PlayerUtils.sendForceRespawn(e.getEntity(), 1);
	}

	@EventHandler
	public void onPlayerDrop(final PlayerDropItemEvent e) {
		if (!this.getGame().getPermissionsManager().drop) e.setCancelled(true);
	}

	@EventHandler
	public void onPlayerMove(final PlayerMoveEvent e) {
		if (!this.getGame().getPermissionsManager().move) e.getPlayer().teleport(e.getFrom());
		if (!this.getGame().getPermissionsManager().movex)
			if (e.getFrom().getBlock().getX() != e.getTo().getBlock().getX()
					|| e.getFrom().getBlock().getZ() != e.getTo().getBlock().getZ())
				e.getPlayer().teleport(e.getFrom());
		if (!this.getGame().getPermissionsManager().movey)
			if (e.getFrom().getBlock().getY() != e.getTo().getBlock().getY()) e.getPlayer().teleport(e.getFrom());
	}

	@EventHandler
	public void onPlayerPickupEvent(final PlayerPickupItemEvent e) {
		if (!this.getGame().getPermissionsManager().itemPickup) e.setCancelled(true);
	}

}
