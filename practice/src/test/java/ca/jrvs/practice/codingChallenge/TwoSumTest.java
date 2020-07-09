package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TwoSumTest {

  @Test
  public void twoSum1() {
    int[] mOne = new int[4];
    mOne[0]=5;
    mOne[1]=8;
    mOne[2]=2;
    mOne[3]=1;
    int[] mTwo = new int[]{5,7,9,11,12,15,8};
    int[] mThree = new int[]{3,2,4};
    TwoSum twoSum=new TwoSum();
    assertArrayEquals(new int[]{0,2},twoSum.twoSum1(7,mOne));
    assertArrayEquals(new int[]{0,6},twoSum.twoSum1(13,mTwo));
    assertArrayEquals(new int[]{0,2},twoSum.twoSum1(7,mThree));
    assertEquals(null,twoSum.twoSum1(1,new int[]{5}));
  }

  @Test
  public void twoSum2() {
    int[] mOne = new int[4];
    mOne[0]=5;
    mOne[1]=8;
    mOne[2]=2;
    mOne[3]=1;
    int[] mTwo = new int[]{5,7,9,11,12,15,8};
    int[] mThree = new int[]{3,2,4};
    TwoSum twoSum=new TwoSum();
    assertArrayEquals(new int[]{2,0},twoSum.twoSum2(7,mOne));
    assertArrayEquals(new int[]{6,0},twoSum.twoSum2(13,mTwo));
    assertArrayEquals(new int[]{2,0},twoSum.twoSum2(7,mThree));
    assertEquals(null,twoSum.twoSum2(1,new int[]{5}));
  }

  @Test
  public void twoSum3() {
    int[] mOne = new int[4];
    mOne[0]=5;
    mOne[1]=8;
    mOne[2]=2;
    mOne[3]=1;
    int[] mTwo = new int[]{5,7,9,11,12,15,8};
    int[] mThree = new int[]{3,2,4};
    TwoSum twoSum=new TwoSum();
    assertArrayEquals(new int[]{2,5},twoSum.twoSum3(7,mOne));
    assertArrayEquals(new int[]{5,8},twoSum.twoSum3(13,mTwo));
    assertArrayEquals(new int[]{3,4},twoSum.twoSum3(7,mThree));
    assertEquals(null,twoSum.twoSum3(1,new int[]{5}));
  }
}