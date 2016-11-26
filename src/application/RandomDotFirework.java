package application;

import java.util.ArrayList;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class RandomDotFirework extends Firework{
	private ArrayList<Circle> explosionEffectContainer = new ArrayList<Circle>();
	
	protected RandomDotFirework(int screenX, int screenY, int delay) {
		super(screenX, screenY,delay);
	}

	@Override
	public void setupExplosion(Pane highScorePane) {
		for(int i = 0; i<= 20; i++){
			Circle circle = new Circle(getRandomCoord(explodeLocX),getRandomCoord(explodeLocY),getRandomSize());
			circle.setFill(Paint.valueOf(this.color));
			explosionEffectContainer.add(circle);
		}
		highScorePane.getChildren().addAll(explosionEffectContainer);
	}

	@Override
	public void explodeFireworkAnimation(Pane highScorePane) {
		if(fuse == 0){
			setupExplosion(highScorePane);
			fuse--;
		}else if(fuse > -200){
			if((fuse * -1) % 5 == 0){
				for(Circle circle : explosionEffectContainer){
					if(showCircle())
						circle.setOpacity(1);
					else
						circle.setOpacity(0);
				}
			}
			fuse--;
		}else if(fuse > -300){
			//fade out effect
			for(Circle circle : explosionEffectContainer){
				circle.setOpacity(circle.getOpacity() - .02);
			}
			fuse--;
		}else{
			highScorePane.getChildren().removeAll(explosionEffectContainer);
			explosionEffectContainer.clear();
			explodeFirework = false;
			shootFirework = false;
			reloadFirework = true;
		}
		
	}
	
	@Override
	public void removeExplosionEffects(Pane highScorePane){
		highScorePane.getChildren().removeAll(explosionEffectContainer);
		explosionEffectContainer.clear();
	}
	
	private int getRandomCoord(int screenX){
		boolean isNegative = Math.random() > .5 ? true : false;
		int coord = (int)((Math.random() * 100) % 50);
		coord = isNegative ? coord : coord * -1;
		return coord + screenX;
		
	}
	private int getRandomSize(){
		return (int)((Math.random() * 100) % 5);
	}
	private boolean showCircle(){
		return Math.random() > .5 ? true : false;
	}

}
