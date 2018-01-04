import java.util.Random;

public class Rand {
	public final Random rand = new Random();
	@SuppressWarnings("unused")
	public boolean fate(int x, int y){
		return (rand.nextInt()%y)<x;
	}
}
