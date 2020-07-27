package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Test;

public class EvenOrOddTest {

  @Test
  public void evenOrOdd() {
    EvenOrOdd evenOrOdd = new EvenOrOdd();
    assertEquals("odd",evenOrOdd.EvenOrOdd(5));
    assertEquals("even",evenOrOdd.EvenOrOdd(6));
    assertEquals("not even or odd",evenOrOdd.EvenOrOdd(0));
  }

  @Test
  public void oddEven() {
    EvenOrOdd evenOrOdd = new EvenOrOdd();
    assertEquals("odd",evenOrOdd.OddEven(5));
    assertEquals("even",evenOrOdd.OddEven(6));
    assertEquals("not odd or even",evenOrOdd.OddEven(0));
  }
}