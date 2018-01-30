public class ArrayDequeue implements DequeInterface{
	public int N=1,f=0,r=1; 
   Object[] arr = new Object[N];
  public void insertFirst(Object o){
  	if(size()==N-1&&N!=1){
      DoubleArray();
      N=N*2;
    }
    else if(N==1){
      DoubleArray();
      N=N*2;
      f=1;r=1;
    }
    f=(f-1+N)%N;
    arr[f]=o;
  }
  public void DoubleArray(){
    Object[] arr2 = new Object[N*2];
    if(r>f){
      for(int i=0;i<size();i++){
        arr2[i]=arr[i];
      }
      arr=arr2;
    }
    else if(r<f){
      int n=size();
      for(int j=f,i=0;i<n;i++){
        arr2[i]=arr[j];
        j=(j+1)%N;
      }
      arr=arr2;
      f=0;r=f+n;
    }
  }
  public void insertLast(Object o){
    if(size()==N-1&&N!=1){
      DoubleArray();
      N=N*2;
    }
    else if(N==1){
      DoubleArray();
      N=N*2;
      f=1;r=1;
    }
    arr[r]=o;
    r=(r+1)%N;
  }
  public int size(){
    return (r-f+N)%N;
  }
  public boolean isEmpty(){
    return f==r;
  }
  public Object removeFirst(){
    Object o =0;
    o = arr[f];
    arr[f]=null;
    f=(f+1)%N;
    return o; 
  }
  public Object removeLast(){
    Object o =0;
    r=(r-1+N)%N;
    o = arr[r];
    arr[r]=null; 
    return o;
  }
  public Object first(){
    Object o=0;
    o=arr[f];
    return o;

  }
  public Object last(){
    Object o=0;
    o=arr[r-1];
    return o;

  }
  public String toString(){
    String s= "[";
    if(f<=r){
      for(int i=f;i<r-1;i++)
        s =s+arr[i]+",";
      s=s+arr[r-1]+"]";
    }
    else{
      int i=f;
      while(i!=r-1){
        s = s+arr[i]+",";
        i = (i+1)%N;
      }
      s= s+arr[r-1]+"]";
    }
    return s;
  }
  public static void main(String[] args){
    int  N = 10;
    DequeInterface myDeque = new ArrayDequeue();
    for(int i = 0; i < N; i++) {
      myDeque.insertFirst(i);
      myDeque.insertLast(-1*i);
    }
   
    int size1 = myDeque.size();
    System.out.println("Size: " + size1);
    System.out.println(myDeque.toString());
    
    if(size1 != 2*N){
      System.err.println("Incorrect size of the queue.");
    }
    
    //Test first() operation
    try{
      int first = (int)myDeque.first();
      int size2 = myDeque.size(); //Should be same as size1
      if(size1 != size2) {
        System.err.println("Error. Size modified after first()");
      }
    }
    catch (EmptyDequeException e){
      System.out.println("Empty queue");
    }
    
    //Remove first N elements
    for(int i = 0; i < N; i++) {
      try{
        int first = (Integer)myDeque.removeFirst();
      }
      catch (EmptyDequeException e) {
        System.out.println("Cant remove from empty queue");
      }
      
    }
    
    
    int size3 = myDeque.size();
    System.out.println("Size: " + myDeque.size());
    System.out.println(myDeque.toString());
    
    if(size3 != N){
      System.err.println("Incorrect size of the queue.");
    }
    
    try{
      int last = (int)myDeque.last();
      int size4 = myDeque.size(); //Should be same as size3
      if(size3 != size4) {
        System.err.println("Error. Size modified after last()");
      }
    }
    catch (EmptyDequeException e){
      System.out.println("Empty queue");
    }
    
    //empty the queue  - test removeLast() operation as well
    while(!myDeque.isEmpty()){
        try{
          int last = (int)myDeque.removeLast();
        }
        catch (EmptyDequeException e) {
          System.out.println("Cant remove from empty queue");
        }
    }
    
    int size5 = myDeque.size();
    if(size5 != 0){
      System.err.println("Incorrect size of the queue.");
    }
    
  }
  
}