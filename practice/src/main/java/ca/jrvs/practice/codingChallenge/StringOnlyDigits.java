package ca.jrvs.practice.codingChallenge;

/**
 * ticker url: https://www.notion.so/Check-if-a-String-contains-only-digits-dce7131bbbb84b9bac1ec73d2da51425
 */
public class StringOnlyDigits {
  public boolean onlyDigitsASCII(String s){
    if (s.length() == 0) {
      return false;
    }
    char[] arr=s.toCharArray();
    for(int i=0;i<s.length();i++){
      if(arr[i]<'0'||arr[i]>'9'){
        return false;
      }
    }
    return true;
  }

  public boolean onlyDigitsAPI(String s){
    try{
      Integer.valueOf(s);
    }catch(NumberFormatException e){
      return false;
    }
    return true;
  }

  public boolean onlyDigitsRegex(String s){
    if(s.matches("\\d+")){
      return true;
    }else {
      return false;
    }
  }
}
