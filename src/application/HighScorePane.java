package application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class HighScorePane {
	private Pane highScorePane;
	private MainMenuPane mainMenuPane;
	private Scene mainMenuScene;
	private Button returnToMainScreenButton;
	private Label highScoreLabel;
	private Stage applicationWindow;
	
	public HighScorePane(Stage window){
		this.applicationWindow = window;
		highScorePane = new Pane();
		highScoreLabel = new Label("High Scores:");
		highScoreLabel.setLayoutX(50);
		highScoreLabel.setLayoutY(60);
		
		returnToMainScreenButton = new Button("Back");
		returnToMainScreenButton.setLayoutX(30);
		returnToMainScreenButton.setLayoutY(20);
		returnToMainScreenButton.setOnAction(e -> returnToMainMenu());
		
		//adding all to it
		highScorePane.getChildren().addAll(highScoreLabel, returnToMainScreenButton);
		
	}
	
	private void returnToMainMenu(){
		mainMenuPane.startMainMenuAnimator();
		applicationWindow.setScene(mainMenuScene);
	}
	
	public Pane getHighScorePane(){
		return highScorePane;
	}
	
	public void setMainMenuPane(MainMenuPane p){
		this.mainMenuPane = p;
	}
	public void setMainMenuScene(Scene s){
		this.mainMenuScene = s;
	}
}
