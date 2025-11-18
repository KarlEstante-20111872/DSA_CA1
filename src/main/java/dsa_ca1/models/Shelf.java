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
    // methods used from linkedlist
    public void addItem(GoodItems item) {
        items.insertAtPosition(item, 0);
    }

    public void removeItem(int position) {
        items.deleteAtPosition(position);
    }

    public boolean searchItem(GoodItems item) {
        return items.searchItem(item);
    }

    public void printItems() {
        items.printList(items.head);
    }

    public void viewStock() {
        System.out.println("  Shelf " + shelfNumber + ":");

        for (Node<GoodItems> item = items.head; item != null; item = item.next) {
            System.out.print("    ");
            item.data.viewStock();
        }

        System.out.println("  Shelf " + shelfNumber + " total value: €" + getTotalValue());
        System.out.println();
    }

    public double getTotalValue() {
        double ToTaL = 0;
        for (Node<GoodItems> bad = items.head; bad != null; bad = bad.next) {
            ToTaL += bad.data.getTotalValue();
        }
        return ToTaL;
    }
    @Override
    public String toString() {
        return "Shelf{" +
                "shelfNumber=" + shelfNumber +
                ", items=" + items +
                '}';
    }
}

