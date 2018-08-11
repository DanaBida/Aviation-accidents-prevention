
public class LinkedList {
	
	private Container head; //pointer to the beginning of the list
	private Container tail; //pointer to the end of the list
	private int listSize;
	
	// LinkedList constructor
	public LinkedList() {
		head = null;
		tail = null;
		listSize = 0;
	}
	
	public LinkedList(Container head, Container tail, int listSize){
		this.head = head;
		this.tail = tail;
		this.listSize = listSize;
	}
	
	// adds a new link at the begining of the list
	public void addFirst(Container n){	
		Container temp = head;
		head = n;
	    if (temp == null) 
	    	tail = n;
	    else{
	   		temp.setPrev(n);
       		n.setNext(temp);
       	}
	    listSize++;
	}
	
	// adds a new link at the end of the list
	public void addLast(Container n){
		Container temp = tail;
	    tail = n;
	    if (temp == null){
	       	head = n;
	    }
	    else{
	        temp.setNext(n);
	        n.setPrev(temp);
	    }
	    listSize++;
	}
	
	// adds a new link n after node "after" and before node "before" in the list
	public void addBetween(Container before, Container after, Container toAdd){		
		before.setNext(toAdd);
		toAdd.setPrev(before);
		after.setPrev(toAdd);
		toAdd.setNext(after);
		listSize++;
	}
	
	// removes the first link of the list
	// returns the new head of the list or null if the list was empty 
	public Container removeFirst(){
		
		if (listSize <= 1){
			if(listSize == 1){
				head = null;
				tail = null;
				listSize--;
			}
		}
		else{
	    	head = head.getNext();
	    	head.setPrev(null);
	    	listSize--;
		}
		return head;
	}

	// removes the last link of the list
	// returns the new tail of the list or null if the list was empty
	public Container removeLast(){
		
		if (listSize <= 1){
			if(listSize == 1){
				head = null;
				tail = null;
				listSize--;
			}
		}
		else{
	    	tail = tail.getPrev();
	    	tail.setNext(null);
	    	listSize--;
		}
		return tail;
	}
	
	//remove node n from the list.
	public void removeBetween(Container before, Container after, Container toRemove){
		if(before!=null && after!=null){
			after.setPrev(before);
			before.setNext(after);
			listSize--;
		}
	}
		
	//returns the first element in this list
	public Container getHead() {
		return head;
	}

	public void setHead(Container head) {
		this.head = head;
	}
	
	//returns the last element in this list
	public Container getTail() {
		return tail;
	}
	
	public void setTail(Container tail) {
		this.tail = tail;
	}
	
	// returns the number of elements in this list
	public int getListSize() {
		return listSize;
	}
		
	public void setListSize(int listSize) {
		this.listSize = listSize;
	}	
		
	public boolean isEmpty() {
		return head == null;
	}	
	
	public String toString() {
		Container current = head;
		String output = "";
		while(current != null) {
			output += "[" + current.getData().toString() + "]";
			current = current.getNext();
		}
		return output;
	}
	
	
}
