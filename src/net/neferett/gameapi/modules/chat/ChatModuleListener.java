package net.neferett.gameapi.modules.chat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.neferett.gameapi.modules.ModuleListener;
import net.neferett.gameapi.modules.teams.TeamObject;
import net.neferett.gameapi.utils.ScoreboardSign;

public class ChatModuleListener extends ModuleListener {

	public ChatModuleListener() {
		super("Chat");
	}

	@EventHandler
	public void onPlayerQuit(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();

		if (getGame().isStarted()) {
			e.setCancelled(true);

			if (getGame().getGameManager().isSpectator(p)) {
				SpectatorSpecEvent event = new SpectatorSpecEvent(p, e.getMessage());
				Bukkit.getPluginManager().callEvent(event);
				return;
			}
			
			if (getGame().useTeam()) {

				if (getGame().getTeamManager().haveTeam(p)) {
					
					TeamObject team = getGame().getTeamManager().getTeam(p);
					
					if (e.getMessage().startsWith("!")) {
						sendMessage(team.getColor() + "(Tous) " + p.getName() + ": §f" + e.getMessage().replaceFirst("!", "").trim());
					} else {
						team.sendChatMessage(p.getName(), e.getMessage());
					}
					
				} else {
					
					e.setCancelled(false);
					
				}
				
			} else {

				sendMessage("§7[§fJoueur§7] §f" + p.getName() + ": " + e.getMessage());

			}

		}
	}

	public void sendMessage(String s) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.sendMessage(s);
		}
	}

	public void sendSpectatorMessage(String s) {
		for (Player p : getGame().getGameManager().getSpectatorsPlayers()) {
			p.sendMessage(s);
		}
	}
	
	@EventHandler
	public void playerQuit(PlayerQuitEvent e) {
		ScoreboardSign.get(e.getPlayer()).destroy();
	}

}
