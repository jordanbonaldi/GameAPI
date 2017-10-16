package net.neferett.gameapi.modules.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.neferett.gameapi.Game;

public abstract class ScoreBoardModule {

	Game game;
	
	public ScoreBoardModule(Game game) {
		this.game = game;
	}
	
	public void update() {
		onUpdate();
		for (Player p : Bukkit.getOnlinePlayers())
			onUpdate(p);
		}
	
	public Game getGame() {
		return game;
	}
	
	

	public abstract void onUpdate();
	public abstract void onUpdate(Player p);

}
