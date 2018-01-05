


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;


class MatchBox implements Serializable {
	public class Resign extends Exception{
		Resign(){
			super("Computer resign");
		}
	}
	private static final int staringPebbles = 10;


	private class Option implements Serializable {
		final Move move;
		int pebbles;

		Option(Move move) {
			this.move = move;
			this.pebbles = MatchBox.staringPebbles;
		}

		Move getMove() {
			return move;
		}

		int getPebbles() {
			return pebbles;
		}

		void setPebbles(int pebbles) {
			this.pebbles = pebbles;
		}

		void incPebbles() {
			pebbles++;
		}

		void decPebbles() {
			pebbles--;
		}

		@Override
		public String toString() {
			return move.toString() + ':' + ((Integer) pebbles).toString();
		}
	}

	private final int state;
	private final ArrayList<Option> options;
	private final HashMap<Long,Option> lastUsed= new HashMap<>();

	public MatchBox(int state) {
		this.state = state;
		options = new ArrayList<>();
		for (Move move : new Board(state).possibleMoves()) {
			options.add(new Option(move));
		}
	}

	public Move chooseMove() throws Resign {
		int totalPebbles = 0;
		int r;
		totalPebbles = getTotalPebbles();
		if (totalPebbles == 0)
			throw new Resign();

		r = MatchBoxBrain.random.rand.nextInt(totalPebbles);
		for (Option o : options) {
			if (r < o.getPebbles()) {
				lastUsed.put(Thread.currentThread().getId(),o);
				return o.getMove();
			} else {
				r -= o.getPebbles();
			}
		}
		throw new RuntimeException();
	}

	private int getTotalPebbles() {
		int totalPebbles=0;
		for (Option o : options) totalPebbles += o.getPebbles();
		return totalPebbles;
	}



	public void rewards(char x, boolean trainingMode) {
		if(!lastUsed.containsKey(Thread.currentThread().getId())) { //todo Strange bug, ugly workaround
			System.out.println("!!!");
			return;
		}
		final Option o = lastUsed.get(Thread.currentThread().getId());
		switch (x) {
			case 'w':

				o.incPebbles();
				o.incPebbles();
				o.incPebbles();
				o.incPebbles();
				break;
			case 'd':
				o.incPebbles();
				break;
			case 'l':
				o.decPebbles();
				break;
		}
		if (o.getPebbles() > 100) for(Option i:options) i.setPebbles(i.getPebbles()/2);
		if (o.getPebbles() <= 0) o.setPebbles(0);
		if(trainingMode) for(Option i:options) if(i.getPebbles()==0) i.setPebbles(1);
		lastUsed.remove(Thread.currentThread().getId());
	}



	public void eliminateIndecision(){
		int max=0;
		for(Option o:options)
			if(o.getPebbles()>max) max=o.getPebbles();
		final int m=max;
		options.stream().filter(x->(x.getPebbles()<m/2)).forEach(x->x.setPebbles(0));
		options.stream().filter(x->(x.getPebbles()==1)).forEach(x->x.setPebbles(0));
	}

	@Override
	public String toString() {
		return ((Integer) state).toString() + "->" + options.toString() + '/' + lastUsed + '\n';
	}
}



