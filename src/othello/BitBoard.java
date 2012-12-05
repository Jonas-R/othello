package othello;

import szte.mi.Move;
import java.util.ArrayList;

public class BitBoard {
	private long white;
	private long black;
	
	private final long MASK_RIGHT = -72340172838076674L;
	private final long MASK_LEFT = 9187201950435737471L;
	
	public static final long[] adjacency = {
		0L,
		5L,
		10L,
		20L,
		40L,
		80L,
		160L,
		0L,
		65537L,
		460039L,
		920078L,
		1840156L,
		3680312L,
		7360624L,
		14721248L,
		8388736L,
		16777472L,
		117769984L,
		235539968L,
		471079936L,
		942159872L,
		1884319744L,
		3768639488L,
		2147516416L,
		4295032832L,
		30149115904L,
		60298231808L,
		120596463616L,
		241192927232L,
		482385854464L,
		964771708928L,
		549764202496L,
		1099528404992L,
		7718173671424L,
		15436347342848L,
		30872694685696L,
		61745389371392L,
		123490778742784L,
		246981557485568L,
		140739635838976L,
		281479271677952L,
		1975852459884544L,
		3951704919769088L,
		7903409839538176L,
		15806819679076352L,
		31613639358152704L,
		63227278716305408L,
		36029346774777856L,
		72058693549555712L,
		505818229730443264L,
		1011636459460886528L,
		2023272918921773056L,
		4046545837843546112L,
		8093091675687092224L,
		-2260560722335367168L,
		-9223231299366420480L,
		0L,
		360287970189639680L,
		720575940379279360L,
		1441151880758558720L,
		2882303761517117440L,
		5764607523034234880L,
		-6917529027641081856L,
		0L,
		};
	
	public static final int[][] directionlist = {
		{ -1, -1, -1, 8, -1, -1, 1, 9 },
		{ -1, 0, -1, 9, 8, -1, 2, 10 },
		{ -1, 1, -1, 10, 9, -1, 3, 11 },
		{ -1, 2, -1, 11, 10, -1, 4, 12 },
		{ -1, 3, -1, 12, 11, -1, 5, 13 },
		{ -1, 4, -1, 13, 12, -1, 6, 14 },
		{ -1, 5, -1, 14, 13, -1, 7, 15 },
		{ -1, 6, -1, 15, 14, -1, -1, -1 },
		{ -1, -1, 0, 16, -1, 1, 9, 17 },
		{ 0, 8, 1, 17, 16, 2, 10, 18 },
		{ 1, 9, 2, 18, 17, 3, 11, 19 },
		{ 2, 10, 3, 19, 18, 4, 12, 20 },
		{ 3, 11, 4, 20, 19, 5, 13, 21 },
		{ 4, 12, 5, 21, 20, 6, 14, 22 },
		{ 5, 13, 6, 22, 21, 7, 15, 23 },
		{ 6, 14, 7, 23, 22, -1, -1, -1 },
		{ -1, -1, 8, 24, -1, 9, 17, 25 },
		{ 8, 16, 9, 25, 24, 10, 18, 26 },
		{ 9, 17, 10, 26, 25, 11, 19, 27 },
		{ 10, 18, 11, 27, 26, 12, 20, 28 },
		{ 11, 19, 12, 28, 27, 13, 21, 29 },
		{ 12, 20, 13, 29, 28, 14, 22, 30 },
		{ 13, 21, 14, 30, 29, 15, 23, 31 },
		{ 14, 22, 15, 31, 30, -1, -1, -1 },
		{ -1, -1, 16, 32, -1, 17, 25, 33 },
		{ 16, 24, 17, 33, 32, 18, 26, 34 },
		{ 17, 25, 18, 34, 33, 19, 27, 35 },
		{ 18, 26, 19, 35, 34, 20, 28, 36 },
		{ 19, 27, 20, 36, 35, 21, 29, 37 },
		{ 20, 28, 21, 37, 36, 22, 30, 38 },
		{ 21, 29, 22, 38, 37, 23, 31, 39 },
		{ 22, 30, 23, 39, 38, -1, -1, -1 },
		{ -1, -1, 24, 40, -1, 25, 33, 41 },
		{ 24, 32, 25, 41, 40, 26, 34, 42 },
		{ 25, 33, 26, 42, 41, 27, 35, 43 },
		{ 26, 34, 27, 43, 42, 28, 36, 44 },
		{ 27, 35, 28, 44, 43, 29, 37, 45 },
		{ 28, 36, 29, 45, 44, 30, 38, 46 },
		{ 29, 37, 30, 46, 45, 31, 39, 47 },
		{ 30, 38, 31, 47, 46, -1, -1, -1 },
		{ -1, -1, 32, 48, -1, 33, 41, 49 },
		{ 32, 40, 33, 49, 48, 34, 42, 50 },
		{ 33, 41, 34, 50, 49, 35, 43, 51 },
		{ 34, 42, 35, 51, 50, 36, 44, 52 },
		{ 35, 43, 36, 52, 51, 37, 45, 53 },
		{ 36, 44, 37, 53, 52, 38, 46, 54 },
		{ 37, 45, 38, 54, 53, 39, 47, 55 },
		{ 38, 46, 39, 55, 54, -1, -1, -1 },
		{ -1, -1, 40, 56, -1, 41, 49, 57 },
		{ 40, 48, 41, 57, 56, 42, 50, 58 },
		{ 41, 49, 42, 58, 57, 43, 51, 59 },
		{ 42, 50, 43, 59, 58, 44, 52, 60 },
		{ 43, 51, 44, 60, 59, 45, 53, 61 },
		{ 44, 52, 45, 61, 60, 46, 54, 62 },
		{ 45, 53, 46, 62, 61, 47, 55, 63 },
		{ 46, 54, 47, 63, 62, -1, -1, -1 },
		{ -1, -1, 48, -1, -1, 49, 57, -1 },
		{ 48, 56, 49, -1, -1, 50, 58, -1 },
		{ 49, 57, 50, -1, -1, 51, 59, -1 },
		{ 50, 58, 51, -1, -1, 52, 60, -1 },
		{ 51, 59, 52, -1, -1, 53, 61, -1 },
		{ 52, 60, 53, -1, -1, 54, 62, -1 },
		{ 53, 61, 54, -1, -1, 55, 63, -1 },
		{ 54, 62, 55, -1, -1, -1, -1, -1 },
	};
	
