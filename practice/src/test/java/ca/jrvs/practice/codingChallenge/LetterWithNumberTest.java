package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Test;

public class LetterWithNumberTest {

  @Test
  public void letterWithNumber() {
    LetterWithNumber letterWithNumber = new LetterWithNumber();
    assertEquals("a1b2A27z26B28", letterWithNumber.letterWithNumber("abAzB"));
  }
}