package net.neferett.gameapi.modules.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GamePreStartTickEvent extends Event {

	private static final HandlerList	handlers	= new HandlerList();

	private int time;
	private String message;
	private String title;
	private String subTitle;

	public GamePreStartTickEvent(int time) {
		this.time = time;
	}

	public int getTime() {
		return time;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getSubTitle() {
		return subTitle;
	}

	public String getMessage() {
		return message;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
