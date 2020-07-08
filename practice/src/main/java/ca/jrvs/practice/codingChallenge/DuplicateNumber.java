package ca.jrvs.practice.codingChallenge;

import java.util.HashMap;
import java.util.Map;

public class DuplicateNumber {
  public int duplicate(int[] nums){
    Map<Integer,Integer> map=new HashMap();
      for (int i = 0; i < nums.length; i++) {
        if (!map.containsKey(nums[i])) {
          map.put(nums[i], 1);
        } else {
          return nums[i];
        }
      }
    return -1;
  }

}
