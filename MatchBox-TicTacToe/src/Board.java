import static java.lang.System.out;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;
import java.lang.Math.*;

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


    public void writeMove(int i, int j, char player){
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
    private Board normalized(){
    	Board ret = new Board(this.m);
    	Board temp = new Board(this.m);
	    for(int i=0;i<4;i++){
		    temp.rotate();
		    if(temp.isLess(ret)) ret.copy(temp);
	    }

	    temp.flip();
	    if(temp.isLess(ret)) ret = new Board(temp.m);

	    for(int i=0;i<4;i++){
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

	public static void test(){
		Board b = new Board();
		b.writeMove(0,0,'x');
		b.writeMove(0,2,'o');
		b.writeMove(1,1,'x');
		b.writeMove(0,1,'x');
		Board c = new Board(b.hashCode());
		c.flip();
		c.rotate();
		out.println(c);
		List<Board> l = new ArrayList<>();
		l.add(b);
		out.println(l.contains(c));


	}
}
