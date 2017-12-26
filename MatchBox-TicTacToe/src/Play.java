import static java.lang.System.out;

public class Play {
	private static MatchBoxBrain brain;
	private static Window window = new Window();
	public static void main(String Argv[]){
		if(Argv.length>1 && Argv[1].equals("--new")){
			brain=new MatchBoxBrain();
			brain.saveToDisk(Argv[0]);
		}
		else brain = MatchBoxBrain.load(Argv[0]);
		while(true) {
			match();
			brain.saveToDisk(Argv[0]);
		}
	}

	private static void match(){
		Board b = new Board();
		while (true){

			b.writeMove(brain.move(b),'x');
			b=new Board(b.normalized());
			out.println(b);
			b.show(window);
			if(b.gameState()!=0) break;
			b.writeMove(Input.inputMove(),'o');
			b=new Board(b.normalized());
			out.println(b);
			b.show(window);
			if(b.gameState()!=0) break;
		}
		switch (b.gameState()){
			case 1:
				out.println("You won");
				brain.reward('l');
				break;
			case 2:
				out.println("You lose");
				brain.reward('w');
				break;
			case 3:
				out.println("Draw");
				brain.reward('d');
				break;
		}
		return;
	}
}
