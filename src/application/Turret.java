package application;


import java.util.HashMap;
import java.util.Iterator;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class Turret {
	public Rectangle base, barrel, barrelEnd;
	public Arc baseDome;
	private double barrelAngle = 0;
	private Rotate barrelRotation;
	private int bulletSpeed = 3;
	private HashMap<Circle, Double> bullets = new HashMap<Circle, Double>();
	private int timeShot;
	public Turret(){
		base = new Rectangle(330,460, 40, 20);
		baseDome = new Arc(base.getX() + (base.getWidth() / 2),base.getY(),40,40, 0, 180);
		barrel = new Rectangle(base.getX() + (base.getWidth() / 2) + 30, base.getY() - 15,80,10);
		barrelEnd = new Rectangle(barrel.getX() + barrel.getWidth(), barrel.getY() - 5,20,20);	
		base.setFill(Paint.valueOf("grey"));
		barrelEnd.setFill(Paint.valueOf("grey"));
		baseDome.setFill(Paint.valueOf("grey"));
		//barrel.getTransforms().add(new Rotate(340,0,0));
	}
	
	public void rotateTurret(double mousePositionX, double mousePositionY){
		double angle = (int)Math.toDegrees(Math.atan2(mousePositionY - (barrel.getY() + 10),mousePositionX - (barrel.getX() + 5)));
		barrelRotation = new Rotate(angle - 360,base.getX() + (base.getWidth() / 2), base.getY() - 10);
		//angle = Math.abs(angle);
		//System.out.println( "rotation axis: " + barrel.getRotationAxis().getX() + " " + barrel.getRotationAxis().getY() + " "+ barrel.getRotationAxis().getZ());
		
		//System.out.println("r: " + Math.abs(angle) + " z: " + base.getScaleZ());
		if((angle != barrelAngle && (angle < 180))  ||(angle!= barrelAngle && (angle > 0))){
			
			barrel.getTransforms().clear();
			barrelEnd.getTransforms().clear();
			barrel.getTransforms().add(barrelRotation);		
			barrelEnd.getTransforms().add(barrelRotation);
			
			barrelAngle = angle;
			
		}
	}
	//need to rethink this method
	public void turretRecoil(Pane p, int time){
		int relativeTime = time - timeShot;
		//System.out.println("barrel x: "+barrel.getX());
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
			bullets.put(bullet, 0.0);
		}else{
			/*
			//moving and removing the bullets fired from the turret
			if(!bullets.isEmpty()){
				for(Circle c : bullets.keySet()){
					if(c.getCenterX() < -100 || c.getCenterX() > 1200){
						p.getChildren().remove(c);
						System.out.println("bullet removed.");
						bullets.remove(c);
					}else{
						c.setCenterX(c.getCenterX() + (bulletSpeed * Math.cos(bullets.get(c))));
					}
				}
			}*/
			Iterator<Circle> bulletIterator = bullets.keySet().iterator();
			while(bulletIterator.hasNext()){
				Circle bullet = bulletIterator.next();
				if(bullet.getCenterX() < -100 || bullet.getCenterX() > 1200){
					p.getChildren().remove(bullet);
					System.out.println("bullet removed.");
					bulletIterator.remove();
				}else{
					bullet.setCenterX(bullet.getCenterX() + (bulletSpeed * Math.cos(0.0)));
				}
			}
		}		
	}
	
	public void setTimeShot(int t){
		this.timeShot = t;
	}
}
