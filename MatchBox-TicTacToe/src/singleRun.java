import static java.lang.System.out;

public  class singleRun implements Runnable{
	private  String[] args;
	private static int cicle=0;
	public singleRun(String[] a){args=a.clone();}
	private  void matchCicle(String[] Argv) {
		for (int i = 0; cicle<Integer.parseInt(Argv[2]); i++) {
			Train.match();
			try {

			}catch (java.util.ConcurrentModificationException e){out.println('!');}
			out.println(cicle++);
			;
		}
		;
	}

	@Override
	public void run() {
		matchCicle(args);
	}
}
