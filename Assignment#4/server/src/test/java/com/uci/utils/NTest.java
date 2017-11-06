package com.uci.utils;

import java.util.ArrayList;
import java.util.List;


public class NTest {

	public static class ListNode {
		int val;
		ListNode next, down;
		public ListNode(int val) {
			this.val = val;
		}

		@Override
		public String toString() {
			return "ListNode{" +
					"val=" + val +
					", next=" + next +
					", down=" + down +
					'}';
		}
	}

	//
    private ListNode tail;

    public ListNode flatten(ListNode head) {
        if (head == null) {
            return null;
        }
		tail = head;//keep track of the tail of list
        ListNode next = head.next;//store the next
        if (head.down != null) {
            head.next = flatten(head.down);//connect all nodes in curr head's down part
        }
        if (next != null) {
        	ListNode cur = tail;
        	//ListNode c = flatten(next);
            //cur.next = flatten(next);//connect the tail to the next part   
            //cur.next = c; ///1, 2 , 7, 3, 4
			tail.next  =  flatten(next);// if the list is 1 - 2 - 3 - 4, we don't need this

			//tail.next = c;                                    // the aim of assigning flatten(next) to tail.next is that
        }
        return head;
    }

    private List<Integer> list = new ArrayList<>();

     public void test(int i){
		 list.add(i);
		 if(i > 0){
			 test(i - 1);
		 }
		 System.out.println(list.size());
	 }

	
	public static void main(String[] args) {
		NTest a = new NTest();

		a.test(4);
		ListNode a1 = new ListNode(1);
		ListNode a2 = new ListNode(2);
		ListNode a3 = new ListNode(3);
		ListNode a4 = new ListNode(4);
		ListNode a5 = new ListNode(5);
		ListNode a6 = new ListNode(6);
		ListNode a7 = new ListNode(7);
		ListNode a8 = new ListNode(8);
		ListNode a9 = new ListNode(9);
		ListNode a10 = new ListNode(10);
		ListNode a11 = new ListNode(11);
		ListNode a12 = new ListNode(12);
		ListNode a13 = new ListNode(13);
		ListNode a14 = new ListNode(14);
		ListNode a15 = new ListNode(15);
		ListNode a16 = new ListNode(16);
		ListNode a17 = new ListNode(17);
		ListNode a18 = new ListNode(18);
		ListNode a19 = new ListNode(19);
		ListNode a20 = new ListNode(20);
		ListNode a21 = new ListNode(21);
		ListNode a22 = new ListNode(22);
		ListNode a23 = new ListNode(23);
		ListNode a24 = new ListNode(24);
		a1.next = a2;
		a2.next = a3;
		a3.next = a4;
		a2.down = a7;
//		a7.next = a8;
//		a8.next = a10;
//		a10.next = a12;
//		a7.down = a9;
//		a8.down = a16;
//		a10.down = a11;
//		a9.down = a14;
//		a16.down = a17;
//		a17.next = a18;
//		a18.next = a19;
//		a19.next = a20;
//		a14.down = a15;
//		a20.down = a21;
//		a15.next = a23;
//		a23.down = a24;
		
		
		
		ListNode r = a.flatten(a1);
		while (r != null) {
			System.out.print(r.val + "->");
			r = r.next;
		}
		
		
		
		
	
	}

}
