import java.util.*;
import java.io.*;


public class LinkedListImage implements CompressedImageInterface {
	public Node A[]; 
	public int m,n;
	public LinkedListImage(String filename)
	{
		try{
			File file = new File(filename);     	
        	Scanner scan = new Scanner(file);
	        m=scan.nextInt();
	        n=scan.nextInt();
	        A=new Node[m];
        	for(int i=0;i<m;i++){
        		Node node = new Node(-1,null);
        		A[i]=node;
        		Node last = new Node();
        		int flag1=0;
        		int flag2=0;
        		Node first=new Node();
        		for(int j=0;j<n;j++){
        			int t=scan.nextInt();
        			if(t==0&&flag2==0&&j!=n-1){
        				Node node1=new Node();
        				node1.setElement(j);
        				first=node1;
        				if(flag1==0){
        					A[i]=node1;
        					flag1=1;
        				}
        				else
        					last.setNext(first);
        				flag2=1;
        			}
        			else if(t==1&&flag2==1){
        				Node node2=new Node(j-1,null);
        				first.setNext(node2);
        				last=node2;
        				flag2=0;
        			}
        			else if(j==n-1&&flag2==1){
        				Node node2=new Node(j,null);
        				first.setNext(node2);
        				last=node2;
        			}
                    else if(j==n-1&&flag2==0&&t==0){
                        Node node1=new Node();
                        node1.setElement(j);
                        first=node1;
                        last.setNext(first);
                        Node node2=new Node(j,null);
                        first.setNext(node2);
                        last=node2;
                    }
        		}
        		last.setNext(node);
        	}	 
	    }
        catch(IOException e){
        	 System.out.println("File Not Found");
        }
	}

    public LinkedListImage(boolean[][] grid, int width, int height)
    {
		m=height;
		n=width;
		A=new Node[m];	        	
		for(int i=0;i<m;i++){
			Node node = new Node(-1,null);
			A[i]=node;
			Node last = new Node();
			int flag1=0;
			int flag2=0;
			Node first=new Node();
			for(int j=0;j<n;j++){
				boolean t=grid[i][j];
				if(t==false&&flag2==0){
					Node node1=new Node();
					node1.setElement(j);
					first=node1;
					if(flag1==0){
						A[i]=node1;
						flag1=1;
					}
					else
						last.setNext(first);
					flag2=1;
				}
				else if(t==true&&flag2==1){
					Node node2=new Node(j-1,null);
					first.setNext(node2);
					last=node2;
					flag2=0;
				}
				else if(j==n-1&&flag2==1){
					Node node2=new Node(j,null);
					first.setNext(node2);
					last=node2;
				}
			}
			last.setNext(node);
		}
    }
    public LinkedListImage(Node[] c){
    	Node[] d = new Node[c.length];
    	m=c.length;
    	n=m;
    	for(int x=0;x<m;x++){
    		Node currentC = c[x];
    		Node currentD = null;
    		boolean start=false;

    		while(currentC.getElement()!=-1){
    			if(start==false){
    				Node d1 = new Node(currentC.getElement(),null);
    				d[x]=d1;
    				currentD=d[x];
    				start = true;
    			}
    			else if(start==true){
    				Node d1 = new Node(currentC.getElement(),null);
    				currentD.setNext(d1);
    				currentD=currentD.getNext();
    			}
    			Node d2 = new Node(currentC.getNext().getElement(),null);
    			currentD.setNext(d2);
    			currentD=currentD.getNext();
    			currentC=currentC.getNext().getNext();
    		}
    		if(currentD==null){
    			Node d1 = new Node(-1,null);
    			d[x]=d1;
    			currentD=d[x];
    		}
    		else{
    			Node d2 = new Node(-1,null);
    			currentD.setNext(d2);
    		}
    	}
    	A=d;
    }

