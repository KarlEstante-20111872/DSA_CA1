package dsa_ca1.models;

public class LinkedList<T> {
    public Node<T> head = null;

    //constructor
    public LinkedList() {
        this.head = null;
    }
    //this class will be full of comments to understand linkedlists and its
    //functions within the project for guidance towards the interview and future use.

    public void add(T value) {
        Node<T> newNode = new Node<>(value);

        if (head == null) {
            head = newNode;
            return;
        }
        Node<T> current = head;
        while (current.next != null) {
            current = current.next;
        }

        current.next = newNode;
    }

    //deletion method
    public boolean deleteByValue(T value) {
        if (head == null) {
            System.out.println("List is empty.");
            return false;
        }
        if (head.data.equals(value)) {
            head = head.next;
            System.out.println("Removed: " + value);
            return true;
        }
        Node<T> removal = head;
        while (removal.next != null) {
            if (removal.next.data.equals(value)) {
                removal.next = removal.next.next;
                System.out.println("Removed: " + value);
                return true;
            }
            removal = removal.next;}
        return false;
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

