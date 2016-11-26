package application;

import java.util.ArrayList;


import javafx.animation.AnimationTimer;
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
	private Scene highScoreScene;
	private AnimationTimer highScoreTimer;
	private ArrayList<Firework> fireworks;

	
	public HighScorePane(Stage window){
		this.applicationWindow = window;
		highScorePane = new Pane();
		highScorePane.getStyleClass().add("highScoreScreen");
		highScoreLabel = new Label("High Scores:");
		highScoreLabel.setId("highScoreLabel");
		
		highScoreLabel.setLayoutX(50);
		highScoreLabel.setLayoutY(60);
		
		returnToMainScreenButton = new Button("Back");
		returnToMainScreenButton.setLayoutX(30);
		returnToMainScreenButton.setLayoutY(20);
		returnToMainScreenButton.setId("highScoreLabel");
		returnToMainScreenButton.setOnAction(e -> returnToMainMenu());
		
		fireworks = new ArrayList<Firework>();
		//adding all to it
		highScorePane.getChildren().addAll(highScoreLabel, returnToMainScreenButton);
		
	}
	
	public void addHighScoreLabels(){
		//add: delete high score labels if they already exist
		//need method to check if highScoreLabels exist(ie, page has
		//been visited before)
		deleteExistingHighScoreLabels();
		int i = 1;
		int labelX = 70;
		int labelY = 90;
		for(int score : HighScore.getHighScores()){
			Label scoreListLabel = new Label(i + ".\t" + score);
			scoreListLabel.setLayoutX(labelX);
			scoreListLabel.setLayoutY(labelY);
			scoreListLabel.setId("scoreListLabel");
			highScorePane.getChildren().addAll(scoreListLabel);
			labelY += 30;
			i++;
			if(i == 11)
				break;
		}
	}
	
	public void play(){
		OutwardRectangleFirework firework1 = new OutwardRectangleFirework(350, 500, 0);
		OutwardCircleFirework firework2 = new OutwardCircleFirework(350, 500, 0);
		OutwardLineFirework firework3 = new OutwardLineFirework(350, 500, 0);
		RandomDotFirework firework4 = new RandomDotFirework(350,500, 0);
		OutwardCircleFirework firework5 = new OutwardCircleFirework(350, 500, 0);
		OutwardLineFirework firework6 = new OutwardLineFirework(350, 500, 0);
		RandomDotFirework firework7 = new RandomDotFirework(350,500, 0);
		fireworks.add(firework1);
		fireworks.add(firework2);
		fireworks.add(firework3);
		fireworks.add(firework4);
		fireworks.add(firework5);
		fireworks.add(firework6);
		fireworks.add(firework7);
		highScorePane.getChildren().addAll(firework1.body, firework2.body,firework3.body,firework4.body, firework5.body,firework6.body,firework7.body);
		highScoreTimer = new AnimationTimer(){
			@Override
			public void handle(long arg0){
				firework1.animateFirework(highScorePane);
				firework2.animateFirework(highScorePane);
				firework3.animateFirework(highScorePane);
				firework4.animateFirework(highScorePane);
				firework5.animateFirework(highScorePane);
				firework6.animateFirework(highScorePane);
				firework7.animateFirework(highScorePane);
			}
		};
		highScoreTimer.start();
	}
	
	private void deleteExistingHighScoreLabels(){
		Label scoreListIdLabel = (Label) highScoreScene.lookup("#scoreListLabel");
		while(scoreListIdLabel != null){
			highScorePane.getChildren().remove(scoreListIdLabel);
			scoreListIdLabel = (Label) highScoreScene.lookup("#scoreListLabel");
		}
	}
	
	
	
	private void returnToMainMenu(){
		mainMenuPane.startMainMenuAnimator();
		//highScorePane.getChildren().removeAll(fireworks);
		//stop highscoreTimer
		removeFireworks();
		highScoreTimer.stop();
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
	
	public void setHighScoreScene(Scene scene){
		this.highScoreScene = scene;
	}
	private void removeFireworks(){
		for(Firework firework : fireworks){
			firework.removeExplosionEffects(highScorePane);
			highScorePane.getChildren().remove(firework.body);
			
		}
	}
}
