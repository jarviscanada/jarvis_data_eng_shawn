package ca.jrvs.practice.codingChallenge;

/**
 * ticket url:https://www.notion.so/Remove-Element-6de959d2a29f4e9aae013f8b5b5298e5
 */
public class RemoveElement {
  public int remove(int[] nums, int val){
    if (nums == null) return -1;
    if (nums.length == 0) return 0;
    int k = 0;
    for (int n: nums) {
      if (n != val) nums[k++] = n;
    }
    return k;
  }

}
