/**
 * @author      Jonas Raedle <jonas@jraedle.de>
 */
package othello;

import szte.mi.Move;
import java.util.ArrayList;

public class BitBoard {
	private long white;
	private long black;
	
	/**
	 * Instantiates a BitBoard with the standard othello starting conformation.
	 */
	public BitBoard() {
		white = black = 0;
		white |= 1L << ((3 * 8) + 3);
		white |= 1L << ((4 * 8) + 4);
		black |= 1L << ((3 * 8) + 4);
		black |= 1L << ((4 * 8) + 3);
	}
	
	/**
	 * Instantiates a BitBoard in the conformation represented by the parameters.
	 * @param white A bit field where bits occupied by white are set to 1.
	 * @param black A bit field where bits occupied by black are set to 1.
	 */
	public BitBoard(long white, long black) 
		{ this.white = white; this.black = black; }
	
	/**
	 * Checks the status of the cell at coordinates <b>x</b> and <b>y</b>.
	 * @param x X coordinate at which to check.
	 * @param y Y coordinate at which to check.
	 * @return 0: unoccupied, 1: white, 2: black
	 */
	public int getPosition(int x, int y) {
		long val = 1L << coordToIndex(x,y);
		if ((white & val) != 0L) return 1;
		else if ((black & val) != 0L) return 2;
		else return 0;
	}
	
	/**
	 * Checks the status of the cell at index <b>n</b>.
	 * @param n Index at which to check. 0 <= n < 64
	 * @return 0: unoccupied, 1: white, 2: black
	 */
	public int getPosition(int n) {
		long val = 1L << n;
		if ((white & val) != 0L) return 1;
		else if ((black & val) != 0L) return 2;
		else return 0;
	}
	
	/**
	 * Checks the status of the cell at the coordinates of <b>move</b>.
	 * @param move Move representing the coordinates to check at.
	 * @return 0: unoccupied, 1: white, 2: black
	 */
	public int getPosition(Move move) { return getPosition( move.x, move.y); }
	
	/**
	 * Sets the cell at coordinates <b>x</b> and <b>y</b> to <b>player</b>.
	 * 
	 * Performs no checks on the input for efficiency reasons!
	 * 
	 * @param x X coordinate to set.
	 * @param y Y coordinate to set.
	 * @param player 0: unoccupied, 1: white, 2: black
	 */
	public void setPosition(int x, int y, int player) {
		long val = 1L << coordToIndex(x,y);
		if (player == 1) {
			if ((black & val) != 0L)
				black ^= val;
			white |= val;
		}
		else {
			if ((white & val) != 0L)
				white ^= val;
			black |= val;
		}
	}
	
	/**
	 * Sets the cell at index <b>n</b> to <b>player</b>.
	 * 
	 * Performs no checks on the input for efficiency reasons!
	 * 
	 * @param n Index to set.
	 * @param player 0: unoccupied, 1: white, 2: black
	 */
	public void setPosition(int n, int player) {
		long val = 1L << n;
		if (player == 1) {
			black ^= val;
			white |= val;
		}
		else {
			white ^= val;
			black |= val;
		}
	}
	
	/**
	 * Sets the cell at coordinates represented by <b>move</b> to <b>player</b>.
	 * 
	 * Performs no checks on the input for efficiency reasons!
	 * 
	 * @param move Move representing the coordinates to set.
	 * @param player 0: unoccupied, 1: white, 2: black
	 */
	public void setPosition(Move move, int player) { setPosition( move.x, move.y, player); }
	
	/**
	 * Counts the number of tokens for <b>player</b>.
	 * @param player 1: white, 2: black
	 * @return The number of tokens <b>player</b> has on the board.
	 */
	public int countTokens(int player) {
		if (player == 1) return Long.bitCount(white);
		else return Long.bitCount(black);		
	}
	
	/**
	 * Returns the potential mobility and real mobility differences.
	 * @param player The player from whose viewpoint to calculate the differences. 1: white, 2: black.
	 * @return A 2-element array. Element 0: Potential mobility difference. Element 1: Real mobility difference.
	 */
	public int[] getMobilityDiffs(int player) {
		int opp = player == 1 ? 2 : 1;
		long myPotential = 0L;
		long oppPotential = 0L;
		int[] results = new int[2];

		for (int n = 0; n < 64; n++) {
			if (getPosition(n) == opp) {
				myPotential |= Constants.adjacency[n];
			}
			else if (getPosition(n) == player) {
				oppPotential |= Constants.adjacency[n];
			}
		}
		results[0] = Long.bitCount(myPotential) - Long.bitCount(oppPotential);
		results[1] = countMoves(player, myPotential) - countMoves(opp, oppPotential);
		return results;
	}
	
	private long getPotentialMoves(int player) {
		long potentialMoves = 0L;
		int opp = player == 1 ? 2 : 1;
		for (int n = 0; n < 64; n++) {
			if (getPosition(n) == opp) {
				potentialMoves |= Constants.adjacency[n];
			}
		}
		return potentialMoves & (~(white | black));
	}
	
