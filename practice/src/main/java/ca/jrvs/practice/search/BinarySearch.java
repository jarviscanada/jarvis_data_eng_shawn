package ca.jrvs.practice.search;

import java.util.Optional;

public class BinarySearch {
  public <E extends Comparable<E>> Optional<Integer> binarySearchRecursion(E[] arr, E target) {
    int left= 0;
    int right = arr.length - 1;

    return binarySearch(arr, target, left, right);
  }

  /**
   * recursively calls itself on any half of its input array based on comparison with target value
   *
   * @param arr    input sorted array
   * @param target value to be searched
   * @param left  of range
   * @param right    of range
   * @param <E>
   * @return target index or optional
   */
  private <E extends Comparable<E>> Optional<Integer> binarySearch(E[] arr, E target, int left,
      int right) {
    if (left <= right) {
      int mid = (left + right) / 2;

      if (arr[mid].compareTo(target) == 0) {
        return Optional.of(mid);
      } else if (arr[mid].compareTo(target) < 0) {
        return binarySearch(arr, target, mid + 1, right);
      } else {
        return binarySearch(arr, target, left, mid - 1);
      }
    } else {
      return Optional.empty();
    }
  }


  /**
   * find the the target index in a sorted array
   *
   * @param arr    input arry is sorted
   * @param target value to be searched
   * @return target index or Optional.empty() if not ound
   */
  public <E extends Comparable<E>> Optional<Integer> binarySearchIteration(E[] arr, E target) {
    int left = 0;
    int right = arr.length - 1;

    while (left <= right) {
      int mid = (left + right) / 2;
      if (arr[mid].compareTo(target) == 0) {
        return Optional.of(mid);
      } else if (arr[mid].compareTo(target) < 0) {
        left = mid + 1;
      } else {
        right = mid - 1;
      }
    }
    return Optional.empty();
  }
}
