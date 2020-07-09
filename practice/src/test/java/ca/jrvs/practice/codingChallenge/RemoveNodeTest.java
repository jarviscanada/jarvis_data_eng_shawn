package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import ca.jrvs.practice.codingChallenge.RemoveNode.ListNode;
import org.junit.Test;

public class RemoveNodeTest {

  @Test
  public void removeNthFromEnd() {
    RemoveNode test = new RemoveNode();

    ListNode head = new ListNode(1, null);
    assertEquals(null, test.removeNthFromEnd(head, 1));

    head = new ListNode(1, null);
    ListNode n = head;
    int i = 2;
    n.next = new ListNode(i, null);
    n = n.next;
    assertEquals(new ListNode(1, null).val, test.removeNthFromEnd(head, 1).val);

    head = new ListNode(1, null);
    n = head;
    i = 2;
    while (i <= 5) {
      n.next = new ListNode(i, null);
      n = n.next;
      i++;
    }
    ListNode actual = test.removeNthFromEnd(head, 2);
    n = actual;
    while (n.next != null) {
      n = n.next;
    }
    assertEquals(5, n.val);

  }
}