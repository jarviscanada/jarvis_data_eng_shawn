package ca.jrvs.practice.codingChallenge;


public class EvenOrOdd {

  /**
   * approach1 using modulo
   * time complicity:O(1)
   * space complicity: O(1)
   * @param num
   * @return
   */
  public String EvenOrOdd(Integer num){
    if(num==0){
      return "not even or odd";
    }
    return num%2==1? "odd":"even";
  }

  /**
   *
   * @param num
   * @return
   */
  public String OddEven(Integer num){
    if(num==0) return "not odd or even";
    if((num&1)==0) {
      return "even";
    }else{
      return "odd";
    }
  }
}
