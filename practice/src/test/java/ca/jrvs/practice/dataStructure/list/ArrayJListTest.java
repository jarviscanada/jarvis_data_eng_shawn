package ca.jrvs.practice.dataStructure.list;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ArrayJListTest {

  @Test
  public void add() {
    List<String> list = new ArrayList<>();
    list.add("first");
    assertEquals(list.get(0),"first");
  }

  @Test
  public void toArray() {
    ArrayJList<String> list = new ArrayJList<String>();
    list.add("first");
    list.add("second");
    assertArrayEquals(list.toArray(), new String[]{"first","second"});
  }

  @Test
  public void isEmpty() {
    ArrayJList<String> list = new ArrayJList<String>();
    assertEquals(list.isEmpty(),true);
  }

  @Test
  public void indexOf() {
    ArrayJList<String> list = new ArrayJList<String>();
    list.add("first");
    list.add("second");
    assertEquals(list.indexOf("first"),0);
  }

  @Test
  public void contains() {
    ArrayJList<String> list = new ArrayJList<String>();
    list.add("first");
    list.add("second");
    assertEquals(list.contains("second"),true);
  }

  @Test
  public void get() {
    ArrayJList<String> list = new ArrayJList<String>();
    list.add("first");
    list.add("second");
    assertEquals(list.get(1),"second");
  }

  @Test
  public void remove() {
    ArrayJList<String> list = new ArrayJList<String>();
    list.add("first");
    list.add("second");
    assertEquals(list.remove(1),"second");
  }

  @Test
  public void clear() {
    ArrayJList<String> list = new ArrayJList<String>();
    list.add("first");
    list.clear();
    assertEquals(list.contains("first"),false);
  }
}