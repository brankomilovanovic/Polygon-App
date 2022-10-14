package polygon.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Polygon {
	
	private boolean complete = false;
    private List<Point> points = new ArrayList<Point>();
    private Point checkPoint = new Point();
    private int numberVertices = 0;
   
    public Polygon() {
    	super();
	}

	public Polygon(boolean complete, List<Point> points) {
		super();
		this.complete = complete;
		this.points = points;
	}

	public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public boolean isComplete() {
        return complete;
    }

    public void clearList() {
        this.points.clear();
    }

    public void addPoint(Point point) {
        this.points.add(point);
    }

    public List<Point> getPoints() {
        return points;
    }

	public int getNumberVertices() {
		return numberVertices;
	}

	public void setNumberVertices(int numberVertices) {
		this.numberVertices = numberVertices;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	public Point getCheckPoint() {
		return checkPoint;
	}

	public void setCheckPoint(Point checkPoint) {
		this.checkPoint = checkPoint;
	}
}
