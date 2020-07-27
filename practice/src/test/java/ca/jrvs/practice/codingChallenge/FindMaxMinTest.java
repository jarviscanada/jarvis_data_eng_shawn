package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class FindMaxMinTest {

  FindMaxMin f=new FindMaxMin();
  @Test
  public void testFindOneLoop() {
    int [] array = {9, -5, 0, 7, 8, -13};
    assertArrayEquals(new int[]{9,-13}, f.findOneLoop(array));
  }

  @Test
  public void testFindStreamAPI() {
    int [] array = {9, -5, 0, 7, 8, -13};
    assertArrayEquals(new int[]{9,-13}, f.findStreamAPI(array));
  }

  @Test
  public void testFindJavaAPI() {
    List<Integer> list = Arrays.asList(9, -5, 0, 7, 8, -13);
    assertArrayEquals(new int[]{9,-13}, f.findJavaAPI(list));
  }
}