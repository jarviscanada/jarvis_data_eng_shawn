package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Test;

public class RotateStringTest {

  @Test
  public void rotateString() {
    RotateString test = new RotateString();
    assertTrue(test.rotateString("abcde", "cdeab"));
    assertFalse(test.rotateString("abcde", "cdaeb"));
    assertFalse(test.rotateString("abcde", "abcd"));
    assertFalse(test.rotateString("abcde", "bcda"));
    assertTrue(test.rotateString("", ""));
    assertFalse(test.rotateString("", " "));
  }
}