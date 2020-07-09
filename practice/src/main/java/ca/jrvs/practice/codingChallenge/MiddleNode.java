package ca.jrvs.practice.codingChallenge;

/**
 * ticket URL: https://www.notion.so/Middle-of-the-Linked-List-83ed73f98be347bc9cf5f6794392606e
 */
class ListNode {
  int val;
  ListNode3 next;
  ListNode() {}
  ListNode(int val) { this.val = val; }
  ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}




public class MiddleNode {

  /**
   * approach1, using linkedlist
   * time complicity:O(n)
   * space complicity: O(n)
   * @param head ListNode
   * @return true/false
   */
  public ListNode middleNode(ListNode head){
    int middle=size(head)/2;
    while(middle!=0){
      head=head.next;
      middle--;
    }
    return head;
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
