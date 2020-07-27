package ca.jrvs.practice.codingChallenge;

import java.util.LinkedList;
import java.util.Queue;

public class StackUsingQueue {
  Queue<Integer> q1;
  Queue<Integer> q2;

  public StackUsingQueue(){
    q1=new LinkedList<>();
    q2=new LinkedList<>();
  }

  public void push(int x){
    q1.add(x);
  }

  public int pop(){
    int last=-1;
    while(!q1.isEmpty()){
      last=q1.remove();
      if(!q1.isEmpty()){
        q2.add(last);
      }
    }
   q1=q2;
    return last;
  }


  /**
   * Big-O: O(n), where n is the number of elements in stack
   * Justification: Remove all the elements until the last one is reached
   *
   * Get the top element.
   */
  public int top(){
    int last=-1;
    while(!q1.isEmpty()){
      last=q1.remove();
      if(!q1.isEmpty()){
        q2.add(last);
      }
    }
    q1=q2;
    return last;
  }

  public boolean empty(){
    return q1.isEmpty();
  }
}
