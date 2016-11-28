package application;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class OutwardRectangleFirework extends Firework{
	
	protected OutwardRectangleFirework(int screenX, int screenY, int delay) {
		super(screenX, screenY,delay);
	}
	
	@Override
	public void setupExplosion(Pane highScorePane){
		
		for(int i = 0; i <=20; i++){
			explosionEffectContainer.add(new Rectangle(explodeLocX,explodeLocY, 4, 2));
		}
		
		String color = getRandomColor();
		int rotate = 0;
		
		for(Rectangle r : explosionEffectContainer){
			r.setFill(Paint.valueOf(color));
			r.getTransforms().add(new Rotate(rotate, explodeLocX, explodeLocY));
			rotate += (360 / explosionEffectContainer.size());
		}
		
		
		highScorePane.getChildren().addAll(explosionEffectContainer);
		Sound.playFireworkExplodeSound();
	}
	
	@Override
	public void explodeFireworkAnimation(Pane highScorePane){
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
	
	private void moveExplosionOutward(){
		for(Rectangle r : explosionEffectContainer){
			r.setX(r.getX() - 2);
			r.setOpacity(r.getOpacity() - .02);
		}

	}
}
