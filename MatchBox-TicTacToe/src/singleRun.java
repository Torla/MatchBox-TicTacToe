import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.out;

class singleRun implements Runnable{
	private final String[] args;
	private static AtomicInteger cicle=new AtomicInteger(0);
	public singleRun(String[] a){args=a.clone();}
	private  void matchCicle(String[] Argv) {
		for (int i = 0; cicle.get()<Integer.parseInt(Argv[2]); i++) {
			try {
				Train.match();
			}
			catch (RuntimeException e){
				e.printStackTrace();
			}
			out.println(cicle.getAndIncrement());
		}
	}

	@Override
	public void run() {
		matchCicle(args);
	}
}
