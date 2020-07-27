package ca.jrvs.practice.codingChallenge;

import java.util.LinkedList;
import java.util.Queue;

public class StackUsingQueue2 {
  Queue<Integer> q1;
  public StackUsingQueue2(){
    q1=new LinkedList<>();
  }

  public void push(int x){
    q1.add(x);
  }

  public int pop(){
    int last = -1;
    int size = q1.size();
    for (int i = 0; i < q1.size(); i++) {
      last=q1.remove();
      if (i<size-1) {
        q1.add(last);
      }
    }
    return last;
  }

  public int top(){
    int last=-1;
    for(int i=0;i<q1.size();i++){
      last= q1.remove();
      q1.add(last);
    }
    return last;
  }

  public boolean empty(){
    return q1.isEmpty();
  }
}
