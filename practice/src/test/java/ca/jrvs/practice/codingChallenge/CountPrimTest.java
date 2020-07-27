package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Test;

public class CountPrimTest {

  CountPrim cp=new CountPrim();
  @Test
  public void countPrime() {
    assertEquals(4,cp.countPrime(10));
    assertEquals(5,cp.countPrime(12));
    assertEquals(1,cp.countPrime(2));
  }
}