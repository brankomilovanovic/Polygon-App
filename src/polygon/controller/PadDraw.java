package polygon.controller;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.List;
import javax.swing.JPanel;
import polygon.gui.MainFrame;
import polygon.model.Polygon;

public class PadDraw extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Polygon polygon;
	
	private int radius = 4;
	private int diameter = radius + radius;
	
	public PadDraw() {
		super();
	}
	
	public PadDraw(MainFrame mainFrame, Polygon polygon) {
		this.polygon = polygon;
		this.addMouseListener(new PointListener(mainFrame, polygon));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		java.awt.Polygon polygonDraw = new java.awt.Polygon();
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// Draw polygon
		if (polygon.isComplete()) {         
			
			if (polygon.getPoints().size() == 0) { // This is autocomplete
				for (int i = 0; i < polygon.getNumberVertices(); i++){
					Point point = new Point((int) (350 + 100 * Math.cos(i * 2 * Math.PI / polygon.getNumberVertices())), 
							(int) (200 + 100 * Math.sin(i * 2 * Math.PI / polygon.getNumberVertices())));
					polygon.addPoint(point);
				}
			}
			
			List<Point> points = polygon.getPoints();
			for (int i = 0; i < points.size(); i++) {
				polygonDraw.addPoint(points.get(i).x, points.get(i).y);
			}
			
			g2d.drawPolygon(polygonDraw);
			
			if(polygon.getCheckPoint().x != 0 && polygon.getCheckPoint().y != 0) {
				String position = "outside", shape = "concave";
				if(polygonDraw.contains(polygon.getCheckPoint()) == true) { position = "inside"; } 
				if(checkConvex() == true) { shape = "convex"; } 
				MainFrame.setCheckPointLabel(position, shape);
			}
		}
		
		
		// Draw points
		int ordinalNumberPoint = 0;
		for (Point point : polygon.getPoints()) {
			g2d.fillOval(point.x - radius, point.y - radius, diameter, diameter);
			g2d.drawString(toAlphabetic(ordinalNumberPoint), point.x + 5, point.y + 5);
			ordinalNumberPoint++;
		}
		
		// Draw check point dot
		if(polygon.isComplete() == true) {
			g2d.fillOval(polygon.getCheckPoint().x - radius, polygon.getCheckPoint().y - radius, diameter, diameter);
			g2d.drawString(toAlphabetic(polygon.getPoints().size()), polygon.getCheckPoint().x + 5, polygon.getCheckPoint().y + 5);
		} 
	}
	
	public int numberVertices(String n) {
		try {
			polygon.setNumberVertices(Integer.parseInt(n));
		} catch (NumberFormatException e) {
			polygon.setNumberVertices(0);
		}
		return polygon.getNumberVertices();
	}
	
	public static String toAlphabetic(int i) { // We convert the ordinal number into a letter of the alphabet (1=A, 2=B, 3=C...)
		if(i < 0) { return "-"+toAlphabetic(-i-1); }
		int quot = i/26;
		int rem = i%26;
		char letter = (char)((int)'A' + rem);
		if( quot == 0 ) {
			return ""+letter;
		} else {
			return toAlphabetic(quot-1) + letter;
		}
	}
	
	public boolean checkConvex() {
		if (polygon.getPoints().size() < 3) { return false; }
		Point p, v, u;
		int res = 0;
		for(int i = 0; i < polygon.getPoints().size(); i++) {
			p = polygon.getPoints().get(i);
			Point tmp = polygon.getPoints().get((i+1) % polygon.getPoints().size());
			v = new Point();
			v.x = tmp.x - p.x;
			v.y = tmp.y - p.y;
			u = polygon.getPoints().get((i+2) % polygon.getPoints().size());
			if (i == 0) { // in first loop direction is unknown, so save it in res
				res = u.x * v.y - u.y * v.x + v.x * p.y - v.y * p.x;
			} else {
				int newres = u.x * v.y - u.y * v.x + v.x * p.y - v.y * p.x;
				if((newres > 0 && res < 0) || (newres < 0 && res > 0)) {
					return false;
				}
			}
		}
		return true;
	}
}

