package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Test;

public class FibonacciTest {

  @Test
  public void fib() {
    Fibonacci fibonacci=new Fibonacci();
    assertEquals(0,fibonacci.fib(0));
    assertEquals(2,fibonacci.fib(3));
    assertEquals(1,fibonacci.fib(2));
    assertEquals(3,fibonacci.fib(4));
  }

  @Test
  public void fibna() {
    Fibonacci fibonacci=new Fibonacci();
    assertEquals(0,fibonacci.fibna(0));
    assertEquals(2,fibonacci.fibna(3));
    assertEquals(1,fibonacci.fibna(2));
    assertEquals(3,fibonacci.fibna(4));
  }
}