package xyz.mintydev.duels.runnable;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import xyz.mintydev.duels.MINTDuels;
import xyz.mintydev.duels.core.DuelGame;
import xyz.mintydev.duels.core.EndReason;
import xyz.mintydev.duels.core.GameState;
import xyz.mintydev.duels.managers.LangManager;
import xyz.mintydev.duels.util.EffectUtil;

public class GameRunnable extends BukkitRunnable {

	private final MINTDuels main;
	private final DuelGame game;
	
	private Map<GameState, Integer> timers = new HashMap<>();
	
	public GameRunnable(MINTDuels main, DuelGame game) {
		this.main = main;
		this.game = game;
	}
	
	@Override
	public void run() {
		if(!timers.containsKey(game.getState())) {
			timers.put(game.getState(), game.getState().getTimer());
		}
		
		int newTimer = getTimer()-1;
		setTimer(newTimer);

		if(game.getState() == GameState.STARTING) {
			if(getTimer() <= 0) {
				game.setState(GameState.ONGOING);
				game.broadcast(LangManager.getMessage("duel.fight").replaceAll("%timer%", newTimer+""), true);
				game.broadcast(LangManager.getMessage("duel.fight").replaceAll("%timer%", newTimer+""), false);
			} else {
				game.broadcast(LangManager.getMessage("duel.start-timer").replaceAll("%timer%", newTimer+""), true);
				game.broadcast(LangManager.getMessage("duel.start-timer").replaceAll("%timer%", newTimer+""), false);
			}
		} else if(game.getState() == GameState.ONGOING) {
			if(getTimer() <= 0) {
				game.setState(GameState.FINISHED);
				main.getDuelManager().endGame(game, EndReason.TIMEOUT, null);
			}
		} else if(game.getState() == GameState.FINISHED) {
			if(game.getWinner() != null && game.getWinner().isOnline()) {
				
				Location spawnLoc = game.getWinner().getLocation().clone();
				spawnLoc.add(0, 3, 0);
				
				EffectUtil.spawnFireworks(spawnLoc, 1);
			}
			
			if(getTimer() <= 0) {
				main.getDuelManager().endGame(game, EndReason.WIN, game.getWinner());
			}
		}
	}
	
	private int setTimer(int toSet) {
		if(timers.containsKey(game.getState())) {
			timers.remove(game.getState());
		}
		
		timers.put(game.getState(), toSet);
		return timers.get(game.getState());
	}
	
	private int getTimer() {
		return timers.get(game.getState());
	}

}
