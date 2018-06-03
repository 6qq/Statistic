package mainPack;

import javax.swing.JTable;

public class Calculator{
	static final int X = 0;
	static final int Y = 1;
	double eX;
	double eY;
	double varX;
	double varY;
	JTable source;
	double[][]table;
	Calculator(JTable t){
		source = t;
		update();
	}
	
	Calculator(){
		
	}
	
	void update() {
		table = new double[source.getRowCount()][source.getColumnCount()];
		for(int row = 0;row < source.getRowCount(); row++) {
			table[row][0] = Double.parseDouble((String)source.getValueAt(row, 0));
			table[row][1] = Double.parseDouble((String)source.getValueAt(row, 1));
		}
		eX = E(Calculator.X);
		eY = E(Calculator.Y);
		varX = Var(Calculator.X);
		varY = Var(Calculator.Y);
	}
	
	double E(int type) {
		double sum = 0;
		for(double[]row : table) {
			sum += row[type];
		}
		return sum / table.length;
	}
	
	double Var(int type) {
		double sum = 0;
		if(type == Calculator.X) {
			for(double[]row : table) {
				sum += (row[type] - eX) * (row[type] - eX);
			}
		}else {
			for(double[]row : table) {
				sum += (row[type] - eY) * (row[type] - eY);
			}
		}
		return sum / (table.length - 1);
	}
	
	void addTable(JTable t) {
		table = new double[t.getRowCount()][t.getColumnCount()];
		for(int row = 0;row < t.getRowCount(); row++) {
			table[row][0] = Double.parseDouble((String)t.getValueAt(row, 0));
			table[row][1] = Double.parseDouble((String)t.getValueAt(row, 1));
		}
	}
	
	double covariance() {
		double sum = 0;
		for(double[]row : table) {
			sum += (row[X] - eX)*(row[Y] - eY);
		}
		return sum / (table.length - 1);
	}
	
	double correlation() {
		return covariance() / Math.sqrt(varX * varY);
	}
	
	double[] regression() {
		double a, b, sum1 = 0, sum2 = 0;
		for(double[]row : table) {
			sum1 += (row[X] - eX)*(row[Y] - eY);
		}
		for(double[]row : table) {
			sum2 += (row[X] - eX)*(row[X] - eX);
		}
		b = sum1 / sum2;
		a = eY - b*eX;
		double[]arr = {a,b};
		return arr;
		
	}
}