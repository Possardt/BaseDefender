package application;

import java.util.ArrayList;

import com.sun.scenario.effect.Effect;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
//You could do even more For these special firework types(ie, they can inherit more..


public class OutwardLineFirework extends Firework {
	private ArrayList<Rectangle> explosionEffectContainer = new ArrayList<Rectangle>();
	
	public OutwardLineFirework(int screenX, int screenY){
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
	
	@Override
	public void setupExplosion(Pane highScorePane) {
		for(int i = 0; i < 60; i++){
			explosionEffectContainer.add(new Rectangle(explodeLocX,explodeLocY, 4,2));
		}
		String color = getRandomColor();
		int rotate = 0;
		
		for(Rectangle r : explosionEffectContainer){
			r.setFill(Paint.valueOf(color));
			r.getTransforms().add(new Rotate(rotate, explodeLocX,explodeLocY));
			rotate += (360 / explosionEffectContainer.size());
		}
		
		highScorePane.getChildren().addAll(explosionEffectContainer);
	}

	@Override
	public void explodeFireworkAnimation(Pane highScorePane) {
		if(fuse == 0){
			setupExplosion(highScorePane);
			fuse--;
		}else if(fuse > -500){
			moveExplosionOutward(highScorePane);
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
	
	private void moveExplosionOutward(Pane highScorePane){
		for(Rectangle r : explosionEffectContainer){
			r.setX(r.getX() - 3);
			r.setWidth(r.getWidth() + 1);
			r.setOpacity(r.getOpacity() - .01);
		}
	}
}
