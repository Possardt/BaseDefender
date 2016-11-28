package application;

import java.io.InputStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public final class Sound {
	public static void playTurretShotSound(){
		try{
			InputStream inputStream = Sound.class.getResourceAsStream("turretShot.wav");
			AudioStream audioStream = new AudioStream(inputStream);
			AudioPlayer.player.start(audioStream);
		}catch(Exception e){
			System.out.println("Sound not played");
			e.printStackTrace();
		}
	}
	public static void playShotMissileCollisionSound(){
		try{
			InputStream inputStream = Sound.class.getResourceAsStream("turretShotMissileColl.wav");
			AudioStream audioStream = new AudioStream(inputStream);
			AudioPlayer.player.start(audioStream);
		}catch(Exception e){
			System.out.println("Sound not played");
			e.printStackTrace();
		}
	}
	public static void playFireworkShotSound(){
		try{
			InputStream inputStream = Sound.class.getResourceAsStream("fireworkShot.wav");
			AudioStream audioStream = new AudioStream(inputStream);
			AudioPlayer.player.start(audioStream);
		}catch(Exception e){
			System.out.println("Sound not played");
			e.printStackTrace();
		}
	}
	public static void playFireworkExplodeSound(){
		try{
			InputStream inputStream = Sound.class.getResourceAsStream("fireworkExplode.wav");
			AudioStream audioStream = new AudioStream(inputStream);
			AudioPlayer.player.start(audioStream);
		}catch(Exception e){
			System.out.println("Sound not played");
			e.printStackTrace();
		}
	}
	
}
