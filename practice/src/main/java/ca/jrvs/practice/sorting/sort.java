package ca.jrvs.practice.sorting;

import java.util.Arrays;

public class sort {

  public static void main(String[] args) {
    int a[]={2,4,6,8,9,4,5};
    Quicksort b= new Quicksort();
    b.quickSort(a,0,6);
    System.out.println(Arrays.toString(a));
  }
}
