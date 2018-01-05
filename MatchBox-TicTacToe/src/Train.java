

import java.util.Arrays;

class Train{

	private static MatchBoxBrain brain1;
	private static MatchBoxBrain brain2;

	private static final int numThreads = 4;
	private static final Thread[] threads = new Thread[numThreads];
	public static void main(String Argv[]){

		for(int i=0;i<numThreads;i++) threads[i]=new Thread(new singleRun(Argv));
		if(Argv.length>3 && Argv[3].equals("--new")){
			brain1=new MatchBoxBrain();
			brain2=new MatchBoxBrain();
			brain1.saveToDisk(Argv[0]);
			brain2.saveToDisk(Argv[1]);
		}
		else {
			brain1 = MatchBoxBrain.load(Argv[0]);
			brain2 = MatchBoxBrain.load(Argv[1]);
		}
		brain1.setTrainingMode(true);
		brain2.setTrainingMode(true);
		for (Thread t : threads) t.start();
			while(Arrays.stream(threads).filter(Thread::isAlive).count()!=0) try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		brain1.eliminateIndecision();
		brain2.eliminateIndecision();
		Train.brain1.saveToDisk(Argv[0]);
		Train.brain2.saveToDisk(Argv[1]);

	}
		static void match(){
		Board b = new Board();
		while (true){
			try {
				b.writeMove(brain1.move(b), 'x');
			}catch (MatchBox.Resign e){
				brain1.reward('l');
				brain2.reward('w');
			}
			b=new Board(b.normalized());
			if(b.gameState()!=0) break;
			try {
				b.writeMove(brain2.move(b), 'o');
			}catch (MatchBox.Resign e){
				brain1.reward('w');
				brain2.reward('l');
			}
			b=new Board(b.normalized());
			if(b.gameState()!=0) break;
		}
		switch (b.gameState()){
			case 1:
				brain1.reward('l');
				brain2.reward('w');
				break;
			case 2:
				brain1.reward('w');
				brain2.reward('l');
				break;
			case 3:
				brain1.reward('d');
				brain2.reward('d');
				break;
		}
	}
}

