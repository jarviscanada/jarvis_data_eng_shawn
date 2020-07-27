package ca.jrvs.practice.codingChallenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * ticket url: https://www.notion.so/Duplicate-Characters-93a4b153528d4919b69b63eaed826181
 */
public class DuplicateCharacter {

  public String[] duplicate(String s){
    Set<Character> set = new HashSet<>();
    List<String> result = new ArrayList<>();
    for(int i=0;i<s.length();i++){
      if(s.charAt(i)!=' '){
        if(set.contains(s.charAt(i))){
          result.add(Character.toString(s.charAt(i)));
        }else{
          set.add(s.charAt(i));
        }
      }
    }
    String[] arr=new String[result.size()];
    return result.toArray(arr);
  }

}
