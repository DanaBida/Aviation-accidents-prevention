import java.lang.Math;

public class DataStructure implements DT {

	LinkedList sortedByX;
	LinkedList sortedByY;
	
	public DataStructure(){	//Creates two empty linked lists
		sortedByX = new LinkedList();
		sortedByY = new LinkedList();
	}
	
	public DataStructure(LinkedList sortedByX, LinkedList sortedByY){
		this.sortedByX = sortedByX;
		this.sortedByY = sortedByY;
	}
	
	// adds a new point to the lists sortedByX and sortedByY in a sorted manner
	public void addPoint(Point point){
		
		// creates a container with the new point for sortedByX 
		Container xList = new Container(point, point.getX());
		// creates a container with the new point for sortedByY 
		Container yList = new Container(point, point.getY());

		// adds each container to the corresponding list
		addPointByAxis(sortedByX, xList);
		addPointByAxis(sortedByY, yList);

		// updates mutual pointers
		xList.setSameInOppList(yList);
		yList.setSameInOppList(xList);
		
	}

	// adds the container 'toAdd' to the list 'list' by his key
	private void addPointByAxis(LinkedList list, Container toAdd){
		
		Container curr = list.getHead();
		// finding the correct place at the list to place the added point
		while (curr!=null && curr.getKey() < toAdd.getKey())
			curr = curr.getNext();
		if(curr!=null && curr.getPrev()!=null) //insert between two containers
			list.addBetween(curr.getPrev(), curr, toAdd);
		else{ //insert at the end or the beginning of the list
			if(curr == null)
				list.addLast(toAdd);
			else
				list.addFirst(toAdd);
		}
		
	}

	/*	returns an array that contains the points between the values min and max in the given axis
	 *  sorted by the same axis (axis=X if axis==true, axis=Y if axis==false) */
	public Point[] getPointsInRangeRegAxis(int min, int max, Boolean axis) {
		
		if(axis) 
			return getPointsInRangeRegAxis(min, max, sortedByX);
		else
			return getPointsInRangeRegAxis(min, max, sortedByY);
	}

	// this method returns a sorted array that contains points from the given list that are in the given range 
	private Point[] getPointsInRangeRegAxis(int min, int max, LinkedList list) {
			
		int counterUntilMin = 0;
		int counterUntilMax = 0;
		Point [] answer = {};
		
		Container currUp = list.getHead();
		//passes all points with AxisValue smaller than min, in ascending way 
		while (currUp.getNext()!=null && currUp.getKey() < min) {
			counterUntilMin++;
			currUp = currUp.getNext();
		}
		Container currDawn = list.getTail();
		//passes all points with AxisValue bigger than max, in descending way 
		while (currDawn.getPrev()!=null && currDawn.getKey() > max) {
			counterUntilMax++;
			currDawn = currDawn.getPrev();
		}
		//fills the answer array with the values in [min,max].
		answer = new Point[list.getListSize()-(counterUntilMax+counterUntilMin)];
		for(int i=0; i<answer.length; i++){
		answer[i] = currUp.getData();
			currUp = currUp.getNext();	
		}
		return answer;
	}
		
	/* returns an array that contains the points between the values min and max in the given axis
	 * sorted by the other axis (axis=X if axis==true, axis=Y if axis==false) */
	public Point[] getPointsInRangeOppAxis(int min, int max, Boolean axis) {
		
		if(axis) //points in X axis sorted by Y values
			return getPointsInRangeOppAxis(min, max, sortedByX, sortedByY);
		else //points in Y axis sorted by X values
			return getPointsInRangeOppAxis(min, max, sortedByY, sortedByX);
	}

	/* returns a sorted array contains points in the given range from 'list' list, 
	 * sorted by their order in 'sortedBy' list */
	public Point[] getPointsInRangeOppAxis(int min, int max, LinkedList list, LinkedList sortedBy) {
				
		int counterUntilMin=0;
		int counterUntilMax=0;
		Point [] answer={};
			
		Container currUp = list.getHead();
		//passes all points with AxisValue smaller than min, in ascending way 
		while (currUp.getNext()!=null && currUp.getKey() < min) {
			counterUntilMin++;
			currUp = currUp.getNext();
		}
		Container currDawn = list.getTail();
		//passes all points with AxisValue bigger than max, in descending way 
		while (currDawn.getPrev()!=null && currDawn.getKey() > max) {
			counterUntilMax++;
			currDawn = currDawn.getPrev();
		}
		//sign the nodes in 'sortedBy' list that in the range [min,max] in 'list' list
		int counter = list.getListSize()-(counterUntilMax+counterUntilMin);
		while (counter > 0){
			currUp.getSameInOppList().setIsInRange(true);
			currUp=currUp.getNext();
			counter--;
		}
		//fills the answer array with the points in the range [min,max] in 'list' list by their order in 'sortedBy'. 
		answer = new Point[list.getListSize()-(counterUntilMax+counterUntilMin)];
		Container pointer = sortedBy.getHead();
		int counterList=0, index=0;
		while(counterList < sortedBy.getListSize() && index < answer.length){
			if(pointer.getIsInRange()){
				answer[index] = pointer.getData();
				index++;
			}
			counterList++;
			pointer = pointer.getNext();
		}	
		Container pointer1 = sortedBy.getHead();
		for( int i=0 ; i < sortedBy.getListSize() ; i++){
			pointer1.setIsInRange(false);
			pointer1 = pointer1.getNext();
		}
		return answer;
	}	
	
