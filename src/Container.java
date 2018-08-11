//Don't change the class name
public class Container
{
	private Point data;//Don't delete or change this field;
	private Container next;
	private Container prev;
	private int key;
	private Container sameInOppList; //a pointer to a container with the same data that is in the list sorted by the opposite axis
	private boolean isInRange; // for the function getPointsInOppAxis
	
	
	public Container(Point data, Container next, Container prev, Container sameInOppList, boolean isInRange, int key){
		this.data = data;
		this.next = next;
		this.prev = prev;
		this.sameInOppList = sameInOppList;
		this.isInRange = isInRange;
		this.key = key;
	}
	
	public Container(Point data, int key){
		this(data, null, null, null, false, key);
	}
		
	//Don't delete or change this function
	public Point getData(){
		return data;
	}
	
	public Container getNext() {
		return next;
	}

	public void setNext(Container next) {
		this.next = next;
	}

	public Container getPrev() {
		return prev;
	}

	public void setPrev(Container prev) {
		this.prev = prev;
	}

	public Container getSameInOppList() {
		return sameInOppList;
	}

	public void setSameInOppList(Container sameInOppList) {
		this.sameInOppList = sameInOppList;
	}

	public boolean getIsInRange() {
		return isInRange;
	}

	public void setIsInRange(boolean isInRange) {
		this.isInRange = isInRange;
	}

	public int getKey() {
		return key;
	}
	
	public void setKey(int key) {
		this.key = key;
	}
		
}
