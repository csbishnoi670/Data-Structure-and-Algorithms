import java.util.*;
import java.lang.*;
import java.io.*;
import java.util.Scanner;
public class FabricBreakup{
	public static void main(String[] args) {
		try{File file = new File(args[0]);
        Scanner scan = new Scanner(file);
        int n= scan.nextInt();
        // scan.nextInt();
        int max=-1,j=0,l=0,p=0,count=0;
        int[] temp1 = new int[n];
        int[] temp2 = new int[n];

        for(int i=0;i<n;i++){
        	int t=scan.nextInt();
        	if(scan.nextInt()==1){
        		
        		int score=scan.nextInt();
				if(score>=max){
					temp1[l]=max;
					temp2[l]=p;
					p=0;
					max=score;
					l++;
				}
				else{
					p++;
					}
			}
			else if(max!=-1){
				System.out.println(t +" "+p);
				max=temp1[--l];
				p=  temp2[l];
			}
			else
				System.out.println(t +" " + "-1");
       }
   }
        catch(IOException e){
        	 System.out.println("File Not Found");
        }
    }
    }