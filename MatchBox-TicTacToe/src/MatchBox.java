
import javax.swing.text.html.Option;
import java.util.ArrayList;


public class MatchBox {

	private static final int staringPebbles= 10;


	private class Option {
		Move move;
		int pebbles;

		public Option(Move move, int pebbles) {
			this.move = move;
			this.pebbles = pebbles;
		}

		public Move getMove() {
			return move;
		}

		public void setMove(Move move) {
			this.move = move;
		}

		public int getPebbles() {
			return pebbles;
		}

		public void setPebbles(int pebbles) {
			this.pebbles = pebbles;
		}
		public void incPebbles(){
			pebbles++;
		}
		public void decPebbles(){
			pebbles--;
		}

		@Override
		public String toString() {
			return move.toString()+':'+ ((Integer) pebbles).toString();
		}
	}

	private int state;
	private ArrayList<Option> options;
	private Option lastUsed = null;

	public MatchBox(int state){
		this.state=state;
		options = new ArrayList<Option>();
		for (Move move:new Board(state).possibleMoves()){
			options.add(new Option(move,staringPebbles));
		}
	}

	public Move chooseMove(){
		int totalPebbles=0;
		int r;
		for(Option o:options) totalPebbles+=o.getPebbles();
		r=Test.random.rand.nextInt()%totalPebbles;
		for(Option o:options){
			if(r<=o.getPebbles()){
				lastUsed=o;
				return o.getMove();
			}
			else{
				r-=o.getPebbles();
			}
		}
		return null;
	};

	public void rewards(char x){
		switch (x){
			case 'w':
				lastUsed.incPebbles();
				lastUsed.incPebbles();
				break;
			case 'd':
				lastUsed.incPebbles();;
				break;
			case 'l':
				lastUsed.decPebbles();
				break;
		}
	};

	@Override
	public String toString() {
		return ((Integer) state).toString()+"->"+options.toString()+'/'+lastUsed.getMove()+'\n';
	}

}


