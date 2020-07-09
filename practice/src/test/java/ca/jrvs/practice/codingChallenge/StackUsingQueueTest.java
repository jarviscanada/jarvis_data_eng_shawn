package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Test;

public class StackUsingQueueTest {
  StackUsingQueue stack = new StackUsingQueue();

  @Test
  public void push() {
    stack.push(1);
    assertEquals(1, stack.top());
    stack.push(2);
    assertEquals(2, stack.top());
  }

  @Test
  public void pop() {
    stack.push(1);
    stack.push(2);
    assertEquals(2, stack.pop());
  }

  @Test
  public void top() {
    stack.push(1);
    stack.push(2);
    stack.pop();
    assertEquals(1, stack.top());
  }

  @Test
  public void empty() {
    assertEquals(true, stack.empty());
    stack.push(5);
    assertEquals(false, stack.empty());
  }
}