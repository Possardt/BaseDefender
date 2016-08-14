package application;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

public class Explosion {
		
	public void missileExplode(Pane screenLayout, Missile missile){
		if(missile.getExplodeTime() == 0){
			missile.circle.setCenterX(missile.getExplodeLocationX());
			missile.circle.setCenterY(missile.getExplodeLocationY());
			missile.circle.setRadius(20);
			missile.circle.setOpacity(.75);
			screenLayout.getChildren().add(missile.circle);
			//System.out.println("circle added");
		}
		if(missile.getExplodeTime() < 40){
			missile.circle.setRadius(missile.circle.getRadius() + 1);
			missile.circle.setFill(Paint.valueOf("red"));
			missile.circle.setOpacity(missile.circle.getOpacity() - .02);
			missile.circle.setCenterX(missile.getExplodeLocationX());
			missile.circle.setCenterY(missile.getExplodeLocationY());
			//System.out.println("time < 40");
		} else if(missile.getExplodeTime() < 100){
			missile.circle.setOpacity((missile.circle.getOpacity() - .01));
			
		}else{
			screenLayout.getChildren().remove(missile.circle);
		}
		
	}
	
	
}
