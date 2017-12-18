import static java.lang.System.out;






public class Test {
    public static void main(String[] args) {
        MatchBoxBrain brain=MatchBoxBrain.load(args[0]);
        out.println(brain);

    }
}
