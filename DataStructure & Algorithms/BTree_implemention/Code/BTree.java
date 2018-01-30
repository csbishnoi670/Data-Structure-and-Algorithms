package col106.a3;

import java.util.List;
import java.util.ArrayList;

public class BTree<Key extends Comparable<Key>,Value> implements DuplicateBTree<Key,Value> {
    private BTNode<Key,Value> root=null ;
    private int t=0;
    int size;

    public BTree(int b) throws bNotEvenException {  /* Initializes an empty b-tree. Assume b is even. */
        if(b%2!=0)
            throw new bNotEvenException();
        else{
            t=b/2;
            BTNode<Key,Value> node1 = new BTNode<Key,Value>();
            root=node1;
        }
    }

    @Override
    public boolean isEmpty() {
        return root==null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int height() {
        return heightTree(root);
    }
    public int heightTree(BTNode<Key,Value> node){
    	if(node.IsExternal()) {
    		return 0;
    	}
    	else {
    		return 1+heightTree(node.Left());
    	}
    }

    @Override
    public List<Value> search(Key key) throws IllegalKeyException {
        List<Value> l = new ArrayList<Value>();
        searchNode(key,root,l);
        if(l.size()==0) {
        	throw new IllegalKeyException();
        }
        return l;
    }

    @Override
    public void insert(Key key, Value val) {
            traverse(key,root,val);
            size++;
            return;
    }

   // @Override
    
public void delete(Key key) throws IllegalKeyException {
	
		search(key);
    	delete(key,root);
    	size--;
    	return;
    }
	public void delete(Key key,BTNode<Key,Value> root) {
		BTNode<Key,Value> node1;
    	node1=FindNode(key,root);
    	if(node1.IsExternal()) {
    		node1.deletekey(key);
    		return;
    	}
    	else if(!node1.IsExternal()) {
	    	BTNode<Key,Value> node2=Inorder(key,node1);
	    	swapKey(node1,node2,key);
	    	node2.deletekey(key);
	    	return;
    	}
	}
	public String toString() {
		return printTree(root);
	}
	public String printTree(BTNode<Key,Value> root) {
		StringBuilder s = new StringBuilder(); 
		Inorderprint(root,s);
		
		return s.toString();
	}
	public void Inorderprint(BTNode<Key,Value> node,StringBuilder s) {
		s.append("[");
		if(node.IsExternal()) {
			node.printNode(s);
		}
		
		else {
			for(int i=0;i<node.numkey;i++) {
				Inorderprint(node.array.get(i).left,s);
				s.append(","+node.array.get(i).key+"="+node.array.get(i).val+",");
			}
			
			Inorderprint(node.array.get(node.numkey-1).right,s);
			
		}
		s.append("]");
	}
    public void traverse(Key key,BTNode<Key,Value> node,Value val){
            if(node.numkey==2*t-1)
            {
                BTNode<Key,Value> node1=node.split(key);
                if(node1.parent.parent==null) {
                	root=node1.parent;
                }
                traverse(key,node1,val);
            }
            else if(node.IsExternal()){
                node.insertKey(key,val);
                return;
            }
            else{
                traverse(key,node.nextNode(key),val);
            }
        }
    public void searchNode(Key key,BTNode<Key,Value> node,List<Value> l){
    	if(node.IsExternal()) {
    		node.Findkey(key,l);
    	}
    	else {
    		for(int i=0;i<node.numkey;i++) {
    			Key key2=node.array.get(i).key;
    			Value val2 = node.array.get(i).val;
    			if(key2.compareTo(key)>0) {
    				searchNode(key,node.array.get(i).left,l);
    			}
    			else if(key2.compareTo(key)==0) {
    				searchNode(key,node.array.get(i).left,l);
    				l.add(val2);
    			}
    			if(i==node.numkey-1) {
    				searchNode(key,node.array.get(i).right,l);
    			}
    		}
    	}
    }
    public BTNode<Key,Value> FindNode(Key key,BTNode<Key,Value> node) {
    		if(node.numkey==t-1&&node!=root) {
    			underflow(node,key);
    		}
    		if(node.IsKey(key)) {
    			return node;
    		}
    		else
    			return FindNode(key,node.findNext(key));
    	
    }
    public BTNode<Key,Value> Inorder(Key key,BTNode<Key,Value> node){
    	BTNode<Key,Value> node1=node.KeyLeft(key);
    	return LastRight(node1,key);
    }
    public BTNode<Key,Value> LastRight(BTNode<Key,Value> node1,Key key){
    	if(node1.numkey==t-1) {
    		underflow(node1,key);
    	}
    	if(node1.IsExternal())
    		return node1;
    	else
    		return LastRight(node1.Right(),key);
    }
    public void swapKey(BTNode<Key,Value> node1,BTNode<Key,Value> node2,Key key) {
    	Position<Key,Value> pos1 = node1.Findpos(key);
    	Position<Key,Value> pos2 = node2.Findpos(key);
    	pos1.key = pos2.key;
    }
    public void mergePSL(Key key,BTNode<Key,Value> node) {
		 Position<Key,Value> ppos = node.parentposR;
		 ArrayList<Position<Key,Value>> array2 = ppos.right.array;
		 Position<Key,Value> posc = new Position<Key,Value>(ppos.key,ppos.val,node.array.get(node.array.size()-1).right,array2.get(0).left);
		 BTNode<Key,Value> node2 = new BTNode<Key,Value>(node.parent);
		 node2.array.addAll(node.array);
		 node2.array.add(posc);
		 node2.array.addAll(array2);
		 node2.parentposR=ppos.right.parentposR;
		 node2.parentposL=ppos.left.parentposL;
		 node2.numkey=node2.array.size();
		 node.parent.array.remove(ppos);
		 node.parent.numkey=node.parent.array.size();
		 if(node.parent.array.size()==0) {
			 root=node2;
		 }
		 if(ppos.right.parentposR!=null) {
			 ppos.right.parentposR.left=node2;
		 }
		 if(ppos.left.parentposL!=null) {
			 ppos.left.parentposL.right=node2;
		 }
		 delete(key,node2);
	    }
    public void mergePSR(BTNode<Key,Value> node,Key key) {
    	node.array.add(0, node.parentposL);
    	node.array.get(0).right=node.array.get(1).left;
    	ArrayList<Position<Key,Value>> array2 = node.parentposL.left.array;
    	node.array.get(0).left=array2.get(array2.size()-1).right;
    	array2.addAll(node.array);
    	node.parentposL.left.numkey=array2.size();
    	node.parent.array.remove(node.parentposL);
    	node.parent.numkey--;
    }
    public void underflow(BTNode<Key,Value> node,Key key) {
    	if(node.parentposR!=null&&node.parentposL==null) {
			if(node.parentposR.right.numkey>t-1) {
				node.rotateR(key);
			}
			else if(node.parent.numkey>t-1) {
				mergePSL(key,node);
			}
			else if(node.parent.numkey==t-1&&node.parent==root) {
				mergePSL(key,node);
			}
    	}
    	else if(node.parentposL!=null&&node.parentposR==null) {
			if(node.parentposL.left.numkey>t-1) {
				node.rotateL(key);
			}
			else if(node.parent.numkey>t-1) {
				//node.mergePSR(key);
			}
			else if(node.parent.numkey==t-1&&node.parent==root) {
				//node.mergePSR(key);
			}
    	}
    	else {
			if(node.parentposL.left.numkey>t-1) {
				node.rotateL(key);
			}
			else if(node.parentposR.right.numkey>t-1) {
				node.rotateR(key);
			}
			else if(node.parent.numkey>t-1) {
				//node.mergePSR(key);
			}
			/*else if(node.parent.numkey==t-1&&node.parent==root) {
				merge
			}*/
		}
    	
    }
}
    
