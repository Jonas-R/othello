package ai;

import othello.BitBoard;

public class Bucket {
	private TableEntry[] values;
	private byte[] referenced;
	private int size;
	private int pointer;
	
	public Bucket(int n) {
		values =  new TableEntry[n];
		referenced = new byte[n];
		size = n;
		pointer = 0;
	}
	
	public int retrieve(BitBoard board) {
		for (int i = 0; i < size; i++) {
			if (values[i] != null) {
				if (values[i].board.getWhite() == board.getWhite() && values[i].board.getBlack() == board.getBlack()) {
					referenced[i] = 1;
					return values[i].bestMove;
				}
			}
		}
		return -1;
	}
	
	public void insert(TableEntry elem) {
		if (values[pointer] == null || referenced[pointer] != 1) {
			values[pointer] = elem;
			referenced[pointer] = 0;
			pointer = (pointer + 1) % size;
			return;
		}
		referenced[pointer] = 0;
		pointer = (pointer + 1) % size;
		insert(elem);
	}
}
