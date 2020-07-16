package ca.jrvs.practice.codingChallenge;

import java.util.List;

class ListNode3 {
  int val;
  ListNode3 next;
  ListNode3() {}
  ListNode3(int val) { this.val = val; }
  ListNode3(int val, ListNode3 next) { this.val = val; this.next = next; }
}

public class ReverseLinkedList {
  public ListNode3 reverseList(ListNode3 head) {
    if(head==null)return null;
    ListNode3 current = head;
    ListNode3 next=current.next;
    ListNode3 previous=null;
    while(current!=null){
      next=current.next;
      current.next=previous;
      previous=current;
      current=next;
    }
    return previous;
  }

}
