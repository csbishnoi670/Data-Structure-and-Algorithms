import java.io.*;
import java.io.IOException;
import java.util.Scanner;
import java.util.*;
import java.util.ArrayList;
class HashNode {
	ArrayList<String> array;
	
	public HashNode(){
		array = new ArrayList<String>();
	}
}
public class Anagram {
	HashNode[] HashTable ;
	int TableSize;;
	public void createTable() {
		HashTable =  new HashNode[100019];
		TableSize = 100019;
	}
	public void hash(String s) {
		int r=0;
		String s2 = Sort(s);
		//System.out.println(s2);
		r = HashIndex(s2);
		
		if(HashTable[r]==null){
			HashNode node = new HashNode();
			node.array.add(s);
			HashTable[r]=node;
		}
		else{
			HashTable[r].array.add(s);
			//System.out.println(s);
		}
	}
	public void Anag(String s){
		if(s.length()<13){
			String s2 = Sort(s);
			int r=HashIndex(s2);
			ArrayList<String> set=new ArrayList<String>();
			if(HashTable[r]!=null){
				int size = HashTable[r].array.size();
				for(int j=0;j<size;j++){
				    String s3 =HashTable[r].array.get(j);
				    set.add(s3);
				}
			}
			if(s2.length()>=6){
				String snew = "";
				Combination(s2,set,snew);
			}
			Collections.sort(set);
			ArrayList<String> set2 = deleteDuplicate(set);
			for(String counter: set2){
					System.out.println(counter);
			}
			System.out.println("-1");
		}
		
	}
	
