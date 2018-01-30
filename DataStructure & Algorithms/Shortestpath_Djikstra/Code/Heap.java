import java.util.*;
public  class Heap{
	HeapNode[] A;
	HashMap<String,HeapNode> hash2;
	int N;
	public Heap(int size,HeapNode source){
		hash2 = new HashMap<String,HeapNode>();
		A = new HeapNode[size];
		BuildHeap(source);
	}
	public void BuildHeap(HeapNode source){
		hash2.put(source.state,source);
		A[1]=source;
		N=1;
		Heapify();
	}

	public int compare(HeapNode a,HeapNode b,int count){
		if(a.dis < b.dis)
			return -1;
		else if(a.dis > b.dis)
			return 1;
		else{
			if(a.moves <= count)
				return -2;
			else
				return 2;
		}
	}
	public HeapNode DeleteMin(){
		HeapNode Min = A[1];
		PercDown(1,A[N]);
		N--;
		return Min;
	}
	public boolean insert(HeapNode heapnode,int count){
		boolean bool=false;
		if(hash2.containsKey(heapnode.state)){
			if(compare(hash2.get(heapnode.state), heapnode,count) == 1){
				hash2.get(heapnode.state).dis = heapnode.dis;
				Heapify();
				bool = false;
			}
			else if(compare(hash2.get(heapnode.state),heapnode,count)==2){
				hash2.get(heapnode.state).moves = count;
				heapnode.moves=count;
				Heapify();
				bool=true;
			}
		}
		else{
			N++;
			A[N] = heapnode;
			PercUp(N,heapnode);
			hash2.put(heapnode.state,heapnode);
			bool=false;
		}
		return bool;
		//print();

	}
	public int size(){
		return N;
	}
	public void Heapify(){
		for(int i=N/2;i>0;i--){
			PercDown(i,A[i]);
		}
	}
	public void PercDown(int i,HeapNode x){
		if(2*i>N){
			A[i]=x;
		}
		else if(2*i==N){
			if(x.dis>A[2*i].dis){
				A[i]=A[2*i];
				A[2*i]=x;
			}
			else
				A[i]=x;
		}
		else{
			int j=0;
			if(A[2*i].dis>A[2*i+1].dis)
				j=2*i+1;
			else
				j=2*i;
			if(A[j].dis>x.dis)
				A[i]=x;
			else{
				A[i]=A[j];
				PercDown(j,x);
			}
		}
	}
	public void PercUp(int i , HeapNode x){
		if(i==1)
			A[i]=x;
		else if(A[i/2].dis<x.dis)
			A[i]=x;
		else if(A[i/2].dis>x.dis){
			HeapNode temp=A[i];
			A[i]=A[i/2];
			A[i/2]=temp;
			PercUp(i/2,A[i/2]);
		}
	}
	public void print(){
		for(int i=1;i<=N;i++){
			System.out.print(" "+A[i].state+" "+A[i].dis);
		}
		System.out.println();
	}

}
