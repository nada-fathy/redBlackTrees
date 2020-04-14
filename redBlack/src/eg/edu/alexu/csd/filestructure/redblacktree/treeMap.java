package eg.edu.alexu.csd.filestructure.redblacktree;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.management.RuntimeErrorException;

public class treeMap <T extends Comparable<T>, V> implements ITreeMap<T,V>{
	
	
	private int size;
	INode <T,V>root;
	treeMap(){
		size=0;
		root=null;
	
	}

	public Entry<T, V> ceilingEntry(T key) {
		if(key==null) {
			throw  new RuntimeErrorException(null);
		}
		
		INode<T,V> node=ceiling(root,key);
		if(node==null) {
			return null;
		}
		Entry<T,V> ent=new entry<T, V>(node.getKey(),node.getValue());
		return node2entry(node);
	}

	@Override
	public T ceilingKey(T key) {
		if(key==null) {
			throw  new RuntimeErrorException(null);
		}
		
		INode<T,V> node=ceiling(root,key);
		if(node==null) {
			return null;
		}
		return node.getKey();
	}
	private INode <T,V> ceiling(INode<T,V> node,T key){
		if(node==null) {
			return null;
		}
		if((key.compareTo(node.getKey()))==0) {
			return node;
		}
		else if((key.compareTo(node.getKey()))>0) {
			return ceiling(node.getRightChild(),key);
		}
		else {
			INode<T,V> n=ceiling(node.getLeftChild(),key);
			if(n!=null) {
				return n;
			}
		}
		return node;
	}

	@Override
	public void clear() {
		root=null;
		size=0;
	}

	
	public boolean containsKey(T key) {
		if(key==null) {
			throw  new RuntimeErrorException(null);
		}
		INode<T,V> node = root;
		while(node!=null) {
			int comp=key.compareTo(node.getKey());
			if(comp==0) {
				return true;
			}
			else if(comp>0) {
				node=node.getRightChild();
			}
			else {
				node=node.getLeftChild();
			}
		}
		return false;
	}

	@Override
	public boolean containsValue(V value) {
		return containvalue(root,value);
		}
	
	private boolean containvalue(INode<T,V> node,V value) {
		if(value==null) {
			throw  new RuntimeErrorException(null);
		}
		if(node==null ) {
			return false;
		}
		if((value!=null && value.equals(node.getValue()))||(value==null && node.getValue()==null)) {
			return true;
		}
		return containvalue(node.getLeftChild(),value)||containvalue(node.getRightChild(),value);
	}
	
	private Entry<T,V> node2entry(INode<T,V> node){
		return new entry<>(node.getKey(),node.getValue());
	}

	@Override
	public Set<Entry<T, V>> entrySet() { 
		if(root==null) {
			return null;
		}
		Set<T> treeSet =  keySet();
		Set<Map.Entry<T,V>> set = new LinkedHashSet<>();
		for(T k:treeSet) {
			INode<T,V> node=getnode(root, k);
			set.add(new AbstractMap.SimpleEntry<>(k, node.getValue()));
		}
		return set;
	}
	

	@Override
	public Entry<T, V> firstEntry() {
		if(root==null) {
			return null;
		}
		INode <T,V> node =root;
		while(node.getLeftChild()!=null) {
			node=node.getLeftChild();
		}
		return node2entry(node);
	}

	@Override
	public T firstKey() {
		if(firstEntry()==null) {
			return null;
		}
		return firstEntry().getKey();
	}

	@Override
	public Entry<T, V> floorEntry(T key) {
		if(key==null) {
			throw  new RuntimeErrorException(null);
		}
		
		INode<T,V> node=floor(root,key);
		if(node==null) {
			return null;
		}
		return node2entry(node);
	}

	@Override
	public T floorKey(T key) {
		if(key==null) {
			throw  new RuntimeErrorException(null);
		}
		
		INode<T,V> node=floor(root,key);
		if(node==null) {
			return null;
		}
		return node.getKey();
	}
	private INode<T,V> floor(INode <T,V> node,T key){
		if(node==null) {
			return null;
		}
		if((key.compareTo(node.getKey()))==0) {
			return node;
		}
		else if((key.compareTo(node.getKey()))<0) {
			return ceiling(node.getLeftChild(),key);
		}
		else {
			INode<T,V> n=ceiling(node.getRightChild(),key);
			if(n!=null) {
				return n;
			}
		}
		return node;
	}

