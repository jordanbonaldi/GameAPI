package net.neferett.gameapi.modules.teams;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.neferett.gameapi.Game;

public abstract class TeamsModuleManager {

	Game game;
	Class<? extends TeamObject> classTeam;
	boolean selector;
	
	public TeamsModuleManager(Game game,Class<? extends TeamObject> classTeam) {
		this.game = game;
		this.classTeam = classTeam;
		this.selector = true;
	}
	
	public void setSelector(boolean selector) {
		this.selector = selector;
	}
	
	public boolean getSelector() {
		return this.selector;
	}
	
	public Game getGame() {
		return game;
	}


	private ArrayList<TeamObject> teams = new ArrayList<TeamObject>();
	private int maxPlayers;	
	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}
	public int getMaxPlayers() {
		return maxPlayers;
	}
	
	public abstract void inits();
	
	public void proctectedInits() {
			if (!teams.isEmpty())
			this.maxPlayers = Bukkit.getServer().getMaxPlayers()/teams.size();		
	}
	

	public ArrayList<TeamObject> getTeams() {
		return teams;
	}
	
	public TeamObject registerTeam(String name,ChatColor color) {
		if (teamExist(name)) return null;
		try {
			Constructor<?> contructor = classTeam.getConstructor(String.class,ChatColor.class);
			TeamObject team = (TeamObject) contructor.newInstance(name,color);
			teams.add(team);
			return team;
		} catch (Exception e) {
			return null;
		}
	}
	
	public boolean unregisterTeam(String name) {
		if (!teamExist(name)) return false;
		teams.remove(getTeam(name));
		return true;
	}
	
	public boolean teamExist(String name) {
		if (getTeam(name) == null) return false;
		return true;
	}
	
	public TeamObject getTeam(String name) {
		for (TeamObject team : getTeams()) {
			if (team.getName().equals(name)) return team;
		}
		return null;
	}
	
	public TeamObject getTeam(Player p) {
		for (TeamObject team : getTeams()) {
			for (Player t : team.getMembers()) {
				if (t.getName().equals(p.getName())) return team;
			}
		}
		return null;
	}
	
	public TeamObject getComeTeam(Player p) {
		for (TeamObject team : getTeams()) {
			if (team.containsComePlayer(p.getName())) return team;
		}
		return null;
	}
	
	public boolean haveTeam(Player p) {
		if (getTeam(p) == null) return false;
		return true;
	}
	
	public boolean inTeam(String name,Player p) {
		if (!teamExist(name)) return false;
		if (!haveTeam(p)) return false;
		if (!getTeam(name).getName().equals(getTeam(p).getName())) return false;
		return true;
	}
}
