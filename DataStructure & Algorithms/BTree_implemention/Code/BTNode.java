package col106.a3;
import java.util.ArrayList;
import java.util.List;
public class BTNode<Key extends Comparable<Key>,Value>{
	ArrayList<Position<Key,Value>> array ;
	BTNode<Key,Value> parent;
	Position<Key,Value> parentposR;
	Position<Key,Value> parentposL;
	public int numkey;
	public BTNode(){
		this.parent=null;
		this.parentposL=null;
		this.parentposR=null;
		numkey=0;
		array = new ArrayList<Position<Key,Value>>();
	}
	public BTNode(BTNode<Key,Value> parent){
		this.parent = parent;
		numkey = 0;
		array = new ArrayList<Position<Key,Value>>();
	}
	public BTNode<Key,Value> nextNode(Key key){
		for(int i=0;i<numkey;i++){
			Key key2=array.get(i).key;
			if(key2.compareTo(key)>0)
				return array.get(i).left;
		}
		return array.get(numkey-1).right;
		
	}
	public boolean IsExternal(){
		if(numkey==0)
			return true;
		else if(array.get(0).left==null&&array.get(0).right==null)
			return true;
		else
			return false;
	}
	public BTNode<Key,Value> split(Key key){
		int mid = numkey/2;
		if(parent==null){
			BTNode<Key,Value> node2 = new BTNode<Key,Value>();
			BTNode<Key,Value> node3 = new BTNode<Key,Value>();
			node3.set(array,0,mid-1);
			node3.numkey=mid;
			node3.setParent();
			BTNode<Key,Value> node4 = new BTNode<Key,Value>();
			node4.set(array,mid+1,numkey-1);
			node4.numkey=numkey-mid-1;
			node4.setParent();
			return node2.set1(array.get(mid).key,array.get(mid).val,node3,node4,key);
		}
		else {
			
			BTNode<Key,Value> node3 = new BTNode<Key,Value>();
			node3.set(array,0,mid-1);
			node3.numkey=mid;
			node3.setParent();
			BTNode<Key,Value> node4 = new BTNode<Key,Value>();
			node4.set(array,mid+1,numkey-1);
			node4.numkey=numkey-mid-1;
			node4.setParent();
			return parent.set2(array.get(mid).key,array.get(mid).val,node3,node4,key);
		}
	}
	public BTNode<Key,Value> set1(Key key,Value val,BTNode<Key,Value> left,BTNode<Key,Value> right,Key key1){
		Position<Key,Value> pos = new Position<Key,Value>(key,val,left,right);
		array.add(pos);
		numkey++;
		left.parentposR=pos;
		left.parent = this;
		
		right.parentposL=pos;
		right.parent=this;
		
		if(key.compareTo(key1)>0) {
			return left;
		}
		else {
			return right;
		}
		
	}
	public BTNode<Key,Value> set2(Key key,Value val,BTNode<Key,Value> left,BTNode<Key,Value> right,Key key1){
		Position<Key,Value> pos = new Position<Key,Value>(key,val,left,right);
		
		int i=findIndex(key);
		array.add(i,pos);
		if(i>0) {
			array.get(i-1).right=left;
			left.parentposL=array.get(i-1);
		}
		if(i<numkey) {
			array.get(i+1).left=right;
			right.parentposR=array.get(i+1);
		}
		left.parentposR = pos;
		left.parent=this;
		
		right.parentposL = pos;
		right.parent=this;
		numkey++;
		if(key.compareTo(key1)>0) {
			return left;
		}
		else {
			return right;
		}
		
	}
	public void set(ArrayList<Position<Key,Value>> array2,int m,int n){
		for(int i=m;i<=n;i++){
			array.add(array2.get(i));
		}
	}
	public void setParent() {
		if(array.get(0).left!=null) {
			for(int i=0;i<numkey;i++) {
				array.get(i).left.parent=this;
			}
		array.get(numkey-1).right.parent=this;
		}
		return;
	}
	public void insertKey(Key key,Value val){
		Position<Key,Value> pos = new Position<Key,Value>(key,val,null,null);
		if(numkey!=0) {
			for(int i=0;i<numkey;i++){
				if((array.get(i).key).compareTo(key)>0){
					array.add(i,pos);
					numkey++;
					return;
				}
	
			}
		}
		array.add(pos);
		numkey++;
		return;
	}
	public void Findkey(Key key,List<Value> l) {
		for(int i=0;i<numkey;i++) {
			Key key2=array.get(i).key;
			Value val2 = array.get(i).val;
			if(key2.compareTo(key)==0)
				l.add(val2);
			else if(key2.compareTo(key)>0)
				return;
		}
	}
	public BTNode<Key,Value> Right(){
		return array.get(numkey-1).right;
		
	}
	public BTNode<Key,Value> Left(){
		return array.get(0).left;
	}
	public boolean IsKey(Key key) {
		for(int i=0;i<numkey;i++){
			Key key2=array.get(i).key;
			if(key2.compareTo(key)==0)
				return true;
		}
		return false;
	}
	public BTNode<Key,Value> findNext(Key key){
		for(int i=0;i<numkey;i++){
			Key key2=array.get(i).key;
			if(key2.compareTo(key)>0)
				return array.get(i).left;
		}
		return array.get(numkey-1).right;
	}
	public BTNode<Key,Value> KeyLeft(Key key){
		for(int i=0;i<numkey;i++){
			Key key2=array.get(i).key;
			if(key2.compareTo(key)==0)
				return array.get(i).left;
		}
		return array.get(numkey-1).right;
	}
	public Position<Key,Value> Findpos(Key key){
		for(int i=0;i<numkey;i++){
			Key key2=array.get(i).key;
			if(key2.compareTo(key)==0)
				return array.get(i);
		}
		return array.get(0);
	}
	public int findIndex(Key key){
		for(int i=0;i<numkey;i++){
			Key key2=array.get(i).key;
			if(key2.compareTo(key)>0)
				return i;
		}
		return numkey;
	}
	public void printNode(StringBuilder s) {
		for(int i=0;i<numkey-1;i++) {
			s.append(array.get(i).key + "=" + array.get(i).val + ",");
		}
		s.append(array.get(numkey-1).key + "=" + array.get(numkey-1).val);
	}
	public void deletekey(Key key) {
		for(int i=0;i<numkey;i++) {
			Key key2=array.get(i).key;
			if(key2.compareTo(key)==0) {
				array.remove(i);
				numkey--;
				return;
			}
		}
	}

