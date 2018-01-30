import java.util.*;
public class HeapNode{
	double dis;
	String state;
	int moves;
	HeapNode prev;
	public HeapNode(String state,int m){
		this.state = state;
		moves = m;
		this.dis = Double.POSITIVE_INFINITY;
		prev=null;
	}

}