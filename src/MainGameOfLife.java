public class MainGameOfLife {
	
	public static void main(String[] args) {
		LifeSimulation gameLogic = new LifeSimulation(50, 50);
		GameUI gameView = new GameUI(gameLogic);
		@SuppressWarnings("unused")
		GameController gameController = new GameController(gameLogic, gameView);
	}
}
