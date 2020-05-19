package ca.jrvs.practice.dataStructure.tree;

import java.util.Comparator;
import java.util.Objects;

/**
 * A simplified BST implementation
 *
 * @param <E> type of object to be stored
 */
public class JBSTree<E> implements JTree<E> {

  /**
   * The comparator used to maintain order in this tree map
   * Comparator cannot be null
   */
  private Comparator<E> comparator;
  private Node<E> root;
  /**
   * Create a new BST
   *
   * @param comparator the comparator that will be used to order this map.
   * @throws IllegalArgumentException if comparator is null
   */
  public JBSTree(Comparator<E> comparator) {
    if (comparator == null) {
      throw new IllegalArgumentException("A comparator must be provided");
    }
    this.comparator = comparator;
  }

  /**
   *
   * @param comparator the comparator that will be used to order this tree
   * @param root provided root of the tree
   * @throws IllegalArgumentException if comparator is null
   */
  public JBSTree(Comparator<E> comparator, Node<E> root) {
    if (comparator == null) {
      throw new IllegalArgumentException("A comparator must be provided");
    }
    this.comparator = comparator;
    this.root = root;
  }


  /**
   * Insert an object into the BST.
   * Please review the BST property.
   *
   * @param object item to be inserted
   * @return inserted item
   * @throws IllegalArgumentException if the object already exists
   */
  @Override
  public E insert(E object) {
    if (root == null){
      root = new Node<E>(object,null);
      return root.value;
    }else if(search(object)!=null){
      throw new IllegalArgumentException("alreadly in BST");
    }else{
      Node parent =null;
      Node n= root;
      boolean leftchild = false;
      while(n!=null){
        if(n.hashCode()<object.hashCode()){
          parent = n;
          n=n.right;
          leftchild = false;
        }else{
          parent = n;
          n=n.left;
          leftchild = true;
        }
      }
      if(leftchild){
        parent.setLeft(new Node(object,parent));
      }else{
        parent.setRight(new Node(object,parent));
      }
    }
    return object;
  }

  /**
   * Search and return an object, return null if not found
   *
   * @param object to be found
   * @return the object if exists or null if not
   */
  @Override
  public E search(E object) {
    Node n = root;
    while(n!=null){
      if(n.value.equals(object)){
        return object;
      }else if(n.hashCode()>object.hashCode()){
        n=n.left;
      }else
        n = n.right;
    }
    return null;
  }

  /**
   * Remove an object from the tree.
   *
   * @param object to be removed
   * @return removed object
   * @throws IllegalArgumentException if the object not exists
   */
  @Override
  public E remove(E object) {
    Node<E> n = root;
    E dummy = null;
    Node parent =null;
    boolean leftChild = false;
    while(n!=null) {
      if (n.value.equals(object)){
        //two child case
        if(n.left!=null && n.right!= null){
          dummy=findLeftMost(n);
          remove(dummy);
          n.value=dummy;
        }
        // is leaf node
        else if(n.left == null && n.right == null)
          if (leftChild) {
            parent.setLeft(null);
          } else {
            parent.setRight(null);
          }
        // only have one left child
        else {
          if (n.left != null) {
            if (leftChild) {
              parent.left = n.left;
            } else {
              parent.right = n.left;
            }
          } else {
            if (leftChild) {
              parent.left = n.right;
            } else {
              parent.right = n.right;
            }
          }
        }
        return object;
      }else if(n.hashCode()>object.hashCode()){
        parent=n;
        n=n.left;
        leftChild=true;
      }else{
        parent=n;
        n=n.right;
        leftChild=false;
      }
    }
    throw new IllegalArgumentException("Object not found in tree");
  }

  /**
   * traverse the tree recursively
   *
   * @return all objects in pre-order
   */
  @Override
  public E[] preOrder() {
    return null;
  }

  /**
   * traverse the tree recursively
   *
   * @return all objects in-order
   */
  @Override
  public E[] inOrder() {
    return null;
  }

  /**
   * traverse the tree recursively
   *
   * @return all objects pre-order
   */
  @Override
  public E[] postOrder() {
    return null;
  }

  public E findLeftMost(Node<E> n ){
    if(n.right!=null)
      n = n.right;
    return n.value;
  }

  public E findRightMost(){
    Node<E> n = root;
    if(n.left!=null)
      n = n.left;
    return n.value;
  }

  /**
   * traverse through the tree and find out the tree height
   * @return height
   * @throws NullPointerException if the BST is empty
   */
  @Override
  public int findHeight() {
    return 0;
  }

  static final class Node<E> {

    E value;
    Node<E> left;
    Node<E> right;
    Node<E> parent;

    public Node(E value, Node<E> parent) {
      this.value = value;
      this.parent = parent;
    }

    public E getValue() {
      return value;
    }

    public void setValue(E value) {
      this.value = value;
    }

    public Node<E> getLeft() {
      return left;
    }

    public void setLeft(Node<E> left) {
      this.left = left;
    }

    public Node<E> getRight() {
      return right;
    }

    public void setRight(Node<E> right) {
      this.right = right;
    }

    public Node<E> getParent() {
      return parent;
    }

    public void setParent(Node<E> parent) {
      this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Node)) {
        return false;
      }
      Node<?> node = (Node<?>) o;
      return getValue().equals(node.getValue()) &&
          Objects.equals(getLeft(), node.getLeft()) &&
          Objects.equals(getRight(), node.getRight()) &&
          getParent().equals(node.getParent());
    }

    @Override
    public int hashCode() {
      return Objects.hash(getValue(), getLeft(), getRight(), getParent());
    }
  }

}
