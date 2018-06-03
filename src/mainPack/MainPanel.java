package mainPack;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class MainPanel extends JPanel{
	GraphPanel graph;
	DataPanel data;
	InfoPanel info;
	Calculator c;
	MainPanel(Dimension d){
		setPreferredSize(d);
		setBackground(Color.gray);
		setLayout(null);
	}
	
	void changeSize() {
		int width = 0, height = 0;
		width = getWidth();
		height = getHeight();
		graph.setSize(new Dimension((int)(width*0.8),(int)(height*0.8)));
		graph.setLocation((int)(width*0.2),0);
		graph.init();
		data.setSize(new Dimension((int)(width*0.2),(int)(height*0.8)));
		data.sp.setPreferredSize(data.getSize());
		data.setLocation(0,0);
		info.setSize(new Dimension((int)(width),(int)(height*0.2)));
		info.setLocation(0,(int)(height*0.8));
		info.init();
		graph.repaint();
	}
	
	void addGraph(GraphPanel p){
		int coordx, coordy = 0, width, height;
		width = (int)(getPreferredSize().getWidth()*0.8);
		height = (int)(getPreferredSize().getHeight()*0.8);
		coordx = (int)(getPreferredSize().getWidth() - width);
		p.setSize(new Dimension(width,height));
		p.setLocation(coordx,coordy);
		graph = p;
		add(p);
	}
	
	void addDataPanel(DataPanel p) {
		int coordx = 0, coordy = 0, width, height;
		width = (int)(getPreferredSize().getWidth()*0.2);
		height = (int)(getPreferredSize().getHeight());
		p.setSize(new Dimension(width,height));
		p.setLocation(coordx,coordy);
		data = p;
		add(data);
		p.init();
	}
	
	void addInfo(InfoPanel p) {
		int coordx = 0, coordy = 0, width, height;
		coordx = (int)(getPreferredSize().getWidth()*0.2);
		coordy =  (int)(getPreferredSize().getHeight()*0.8);
		width = (int)(getPreferredSize().getWidth()*0.8);
		height = (int)(getPreferredSize().getHeight()*0.2);
		p.setSize(new Dimension(width,height));
		p.setLocation(coordx,coordy);
		info = p;
		add(info);
		p.init();
	}
	
	void calculate() {
		c.update();
		info.setInfo(c.eX, c.eY, c.varX, c.varY, c.correlation(), c.covariance());
	}
}