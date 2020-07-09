package ca.jrvs.practice.codingChallenge;

/**
 * https://www.notion.so/Fibonacci-Number-Climbing-Stairs-822951ca0bf044ddace1dc6b630ea315
 */
public class Fibonacci {

  /**
   * DP
   * Time complexity: O(n)
   * Space complexity: O(n)
   * @param n
   * @return
   */
  public int fib(int n) {
    int[] contain = new int[n+1];
    return mem(n,contain);
  }

  public int mem(int n,int[] contain) {
    int result = 0;
    if (contain[n] != 0) {
      return contain[n];
    }
    if (n <= 1) {
      result = n;
    } else {
      result = mem(n - 1, contain) + mem(n - 2, contain);
    }
    contain[n] = result;
    return result;
  }

  /**
   * recursion
   * Time complexity: O(2^n)- since T(n) = T(n-1) + T(n-2)is an exponential time
   * Space complexity: O(n) - space for recursive function call stack
   * @param n
   * @return
   */
  public int fibna(int n){
    if(n<=1){
      return n;
    }else {
      return fibna(n - 1) + fibna(n - 2);
    }
  }
}
