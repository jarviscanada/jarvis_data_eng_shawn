package ca.jrvs.practice.codingChallenge;

import java.util.Stack;

public class QueueUsingStack2 {
  public Stack<Integer> s1;
  public Stack<Integer> s2;

  public QueueUsingStack2(){
    s1=new Stack<>();
    s2=new Stack<>();
  }

  public void push(int x){
    while(!s1.isEmpty()){
      s2.push(s1.pop());
    }
    s1.push(x);
    while(!s2.isEmpty()){
      s1.push(s2.pop());
    }
  }

  public int pop(){
    return s1.pop();
  }

  public int peek() {
    return s1.peek();
  }

  public boolean empty() {
    return s1.isEmpty();
  }
}
