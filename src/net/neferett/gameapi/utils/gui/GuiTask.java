package net.neferett.gameapi.utils.gui;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.neferett.gameapi.Game;



public class GuiTask extends BukkitRunnable {
 
    @SuppressWarnings("unused")
	private final Game plugin;
    private final Player p;
    private final GuiScreen gui;
 
    public GuiTask(Game plugin,Player p,GuiScreen gui) {
        this.plugin = plugin;
        this.p = p;
        this.gui = gui;
        gui.open();
    }
 
    @Override
    public void run() {
    	
    	if (!gui.getInventory().getViewers().contains(p)) {
    		this.cancel();
    		return;
    	}
    	
    	Game.getGame().getServer().getPluginManager().callEvent(new GuiUpdateEvent(p,gui,false));
    	gui.drawScreen();

    }
 
}
