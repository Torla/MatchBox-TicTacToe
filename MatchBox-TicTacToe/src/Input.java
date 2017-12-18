import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

public class Input {
	public static Move inputMove(){
		int x,y;
		Scanner s = new Scanner(System.in);
		x = s.nextInt();
		y = s.nextInt();
		//s.close();
		return new Move(x,y);
	}

	public static void test(){
		System.out.println(inputMove());
	}
}
