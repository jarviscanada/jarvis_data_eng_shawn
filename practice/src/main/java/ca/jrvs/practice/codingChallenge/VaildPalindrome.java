package ca.jrvs.practice.codingChallenge;

/**
 * ticket URl: https://www.notion.so/Valid-Palindrome-574851fea3c04ed2842ecfb4ab7c9538
 */
public class VaildPalindrome {

  /**
   * approach1 using one for loop
   * time complicity:O(n)
   * space complicity: O(1)
   * @param s
   * @return
   */
    public boolean isPalindrome(String s) {
      s=s.trim().toLowerCase();
      if(s=="")return true;
      char[] arr=s.toCharArray();
      int i=0;int j=s.length()-1;
      for(int k=j;k>0;k--){
        if(arr[i]==arr[j]){
          i++;
          j--;
        }else if(arr[i]<41||(arr[i]>=91&&arr[i]<=96)||arr[i]>122){
          i++;
        }else if(arr[j]<41||(arr[j]>=91&&arr[j]<=96)||arr[j]>122){
          j--;
        }
      }
      if(arr[i]==arr[j])return true;
      return false;
    }
}