    public boolean getPixelValue(int x, int y) throws PixelOutOfBoundException
    {
        if(x>=m||y>=n||x<0||y<0){
            throw new PixelOutOfBoundException("");
        }
        else{
        	Node current=A[x];
    		while(current.next!=null){
    			if(y>=current.element&&y<=current.next.element)
    				return false;
    			current=current.next.next;
    		}
    		return true;
        }
    }

    public void setPixelValue(int x, int y, boolean val) throws PixelOutOfBoundException
    {
        if(x>=m||y>=n||x<0||y<0){
            throw new PixelOutOfBoundException("");
        }
        else{
    		Node current=A[x];
            Node previous=current;
    		while(current.next!=null){
    			if(y>=current.element&&y<=current.next.element&&val==false)
    			{
    				break;
    			}
    			else if((y<current.element||y>current.next.element)&&val==true)
    			{	
    				break;
    			}
    			else if(y>current.element&&y<current.next.element&&val==true){
    				Node add1 = new Node(y-1,current.next);
    				current.next=add1;
    				current=current.next;
    				Node add2 = new Node(y+1,current.next);
    				current.next=add2;
    				break;
    			}
    			else if(current.element==current.next.element&&y==current.element&&val==true){
    				if(previous==current)
                        A[x]=current.next.next;
                    else
                        previous.next=current.next.next;
    				break;
    			}
    			else if(y==current.element&&val==true){
    				current.element=y+1;
    				break;
    			}
    			else if(y==current.next.element&&val==true){
    				current.next.element=y-1;
    				break;
    			}
    			else if(current.element>(y+1)&&val==false){
    				Node add1 = new Node(y,current);
    				current=add1;
    				Node add2 = new Node(y,add1);
    				A[x]=add2;
    				break;
    			}
    			else if(y>current.next.element+1&&val==false&&y<current.next.next.element){
    				if(current.next.next.element==y+1){
    					current.next.next.element=y;
    				}
    				
    				else{
    					Node add1 = new Node(y,current.next.next);
    					current.next.next=add1;
    					current=current.next;
    					Node add2 = new Node(y,current.next.next);
    					current.next.next=add2;
    				}
    				break;
    			}
                else if(y>current.next.element+1&&val==false&&current.next.next.element==-1){
                    Node add1 = new Node(y,current.next.next);
                    current.next.next=add1;
                    current=current.next;
                    Node add2 = new Node(y,current.next.next);
                    current.next.next=add2;
                    break;
                }
    			else if(current.element==(y+1)&&val==false){
    				current.element=y;
    				break;
    			}
    			else if(current.next.element==(y-1)&&val==false){
                    if(current.next.next.element==y+1&&current.next.next.element!=-1)
                        current.next=current.next.next.next;
                    else
    				    current.next.element=y;
    				break;
    			}
                previous=current;
    			current=current.next.next;
        	}
        }
    }

    public int[] numberOfBlackPixels()
    {
		int arr[]=new int[m];
		for(int i=0;i<m;i++){
			Node current=A[i];
			int num=0;
			while(current.next!=null){
				num=num+(current.next.element-current.element)+1;
				current=current.next.next;
			}
			arr[i]=num;
		}
		return arr;
    }
    
