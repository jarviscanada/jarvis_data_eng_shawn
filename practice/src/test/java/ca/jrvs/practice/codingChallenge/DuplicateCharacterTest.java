package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Test;

public class DuplicateCharacterTest {

  DuplicateCharacter dp = new DuplicateCharacter();
  @Test
  public void duplicate() {
    assertArrayEquals(new String[]{"c","a"}, dp.duplicate("A black cat"));
  }
}