	@Override
	public V get(T key) {
		if(key==null) {
			throw  new RuntimeErrorException(null);
		}
		return getvalue(root,key);
	}
	private V getvalue(INode <T,V> node, T key) {
		if(node ==null) {
			return null;
		}
		if(node.getKey().equals(key)) {
			return node.getValue();
		}
		if(node.getKey().compareTo(key)>0) {
			return getvalue(node.getLeftChild(),key);
		}
		return getvalue(node.getRightChild(),key);
	}
	private INode<T,V> getnode(INode <T,V> node, T key) {
		if(node ==null) {
			return null;
		}
		if(node.getKey().equals(key)) {
			return node;
		}
		if(node.getKey().compareTo(key)>0) {
			return getnode(node.getLeftChild(),key);
		}
		return getnode(node.getRightChild(),key);
	}
	
	

	@Override
	public ArrayList<Entry<T, V>> headMap(T toKey) {
		if(root==null) {
			return null;
		}
		Set<T> treeSet =  keySet();
		ArrayList<Map.Entry<T,V>> set = new ArrayList<>();
		for(T k:treeSet) {
			if(k.compareTo(toKey)>0) {
				INode<T,V> node=getnode(root, k);
				set.add(node2entry(node));	
			}
			else {
				break;
			}
		}
		return set;
		
	}
	

	@Override
	public ArrayList<Entry<T, V>> headMap(T toKey, boolean inclusive) {
		if(root==null) {
			return null;
		}
		Set<T> treeSet =  keySet();
		ArrayList<Map.Entry<T,V>> set = new ArrayList<>();
		if(inclusive==false) {
			for(T k:treeSet) {
				if(k.compareTo(toKey)>0) {
					INode<T,V> node=getnode(root, k);
					set.add(node2entry(node));	
				}
				else {
					break;
				}
			}
		}
		else {
			for(T k:treeSet) {
				if(k.compareTo(toKey)>=0) {
					INode<T,V> node=getnode(root, k);
					set.add(node2entry(node));	
				}
				else {
					break;
				}
			}
		}
	
		return set;
	}

	@Override
	public Set<T> keySet() {
		Set<T> tree= new HashSet<>();
		kset(tree,root);
		Set<T> tree_Set = new TreeSet<T>(tree);
		return  tree_Set;
	}
	private void kset(Set<T> tree,INode<T,V> node) {
		if(node==null) {
			return;
		}
		kset(tree,node.getLeftChild());
		tree.add(node.getKey());
		kset(tree,node.getRightChild());
	}
	

	@Override
	public Entry<T, V> lastEntry() {
		if(root==null) {
			return null;
		}
		INode <T,V> node =root;
		while(node.getRightChild()!=null) {
			node=node.getRightChild();
		}
		return node2entry(node);
	}

	@Override
	public T lastKey() {
		if(lastEntry()==null) {
			return null;
		}
		return lastEntry().getKey();
	}

	@Override
	public Entry<T, V> pollFirstEntry() {
		Entry<T,V> ent=firstEntry();
		if(ent==null) {
			return null;
		}
		remove(ent.getKey());
		return ent;
	}

	@Override
	public Entry<T, V> pollLastEntry() {
		Entry<T,V> ent=lastEntry();
		if(ent==null) {
			return null;
		}
		remove(ent.getKey());
		return ent;
	}

	@Override
	public void put(T key, V value) {
		if(key==null || value==null) {
			throw  new RuntimeErrorException(null);
		}
		if(containsKey(key)) {
			INode<T,V> node=getnode(root, key);
			node.setValue(value);
			return;
		}
		root=put(root,key,value);
		root.setColor(false);
		size++;
		
	}
	private INode<T,V>put(INode<T,V>x,T key,V value){
		if(x==null) {
			return new Node<>(key,value,true);
		}
		if(key.compareTo(x.getKey())<0) {
			x.setLeftChild(put(x.getLeftChild(),key,value));
		}
		else if(key.compareTo(x.getKey())>0) {
			x.setRightChild(put(x.getRightChild(),key,value));
		}
		else {
			x.setValue(value);
		}
		if(isred(x.getRightChild())&&!isred(x.getLeftChild())) {
			x=rotateleft(x);
		}
		if(isred(x.getLeftChild())&&isred(x.getLeftChild().getLeftChild())) {
			x=rotateright(x);
		}
		if(isred(x.getLeftChild())&&isred(x.getRightChild())) {
			change(x);
		}
		return x;
		
	}

	@Override
	public void putAll(Map<T, V> map) {
		if(map==null) {
			throw  new RuntimeErrorException(null);
		}
		for (Map.Entry<T,V> entry : map.entrySet()) {
			put(entry.getKey(),entry.getValue());
		}
		
	}

