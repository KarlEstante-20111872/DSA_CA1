package dsa_ca1.models;

public class Aisle {
    private String aisleName;       // e.g., "Cheese", "Bread"
    private int length;             // dimensions in meters or cm
    private int width;
    private String temperature;     // "Unrefrigerated", "Refrigerated", "Frozen"
    private LinkedList<Shelf> shelves;

    public Aisle(String aisleName, String temperature, int length, int width, LinkedList<Shelf> shelves) {
        this.aisleName = aisleName;
        this.temperature = temperature;
        this.length = length;
        this.width = width;
        this.shelves = shelves;
    }

    public String getAisleName() {
        return aisleName;
    }

    public void setAisleName(String aisleName) {
        this.aisleName = aisleName;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
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

    public LinkedList<Shelf> getShelves() {
        return shelves;
    }

    public void setShelves(LinkedList<Shelf> shelves) {
        this.shelves = shelves;
    }

    @Override
    public String toString() {
        return "Aisle{" +
                "aisleName='" + aisleName + '\'' +
                ", length=" + length +
                ", width=" + width +
                ", temperature='" + temperature + '\'' +
                ", shelves=" + shelves +
                '}';
    }
}
