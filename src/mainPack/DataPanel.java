package mainPack;

import java.awt.FlowLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class DataPanel extends JPanel{
	JTable table;
	JScrollPane sp;
	boolean isReading = false;
	DataPanel(){
		setLayout(new FlowLayout());
	}
	
	void init() {
		sp = new JScrollPane();
		table = new JTable();
		sp.setViewportView(table);
		sp.setSize(getSize());
		add(sp);
		String[]cols = new String[2];
		String[][]rows = new String[0][2];
		cols[0] = "X";
		cols[1] = "Y";
		DefaultTableModel model = new DefaultTableModel(rows,cols);
		table.setModel(model);
	    MainPanel parent = ((MainPanel)this.getParent());
	    parent.c = new Calculator(table);
		model.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				if(e.getFirstRow() != -1 && e.getColumn() != -1) {
					try {
						Double.parseDouble((String)model.getValueAt(e.getFirstRow(), e.getColumn()));
					}catch(NumberFormatException e2) {
						model.setValueAt(0.0, e.getFirstRow(), e.getColumn());
					}
				}
				if(!isReading) {
					parent.calculate();
					parent.graph.repaint();
				}
			}			
		});
	}
	void resetData() {
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		model.getDataVector().removeAllElements();
		model.addRow(new String[] {"0","0"});
		MainPanel parent = (MainPanel)this.getParent();
		parent.calculate();
	}
	
	void readData(File file) {
		isReading = true;
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		Scanner inp;
		double x = 0, y = 0;
		try {
			inp = new Scanner(file);
			while(inp.hasNextDouble()) {
				x = inp.nextDouble();
				if(inp.hasNextDouble()) {
					y = inp.nextDouble();
				}else {
					y = 0;
				}
				model.addRow(new String[] {"" + x,"" + y});
			}
			inp.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File not found!", "warning", JOptionPane.WARNING_MESSAGE);
		}
		MainPanel parent = (MainPanel)this.getParent();
		parent.calculate();
		parent.graph.repaint();
		isReading = false;
	}
	
	void writeData(File file) {
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		try {
			file.createNewFile();
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file));
			for(int i = 0;i < model.getRowCount();i++) {
				String data = (String)model.getValueAt(i, 0) + " " + (String)model.getValueAt(i, 1);
				out.write(data + System.lineSeparator());
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		MainPanel parent = (MainPanel)this.getParent();
		parent.calculate();
	}
	
	void addRow(String x, String y) {
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		model.addRow(new String[] {x,y});
	}
}