package net.neferett.gameapi.modules;

import org.bukkit.event.Listener;

import net.neferett.gameapi.Game;

public class ModuleListener implements Listener {

	private Game game;
	private String name;
	
	public ModuleListener(String name) {
		this.name = name;
	}
	
	public void setGame(Game game) {
		this.game = game;
	}
	
	public Game getGame() {
		return game;
	}
	
	public String getName() {
		return name;
	}

}
