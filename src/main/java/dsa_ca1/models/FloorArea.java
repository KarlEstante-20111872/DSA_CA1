package dsa_ca1.models;

public class FloorArea {
    private String name;
    private LinkedList<Aisle> aisles;

    public FloorArea(String name, LinkedList<Aisle> aisle) {
        this.name = name;
        this.aisles = aisles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addFloor(Aisle aisle) {
        aisles.insertAtPosition(aisle,0);
    }

    public void removeFloor(int position) {
        aisles.deleteAtPosition(position);
    }

    public boolean searchFloor(Aisle aisle) {
        return aisles.searchItem(aisle);
    }

    public void printAisles() {
        aisles.printList(aisles.head);
    }

    @Override
    public String toString() {
        return "FloorArea{" +
                "name='" + name + '\'' +
                '}';
    }
}
