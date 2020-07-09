package ca.jrvs.practice.codingChallenge;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * ticket URL https://www.notion.so/How-to-compare-two-maps-afad00a29a7f41b6868aa1ee82b20841
 */
public class MapComparison {

  /**
   *time complicity: O(n)
   *justification: iterates through entry set of each map only when their sizes are
   *equal.
   */
  public  <K,V> boolean compareMaps(Map<K,V> m1, Map<K,V> m2){
    if(m1.equals(m2)){
      return true;
    }
    return false;
  }


  public <K, V> boolean compareMapsWithHashMap(Map<K, V> m1, Map<K, V> m2){
    HashMap<K,V> hashMap1 = (HashMap<K,V>) m1;
    HashMap<K,V> hashMap2 = (HashMap<K,V>) m2;
    Set<Map.Entry<K,V>>entrySet1 = hashMap1.entrySet();
    Set<Map.Entry<K,V>>entrySet2 = hashMap2.entrySet();
    if(entrySet1.equals(entrySet2)){
      return true;
    }
    return false;
  }
}
