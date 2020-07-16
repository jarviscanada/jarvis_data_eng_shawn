package ca.jrvs.practice.codingChallenge;

/**
 * ticket URL: https://www.notion.so/Nth-Node-From-End-of-LinkedList-8a2aa1dcd59b4e4499b20537094a7a50
 */
public class RemoveNode {
   public static class ListNode {
     int val;
     ListNode next;
     ListNode() {}
     ListNode(int val) { this.val = val; }
     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 }

  /**
   * approach1, using linkedlist
   * time complicity:O(n)
   * space complicity: O(n)
   * @param head ListNode
   * @param n n th node from end
   * @return true/false
   */
    public ListNode removeNthFromEnd(ListNode head, int n) {
      if(head.next==null||head==null){
        return null;
      }
      ListNode target=head.next;
      ListNode first=head;
      int i=size(head)-n+1;
      if(i==1){
        return target;
      }
      for(int j=2;j<i;j++){
        head=head.next;
        target=target.next;
      }
      if(target.next==null){
        head.next=null;
      }else{
        head.next=target.next;
      }
      return first;
    }

    public int size(ListNode head){
      ListNode a=head;
      int i=0;
      while(a!=null){
        a=a.next;
        i++;
      }
      return i;
    }

  }
