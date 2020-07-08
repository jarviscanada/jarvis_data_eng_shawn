package ca.jrvs.practice.codingChallenge;

import java.util.HashSet;

public class RemoveDuplicate {
  public int removeDuplicate(int[] nums){
    HashSet<Integer> set=new HashSet();
    int k =0;
    for(int n:nums){
      if(!set.contains(n)){
        nums[k++]=n;
        set.add(n);
      }
    }
    return k;
  }

}
