 import javax.swing.text.html.Option;
 import java.io.*;
 import java.util.ArrayList;
import java.util.HashMap;


 public class MatchBoxBrain implements Serializable{
	private HashMap<Integer,MatchBox> matchBoxes;
	private ArrayList<MatchBox> lastsUsed;
	public int numMatch=1;
	transient public static Rand random = new Rand();
	public  MatchBoxBrain(){
		matchBoxes = new HashMap<Integer, MatchBox>();
		for(int state:Board.generateAllHash()){
			matchBoxes.put(state,new MatchBox(state));
		}
		lastsUsed = new ArrayList<MatchBox>();
	}

	public Move move(Board b){
		if(!matchBoxes.containsKey(b.hashCode())) System.out.println(b);
		MatchBox m= matchBoxes.get(b.hashCode());
		lastsUsed.add(m);
		return m.chooseMove();
	}

	public void reward(char x){
		numMatch++;
		for(MatchBox m:lastsUsed){
			m.rewards(x);
		}
		lastsUsed.clear();
	}

	public void saveToDisk(String path){
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
