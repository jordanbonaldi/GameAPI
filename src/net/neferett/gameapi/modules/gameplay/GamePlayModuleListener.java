package net.neferett.gameapi.modules.gameplay;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.neferett.gameapi.modules.ModuleListener;
import net.neferett.linaris.utils.PlayerUtils;

public class GamePlayModuleListener extends ModuleListener {

	public GamePlayModuleListener() {
		super("GamePlay");
	}

	@EventHandler (priority = EventPriority.HIGHEST)
	public void onPlayerJoinGame(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		for (Player t : Bukkit.getOnlinePlayers()) {
			if (t.equals(p)) continue;
			t.hidePlayer(p);
		}
		e.setJoinMessage(new String(""));
		
		PlayerUtils.razPlayer(p);
		
		p.teleport(getGame().getGameManager().getLobbyLocation());
	

	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		e.setQuitMessage("");
	}

}
