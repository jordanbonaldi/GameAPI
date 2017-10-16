package net.neferett.gameapi.modules.teams;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import net.neferett.gameapi.Game;
import net.neferett.gameapi.utils.ItemStackUtils;
import net.neferett.gameapi.utils.gui.GuiScreen;

public class TeamGui extends GuiScreen {

	public static String InvName = "§6Choisir une equipe §7(Clic-droit)";
	
	public static ItemStack getItem() {
		return ItemStackUtils.create(Material.BANNER,(byte) 15,1, InvName,null);
	}
	
	public TeamGui(Player p) {
		super("Choisissez votre équipe", 1, p, false);
		build();
	}

	@Override
	public void drawScreen() {
		for (TeamObject to : Game.getGame().getTeamManager().getTeams()) {
			String displayName = to.getName();
			List<String> lore = new ArrayList<String>();
			ItemStack itemTeam = ItemStackUtils.create(Material.BANNER, ColorsUtils.get(to.getColor()).getData(), 1, "§f" + displayName, lore);
			addItem(itemTeam);
		}
	}

	@Override
	public void onOpen() {
	}

	@Override
	public void onClick(ItemStack item, InventoryClickEvent event) {
		if (!ItemStackUtils.isValid(item))
			return;
		event.setCancelled(true);

		TeamsModuleManager tm = Game.getGame().getTeamManager();
		TeamObject team = tm.getTeam(ChatColor.stripColor(item.getItemMeta().getDisplayName()));

		if (team == null)return;
		
		
		if (team.isFull()) {
			getPlayer().sendMessage("L'équipe " + team.getName() +" est complète");
			return;
		}
		if (tm.haveTeam(getPlayer())) {
			if (tm.getTeam(getPlayer()).equals(team)) {
				getPlayer().sendMessage("Vous êtes déjà dans l'équipe " + team.getName() + ".");
				return;
			} else
			tm.getTeam(getPlayer()).leaveTeam(getPlayer());
		}
		team.joinTeam(getPlayer());

		close();
	}

	@Override
	public void onClose() {
	}

}
