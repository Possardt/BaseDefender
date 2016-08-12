package application;


import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
//import javafx.stage.Screen;

public class Missile {
	//includes all shapes that go into creating the missile
	public Rectangle missile;
	public Circle circle;
	public Polygon tip;
	public Polygon blade1;
	public Polygon blade2;
	public Rectangle outerFire;
	public Rectangle innerFire;
	boolean isExploded = false;
	boolean exploded = false;
	private int missileSpeed;
	private int explodeTime;
	private double explodeLocation;
	private int screenHeight,screenWidth;
	
	public void setExplodeTime(int eTime){
		this.explodeTime = eTime;
	}
	
	public int getExplodeTime(){
		return this.explodeTime;
	}
	
	public void setExplodeLocation(double eLocation){
		this.explodeLocation = eLocation;
	}
	
	public double getExplodeLocation(){
		return explodeLocation;
	}
	
	public Missile(int mSpeed, int screenWidth, int screenHeight){
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		missile = new Rectangle(0,0,5,15);
		outerFire = new Rectangle(0,0,3,5);
		innerFire = new Rectangle(0,0,1,9);
		circle = new Circle();
		tip = new Polygon();
		blade1 = new Polygon();
		blade2 = new Polygon();
		missile.setY((int)(Math.random()*-200)-20);
		missile.setX((int)(Math.random() * screenWidth));
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
	
	public void moveMissile(){
		missile.setY(missile.getY() + missileSpeed);
		moveFire();
		movePolygonY(blade1);
		movePolygonY(tip);
		movePolygonY(blade2);
	}
	
	public boolean isExploded(){
		if((missile.getY() + 30) > screenHeight)
			isExploded = true;
		else
			isExploded = false;
		return isExploded;
	}
	
	public double getY(){
		return missile.getY();
	}
	public void animateMissile(Pane screenLayout, Explosion explosion){
		if(exploded){
     	   explosion.missileExplode(screenLayout,this);
     	   if(getExplodeTime() > 100){
     		   exploded = false;
     		   setExplodeTime(0);
     	   }else
     		   setExplodeTime(getExplodeTime() + 1);
        }else{
     	   if(!isExploded())
     		   moveMissile();
     	   if(isExploded()){
     		   setExplodeLocation(missile.getX());           		   
     		   missile.setX(Math.random() * screenWidth);
     		   missile.setY((Math.random() * -100) - 20);
     		   resetTip(missile.getX(),missile.getY());
     		   resetFire(missile.getX(),missile.getY());
     		   resetBlade1(missile.getX(),missile.getY());
     		   resetBlade2(missile.getX(),missile.getY());
     		   exploded = true;
     	   }   
        }
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
	public void moveFire(){
		outerFire.setY(outerFire.getY() + missileSpeed);
		innerFire.setY(innerFire.getY() + missileSpeed);
	}
	
	public void resetFire(Double x, Double y){
		outerFire.setX(x + 1);
		outerFire.setY(y - 5);
		innerFire.setX(x + 2);
		innerFire.setY(y - 9);
	}
	public int getScreenHeight(){
		return this.screenHeight;
	}
}
