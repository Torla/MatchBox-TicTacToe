import java.io.Serializable;

public class Move implements Serializable {

	private int i;
	private int j;

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
	public Move(char x){
		this.i=-1;
	}
	public boolean resign(){
		return this.i==-1;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append('(').append(i).append(';').append(j).append(')');
		return s.toString();
	}
}
