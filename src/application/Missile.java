package application;


import java.util.HashSet;
import java.util.Iterator;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;


public class Missile {
	//includes all shapes that go into creating the missile
	public Rectangle missile;
	public Circle circle;
	public Polygon tip;
	public Polygon blade1;
	public Polygon blade2;
	public Rectangle outerFire;
	public Rectangle innerFire;
	boolean exploded = false;
	private int missileSpeed;
	private int explodeTimer;
	private double explodeLocationX,explodeLocationY;
	private int screenHeight,screenWidth;
	private boolean bulletMissileCollisionBool;
	
	
	public Missile(int mSpeed, int screenWidth, int screenHeight, int startingX, int startingY){
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		missile = new Rectangle(0,0,5,15);
		outerFire = new Rectangle(0,0,3,5);
		innerFire = new Rectangle(0,0,1,9);
		circle = new Circle();
		tip = new Polygon();
		blade1 = new Polygon();
		blade2 = new Polygon();
		missile.setX(startingX);
		missile.setY(startingY);
		tip.getPoints().addAll(new Double[]{
				missile.getX() - 2.0, missile.getY() + 15.0,
				missile.getX() + 7.0, missile.getY() + 15.0,
				missile.getX() + 2.5, missile.getY() + 19.0
		});
		blade1.getPoints().addAll(new Double[]{
				missile.getX(), missile.getY() + 1,
				missile.getX(), missile.getY() + 5,
				missile.getX() - 4, missile.getY() + 1
		});
		blade2.getPoints().addAll(new Double[]{
				missile.getX() + 5, missile.getY() + 1,
				missile.getX() + 5, missile.getY() + 5,
				missile.getX() + 9, missile.getY() + 1
		});
		blade1.setFill(Paint.valueOf("red"));
		blade2.setFill(Paint.valueOf("red"));
		tip.setFill(Paint.valueOf("red"));
		outerFire.setX(missile.getX() + 1);
		outerFire.setY(missile.getY() - 5);
		outerFire.setFill(Paint.valueOf("orange"));
		outerFire.setOpacity(.5);
		innerFire.setX(missile.getX() + 2);
		innerFire.setY(missile.getY() - 9);
		innerFire.setFill(Paint.valueOf("yellow"));
		innerFire.setOpacity(.45);
		this.missileSpeed = mSpeed;
		System.out.println(missile.getX());
	}
	
	
	
	
	public void animateMissile(Pane screenLayout, Iterator<Circle> bullets, Turret turret, HashSet<Integer> missileCoordsX){
		if(exploded){
     	   missileExplode(screenLayout);
     	   System.out.println("explode animation");
        }else{
     	   if(!(bulletMissileCollisionBool || missileFloorCollisionInGame(turret) || turretMissileCollision(turret))){
     		   moveMissile();
     	   }else{
     		   setupMissileExplosion(missileCoordsX);
     	   }   
        }
	}
	
	
	public void animateMissileHomeScreen(Pane screenLayout, HashSet<Integer> missileCoordsX){
		if(exploded){
	     	   missileExplode(screenLayout);
	        }else{
	     	   if(!missileBottomCollisionHomeScreen())
	     		   moveMissile();
	     	   else{
	     		   setupMissileExplosion(missileCoordsX);
	     	   }
	      }
	}
	
	private void setupMissileExplosion(HashSet<Integer> missileCoordsX){
		setExplodeLocationX(missile.getX());  
		setExplodeLocationY(missile.getY());
		//resetMissile method
		missileCoordsX.remove((int) this.getX());
		missile.setX(getNewMissileX(missileCoordsX));
		missileCoordsX.add((int) this.getX());
		missile.setY((Math.random() * -100) - 20);
		resetTip(missile.getX(),missile.getY());
		resetFire(missile.getX(),missile.getY());
		resetBlade1(missile.getX(),missile.getY());
		resetBlade2(missile.getX(),missile.getY());
		bulletMissileCollisionBool = false;
		exploded = true;
	}
	
	public void missileExplode(Pane screenLayout){
		if(getExplodeTimer() == 0){
			circle.setCenterX(getExplodeLocationX());
			circle.setCenterY(getExplodeLocationY());
			circle.setRadius(20);
			circle.setOpacity(.75);
			screenLayout.getChildren().add(circle);
			//System.out.println("circle added");
		} else if(getExplodeTimer() < 40){
			circle.setRadius(circle.getRadius() + 1);
			circle.setFill(Paint.valueOf("red"));
			circle.setOpacity(circle.getOpacity() - .02);
			circle.setCenterX(getExplodeLocationX());
			circle.setCenterY(getExplodeLocationY());
			//System.out.println("time < 40");
		} else if(getExplodeTimer() < 100){
			circle.setOpacity((circle.getOpacity() - .01));
		}else{
			screenLayout.getChildren().remove(circle);
			setExplodeTimer(0);
			exploded = false;
			bulletMissileCollisionBool = false;
			return;
		}
		setExplodeTimer(getExplodeTimer() + 1);
	}
	
	public void removeBulletFromIterator(Iterator<Circle> bullets, Circle bullet){
		while(bullets.hasNext()){
			Circle nextBullet = bullets.next();
			if(nextBullet.equals(bullet)){
				bullets.remove();
				System.out.println("removing a bullet from collision");
				break;
			}
		}
	}
	
