import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;



public class MatchBoxBrain implements Serializable{
	private HashMap<Integer,MatchBox> matchBoxes;
	private ArrayList<MatchBox> lastsUsed;

	public  MatchBoxBrain(){
		matchBoxes = new HashMap<Integer, MatchBox>();
		for(int state:Board.generateAllHash()){
			matchBoxes.put(state,new MatchBox(state));
		}
		lastsUsed = new ArrayList<MatchBox>();
	}

	public Move move(Board b){
		MatchBox m= matchBoxes.get(b.hashCode());
		lastsUsed.add(m);
		return m.chooseMove();
	}

	public void reward(char x){
		for(MatchBox m:lastsUsed){
			m.rewards(x);
		}
		//lastsUsed.clear();
	}

	@Override
	public String toString() {
		return matchBoxes.toString()+'\n'+lastsUsed;
	}

}
