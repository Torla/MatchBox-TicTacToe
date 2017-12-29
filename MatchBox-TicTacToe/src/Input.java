import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;


public class Input{
	public Input input = new Input();
	private Thread thread =null;
	private boolean exist=false;
	private static int x,y;
	private static boolean ready;



	public synchronized static Move inputMove(){
		while(!ready){
			try{Thread.yield();}catch (Exception e){out.println(e.getMessage());}
		};
		ready = false;
		return new Move(x, y);
	}
	public static void set(int x1,int y1){
		x=x1;
		y=y1;
		ready=true;
	}

	public static void test(){
		System.out.println(inputMove());
	}
}


