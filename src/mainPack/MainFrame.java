package mainPack;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;

public class MainFrame extends JFrame implements ActionListener{
	MainPanel panel;
	ProgramMenu menu;
	MainFrame(String title){
		setTitle(title);
	}
	
	void addPanel(MainPanel p) {
		panel = p;
		setContentPane(p);
		setMinimumSize(panel.getPreferredSize());
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Point origin, clicked, move;
				origin = new Point(panel.graph.getWidth()/2, panel.graph.getHeight()/2);
				clicked = new Point(e.getX() - (int)panel.graph.getLocation().getX(), e.getY() - (int)panel.graph.getLocation().getY());
				move = new Point(clicked.x - origin.x, clicked.y - origin.y);
				panel.graph.pointer.x += move.x;
				panel.graph.pointer.y += move.y;
				panel.graph.repaint();
			}
		});
		addComponentListener(new ComponentAdapter() {
		    public void componentResized(ComponentEvent componentEvent) {
		    	panel.changeSize();
		    }
		});
	}
	
	void addMenu(ProgramMenu m) {
		menu = m;
		menu.itemNew.addActionListener(this);
		menu.itemSave.addActionListener(this);
		menu.itemOpen.addActionListener(this);
		menu.itemNumber.addActionListener(this);
		JMenuItem itemAdd = new JMenuItem("add");
		itemAdd.addActionListener(this);
		JMenuItem itemDelete = new JMenuItem("delete");
		itemDelete.addActionListener(this);
		JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.add(itemAdd);
		popupMenu.add(itemDelete);
		panel.data.table.setComponentPopupMenu(popupMenu);
		setJMenuBar(m);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		DefaultTableModel model = (DefaultTableModel)panel.data.table.getModel();
		String path;
		int row, column;
		switch(e.getActionCommand()) {
		case "new" :
			panel.data.resetData();
			panel.graph.repaint();
			break;
		case "open" : 
			path = JOptionPane.showInputDialog("Please enter the file path");
			if(path != null) {
				panel.data.readData(new File(path));
			}
			break;
		case "save" :
			path = JOptionPane.showInputDialog("Please enter the file path");
			if(path != null) {
				panel.data.writeData(new File(path));
			}
			break;
		case "add" :
			row = panel.data.table.getSelectedRow();
			column = panel.data.table.getSelectedColumn();
			if(column != -1 && row != -1) {
				model.insertRow(row + 1, new String[] {"0","0"});
				panel.calculate();
				panel.graph.repaint();
			}
			break;
		case "delete" :
			row = panel.data.table.getSelectedRow();
			column = panel.data.table.getSelectedColumn();
			if(column != -1 && row != -1) {
				model.removeRow(row);
				panel.calculate();
				panel.graph.repaint();
			}
			break;
		case "number displayed" :
			panel.graph.numberDisplay = menu.itemNumber.isSelected();
			panel.graph.repaint();
			break;
		}
	}
	
	public static void main(String[]args) {
		MainFrame frame = new MainFrame("graph program");
		MainPanel panel = new MainPanel(new Dimension(500,500));
		panel.addGraph(new GraphPanel());
		panel.addDataPanel(new DataPanel());
		panel.addInfo(new InfoPanel());
		frame.addPanel(panel);
		frame.addMenu(new ProgramMenu());
		frame.setDefaultCloseOperation(MainFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}