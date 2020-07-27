package ca.jrvs.practice.codingChallenge;

import java.util.stream.IntStream;

/**
 * ticket url: https://www.notion.so/Missing-Number-932d07a55e454926b14fa21a5d342ab1
 */
public class MissingNumber {

  public int missed(int[] nums){
    long n = nums.length;
    return (int) (n * (n+1) / 2 - IntStream.of(nums).sum());
  }
}
