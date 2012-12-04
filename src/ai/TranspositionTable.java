package ai;

import java.nio.ByteBuffer;
import java.util.Random;
import othello.BitBoard;

public class TranspositionTable {
	private int numBuckets;
	private Bucket[] buckets;
	int seed;
	
	public TranspositionTable(int n, int bucketSize, Random rnd) {
		numBuckets = n / bucketSize;
		buckets = new Bucket[numBuckets];
		for (int i = 0; i < numBuckets; i++) {
			buckets[i] = new Bucket(bucketSize);
		}
		seed = rnd.nextInt();
	}
	
	public int retrieve(BitBoard board) {
		return buckets[Math.abs(hash(board)) % numBuckets].retrieve(board);
	}
	
	public void insert(TableEntry entry) {
		buckets[Math.abs(hash(entry.board)) % numBuckets].insert(entry); 
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
        
        System.out.println(h);
        return h;
    }
}
