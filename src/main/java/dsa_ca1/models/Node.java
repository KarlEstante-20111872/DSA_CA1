package dsa_ca1.models;

public class Node<T> {
    public T data;
    public Node<T> next = null;

    public Node(T data) {
        this.data = data;
    }
}
