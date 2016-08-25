package application;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GamePane {
private int timeKeeper;
private Pane gameScreenLayout;
private MainMenuPane mainMenuPane;
private AnimationTimer gameTimer;
public static boolean isGameStarted;	
private Label gameLabel, countDown, healthLabel,scoreLabel, currentScoreLabel, gameOverLabel;
private Turret turret;
private int gameScore = 0;
private Scene mainMenuScene;
private double mouseX,mouseY;
private Stage applicationWindow;
private Button returnToMainMenuButton, exitGameButton;
private ArrayList<Missile> missileContainer = new ArrayList<Missile>();
	public GamePane(Stage window){
		applicationWindow = window;
		instantiateGamePaneLabels();
		gameScreenLayout = new Pane();
		turret = new Turret();
		Rectangle bottom = new Rectangle(0,480,700,20);
		gameScreenLayout.getChildren().addAll(bottom, gameLabel,countDown,healthLabel,scoreLabel,currentScoreLabel);
		addTurretToGamePane(turret);
		//turret.barrel.getTransforms().add(new Rotate(230, turret.barrel.getX() - 5,turret.barrel.getY()));
		gameScreenLayout.addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent mouseEvent){
				mouseX = mouseEvent.getX();
				mouseY = mouseEvent.getY();
			}
		});
		gameScreenLayout.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent mouseEvent){
				turret.setTimeShot(timeKeeper);
			}
		});
		
	}
	
	public Pane getGameScreenLayout(){
		return gameScreenLayout;
	}
	
	public void startGame(){
		
		Explosion explosion = new Explosion();
		Explosion explosion1 = new Explosion();
		//Explosion explosion2 = new Explosion();
		//Explosion explosion3 = new Explosion();
		//Explosion explosion4 = new Explosion();
		
		Missile missile = new Missile(2,700,500);
		Missile missile1 = new Missile(3,700,500);
		//Missile missile2 = new Missile(2,700,500);
		//Missile missile3 = new Missile(3,700,500);
		//Missile missile4 = new Missile(2,700,500);
		
		addMissileToPane(missile);
		addMissileToPane(missile1);
		//addMissileToPane(missile2);
		//addMissileToPane(missile3);
		//addMissileToPane(missile4);
		missileContainer.add(missile);
		missileContainer.add(missile1);
		
		resetGameValues();
		if(isGameStarted)
			System.out.println("game timer started");
			gameTimer = new AnimationTimer(){
				@Override
				public void handle(long arg0){
					if(timeKeeper < 500){
						doCountDown(timeKeeper);
					}else{
						missile.bulletMissileCollisionListener(turret.getBulletIterator(),gameScreenLayout, explosion);
						missile1.bulletMissileCollisionListener(turret.getBulletIterator(), gameScreenLayout, explosion1);
						missile.animateMissile(gameScreenLayout, explosion, turret.getBulletIterator(),turret);
						missile1.animateMissile(gameScreenLayout, explosion1, turret.getBulletIterator(), turret);
						//missile2.animateMissile(gameScreenLayout, explosion2);
						//missile3.animateMissile(gameScreenLayout, explosion3);
						//missile4.animateMissile(gameScreenLayout, explosion4);
						
						//missile1.bulletMissileCollision(turret.getBulletIterator());
						//missile2.bulletMissileCollision(turret.getBulletIterator());
						//missile3.bulletMissileCollision(turret.getBulletIterator());
						//missile4.bulletMissileCollision(turret.getBulletIterator());
						iterateGameScore(timeKeeper);
					}
				turret.rotateTurret(mouseX, mouseY);
				turret.turretRecoil(getGameScreenLayout(),timeKeeper);
				turret.turretShot(getGameScreenLayout(),timeKeeper, mouseX, mouseY);
				turret.updateHealth();
				if(turret.getTurretHealth() == 0){
					gameOver();
					this.stop();
				}
				timeKeeper++;				
				updateGameScore();
			}
			
		};
		gameTimer.start();
	}
	
	
	//takes time keeper and displays countdown until game starts
	public void doCountDown(int tKeeper){
		if(tKeeper < 100){
			countDown.setOpacity(countDown.getOpacity() -  .01);
			if(tKeeper == 99){
				countDown.setText("2");
				countDown.setOpacity(1);
			}
		}else if(tKeeper < 200){
			countDown.setOpacity(countDown.getOpacity() - .01);
			if(tKeeper == 199){
				countDown.setText("1");
				countDown.setOpacity(1);
			}
		}else if(tKeeper < 300){
			countDown.setOpacity(countDown.getOpacity() - .01);
			if(tKeeper == 299){
				countDown.setText("GO!");
				countDown.setOpacity(1);
			}
		}else if(tKeeper < 400){
			countDown.setOpacity(countDown.getOpacity() - .01);
			if(tKeeper == 399){
				gameScreenLayout.getChildren().remove(countDown);
			}
			gameLabel.setOpacity(gameLabel.getOpacity() - .01);
			if(tKeeper == 399){
				gameScreenLayout.getChildren().remove(gameLabel);
			}			
		}
	}
	
	private void updateGameScore(){
		currentScoreLabel.setText(Integer.toString(gameScore));
	}
	
	//for other calsses to iterate game score
	public void addToGameScore(int amountToAdd){
		this.gameScore = gameScore + amountToAdd;
	}
	
	private void iterateGameScore(int time){
		if(time %10 == 0){
			gameScore++;
		}
	}
	
	private void gameOver(){
		addGameOverTextToScreen();
		addExitAndReturnToHomeButtons();
		isGameStarted = false;
		
		mainMenuPane.startMainMenuAnimator();		
	}
	
	private void addGameOverTextToScreen(){
		gameOverLabel = new Label("GAME OVER!");
		gameOverLabel.setId("gameOverLabel");
		gameOverLabel.setLayoutX(30);
		gameOverLabel.setLayoutY(30);
		gameScreenLayout.getChildren().add(gameOverLabel);
		
	}
	
	
	private void instantiateGamePaneLabels(){
		gameLabel = new Label("Defend the Base!");
		countDown = new Label("3");
		healthLabel = new Label("Health:");
		scoreLabel = new Label("Score:");
		currentScoreLabel = new Label(Integer.toString(gameScore));
		gameLabel.setLayoutX(180);
		gameLabel.setLayoutY(40);
		countDown.setLayoutX(340);
		countDown.setLayoutY(100);
		healthLabel.setLayoutX(30);
		healthLabel.setLayoutY(20);
		healthLabel.setId("healthLabel");
		scoreLabel.setLayoutX(500);
		scoreLabel.setLayoutY(20);
		scoreLabel.setId("scoreLabel");
		currentScoreLabel.setLayoutX(600);
		currentScoreLabel.setLayoutY(20);
		currentScoreLabel.setId("currentScoreLabel");
		
	}
	
	private void addExitAndReturnToHomeButtons(){
		returnToMainMenuButton = new Button();
		exitGameButton = new Button();
		exitGameButton.setText("exit");
		returnToMainMenuButton.setText("Main Menu");
		returnToMainMenuButton.setLayoutX(340);
		returnToMainMenuButton.setLayoutY(120);
		exitGameButton.setLayoutX(340);
		exitGameButton.setLayoutY(160);
		exitGameButton.setOnAction(e -> applicationWindow.close());
		returnToMainMenuButton.setOnAction(e -> returnToMainMenuScreenMethod());
		gameScreenLayout.getChildren().addAll(returnToMainMenuButton, exitGameButton);
	}
	
	private void addTurretToGamePane(Turret t){
		gameScreenLayout.getChildren().addAll(t.barrel,t.barrelEnd,t.base,t.baseDome, t.turretHealthBar);
	}
	private void addMissileToPane(Missile m){
		gameScreenLayout.getChildren().addAll(m.missile, m.tip, m.outerFire, m.innerFire, m.blade1, m.blade2);
	}
	public void setMainMenuScene(Scene mainMenu){
		this.mainMenuScene = mainMenu;
	}
	public void setMainMenuPane(MainMenuPane mainMenuPane){
		this.mainMenuPane = mainMenuPane;
	}
	
	private void resetGameValues(){
		gameScore = 0;
		timeKeeper = 0;
		turret.setTurretHealth(100);
	}
	
	private void returnToMainMenuScreenMethod(){
		//cleanup method needed
		cleanup();
		applicationWindow.setScene(mainMenuScene);
	}
	
	private void cleanup(){
		gameScreenLayout.getChildren().remove(returnToMainMenuButton);
		gameScreenLayout.getChildren().remove(exitGameButton);
		gameScreenLayout.getChildren().remove(gameOverLabel);
		removeMissilesFromScreen();
		removeTurretFromScreen();
	}
	
	private void removeMissilesFromScreen(){
		for(Missile m : missileContainer){
			gameScreenLayout.getChildren().remove(m.blade1);
			gameScreenLayout.getChildren().remove(m.blade2);
			gameScreenLayout.getChildren().remove(m.innerFire);
			gameScreenLayout.getChildren().remove(m.missile);
			gameScreenLayout.getChildren().remove(m.outerFire);
			gameScreenLayout.getChildren().remove(m.circle);
			gameScreenLayout.getChildren().remove(m.tip);
		}
		missileContainer.clear();
	}
	private void removeTurretFromScreen(){
		gameScreenLayout.getChildren().remove(turret.barrel);
		gameScreenLayout.getChildren().remove(turret.barrelEnd);
		gameScreenLayout.getChildren().remove(turret.base);
		gameScreenLayout.getChildren().remove(turret.baseDome);
		turret = new Turret();
		addTurretToGamePane(turret);
	}
	
	
	
}
