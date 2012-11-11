package othello;
public interface Player {
	void init(int order, long t, java.util.Random rnd);
	Move nextMove(Move prevMove, long tOpponent, long t);
}