	public void Combination(String s ,ArrayList<String> set,String snew){
		for(int i=0;i<s.length();i++){
				for(int j=i+1;j<s.length();j++){
					for(int l=j+1;l<s.length();l++){
						StringBuilder sb1 = new StringBuilder();
						sb1.append(s.charAt(i)).append(s.charAt(j)).append(s.charAt(l));
						StringBuilder sb2 = new StringBuilder(s);
						sb2.deleteCharAt(l).deleteCharAt(j).deleteCharAt(i);
						String s1 = sb1.toString();
						String s2 = sb2.toString();
						if(Find(s1)&&Find(s2)){
							addSet(s1,s2,snew,set);
						}
						if(Find(s1)&&s2.length()>5&&snew.length()==0){
							Combination(s2,set,s1);
						}
						if(s.length()>7){
							for(int m=l+1;m<s.length();m++){
								StringBuilder sb3 = new StringBuilder();
								sb3.append(s.charAt(i)).append(s.charAt(j)).append(s.charAt(l)).append(s.charAt(m));
								StringBuilder sb4 = new StringBuilder(s);
								sb4.deleteCharAt(m).deleteCharAt(l).deleteCharAt(j).deleteCharAt(i);
								String s3 = sb3.toString();
								String s4 = sb4.toString();
								if(Find(s3)&&Find(s4)){
								addSet(s3,s4,snew,set);
								}
								if(Find(s3)&&s4.length()>5&&snew.length()==0){
									Combination(s4,set,s3);
								}
								if(s.length()>9){
									for(int n=m+1;n<s.length();n++){
										StringBuilder sb5 = new StringBuilder();
										sb5.append(s.charAt(i)).append(s.charAt(j)).append(s.charAt(l)).append(s.charAt(m)).append(s.charAt(n));
										StringBuilder sb6 = new StringBuilder(s);
										sb6.deleteCharAt(n).deleteCharAt(m).deleteCharAt(l).deleteCharAt(j).deleteCharAt(i);
										String s5 = sb5.toString();
										String s6 = sb6.toString();
										if(Find(s5)&&Find(s6)){
										addSet(s5,s6,snew,set);
										}
										if(Find(s5)&&s6.length()>5&&snew.length()==0){
											Combination(s6,set,s5);
										}
										if(s.length()>11){
											for(int o=n+1;o<s.length();o++){
												StringBuilder sb7 = new StringBuilder();
												sb7.append(s.charAt(i)).append(s.charAt(j)).append(s.charAt(l)).append(s.charAt(m)).append(s.charAt(n)).append(s.charAt(o));
												StringBuilder sb8 = new StringBuilder(s);
												sb8.deleteCharAt(o).deleteCharAt(n).deleteCharAt(m).deleteCharAt(l).deleteCharAt(j).deleteCharAt(i);
												String s7 = sb7.toString();
												String s8 = sb8.toString();
												if(Find(s7)&&Find(s8)){
												addSet(s7,s8,snew,set);
												}
												if(Find(s7)&&s8.length()>5&&snew.length()==0){
													Combination(s8,set,s7);
												}
												if(o!=s.length()-1&&s.charAt(o)==s.charAt(o+1))
													o++;
											}
										}
										if(n!=s.length()-1&&s.charAt(n)==s.charAt(n+1))
											n++;
									}
								}
								if(m!=s.length()-1&&s.charAt(m)==s.charAt(m+1))
									m++;
							}	
						}
						if(l!=s.length()-1&&s.charAt(l)==s.charAt(l+1))
							l++;
					}
					if(j!=s.length()-1&&s.charAt(j)==s.charAt(j+1))
						j++;
				}
				if(i!=s.length()-1&&s.charAt(i)==s.charAt(i+1))
					i++;
			}
	}
	public void addSet(String s1,String s2,String snew,ArrayList<String> set){
		if(snew.equals("")){
			int size1 = HashTable[HashIndex(s1)].array.size();
			int size2 = HashTable[HashIndex(s2)].array.size();
			for(int i=0;i<size1;i++){
				for(int j=0;j<size2;j++){
					StringBuilder sB = new StringBuilder(HashTable[HashIndex(s1)].array.get(i)+" "+HashTable[HashIndex(s2)].array.get(j));
					set.add(sB.toString());
					if(s1.length()!=s2.length()){
						StringBuilder sB2 = new StringBuilder(HashTable[HashIndex(s2)].array.get(j)+" "+HashTable[HashIndex(s1)].array.get(i));
						set.add(sB2.toString());
					}

				}
			}
		}
		else{
			int size1 = HashTable[HashIndex(snew)].array.size();
			int size2 = HashTable[HashIndex(s1)].array.size();
			int size3 = HashTable[HashIndex(s2)].array.size();
			for(int i=0;i<size1;i++){
				for(int j=0;j<size2;j++){
					for(int k=0;k<size3;k++){
						StringBuilder sB1 = new StringBuilder(HashTable[HashIndex(snew)].array.get(i)+" "+HashTable[HashIndex(s1)].array.get(j)+" "+HashTable[HashIndex(s2)].array.get(k));
						set.add(sB1.toString());
						if(s1.length()!=s2.length()){
							StringBuilder sB2 = new StringBuilder(HashTable[HashIndex(snew)].array.get(i)+" "+HashTable[HashIndex(s2)].array.get(k)+" "+HashTable[HashIndex(s1)].array.get(j));
							set.add(sB2.toString());
						}
						if(snew.length()!=s1.length()){
							StringBuilder sB3 = new StringBuilder(HashTable[HashIndex(s1)].array.get(j)+" "+HashTable[HashIndex(snew)].array.get(i)+" "+HashTable[HashIndex(s2)].array.get(k));
							set.add(sB3.toString());
							StringBuilder sB4 = new StringBuilder(HashTable[HashIndex(s2)].array.get(k)+" "+HashTable[HashIndex(snew)].array.get(i)+" "+HashTable[HashIndex(s1)].array.get(j));
							set.add(sB4.toString());
						}
						if(snew.length()!=s2.length()){
							StringBuilder sB5 = new StringBuilder(HashTable[HashIndex(s1)].array.get(j)+" "+HashTable[HashIndex(s2)].array.get(k)+" "+HashTable[HashIndex(snew)].array.get(i));
							set.add(sB5.toString());
							StringBuilder sB6 = new StringBuilder(HashTable[HashIndex(s2)].array.get(k)+" "+HashTable[HashIndex(s1)].array.get(j)+" "+HashTable[HashIndex(snew)].array.get(i));
							set.add(sB6.toString());
						}
					}
					
				}
			}
		}
	}
	public boolean Find(String s){
		int r = HashIndex(s);
		if(HashTable[r]==null){
			return false;
		}
		else if(Sort(HashTable[r].array.get(0)).equals(s)){
			return true;
		}
		else{
			return false;
		}
	}
	public String Sort(String s){
		char[] arrayOfstring = s.toCharArray();
		Arrays.sort(arrayOfstring) ;
		
		return new String(arrayOfstring); 
	}
	public int HashIndex(String s2){
		int r=0;
		for(int i=0;i<s2.length();i++) {
			r=(s2.charAt(i)+r*33)%TableSize;
		}
		if(HashTable[r]==null)
			return r;
		else if(HashTable[r]!=null&&Sort(HashTable[r].array.get(0)).equals(s2)){
			return r;
		}
		else{
			while(HashTable[r]!=null&&!Sort(HashTable[r].array.get(0)).equals(s2)){
				r=(r+1)%TableSize;
			}
			return r;
		}
	}
	public ArrayList<String> deleteDuplicate(ArrayList<String> set){
		int siz = set.size();
		ArrayList<String> set2 =new ArrayList<String>();
		for(int i=0;i<siz;i++){
			if(i<siz-1){
				if(!set.get(i).equals(set.get(i+1))){
					set2.add(set.get(i));
				}
				
			}
			else if(i==siz-1){
				set2.add(set.get(i));
			}
		}
		return set2;
	}

	public static void main(String[] args) {
		try {
			long startTime=System.currentTimeMillis();
			FileReader fr=new FileReader(args[0]);    
          	BufferedReader br=new BufferedReader(fr); 
	        Anagram obj = new Anagram();
	        int Tablesize =  Integer.parseInt(br.readLine());
	        obj.createTable();
	        for(int i=0;i<Tablesize;i++) {
		        String s = br.readLine();
		        obj.hash(s);
	        }
	        FileReader fr2=new FileReader(args[1]);    
          	BufferedReader br2=new BufferedReader(fr2); 
	        int n = Integer.parseInt(br2.readLine());
	        //br2.readLine();
	        for(int i=0;i<n;i++){
	        	String s2 = br2.readLine();
	        	obj.Anag(s2);
	        } 
	      
		}
		catch(IOException e){
       	 System.out.println("File Not Found");
       }
		
	}
}
