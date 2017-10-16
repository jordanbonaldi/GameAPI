package net.neferett.gameapi;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.neferett.gameapi.commands.GameComand;
import net.neferett.gameapi.gametasks.InGameTask;
import net.neferett.gameapi.modules.ModulesManager;
import net.neferett.gameapi.modules.chat.ChatModuleListener;
import net.neferett.gameapi.modules.finish.FinishModuleListener;
import net.neferett.gameapi.modules.gameplay.GamePlayModuleListener;
import net.neferett.gameapi.modules.kits.KitsModuleListener;
import net.neferett.gameapi.modules.kits.KitsModuleManager;
import net.neferett.gameapi.modules.permissions.PermissionsModuleListener;
import net.neferett.gameapi.modules.permissions.PermissionsModuleManager;
import net.neferett.gameapi.modules.prestart.PreStartModuleListener;
import net.neferett.gameapi.modules.scoreboard.ScoreBoardModule;
import net.neferett.gameapi.modules.statscounter.StatsCounterModuleListener;
import net.neferett.gameapi.modules.teams.TeamsModuleListener;
import net.neferett.gameapi.modules.teams.TeamsModuleManager;
import net.neferett.linaris.BukkitAPI;
import net.neferett.linaris.api.Games;
import net.neferett.linaris.api.ServerInfo;
import net.neferett.linaris.utils.ServerUtils;

public abstract class Game extends JavaPlugin {

	private static Game game;
	public static Game getGame() {
		return game;
	}
	
	private Games gameID;
	private String map;
	private boolean team;
	private boolean build;
	private boolean kits;
	
	private ServerInfo infos;
	
	private int coinsForWin;
	private int mCoinsForWin;
	
	private int minPlayer;
	private int maxPlayer;
	private int timeToStart;
	
	protected boolean inits;
	
	private ModulesManager modulesManager;
	private AbstractGameManager gameManager;

	private TeamsModuleManager teamManager;
	private TeamsModuleListener teamListener;
	
	private KitsModuleManager kitsManager;
	private KitsModuleListener kitsListener;
	
	private ChatModuleListener chatListener;
	
	private PreStartModuleListener preStartListener;
	
	private GamePlayModuleListener gamePlayListener;
	
	private FinishModuleListener finishListener;
	
	private PermissionsModuleManager permissionsManager;
	private PermissionsModuleListener permissionsModule;
	
	private ScoreBoardModule scoreboard;
	
	private InGameTask inGameTask;
	
	public void inits(Games gameID,String map, AbstractGameManager gameManager) {
		Game.game = this;
		this.gameID = gameID;
		this.map = map;
		this.team = false;
		this.build = false;
		this.kits = false;
		this.minPlayer = Bukkit.getMaxPlayers() / 2;
		this.maxPlayer = Bukkit.getMaxPlayers();
		this.timeToStart = 120;
		
		this.mCoinsForWin = 1;
		this.coinsForWin = 5;
		
		this.gameManager = gameManager;
		this.modulesManager = new ModulesManager(this);
		
		this.permissionsManager = new PermissionsModuleManager();
		this.permissionsModule = new PermissionsModuleListener();
		
		preInits();

		infos = BukkitAPI.get().getServerInfos();
		infos.setGameName(gameID.getDisplayName());
		infos.setMapName(map);

		inits = true;
		
	}
	
	public void load() {
		if (!inits) return;
		
		gameManager.inits();
		
		if (team) {
			teamManager.inits();
			teamManager.proctectedInits();
			modulesManager.registerModuleListener(teamListener);
		}
		
		if (kits) {
			kitsManager.inits();
			modulesManager.registerModuleListener(kitsListener);
		}
		
		modulesManager.registerModuleListener(permissionsModule);
		modulesManager.registerModuleListener(chatListener);
		modulesManager.registerModuleListener(preStartListener);
		modulesManager.registerModuleListener(new StatsCounterModuleListener());
		
		this.getCommand("game").setExecutor(new GameComand(this));
		
		inits();
	}
	
	@Override
	public void onEnable() {
		load();
	}
	
	public int getMCoinsForWin() {
		return mCoinsForWin;
	}
	
