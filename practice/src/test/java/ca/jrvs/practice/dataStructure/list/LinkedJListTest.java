package ca.jrvs.practice.dataStructure.list;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class LinkedJListTest {

  @Test
  public void add() {
    LinkedJList<String> list = new LinkedJList<String>();
    list.add("first");
    assertEquals(list.get(0),"first");
  }

  @Test
  public void toArray() {
    LinkedJList<String> list = new LinkedJList<String>();
    list.add("first");
    list.add("second");
    assertArrayEquals(list.toArray(), new String[]{"first","second"});
  }

  @Test
  public void isEmpty() {
    LinkedJList<String> list = new LinkedJList<String>();
    assertEquals(list.isEmpty(),true);
  }

  @Test
  public void indexOf() {
    LinkedJList<String> list = new LinkedJList<String>();
    list.add("first");
    list.add("second");
    assertEquals(list.indexOf("first"),0);
  }

  @Test
  public void contains() {
    LinkedJList<String> list = new LinkedJList<String>();
    list.add("first");
    list.add("second");
    assertEquals(list.contains("second"),true);
  }

  @Test
  public void get() {
    LinkedJList<String> list = new LinkedJList<String>();
    list.add("first");
    list.add("second");
    assertEquals(list.get(1),"second");
  }

  @Test
  public void remove() {
    LinkedJList<String> list = new LinkedJList<String>();
    list.add("first");
    list.add("second");
    assertEquals(list.remove(1),"second");
  }

  @Test
  public void clear() {
    LinkedJList<String> list = new LinkedJList<String>();
    list.add("first");
    list.clear();
    assertEquals(list.contains("first"),false);
  }
}