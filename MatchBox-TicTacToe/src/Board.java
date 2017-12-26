import javafx.print.Collation;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Board {
    private int m[][] = new int[3][3];

	public Board(int[][] m) {
		this.m = new int[3][3];
		for(int i=0;i<m.length;i++){
			for(int j=0;j<m[0].length;j++){
				this.m[i][j]=m[i][j];
			}
		}

	}

	public Board() {
        for(int i=0;i<m.length;i++){
            for(int j=0;j<m[0].length;j++){
                m[i][j]=0;
            }
        }
    }

    public void copy(Board b){
	    this.m = new int[3][3];
	    for(int i=0;i<m.length;i++){
		    for(int j=0;j<m[0].length;j++){
			    this.m[i][j]=b.m[i][j];
		    }
	    }
    }

    public Board (int hash){
	    this.m = new int[3][3];
	    int num=hash;
	    for(int i=0;i<m.length;i++){
		    for(int j=0;j<m[0].length;j++){
			    this.m[i][j]=hash%3;
			    hash/=3;
		    }
	    }
    }
    public Board (Board b){
		this.copy(b);
    }

    @Override
    public String toString() {
        StringBuilder s= new StringBuilder("\n");
        for(int i=0;i<m.length;i++){
            for(int  j=0;j<m[0].length;j++){
                switch (m[i][j]){
                    case 0:
                        s.append(" |");
                        break;
                    case 1:
                        s.append("o|");
                        break;
                    case 2:
                        s.append("x|");
                        break;
                }
            }
            s.delete(s.length()-1,s.length());
            s.append("\n_____\n");
        }s.delete(s.length()-"\n_____\n".length(),s.length());
        return s.toString();
    }


    private Board writeMove(int i, int j, char player){
        switch (player){
            case ' ':
                m[i][j]=0;
                break;
            case 'o':
                m[i][j]=1;
                break;
            case 'x':
                m[i][j]=2;
                break;
        }
        return this;
    }
    public Board writeMove(Move move,char player){
    	return writeMove(move.getI(),move.getJ(),player);
    }

	private void rotate(int times) {
	    int temp;
	    if(times==0) return;
	    final int len = m.length;
	    for (int s = 0; s < len / 2; s++)
		    for (int i = 0; i < len - 2 * s - 1; i++) {
			    temp = m[s][s + i];
			    m[s][s + i] = m[len - s - i - 1][s];
			    m[len - s - i - 1][s] = m[len - s - 1][len - s - i - 1];
			    m[len - s - 1][len - s - i - 1] = m[s + i][len - s - 1];
			    m[s + i][len - s - 1] = temp;
		    }
		this.rotate(times-1);
    }
    private void rotate(){
    	rotate(1);
    }
	private void flip(){
    	int temp[]=m[0].clone();
    	m[0]=m[2].clone();
    	m[2]=temp.clone();
    }

    private int firstMetric(){
		int count=0;
		int val=0;
	    for(int i=0;i<m.length;i++){
		    for(int j=0;j<m[0].length;j++){
			    val+=m[i][j]*count++;
		    }
	    }
	    return val;
    }
    private int secondMetric(){
		int count=0;
		int val=1;
	    for(int i=0;i<m.length;i++){
		    for(int j=0;j<m[0].length;j++){
		    	count++;
		    	if(m[i][j]==0) continue;
			    val*=m[i][j]*count;
		    }
	    }
	    return val;
    }
    private boolean isLess(Board b){
    	if(b.firstMetric()==this.firstMetric()) return this.secondMetric() < b.secondMetric();
    	else return this.firstMetric() < b.firstMetric();
    }
    public Board normalized(){
    	Board ret = new Board(this.m);
    	Board temp = new Board(this.m);
	    for(int i=0;i<3;i++){
		    temp.rotate();
		    if(temp.isLess(ret)) ret.copy(temp);
	    }

	    temp.flip();
	    if(temp.isLess(ret)) ret = new Board(temp.m);

	    for(int i=0;i<3;i++){
		    temp.rotate();
		    if(temp.isLess(ret)) ret = new Board(temp.m);
	    }

	    return ret;
    }


	@Override
	public int hashCode() {
    	int hash=0;
    	int place=1;
    	Board b = (new Board(this.m).normalized());
		for(int i=0;i<m.length;i++){
			for(int j=0;j<m[0].length;j++){
				hash+=b.m[i][j]*place;
				place*=3;
			}
		}
		return hash;
	}

	@Override
	public boolean equals(Object b) {
    	if(this.getClass()!=b.getClass()) return false;
		return this.hashCode()==b.hashCode();
	}

	public int gameState(){
		int count,count2;
    	ArrayList<Integer> l= new ArrayList<>();
    	int[][] magicSquare={{8,1,6},{3,5,7},{4,9,2}};
    	if(isDraw()) return 3;
		for(int i=0;i<3;i++){
			count=0;
			count2=0;
			for(int j=0;j<3;j++){
				count+=m[i][j]*magicSquare[i][j];
				count2+=m[j][i]*magicSquare[j][i];
			}
			l.add(count);
			l.add(count2);
		}
		l.add((m[0][0] * magicSquare[0][0]) + (m[1][1] * magicSquare[1][1]) + (m[2][2] * magicSquare[2][2]));
		l.add((m[2][0] * magicSquare[2][0]) + (m[1][1] * magicSquare[1][1]) + (m[0][2] * magicSquare[0][2]));
		if(l.contains(15)) return 1;
		if(l.contains(30)) return 2;
		else return 0;

	}
	public boolean isDraw(){
		for(int[] row:m){
			for(int cell:row){
				if(cell==0) return false;
			}
		}
		return true;
	}

	private static void generateAllR(int i, int j,Board b,HashSet<Board> retSet){
		Board b2;
		if(j>2){
			j=0;
			i++;
		}
		if (i > 2) return;
		char[] ch = {' ','o','x'};
		for(char c:ch) {
			b2 = new Board(b.m);
			b2.writeMove(i, j, c);
			if(b2.gameState()!=0 || b2.isDraw()) continue;
			retSet.add(b2);
			generateAllR(i, j + 1, b2, retSet);
		}

	}
	public static HashSet<Board> generateAll(){
		HashSet<Board> retSet=new HashSet<>();
		Board b=new Board();
		generateAllR(0,0,b,retSet);
		return retSet;
	}
	public static HashSet<Integer> generateAllHash() {
		return generateAll().stream().map((x)->x.hashCode()).collect(Collectors.toCollection(HashSet<Integer>::new));

	}
	public ArrayList<Move> possibleMoves(){
		ArrayList<Move> ret= new ArrayList<>();
		ArrayList<Integer> states= new ArrayList<>();
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				if(m[i][j]==0){
					Move m= new Move(i,j);
					Board b2 = new Board(this.m).writeMove(m,'x');
					if(!states.contains(b2.hashCode())){
						states.add(b2.hashCode());
						ret.add(m);
					}
				}
			}
		}

		return ret;
	}

	public void show(Window w){
		w.show(this.m);
	}
	static void test(){
		Board b = new Board()
				.writeMove(0,0,'x')
				.writeMove(1,1,'x')
				.writeMove(0,2,'x')
				.writeMove(0,1,'x');
		out.println(generateAllHash().size());
	}
}
