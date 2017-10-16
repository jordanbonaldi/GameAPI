package net.neferett.gameapi.gametasks;

import net.neferett.gameapi.Game;
import net.neferett.gameapi.GameTask;

public class InGameTask extends GameTask {

	public InGameTask(Game game) {
		super(game, 0);
	}

	@Override
	public void run() {
		setTime(getTime() + 1);
		getGame().getGameManager().addElapsedTime();

	}

}
