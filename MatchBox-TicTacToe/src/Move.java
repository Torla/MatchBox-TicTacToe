import java.io.Serializable;

public class Move implements Serializable {

	private final int i;
	private final int j;

	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}

	public Move(int i, int j) {
		this.i = i;
		this.j = j;
	}

	public boolean resign(){
		return this.i==-1;
	}

	@Override
	public String toString() {
		return "(" + i + ';' + j + ')';
	}
}
