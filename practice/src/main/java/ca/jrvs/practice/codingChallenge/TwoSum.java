package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TwoSum {

  /**
   * approach1 using two for loops
   * time complicity:O(n^2)
   * space complicity: O(1)
   * @param n
   * @param m
   * @return
   */
  public int[] twoSum1(int n, int[] m) {
    for (int i = 0; i < m.length; i++) {
      for (int j = i+1; j < m.length; j++) {
        if (m[i] + m[j] == n) {
          return new int[]{i,j};
        }
      }
    }
    return null;
  }

  /**
   * approach2 using hashmap
   * time complicity:O(n)
   * space complicity: O(n)
   * @param n
   * @param m
   * @return
   */
  public int[] twoSum2(int n, int[] m) {
    Map<Integer, Integer> map = new HashMap<Integer, Integer>();
    if (m.length < 2)
      return null;
    for (int i = 0; i < m.length; i++) {
      if (map.containsKey(m[i])) {
        return new int[]{i, map.get(m[i])};
      }
      map.put(n - m[i], i);
    }
    throw new IllegalArgumentException("no solution");
  }


  /**
   * approach3 using sorting method
   * time complicity:O(n*log(n))
   * space complicity: O(n)
   * @param n
   * @param m
   * @return
   */
  public int[] twoSum3(int n, int[] m) {
    Arrays.sort(m);
    int i = 0;
    int j = m.length - 1;
    if (m.length < 2)
      return null;
    while (j > i) {
      if (m[i] + m[j] > n) {
        j--;
      }else if(m[i] + m[j] < n) {
        i++;
      }else{
        return new int[]{m[i], m[j]};
      }
    }
    return null;
  }
}

