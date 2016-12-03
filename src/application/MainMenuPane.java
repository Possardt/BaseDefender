package application;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainMenuPane {
	private Pane homeScreenLayout;
	private AnimationTimer mainMenuAnimator;
	//default constructor builds screen
	public MainMenuPane(Stage window, Scene gameScene, GamePane gamePane, Scene highScoreScene, HighScorePane highScorePane){
		Label baseDefenderLabel = new Label("BaseDefender");
		baseDefenderLabel.setId("baseDefenderLabel");
		Button toGameBtn = new Button("Play");
		Button highScoresBtn = new Button("High Scores");
		Button exitBtn = new Button("Exit");
		exitBtn.setOnAction(e -> window.close());

		
		toGameBtn.setLayoutX(300);
		toGameBtn.setLayoutY(150);
		highScoresBtn.setLayoutX(250);
		highScoresBtn.setLayoutY(180);
		exitBtn.setLayoutX(300);
		exitBtn.setLayoutY(210);
		baseDefenderLabel.setLayoutX(100);
		baseDefenderLabel.setLayoutY(80);
		//Layout for home screen
		homeScreenLayout = new Pane();
		
		Missile missile = new Missile(2, 700, 500, 140, -20);
		Missile missile1 = new Missile(3, 700, 500, 280, -30);
		Missile missile2 = new Missile(2, 700, 500, 420, -20);
		Missile missile3 = new Missile(3, 700, 500, 560, -10);
		Missile missile4 = new Missile(2, 700, 500, 660, -5);
		
		addMissileToPane(missile);
		addMissileToPane(missile1);
		addMissileToPane(missile2);
		addMissileToPane(missile3);
		addMissileToPane(missile4);
		
		
		Image citySilhouette = new Image("/resources/Cityscape.png");
		ImageView city = new ImageView(citySilhouette);
		city.setImage(citySilhouette);
		city.setFitWidth(740);
		city.setFitHeight(100);
		//city.setPreserveRatio(true);
		city.setSmooth(true);
		city.setCache(true);
		city.setY(400);
		mainMenuAnimator = new AnimationTimer(){
            @Override
            public void handle(long arg0) {

                // UPDATE
            	
                // RENDER
               missile.animateMissileHomeScreen(homeScreenLayout);
               missile1.animateMissileHomeScreen(homeScreenLayout);
               missile2.animateMissileHomeScreen(homeScreenLayout);
               missile3.animateMissileHomeScreen(homeScreenLayout);
               missile4.animateMissileHomeScreen(homeScreenLayout);
               
            }      
        };
        toGameBtn.setOnAction(e -> toGame(window, gameScene, mainMenuAnimator, gamePane));
		highScoresBtn.setOnAction(e -> toHighScoreScreen(window, highScoreScene, mainMenuAnimator, highScorePane));
        city.toFront();
		homeScreenLayout.getChildren().addAll(toGameBtn, highScoresBtn, exitBtn, baseDefenderLabel, city);
		mainMenuAnimator.start();
		
	}
	
	public Pane getMenuPane(){
		return homeScreenLayout;
	}
	
	public void startMainMenuAnimator(){
		mainMenuAnimator.start();
	}
	
	public void toGame(Stage window, Scene scene, AnimationTimer animator, GamePane gamepane){
		window.setScene(scene);
		animator.stop();
		GamePane.isGameStarted = true;
		gamepane.startGame();
	}
	
	public void toHighScoreScreen(Stage window, Scene scene, AnimationTimer animator, HighScorePane hsp){
		window.setScene(scene);
		hsp.addHighScoreLabels();
		hsp.play();
		animator.stop();
	}
	
	private void addMissileToPane(Missile m){
		homeScreenLayout.getChildren().addAll(m.missile, m.tip, m.outerFire, m.innerFire, m.blade1, m.blade2);
	}
	
}

