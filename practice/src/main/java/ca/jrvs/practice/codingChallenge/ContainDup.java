package ca.jrvs.practice.codingChallenge;

import java.util.HashSet;

public class ContainDup {
  /**
   * time conplicity: O(nlogn)
   */
  public boolean contains(int[] nums){
    HashSet<Integer> set=new HashSet<>();
    for(int n:nums){
      if(set.contains(n)){
        return true;
      }else{
        set.add(n);
      }
    }
    return false;
  }

}
