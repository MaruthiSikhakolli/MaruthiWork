package linkedList;

import java.util.ArrayList;
import java.util.List;

import linkedList.DoubleLinkedLists.Node;

public class LinkedList {

	Node head;

	static class Node {

		int data;
		Node next;

		Node(int d) {
			data = d;
			next = null;
		}
	}
	
	public void printLinkedList() {

		Node temp = head;
		System.out.println("Print");
		while (temp != null) {
			System.out.print(temp.data+ "  ");
			temp = temp.next;
		}
		System.out.println();

	}
	public void printLinkedListWithNode(Node n){
		Node temp = n;
		while (temp!=null){
			System.out.print(temp.data + "  ");
			temp = temp.next;
		}
		System.out.println();
	}
	public void addingANodeAtFirst(LinkedList l, int newNodeData) {

		Node n3 = new Node(newNodeData);
		n3.next = head;
		head = n3;

	}
	public void addingAtLastOfLinkedList(LinkedList l, int newNodeData) {
		Node n4 = new Node(newNodeData);
		if (l.head == null) {
			l.head = n4;
		}

		Node temp = l.head;
		while (temp.next != null) {
			temp = temp.next;
		}
		temp.next = n4;
	}
	public void appendAtEndOfLinkedList(int new_data)
	{
	    /* 1. Allocate the Node &
	       2. Put in the data
	       3. Set next as null */
	    Node new_node = new Node(new_data);
	 
	    /* 4. If the Linked List is empty, then make the
	           new node as head */
	    if (head == null)
	    {
	        head = new Node(new_data);
	        return;
	    }
	 
	    /* 4. This new node is going to be the last node, so
	         make next of it as null */
	    new_node.next = null;
	 
	    /* 5. Else traverse till the last node */
	    Node last = head; 
	    while (last.next != null)
	        last = last.next;
	 
	    /* 6. Change the next of last node */
	    last.next = new_node;
	    return;
	}	
	public void addingAtTheMiddleOf(LinkedList l, Node prev_node, int newNodeData) {
		if (prev_node == null) {
			System.out.println("The given previous node cannot be null");
			return;
		}
		Node n5 = new Node(newNodeData);
		n5.next = prev_node.next;
		prev_node.next = n5;
	}
	public void deleteFirstNodeOfLinkedList(LinkedList l) {
		Node temp = l.head;
		l.head = temp.next;
		temp.next = null;
	}
	public void deleteLastNodeOfLinkedList(LinkedList l) {
		Node temp1 = l.head;
		while (temp1.next.next != null) {
			temp1 = temp1.next;
		}
		temp1.next = null;
	}
	public void deleteFirstOccuranceOfLinkedList(LinkedList l, int key) {
		Node temp = l.head;
		Node prev = null;
		if (temp != null && temp.data == key) {
			l.head = temp.next;
			return;
		}
		while (temp != null && temp.data != key) {
			prev = temp;
			temp = temp.next;
		}
		if (temp == null) {
			return;
		}
		prev.next = temp.next;
	}
	public void deleteAtGivenPosition(LinkedList l, int position) {
		if (l.head == null) {
			System.out.println("No Node available to delete");
			return;
		}
		Node temp = head;
		if (position == 0) {
			head = temp.next;
			return;
		}
		for (int i = 0; temp != null && i < position - 1; i++) {
			temp = temp.next;
		}
		if (temp == null || temp.next == null) {
			return;
		}
		Node nodeAfterDeletedNode = temp.next.next;
		temp.next = nodeAfterDeletedNode;
	}
	public int lengthOfLinkedList(LinkedList l) {
		int count = 0;
		if (head == null) {
			return count;
		}
		Node temp = head;
		while (temp != null) {
			count++;
			temp = temp.next;
		}
		return count;
	}
	public int lengthOfLinkedListRecursive(LinkedList l, Node node) {
		int count = 0;
		if (node == null)
			return count;
		return 1 + lengthOfLinkedListRecursive(l, node.next);
	}
	public void swapNodesInALinkedListWithoutSwappingData(LinkedList l, int x, int y) {
		 // Nothing to do if x and y are same
        if (x == y) return;
 
        // Search for x (keep track of prevX and CurrX)
        Node prevX = null, currX = head;
        while (currX != null && currX.data != x)
        {
            prevX = currX;
            currX = currX.next;
        }
 
        // Search for y (keep track of prevY and currY)
        Node prevY = null, currY = head;
        while (currY != null && currY.data != y)
        {
            prevY = currY;
            currY = currY.next;
        }
 
        // If either x or y is not present, nothing to do
        if (currX == null || currY == null)
            return;
 
        // If x is not head of linked list
        if (prevX != null)
            prevX.next = currY;
        else //make y the new head
            head = currY;
 
        // If y is not head of linked list
        if (prevY != null)
            prevY.next = currX;
        else // make x the new head
            head = currX;
 
        // Swap next pointers
        Node temp = currX.next;
        currX.next = currY.next;
        currY.next = temp;

	}
	public void reverseALinkedList(LinkedList l){
		if(l.head == null) {
			System.out.println("There is no linked list to reverse");
			return;
		}
		if(l.head.next == null) {
			System.out.println("Since there is only one node in the  linked list, there is no need to reverse");
			return;
		}
		Node prev = null, next = null, current = l.head;
		while(current!=null){
			next = current.next;
			current.next = prev;
			prev = current;
			current = next;
		}
		l.head = prev;
	}
	public Node reverseInGroupsOfGivenNode(Node head, int k){
		Node prev = null, next = null, current = head;
		int count = 1;
		while(current != null && count <= k){
			next = current.next;
			current.next = prev;
			prev = current;
			current = next;
			count++;
		}
		if(next!=null)
		head.next = reverseInGroupsOfGivenNode(current, k);
		return prev;
	}
	public Node mergeTwoSortedLists(LinkedList l1, LinkedList l2){
		Node newHead = new Node(0);
		Node temp = newHead;
		Node l1temp = l1.head;
		Node l2temp = l2.head;
		if(l1temp == null) return l2.head;
		if(l2temp == null) return l1.head;
		if(l1temp!=null && l2temp!=null){
			while(l1temp!=null && l2temp!=null){
				if(l1temp.data < l2temp.data){
					temp.next = l1temp;
					l1temp = l1temp.next;
				}
				else{
					temp.next = l2temp;
					l2temp = l2temp.next;
				}
				temp = temp.next;
			}
			if(l1temp!=null)
				temp.next = l1temp;
			if(l2temp!=null)
				temp.next = l2temp;
		}
		
		return newHead.next;
	}
	public Node mergeTwoSortedListsUsingRecursion(Node l1, Node l2){
		Node result = null;
		if(l1 == null) return l2;
		if(l2 == null) return l1;
		if(l1.data < l2.data){
			result = l1;
			result.next = mergeTwoSortedListsUsingRecursion(l1.next, l2);
		}
		else{
			result = l2;
			result.next = mergeTwoSortedListsUsingRecursion(l1,l2.next);
		}
		return result;
	}
    public Node mergeSort(Node a){
    	
    	if (a==null || a.next==null) return a;
    	Node middle = getMiddleOfLinkedList(a);
    	Node nextOfMiddle = middle.next;
    	middle.next = null;
    	Node left  =mergeSort(a);
    	Node right = mergeSort(nextOfMiddle);
    	Node sortedList = mergeTwoSortedListsUsingRecursion(left, right);
    	return sortedList;
    	
    }
    public Node getMiddleOfLinkedList(Node h){
        //Base case
        if (h == null)
            return h;
        Node fastptr = h.next;
        Node slowptr = h;
         
        // Move fastptr by two and slow ptr by one
        // Finally slowptr will point to middle node
        while (fastptr != null)
        {
            fastptr = fastptr.next;
            if(fastptr!=null)
            {
                slowptr = slowptr.next;
                fastptr=fastptr.next;
            }
        }
        return slowptr;
    }
    public Node detectAndRemoveLoopUsingArrayList(Node head) {
    	Node temp = head;
    	List<Integer> tempList = new ArrayList<Integer>();
    	while(temp!=null){
    		tempList.add(temp.data);
    		if(tempList.contains(temp.next.data)) {
    			temp.next = null;
    			break;
    		}
    		else temp = temp.next;
    	}
    	return head;
    }
    public void detectAndRemoveLoop(Node node){   
    	   // If list is empty or has only one node
        // without loop
        if (node == null || node.next == null)
            return;
 
        Node slow = node, fast = node;
 
        // Move slow and fast 1 and 2 steps
        // ahead respectively.
        slow = slow.next;
        fast = fast.next.next;
 
        // Search for loop using slow and fast pointers
        while (fast != null && fast.next != null) {
            if (slow == fast) 
                break;
             
            slow = slow.next;
            fast = fast.next.next;
        }
 
        /* If loop exists */
        if (slow == fast) {
            slow = node;
            while (slow.next != fast.next) {
                slow = slow.next;
                fast = fast.next;
            }
 
            /* since fast->next is the looping point */
            fast.next = null; /* remove loop */
        }
    }
    public Node addTwoLists(Node temp1, Node temp2){

    	Node a = temp1;
    	Node b = temp2;
    	int actualSum;
    	int carry = 0; 
    	LinkedList result = new LinkedList();
    	result.appendAtEndOfLinkedList(0);
    	if(a==null) return b;
    	if(b==null) return a;
    	
    	while(a!=null || b!=null){
    		actualSum = (a!=null ? a.data : 0) + (b!=null ? b.data : 0) +carry;
    		
    				if(actualSum < 10)
    				{
    					result.appendAtEndOfLinkedList(actualSum);
    					carry = 0;
    				}
    				else{
    					carry = actualSum / 10;
    					result.appendAtEndOfLinkedList(actualSum%10);
    				}
    				
    				if(a!=null) a = a.next;
    				if(b!=null) b = b.next;
    	}
    	if(carry != 0){
    		result.addingAtLastOfLinkedList(result, carry);
    	}
    	return result.head.next;
    }
    public void rotateLinkedList(int k){
    	int count =1;
    	Node temp1=head;
    	while(count < k){
    		temp1 = temp1.next;
    		count++;
    	}
    	Node futureHead = temp1.next;
    	Node temp2 = futureHead;
    	temp1.next = null;
    	while(temp2.next!=null){
    		temp2=temp2.next;
    	}
    	temp2.next = head;
    	head = futureHead;
    }
    public Node partition(Node l, Node h) {
    	int x = h.data;
    	Node i = null;
    	for(Node j = l; j!=h; j=j.next) {
    		if(j.data<x) {
    			i = (i==null)? l : i.next;
    			int temp = i.data;
                i.data = j.data;
                j.data = temp;
    		}
    	}
    	  i = (i==null) ? l : i.next;  // Similar to i++
          int temp = i.data;
          i.data = h.data;
          h.data = temp;
          return i;
    }
    public void quickSort(Node node) {
    	Node head = lastNode(node);
    	_quickSort(node,head);
    }
    public void _quickSort(Node l, Node h) {
    	if(h!=null && l!=h && l.next!=h) {
    		Node temp = partition(l,h);
        	Node temp2 = l;
        	while(temp2.next!=temp) {
        		temp2=temp2.next;
        	}
        	_quickSort(l, temp2);
        	_quickSort(temp.next, h);
    	}
    	
    }
    public Node lastNode(Node head) {
    	while(head.next!=null) {
    		head = head.next;
    	}
    	return head;
    }
    public static void main(String[] args) {

		LinkedList l = new LinkedList();
		l.head = new Node(1);
		Node n1 = new Node(10);
		Node n2 = new Node(30);
		Node n3 = new Node(9);
		Node n4 = new Node(15);
		l.head.next = n1;
		n1.next = n2;
		n2.next = n3;
		n3.next = n4;
		
		LinkedList m = new LinkedList();
		m.head = new Node(4);
		Node m2 = new Node(15);
		Node m3 = new Node(35);
		m.head.next = m2;
		m2.next = m3;
		
		 LinkedList list = new LinkedList();
	        list.head = new Node(50);
	        list.head.next = new Node(20);
	        list.head.next.next = new Node(15);
	        list.head.next.next.next = new Node(4);
	        list.head.next.next.next.next = new Node(10);
	 
	        System.out.println("List 1 Before");
	        l.printLinkedListWithNode(l.head);
	        l.quickSort(l.head);
	        System.out.println("List 1 After");
	        l.printLinkedListWithNode(l.head);
		

	}

}
