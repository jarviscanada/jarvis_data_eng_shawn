package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringToIntegerTest {

  @Test
  public void atoiWithAPI() {
    String s="3251fsaa ";
    String d=" -1231de ";
    String f="adsa  ";
    StringToInteger stringToInteger=new StringToInteger();
    assertEquals(3251,stringToInteger.atoiWithAPI(s));
    assertEquals(-1231,stringToInteger.atoiWithAPI(d));
    assertEquals(0,stringToInteger.atoiWithAPI(f));
  }

  @Test
  public void atoiWithoutAPI() {
    String s="3251fsaa ";
    String d=" -1231de ";
    String f="adsa  ";
    StringToInteger stringToInteger=new StringToInteger();
    assertEquals(3251,stringToInteger.atoiWithAPI(s));
    assertEquals(-1231,stringToInteger.atoiWithAPI(d));
    assertEquals(0,stringToInteger.atoiWithAPI(f));
  }
}