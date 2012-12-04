package othello;
import szte.mi.Move;
import java.util.ArrayList;

public interface Board {
	public int getPosition(int x, int y);
	public int getPosition(Move move);
	public void setPosition(int x, int y, int player);
	public void setPosition(Move move, int player);
	public int countStableTokens(int player);
	public int countTokens(int player);
	public Board copyBoard();
	public boolean hasValidMove(int player);
	public boolean isValidMove(int player, int n);
	public int makeMove(int player, int n);
	public ArrayList<Integer> getValidMoves(int player);
}
