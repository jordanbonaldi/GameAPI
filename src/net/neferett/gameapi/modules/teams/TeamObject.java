package net.neferett.gameapi.modules.teams;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.neferett.gameapi.Game;
import net.neferett.gameapi.modules.teams.events.PlayerDieTeamEvent;
import net.neferett.gameapi.modules.teams.events.PlayerJoinTeamEvent;
import net.neferett.gameapi.modules.teams.events.PlayerLeaveTeamEvent;
import net.neferett.linaris.BukkitAPI;
import net.neferett.linaris.api.PlayerLocal;

public abstract class TeamObject {

	private final ChatColor			color;

	private final ArrayList<String>	comeMembers;
	private final ArrayList<Player>	members;
	private final String			name;
	private final String			prefix;
	private Location				spawn;
	protected TeamsModuleManager	teamManager;

	private String					teamPrefix;

	public TeamObject(final String name, final ChatColor color) {

		this.name = name;
		this.prefix = color + name;
		this.color = color;
		this.members = new ArrayList<>();
		this.teamPrefix = color.toString();
		this.teamManager = Game.getGame().getTeamManager();
		this.comeMembers = new ArrayList<>();
	}

	public boolean containsComePlayer(final String name) {
		for (final String op : new ArrayList<>(this.comeMembers))
			if (op.equalsIgnoreCase(name)) return true;
		return false;
	}

	public void die(final Player p) {
		if (!this.inTeam(p)) return;

		final PlayerDieTeamEvent event = new PlayerDieTeamEvent(p, this, false);
		Game.getGame().getServer().getPluginManager().callEvent(event);
		if (event.isCancelled()) return;

		this.members.remove(p);
		final PlayerLocal wpl = BukkitAPI.get().getPlayerLocalManager().getPlayerLocal(p.getName());
		if (wpl != null) wpl.setPrefix("");

	}

	public ChatColor getColor() {
		return this.color;
	}

	public ArrayList<String> getComeMembers() {
		return this.comeMembers;
	}

	public ArrayList<Player> getMembers() {
		return this.members;
	}

	public String getName() {
		return this.name;
	}

	public String getPrefix() {
		return this.prefix;
	}

	public Location getSpawn() {
		return this.spawn;
	}

	public boolean inTeam(final Player p) {
		if (this.getMembers().contains(p)) return true;
		return false;
	}

	public boolean isFull() {
		return this.getMembers().size() + this.comeMembers.size() >= this.teamManager.getMaxPlayers() ? true : false;
	}

	public void joinComeTeam(final String p) {
		this.comeMembers.add(p);
	}

	public void joinTeam(final Player p) {
		if (this.inTeam(p)) return;

		final PlayerJoinTeamEvent event = new PlayerJoinTeamEvent(p, this, false);
		Game.getGame().getServer().getPluginManager().callEvent(event);
		if (event.isCancelled()) return;

		this.members.add(p);
		final PlayerLocal wpl = BukkitAPI.get().getPlayerLocalManager().getPlayerLocal(p.getName());
		wpl.setPrefix(this.teamPrefix);

	}

	public void leaveTeam(final Player p) {
		if (!this.inTeam(p)) return;

		final PlayerLeaveTeamEvent event = new PlayerLeaveTeamEvent(p, this, false);
		Game.getGame().getServer().getPluginManager().callEvent(event);
		if (event.isCancelled()) return;

		this.members.remove(p);
		final PlayerLocal wpl = BukkitAPI.get().getPlayerLocalManager().getPlayerLocal(p.getName());
		if (wpl != null) wpl.setPrefix("");

	}

	public void quitTeam(final Player p) {
		if (!this.inTeam(p)) return;

		final PlayerLeaveTeamEvent event = new PlayerLeaveTeamEvent(p, this, false);
		Game.getGame().getServer().getPluginManager().callEvent(event);
		if (event.isCancelled()) return;

		final PlayerLocal wpl = BukkitAPI.get().getPlayerLocalManager().getPlayerLocal(p.getName());
		if (wpl != null) wpl.setPrefix("");

		this.members.remove(p);
	}

	public void sendChatMessage(final String player, final String msg) {
		for (final Player p : this.getMembers())
			p.sendMessage(this.getColor() + "(Equipe) " + player + ": §f" + msg);
	}

	public void sendTeamMessage(final String msg) {
		for (final Player p : this.getMembers())
			p.sendMessage(this.getColor() + "Equipe " + this.getName() + " " + msg);
	}

	public void setPrefix(final String prefix) {
		this.teamPrefix = prefix;
		if (this.getMembers().isEmpty()) return;
		for (final Player p : this.getMembers()) {
			final PlayerLocal wpl = BukkitAPI.get().getPlayerLocalManager().getPlayerLocal(p.getName());
			wpl.setPrefix(this.teamPrefix);
		}
	}

	public void setSpawn(final Location spawn) {
		this.spawn = spawn;
	}

	public void teleportAllMembers(final Location loc) {
		for (final Player p : this.getMembers())
			p.teleport(loc);
	}

}
