package ca.jrvs.practice.codingChallenge;

/**
 * ticket URL: https://www.notion.so/Valid-Anagram-cb348f2555704ecf9abedf20008cfea5
 */

import java.util.Arrays;

public class VaildAnagram {

  /**
   * approach1, using sort
   * time complicity:O(n)
   * space complicity: O(n)
   * @param s first string
   * @param t second string
   * @return true/false
   */
  public boolean isAnagram(String s, String t){
    if(s.equals(t))return true;
    char[] s1=s.toCharArray();
    char[] t1=t.toCharArray();
    Arrays.sort(s1);
    Arrays.sort(t1);
    StringBuilder s2=new StringBuilder("");
    StringBuilder t2=new StringBuilder("");
    s2.append(s1);
    t2.append(t1);
    if(s2.toString().equals(t2.toString())) return true;
    return false;
  }

  /**
   * approach1, using array
   * time complicity:O(n)
   * space complicity: O(n)
   * @param s first string
   * @param t second string
   * @return true/false
   */
  public boolean isAnagramWithoutSorting(String s, String t) {
    if (s.length() != t.length()) {
      return false;
    }
    int[] check = new int[26];
    boolean allZeroes = true;
    for (int i = 0; i < s.length(); i++) {
      check[s.charAt(i) - 'a']++;
      check[t.charAt(i) - 'a']--;
    }
    for (int i = 0; i < 26; i++) {
      if (check[i] != 0) {
        return false;
      }
    }
    return true;
  }
}
