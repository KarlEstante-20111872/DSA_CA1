package dsa_ca1.models;

public class LinkedList<T> {
    public Node<T> head = null;

    //constructor
    public LinkedList() {
        this.head = null;
    }

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
    public boolean deleteByValue(T boop) {
        if (head == null) {
            System.out.println("Empty List");
            return false;
        }
        if (head.data.equals(boop)) {
            head = head.next;
            System.out.println("Removed: " + boop);
            return true;
        }// the actual deleting below
        Node<T> THE_REMOVAL = head;
        while (THE_REMOVAL.next != null) {
            if (THE_REMOVAL.next.data.equals(boop)) {
                THE_REMOVAL.next = THE_REMOVAL.next.next;
                return true;
            }
            THE_REMOVAL = THE_REMOVAL.next;}
        return false;
    }

    public boolean searchItem(T BoopV2) {
        Node<T> poop = head; //starts at the head
        while (poop != null) {
            if (poop.data.equals(BoopV2)) {//compare using .equals
                return true;//if found, returns true
            }
            poop = poop.next;
        }
        return false;
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

