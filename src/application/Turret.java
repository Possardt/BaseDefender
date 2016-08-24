package application;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class Turret {
	public Rectangle base, barrel, barrelEnd, turretHealthBar;
	public Arc baseDome;
	private double barrelAngle = 0;
	private Rotate barrelRotation;
	private int bulletSpeed = 6;
	private Set<Circle> bullets = new HashSet<Circle>();
	private int timeShot;
	private int turretHealth = 100;
	
	
	/**
	 * Constructor
	 */
	public Turret(){
		base = new Rectangle(330,460, 40, 20);
		baseDome = new Arc(base.getX() + (base.getWidth() / 2),base.getY(),40,40, 0, 180);
		barrel = new Rectangle(base.getX() + (base.getWidth() / 2) + 30, base.getY() - 15,80,10);
		barrelEnd = new Rectangle(barrel.getX() + barrel.getWidth(), barrel.getY() - 5,20,20);
		turretHealthBar = new Rectangle(50,40,100,10);
		base.setFill(Paint.valueOf("grey"));
		barrelEnd.setFill(Paint.valueOf("grey"));
		baseDome.setFill(Paint.valueOf("grey"));
		turretHealthBar.setFill(Paint.valueOf("red"));
	}
	
	public void rotateTurret(double mousePositionX, double mousePositionY){
		double angle = (int)Math.toDegrees(Math.atan2(mousePositionY - (barrel.getY() + 10),mousePositionX - (barrel.getX() + 5)));
		barrelRotation = new Rotate(angle - 360,base.getX() + (base.getWidth() / 2), base.getY() - 10);
		if((angle != barrelAngle && (angle < 180))  ||(angle!= barrelAngle && (angle > 0))){
			
			barrel.getTransforms().clear();
			barrelEnd.getTransforms().clear();
			barrel.getTransforms().add(barrelRotation);		
			barrelEnd.getTransforms().add(barrelRotation);
			
			barrelAngle = angle;
			
		}
	}

	public void turretRecoil(Pane p, int time){
		int relativeTime = time - timeShot;
		if(barrel.getX() < 345){
			barrel.setX(barrel.getX() + 1);
			barrelEnd.setX(barrelEnd.getX() + 1);
			return;
		}
		if(relativeTime < 10){
			barrel.setX(barrel.getX() - 4);
			barrelEnd.setX(barrelEnd.getX() - 4);
		}else if(relativeTime < 20){
			barrel.setX(barrel.getX() + 1);
			barrelEnd.setX(barrelEnd.getX() + 1);
		}
		if(relativeTime < 50 && barrel.getX() < 360){
			barrel.setX(barrel.getX() + 1);
			barrelEnd.setX(barrelEnd.getX() + 1);
		}		
	}
	
	public void turretShot(Pane p, int time, double mouseX, double mouseY){
		if(time == timeShot){
			Circle bullet = new Circle(barrelEnd.getX(),barrelEnd.getY(),5);
			p.getChildren().add(bullet);
			barrelRotation.setAngle(barrelRotation.getAngle() + 5);
			bullet.getTransforms().add(barrelRotation);
			bullets.add(bullet);
			bullet.setOpacity(.1);
		}else{
			Iterator<Circle> bulletIterator = bullets.iterator();
			while(bulletIterator.hasNext()){
				Circle bullet = bulletIterator.next();
				if(bullet.getCenterX() < -100 || bullet.getCenterX() > 1200){
					p.getChildren().remove(bullet);
					System.out.println("bullet removed.");
					bulletIterator.remove();
				}else{
					bullet.setCenterX(bullet.getCenterX() + (bulletSpeed * Math.cos(0.0)));
					bullet.setOpacity(bullet.getOpacity() + .01);
				}
			}
		}		
	}
	
	public void updateHealth(){
		turretHealthBar.setWidth(getTurretHealth());
	}
	
	public void setTimeShot(int t){
		this.timeShot = t;
	}
	public Iterator<Circle> getBulletIterator(){
		return bullets.iterator();
	}
	public void subtractFromTurretHealth(int amountToSubtract){
		this.turretHealth = turretHealth - amountToSubtract;
	}
	public int getTurretHealth(){
		return this.turretHealth;
	}
	
	public void setTurretHealth(int health){
		this.turretHealth = health;
		updateHealth();
	}
}
