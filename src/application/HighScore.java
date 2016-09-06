package application;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

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
		try {
			Scanner scanner = new Scanner(HighScore.class.getResourceAsStream("/resources/HighScore.txt"));
			while(scanner.hasNextLine()){
				String line = scanner.nextLine();
				scores.add(Integer.parseInt(line));
			}
			scanner.close();
			scores.sort(highToLowComparator);
		}catch(Exception e){
			e.printStackTrace();
		}
			/*
			BufferedReader reader = new BufferedReader(new FileReader("/resources/HighScore.txt"));
			String line = reader.readLine();
			while(line != null){
				scores.add(Integer.parseInt(line));
			}
			scores.sort(null);
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		return scores;
	}
	
	public static void addHighScore(int score){
		StringBuilder scoreString = new StringBuilder();
		scoreString.append(score + "\n");
		/*
		try{
			Files.write(Paths.get(HighScore.class.getResource("/resources/HighScore.txt").toURI()), scoreString.toString().getBytes(), StandardOpenOption.APPEND);
		}catch(Exception e){
			e.printStackTrace();
		}
		*/
		try {
			FileWriter writer = new FileWriter("/resources/HighScore.txt", true);
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			bufferedWriter.write(scoreString.toString());
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