	private long extractValidMoves(int player, long potentialMoves) {
		long myBoard = player == 1 ? white : black;
		long oppBoard = player == 1 ? black : white;
		long validMoves = 0L;
		/* move pieces to the right */
		int count = 1;
		long temp = ((potentialMoves << 1) & Constants.MASK_RIGHT) & oppBoard;
		while (temp != 0L) {
			temp = (temp << 1) & Constants.MASK_RIGHT;
			count++;
			validMoves |= (temp & myBoard) >>> count;
			temp &= oppBoard;
		}
		/* move pieces to the left */
		count = 1;
		temp = ((potentialMoves >>> 1) & Constants.MASK_LEFT) & oppBoard;
		while (temp != 0L) {
			temp = (temp >>> 1) & Constants.MASK_LEFT;
			count++;
			validMoves |= (temp & myBoard) << count;
			temp &= oppBoard;
		}
		/* move pieces up */
		count = 1;
		temp = (potentialMoves >>> 8) & oppBoard;
		while (temp != 0L) {
			temp >>>= 8;
			count++;
			validMoves |= (temp & myBoard) << (8 * count);
			temp &= oppBoard;
		}
		/* move pieces down */
		count = 1;
		temp = (potentialMoves << 8) & oppBoard;
		while (temp != 0L) {
			temp <<= 8;
			count++;
			validMoves |= (temp & myBoard) >>> (8 * count);
			temp &= oppBoard;
		}
		/* move pieces up and right */
		count = 1;
		temp = ((potentialMoves >>> 7) & Constants.MASK_RIGHT) & oppBoard;
		while (temp != 0L) {
			temp = (temp >>> 7) & Constants.MASK_RIGHT;
			count++;
			validMoves |= (temp & myBoard) << (7 * count);
			temp &= oppBoard;
		}
		/* move pieces up and left */
		count = 1;
		temp = ((potentialMoves >>> 9) & Constants.MASK_LEFT) & oppBoard;
		while (temp != 0L) {
			temp = (temp >>> 9) & Constants.MASK_LEFT;
			count++;
			validMoves |= (temp & myBoard) << (9 * count);
			temp &= oppBoard;
		}
		/* move pieces down and right */
		count = 1;
		temp = ((potentialMoves << 9) & Constants.MASK_RIGHT) & oppBoard;
		while (temp != 0L) {
			temp = (temp << 9) & Constants.MASK_RIGHT;
			count++;
			validMoves |= (temp & myBoard) >>> (9 * count);
			temp &= oppBoard;
		}
		/* move pieces down and left */
		count = 1;
		temp = ((potentialMoves << 7) & Constants.MASK_LEFT) & oppBoard;
		while (temp != 0L) {
			temp = (temp << 7) & Constants.MASK_LEFT;
			count++;
			validMoves |= (temp & myBoard) >>> (7 * count);
			temp &= oppBoard;
		}
		return validMoves;
	}
	
	private int countMoves(int player, long potentialMoves) {
		return Long.bitCount(extractValidMoves(player, potentialMoves));		
	}
	
	/**
	 * Tests if <b>player</b> has valid moves.
	 * @param player 1: white, 2: black
	 * @return True if <b>player</b> has at least one valid move.
	 */
	public boolean hasValidMove(int player) {
		return countMoves(player, getPotentialMoves(player)) > 0;
	}
	
	/**
	 * Gets the valid moves for <b>player</b>.
	 * @param player 1: white, 2: black
	 * @return An ArrayList containing the indices of the valid moves.
	 */
	public ArrayList<Integer> getValidMoves(int player) {
		ArrayList<Integer> moves = new ArrayList<Integer>();
		long validMoves = extractValidMoves(player, getPotentialMoves(player));
		for (int i = 0; i < 64; i++) {
			if ((validMoves & (1L << i)) != 0L) moves.add(i);
		}
		return moves;
	}
	
	/**
	 * Makes a move.
	 * 
	 * Does not check if any of the inputs is valid
	 * and does not check whether the move itself is valid.
	 * @param player 1: white, 2: black
	 * @param n Index at which to move.
	 * @return The number of tokens that were turned. I should probably fix this though.
	 */
	public int makeMove(int player, int n) {
		setPosition(n, player);
		int opp = player == 1 ? 2 : 1;
		int length, m;
		int[] turns;
		int totalturns = 1;
		setPosition(n, player);
		
		int[] neighbors = Constants.directionList[n];
		for (int i = 0; i < 8; i++) {
			m = neighbors[i];
			turns = new int[8];
			length = 0;
			while (m != -1 && getPosition(m) == opp) {
				turns[length] = m;
				length++;
				m = Constants.directionList[m][i];
			}
			if (length > 0 && m!= -1 && getPosition(m) == player) {
				totalturns += length;
				for (int j = 0; j < length; j++) {
					setPosition(turns[j], player);
				}
			}
		}
		return totalturns;
	}
	
	private int coordToIndex(int x, int y) {
		return (y * 8) + x;
	}
	
	/**
	 * @return A copy of the board.
	 */
	public BitBoard copyBoard() {
		return new BitBoard(white, black);
	}

	/**
	 * @return A long representing the white bitboard.
	 */
	public long getWhite() {
		return white;
	}

	/**
	 * @return A long representing the black bitboard.
	 */
	public long getBlack() {
		return black;
	}
	
	/**
	 * Tests if two BitBoards are equal.
	 * @param other The BitBoard to compare this to.
	 * @return True iff the Bitboards are equal.
	 */
	public boolean equals(BitBoard other) {
		if (this.white == other.white && this.black == other.black) return true;
		else return false;
	}
	
}
