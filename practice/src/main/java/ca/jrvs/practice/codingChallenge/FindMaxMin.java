package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * ticket url: https://www.notion.so/Find-Largest-Smallest-c99139e521604048823c00ac9fa0739e
 */
public class FindMaxMin {

  /**
   * approach1, using for loop
   * time complicity:O(n)
   * space complicity: O(n)
   * @param array int[]
   * @return maxmin array
   */
  public int[] findOneLoop(int[] array) {
    int[] maxMin = new int[2]; //index 0 will have max number, index 1 will have min number
    maxMin[0] = array[0];
    maxMin[1] = array[0];
    for (int i = 1; i < array.length; i++) {
      if (maxMin[0] < array[i]) {
        maxMin[0] = array[i];
      }
      if (maxMin[1] > array[i]) {
        maxMin[1] = array[i];
      }
    }
    return maxMin;
  }

  /**
   * approach1, using stream api
   * time complicity:O(n)
   * space complicity: O(n)
   * @param array int[]
   * @return maxmin array
   */
  public int[] findStreamAPI(int[] array) {
    int[] maxMin = new int[2]; //index 0 will have max number, index 1 will have min number
    maxMin[0] = Arrays.stream(array).max().getAsInt();
    maxMin[1] = Arrays.stream(array).min().getAsInt();
    return maxMin;
  }


  /**
   * approach1, using java api
   * time complicity:O(n)
   * space complicity: O(n)
   * @param list List<>
   * @return maxmin array
   */
  public int[] findJavaAPI(List<Integer> list) {
    int[] maxMin = new int[2]; //index 0 will have max number, index 1 will have min number
    maxMin[0] = Collections.max(list);
    maxMin[1] = Collections.min(list);
    return maxMin;
  }
}
