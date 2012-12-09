import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import othello.Othello;
import ai.Evaluation;


public class ConvertBook {
	public static void main(String[] args) {
		ArrayList<BookEntry> output = new ArrayList<BookEntry>();
		Evaluation eval = new Evaluation();
		try (BufferedReader reader = Files.newBufferedReader(Paths.get("book.txt"), Charset.forName("UTF-8"))) {
			String line = null;
			int i = 0;
			int j = 0;
			Othello o = new Othello();
			while ((line = reader.readLine()) != null) {
				if (line.charAt(0) == 'b') {
					o.makeMove(2, Integer.parseInt(line.substring(1).trim()));
					int[] mobDiff = o.getBoard().getMobilityDiffs(2);
					output.add(new BookEntry(o.getBlackTokens() - o.getWhiteTokens(), mobDiff[1], mobDiff[0], eval.getCornerDiff(o, 2, 1), eval.getXSquaresDiff(o, 2, 1), o.getNumMoves()));
					i++;
				}
				else if (line.charAt(0) == 'w') {
					o.makeMove(1, Integer.parseInt(line.substring(1).trim()));
					int[] mobDiff = o.getBoard().getMobilityDiffs(2);
					output.add(new BookEntry(o.getBlackTokens() - o.getWhiteTokens(), mobDiff[1], mobDiff[0], eval.getCornerDiff(o, 2, 1), eval.getXSquaresDiff(o, 2, 1), o.getNumMoves()));
					i++;
				}
				else {
					for (int n = j; n < i; n++) {
						int res = Integer.parseInt(line);
						output.get(n).setResult(res);
					}
					o = new Othello();
					j = i;
				}
				
			}
		} catch (IOException ex) {
				System.err.format("IOException; %s&n", ex);
		}
		
		try {
			BufferedWriter writer[] = new BufferedWriter[16];
			for (int i = 0; i < 16; i++) {
				writer[i] = Files.newBufferedWriter(Paths.get("positions" + i + ".txt"), Charset.forName("UTF-8"), java.nio.file.StandardOpenOption.WRITE);
				writer[i].write("discD;mobD;potmobD;cornerD;xsquD;moves;result");
			}
			for (int i = 0; i< output.size(); i++) {	
				writer[output.get(i).getNumMoves() / 4].write(output.get(i).toString());
			}
		} catch (IOException ex) {
			System.err.format("IOException: %s%n", ex);
		}
		
	}
	
	private static class BookEntry{
		private int pieceDiff;
		private int mobDiff;
		private int potMobDiff;
		private int cornerDiff;
		private int xDiff;
		private int numMoves;
		private int result;
		
		public BookEntry(int pieceDiff, int mobDiff, int potMobDiff, int cornerDiff, int xDiff, int numMoves) {
			this.pieceDiff = pieceDiff;
			this.mobDiff = mobDiff;
			this.potMobDiff = potMobDiff;
			this.cornerDiff = cornerDiff;
			this.xDiff = xDiff;
			this.numMoves = numMoves;
		}
		
		public int getNumMoves() {
			return numMoves;
		}
		
		public void setResult(int result) {
			this.result = result;
		}
		
		public String toString() {
			return pieceDiff + ";" + mobDiff + ";" + potMobDiff + ";" + cornerDiff + ";" + xDiff + ";" + numMoves + ";" + result + "\n";
		}
	}
}
