package polygon.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import polygon.controller.PadDraw;
import polygon.model.Polygon;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainFrame {

    private Polygon polygon; // model
    private PadDraw padDraw;
    
	private JLabel countNumberVerticesLabel = new JLabel("0/0");
	private static JLabel checkPointLabel = new JLabel();
	private JTextField numberVerticesInput = new JTextField();
	private JButton autoDrawButton = new JButton("Auto drawing");

    public MainFrame() {
        this.polygon = new Polygon();
        this.padDraw = new PadDraw(this, polygon);
    }

    public void run() {
    	JFrame frame = new JFrame("Polygon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
		ImageIcon iconInformation = new ImageIcon(new ImageIcon("src/assets/lilly021.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
		frame.setIconImage(iconInformation.getImage());
		
        padDraw.setPreferredSize(new Dimension(750, 500));
        frame.add(padDraw, BorderLayout.CENTER);
        frame.add(buttomPanel(), BorderLayout.SOUTH);
		frame.setJMenuBar(menuBar());
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
    }

    private JMenuBar menuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu menuBarHelp = new JMenu("Help");
		menuBar.add(menuBarHelp);
		ImageIcon iconInformation = new ImageIcon(new ImageIcon("src/assets/information.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
		menuBarHelp.setIcon(iconInformation);
		
		JMenuItem menuBarHelpMenu = new JMenuItem("About");
		menuBarHelp.add(menuBarHelpMenu);
		menuBarHelpMenu.setIcon(iconInformation);
		menuBarHelpMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
        		JOptionPane.showMessageDialog(null, "Program was made by Branko Milovanovic\nlinkedin.com/in/branko-milovanovic\n\nThis is a program to create a polygon and find a given point,\nis it located inside or outside the polygon." , "About application", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		JMenuItem menuBarHelpMenu2 = new JMenuItem("Draw Polygon");
		menuBarHelp.add(menuBarHelpMenu2);
		ImageIcon iconHelp = new ImageIcon(new ImageIcon("src/assets/help.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
		menuBarHelpMenu2.setIcon(iconHelp);
		menuBarHelpMenu2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
        		JOptionPane.showMessageDialog(null, "Step 1:\nEnter the number of vertices of the polygon\n\n"
        											+ "Step 2:\nYou are positioning the vertices of the polygon,\nby clicking the mouse somewhere in the empty space\nor\nyou can choose to have the polygon drawn automatically,\nby pressing a button 'Auto Drawing'\n\n"
        											+ "Step 3:\nYou set a checkpoint, you can change the position of the control point by pressing the mouse\n\n"
        											+ "Step 4:\nYou get a message the point is inside or outside the polygon and it is concave or convex" , "How the program works", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		return menuBar;
    }
    
    private JPanel buttomPanel() {

    	JPanel containerPanel = new JPanel();
    	containerPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		containerPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel buttomPanel = new JPanel();
		buttomPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		containerPanel.add(buttomPanel, BorderLayout.SOUTH);
		buttomPanel.setLayout(new BoxLayout(buttomPanel, BoxLayout.X_AXIS));
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_1.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		buttomPanel.add(panel_1);
		
		JLabel numberVerticesLabel = new JLabel("Number vertices:");
		panel_1.add(numberVerticesLabel);
		
		panel_1.add(numberVerticesInput);
		numberVerticesInput.setColumns(3);
		
		numberVerticesInput.addKeyListener((KeyListener) new KeyAdapter() {
			   boolean oneWay = false; // If I don't put this kind of check, goes through the loop as many times as we entered the character.
			   public void keyTyped(KeyEvent e) {
			      char c = e.getKeyChar();
			      if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
			         e.consume(); // if it's not a number, ignore the event
			      } else {
			    	if(oneWay == false) {
				  		numberVerticesInput.getDocument().addDocumentListener(new DocumentListener() {
							@Override // We set the number of vertices and write that number in the label
							public void removeUpdate(DocumentEvent e) { updateCountNumberVerticesLabel(polygon.getPoints().size(), padDraw.numberVertices(numberVerticesInput.getText())); }
							
							@Override
							public void insertUpdate(DocumentEvent e) { updateCountNumberVerticesLabel(polygon.getPoints().size(), padDraw.numberVertices(numberVerticesInput.getText()));  }
				
							@Override
							public void changedUpdate(DocumentEvent e) { updateCountNumberVerticesLabel(polygon.getPoints().size(), padDraw.numberVertices(numberVerticesInput.getText()));  }
						});
			    	}
			    	oneWay = true;
			      }
			   }
			});
		
		panel_1.add(countNumberVerticesLabel);
		
		JPanel panel_3 = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panel_3.getLayout();
		flowLayout_2.setVgap(9);
		buttomPanel.add(panel_3);
		
		panel_3.add(checkPointLabel);
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		buttomPanel.add(panel_2);
		
		panel_2.add(autoDrawButton);
		autoDrawButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				autoDrawing(numberVerticesInput.getText());
			}
		});
		
		JButton resetButton = new JButton("Reset");
		panel_2.add(resetButton);
		resetButton.addActionListener(event -> { reset(); });

        return containerPanel;
    }

    public void reset() {
		polygon.setNumberVertices(0);
		polygon.setComplete(false);
		polygon.clearList();
		polygon.setCheckPoint(new Point());
		countNumberVerticesLabel.setText("0/0");
		checkPointLabel.setText("");
		numberVerticesInput.setText(null);
		numberVerticesInput.setEditable(true);
		getAutoDrawButton().setEnabled(true);
      	repaint();
    }
    
    public void repaint() {
    	padDraw.repaint();
    }
    
    public void updateCountNumberVerticesLabel(int drawnDots, int numberVertices) {
		countNumberVerticesLabel.setText(drawnDots + "/" + numberVertices);
    }
    
    public void autoDrawing(String number) {
    	try {
    		if(Integer.parseInt(numberVerticesInput.getText()) >= 3) {
    			numberVerticesInput.setEditable(false);
				polygon.setComplete(true);
				polygon.setNumberVertices(Integer.parseInt(numberVerticesInput.getText()));
				repaint();
				updateCountNumberVerticesLabel(Integer.parseInt(numberVerticesInput.getText()), Integer.parseInt(numberVerticesInput.getText()));
				getAutoDrawButton().setEnabled(false);
    		} else {
             	JOptionPane.showMessageDialog(null, "ERROR: You can't enter less than 3 polygon vertices!!" , "Error message", JOptionPane.ERROR_MESSAGE);
    		}
    	} catch (NumberFormatException e) {
    		JOptionPane.showMessageDialog(null, "ERROR: You have not entered any polygon vertices!!" , "Error message", JOptionPane.ERROR_MESSAGE);
		}
    }

	public JTextField getNumberVerticesInput() {
		return numberVerticesInput;
	}
	
	public static void setCheckPointLabel(String position, String shape) {
		checkPointLabel.setText("Point is " + position + " the " + shape + " polygon!");
	}

	public JButton getAutoDrawButton() {
		return autoDrawButton;
	}
}
