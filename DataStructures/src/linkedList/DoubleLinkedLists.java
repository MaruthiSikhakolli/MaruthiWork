package linkedList;

public class DoubleLinkedLists {
	Node head; // head of list
    
    /* Doubly Linked list Node*/
    class Node
    {
        int data;
        Node prev;
        Node next;
         
         
        // Constructor to create a new node
        // next and prev is by default initialized as null
        Node(int d){data=d;} 
    }
    public void printlist(Node n){
    	if(n == null){
    		System.out.println("Theres nothing to print");
    		return;
    	}
    	Node temp = n;
    	while(temp!=null){
    		System.out.println(temp.data);
    		temp = temp.next;
    	}
    }
    public void insertionAtFront(int newData){
    	Node n = new Node(newData);
    	n.next = head;
    	n.prev = null;
    	if(head!=null)
    	head.prev = n;
    	
    	head = n;
    }
    public void insertAfterGivenNode(Node prevNode, int data){
    	Node n = new Node(data);
    	if(prevNode == null) {
    		System.out.println("Prev Node cannot be null");
        	return;
    	}
    		
    	if(prevNode.next == null){
    		n.next = null;
    		n.prev = prevNode;
    		prevNode.next = n;
    	}
    	else{
    		n.next = prevNode.next;
    		n.prev = prevNode;
    		prevNode.next = n;
    		n.next.prev = n;
    	}
    }
    public void insertAtEnd(int newData){
    	Node n =new Node(newData);
    	if(head == null){
    		n.next = null;
    		n.prev = null;
    		head = n;
    		return;
    	}
    	Node temp = head;
    	while(temp.next!=null){
    		temp = temp.next;
    	}
    	n.next = null;
    	n.prev = temp;
    	temp.next = n;
    }
    public void insertBeforeANode(Node nextNode, int newData){
    	Node n = new Node(newData);
    	if(nextNode == null){
    		System.out.println("Next Node cannot be null");
    		return;
    	}
    	if(nextNode == head){
    		n.prev = null;
    		n.next = head;
    		head = n;
    	}
    	else{
    		n.next = nextNode;
    		n.prev = nextNode.prev;
    		n.prev.next = n;
    		nextNode.prev = n;
    	}
    }
    public void deleteAGivenNode(Node deleteNode){
    	if(deleteNode == null){
    		System.out.println("Node to be deleted should not be null");
    	}
    	else if(deleteNode == head){
    		head = head.next;
    		head.prev = null;
    	}
    	else if(deleteNode.next == null){
    		deleteNode.prev = null;
    	}
    	else{
    		deleteNode.next.prev = deleteNode.prev;
    		deleteNode.prev.next = deleteNode.next;
    		deleteNode.next = deleteNode.prev = null;
    	}
    	return;
    }
	public Node reverseADoubleLinkedList(Node head){
		if(head == null){
			System.out.println("No need to reverse");
			return head;
		}
		if(head.next == null){
			return head;
		}
		Node current = head;
		Node temp = null;
		while(current!=null){
			temp = current.prev;
			current.prev = current.next;
			current.next = temp;
			current = current.prev;
		}
		return temp.prev;
	}
    public Node lastNode(Node head) {
    	while(head.next!=null) {
    		head = head.next;
    	}
    	return head;
    }
    public void quickSort(Node node) {
    	Node head = lastNode(node);
    	_quickSort(node,head);
    }
    public void _quickSort(Node l, Node h) {
    	if(h!=null && l!=h && l.next!=h) {
    		Node temp = partition(l,h);
        	_quickSort(l, temp.prev);
        	_quickSort(temp.next, h);
    	}
    
    }
    public Node partition(Node l, Node h) {
    	int x = h.data;
    	Node i = l.prev;
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
	
    public static void main(String[] args) {

		// TODO Auto-generated method stub
		 /* Start with the empty list */
        DoubleLinkedLists dll = new DoubleLinkedLists();
          
        // Insert 6. So linked list becomes 6->NULL
        dll.insertAtEnd(6);
         
     // Insert 7 at the beginning. So linked list becomes 7->6->NULL
        dll.insertionAtFront(7);
         
        // Insert 1 at the beginning. So linked list becomes 1->7->6->NULL
        dll.insertionAtFront(1);
         
        // Insert 4 at the end. So linked list becomes 1->7->6->4->NULL
        dll.insertAtEnd(4);
         
        // Insert 8, after 7. So linked list becomes 1->7->8->6->4->NULL
        dll.insertAfterGivenNode(dll.head.next, 8);
   
        System.out.println("List before reverse is: ");
        dll.printlist(dll.head);
        Node k = dll.reverseADoubleLinkedList(dll.head);
        System.out.println("List after reverse is  ");
        dll.printlist(k);
	}

}
