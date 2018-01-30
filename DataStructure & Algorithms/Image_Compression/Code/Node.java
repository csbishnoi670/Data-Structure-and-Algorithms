import java.util.*;
public class Node{
	public int element;
	public Node next;

	public Node(){
		this(0,null);
	}
	public Node(int e,Node n){
		element=e;
		next=n;
	}
	public void setNext(Node n){next=n;}
	public void setElement(int e){element=e;}
	public Node getNext(){return next;}
	public int getElement(){return element;}

}
