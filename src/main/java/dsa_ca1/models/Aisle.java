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
        shelves.add(shelf);
    }

    public void removeShelf(Shelf shelf) {
        shelves.deleteByValue(shelf);
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

    public double getTotalValue() {
        double totaLL = 0;
        for (Node<Shelf> shelf = shelves.head; shelf != null; shelf = shelf.next) {
            totaLL += shelf.data.getTotalValue();
        }
        return totaLL;
    }

    public Shelf getShelfByNumber(int number) {
        for (Node<Shelf> s = shelves.head; s != null; s = s.next) {
            if (s.data.getShelfNumber() == number) return s.data;
        }
        return null;
    }

    public String viewStock() {
        StringBuilder sb = new StringBuilder();
        sb.append("Aisle '").append(aisleName).append("':\n");
        for (Node<Shelf> shelf = shelves.head; shelf != null; shelf = shelf.next) {
            sb.append(shelf.data.viewStock()); // call Shelf's version returning a String
        }
        sb.append("Aisle '").append(aisleName).append(" has a total goods value of: €").append(getTotalValue()).append("\n");
        return sb.toString();
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
