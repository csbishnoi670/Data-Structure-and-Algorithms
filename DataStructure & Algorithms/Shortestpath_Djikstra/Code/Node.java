import java.util.*;
public class Node{
	String state;
	LinkedList<HeapNode> padosi;
	public Node(String state){
		this.state = state;
	}
	public Node(String state,LinkedList<HeapNode> padosi){
		this.state = state;
		this.padosi = padosi;
	}
}