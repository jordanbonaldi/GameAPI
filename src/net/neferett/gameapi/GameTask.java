package net.neferett.gameapi;

import org.bukkit.scheduler.BukkitRunnable;

public abstract class GameTask extends BukkitRunnable {

	private Game game;
	private int time;
	
	public GameTask(Game game,int counter) {
		this.game = game;	    	 
	    this.time = counter;	        
	}
	
	public Game getGame() {
		return game;
	}
	
	public int getTime() {
		return time;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
}
