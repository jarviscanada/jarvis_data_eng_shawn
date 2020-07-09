package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Test;

public class SwapNumberTest {

  SwapNumber sn=new SwapNumber();
  int[] array = {-2,3};
  @Test
  public void swap1() {
    assertArrayEquals(new int[]{3,-2}, sn.swap1(array));
  }

  @Test
  public void swap2() {
    assertArrayEquals(new int[]{3,-2}, sn.swap2(array));
  }
}