package ca.jrvs.practice.codingChallenge;

/**
 *ticket URL: https://www.notion.so/Rotate-String-c239496ba6da4d3cb33457dd119c8683
 */
public class RotateString {

  /**
   * time complicity:O(1)
   * space complicity: O(1)
   * @param A first string
   * @param B second string
   * @return true/false
   */
  public boolean rotateString(String A, String B) {
    if (A.length() != B.length()) {
      return false;
    } else {
      if ((A + A).contains(B)) {
        return true;
      } else {
        return false;
      }
    }
  }
}
