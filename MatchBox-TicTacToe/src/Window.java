import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;

class Window extends Frame implements WindowListener,ActionListener{
	private final JPanel jPanel;
	private static final int dim = 500;
	private final Component[][] screen = new Component[3][3];
	private final Font f = new Font("ciao",Font.BOLD,dim/3);
	public Window() throws HeadlessException {
		super("prova");
		jPanel=new JPanel(new FlowLayout());
		add(jPanel);
		addWindowListener(this);
		setSize(dim, dim);
		setVisible(true);
	}
	private void setOnScreen(int i,int j,char c){
		if(c!=' ')screen[i][j] =new Label(String.valueOf(c));
		else{
			Button b = new Button(" ");
			b.addActionListener(this);
			screen[i][j]=b;
		}
		screen[i][j].setPreferredSize(new Dimension(dim/3-dim/20,dim/3-dim/20));
		screen[i][j].setFont(f);
		jPanel.add(screen[i][j]);
	}
	private void clear(){
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				if(screen[i][j]==null) continue;
				jPanel.remove(screen[i][j]);
			}
		}
	}
	void show(int x[][]){
		clear();
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				switch (x[i][j]){
					case 0:
						setOnScreen(i,j,' ');
						break;
					case 1:
						setOnScreen(i,j,'o');
						break;
					case 2:
						setOnScreen(i,j,'x');
						break;
				}
			}
		}
		setSize(dim+1, dim);
		setSize(dim, dim);
	}

	void message(String s){
		JOptionPane.showMessageDialog(null,s," ",JOptionPane.PLAIN_MESSAGE);
	}

	@Override
	public void actionPerformed(ActionEvent e){
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				if(screen[i][j]==e.getSource()){
					Input.set(i,j);
					break;
				}
			}
		}
	}

	public void windowClosing(WindowEvent e) {
		dispose();
		System.exit(0);
	}
	public void windowOpened(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
}
