package mainPack;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoPanel extends JPanel{
	JLabel eX, eY, varX, varY, cov, cor;
	InfoPanel(){
		setBackground(Color.gray);
		eX = new JLabel();
		add(eX);
		eY = new JLabel();
		add(eY);
		varX = new JLabel();
		add(varX);
		varY = new JLabel();
		add(varY);
		cov = new JLabel();
		add(cov);
		cor = new JLabel();
		add(cor);
		setLayout(null);
	}
	void init() {
		int width = getWidth();
		int height = getHeight();
		Dimension d = new Dimension(120,30);
		eX.setSize(d);
		eX.setLocation(width/6,height / 5);
		
		eY.setSize(d);
		eY.setLocation(width/6,3*height / 5);
		
		varX.setSize(d);
		varX.setLocation(5*width/12,height / 5);
		
		varY.setSize(d);
		varY.setLocation(5*width/12,3*height / 5);
	
		cov.setSize(d);
		cov.setLocation(8*width/12,height / 5);

		cor.setSize(d);
		cor.setLocation(8*width/12,3*height / 5);
		add(cor);
	}
	
	void setInfo(double ex, double ey, double varx, double vary, double corxy, double covxy) {
		eX.setText("E(X) =" + " " + String.format("%.2f", ex));
		eY.setText("E(Y) =" + " " + String.format("%.2f", ey));
		varX.setText("Var(X) =" + " " + String.format("%.2f", varx));
		varY.setText("Var(Y) =" + " " + String.format("%.2f", vary));
		cov.setText("Cov(X,Y) =" + " " + String.format("%.3f", covxy));
		cor.setText("Cor(X,Y) =" + " " + String.format("%.3f", corxy));
	}
}