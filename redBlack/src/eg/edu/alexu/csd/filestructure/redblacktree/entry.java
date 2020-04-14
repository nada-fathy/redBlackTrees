package eg.edu.alexu.csd.filestructure.redblacktree;

import java.util.Map;

public class entry <T extends Comparable<T>, V>implements Map.Entry<T, V>{

	private final T key;
	private V value;
	 entry<T,V> parent,left,right;
	
	
	 public entry(T key, V value) {
			this.key = key;
	        this.value = value;
	    	parent=null;
			left=null;
			right=null;
	    }
	 
	 public void setParent(entry<T,V> E) {
		 parent=E;
	 }
	 
	 public entry<T,V> getParent() {
		 return parent;
	 }
	 
	 public void setleft(entry<T,V> E) {
		 left=E;
	 }
	 
	 public entry<T,V> getleft() {
		 return left;
	 }
	 
	 public void setright(entry<T,V> E) {
		 right=E;
	 }
	 
	 public entry<T,V> getright() {
		 return right;
	 }
	
	public T getKey() {
		return key;
	}

	
	public V getValue() {
		return value;
	}


	public V setValue(V value) {
		V prev=this.value;
		this.value=value;
		return prev;
	}

}
