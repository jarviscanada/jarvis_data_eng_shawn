package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringOnlyDigitsTest {

  StringOnlyDigits test = new StringOnlyDigits();
  @Test
  public void onlyDigitsASCII() {
    String s = "1234";
    assertTrue(test.onlyDigitsASCII(s));
    s = "125,000";
    assertFalse(test.onlyDigitsASCII(s));
    s = "abc123";
    assertFalse(test.onlyDigitsASCII(s));
    s = "";
    assertFalse(test.onlyDigitsASCII(s));
    s = "abc";
    assertFalse(test.onlyDigitsASCII(s));
  }

  @Test
  public void onlyDigitsAPI() {
    String s = "1234";
    assertTrue(test.onlyDigitsAPI(s));
    s = "125,000";
    assertFalse(test.onlyDigitsAPI(s));
    s = "abc123";
    assertFalse(test.onlyDigitsAPI(s));
    s = "";
    assertFalse(test.onlyDigitsAPI(s));
    s = "abc";
    assertFalse(test.onlyDigitsAPI(s));
  }

  @Test
  public void onlyDigitsRegex() {
    String s = "1234";
    assertTrue(test.onlyDigitsRegex(s));
    s = "125,000";
    assertFalse(test.onlyDigitsRegex(s));
    s = "abc123";
    assertFalse(test.onlyDigitsRegex(s));
    s = "";
    assertFalse(test.onlyDigitsRegex(s));
    s = "abc";
    assertFalse(test.onlyDigitsRegex(s));
  }
}