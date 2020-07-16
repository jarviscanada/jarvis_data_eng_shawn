package ca.jrvs.practice.codingChallenge;

/**
 * ticker url: https://www.notion.so/LinkedList-Cycle-ca7087159152434f9fc07f475498b729
 */
class ListNode1 {
  int val;
  ListNode1 next;

  ListNode1(int x) {
    val = x;
    next = null;
  }
}

public class LinkedListCycle {
  /**
   * approach1, using two pointers
   * time complicity:O(n)
   * space complicity: O(n)
   * @param head ListNode1
   * @return true/false
   */
       public boolean hasCycle(ListNode1 head) {
         ListNode1 b =head;
         while(b!=null&&b.next!=null){
           if(head==b){return true;}
           head=head.next;
           b=b.next.next;
         }
         return false;
       }
}


