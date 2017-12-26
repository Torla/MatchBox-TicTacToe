import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

public class Input {
	private static int x,y;
	private static boolean ready;
	public static Move inputMove(){
		while(!ready){
			try{Thread.sleep(10);}catch (Exception e){}
		};
		ready=false;
		return new Move(x,y);
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