	public void rotateL(Key key) {
		Position<Key,Value> ppos=parentposL;
		ArrayList<Position<Key,Value>> array2=ppos.left.array;
		Position<Key,Value> posc = new Position<Key,Value>(ppos.key,ppos.val,array2.get(array2.size()-1).right,array.get(0).left);
		ppos.key=array2.get(array2.size()-1).key;
		array.add(0,posc);
		if(posc.left!=null) {
			posc.left.parentposR=posc;
			posc.right.parentposL=posc;
			posc.left.parentposL=null;
			array2.get(array2.size()-2).right.parentposR=null;
		}
		array2.remove(array2.size()-1);
		numkey++;
		ppos.left.numkey--;
   }
	 public void rotateR(Key key) {
		 Position<Key,Value> ppos = parentposR;
		 ArrayList<Position<Key,Value>> array2 = ppos.right.array;
		 Position<Key,Value> posc = new Position<Key,Value>(ppos.key,ppos.val,array.get(array.size()-1).right,array2.get(0).left);
		 ppos.key=array2.get(0).key;
		 array.add(array.size(),posc);
		 if(posc.right!=null) {
			 	posc.right.parentposL=posc;
				posc.left.parentposR=posc;
				posc.right.parentposR=null;
				array2.get(1).left.parentposL=null;
		 }
		 array2.remove(0);
		 numkey++;
		 ppos.right.numkey--;
		 
	 }
	 
}