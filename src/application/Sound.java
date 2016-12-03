package application;

import java.io.InputStream;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public final class Sound {
	public static void playTurretShotSound(){
		playSound("turretShot.wav");
	}
	public static void playShotMissileCollisionSound(){
		playSound("turretShotMissileColl.wav");
	}
	public static void playFireworkShotSound(){
		playSound("fireworkShot.wav");
	}
	public static void playFireworkExplodeSound(){
		playSound("fireworkExplode.wav");
	}
	public static void playBaseMissileCollisionSound(){
		playSound("baseMissileColl.wav");
	}
	private static void playSound(String wav){
		try{
			InputStream inputStream = Sound.class.getResourceAsStream(wav);
			AudioStream audioStream = new AudioStream(inputStream);
			AudioPlayer.player.start(audioStream);
		}catch(Exception e){
			System.out.println("Sound not played");
			e.printStackTrace();
		}
	}
	
}
