package ca.jrvs.practice.codingChallenge;

import java.util.Stack;

public class QueueUsingStack {

  Stack<Integer>s1;
  Stack<Integer>s2;

  public QueueUsingStack(){
    s1=new Stack<>();
    s2=new Stack<>();
  }

  public void push(int x){
    s1.push(x);
  }

  public int pop(){
    int last=-1;
    while(!s1.isEmpty()){
      last=s1.pop();
      if(!s1.isEmpty()){
        s2.push(last);
      }
    }
    while(!s2.isEmpty()){
      s1.push(s2.pop());
    }
    return last;
  }

  public int peek(){
    int last=-1;
    while(!s1.isEmpty()){
      last=s1.pop();
      s2.push(last);
    }
    while(!s2.isEmpty()){
      s1.push(s2.pop());
    }
    return last;
  }

  public boolean empty() {
    return s1.isEmpty();
  }
}
