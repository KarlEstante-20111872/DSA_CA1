package dsa_ca1.controllers;

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
                                    "Found '" + ITEM.data.getDescription() + "' on " + "Floor='" + FLOOR.data.getName() + "', " + "Aisle='" + AISLE.data.getAisleName() + "', " + "Shelf=" + SHELF.data.getShelfNumber() + ", " + "Qty=" + ITEM.data.getQuantity()
                            );
                        }
                    }
                }
            }
        }
        if (!found){ System.out.println("Item is not found.");
        }
    }

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
                                                                        // after it essentially cutting it out
                                            }
                                        }
                                        removed = true;
                                        // ^ here is the ACTUAL removal via prev like the linkd list
                                        System.out.println("Removed " + quantity + " of " + itemName + " from Shelf " + shelfNumber);
                                        break; // only exits ITEM loop after removal
                                    }prev = ITEM;}}}}}}}}


    // 7. PLACEHOLDER FOR METHOD
    // 8. PLACEHOLDER FOR METHOD
    // 9. PLACEHOLDER FOR METHOD
}
