package ai;

import java.nio.ByteBuffer;
import java.util.Random;
import othello.BitBoard;

public class TranspositionTable {
	private int numBuckets;
	private Bucket[] buckets;
	private int bucketsize;
	int seed;
	
	public TranspositionTable(int n, int bucketSize, Random rnd) {
		numBuckets = n / bucketSize;
		buckets = new Bucket[numBuckets];
		for (int i = 0; i < numBuckets; i++) {
			buckets[i] = new Bucket(bucketSize);
		}
		seed = rnd.nextInt();
		this.bucketsize = bucketSize;
	}
	
	public double probeTable(BitBoard board, int depth, double alpha, double beta) {
		TableEntry entry = retrieve(board);
		if (entry != null) {
			if (entry.depth >= depth) {
				if (entry.flag == TableEntry.EXACT)
					return entry.score;
				if (entry.flag == TableEntry.ALPHA && entry.score <= alpha)
					return alpha;
				if (entry.flag == TableEntry.BETA && entry.score >= beta)
					return beta;
			}
		}
		return Double.NaN;
	}
	
	
	public TableEntry retrieve(BitBoard board) {
		return buckets[Math.abs(hash(board) % numBuckets)].retrieve(board);
	}
	
	public void insert(TableEntry entry) {
		buckets[Math.abs(hash(entry.board) % numBuckets)].insert(entry); 
	}
	
	private int hash(BitBoard board) {
		return hash_(ByteBuffer.allocate(16).putLong(board.getWhite()).putLong(board.getBlack()).array());
	}
	/*
	 * from http://dmy999.com/article/50/murmurhash-2-java-port
	 */
	private int hash_(byte[] data) {
        // 'm' and 'r' are mixing constants generated offline.
        // They're not really 'magic', they just happen to work well.
        int m = 0x5bd1e995;
        int r = 24;

        // Initialize the hash to a 'random' value
        int len = data.length;
        int h = seed ^ len;

        int i = 0;
        while (len  >= 4) {
            int k = data[i + 0] & 0xFF;
            k |= (data[i + 1] & 0xFF) << 8;
            k |= (data[i + 2] & 0xFF) << 16;
            k |= (data[i + 3] & 0xFF) << 24;

            k *= m;
            k ^= k >>> r;
            k *= m;

            h *= m;
            h ^= k;

            i += 4;
            len -= 4;
        }

        switch (len) {
        case 3: h ^= (data[i + 2] & 0xFF) << 16;
        case 2: h ^= (data[i + 1] & 0xFF) << 8;
        case 1: h ^= (data[i + 0] & 0xFF);
                h *= m;
        }

        h ^= h >>> 13;
        h *= m;
        h ^= h >>> 15;
        
        return h;
    }
	
	public void printStatistics() {
		int[] counts = new int[bucketsize + 1];
		for (int i = 0; i < bucketsize + 1; i++) counts[i] = 0;
		for (Bucket bucket : buckets) {
			counts[bucket.items()]++;
		}
		for (int i = 0; i < bucketsize + 1; i++) {
			System.out.println(i + ": " + counts[i]);
		}
	}
}
