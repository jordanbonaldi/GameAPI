package net.neferett.gameapi.commands;

import net.neferett.linaris.api.ranks.RankAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.neferett.gameapi.Game;
import net.neferett.linaris.BukkitAPI;
import net.neferett.linaris.api.PlayerData;

public class GameComand implements CommandExecutor {

	Game game;

	public GameComand(final Game game) {
		this.game = game;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

		if (!(sender instanceof Player))
			return true;

		final Player p = (Player) sender;

		if (label.equalsIgnoreCase("game")) {
			BukkitAPI.get().getTasksManager().addTask(() -> {

				final PlayerData pd = BukkitAPI.get().getPlayerDataManager().getPlayerData(p.getName());

				final RankAPI rank = pd.getRank();

				if (rank.getModerationLevel() < 3) {
					p.sendMessage("�cVous ne pouvez pas utiliser cette commande !");
					return;
				}

				if (args.length == 0) {
					p.sendMessage("�e/game start");
					return;
				}

				if (args.length == 1)
					if (args[0].equalsIgnoreCase("start")) {
						p.sendMessage("�aGame Started");
						new BukkitRunnable() {

							@Override
							public void run() {
								GameComand.this.game.getGameManager().startGameProtected();

							}
						}.runTask(this.game);

						return;
					}

			});
			return true;

		}

		return true;
	}

}
