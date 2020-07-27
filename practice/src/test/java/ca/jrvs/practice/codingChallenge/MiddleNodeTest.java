package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Test;

public class MiddleNodeTest {

  @Test
  public void middleNode() {
    MiddleNode middleOfLinkedList = new MiddleNode();
    ListNode head = new ListNode(1, null);
    assertEquals(1, middleOfLinkedList.middleNode(head).val);
    ListNode n = head;
    n.next = new ListNode(2, null);
    assertEquals(2, middleOfLinkedList.middleNode(head).val);
    n = n.next;
    n.next = new ListNode(3, null);
    assertEquals(2, middleOfLinkedList.middleNode(head).val);
    n = n.next;
    n.next = new ListNode(4, null);
    assertEquals(3, middleOfLinkedList.middleNode(head).val);
    n = n.next;
    n.next = new ListNode(5, null);
    assertEquals(3, middleOfLinkedList.middleNode(head).val);
  }

}