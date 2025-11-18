package dsa_ca1.controllers;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import dsa_ca1.models.*;

public class SupermarketAPI {

    private LinkedList<FloorArea> floorAreas;

    public SupermarketAPI() {
        floorAreas = new LinkedList<>();
    }

    public void addFloorArea(FloorArea FA) {
        floorAreas.insertAtPosition(FA, 0);
    }

    public void removeFloorArea(int position) {
        floorAreas.deleteAtPosition(position);
    }

    public boolean searchFloorArea(FloorArea fa) {
        return floorAreas.searchItem(fa);
    }

    public void printFloorAreas() {
        floorAreas.printList(floorAreas.head);
    }

    // search good items (12 hours to figure out and the rest took considerably less time)
    public void searchGoodItem(String name) {
        boolean found = false;

        for (Node<FloorArea> FLOOR = floorAreas.head; FLOOR != null; FLOOR = FLOOR.next) { //checks the linkedlists of each class
            for (Node<Aisle> AISLE = FLOOR.data.getAisles().head; AISLE != null; AISLE = AISLE.next) {//and it also documents it
                for (Node<Shelf> SHELF = AISLE.data.getShelves().head; SHELF != null; SHELF = SHELF.next) {//so we keep going down until
                    for (Node<GoodItems> ITEM = SHELF.data.getItems().head; ITEM != null; ITEM = ITEM.next) {//we reach the item itself
                        if (ITEM.data.getDescription().equals(name)) {//then we ask if it has the same value(name)
                            found = true;//if true, it prints out where it all is from each class with its values needed
                            System.out.println(
                                    "Found '" + ITEM.data.getDescription() + "' on " + "Floor='" + FLOOR.data.getName() + "', " + "Aisle='" + AISLE.data.getAisleName() + "', " + "Shelf=" + SHELF.data.getShelfNumber() + ", " + "Qty=" + ITEM.data.getQuantity());
                        }}}}}if (!found){ System.out.println("Item is not found.");}}

    // removegood item (5 ours 43 mins) search helped alot and used a bit of remove node linkedlists from linked list class for this
    public void removeGoodItem(String floorName, String aisleName, int shelfNumber, String itemName, int quantity) {
        boolean removed = false;

        for (Node<FloorArea> FLOOR = floorAreas.head; FLOOR != null; FLOOR = FLOOR.next) {
            if (FLOOR.data.getName().equals(floorName)) {
                // as each floor is traversed it checks the name, if it doesnt match, it keeps going until
                //we find the floor used for the good item (same as we go down)
                for (Node<Aisle> AISLE = FLOOR.data.getAisles().head; AISLE != null; AISLE = AISLE.next) {
                    if (AISLE.data.getAisleName().equals(aisleName)) {
                        for (Node<Shelf> SHELF = AISLE.data.getShelves().head; SHELF != null; SHELF = SHELF.next) {
                            if (SHELF.data.getShelfNumber() == shelfNumber) {
                                //^ same thing as before, traverse through the classes
                                Node<GoodItems> prev = null; // < used ffor removal of item
                                for (Node<GoodItems> ITEM = SHELF.data.getItems().head; ITEM != null; ITEM = ITEM.next) {
                                    if (ITEM.data.getDescription().equals(itemName)) {
                                        if (ITEM.data.getQuantity() > quantity) {
                                            ITEM.data.setQuantity(ITEM.data.getQuantity() - quantity);
                                            // here is for reducing the quantity of the item ^
                                        } else {
                                            if (prev == null) {
                                                SHELF.data.getItems().head = ITEM.next; // removing the head
                                            } else {
                                                prev.next = ITEM.next; // we link the node before the removed one to the one
                                            }}                        // after it essentially cutting it out
                                        removed = true;
                                        // ^ here is the ACTUAL removal via prev like the linkd list
                                        System.out.println("Removed " + quantity + " of " + itemName + " from Shelf " + shelfNumber);
                                        break; // only exits ITEM loop after removal
                                    }prev = ITEM;}}}}}}}}

    public int countFloorAreas() {
        int count = 0;
        for (Node<FloorArea> f = floorAreas.head; f != null; f = f.next) {
            count++;
        }
        return count;
    }
    // also viewAll
    public double getTotalValue() {
        double sum = 0;
        for (Node<FloorArea> f = floorAreas.head; f != null; f = f.next) {
            sum += f.data.getTotalValue();
        }
        return sum;
    }

    //viewStock in every other class
    public void viewAllStock() {
        System.out.println("=== FULL SUPERMARKET STOCK REPORT ===\n");

        for (Node<FloorArea> floor = floorAreas.head; floor != null; floor = floor.next) {
            floor.data.viewStock();
        }

        System.out.println("Supermarket: " + countFloorAreas() +
                " Floor Areas, total goods value: €" + getTotalValue());
    }

    public void smartAdd(GoodItems randomitem) {
        // identical items to put with
        for (Node<FloorArea> F = floorAreas.head; F != null; F = F.next) {
            for (Node<Aisle> A = F.data.getAisles().head; A != null; A = A.next) {
                for (Node<Shelf> S = A.data.getShelves().head; S != null; S = S.next) {
                    for (Node<GoodItems> I = S.data.getItems().head; I != null; I = I.next) {
                        if (I.data.getDescription().equals(randomitem.getDescription())) {
                            I.data.setQuantity(I.data.getQuantity() + randomitem.getQuantity());
                            return;}}}}}
        // identical temperature to put with
        for (Node<FloorArea> F = floorAreas.head; F != null; F = F.next) {
            for (Node<Aisle> A = F.data.getAisles().head; A != null; A = A.next) {
                if (A.data.getTemperature().equals(randomitem.getStorageTemperature()) && A.data.getShelves().head != null) {
                    A.data.getShelves().head.data.addItem(randomitem);
                    return;}}}
        // any place it belongs
        if (floorAreas.head != null && floorAreas.head.data.getAisles().head != null && floorAreas.head.data.getAisles().head.data.getShelves().head != null) {
            floorAreas.head.data.getAisles().head.data.getShelves().head.data.addItem(randomitem);
            return;}
        System.out.println("tossed into available shelf!");
    }
    // from last year programming XStream library
    public void load() throws Exception {
        //list of classes for XML deserialization
        Class<?>[] classes = new Class[] {
                SupermarketAPI.class,
                FloorArea.class,
                Aisle.class,
                Shelf.class,
                GoodItems.class,
                LinkedList.class,
                Node.class
        };
        XStream xstream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);
        ObjectInputStream is = xstream.createObjectInputStream(new FileReader("supermarket.xml"));
        floorAreas = (LinkedList<FloorArea>) is.readObject();
        is.close();
    }
    public void save() throws Exception {
        XStream xstream = new XStream(new DomDriver());
        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter("supermarket.xml"));
        out.writeObject(floorAreas);
        out.close();
    }
}
