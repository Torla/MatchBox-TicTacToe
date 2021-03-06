

import static java.lang.System.out;

class Play {
	private static MatchBoxBrain brain;
	private static final Window window = new Window();
	public static void main(String Argv[]){
		if(Argv.length>1 && Argv[1].equals("--new")){
			brain=new MatchBoxBrain();
			brain.saveToDisk(Argv[0]);
		}
		else brain = MatchBoxBrain.load(Argv[0]);
		//noinspection InfiniteLoopStatement
		while(true) {
			match();
			brain.saveToDisk(Argv[0]);
		}
	}

	private static void match(){
		Board b = new Board();
		while (true){
			try {
				b.writeMove(brain.move(b), 'x');
			}catch (MatchBox.Resign e){
				out.println("Resign, you won");
				window.message("Resign, you won ("+brain.numMatch + ")");
				brain.reward('l');
				return;
			}
			b=new Board(b.normalized());
			out.println(b);
			b.show(window);
			if(b.gameState()!=0) break;
			b.writeMove(Input.inputMove(),'o');
			b=new Board(b.normalized());
			out.println(b);
			if(b.gameState()!=0) break;
		}
		switch (b.gameState()){
			case 1:
				out.println("You won");
				window.message("You won ("+brain.numMatch + ")");
				brain.reward('l');
				break;
			case 2:
				out.println("You lose");
				window.message("You lose ("+brain.numMatch + ")");
				brain.reward('w');
				break;
			case 3:
				out.println("Draw");
				window.message("Draw ("+brain.numMatch + ")");
				brain.reward('d');
				break;
		}
	}
}
