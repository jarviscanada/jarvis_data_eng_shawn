package ca.jrvs.practice.codingChallenge;

/**
 * ticket url: https://www.notion.so/Count-Primes-5c4f9ff88b874f9fa0e31fc783e9a5cd
 */
public class CountPrim {

  /**
   * using two for loops
   * time complexity: O(n^2)
   * @param a
   * @return
   */
  public int countPrime(int a){
    if(a==1) return 0;
    int count=0;
    for(int i=1;i<=a;i++){
      if(checkprime(i)) count++;
    }
    return count;
  }

  public boolean checkprime(int n){
    int count =0;
    for(int i=1;i<=n;i++){
      if(n%i==0) count++;
    }
    if(count==2)return true;
    return false;
  }
}
