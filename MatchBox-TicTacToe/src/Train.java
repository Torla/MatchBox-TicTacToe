import sun.awt.SunHints;

import static java.lang.System.out;

public class Train {
	private static MatchBoxBrain brain;
	public static void main(String Argv[]){
		if(Argv.length>2 && Argv[2].equals("--new")){
			brain=new MatchBoxBrain();
			brain.saveToDisk(Argv[0]);
		}
		else brain = MatchBoxBrain.load(Argv[0]);
		for(int i=0;i< Integer.parseInt(Argv[1]);i++ ) {
			match();
			brain.saveToDisk(Argv[0]);
			out.println(i);
		}
		return;
	}
	private static void match(){
		Board b = new Board();
		while (true){

			b.writeMove(brain.move(b),'x');
			b=new Board(b.normalized());
			if(b.gameState()!=0) break;
			b.writeMove(brain.move(b),'o');
			b=new Board(b.normalized());
			if(b.gameState()!=0) break;
		}
		switch (b.gameState()){
			case 1:
				brain.reward('l');
				break;
			case 2:
				brain.reward('w');
				break;
			case 3:
				brain.reward('d');
				break;
		}
		return;
	}
}
