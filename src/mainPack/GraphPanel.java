package mainPack;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class GraphPanel extends JPanel{
	JButton zoom, outzoom, reset;
	Point pointer = new Point(0,0);
	double sight = 1;
	boolean numberDisplay = false;
	GraphPanel(){
		setBackground(Color.white);
		zoom = new JButton("zoom in");
		zoom.setMargin(new Insets(0, 0, 0, 0));
		zoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sight *= 2;
				pointer.x *=2;
				pointer.y *= 2;
				repaint();
			}
		});
		add(zoom);
		outzoom = new JButton("zoom out");
		outzoom.setMargin(new Insets(0, 0, 0, 0));
		outzoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sight /= 2;
				pointer.x /= 2;
				pointer.y /= 2;
				repaint();
			}
		});
		add(outzoom);
		reset = new JButton("reset");
		reset.setMargin(new Insets(0, 0, 0, 0));
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pointer = new Point(0,0);
				repaint();
			}
		});
		add(reset);
		setLayout(null);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		int width, height;
		width = getWidth();
		height = getHeight();
		g.clearRect(0, 0, width, height);
		drawGrid(g, width, height);
		drawAxis(g, width, height);
		drawRegressionLine(g, width, height);
		drawPoints(g, width, height);
		reset.updateUI();
		zoom.updateUI();
		outzoom.updateUI();
	}
	
	void drawGrid(Graphics g, int width, int height) {
		g.setColor(Color.gray);
		for(int i = 0;i < 10;i++) {
			g.drawLine(0, i*(int)(height/10.0), width, i*(int)(height/10.0));
			g.drawLine(i*(int)(width/10.0), 0, i*(int)(width/10.0), height);
		}
	}
	
	void drawAxis(Graphics g, int width, int height) {
		g.setColor(Color.blue);
		g.drawLine(width/2 - pointer.x, 0, width/2 - pointer.x, height);
		g.drawLine(0, height/2 - pointer.y, width, height/2 - pointer.y);
	}
	
	void drawRegressionLine(Graphics g, int width, int height) {
		MainPanel parent = (MainPanel)this.getParent();
		if(parent.c != null) {
			double[]line = parent.c.regression();
			double a = height - sight*line[0];
			double b = -line[1];
			g.setColor(Color.red);
			int x1 = width/2 + Math.min(-width + pointer.x, -height + pointer.y) - pointer.x;
			int y1 = -height/2 + (int)(a + Math.min(-width + pointer.x, -height + pointer.y)*b) - pointer.y;
			int x2 = width/2 + Math.max(width - pointer.x, height - pointer.y) - pointer.x;
			int y2 = -height/2 + (int)(a + Math.max(width - pointer.x, height - pointer.y)*b) - pointer.y;
			g.drawLine(x1, y1, x2, y2);
		}
	}
	
	void drawPoints(Graphics g, int width, int height) {
		MainPanel parent = (MainPanel)this.getParent();
		g.setColor(Color.black);
		g.setFont(new Font(Font.DIALOG_INPUT,Font.PLAIN,15));
		for(int row = 0;row < parent.data.table.getRowCount();row++) {
			double dataX = Double.valueOf((String)parent.data.table.getValueAt(row, 0));
			double dataY = Double.valueOf((String)parent.data.table.getValueAt(row, 1));
			double x = sight*dataX;
			double y = height - sight*dataY;
			int onGraphX = (int)(width/2 + x - 2 - pointer.x);
			int onGraphY = (int)(-height/2 + y - 2 - pointer.y);
			if(onGraphX > 0 && onGraphX < width && onGraphY > 0 && onGraphY < height) {
				g.fillOval(onGraphX, onGraphY, 4, 4);
				if(numberDisplay) {
					g.drawString("(" + dataX + "," + dataY + ")", (int)(width/2 + x - 2 - pointer.x), (int)(-height/2 + y - 2 - pointer.y));
				}
			}
		}
	}
	
	void init() {
		Dimension d = new Dimension(getWidth()/5,getHeight()/10);
		reset.setSize(d);
		zoom.setSize(d);
		outzoom.setSize(d);
		reset.setLocation((int)(getWidth()*0.4), (int)(getHeight()*0.9));
		zoom.setLocation((int)(getWidth()*0.6), (int)(getHeight()*0.9));
		outzoom.setLocation((int)(getWidth()*0.8), (int)(getHeight()*0.9));
	}
}