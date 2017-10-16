package net.neferett.gameapi.gametasks;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import net.neferett.gameapi.AbstractGameManager;
import net.neferett.gameapi.Game;
import net.neferett.gameapi.GameTask;
import net.neferett.gameapi.modules.events.GamePreStartTickEvent;
import net.neferett.gameapi.utils.TitleUtils;

public class PreStartGameTask extends GameTask {

	public PreStartGameTask(final Game game) {
		super(game, game.getTimeToStart());
		this.sendTimeMessage();
	}

	@Override
	public void run() {
		final AbstractGameManager m = this.getGame().getGameManager();

		for (final Player p : Bukkit.getOnlinePlayers()) {

			p.setLevel(this.getTime());
			p.setExp(0);
		}

		if (this.getTime() == 60) this.sendTimeMessage();
		if (this.getTime() == 30) this.sendTimeMessage();

		if (this.getTime() <= 10) if (m.getNumberPlayers() >= this.getGame().getMinPlayer()) {

			if (this.getTime() == 60) this.sendTimeMessage();

			if (this.getTime() == 30) this.sendTimeMessage();

			if (this.getTime() == 10) this.sendTimeMessage();

			if (this.getTime() <= 5) if (this.getTime() == 0) {

				for (final Player p : Bukkit.getOnlinePlayers()) {
					p.playSound(p.getLocation(), Sound.WITHER_SPAWN, 2.5F, 3.5F);
					p.setLevel(0);
				}

				m.startGameProtected();

				this.cancel();

			} else {

				for (final Player p : Bukkit.getOnlinePlayers()) {
					p.setLevel(this.getTime());
					p.setExp(0);
				}

				this.sendTimeMessage();
				for (final Player player : Bukkit.getOnlinePlayers())
					player.playSound(player.getLocation(), Sound.NOTE_PLING, 1F, 1F);

			}

		} else {

			this.cancel();
			this.getGame().getGameManager().resetTask();
			for (final Player player : Bukkit.getOnlinePlayers()) {
				player.sendMessage("§5Pas assez de joueurs pour commencer la partie");
				player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1F, 1F);
			}

		}

		this.setTime(this.getTime() - 1);

	}

	public void sendMessage(final String message) {
		for (final Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(message);
			player.playSound(player.getLocation(), Sound.NOTE_STICKS, 1, 1F);
		}
	}

	public void sendTimeMessage() {
		final GamePreStartTickEvent event = new GamePreStartTickEvent(this.getTime());
		Bukkit.getServer().getPluginManager().callEvent(event);
		for (final Player player : Bukkit.getOnlinePlayers()) {
			player.playSound(player.getLocation(), Sound.NOTE_STICKS, 1, 1F);
			if (event.getMessage() != null) player.sendMessage(event.getMessage());
			if (event.getSubTitle() != null || event.getTitle() != null)
				TitleUtils.sendTitle(player, event.getTitle(), event.getSubTitle());
			// player.sendMessage("§6Debut du jeu dans §e" + getTime() +"
			// seconde(s).");
			// player.playSound(player.getLocation(), Sound.NOTE_STICKS, 1, 1F);
			// TitleUtils.sendTitle(player, "§r", "§6Debut du jeu dans §e" +
			// getTime());
		}
	}

}
