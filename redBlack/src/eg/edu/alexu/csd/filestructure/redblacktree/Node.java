package eg.edu.alexu.csd.filestructure.redblacktree;

public class Node <T extends Comparable<T>, V> implements INode<T,V>{
	private INode<T, V> parent,left,right;
	private T key;
	private V value;
	private boolean color;
	static final boolean RED   = true;
    static final boolean BLACK = false;
    public Node() {
		// TODO Auto-generated constructor stub
	}

	Node(T key,V value,boolean color){
		this.value=value;
		this.key=key;
		this.color=color;
		parent=null;
		left=null;
		right=null;
	}

	@Override
	public void setParent(INode<T, V> parent) {
		this.parent= parent;
		
	}

	@Override
	public INode<T, V> getParent() {
		return parent;
	}

	@Override
	public void setLeftChild(INode<T, V> leftChild) {
		left=leftChild;
		
	}

	@Override
	public INode<T, V> getLeftChild() {
		return left;
	}

	@Override
	public void setRightChild(INode<T, V> rightChild) {
		right=rightChild;
		
	}

	@Override
	public INode<T, V> getRightChild() {
		return right;
	}

	@Override
	public T getKey() {
		return key;
	}

	@Override
	public void setKey(T key) {
		this.key=key;
		
	}

	@Override
	public V getValue() {
		return value;
	}

	@Override
	public void setValue(V value) {
		this.value=value;
		
	}

	@Override
	public boolean getColor() {
		if(isNull()) {
			return false;
		}
		return color;
	}

	@Override
	public void setColor(boolean color) {
		this.color=color;
		
	}

	@Override
	public boolean isNull() {
		
		return key==null;
	}

}
