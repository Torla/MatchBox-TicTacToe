import java.io.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.*;
import java.util.stream.Collectors;

class MatchBoxBrain implements Serializable {
	private final Semaphore lock=new Semaphore(1);
	private final HashMap<Integer, MatchBox> matchBoxes;
	private class Used implements Serializable{
		Used(MatchBox box, long threadId) {
			this.box = box;
			this.threadId = threadId;
		}
		final MatchBox box;
		final long threadId;

		MatchBox getBox() {
			return box;
		}

		long getThreadId() {
			return threadId;
		}
	}
	private final ArrayList<Used> lastsUsed;
	public int numMatch=1;
	final transient public static Rand random = new Rand();
	private transient boolean trainingMode = false;

	public  MatchBoxBrain(){
		matchBoxes = new HashMap<>();
		for(int state: Board.generateAllHash()){
			matchBoxes.put(state,new MatchBox(state));
		}
		lastsUsed = new ArrayList<>();
	}

	public Move move(Board b)throws MatchBox.Resign{

		if(!matchBoxes.containsKey(b.hashCode())) System.out.println(b);
		MatchBox m= matchBoxes.get(b.hashCode());
		try {
			lock.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		lastsUsed.add(new Used(m,Thread.currentThread().getId()));
		Move move = m.chooseMove();
		lock.release();
		return move;
	}

	public void  reward(char x) {
		try {
			lock.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		numMatch++;
	try{

		for (Used u : lastsUsed.stream()
				.filter(l -> l.getThreadId() == Thread.currentThread().getId())
				.collect(Collectors.toCollection(ArrayList::new))) { //todo error in concurrency
			u.getBox().rewards(x, trainingMode);
			lastsUsed.remove(u);
		}
	}catch (ConcurrentModificationException e) {
		e.printStackTrace();
	}
		lock.release();
	}

	public synchronized void saveToDisk(String path){
		try {
			FileOutputStream f = new FileOutputStream(path);
			ObjectOutputStream s=new ObjectOutputStream(f);
			s.writeObject(this);
			s.close();
			f.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

	static MatchBoxBrain load(String path){
		MatchBoxBrain brain=null;
		try {
			FileInputStream f = new FileInputStream(path);
			ObjectInputStream s = new ObjectInputStream(f);
			brain = (MatchBoxBrain) s.readObject();
			s.close();
			f.close();
		}
		catch (IOException | ClassNotFoundException e){
			e.printStackTrace();
		}
		return brain;
	}

	public void setTrainingMode(boolean trainingMode) {
		this.trainingMode = trainingMode;
	}

	public void eliminateIndecision(){
		for (MatchBox m:matchBoxes.values()) m.eliminateIndecision();
	}
	@Override
	public String toString() {
		return matchBoxes.toString()+'\n'+lastsUsed;
	}

}
