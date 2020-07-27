package ca.jrvs.practice.codingChallenge;

/**
 * ticket url: https://www.notion.so/Print-letter-with-number-1d479b3ba3294539a1f67f3a72c24c99
 */
public class LetterWithNumber {

  /**
   * approach1, using for loop
   * time complicity:O(n)
   * space complicity: O(n)
   * @param s String
   * @return newString
   */
  public String letterWithNumber(String s){
    if(s==null) return null;
    char[] array=s.toCharArray();
    StringBuilder newString = new StringBuilder("");
    for(int i=0;i<array.length;i++){
      newString.append(array[i]);
      if (array[i] >= 'a' && array[i] <= 'z') {
        newString.append((int) array[i] - 'a' + 1);
      } else {
        newString.append((int) array[i] - 'A' + 27);
      }
    }
    return newString.toString();
  }
}