	@Override
	public boolean remove(T key) {
		if(key==null) {
			throw  new RuntimeErrorException(null);
		}
		if(containsKey(key)) {
			if(!(isred(root.getLeftChild())||isred(root.getRightChild()))) {
				root.setColor(true);
			}
			root=remove(root,key);
			size--;
			if(size!=0) {
				root.setColor(false);
			}
			return true;
		}
		return false;
	}
	private INode<T,V> remove(INode<T,V> node,T key){
		if(key.compareTo(node.getKey())<0) {
			if(!isred(node.getLeftChild())&& ! isred(node.getLeftChild().getLeftChild())) {
				node=moveleft(node);
			}
			node.setLeftChild(remove(node.getLeftChild(),key));
		}
		else {
			if(isred(node.getLeftChild())) {
				node=rotateright(node);
			}
			if(key.compareTo(node.getKey())==0 && node.getRightChild()==null) {
				return null;
			}
			if(!isred(node.getRightChild())&& ! isred(node.getRightChild().getLeftChild())) {
				node=moveright(node);
			}
			if(key.compareTo(node.getKey())==0) {
				INode<T,V> x=node.getRightChild();
				while(x.getLeftChild()!=null) {
					x=x.getLeftChild();
				}
				node.setKey(x.getKey());
				node.setValue(x.getValue());
				node.setRightChild(removemin(node.getRightChild()));
			}
			else {
				node.setRightChild(remove(node.getRightChild(),key));
			}
		}
		return balance(node);
	}
	
	
	private INode<T,V> moveleft(INode<T,V> node){
		if(node==null) {
			return null;
		}
		if(isred(node) && ! isred(node.getLeftChild())&& !isred(node.getLeftChild().getLeftChild())) {
			change(node);
			if(isred(node.getRightChild().getLeftChild())) {
				node.setRightChild(rotateright(node.getRightChild()));
				node=rotateleft(node);
				change(node);
			}
		}
		return node;
	}
	
	private INode<T,V> moveright(INode<T,V> node){
		if(node==null) {
			return null;
		}
		if(isred(node) && ! isred(node.getRightChild())&& !isred(node.getRightChild().getLeftChild())) {
			change(node);
			if(isred(node.getLeftChild().getLeftChild())) {
				node=rotateright(node);
				change(node);
			}
		}
		return node;
	}
	INode<T,V> removemin(INode <T,V> node){
		if(node.getLeftChild()==null) {
			return null;
		}
		if(!isred(node.getLeftChild())&& !isred(node.getLeftChild().getLeftChild())) {
			node=moveleft(node);
		}
		node.setLeftChild(removemin(node.getLeftChild()));
		return balance(node);
	}

	
	public int size() {
		return size;
	}

	@Override
	public Collection<V> values() {
		if(root==null) {
			return null;
		}
		Collection <V> tree=new ArrayList<>();
		values(root,tree);
		return tree;
	}
	private void values(INode <T,V> node, Collection <V> tree) {
		if(node==null) {
			return;
		}
		values(node.getLeftChild(),tree);
		tree.add(node.getValue());
		values(node.getRightChild(),tree);
	}
	
	public INode<T,V> rotateleft(INode<T,V>node){
		if(node==null) {
			return null;
		}
		
		INode<T,V> x=node.getRightChild();
		node.setRightChild(x.getLeftChild());
		x.setLeftChild(node);
		x.setColor(x.getLeftChild().getColor());
		x.getLeftChild().setColor(true);
		return x;
		
	}
	
	public INode<T,V> rotateright(INode<T,V>node){
		if(node==null) {
			return null;
		}
		INode<T,V> x=node.getLeftChild();
		node.setLeftChild(x.getRightChild());
		x.setRightChild(node);
		x.setColor(x.getRightChild().getColor());
		x.getRightChild().setColor(true);
		return x;
	}
	
	private INode<T,V> balance(INode <T,V> node){
		if(node==null) {
			return null;
		}
		if(isred(node.getRightChild())) {
			node=rotateleft(node);
		}
		if(isred(node.getLeftChild()) && isred(node.getLeftChild().getLeftChild())) {
			node=rotateright(node);
		}
		if(isred(node.getLeftChild())&&isred(node.getRightChild())) {
			change(node);
		}
		return node;
		
		
	}
	private boolean isred(INode<T,V>node) {
		if(node==null) {
			return false;
		}
		return node.getColor();
	}
	private void change(INode<T,V> node) {
		if(node==null || node.getLeftChild()==null || node.getRightChild()==null) {
			return;
		}
		if(!(isred(node)&&!isred(node.getLeftChild())&&!isred(node.getRightChild()))  &&  !(!isred(node)&&isred(node.getLeftChild())&&isred(node.getRightChild()))) {
			return;
		}
		node.setColor(!node.getColor());
		node.getLeftChild().setColor(!node.getLeftChild().getColor());
		node.getRightChild().setColor(!node.getRightChild().getColor());
	}

}
