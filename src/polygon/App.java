package polygon;

import java.awt.EventQueue;

import polygon.gui.MainFrame;

/*
 * Program was made by Branko Milovanovic
 * linkedin.com/in/branko-milovanovic
 * github.com/brankomilovanovic
*/

public class App {
	
	public static void main(String[] args) { 
		EventQueue.invokeLater(new Runnable() {
		public void run() {
			try {
				MainFrame mainFrame = new MainFrame();
				mainFrame.run();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});
	}
}
