package application;

import java.util.ArrayList;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class OutwardCircleFirework extends Firework{
	private ArrayList<Circle> explosionEffectContainer = new ArrayList<Circle>();
	
	public OutwardCircleFirework(int screenX, int screenY){
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
	
	@Override
	public void setupExplosion(Pane highScorePane) {
		for(int i = 0;i <= 15;i++){
			explosionEffectContainer.add(new Circle(explodeLocX,explodeLocY,2));
		}
		
		String color = getRandomColor();
		int rotate = 0;
		
		for(Circle circle : explosionEffectContainer){
			circle.setFill(Paint.valueOf(color));
			circle.getTransforms().add(new Rotate(rotate, explodeLocX, explodeLocY));
			rotate += (360 / explosionEffectContainer.size());
		}
		highScorePane.getChildren().addAll(explosionEffectContainer);
	}

	@Override
	public void explodeFireworkAnimation(Pane highScorePane) {
		if(fuse == 0){
			setupExplosion(highScorePane);
			fuse--;
		}else if(fuse > -300){
			moveExplosionOutward();
			fuse--;
		}else{
			highScorePane.getChildren().removeAll(explosionEffectContainer);
			explosionEffectContainer.clear();
			explodeFirework = false;
			shootFirework = false;
			reloadFirework = true;
		}
	}
	
	private void moveExplosionOutward(){
		for(Circle circle : explosionEffectContainer){
			circle.setCenterX(circle.getCenterX() - 2);
			circle.setOpacity(circle.getOpacity() - .01);
		}
	}
	
}
