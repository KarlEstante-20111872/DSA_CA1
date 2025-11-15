package dsa_ca1.models;

public class FloorArea {
    private String name;
    private int floor;
    private LinkedList<Aisle> aisles;

    public FloorArea(String name, int floor) {
        this.name = name;
        this.floor = floor;
        this.aisles = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public void addAisle(Aisle aisle) {
        aisles.insertAtPosition(aisle, 0 );
    }

    public void removeAisle(int position) {
        aisles.deleteAtPosition(position);
    }

    public boolean searchAisle(Aisle aisle) {
        return aisles.searchItem(aisle);
    }

    public void printAisles() {
        aisles.printList(aisles.head);
    }

    public LinkedList<Aisle> getAisles() {
        return aisles;
    }

    @Override
    public String toString() {
        return "FloorArea{" +
                "name='" + name + '\'' +
                ", floor=" + floor +
                ", aisles=" + aisles +
                '}';
    }
}