	// returns the density of the points in the data structure
	public double getDensity() {
		
		double xMin ,xMax ,yMin ,yMax;
		if(sortedByX.getListSize() <= 1)
			return 0;
		else{
			xMin = sortedByX.getHead().getKey();
			xMax = sortedByX.getTail().getKey();
			yMin = sortedByY.getHead().getKey();
			yMax = sortedByY.getTail().getKey();
		}	
		return sortedByX.getListSize() / ((xMax - xMin) * (yMax - yMin));
	}

	/* removes all the points that bigger than max or smaller than min in sortedByAxis list,
	 * Axis=X if axis=true, else Axis=Y. */
	public void narrowRange(int min, int max, Boolean axis) {
		
		if(axis)
			narrowRangeInList(min, max, sortedByX, sortedByY);
		else
			narrowRangeInList(min, max, sortedByY, sortedByX);
	}

	/* removes the points as required from 'sortedBy' list
	 * and their sameInOppList points, that placed in the 'oppList' */
	private void narrowRangeInList(int min, int max, LinkedList sortedBy, LinkedList oppList){
		
		Container curr = sortedBy.getHead();
		// checks if points from the begining of the list should be removed
		while(curr != null && curr.getKey() < min){
			// remove the node that contains the same point from the oppList
			if(curr.getSameInOppList() == oppList.getHead())
				oppList.removeFirst();
			else if(curr.getSameInOppList() == oppList.getTail())
				oppList.removeLast();
			else 
				oppList.removeBetween(curr.getSameInOppList().getPrev(), curr.getSameInOppList().getNext(), curr.getSameInOppList());
			curr = sortedBy.removeFirst(); //curr became the new head after removing the first element from the list
		}
		
		curr = sortedBy.getTail();	
		// checks if points from the end of the list should be removed
		while(curr != null && curr.getKey() > max){
			// remove the node that contains the same point from the oppList
			if(curr.getSameInOppList() == oppList.getHead())
				oppList.removeFirst();
			else if(curr.getSameInOppList() == oppList.getTail())
				oppList.removeLast();
			else 
				oppList.removeBetween(curr.getSameInOppList().getPrev(), curr.getSameInOppList().getNext(), curr.getSameInOppList());			
			curr = sortedBy.removeLast();
		}
	}
	
	// returns the largest axis for the points that are in the data structure
 	public Boolean getLargestAxis() {
		
		int xMin ,xMax ,yMin ,yMax;
		if(sortedByX.isEmpty()||sortedByY.isEmpty())
			return true;
		else{
			xMin = sortedByX.getHead().getKey();
			xMax = sortedByX.getTail().getKey();
			yMin = sortedByY.getHead().getKey();
			yMax = sortedByY.getTail().getKey();
		}

		// if axis x is larger then axis y, return true
		return (xMax-xMin > yMax-yMin);
	}

 	//returns the median of the data structure
	public Container getMedian(Boolean axis) {
		
		if(axis)
			return getMedian(sortedByX);
		else
			return getMedian(sortedByY);
	}
	
	// returns the median point in a specific list of points
	public Container getMedian(LinkedList sortedBy) {
		
		if(sortedBy.isEmpty()){
			return null; 
		}
		else{
			Container current = sortedBy.getHead();
			Container jumper = sortedBy.getHead();
			while( (jumper != null) && 
					(jumper.getNext() != null) ){
				current = current.getNext();
				jumper = jumper.getNext().getNext();
			}
		return current;
		}
	}