	private final int[][] directions = { {0,1}, {-1,1}, {-1,0}, {-1,-1}, {0,-1}, {1,-1}, {1,0}, {1,1} };
	
	public BitBoard() {
		white = black = 0;
		white |= 1L << ((3 * 8) + 3);
		white |= 1L << ((4 * 8) + 4);
		black |= 1L << ((3 * 8) + 4);
		black |= 1L << ((4 * 8) + 3);
	}
	
	public BitBoard(long white, long black) 
		{ this.white = white; this.black = black; }
	
	public int getPosition(int x, int y) {
		long val = 1L << coordToN(x,y);
		if ((white & val) != 0L) return 1;
		else if ((black & val) != 0L) return 2;
		else return 0;
	}
	
	public int getPosition(int n) {
		long val = 1L << n;
		if ((white & val) != 0L) return 1;
		else if ((black & val) != 0L) return 2;
		else return 0;
	}
	
	public int getPosition(Move move) { return getPosition( move.x, move.y); }
	
	public void setPosition(int x, int y, int player) {
		long val = 1L << coordToN(x,y);
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
	
	public void setPosition(int n, int player) {
		long val = 1L << n;
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
	
	public void setPosition(Move move, int player) { setPosition( move.x, move.y, player); }
	
	public int countTokens(int player) {
		if (player == 1) return Long.bitCount(white);
		else return Long.bitCount(black);		
	}
	
	public int countStableTokens(int player) {
		int[][] corners = { {0,0}, {0,7}, {7,0}, {7,7} };
		int count = 0;
		
		for (int[] corner : corners) {
			int x = corner[0]; int y = corner[1];
			if (this.getPosition(x, y) != player) break;
			
			count++;
			
			for (int[] direction : directions) {
				x += direction[0]; y += direction[1];
				while (x < 8 && y < 8 && x >= 0 && y >= 0) {
					if (this.getPosition(x, y) == player) count += 1;
					else break;
					x += direction[0]; y += direction[1];
				}
			}
		}
		
		return count;
	}
	
	public int[] getMobilityDiffs(int player) {
		int opp = player == 1 ? 2 : 1;
		long myPotential = 0L;
		long oppPotential = 0L;
		int[] results = new int[2];

		for (int n = 0; n < 64; n++) {
			if (getPosition(n) == opp) {
				myPotential |= adjacency[n];
			}
			else if (getPosition(n) == player) {
				oppPotential |= adjacency[n];
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
				potentialMoves |= adjacency[n];
			}
		}
		return potentialMoves & (~(white | black));
	}
	
	private int countMoves(int player, long potentialMoves) {
		long myBoard = player == 1 ? white : black;
		long oppBoard = player == 1 ? black : white;
		long validMoves = 0L;
		/* move pieces to the right */
		int count = 1;
		long temp = ((potentialMoves << 1) & MASK_RIGHT) & oppBoard;
		while (temp != 0L) {
			temp = (temp << 1) & MASK_RIGHT;
			count++;
			validMoves |= (temp & myBoard) >>> count;
			temp &= oppBoard;
		}
		/* move pieces to the left */
		count = 1;
		temp = ((potentialMoves >>> 1) & MASK_LEFT) & oppBoard;
		while (temp != 0L) {
			temp = (temp >>> 1) & MASK_LEFT;
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
		temp = ((potentialMoves >>> 7) & MASK_RIGHT) & oppBoard;
		while (temp != 0L) {
			temp = (temp >>> 7) & MASK_RIGHT;
			count++;
			validMoves |= (temp & myBoard) << (7 * count);
			temp &= oppBoard;
		}
		/* move pieces up and left */
		count = 1;
		temp = ((potentialMoves >>> 9) & MASK_LEFT) & oppBoard;
		while (temp != 0L) {
			temp = (temp >>> 9) & MASK_LEFT;
			count++;
			validMoves |= (temp & myBoard) << (9 * count);
			temp &= oppBoard;
		}
		/* move pieces down and right */
		count = 1;
		temp = ((potentialMoves << 9) & MASK_RIGHT) & oppBoard;
		while (temp != 0L) {
			temp = (temp << 9) & MASK_RIGHT;
			count++;
			validMoves |= (temp & myBoard) >>> (9 * count);
			temp &= oppBoard;
		}
		/* move pieces down and left */
		count = 1;
		temp = ((potentialMoves << 7) & MASK_LEFT) & oppBoard;
		while (temp != 0L) {
			temp = (temp << 7) & MASK_LEFT;
			count++;
			validMoves |= (temp & myBoard) >>> (7 * count);
			temp &= oppBoard;
		}
		return Long.bitCount(validMoves);		
	}
	
	public boolean hasValidMove(int player) {
		return !getValidMoves(player).isEmpty();
	}
	
	public ArrayList<Integer> getValidMoves(int player) {
		long myBoard = player == 1 ? white : black;
		long oppBoard = player == 1 ? black : white;
		long potentialMoves = getPotentialMoves(player);
		long validMoves = 0L;
		ArrayList<Integer> moves = new ArrayList<Integer>();
		/* move pieces to the right */
		int count = 1;
		long temp = ((potentialMoves << 1) & MASK_RIGHT) & oppBoard;
		while (temp != 0L) {
			temp = (temp << 1) & MASK_RIGHT;
			count++;
			validMoves |= (temp & myBoard) >>> count;
			temp &= oppBoard;
		}
		/* move pieces to the left */
		count = 1;
		temp = ((potentialMoves >>> 1) & MASK_LEFT) & oppBoard;
		while (temp != 0L) {
			temp = (temp >>> 1) & MASK_LEFT;
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
		temp = ((potentialMoves >>> 7) & MASK_RIGHT) & oppBoard;
		while (temp != 0L) {
			temp = (temp >>> 7) & MASK_RIGHT;
			count++;
			validMoves |= (temp & myBoard) << (7 * count);
			temp &= oppBoard;
		}
		/* move pieces up and left */
		count = 1;
		temp = ((potentialMoves >>> 9) & MASK_LEFT) & oppBoard;
		while (temp != 0L) {
			temp = (temp >>> 9) & MASK_LEFT;
			count++;
			validMoves |= (temp & myBoard) << (9 * count);
			temp &= oppBoard;
		}
		/* move pieces down and right */
		count = 1;
		temp = ((potentialMoves << 9) & MASK_RIGHT) & oppBoard;
		while (temp != 0L) {
			temp = (temp << 9) & MASK_RIGHT;
			count++;
			validMoves |= (temp & myBoard) >>> (9 * count);
			temp &= oppBoard;
		}
		/* move pieces down and left */
		count = 1;
		temp = ((potentialMoves << 7) & MASK_LEFT) & oppBoard;
		while (temp != 0L) {
			temp = (temp << 7) & MASK_LEFT;
			count++;
			validMoves |= (temp & myBoard) >>> (7 * count);
			temp &= oppBoard;
		}
		
		for (int i = 0; i < 64; i++) {
			if ((validMoves & (1L << i)) != 0L) moves.add(i);
		}
		return moves;
	}
	
	public boolean isValidMove(int player, int n) {
		int opp = (player == 1 ? 2 : 1);
		boolean turned;
		int m;
		
		int[] neighbors = directionlist[n];
		for (int i = 0; i < 8; i++) {
			m = neighbors[i];
			turned = false;
			while (m != -1 && getPosition(m) == opp) {
				turned = true;
				m = directionlist[m][i];
			}
			if (turned && m != -1 && getPosition(m) == player) return true;
		}
		return false;
	}
	
	public int makeMove(int player, int n) {
		setPosition(n, player);
		int opp = player == 1 ? 2 : 1;
		int length, m;
		int[] turns;
		int totalturns = 1;
		setPosition(n, player);
		
		int[] neighbors = directionlist[n];
		for (int i = 0; i < 8; i++) {
			m = neighbors[i];
			turns = new int[8];
			length = 0;
			while (m != -1 && getPosition(m) == opp) {
				turns[length] = m;
				length++;
				m = directionlist[m][i];
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
	
	private int coordToN(int x, int y) {
		return (y * 8) + x;
	}
	
	public BitBoard copyBoard() {
		return new BitBoard(white, black);
	}

	public long getWhite() {
		return white;
	}

	public long getBlack() {
		return black;
	}
	
	public boolean equals(BitBoard other) {
		if (this.white == other.white && this.black == other.black) return true;
		else return false;
	}
	
}
