package col106.a3;
//import java.util.List;

public class Position<Key extends Comparable<Key>,Value>{
	Key key;
	Value val;
	BTNode<Key,Value> right;
	BTNode<Key,Value> left;
	public Position(){
		this.key=null;
		this.val=null;
		this.left=null;
		this.right=null;
	}
	public Position(Key key, Value val,BTNode<Key,Value> left,BTNode<Key,Value> right){
		this.key=key;
		this.val=val;
		this.left=left;
		this.right=right;
	}
}