	// returns the nearest pair in the strip with the width "width" and the center "container" according to axis
	public Point[] nearestPairInStrip(Container container, double width, Boolean axis) {
			
		int counterUntilleftEdge = 0, counterUntilrightEdge = 0;
		Point[] stripArr = {}; //the points in axis with value in the range: [container.x-width/2, container.x+width/2].
		Container rightEdge = container, leftEdge = container;
		
		// creates a sub array of points not further than container+-width.
		if(container.getNext()!=null){ //there are points in the right side of container
			rightEdge = container.getNext();
			while(rightEdge.getKey() <= container.getKey()+(double)(width/2)){
				counterUntilrightEdge++;
				if(rightEdge.getNext() == null)
					break;
				rightEdge = rightEdge.getNext();
			}
		}
		if(container.getPrev()!=null){ //there are points in the left side of container
			leftEdge = container.getPrev();
			while(leftEdge.getKey() >= container.getKey()-(double)(width/2)){
				counterUntilleftEdge++;
				if(leftEdge.getPrev() == null)
					break;
				leftEdge = leftEdge.getPrev();
			}
		}	
			
		// if the strip doesn't contain points
		if(container.getNext() == null && container.getPrev() == null || sortedByX.isEmpty() || counterUntilleftEdge == 0 && counterUntilrightEdge == 0)
			return null;
		// else fill the sub array
		stripArr = new Point[1 + counterUntilleftEdge + counterUntilrightEdge];
		Container temp = leftEdge;
		for(int i=0; i<stripArr.length; i++){
			stripArr[i] = temp.getData();
			temp = temp.getNext();
		}
		// saves the two points with the nearest distance and their distance.
		Point [] nearestPair = new Point [2];
		nearestPair[0] = leftEdge.getData();
		nearestPair[1] = rightEdge.getData();
		int x1, x2, y1, y2;
		DistancePair nearestDisPair = new DistancePair(nearestPair);
		for(int i=0 ; i<stripArr.length ; i++) { //passes all the cells in the strip array 
			for(int j=i+1 ; j< Math.min(i+7, stripArr.length) ; j++){ //passes all the cells that after i cell  		
				x1= stripArr[i].getX();
				x2= stripArr[j].getX();
				y1= stripArr[i].getY();
				y2= stripArr[j].getY();
				double distance = Math.hypot(x2-x1, y2-y1);
				if(distance < nearestDisPair.getDistance()){
					nearestPair[0] = stripArr[i];
					nearestPair[1] = stripArr[j];
					nearestDisPair = new DistancePair(nearestPair);
				}
			}	
		}
		return nearestDisPair.getPair();
	}
	
	// returns an array containing the closest pair of points in the data structure
	public Point[] nearestPair() {
	
		return nearestPair(this);
	}

	// this method returns an array containing the closest pair of points in the given data structure 'ds'
	private Point[] nearestPair(DataStructure ds){
		
		if(ds.sortedByX.getListSize() <= 2) //basic
			return ds.basicNearestPair();
		else{
			boolean largestAxis = ds.getLargestAxis();
			Container median = ds.getMedian(largestAxis);
			//split the data structure when median is in the middle
			DataStructure dsLeft = new DataStructure();
			DataStructure dsRight = new DataStructure();
			LinkedList list;
			if(largestAxis)
				list = ds.sortedByX;
			else
				list = ds.sortedByY;
			// adds the points with a key smaller than median key to dsLeft 
			Container curr = list.getHead();
			while(curr.getKey() < median.getKey()){
				dsLeft.addPoint(curr.getData());
				curr = curr.getNext();
			}
			// adds median point and the points with a key nearest than median key to dsRight 
			while(curr != null){
				dsRight.addPoint(curr.getData());
				curr = curr.getNext();
			}
			
			// saves the two nearest pairs - in the left and in the right side of median
			DistancePair closestPairLeft = new DistancePair(nearestPair(dsLeft));
			DistancePair closestPairRight = new DistancePair(nearestPair(dsRight));
			
			DistancePair closestPair = new DistancePair();			
			// calculate the nearest pair between the two pairs
			closestPair = closestPairRight;
			if(closestPairLeft!=null && closestPairLeft.getDistance() < closestPairRight.getDistance())
				closestPair = closestPairLeft;
			
			double minDist = closestPair.getDistance();
			// check if in split with width minDist*2 there is a nearest pair
			Point[] pairInStripArr = ds.nearestPairInStrip(median, (int)(2*minDist), largestAxis);
			if(pairInStripArr != null){
				DistancePair pairInStrip = new DistancePair(pairInStripArr);
				if(pairInStrip.getDistance() < minDist)
					closestPair = pairInStrip;
			}
			return closestPair.getPair();
		}	
	}

	public Point[] basicNearestPair(){ //there are two points or less 
				
		if(sortedByX.getListSize() <=1) 
			return null;
		if(sortedByX.getListSize() ==2) {
			Point[] twoClosestPoints = new Point[2];
			twoClosestPoints[0] = sortedByX.getHead().getData();
			twoClosestPoints[1] = sortedByX.getTail().getData();
			return twoClosestPoints;
		}
		return null;
	}

	public String toString() {
		
		Container currentX = sortedByX.getHead();
		String outputX = "[";
		while(currentX!=null && currentX.getData() != null) {
			if(currentX.getNext()==null)
				outputX += currentX.getData().toString();
			else
				outputX += currentX.getData().toString()+ "," ;
			currentX = currentX.getNext();
			}
		Container currentY = sortedByY.getHead();
		String outputY = "[";
		while(currentY!=null && currentY.getData() != null) {
			if(currentY.getNext()==null)
				outputY += currentY.getData().toString();
			else
				outputY += currentY.getData().toString() + ",";
			currentY = currentY.getNext();
			}
		return "list sorted by x: "+outputX+"]"+", list sorted by y: "+outputY+"]" ;
	}
	
}

