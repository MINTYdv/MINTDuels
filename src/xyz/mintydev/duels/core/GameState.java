package xyz.mintydev.duels.core;

public enum GameState {

	STARTING(5),
	ONGOING(120),
	FINISHED(10);
	
	private int timer;
	
	GameState(int timer){
		this.timer = timer;
	}
	
	public int getTimer() {
		return timer;
	}
	
}
