package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Test;

public class RemoveElementTest {

  @Test
  public void remove() {
    RemoveElement re = new RemoveElement();
    assertEquals(2, re.remove(new int[]{3, 2, 2, 3}, 3));
    assertEquals(5, re.remove(new int[]{0, 1, 2, 2, 3, 0, 4, 2}, 2));
  }
}