    public void invert()
    {
		Node B[]=new Node[m];
    	for(int x=0;x<m;x++){
			Node currentA = A[x];
			Node currentB = null;
			boolean start=false,end=false;
			while(currentA.next!=null){
				if(currentA.next.next.next==null&&end==false){
					end=true;
				}
				if(start==false){
					if(currentA.element!=0){
						Node f = new Node(0,null);
						B[x] = f;
						currentB = B[x];
						Node f2 = new Node(currentA.element-1,null);
						currentB.next=f2;
						currentB=currentB.next;
						start=true;
					}
					else{
						if(currentA.next.element==n-1){
							break;
						}
						else{
							Node f = new Node(currentA.next.element+1,null);
							B[x] = f;
							currentB = B[x];
							if(end==false){
								Node f2 = new Node(currentA.next.next.element-1,null);
								currentB.next=f2;
							}
							else{
								Node f2 = new Node(n-1,null);
								currentB.next=f2;
							}
							currentB=currentB.next;
							currentA = currentA.next.next;
							start=true;
						}
					}
				}
				if(currentA.next==null){
					break;
				}
				else{
					if(currentA.next.next.element==-1){
						end=true;
					}
				}
				
				if(start==true&&end==true){
					if(currentA.next.element!=n-1){
						Node f = new Node(currentA.next.element+1,null);
						currentB.next=f;
						currentB = currentB.next;
						if(currentA.next.element==n-2){
							Node f2 = new Node(currentA.next.element+1,null);
							currentB.next=f2;
						}
						else{
							Node f3 = new Node(n-1,null);
							currentB.next=f3;

						}
						currentB = currentB.next; 
						break;
					}
					else{
                        
						break;
                     }	
					
				}
				if(start==true&&end==false){
		
					Node f = new Node(currentA.next.element+1,null);
					currentB.next=f;
					currentB=currentB.next;
					Node f2 = new Node(currentA.next.next.element-1,null);
					currentB.next=f2;
					currentB=currentB.next;
				}
				currentA=currentA.next.next;
    		}
	    	if(currentA.next==null&&currentB==null){
	    		Node f = new Node(0,null);
	    		B[x]=f;
	    		currentB=B[x];
	    		Node f2 = new Node(n-1,null);
	    		currentB.next=f2;
	    		currentB=currentB.next; 
	    	}
	    	Node f4 = new Node(-1,null);
	    	if(currentB==null){
	    	B[x]=f4;
	    	}
	    	else
	    		currentB.next=f4;
	    	A[x]=B[x];
    	}
    }
    
    public void performAnd(CompressedImageInterface img) throws BoundsMismatchException
    {
        LinkedListImage img1 = (LinkedListImage)img;
        if(m!=img1.m||n!=img1.n){
            throw new BoundsMismatchException("");
        }
        else{
        	
    		this.invert();
        	img.invert();
        	this.performOr(img);
        	this.invert();
        	img.invert();
        }
    }
    
    public void performOr(CompressedImageInterface img) throws BoundsMismatchException
    {
        LinkedListImage img1 = (LinkedListImage)img;
        if(m!=img1.m||n!=img1.n){
            throw new BoundsMismatchException("");
        }
        else{
    		Node B[] = new Node[m];
        	for(int i=0;i<m;i++){
        		Node current1 = A[i];
        		Node current2 = img1.A[i];
        		Node currentB = null;
        		boolean start = false;
        		while(current1.next!=null&&current2.next!=null){
    	    		int max1 = max(current1.element,current2.element);
    	    		int min1 = min(current1.next.element,current2.next.element);
    	    		if(max1<=min1){
    	    			if(start==false){
    	    				Node max0 = new Node(max1,null);
    	    				B[i] = max0;
    	    				currentB = B[i];
    	    				start = true;
    	    			}
    	    			else{
    	    				Node max0 = new Node(max1,null);
    	    				currentB.next = max0;
    	    				currentB = currentB.next;
    	    			}
    	    			Node min0 = new Node(min1,null);
    	    			currentB.next = min0;
    	    			currentB = currentB.next;
    	    			if(current1.next.element<current2.next.element)
    	    				current1 = current1.next.next;
    	    			else if(current1.next.element>current2.next.element)
                            current2 = current2.next.next;
                        else{
                            current1 = current1.next.next;
                            current2 = current2.next.next;
                        }
    	    		}
    	    		else if(max1>min1){

    	    			if(current2.next.element<current1.element){
    	    				if(current2.next.next==null)
    	    					break;
    	    				else
    	    					current2=current2.next.next;
    	    			}
    	    			else if(current2.element>current1.next.element){
    	    				if(current1.next.next==null)
    	    					break;
    	    				else
    	    					current1 = current1.next.next;
    	    			}
    	    		}
    	    	}
    	    	Node f4 = new Node(-1,null);
    	    	if(currentB==null)
    	    		B[i] = f4;
    	    	else
    	    		currentB.next = f4;
    	    	A[i] = B[i];
        	}
        }

    }
    public int max(int a,int b){
    	if(a>=b)
    		return a;
    	else
    		return b;
    }
    public int min(int a,int b){
    	if(a>=b)
    		return b;
    	else
    		return a;
    }
    
