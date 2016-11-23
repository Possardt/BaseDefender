package application;

import java.io.InputStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public final class Sound {
	public void playTurretShotSound(){
		try{
			InputStream inputStream = getClass().getResourceAsStream("turretShot.wav");
			AudioStream audioStream = new AudioStream(inputStream);
			AudioPlayer.player.start(audioStream);
		}catch(Exception e){
			System.out.println("Sound not played");
			e.printStackTrace();
		}
}
}
