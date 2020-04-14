package eg.edu.alexu.csd.filestructure.redblacktree;
import java.util.ArrayList;

import javax.management.RuntimeErrorException;
public class red_black<T extends Comparable<T>,V>  implements IRedBlackTree<T, V>{
	 private INode<T,V> root=null;
	  private INode<T,V> TNULL;
	int size=0;
	public red_black ()
	{
		  TNULL = new Node<T,V>();
		    TNULL.setColor(false); 
		    TNULL.setLeftChild(null); 
		    TNULL.setRightChild(null); 
		    root = TNULL;
	}

	@Override
	public INode<T, V> getRoot() {
		
				return root;
	}

	@Override
	public boolean isEmpty() {
		return size==0;
	}

	@Override
	public void clear() {
		
	 root=null;
	  size=0;
		
	}

	@Override
	public V search(T key) {
		if (key ==null){
			throw new RuntimeErrorException(null);
			}
		if(root==null) {
			return null;
		}
		  return    searchTreeHelper(root, key).getValue();
	}
	private INode<T,V> searchTreeHelper(INode<T,V> iNode, T key) {
	    if (iNode == TNULL ||iNode==null ||iNode.getKey()==null ) {
	    	
	      return iNode;
	    }
	    if( key.compareTo(iNode.getKey())==0) {
	    	return iNode;
	    }

	    if (key.compareTo( iNode.getKey())<0) {
	      return searchTreeHelper( iNode.getLeftChild(), key);
	    }
	    return searchTreeHelper(iNode.getRightChild(), key);
	  }
	@Override
	public boolean contains(T key) {
		if (key ==null){
			throw new RuntimeErrorException(null);
			}
		if( searchTreeHelper(root,key).getKey()==null) {
			return false;
		}
		
		return true;
	}

	@Override

	 public void insert(T key, V value) {
		if(key==null || value==null) {
			throw  new RuntimeErrorException(null);
		}
		 INode<T, V> node = new Node<>(key,value,true); 
		    node.setParent(null);
		    node.setLeftChild(TNULL);
		    node.setRightChild(TNULL);
		size++;
		 INode <T,V>y = null;
		    if(this.root==null)
		    {
		    	this.root=node;
		    	root.setColor(false);  
		    }
		    else { 
	  if(contains(key))
	  {
		 searchTreeHelper(root, key).setValue(value);
		 size--;
		 return;
	  }
	  else {
	    INode<T, V> x = this.root;
	    while (x != TNULL) {
	      y = x;
	      if (node.getKey().compareTo( x.getKey())<0) {
	        x =  x.getLeftChild();
	      } else {
	        x = x.getRightChild();
	      }
	    }

	    node.setParent(y);
	    if (y == null) {
	      root = node;
	    } else if (node.getKey().compareTo( y.getKey())<0) {
	      y.setLeftChild(node);
	    } else {
	      y.setRightChild(node); 
	    }

	    if (node.getParent() == null) {
	      node.setColor(false);              
	      return;
	    }

	    if (node.getParent().getParent() == null) {
	      return;
	    }

	    fixTree(node);
	    }
	    
	  }
	  }

	private void fixTree(INode<T,V> k) {
	
		 INode<T, V> u;
		    while (k.getParent().getColor() == true) {      
		      if (k.getParent() == k.getParent().getParent().getRightChild()) {
		        u = k.getParent().getParent().getLeftChild();
		        if (u.getColor()== true) {  
		          u.setColor(false);   
		          k.getParent().setColor(false); 
		          k.getParent().getParent().setColor(true); 
		          k = k.getParent().getParent();
		        } else {
		          if (k == k.getParent().getLeftChild()) {
		            k = k.getParent();
		            rotateRight(k);
		          }
		          k.getParent().setColor(false);   
		          k.getParent().getParent().setColor(true);  
		          rotateLeft(k.getParent().getParent());
		        }
		      } else {
		        u = k.getParent().getParent().getRightChild();

		        if (u.getColor() == true) {    
		          u.setColor(false);   
		          k.getParent().setColor(false); 
		          k.getParent().getParent().setColor(true); 
		          k = k.getParent().getParent();
		        } else {
		          if (k == k.getParent().getRightChild()) {
		            k = k.getParent();
		            rotateLeft(k);
		          }
		          k.getParent().setColor(false);  
		          k.getParent().getParent().setColor(true); 
		          rotateRight(k.getParent().getParent());
		        }
		      }
		      if (k == root) {
		        break;
		      }
		    }
		    root.setColor(false);  
    }
	