    public void performXor(CompressedImageInterface img) throws BoundsMismatchException
    {
    	LinkedListImage img1 = (LinkedListImage)img;
        if(m!=img1.m||n!=img1.n){
            throw new BoundsMismatchException("");
        }
        else{
    		LinkedListImage a = new LinkedListImage(img1.A);
        	LinkedListImage b = new LinkedListImage(this.A);
        	this.invert();
        	this.performAnd(img1);
        	a.invert();
        	a.performAnd(b);
        	this.performOr(a); 
        }
    }
    
    public String toStringUnCompressed()
    {
        StringBuilder s= new StringBuilder();
        s.append(m+" "+n);
		for(int i=0;i<m;i++){
			Node current = A[i];
			if(A[i].element==-1){
				s.append(",");
				for(int j=0;j<n;j++)
					s.append(" 1");
			}
			else{
				s.append(",");
				for(int j=0;j<current.element;j++){
					s.append(" 1");
				}

				while(current.element!=-1){
						for(int j=current.element;j<current.next.element+1;j++)
							s.append(" 0");
						if(current.next.next.element==-1){
							//s=s+",";
							for(int j=current.next.element+1;j<n;j++)
								s.append(" 1");
							break;
						}
						else if(current.next.next.element!=-1){
							for(int j=current.next.element+1;j<current.next.next.element;j++)
								s.append(" 1");
							current=current.next.next;

						}
					
				}
			}

		}
		return s.toString();
    }
    
    public String toStringCompressed()
    {
        StringBuilder s = new StringBuilder();
		s.append(m+" "+n);
		for(int i=0;i<m;i++){
			Node current = A[i];
			s.append(",");
			while(current.element!=-1){
				s.append(" "+current.element + " "+current.next.element);
				current=current.next.next;
			}
			s.append(" -1");
		}
		return s.toString();
    }

