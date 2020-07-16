package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Test;

public class VaildAnagramTest {

  @Test
  public void isAnagram() {
    VaildAnagram validAnagram = new VaildAnagram();

    assertEquals(true, validAnagram.isAnagram("", ""));
    assertEquals(false, validAnagram.isAnagram("", " "));
    assertEquals(false, validAnagram.isAnagram("", "a"));
    assertEquals(true, validAnagram.isAnagram("a", "a"));
    assertEquals(false, validAnagram.isAnagram("a", "b"));
    assertEquals(false, validAnagram.isAnagram("a", "ab"));
    assertEquals(false, validAnagram.isAnagram("ab", "b"));
    assertEquals(true, validAnagram.isAnagram("anagram", "nagaram"));
    assertEquals(false, validAnagram.isAnagram("rat", "cat"));
    assertEquals(false, validAnagram.isAnagram("anagram", "anagrams"));
  }

  @Test
  public void testIsAnagramWithoutSorting() {
    VaildAnagram validAnagram = new VaildAnagram();

    assertEquals(true, validAnagram.isAnagramWithoutSorting("", ""));
    assertEquals(false, validAnagram.isAnagramWithoutSorting("", " "));
    assertEquals(false, validAnagram.isAnagramWithoutSorting("", "a"));
    assertEquals(true, validAnagram.isAnagramWithoutSorting("a", "a"));
    assertEquals(false, validAnagram.isAnagramWithoutSorting("a", "b"));
    assertEquals(false, validAnagram.isAnagramWithoutSorting("a", "ab"));
    assertEquals(false, validAnagram.isAnagramWithoutSorting("ab", "b"));
    assertEquals(true, validAnagram.isAnagramWithoutSorting("anagram", "nagaram"));
    assertEquals(false, validAnagram.isAnagramWithoutSorting("rat", "cat"));
    assertEquals(false, validAnagram.isAnagramWithoutSorting("anagram", "anagrams"));
  }
}