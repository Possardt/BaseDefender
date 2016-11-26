package application;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class OutwardLineFirework extends Firework {
	
	public OutwardLineFirework(int screenX, int screenY, int delay){
		super(screenX,screenY, delay);
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
