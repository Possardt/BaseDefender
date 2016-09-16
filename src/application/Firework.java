package application;

import java.util.ArrayList;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class Firework {
	public Rectangle body;
	public Rectangle trail;
	private boolean shootFirework,explodeFirework,reloadFirework;
	private String[] colors = {"Red","Purple","Green","Orange"};
	private int fuse;
	private int explodeLocX, explodeLocY;
	private ArrayList<Rectangle> explosionEffectContainer = new ArrayList<Rectangle>();
	private int angle, centerScreenX, centerScreenY;
	private String color;
	
	
	public Firework(int screenX, int screenY){
		centerScreenX = screenX;
		centerScreenY = screenY;
		body = new Rectangle(centerScreenX, centerScreenY, 5, 15);
		color = getRandomColor();
		angle = getRandomAngle();
		body.setFill(Paint.valueOf(color));
		//rotate firework
		body.getTransforms().add(new Rotate(angle,250,700));
		fuse = 80;
		
		shootFirework = true;
	}
	
	public void animateFirework(Pane highScorePane){
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
	
	private void moveFireworkUpScreen(){
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
	
	private void explodeFireworkAnimation(Pane highScorePane){
		if(fuse == 0){
			System.out.println("setting up explosion");
			setupExplosion(highScorePane);
			fuse--;
		}else if(fuse > -200){
			//move effect outward
			moveExplosionOutward();
			fuse--;
		}else{
			//reset firework(all done)
			highScorePane.getChildren().removeAll(explosionEffectContainer);
			explosionEffectContainer.clear();
			explodeFirework = false;
			shootFirework = false;
			reloadFirework = true;
		}
	}
	
	private void reloadFireworkInHighScoreScreen(){
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
	
	private void setupExplosion(Pane highScorePane){
		Rectangle rectangle1 = new Rectangle(explodeLocX, explodeLocY, 4,2);
		Rectangle rectangle2 = new Rectangle(explodeLocX, explodeLocY, 4,2);
		Rectangle rectangle3 = new Rectangle(explodeLocX, explodeLocY, 4,2);
		Rectangle rectangle4 = new Rectangle(explodeLocX, explodeLocY, 4,2);
		Rectangle rectangle5 = new Rectangle(explodeLocX, explodeLocY, 4,2);
		Rectangle rectangle6 = new Rectangle(explodeLocX, explodeLocY, 4,2);
		Rectangle rectangle7 = new Rectangle(explodeLocX, explodeLocY, 4,2);
		Rectangle rectangle8 = new Rectangle(explodeLocX, explodeLocY, 4,2);
		Rectangle rectangle9 = new Rectangle(explodeLocX, explodeLocY, 4,2);
		Rectangle rectangle10 = new Rectangle(explodeLocX, explodeLocY, 4,2);
		Rectangle rectangle11 = new Rectangle(explodeLocX, explodeLocY, 4,2);
		Rectangle rectangle12 = new Rectangle(explodeLocX, explodeLocY, 4,2);
		
		
		explosionEffectContainer.add(rectangle1);
		explosionEffectContainer.add(rectangle2);
		explosionEffectContainer.add(rectangle3);
		explosionEffectContainer.add(rectangle4);
		explosionEffectContainer.add(rectangle5);
		explosionEffectContainer.add(rectangle6);
		explosionEffectContainer.add(rectangle7);
		explosionEffectContainer.add(rectangle8);
		explosionEffectContainer.add(rectangle9);
		explosionEffectContainer.add(rectangle10);
		explosionEffectContainer.add(rectangle11);
		explosionEffectContainer.add(rectangle12);
		
		
		String color = getRandomColor();
		int rotate = 0;
		
		for(Rectangle r : explosionEffectContainer){
			r.setFill(Paint.valueOf(color));
			r.getTransforms().add(new Rotate(rotate, explodeLocX, explodeLocY));
			rotate += (360 / explosionEffectContainer.size());
		}
		
		
		highScorePane.getChildren().addAll(explosionEffectContainer);
		
		
	}
	
	private void moveExplosionOutward(){
		for(Rectangle r : explosionEffectContainer){
			r.setX(r.getX() - 2);
			r.setOpacity(r.getOpacity() - .02);
		}

	}
	
	private String getRandomColor(){
		return colors[(int)(Math.random() * 100) % colors.length];
	}
	//returns random value from -30 to 30
	private int getRandomAngle(){
		int rand = (int) (Math.random() * 100) % 30;
		if(Math.random() > .5)
			rand *= -1;
		return rand;
	}
	
}
