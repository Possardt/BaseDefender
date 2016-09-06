package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

public final class HighScore {
	private static File file = new File("../resources/HighScore.txt");
	
	
	public static int getHighScore(){
		int score = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			while(line != null){
				score = Integer.parseInt(line.trim());
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return score;
	}
	
	public static void setHighScore(int score){
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(score + "\n");
			writer.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
