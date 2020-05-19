package ca.jrvs.practice.dataStructure.stackQueue;

import ca.jrvs.practice.dataStructure.list.LinkedJList;
import java.util.EmptyStackException;
import java.util.NoSuchElementException;


public class LinkedJListJDeque<E> extends LinkedJList<E> implements JDeque<E> {

  transient int size = 0;
  transient LinkedJListJDeque.Node<E> first;
  transient LinkedJListJDeque.Node<E> last;
  private static class Node<E> {

    E item;
    LinkedJListJDeque.Node<E> next;
    LinkedJListJDeque.Node<E> prev;

    Node(LinkedJListJDeque.Node<E> prev, E element, LinkedJListJDeque.Node<E> next) {
      this.item = element;
      this.next = next;
      this.prev = prev;
    }
  }
  /**
   * This is equivalent enqueue operation in Queue ADT
   * <p>
   * Inserts the specified element into the queue represented by this deque (in other words, at the
   * tail of this deque) if it is possible to do so immediately without violating capacity
   * restrictions, returning {@code true} upon success and throwing an {@code IllegalStateException}
   * if no space is currently available.
   *
   * @param e the element to add
   * @return {@code true} (as specified by {)
   * @throws NullPointerException if the specified element is null and this deque does not permit
   *                              null elements
   */
  @Override
  public boolean add(E e) {
    final Node<E> newNode = new Node<>(null, e, null);
    if(size == 0){
      last=first=newNode;
    }else{
      newNode.next=first;
      first.prev=newNode;
      first=newNode;
    }
    size++;
    return true;
  }

  /**
   * This is equivalent dequeue operation in Queue ADT
   * <p>
   * Retrieves and removes the head of the queue represented by this deque (in other words, the
   * first element of this deque).
   *
   * @return the head of the queue represented by this deque
   * @throws NoSuchElementException if this deque is empty
   */
  @Override
  public E remove() {
    if(size== 0){
      throw new NoSuchElementException();
    }else {
      Node<E> oldNode = first;
      first.next.prev = null;
      first = first.next;
      size--;
      return oldNode.item;
    }
  }

  /**
   * Pops an element from the stack represented by this deque. In other words, removes and returns
   * the first element of this deque.
   *
   * @return the element at the front of this deque (which is the top of the stack represented by
   * this deque)
   * @throws NoSuchElementException if this deque is empty
   */
  @Override
  public E pop() {
    Node<E> oldNode = last;
    if(size == 0){
      throw new NoSuchElementException();
    }else {
      last = null;
      size--;
      return oldNode.item;
    }
  }

  /**
   * Pushes an element onto the stack represented by this deque (in other words, at the head of this
   * deque) if it is possible to do so immediately without violating capacity restrictions
   *
   * @param e the element to push
   * @throws NullPointerException if the specified element is null and this deque does not permit
   *                              null elements
   */
  @Override
  public void push(E e) {
    final Node<E> newNode = new Node<>(null, e, null);
    if(size==0){
      first = newNode;
      last = newNode;
    }else{
      last.next=newNode;
      newNode.prev=last;
      last = newNode;
    }
    size++;
  }

  /**
   * Retrieves, but does not remove, the head of the queue represented by this deque (in other
   * words, the first element of this deque), or returns {@code null} if this deque is empty.
   *
   * @return the head of the queue represented by this deque, or {@code null} if this deque is empty
   */
  @Override
  public E peek() {
    if(size == 0){
      throw new EmptyStackException();
    }else{
      return first.item;
    }
  }
}
