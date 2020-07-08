package ca.jrvs.practice.codingChallenge;

/**
 * ticket url: https://www.notion.so/Swap-two-numbers-d279fc3e000b4581aadebf968a97940a
 */
public class SwapNumber {

  public int[] swap1(int[] array){
    array[0] = array[0] ^ array[1];
    array[1] = array[0] ^ array[1];
    array[0] = array[0] ^ array[1];
    return array;
  }

  public int[] swap2(int[] array) {
    array[0] = array[0] + array[1];
    array[1] = array[0] - array[1];
    array[0] = array[0] - array[1];
    return array;
  }
}
