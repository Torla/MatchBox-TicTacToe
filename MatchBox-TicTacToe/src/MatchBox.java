


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;


public class MatchBox implements Serializable {
	public class Resign extends Exception{
		public Resign(){
			super("Computer resign");
		}
	}
	private static final int staringPebbles = 10;


	private class Option implements Serializable {
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

		public void incPebbles() {
			pebbles++;
		}

		public void decPebbles() {
			pebbles--;
		}

		@Override
		public String toString() {
			return move.toString() + ':' + ((Integer) pebbles).toString();
		}
	}

	private int state;
	private ArrayList<Option> options;
	//private Option lastUsed = null; // TODO account for thread
	private HashMap<Long,Option> lastUsed= new HashMap<>();

	public MatchBox(int state) {
		this.state = state;
		options = new ArrayList<Option>();
		for (Move move : new Board(state).possibleMoves()) {
			options.add(new Option(move, staringPebbles));
		}
	}

	public Move chooseMove() throws Resign {
		int totalPebbles = 0;
		int r;
		totalPebbles = getTotalPebbles(totalPebbles);
		if (totalPebbles == 0) throw new Resign();
		r = MatchBoxBrain.random.rand.nextInt(totalPebbles);
		for (Option o : options) {
			if (r < o.getPebbles()) {
				lastUsed.put(Thread.currentThread().getId(),o);
				return o.getMove();
			} else {
				r -= o.getPebbles();
			}
		}
		return null;
	}

	private int getTotalPebbles(int totalPebbles) {
		for (Option o : options) totalPebbles += o.getPebbles();
		return totalPebbles;
	}

	;

	public void rewards(char x) {
		if(!lastUsed.containsKey(Thread.currentThread().getId())) {
			System.out.println("!!!");
			return;
		}
		final Option o = lastUsed.get(Thread.currentThread().getId());
		switch (x) {
			case 'w':

				o.incPebbles();
				o.incPebbles();
				break;
			case 'd':
				o.incPebbles();
				;
				break;
			case 'l':
				o.decPebbles();
				break;
		}
		if (o.getPebbles() < 0) o.setPebbles(0);
		lastUsed.remove(Thread.currentThread().getId());
	}

	;

	@Override
	public String toString() {
		return ((Integer) state).toString() + "->" + options.toString() + '/' + lastUsed + '\n';
	}
}



