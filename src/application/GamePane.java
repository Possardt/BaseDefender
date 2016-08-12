package application;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class GamePane {
private int timeKeeper;
private Pane gameScreenLayout;
private AnimationTimer gameTimer;
public static boolean isGameStarted;	
private Label gameLabel, countDown;
private Turret turret;	
private double mouseX,mouseY;
	public GamePane(Stage window){
		gameLabel = new Label("Defend the Base!");
		countDown = new Label("3");
		gameLabel.setLayoutX(140);
		gameLabel.setLayoutY(40);
		countDown.setLayoutX(310);
		countDown.setLayoutY(60);
		gameScreenLayout = new Pane();
		turret = new Turret();
		Rectangle bottom = new Rectangle(0,480,700,20);
		gameScreenLayout.getChildren().addAll(bottom, gameLabel,countDown);
		addTurretToGamePane(turret);
		turret.barrel.getTransforms().add(new Rotate(230, turret.barrel.getX() - 5,turret.barrel.getY()));
		gameScreenLayout.addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent mouseEvent){
				mouseX = mouseEvent.getX();
				mouseY = mouseEvent.getY();
				//System.out.println("mouse X: " + mouseX + ", mouse y: " + mouseY);
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
		Explosion explosion2 = new Explosion();
		Explosion explosion3 = new Explosion();
		Explosion explosion4 = new Explosion();
		
		Missile missile = new Missile(2,700,500);
		Missile missile1 = new Missile(3,700,500);
		Missile missile2 = new Missile(2,700,500);
		Missile missile3 = new Missile(3,700,500);
		Missile missile4 = new Missile(2,700,500);
		
		addMissileToPane(missile);
		addMissileToPane(missile1);
		addMissileToPane(missile2);
		addMissileToPane(missile3);
		addMissileToPane(missile4);
		
		if(isGameStarted)
			System.out.println("game timer started");
			gameTimer = new AnimationTimer(){
				@Override
				public void handle(long arg0){
					if(timeKeeper < 500){
						doCountDown(timeKeeper);
					}else{
						missile.animateMissile(gameScreenLayout, explosion);
						missile1.animateMissile(gameScreenLayout, explosion1);
						missile2.animateMissile(gameScreenLayout, explosion2);
						missile3.animateMissile(gameScreenLayout, explosion3);
						missile4.animateMissile(gameScreenLayout, explosion4);
					}
				turret.rotateTurret(mouseX, mouseY);
				turret.turretRecoil(getGameScreenLayout(),timeKeeper);
				turret.turretShot(getGameScreenLayout(),timeKeeper, mouseX, mouseY);
				timeKeeper++;
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
	private void addTurretToGamePane(Turret t){
		gameScreenLayout.getChildren().addAll(t.barrel,t.barrelEnd,t.base,t.baseDome);
	}
	private void addMissileToPane(Missile m){
		gameScreenLayout.getChildren().addAll(m.missile, m.tip, m.outerFire, m.innerFire, m.blade1, m.blade2);
	}
}
