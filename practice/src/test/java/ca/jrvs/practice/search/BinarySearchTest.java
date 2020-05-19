package ca.jrvs.practice.search;

import static org.junit.Assert.*;

import java.util.Optional;
import org.junit.Test;

public class BinarySearchTest {

  @Test
  public void binarySearchRecursion() {
    BinarySearch binarySearch = new BinarySearch();
    Integer[] array = {-5,-2,-1,0,5,8,9,111};

    Optional<Integer> index = binarySearch.binarySearchIteration(array, -5);
    assertEquals(Optional.of(0), index);

    index = binarySearch.binarySearchIteration(array, 0);
    assertEquals(Optional.of(3), index);

    index = binarySearch.binarySearchIteration(array, 8);
    assertEquals(Optional.of(5), index);

    index = binarySearch.binarySearchIteration(array, 15);
    assertEquals(Optional.empty(), index);
  }

  @Test
  public void binarySearchIteration() {
    BinarySearch binarySearch = new BinarySearch();
    Integer[] array = {-5,-2,-1,0,5,8,9,111};

    Optional<Integer> index = binarySearch.binarySearchIteration(array, -5);
    assertEquals(Optional.of(0), index);

    index = binarySearch.binarySearchIteration(array, 0);
    assertEquals(Optional.of(3), index);

    index = binarySearch.binarySearchIteration(array, 8);
    assertEquals(Optional.of(5), index);

    index = binarySearch.binarySearchIteration(array, 15);
    assertEquals(Optional.empty(), index);
  }
}