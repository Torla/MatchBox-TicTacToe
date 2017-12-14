import java.util.Random;

public class Rand {
	public Random rand = new Random();
	public boolean fate(int x,int y){
		return (rand.nextInt()%y)<x;
	}
}
