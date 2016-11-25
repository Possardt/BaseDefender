package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public final class HighScore {
	//private static File file = ClassLoader.getSystemResource("/resources/HighScore.txt");
	private static Comparator<Integer> highToLowComparator = new Comparator<Integer>(){
		@Override
		public int compare(Integer a, Integer b){
			if((b - a) > 0){
				return 1;
			}else if((b - a) < 0){
				return -1;
			}
			return 0;
		}
	};
	
	
	public static ArrayList<Integer> getHighScores(){
		ArrayList<Integer> scores = new ArrayList<Integer>();
		
		try{
			BufferedReader bufferedReader = new BufferedReader(new FileReader("HighScore.txt"));
			String line = bufferedReader.readLine();
			while(line != null){
				scores.add(Integer.parseInt(line));
				line = bufferedReader.readLine();
			}
			scores.sort(highToLowComparator);
			bufferedReader.close();
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return scores;
	}
	
	public static void addHighScore(int score){
		StringBuilder scoreString = new StringBuilder();
		scoreString.append(score + "\n");
		try {
			FileWriter writer = new FileWriter("HighScore.txt", true);
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			bufferedWriter.append(scoreString);
			bufferedWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static int getWorstHighScore(){
		int listSize = getHighScores().size();
		if(listSize == 0){
			return 0;
		}else if(listSize < 10){
			return getHighScores().get(listSize - 1);
		}
		return getHighScores().get(9);
	}
}
