import static java.lang.System.out;






public class Test {
    public static void main(String[] args) {
        Window window=new Window();
        Board b = new Board();
        b.writeMove(new Move(0,0),'x')
            .writeMove(new Move(1,1),'o');
        b.show(window);

    }
}
