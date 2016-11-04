package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;


public class Main extends Application {

	Stage window;
	Scene mainMenuScene, gameScene, highScoreScene;
	@Override
	public void start(Stage primaryStage) {
		window = primaryStage;
		GamePane.isGameStarted = false;

		GamePane gp = new GamePane(window);
		gameScene = new Scene(gp.getGameScreenLayout(), 700,500, Color.GAINSBORO);
		gameScene.getStylesheets().add("/application/application.css");
		
		HighScorePane hsp = new HighScorePane(window);
		highScoreScene = new Scene(hsp.getHighScorePane(), 700,500, Color.GAINSBORO);
		highScoreScene.getStylesheets().add("/application/application.css");
		
		MainMenuPane mmp = new MainMenuPane(window, gameScene, gp, highScoreScene, hsp);
		mainMenuScene = new Scene(mmp.getMenuPane(), 700,500, Color.GAINSBORO);
		mainMenuScene.getStylesheets().add("/application/application.css");
		
		
		
		//need this to return to main menu after game over
		gp.setMainMenuScene(mainMenuScene);
		gp.setMainMenuPane(mmp);
		//need this to return to main menu pane
		hsp.setMainMenuScene(mainMenuScene);
		hsp.setMainMenuPane(mmp);
		//need this to get if there are existing high scores in high score screen
		hsp.setHighScoreScene(highScoreScene);
		
		
		window.setScene(mainMenuScene);
		window.setTitle("BD V2.1");
		window.setResizable(false);
		window.show();
		
	}
	public static void main(String[] args) {
		launch(args);
	}
}
