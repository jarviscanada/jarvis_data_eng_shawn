package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.testng.annotations.Test;

public class MapComparisonTest {

  @Test
  public void compareMaps() {
    MapComparison mapComparison = new MapComparison();
    Map<Integer, String> m1 = new HashMap<Integer, String>();
    m1.put(1, "one");
    m1.put(4, "one");
    m1.put(2, "two");
    m1.put(3, "three");
    Map<Integer, String> m2 = new HashMap<Integer, String>();
    m2.put(1, "one");
    m2.put(2, "two");
    m2.put(3, "three");
    Map<Integer, String> m3 = new HashMap<Integer, String>();
    m2.put(1, "one");
    m2.put(2, "two");
    m2.put(3, "three");

    Assert.assertEquals(false, mapComparison.compareMaps(m1, m2));
    Assert.assertEquals(false, mapComparison.compareMaps(m2, m3));
  }

  @Test
  public void compareMapsWithHashMap() {
    MapComparison mapComparison = new MapComparison();
    Map<Integer, String> m1 = new HashMap<Integer, String>();
    m1.put(1, "one");
    m1.put(4, "one");
    m1.put(2, "two");
    m1.put(3, "three");
    Map<Integer, String> m2 = new HashMap<Integer, String>();
    m2.put(1, "one");
    m2.put(2, "two");
    m2.put(3, "three");
    Map<Integer, String> m3 = new HashMap<Integer, String>();
    m2.put(1, "one");
    m2.put(2, "two");
    m2.put(3, "three");

    Assert.assertEquals(false, mapComparison.compareMapsWithHashMap(m1, m2));
    Assert.assertEquals(false, mapComparison.compareMapsWithHashMap(m2, m3));
  }
}