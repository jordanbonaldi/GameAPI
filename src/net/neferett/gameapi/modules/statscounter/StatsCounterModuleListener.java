package net.neferett.gameapi.modules.statscounter;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import net.neferett.gameapi.modules.ModuleListener;
import net.neferett.linaris.api.PlayerLocal;
import net.neferett.linaris.api.PlayerLocalManager;
import net.neferett.linaris.events.ReturnToLobbyEvent;
import net.neferett.linaris.utils.PlayerUtils;

public class StatsCounterModuleListener extends ModuleListener {

	public StatsCounterModuleListener() {
		super("Stats");
	}

	@EventHandler
	public void onDamageEntity(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			Player damaged = (Player) e.getEntity();
			Player damager = (Player) e.getDamager();
			
			PlayerUtils.setLastDamagerName(damaged, damager.getName());

			PlayerUtils.setLastDamagerName(damager, damaged.getName());
			
			PlayerUtils.changeKillerToLastDammager(damaged);
//
			PlayerUtils.changeKillerToLastDammager(damager);
		}
	}
	
	@EventHandler
	public void onPlayerQuit(ReturnToLobbyEvent e) {
		 Player player = e.getTarget();
		 if (getGame().getGameManager().isPreStart()) return;
		 PlayerLocal pl = PlayerLocalManager.get().getPlayerLocal(player.getName());
		 player.sendMessage("§6-----------------------------------------------------");
		 player.sendMessage("§6Fin de partie sur §b" + Bukkit.getServerName());
		 player.sendMessage("§7Gain total de §eCoins §7sur la partie : §e" + String.format("%.2f", pl.getGainedEC()));
		 player.sendMessage("§7Gain total de §bCrédits §7sur la partie : §e" + String.format("%.2f", pl.getGainedLC()));
		 player.sendMessage("§6-----------------------------------------------------");
	}

//	@EventHandler
//	public void onPlayerDeath(PlayerDeathEvent e) {
//		Player p = e.getEntity();
//		
//		BukkitAPI.get().getTasksManager().addTask(() -> {
//			
//			// ADD MORT P
//			
//			if (p.getKiller() != null) {
//				Player target = p.getKiller();
//				// ADD KILL TARGET
//			}
//			
//		});
//		
//	}

}
