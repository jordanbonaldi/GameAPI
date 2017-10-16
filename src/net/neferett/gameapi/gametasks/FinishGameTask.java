package net.neferett.gameapi.gametasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.neferett.gameapi.Game;
import net.neferett.gameapi.GameTask;
import net.neferett.linaris.BukkitAPI;
import net.neferett.linaris.utils.PlayerUtils;

public class FinishGameTask extends GameTask{

	public FinishGameTask(Game game) {
		super(game,20);
	}

	@Override
	public void run() {
		setTime(getTime() - 1);
		
		if (getTime() == 10) {
			for (Player p : Bukkit.getOnlinePlayers())
				PlayerUtils.returnToHub(p);
		}
		if (getTime() == 0) {
			BukkitAPI.get().reboot();
		}
	}
}
