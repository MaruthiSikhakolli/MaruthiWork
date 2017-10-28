package linkedList;

import linkedList.LinkedList.Node;

public class LinkedListMethods {
	public Node mergeTwoSortedLists(Node l1, Node l2){
		Node newHead = new Node(0);
		Node temp = newHead;
		if(l1 == null)  return l2;
		if(l2 == null)  return l1;
		if(l1!=null && l2!=null){
			while(l1!=null && l2!=null){
				if(l1.data < l2.data){
					temp.next = l1;
					l1 = l1.next;
				}
				else{
					temp.next = l2;
					l2 = l2.next;
				}
				temp=temp.next;
			}
		}
		if(l1!=null)
			temp.next = l1;
		if(l2!=null)
			temp.next = l2;
		
		return newHead.next;
	}
	public Node mergeTwoLists(Node l1, Node l2) {
	    Node head = new Node(0);
	    Node p=head;
	 
	    Node p1=l1;
	    Node p2=l2;
	    while(p1!=null && p2!=null){
	        if(p1.data < p2.data){
	            p.next = p1;
	            p1 = p1.next;
	        }else{
	            p.next = p2;
	            p2 = p2.next;
	        }
	        p=p.next;
	    }
	 
	    if(p1!=null){
	        p.next = p1;
	    }
	 
	    if(p2!=null){
	        p.next = p2;
	    }
	 
	    return head.next;
	}
}


