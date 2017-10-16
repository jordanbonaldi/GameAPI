package net.neferett.gameapi.modules.prestart;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.neferett.gameapi.AbstractGameManager;
import net.neferett.gameapi.gametasks.PreStartGameTask;
import net.neferett.gameapi.modules.ModuleListener;
import net.neferett.gameapi.modules.kits.KitsModuleListener;
import net.neferett.gameapi.modules.teams.TeamGui;
import net.neferett.gameapi.modules.teams.TeamObject;
import net.neferett.linaris.spectator.GamePosHider;
import net.neferett.linaris.utils.PlayerUtils;

public class PreStartModuleListener extends ModuleListener {

	public PreStartModuleListener() {
		super("PreStart");
	}

	@EventHandler
	public void entitySpawn(EntitySpawnEvent e) {
		e.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoinGame(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		AbstractGameManager m = getGame().getGameManager();
		p.setGameMode(GameMode.ADVENTURE);
		e.setJoinMessage(getGame().playerJoinMessage(p, m.getNumberPlayers(), m.getMaxPlayers()));

		GamePosHider.showPos(p);
		PlayerUtils.razPlayer(p);
		if (getGame().useTeam() && getGame().getTeamManager().getSelector())
			p.getInventory().setItem(1, TeamGui.getItem());
		if (getGame().useKits())
			p.getInventory().setItem(getGame().getKitsManager().getSlot(), KitsModuleListener.getItem());

		PlayerUtils.givePlayerBackToHubItem(p);

		p.teleport(getGame().getGameManager().getLobbyLocation());

		if (m.getNumberPlayers() >= getGame().getMinPlayer() && m.getGameTask() == null) {
			m.setGameTask(new PreStartGameTask(getGame()));
		}
		if (m.getNumberPlayers() == getGame().getMaxPlayer() && m.getGameTask() != null) {
			m.getGameTask().setTime(10);
		}

	}

	@EventHandler
	public void onPlayerQuitGame(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if (getGame().useTeam()) {
			if (getGame().getTeamManager().haveTeam(p)) {
				TeamObject t = getGame().getTeamManager().getTeam(p);
				t.quitTeam(p);
			}
		}
		AbstractGameManager m = getGame().getGameManager();
		e.setQuitMessage(getGame().playerQuitMessage(p, m.getNumberPlayers()-1, m.getMaxPlayers()));
	}

}