	public void setMCoinsForWin(int mCoinsForWin) {
		this.mCoinsForWin = mCoinsForWin;
	}
	
	public void setCoinsForWin(int coinsForWin) {
		this.coinsForWin = coinsForWin;
	}
	
	public int getCoinsForWin() {
		return coinsForWin;
	}
	
	public Games getGameID() {
		return gameID;
	}
	
	public String getGameName() {
		return gameID.getDisplayName();
	}
	
	public String getMap() {
		return map;
	}

	public void setMinPlayer(int minPlayer) {
		this.minPlayer = minPlayer;
	}
	
	public int getMinPlayer() {
		return minPlayer;
	}
	
	public int getMaxPlayer() {
		return maxPlayer;
	}
	
	public int getTimeToStart() {
		return timeToStart;
	}
	
	public void setTimeToStart(int timeToStart) {
		this.timeToStart = timeToStart;
	}
	
	public void setBuild(boolean build) {
		this.build = build;
	}

	public void setTeam(TeamsModuleManager manager,TeamsModuleListener listener) {
		this.teamManager = manager;
		this.teamListener = listener;
		this.team = true;
	}
	
	public void setTeam(TeamsModuleManager manager,TeamsModuleListener listener,boolean selector) {
		this.teamManager = manager;
		this.teamManager.setSelector(selector);
		this.teamListener = listener;
		this.team = true;
	}
	
	public void setKits(KitsModuleManager manager,KitsModuleListener listener) {
		this.kitsManager = manager;
		this.kitsListener = listener;
		this.kits = true;
	}
	
	public void setGamePlayListener(GamePlayModuleListener gamePlayListener) {
		this.gamePlayListener = gamePlayListener;
	}
	
	public void setPreStartListener(PreStartModuleListener preStartListener) {
		this.preStartListener = preStartListener;
	}

	public void setChatListener(ChatModuleListener chatListener) {
		this.chatListener = chatListener;
	}
	
	public void setInGameTask(InGameTask inGameTask) {
		this.inGameTask = inGameTask;
	}
	
	public void setMaxPlayer(int maxPlayer) {
		this.maxPlayer = maxPlayer;
		ServerUtils.changeMaxPlayers(maxPlayer);
		BukkitAPI.get().setMaxPlayers(maxPlayer);
	}
	
	public void setScoreboard(ScoreBoardModule scoreboard) {
		this.scoreboard = scoreboard;
	}
	
	public void setFinishListener(FinishModuleListener finishListener) {
		this.finishListener = finishListener;
	}

	public boolean useTeam() { return this.team; };
	public boolean useBuild() { return this.build; };
	public boolean useKits() { return this.kits; };
	
	public ServerInfo getInfos() {
		return infos;
	}
	
	public AbstractGameManager getGameManager() {
		return gameManager;
	}
	
	public ModulesManager getModulesManager() {
		return modulesManager;
	}
	
	public TeamsModuleManager getTeamManager() {
		return teamManager;
	}
	
	public KitsModuleManager getKitsManager() {
		return kitsManager;
	}
	
	public PreStartModuleListener getPreStartListener() {
		return preStartListener;
	}
	
	public GamePlayModuleListener getGamePlayListener() {
		return gamePlayListener;
	}
	
	public PermissionsModuleManager getPermissionsManager() {
		return permissionsManager;
	}
	
	public ChatModuleListener getChatListener() {
		return chatListener;
	}
	
	public FinishModuleListener getFinishListener() {
		return finishListener;
	}
	
	public ScoreBoardModule getScoreboard() {
		return scoreboard;
	}
	
	public InGameTask getInGameTask() {
		return inGameTask;
	}
	
	public boolean isPreStart() {
		return gameManager.isPreStart();
	}
	public boolean isStarted() {
		return gameManager.isStarted();
	}
	public boolean isFinish() {
		return gameManager.isFinish();
	}
	
	public abstract void preInits();
	
	public abstract void inits();
	
	public abstract String playerJoinMessage(Player p,int players,int maxPlayers);
	
	public abstract String playerQuitMessage(Player p,int players,int maxPlayers);
}
