package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Test;

public class ValidParenthesesTest {

  @Test
  public void isValid() {
    ValidParentheses vp = new ValidParentheses();

    assertTrue(vp.isValid("()"));
    assertFalse(vp.isValid("("));
    assertTrue(vp.isValid(""));
    assertTrue(vp.isValid("(){}[]"));
    assertFalse(vp.isValid("(]}"));
    assertTrue(vp.isValid("{[]}"));
    assertFalse(vp.isValid("([)]"));
  }
}