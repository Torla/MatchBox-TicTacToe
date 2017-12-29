import java.io.*;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MatchBoxBrain implements Serializable {
	private Semaphore lock=new Semaphore(1);
	private HashMap<Integer, MatchBox> matchBoxes;
	private class Used implements Serializable{
		public Used(MatchBox box, long threadId) {
			this.box = box;
			this.threadId = threadId;
		}
		MatchBox box;
		long threadId;

		public MatchBox getBox() {
			return box;
		}

		public long getThreadId() {
			return threadId;
		}
	}
	private ArrayList<Used> lastsUsed;
	public int numMatch=1;
	transient public static Rand random = new Rand();

	public  MatchBoxBrain(){
		matchBoxes = new HashMap<Integer, MatchBox>();
		for(int state: Board.generateAllHash()){
			matchBoxes.put(state,new MatchBox(state));
		}
		lastsUsed = new ArrayList<Used>();
	}

	public Move move(Board b)throws MatchBox.Resign{
		if(!matchBoxes.containsKey(b.hashCode())) System.out.println(b);
		MatchBox m= matchBoxes.get(b.hashCode());
		lastsUsed.add(new Used(m,Thread.currentThread().getId()));
		return m.chooseMove();
	}

	public void  reward(char x){
		while(lock.tryAcquire());
		numMatch++;
		for (Used u : lastsUsed.stream()
				.filter(l -> l.getThreadId() == Thread.currentThread().getId())
				.collect(Collectors.toCollection(ArrayList<Used>::new))) {
			u.getBox().rewards(x);
			lastsUsed.remove(u);
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

	@Override
	public String toString() {
		return matchBoxes.toString()+'\n'+lastsUsed;
	}

}
