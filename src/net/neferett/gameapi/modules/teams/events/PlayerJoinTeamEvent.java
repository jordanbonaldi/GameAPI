package net.neferett.gameapi.modules.teams.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import net.neferett.gameapi.modules.teams.TeamObject;


public class PlayerJoinTeamEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	private Player player;
	private TeamObject team;
	private boolean cancelled;
	
	public PlayerJoinTeamEvent(Player player, TeamObject team, boolean who) {
		super(who);
		this.player = player;
		this.team = team;
	}

	public TeamObject getTeam() {
		return team;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public HandlerList getHandlers()
	{
	  return handlers;
	}
	  
	public static HandlerList getHandlerList()
	{
	  return handlers;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean arg0) {
		this.cancelled = arg0;
	}
}
