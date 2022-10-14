package polygon.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import polygon.gui.MainFrame;
import polygon.model.Polygon;

public class PointListener extends MouseAdapter {

        private MainFrame mainFrame; //view
        private Polygon polygon; //model

        public PointListener() {
        	super();
        }
        
        public PointListener(MainFrame mainFrame, Polygon polygon) {
            this.mainFrame = mainFrame;
            this.polygon = polygon;
        }

        public void mousePressed(MouseEvent event) {
        	if(polygon.getNumberVertices() < 3) {
        		JOptionPane.showMessageDialog(null, "ERROR: You can't enter less than 3 polygon vertices or you haven't entered no one!!" , "Error message", JOptionPane.ERROR_MESSAGE);
        	}
        	else if(polygon.getPoints().size() < polygon.getNumberVertices()) {
        		mainFrame.getAutoDrawButton().setEnabled(false);
        		mainFrame.getNumberVerticesInput().setEditable(false);
            	polygon.addPoint(event.getPoint()); // We add a new position to the list
                mainFrame.repaint(); // We paint again to show the newly added point
                if(polygon.getPoints().size() == polygon.getNumberVertices()) { // When the list of points is the same length as the number of vertices, we draw
            		polygon.setComplete(true);
        			mainFrame.repaint();
                }
        		mainFrame.updateCountNumberVerticesLabel(polygon.getPoints().size(), polygon.getNumberVertices());
        	} else {
        		polygon.setCheckPoint(event.getPoint());
        		mainFrame.repaint();
        	}
        }
    }
