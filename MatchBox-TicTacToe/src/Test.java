import static java.lang.System.out;






public class Test {
    public static Rand random = new Rand();
    public static void main(String[] args) {
        Board b = new Board();
        out.println(b);
        MatchBox m = new MatchBox(b.hashCode());
        out.println(m.chooseMove());
        m.rewards('d');
        out.println(m);
    }
}