	void rotateLeft(INode<T,V> node) {
        if (node.getParent() != null) {
            if (node == node.getParent().getLeftChild()) {
                node.getParent().setLeftChild(node.getRightChild()); 
            } else {
                node.getParent().setRightChild(node.getRightChild()); 
            }
            node.getRightChild().setParent(node.getParent()); 
            node.setParent(node.getRightChild());
            if (node.getRightChild().getLeftChild() != null) {
                node.getRightChild().getLeftChild().setParent(node); 
            }
            node.setRightChild(node.getRightChild().getLeftChild());
            node.getParent().setLeftChild(node); 
        } else {
            INode<T, V> right =  root.getRightChild();
            root.setRightChild(right.getLeftChild()); 
            right.getLeftChild().setParent(root);
            root.setParent(right); 
            right.setLeftChild(root);
            right.setParent(null); 
            root = right;
        }
    }

    void rotateRight(INode<T,V> node) {
        if (node.getParent() != null) {
            if (node == node.getParent().getLeftChild()) {
                node.getParent().setLeftChild(node.getLeftChild()); 
            } else {
                node.getParent().setRightChild(node.getLeftChild()); 
            }

            node.getLeftChild().setParent(node.getParent());
            node.setParent(node.getLeftChild());
            if (node.getLeftChild().getRightChild() != null) {
                node.getLeftChild().getRightChild().setParent(node); 
            }
            node.setLeftChild(node.getLeftChild().getRightChild()); 
            node.getParent().setRightChild(node);
        } else {//Need to rotate root
            INode<T,V> left = root.getLeftChild();
            root.setLeftChild(root.getLeftChild().getRightChild());
            left.getRightChild().setParent(root) ;
            root.setParent(left); 
            left.setRightChild(root);
            left.setParent(null); 
            root =  left;
        }
    }
    public void printTree(INode<T,V> iNode) {
        if (iNode == null) {
            return;
        }
        printTree( iNode.getLeftChild());
       System.out.println(iNode.getKey());
        printTree( iNode.getRightChild());
    }
    public void prettyPrint() {
        printHelper(this.root, "", false);    
      }



    private void printHelper(INode<T,V> iNode, String indent, boolean last) {
        if (iNode != TNULL) {
          System.out.print(indent);
          if (last) {
            System.out.print("R----");
            indent += "   ";
          } else {
            System.out.print("L----");
            indent += "|  ";
          }

          String sColor = iNode.getColor() == true ? "RED" : "BLACK";  
          System.out.println(iNode.getKey() + "(" + sColor + ")");
          printHelper(iNode.getLeftChild(), indent, true);  
          printHelper(iNode.getRightChild(), indent, false);  
        }
      }

