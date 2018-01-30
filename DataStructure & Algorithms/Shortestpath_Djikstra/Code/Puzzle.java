import java.util.*;
import java.util.List;
import java.io.*;
import java.util.Scanner;
public class Puzzle{
	HashMap<String,Node> hash;
	HashMap<String,HeapNode> hash2;
	String start="12345678G";
	String start2 ="12345687G";
	Stack<String> stack;
	public void CreateGraph(){
		//System.out.println("CreateGraph is start");
		hash = new HashMap<String,Node>();
		hash2 = new HashMap<String,HeapNode>();
		stack = new Stack<String>();
		stack.push(start);
		stack.push(start2);
		CreateGraph2();
		//System.out.println("CreateGraph is over");
	}
	public void CreateGraph2(){
		//System.out.println("CreateGraph2 is start");
		while(!stack.empty()){
			setPadosi(stack.pop());
		}
		//System.out.println("CreateGraph2 is over");
	}
	public boolean IsSolvable(String source,String goal){
		int inv = inverseNumber(source);
		int inv2 = inverseNumber(goal);
		return ((inv-inv2)%2==0);
	}
	public int inverseNumber(String string){
		int k = string.indexOf('G');
		StringBuilder s = new StringBuilder(string);
		s.deleteCharAt(k);
		int count=0;
		for(int i=0;i<7;i++){
			for(int j=i+1;j<8;j++)
				if(Character.getNumericValue(s.charAt(i))>Character.getNumericValue(s.charAt(j)))
					count++;
		}
		return count;
	}


