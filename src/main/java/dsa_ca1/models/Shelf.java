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
        items.add(item);
    }

    public void removeItem(GoodItems item) {
        items.deleteByValue(item);
    }

    public boolean searchItem(GoodItems item) {
        return items.searchItem(item);
    }

    public void printItems() {
        items.printList(items.head);
    }

    public String viewStock() {
        StringBuilder sb = new StringBuilder();
        sb.append("Shelf ").append(shelfNumber).append(":\n");
        for (Node<GoodItems> ITEM = items.head; ITEM != null; ITEM = ITEM.next) {
            sb.append("Item: ").append(ITEM.data.getItemName())
                    .append(" with a quantity of ").append(ITEM.data.getQuantity())
                    .append(" and the price of: €").append(ITEM.data.getUnitPrice()).append("\n");
        }
        sb.append("Shelf ").append(shelfNumber)
                .append(" has a total goods value of: €").append(getTotalValue()).append("\n");
        return sb.toString();
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