	@Override
	public boolean delete(T key) {
		if(key==null) {
			throw  new RuntimeErrorException(null);
		}
		if(contains(key)) {
			if(!(isred(root.getLeftChild())||isred(root.getRightChild()))) {
				root.setColor(true);
			}
			root=remove(root,key);
			size--;
			if(size!=0) {
				root.setColor(false);
			}
			else {
				root=TNULL;
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
	
	private boolean deleteNodeHelper(INode<T,V> root2, T key) {

	    INode<T, V> z = TNULL;
	    INode<T, V> x;
		INode<T, V> y;
	    while (root2 != TNULL) {
	      if (root2.getKey() .compareTo(key)==0) {
	        z =  root2;
	       
	      }

	      if (root2.getKey().compareTo(key)<=0){
	        root2 =  root2.getRightChild();

	       
	      } else {
	        root2 =  root2.getLeftChild();
	        
	      }
	    }

	    if (z == TNULL) {
	      
	      return false;
	    }

	    y =  z;
	    boolean yOriginalColor = y.getColor();
	    if (z.getLeftChild() == TNULL) {
	      x =  z.getRightChild();
	
	      rbTransplant(z, z.getRightChild());
	    } else if (z.getRightChild() == TNULL) {
	      x =  z.getLeftChild();
	      rbTransplant(z, z.getLeftChild());
	    } else {
	      y =  minimum(z.getRightChild());
	      yOriginalColor = y.getColor();
	      x =  y.getRightChild();
	      if (y.getParent() == z&&y!=null&&x!=null) {
	        x.setParent(y); 
	      } else {
	        rbTransplant(y, y.getRightChild());
	        y.setRightChild(z.getRightChild()); 
	        y.getRightChild().setParent(y); 
	      }

	      rbTransplant(z, y);
	      y.setLeftChild(z.getLeftChild()); 
	      y.getLeftChild().setParent(y);
	      y.setColor(z.getColor());
	    }
	    if (yOriginalColor == true) {   
	      fixDelete(x);
	    }
	    return false;  
		
	  }


	private void rbTransplant(INode<T, V> z, INode<T, V> y) {
		 
	    if (z.getParent() == null) {
	      root = y;
	    } else if (z == z.getParent().getLeftChild()) {
	      z.getParent().setLeftChild(y); 
	    } else {
	      z.getParent().setRightChild(y); 
	    }
	    
	    y.setParent(z.getParent());

	  }
	private void fixDelete(INode<T, V> x) {
	    INode<T, V> s;
	    while (x != root && x.getColor() == false) { 
	      if (x == x.getParent().getLeftChild()) {
	        s = x.getParent().getRightChild();
	        if (s.getColor() == true) {   
	          s.setColor(false);     
	          x.getParent().setColor(true); 
	          rotateLeft(x.getParent());
	          s = x.getParent().getRightChild();
	        }
	 
	        if (s.getLeftChild().getColor() == false && s.getRightChild().getColor() == false) {
	          s.setColor(true);  
	          x = x.getParent();
	        } else {
	          if (s.getRightChild().getColor() == false) {  
	            s.getLeftChild().setColor(false);   
	            s.setColor(true);    
	            rotateRight(s);
	            s = x.getParent().getRightChild();
	          }

	          s.setColor(x.getParent().getColor()); 
	          x.getParent().setColor(false);   
	          s.getRightChild().setColor(false);  
	          rotateLeft(x.getParent());
	          x = root;
	      }
	      } else {
	        s = x.getParent().getLeftChild();
	        if (s.getColor() == true) {  
	          s.setColor(false); 
	          x.getParent().setColor(true);  
	          rotateRight(x.getParent());
	          s = x.getParent().getLeftChild();
	        }

	        if (s.getRightChild().getColor() == false && s.getRightChild().getColor() == false) {
	          s.setColor(true);  
	          x = x.getParent();
	        } else {
	          if (s.getLeftChild().getColor() == false) {  
	            s.getRightChild().setColor(false); 
	            s.setColor(true);  
	            rotateLeft(s);
	            s = x.getParent().getLeftChild();
	          }

	          s.setColor(x.getParent().getColor());
	          x.getParent().setColor(false);   
	          s.getLeftChild().setColor(false);  
	          rotateRight(x.getParent());
	          x = root;
	        }
	      }
	    }
	    x.setColor(true); 
	  }
	 public INode<T,V> minimum(INode<T,V> node) {
		    while (node.getLeftChild() != TNULL) {
		      node = node.getLeftChild();
		    }
		    return node;
		  }

		  public INode<T,V> maximum(INode<T,V> node) {
		    while (node.getRightChild() != TNULL) {
		      node = node.getRightChild();
		    }
		    return node;
		  }
		 

}