package net.neferett.gameapi.modules.teams;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import net.neferett.gameapi.modules.ModuleListener;
import net.neferett.gameapi.utils.gui.GuiManager;
import net.neferett.linaris.utils.ItemStackUtils;

public class TeamsModuleListener extends ModuleListener {

	public TeamsModuleListener() {
		super("Teams");
	}
	
	@EventHandler (priority=EventPriority.LOWEST)
	public void onPlayerJoinGame(PlayerJoinEvent e) {
		TeamObject tt = getGame().getTeamManager().getComeTeam(e.getPlayer());
		if (tt == null) return;
		tt.joinTeam(e.getPlayer());
		tt.getComeMembers().remove(e.getPlayer().getName().toLowerCase());
	}

	@EventHandler
	public void onePlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (!ItemStackUtils.isValid(p.getItemInHand()))
			return;
		if (p.getItemInHand().getItemMeta() == null)
			return;
		if (!p.getItemInHand().getItemMeta().hasDisplayName())
			return;
		if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;
		if (!p.getItemInHand().getItemMeta().getDisplayName().equals(TeamGui.InvName))
			return;
		e.setCancelled(true);
		GuiManager.openGui(new TeamGui(p));
		p.getInventory().setHelmet(null);
		p.updateInventory();

	}

	@EventHandler
	public void onDamageEvent(EntityDamageByEntityEvent e) {
		if (getGame().isPreStart())
			return;
		if (!(e.getEntity() instanceof Player))
			return;

		Player p = (Player) e.getEntity();

		if (!getGame().getTeamManager().haveTeam(p))
			return;

		TeamObject pTeam = getGame().getTeamManager().getTeam(p);

		if (e.getDamager() instanceof Player) {

			Player target = (Player) e.getDamager();

			if (pTeam.getMembers().contains(target)) {

				e.setCancelled(true);
				return;

			}

		}
		if (e.getDamager() instanceof Projectile) {

			Projectile proj = (Projectile) e.getDamager();

			if (proj.getShooter() == null)
				return;

			if (!(proj.getShooter() instanceof Player))
				return;

			Player target = (Player) proj.getShooter();

			if (pTeam.getMembers().contains(target)) {

				e.setCancelled(true);
				return;

			}

		}

	}

}
