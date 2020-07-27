package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Test;

public class ContainDupTest {

  @Test
  public void contains() {
    ContainDup cd= new ContainDup();
    int[] a=new int[]{1,2,3,1};
    assertEquals(true,cd.contains(a));
    assertEquals(false,cd.contains(new int[]{1,2,3}));
  }
}