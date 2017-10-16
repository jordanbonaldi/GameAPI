package net.neferett.gameapi;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.neferett.gameapi.gametasks.FinishGameTask;
import net.neferett.gameapi.modules.teams.TeamObject;
import net.neferett.linaris.BukkitAPI;
import net.neferett.linaris.utils.PlayerUtils;
import net.neferett.linaris.utils.tasksmanager.TaskManager;

public abstract class AbstractGameManager {

	protected long		elapsedTime;
	protected Game		game;
	private GameState	gameState;
	private Location	lobbyLocation;
	private GameTask	task;
	protected Object	winner;

	public AbstractGameManager(final Game game) {
		this.game = game;
		this.gameState = GameState.PRESTART;
	}

	public void addElapsedTime() {
		this.elapsedTime++;
	}

	public abstract void gameFinish();

	public abstract void gameStart();

	public List<Player> getAlivesPlayers() {
		final List<Player> players = new ArrayList<>();
		for (final Player p : Bukkit.getOnlinePlayers())
			if (!this.isSpectator(p)) players.add(p);
		return players;
	}

	public long getElapsedTime() {
		return this.elapsedTime;
	}

	public Game getGame() {
		return this.game;
	}

	public GameState getGameState() {
		return this.gameState;
	}

	public GameTask getGameTask() {
		return this.task;
	}

	public Location getLobbyLocation() {
		return this.lobbyLocation;
	}

	public int getMaxPlayers() {
		return Bukkit.getMaxPlayers();
	}

	public int getNumberAlivePlayers() {
		return this.getAlivesPlayers().size();
	}

	public int getNumberPlayers() {
		return Bukkit.getOnlinePlayers().size();
	}

	public List<Player> getSpectatorsPlayers() {
		final List<Player> players = new ArrayList<>();
		for (final Player p : Bukkit.getOnlinePlayers())
			if (this.isSpectator(p)) players.add(p);
		return players;
	}

	public Object getWinner() {
		return this.winner;
	}

	public abstract void inits();

	public boolean isFinish() {
		return this.getGameState() == GameState.FINISH ? true : false;
	}

	public boolean isPreStart() {
		return this.getGameState() == GameState.PRESTART ? true : false;
	}

	public boolean isSpectator(final Player p) {
		return p.getGameMode() != GameMode.SPECTATOR && p.getGameMode() != GameMode.CREATIVE ? false : true;
	}

	public boolean isStarted() {
		return this.getGameState() == GameState.INGAME ? true : false;
	}

	public abstract void onFinish();

	public void resetTask() {
		if (this.task != null) this.task.cancel();
		this.task = null;
	}

	public void setElapsedTime(final long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public void setGameState(final GameState gameState) {
		this.gameState = gameState;
	}

	public void setGameTask(final GameTask task) {
		if (this.task != null) this.task.cancel();
		this.task = task;
		this.task.runTaskTimer(this.game, 0, 20);
	}

	public void setLobbyLocation(final Location lobbyLocation) {
		this.lobbyLocation = lobbyLocation;
	}

	public void startGameProtected() {
		this.setGameState(GameState.INGAME);
		if (this.getGameTask() != null) this.getGameTask().cancel();
		this.gameStart();
		this.setGameTask(this.game.getInGameTask());
		this.game.getModulesManager().unregisterModuleListener(this.game.getPreStartListener());
		this.game.getModulesManager().registerModuleListener(this.game.getGamePlayListener());

	}

	public abstract void testFinish();

	public void winGame(final Object winner) {
		if (this.isFinish()) return;

		this.winner = winner;

		this.setGameState(GameState.FINISH);

		if (winner instanceof Player) {

			final Player p = (Player) winner;

			for (final Player t : Bukkit.getOnlinePlayers()) {

				t.sendMessage("§6§lVictoire de " + p.getName()
						+ " §e§k|§b§k|§a§k|§c§k|§d§k|§e§k|§b§k|§a§k|§c§k|§d§k|§b§l Félicitations §e§k |§b§k|§a§k|§c§k|§d§k|§e§k|§b§k|§a§k|§c§k|§d§k|");
				t.getInventory().clear();
			}

		} else if (winner instanceof TeamObject) {

			final TeamObject t = (TeamObject) winner;

			for (final Player p : Bukkit.getOnlinePlayers()) {
				p.sendMessage("§6§lVictoire de l'équipe " + t.getColor() + t.getName()
						+ " §e§k|§b§k|§a§k|§c§k|§d§k|§e§k|§b§k|§a§k|§c§k|§d§k|§b§l Félicitations §e§k |§b§k|§a§k|§c§k|§d§k|§e§k|§b§k|§a§k|§c§k|§d§k|");
				p.getInventory().clear();
			}

		} else {

			for (final Player p : Bukkit.getOnlinePlayers()) {
				// p.sendMessage("§6§lFin de la partie
				// §e§k|§b§k|§a§k|§c§k|§d§k|§e§k|§b§k|§a§k|§c§k|§d§k|§b§l
				// Félicitations §e§k
				// |§b§k|§a§k|§c§k|§d§k|§e§k|§b§k|§a§k|§c§k|§d§k|");
				PlayerUtils.returnToHub(p);
				p.getInventory().clear();
				TaskManager.runTaskLater(() -> {
					BukkitAPI.get().reboot();

				}, 5 * 20);
			}
			this.onFinish();
			return;
		}
		this.onFinish();
		this.setGameTask(new FinishGameTask(this.game));;
		this.gameFinish();
	}
}
