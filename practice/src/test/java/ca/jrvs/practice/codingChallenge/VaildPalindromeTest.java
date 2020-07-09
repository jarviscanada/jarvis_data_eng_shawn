package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Test;

public class VaildPalindromeTest {

  @Test
  public void isPalindrome() {
    String s1="abddba";
    String s2="abdd,ba";
    String s3="a bdd ba";
    String s4="abd2dba";
    String s5="Abddba";
    VaildPalindrome vp=new VaildPalindrome();
    assertEquals(true,vp.isPalindrome(s1));
    assertEquals(false,vp.isPalindrome(s2));
    assertEquals(true,vp.isPalindrome(s3));
    assertEquals(true,vp.isPalindrome(s4));
    assertEquals(true,vp.isPalindrome(s5));
  }
}