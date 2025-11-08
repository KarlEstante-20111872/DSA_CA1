package dsa_ca1.models;

public class Aisle {
    private String aisleName;
    private String aisleNumber;
    private LinkedList<Shelf> shelves;

    public Aisle(String aisleName, String aisleNumber, LinkedList<Shelf> shelves) {
        this.aisleName = aisleName;
        this.aisleNumber = aisleNumber;
        this.shelves = shelves;
    }

    public String getAisleName() {
        return aisleName;
    }

    public void setAisleName(String aisleName) {
        this.aisleName = aisleName;
    }

    public String getAisleNumber() {
        return aisleNumber;
    }

    public void setAisleNumber(String aisleNumber) {
        this.aisleNumber = aisleNumber;
    }

    public void addShelf(Shelf shelf) {
        shelves.insertAtPosition(shelf, 0);
    }

    public void removeShelf(int position) {
        shelves.deleteAtPosition(position);
    }

    public boolean searchShelf(Shelf shelf) {
        return shelves.searchItem(shelf);
    }

    public void printShelves() {
        shelves.printList(shelves.head);
    }

    @Override
    public String toString() {
        return "Aisle{" +
                "aisleNumber='" + aisleNumber + '\'' +
                ", aisleName='" + aisleName + '\'' +
                '}';
    }
}