	public void ShortestPath(String source,String goal,int[] CostString){
		//System.out.println("ShortestPath is start");
		Node start = hash.get(source);
		HeapNode startHeapnode = new HeapNode(source,0);
		startHeapnode.dis = 0;
		int size = hash.size();
		int count=0 ;
		Heap heap = new Heap(size,startHeapnode);
		//startHeapnode.prev = null;
		while(heap.size()!=0){
			HeapNode heapCurrentnode = heap.DeleteMin();
			count = heapCurrentnode.moves+1;
			if(heapCurrentnode.state.equals(goal)){
				PrintShortest(heapCurrentnode,CostString);
				break;
			}
			LinkedList<HeapNode> List = hash.get(heapCurrentnode.state).padosi;
			for(int i=0;i<List.size();i++){
				HeapNode padosiheapnode = List.get(i);
				//padosiheapnode.moves=count;
				ChangeDis(heapCurrentnode,padosiheapnode,CostString);
				if(heap.insert(padosiheapnode,count)){
					padosiheapnode.moves= count;
					padosiheapnode.prev=heapCurrentnode;
				}
			}
		}
		//System.out.println("ShortestPath is over");
	}
	public void ChangeDis(HeapNode heapCurrentnode,HeapNode padosiheapnode,int[] CostString){
		//System.out.println("ChangeDis is start");
		int cost = Cost(heapCurrentnode,padosiheapnode,CostString) ;
		//System.out.println("cost"+cost);
		if(padosiheapnode.dis>heapCurrentnode.dis+cost){
			padosiheapnode.dis = heapCurrentnode.dis+cost;
			padosiheapnode.prev=heapCurrentnode;
		}
		//System.out.println("ChangeDis is over");
	}
	public int Cost(HeapNode heapCurrentnode,HeapNode padosiheapnode,int[] CostString){
		//System.out.println("Cost is start");
		String state1 = heapCurrentnode.state; 
		int i = state1.indexOf('G');
		String state2 = padosiheapnode.state;
		char moveElement = state2.charAt(i);
		//System.out.println("Cost is over");
		return CostFunction(moveElement,CostString); 
		
	}
	public int CostFunction(char c,int[] CostString){
		//System.out.println("CostFunction is start");
		int i = Character.getNumericValue(c);
		int c2 = CostString[i-1];
		//System.out.println("CostFunction is over");
		return c2;
		
	}
	public void PrintShortest(HeapNode heapnode,int[] CostString){
		//System.out.println("PrintShortest is start");
		int step = 0;
		int cost = 0;
		Stack<String> moves = new Stack<String>();
		while(heapnode.prev!=null){
			step++;
			cost = cost+Cost(heapnode.prev,heapnode,CostString);
			String move = MoveElements(heapnode.prev,heapnode);
			moves.push(move);
			heapnode=heapnode.prev;
		}
		System.out.println(step+" "+cost);
		//System.out.print(moves.size());
		int size = moves.size();
		if(moves.size()!=0){
			for(int i=0;i<size-1;i++){
				System.out.print(moves.pop()+" ");
			}
			System.out.println(moves.pop());
		}
		else
			System.out.println();
	}
	public String MoveElements(HeapNode heapNode,HeapNode padosiHeapNode){
		String state1 = heapNode.state;
		String state2 = padosiHeapNode.state;
		int i = state1.indexOf('G');
		int j = state2.indexOf('G');
		StringBuilder s2 = new StringBuilder("");
		if(i-j==1){
			s2.append(state1.charAt(j)+"R");
		}
		else if(i-j==-1){
			s2.append(state1.charAt(j)+"L");
		}
		else if(i-j==3){
			s2.append(state1.charAt(j)+"D");
		}
		else if(i-j==-3){
			s2.append(state1.charAt(j)+"U");
		}
		return s2.toString();
	}
	public void setPadosi(String state){
		int i = state.indexOf('G');
		LinkedList<HeapNode> padosi = new LinkedList<HeapNode>();
		if(i>0&&((i-1)%3 == i%3 -1)){
			String sNyi = nyiString(i,i-1,state);
			if(hash2.containsKey(sNyi)){
				padosi.add(hash2.get(sNyi));
			}
			else{
				HeapNode heapnode = new HeapNode(sNyi,0);
				padosi.add(heapnode);
				hash2.put(sNyi,heapnode);
			}
			
			if(!hash.containsKey(sNyi)){
				stack.push(sNyi);
			}
			
		}
		if(i<6){
			String sNyi = nyiString(i,i+3,state);
			if(hash2.containsKey(sNyi)){
				padosi.add(hash2.get(sNyi));
			}
			else{
				HeapNode heapnode = new HeapNode(sNyi,0);
				padosi.add(heapnode);
				hash2.put(sNyi,heapnode);
			}
			if(!hash.containsKey(sNyi)){
				stack.push(sNyi);
			}
		}
		if(i>2){
			String sNyi = nyiString(i,i-3,state);
			if(hash2.containsKey(sNyi)){
				padosi.add(hash2.get(sNyi));
			}
			else{
				HeapNode heapnode = new HeapNode(sNyi,0);
				padosi.add(heapnode);
				hash2.put(sNyi,heapnode);
			}
			if(!hash.containsKey(sNyi)){
				stack.push(sNyi);
			}
		}
		if(i<8&&((i+1)%3 == i%3+1)){
			String sNyi = nyiString(i,i+1,state);
			//String process = state.charAt(i+1)+"L";
			if(hash2.containsKey(sNyi)){
				padosi.add(hash2.get(sNyi));
			}
			else{
				HeapNode heapnode = new HeapNode(sNyi,0);
				padosi.add(heapnode);
				hash2.put(sNyi,heapnode);
			}
			if(!hash.containsKey(sNyi)){
				stack.push(sNyi);
			}
		}
		Node node = new Node(state,padosi);
		hash.put(state,node);
		return;



	}
	public String nyiString(int i,int j,String s){
		StringBuilder sb = new StringBuilder(s);
		char c1 = sb.charAt(i); char c2 = sb.charAt(j);
		sb.setCharAt(i,c2);
		sb.setCharAt(j,c1);
		return sb.toString();
	}
	public static void main(String[] args) {
		Puzzle obj = new Puzzle();
		long startTime = System.currentTimeMillis();
		
		try{
			FileReader reader = new FileReader(args[0]);
			PrintStream out = new PrintStream(new FileOutputStream(args[1]));
			System.setOut(out);
			Scanner br = new Scanner(reader);
			int n = br.nextInt();
			br.nextLine();
			
			for(int i=0;i<n;i++){
				String states = br.nextLine();
				String[] states2 = states.split("\\s");
				String intial = states2[0];
				String goal = states2[1];
				int[] cost2 = new int[8];
				for(int j=0;j<8;j++){
					cost2[j]=br.nextInt();
				}
				br.nextLine();
				if(!obj.IsSolvable(intial,goal)){
					System.out.println("-1 -1");
					System.out.println();
				}
				else{
					obj.CreateGraph();
					obj.ShortestPath(intial,goal,cost2);
				}
			}

		}
	catch(IOException e){
		System.out.println(e);
	}
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		//System.out.println(totalTime);
	}

}