	private int getNewMissileX(HashSet<Integer> missileCoords){
		int coord = (int) (Math.random() * screenWidth);
		for(Integer i : missileCoords){
			if(Math.abs((coord - i)) < 30){
				return getNewMissileX(missileCoords);
			}
		}
		return coord;
	}
	
	//Collision detections
	
	public boolean missileBottomCollisionHomeScreen(){
		if(missile.getY() + 10 > screenHeight)
			return true;
		return false;
	}
	
	public boolean missileFloorCollisionInGame(Turret t){
		if(missile.getY() + 30 > screenHeight){
			t.subtractFromTurretHealth(50);
			System.out.println(t.getTurretHealth());
			Sound.playBaseMissileCollisionSound();
			return true;
		}
		return false;
	}
	
	public void bulletMissileCollisionListener(Iterator<Circle> bullets, Pane p){
		if(!bullets.hasNext()){
			bulletMissileCollisionBool = false;
		}else{
			while(bullets.hasNext()){
				Circle bullet = bullets.next();
				if(bullet.getBoundsInParent().intersects(missile.getBoundsInParent()) || bullet.getBoundsInParent().intersects(tip.getBoundsInParent())){
					System.out.println("collision!");
					bullets.remove();
					p.getChildren().remove(bullet);
					System.out.println("removed bullet and missile.");
					Sound.playShotMissileCollisionSound();
					bulletMissileCollisionBool = true;
					return;
				}
			}
		}
	}
	
	public boolean turretMissileCollision(Turret turret){
		if(turret.barrelEnd.getBoundsInParent().intersects(missile.getBoundsInParent()) || 
				turret.barrel.getBoundsInParent().intersects(missile.getBoundsInParent()) || 
					turret.baseDome.getBoundsInParent().intersects(missile.getBoundsInParent())){
			turret.subtractFromTurretHealth(10);
			Sound.playBaseMissileCollisionSound();
			return true;
		}else{
			return false;
		}
	}
	
	
	//Move and reset missile methods
	
	public void moveMissile(){
		missile.setY(missile.getY() + missileSpeed);
		moveFire();
		movePolygonY(blade1);
		movePolygonY(tip);
		movePolygonY(blade2);
	}
	
	public void moveTipY(){
		int i = 1;
		for(Double d : tip.getPoints()){
			if(i%2 ==0){
				tip.getPoints().set(i - 1, d + missileSpeed);
			}
			i++;
		}
	}
	
	public void movePolygonY(Polygon p){
		int i = 1;
		for(Double d : p.getPoints()){
			if(i%2 ==0){
				p.getPoints().set(i - 1, d + missileSpeed);
			}
			i++;
		}
	}
	
	public void moveFire(){
		outerFire.setY(outerFire.getY() + missileSpeed);
		innerFire.setY(innerFire.getY() + missileSpeed);
	}
	
	public void resetTip(Double x,Double y){
		for(int i = 1; i <= tip.getPoints().size() / 2; i++){
			if(i%3 == 1){
				tip.getPoints().set(i - 1, x - 2.0);
				tip.getPoints().set(i, y + 15.0);
			}else if(i%3 == 2){
				tip.getPoints().set(i, x + 7.0);
				tip.getPoints().set(i + 1, y + 15.0);
			}else if(i%3==0){
				tip.getPoints().set(i + 1, x + 2.5);
				tip.getPoints().set(i + 2, y + 19.0);
			}
		}
	}
	public void resetBlade1(Double x, Double y){
		for(int i = 1; i <= blade1.getPoints().size() / 2; i++){
			if(i%3 == 1){
				blade1.getPoints().set(i - 1, x);
				blade1.getPoints().set(i, y + 1);
			}else if(i%3 == 2){
				blade1.getPoints().set(i, x);
				blade1.getPoints().set(i + 1, y + 5);
			}else if(i%3==0){
				blade1.getPoints().set(i + 1, x - 4);
				blade1.getPoints().set(i + 2, y + 1);
			}
		}
	}
	public void resetBlade2(Double x, Double y){
		for(int i = 1; i <= blade2.getPoints().size() / 2; i++){
			if(i%3 == 1){
				blade2.getPoints().set(i - 1, x + 5);
				blade2.getPoints().set(i, y + 1);
			}else if(i%3 == 2){
				blade2.getPoints().set(i, x + 5);
				blade2.getPoints().set(i + 1, y + 5);
			}else if(i%3==0){
				blade2.getPoints().set(i + 1, x + 9);
				blade2.getPoints().set(i + 2, y + 1);
			}
		}
	}
	
	public void resetFire(Double x, Double y){
		outerFire.setX(x + 1);
		outerFire.setY(y - 5);
		innerFire.setX(x + 2);
		innerFire.setY(y - 9);
	}
	
	
	
	//Getters and setters
	
	public double getY(){
		return missile.getY();
	}
	
	public double getX(){
		return missile.getX();
	}

	public int getScreenHeight(){
		return this.screenHeight;
	}
	
	public void setExplodeTimer(int eTime){
		this.explodeTimer = eTime;
	}
	
	public int getExplodeTimer(){
		return this.explodeTimer;
	}
	
	public void setExplodeLocationX(double eLocation){
		this.explodeLocationX = eLocation;
	}
	
	public double getExplodeLocationX(){
		return explodeLocationX;
	}
	
	public void setExplodeLocationY(double eLocation){
		this.explodeLocationY = eLocation;
	}
	
	public double getExplodeLocationY(){
		return explodeLocationY;
	}
	

}
