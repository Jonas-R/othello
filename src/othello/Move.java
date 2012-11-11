package othello;
public class Move{
	public final int x;
	public final int y;
	
	public Move(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if ( !(obj instanceof Move) ) return false;
		Move other = (Move) obj;
		return this.x == other.x && this.y == other.y;
	}
}
