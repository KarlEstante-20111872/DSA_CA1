package dsa_ca1.models;

public class LinkedList<T> {
    public Node<T> head = null;

    //constructor
    public LinkedList() {
        this.head = null;
    }
    //this class will be full of comments to understand linkedlists and its
    //functions within the project for guidance towards the interview and future use.

    public void insertAtPosition(T data, int position) {
        Node<T> newNode = new Node<>(data);//creation
        //insertion at head
        if (position == 0) {
            newNode.next = head;
            head = newNode;
            System.out.println("item has been added to the list");
            return;
        }
        //traversal process
        Node<T> prev = head;
        for (int i = 0; i < position - 1; i++) {
            prev = prev.next;
        }
        //insertion process
        newNode.next = prev.next;
        prev.next = newNode;        //newNode.next points to the same value as prev.next which is after the new node
        //then you replace prev.next with newNode value
        System.out.println("item has been added");
    }

    public void deleteAtPosition(int position) {
        //deleting the head of the list
        if (position == 0) {
            Node temp = head;  //stores the head in a temporary variable
            head = head.next;  //this moves the head to the next node
            temp.next = null;  //this gets rid of the values after the deleted value in the temporary variable
            System.out.println("You removed: " + temp.data + " from the front of the list!");
            return;
        }
        //this uses a for loop to traversal to the node before the node needed for deletion
        Node prev = head;
        for (int i = 0; i < position - 1; i++) { //the position before the item being removed is shown here
            prev = prev.next;
        }
        //removal process
        Node removedNode = prev.next;
        prev.next = removedNode.next;   //this makes the previous node point to the .next node so you skip the node removed
        removedNode.next = null;        //could have done head.next=head.next.next but id like to print the value for clarity
        System.out.println("You removed: " + removedNode.data);
    }
    public boolean searchItem(T data) {
        Node<T> poop = head; //starts at the head
        while (poop != null) {
            if (poop.data.equals(data)) {//compare using .equals
                System.out.println("Item found");
                return true;//if found, returns true
            }
            poop = poop.next;
        }

        System.out.println("Item not found in the list.");
        return false; // reached end without finding
    }

    //prints the whole linkedlist
    public void printList(Node current) {
        while (current != null) {
            System.out.print(current.data);
            if (current.next != null) {// all this prints it out until it hits null/last one
                System.out.print("->");
            }
            current = current.next;
        }
    }
}