    public static void main(String[] args) {
    	// testing all methods here :
    	boolean success = true;

    	// check constructor from file
    	CompressedImageInterface img1 = new LinkedListImage("sampleInputFile.txt");

    	// check toStringCompressed
    	String img1_compressed = img1.toStringCompressed();
    	String img_ans = "16 16, -1, 5 7 -1, 3 7 -1, 2 7 -1, 2 2 6 7 -1, 6 7 -1, 6 7 -1, 4 6 -1, 2 4 -1, 2 3 14 15 -1, 2 2 13 15 -1, 11 13 -1, 11 12 -1, 10 11 -1, 9 10 -1, 7 9 -1";
    	success = success && (img_ans.equals(img1_compressed));

    	if (!success)
    	{
    		System.out.println("Constructor (file) or toStringCompressed ERROR");
    		return;
    	}

    	// check getPixelValue
    	boolean[][] grid = new boolean[16][16];
    	for (int i = 0; i < 16; i++)
    		for (int j = 0; j < 16; j++)
    		{
                try
                {
        			grid[i][j] = img1.getPixelValue(i, j);                
                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
    		}

    	// check constructor from grid
    	CompressedImageInterface img2 = new LinkedListImage(grid, 16, 16);
    	String img2_compressed = img2.toStringCompressed();
    	success = success && (img2_compressed.equals(img_ans));

    	if (!success)
    	{
    		System.out.println("Constructor (array) or toStringCompressed ERROR");
    		return;
    	}

    	// check Xor
        try
        {
        	img1.performXor(img2);        
        }
        catch (BoundsMismatchException e)
        {
            System.out.println("Errorrrrrrrr");
        }
    	for (int i = 0; i < 16; i++)
    		for (int j = 0; j < 16; j++)
    		{
                try
                {
        			success = success && (!img1.getPixelValue(i,j));                
                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
    		}

    	if (!success)
    	{
    		System.out.println("performXor or getPixelValue ERROR");
    		return;
    	}

    	// check setPixelValue
    	// System.out.println(img1.toStringCompressed());
    	for (int i = 0; i < 16; i++)
        {
            try
            {
    	    	img1.setPixelValue(i, 0, true);            
            }
            catch (PixelOutOfBoundException e)
            {
                System.out.println("Errorrrrrrrr");
            }
        }
        //System.out.println(img1.toStringCompressed());
         //System.out.println(Arrays.toString(img1.numberOfBlackPixels()));
    	// check numberOfBlackPixels
    	int[] img1_black = img1.numberOfBlackPixels();
    	success = success && (img1_black.length == 16);
    	for (int i = 0; i < 16 && success; i++)
    		success = success && (img1_black[i] == 15);
    	if (!success)
    	{
    		System.out.println("setPixelValue or numberOfBlackPixels ERROR");
    		return;
    	}


    	// check invert
        img1.invert();
        for (int i = 0; i < 16; i++)
        {
            try
            {
                success = success && !(img1.getPixelValue(i, 0));            
            }
            catch (PixelOutOfBoundException e)
            {
                System.out.println("Errorrrrrrrr");
            }
        }
        if (!success)
        {
            System.out.println("invert or getPixelValue ERROR");
            return;
        }
        // System.out.println(img1.toStringCompressed());
        // System.out.println(img2.toStringCompressed());
    	// check Or

    	

        try
        {
            img1.performOr(img2);
            //System.out.println(img1.toStringCompressed());        
        }
        catch (BoundsMismatchException e)
        {
            System.out.println("Errorrrrrrrr");
        }
        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 16; j++)
            {
                try
                {
                	//System.out.print(" "+success);
                    success = success && img1.getPixelValue(i,j);

                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
            }
        if (!success)
        {
            System.out.println("performOr or getPixelValue ERROR");
            return;
        }
       
        // check And
        try
        {

            img1.performAnd(img2);
            

        }
        catch (BoundsMismatchException e)
        {
            System.out.println("Errorrrrrrrr");
        }
        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 16; j++)
            {
                try
                {
                    success = success && (img1.getPixelValue(i,j) == img2.getPixelValue(i,j));             
                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
            }
        if (!success)
        {
            System.out.println("performAnd or getPixelValue ERROR");
            return;
        }

    	// check toStringUnCompressed
        String img_ans_uncomp = "16 16, 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1, 1 1 1 1 1 0 0 0 1 1 1 1 1 1 1 1, 1 1 1 0 0 0 0 0 1 1 1 1 1 1 1 1, 1 1 0 0 0 0 0 0 1 1 1 1 1 1 1 1, 1 1 0 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 0 0 0 1 1 1 1 1 1 1 1 1, 1 1 0 0 0 1 1 1 1 1 1 1 1 1 1 1, 1 1 0 0 1 1 1 1 1 1 1 1 1 1 0 0, 1 1 0 1 1 1 1 1 1 1 1 1 1 0 0 0, 1 1 1 1 1 1 1 1 1 1 1 0 0 0 1 1, 1 1 1 1 1 1 1 1 1 1 1 0 0 1 1 1, 1 1 1 1 1 1 1 1 1 1 0 0 1 1 1 1, 1 1 1 1 1 1 1 1 1 0 0 1 1 1 1 1, 1 1 1 1 1 1 1 0 0 0 1 1 1 1 1 1";
        success = success && (img1.toStringUnCompressed().equals(img_ans_uncomp)) && (img2.toStringUnCompressed().equals(img_ans_uncomp));

        if (!success)
        {
            System.out.println("toStringUnCompressed ERROR");
            return;
        }
        else
            System.out.println("ALL TESTS SUCCESSFUL! YAYY!");
    }
}