package mainPack;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ProgramMenu extends JMenuBar{
	MainFrame parent;
	JMenu file, display;
	JMenuItem itemNew, itemOpen, itemSave;
	JCheckBoxMenuItem itemNumber;
	
	ProgramMenu(){
		file = new JMenu("file");
		add(file);
		itemNew = new JMenuItem("new");
		itemOpen = new JMenuItem("open");
		itemSave = new JMenuItem("save");
		file.add(itemNew);file.add(itemOpen);file.add(itemSave);
		
		display = new JMenu("display");
		add(display);
		itemNumber = new JCheckBoxMenuItem("number displayed");
		display.add(itemNumber);
	}
}