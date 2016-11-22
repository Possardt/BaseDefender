package application;

import java.util.ArrayList;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public abstract class Firework {
	protected Rectangle body;
	protected Rectangle trail;
	protected boolean shootFirework,explodeFirework,reloadFirework;
	protected String[] colors = {"Red","Purple","Green","Orange"};
	protected int fuse;
	protected int explodeLocX, explodeLocY;
	protected int angle, centerScreenX, centerScreenY;
	protected String color;
	protected ArrayList<Rectangle> explosionEffectContainer = new ArrayList<Rectangle>();
	
	protected Firework(int screenX, int screenY){
		centerScreenX = screenX;
		centerScreenY = screenY;
		body = new Rectangle(centerScreenX, centerScreenY, 2, 10);
		color = getRandomColor();
		angle = getRandomAngle();
		body.setFill(Paint.valueOf(color));
		//rotate firework
		body.getTransforms().add(new Rotate(angle,250,700));
		fuse = 80;
		
		shootFirework = true;
	}
	
	protected void animateFirework(Pane highScorePane){
		//use 3 cases: shoot, explode, and reloadFirework
		if(shootFirework){
			//move the firework up the screen and subtract from the fuse
			moveFireworkUpScreen();
		}else if(explodeFirework){
			//explode firework animation
			explodeFireworkAnimation(highScorePane);
		}else if(reloadFirework){
			//explode finished and firework ready to be reloaded
			reloadFireworkInHighScoreScreen();
		}
	}
	
	protected void moveFireworkUpScreen(){
		if(fuse > 10){
			body.setY(body.getY() - 5);
			fuse--;
			//System.out.println("moving firework upscreen");
			//add trail later
		}else if(fuse >= 1){
			body.setY(body.getY() - 2);
			body.setOpacity(body.getOpacity() - .02);
			fuse--;
			
		}else{
			System.out.println("firework exploded! " + fuse);
			System.out.println("Math.cos(angle = " + Math.cos(angle) * 100);  
			explodeLocX = (int)body.getBoundsInParent().getMaxX();
			explodeLocY = (int)body.getBoundsInParent().getMaxY();
			body.setX(7000);
			explodeFirework = true;
			shootFirework = false;
			reloadFirework = false;
		}
	}
	
	
	protected void reloadFireworkInHighScoreScreen(){
		explodeLocX = 0;
		explodeLocY = 0;
		fuse = 80;
		angle = getRandomAngle();
		color = getRandomColor();
		body.setOpacity(1);
		body.setFill(Paint.valueOf(color));
		body.setX(centerScreenX);
		body.setY(centerScreenY);
		body.getTransforms().clear();
		body.getTransforms().add(new Rotate(angle, 250, 700));
		explodeFirework = false;
		shootFirework = true;
		reloadFirework = false;
	}
	
	
	//abstract methods
	public abstract void setupExplosion(Pane highScorePane);
	public abstract void explodeFireworkAnimation(Pane highScorePane);
	public void removeExplosionEffects(Pane highScorePane){
		highScorePane.getChildren().removeAll(explosionEffectContainer);
	}
	
	protected String getRandomColor(){
		return colors[(int)(Math.random() * 100) % colors.length];
	}
	
	//returns random value from -30 to 30
	protected int getRandomAngle(){
		int rand = (int) (Math.random() * 100) % 30;
		if(Math.random() > .5)
			rand *= -1;
		return rand;
	}
}
