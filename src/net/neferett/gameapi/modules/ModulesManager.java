package net.neferett.gameapi.modules;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

import net.neferett.gameapi.Game;

public class ModulesManager {

	HashMap<String, ModuleListener> listeners;
	
	private Game game;
	
	public ModulesManager(Game game) {
		this.game = game;
		this.listeners = new HashMap<String, ModuleListener>();
	}
	
	public HashMap<String, ModuleListener> getListeners() {
		return listeners;
	}
	
	public void registerModuleListener(ModuleListener listener) {
		String name = listener.getName();
		
		if (listeners.containsKey(name))
			unregisterModuleListener(name);
		
		listener.setGame(game);
		listeners.put(name, listener);
		Bukkit.getServer().getPluginManager().registerEvents(listener, game);
	}

	public void unregisterModuleListener(String name) {
		if (!listeners.containsKey(name)) return;
		HandlerList.unregisterAll(listeners.get(name));
		listeners.remove(name);
	}
	
	public void unregisterModuleListener(ModuleListener listener) {
		if (!listeners.containsValue(listener)) return;
		HandlerList.unregisterAll(listener);
		listeners.remove(listener.getName());
	}
}
