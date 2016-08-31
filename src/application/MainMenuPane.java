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
	public MainMenuPane(Stage window, Scene gameScene, GamePane gamePane){
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
		
		Explosion explosion = new Explosion();
		Explosion explosion1 = new Explosion();
		Explosion explosion2 = new Explosion();
		Explosion explosion3 = new Explosion();
		Explosion explosion4 = new Explosion();
		
		Missile missile = new Missile(2, 700, 500);
		Missile missile1 = new Missile(3, 700, 500);
		Missile missile2 = new Missile(2, 700, 500);
		Missile missile3 = new Missile(3, 700, 500);
		Missile missile4 = new Missile(2, 700, 500);
		
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
               missile.animateMissileHomeScreen(homeScreenLayout, explosion);
               missile1.animateMissileHomeScreen(homeScreenLayout, explosion1);
               missile2.animateMissileHomeScreen(homeScreenLayout, explosion2);
               missile3.animateMissileHomeScreen(homeScreenLayout, explosion3);
               missile4.animateMissileHomeScreen(homeScreenLayout, explosion4);
               
            }      
        };
        toGameBtn.setOnAction(e -> toGame(window, gameScene, mainMenuAnimator, gamePane));
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
	
	private void addMissileToPane(Missile m){
		homeScreenLayout.getChildren().addAll(m.missile, m.tip, m.outerFire, m.innerFire, m.blade1, m.blade2);
	}
	
}

