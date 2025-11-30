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

    public void addAisle(Aisle aisle) {
        aisles.add(aisle);
    }

    public void removeAisle(Aisle aisle) {
        aisles.deleteByValue(aisle);
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

    public Aisle getAisleByName(String NAME) {
        for (Node<Aisle> a = aisles.head; a != null; a = a.next) {
            if (a.data.getAisleName().equals(NAME)) return a.data;
        }
        return null;
    }


    public String viewStock() {
        StringBuilder sb = new StringBuilder();
        sb.append("Floor Area '").append(name).append("':\n");
        for (Node<Aisle> aisle = aisles.head; aisle != null; aisle = aisle.next) {
            sb.append(aisle.data.viewStock()); // call Aisle's version returning a String
        }
        sb.append("Floor Area ").append(name).append(" has a total goods value: €").append(getTotalValue()).append("\n");

        return sb.toString();
    }

    public double getTotalValue() {
        double totally = 0;
        for (Node<Aisle> a = aisles.head; a != null; a = a.next) {
            totally += a.data.getTotalValue();
        }
        return totally;
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