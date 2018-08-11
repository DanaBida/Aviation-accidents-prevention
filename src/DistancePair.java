
public class DistancePair {

	private Point[] pair;
	private double distance;
	
	// Constructor that calculates the distance between the two points
	public DistancePair(Point[] pair) {
		this.pair = pair;
		if(pair != null) //calculates the distance between two points
			distance = Math.hypot(pair[0].getX()-pair[1].getX(), pair[0].getY()-pair[1].getY());
		else
			distance = Integer.MAX_VALUE;
	}
	
	public DistancePair(){
		this(null);
	}

	
	public double getDistance() {
	
		return distance;
	}
	
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	public Point[] getPair() {
		return pair;
	}
	
	public void setPair(Point[] pair) {
		this.pair = pair;
	}
	
}
