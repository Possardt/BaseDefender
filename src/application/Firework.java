package application;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Firework {
	private Rectangle body;
	private Rectangle trail;
	private String[] colors = {"Red","Purple","Green","Orange"};
	
	public Firework(int locX, int locY){
		body = new Rectangle(locX, locY, 10, 30);
		body.setFill(Paint.valueOf(getRandomColor()));
	}
	
	
	private String getRandomColor(){
		return colors[(int)Math.random() * 100 % colors.length];
	}
	
}
