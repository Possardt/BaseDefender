package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;


public class Main extends Application {

	Stage window;
	Scene mainMenuScene, gameScene;
	@Override
	public void start(Stage primaryStage) {

		window = primaryStage;
		GamePane.isGameStarted = false;

		GamePane gp = new GamePane(window);
		gameScene = new Scene(gp.getGameScreenLayout(), 700,500, Color.GAINSBORO);
		gameScene.getStylesheets().add("/application/application.css");
		
		MainMenuPane mmp = new MainMenuPane(window, gameScene, gp);
		mainMenuScene = new Scene(mmp.getMenuPane(), 200,300, Color.GAINSBORO);
		mainMenuScene.getStylesheets().add("/application/application.css");
		
		//need this to return to main menu after game over
		gp.setMainMenuScene(mainMenuScene);
		gp.setMainMenuPane(mmp);
		
		window.setScene(mainMenuScene);
		window.setTitle("BD V1.1");
		window.setResizable(false);
		window.show();
		
	}
	public static void main(String[] args) {
		launch(args);
	}
}
