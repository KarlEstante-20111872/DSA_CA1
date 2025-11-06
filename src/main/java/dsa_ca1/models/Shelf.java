package dsa_ca1.models;

public class Shelf {
    private int shelfNumber;
    private LinkedList<GoodItems> items;

    public Shelf(int shelfNumber, LinkedList<GoodItems> items) {
        this.shelfNumber = shelfNumber;
        this.items = items;
    }

    public int getShelfNumber() {
        return shelfNumber;
    }

    public void setShelfNumber(int shelfNumber) {
        this.shelfNumber = shelfNumber;
    }

    public LinkedList<GoodItems> getItems() {
        return items;
    }

    public void setItems(LinkedList<GoodItems> items) {
        this.items = items;
    }
    // methods
    public void addItem(GoodItems item) {
        items.insertAtPosition(item, 0); // you can choose position logic
    }

    public void removeItem(int position) {
        items.deleteAtPosition(position);
    }

    public boolean searchItem(GoodItems item) {
        return items.searchItem(item); // simply calls your LinkedList search
    }

    public void printItems() {
        items.printList(items.head); // uses your LinkedList print method
    }
}

