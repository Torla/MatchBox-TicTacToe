import static java.lang.System.out;


class Input{
	// --Commented out by Inspection (04/01/2018 17:32):public Input input = new Input();
	// --Commented out by Inspection (04/01/2018 17:32):private Thread thread =null;
	// --Commented out by Inspection (04/01/2018 17:32):private static boolean exist=false;
	private static int x,y;
	private static boolean ready;



	public synchronized static Move inputMove(){
		while(!ready){
			try{Thread.yield();}catch (Exception e){out.println(e.getMessage());}
		}
		ready = false;
		return new Move(x, y);
	}
	public static void set(int x1,int y1){
		x=x1;
		y=y1;
		ready=true;
	}

// --Commented out by Inspection START (04/01/2018 17:32):
//	public static void test(){
//		System.out.println(inputMove());
//	}
// --Commented out by Inspection STOP (04/01/2018 17:32)
}


