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
	
	public TableEntry retrieve(BitBoard board) {
		for (int i = 0; i < size; i++) {
			if (values[i] != null) {
				if (values[i].board.equals(board)) {
					referenced[i] = 1;
					return values[i];
				}
			}
		}
		return null;
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
	
	public int items() {
		int count = 0;
		for (int i = 0; i < size; i++) {
			if (values[i] != null) {
				count++;
			}
		}
		return count;
	}
}
