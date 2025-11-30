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
        floorAreas.add(FA);
    }

    public void removeFloorArea(FloorArea FA) {
        floorAreas.deleteByValue(FA);
    }

    public boolean searchFloorArea(FloorArea fa) {
        return floorAreas.searchItem(fa);
    }

    public void printFloorAreas() {
        floorAreas.printList(floorAreas.head);
    }

    public LinkedList<FloorArea> getFloorAreas() {
        return floorAreas;
    }

    public void setFloorAreas(LinkedList<FloorArea> floorAreas) {
        this.floorAreas = floorAreas;
    }

    public FloorArea getFloorAreaByName(String name) {
        for (Node<FloorArea> f = floorAreas.head; f != null; f = f.next) {
            if (f.data.getName().equals(name)) return f.data;
        }
        return null;
    }

    // search good items (12 hours to figure out and the rest took considerably less time)
    public String searchGoodItem(String name) {
        boolean found = false;
        StringBuilder sb = new StringBuilder();

        for (Node<FloorArea> FLOOR = floorAreas.head; FLOOR != null; FLOOR = FLOOR.next) {
            for (Node<Aisle> AISLE = FLOOR.data.getAisles().head; AISLE != null; AISLE = AISLE.next) {
                for (Node<Shelf> SHELF = AISLE.data.getShelves().head; SHELF != null; SHELF = SHELF.next) {
                    for (Node<GoodItems> ITEM = SHELF.data.getItems().head; ITEM != null; ITEM = ITEM.next) {
                        if (ITEM.data.getItemName().toLowerCase().contains(name.toLowerCase()))
                        {
                            found = true;
                            sb.append("---- Searching Results ----\n")
                                    .append("Item: ").append(ITEM.data.getItemName()).append("\n")
                                    .append("Description: ").append(ITEM.data.getDescription()).append("on").append("\n")
                                    .append(FLOOR.data.getName()).append("\n")
                                    .append("Aisle: ")
                                    .append(AISLE.data.getAisleName()).append("\n")
                                    .append("Shelf: ")
                                    .append(SHELF.data.getShelfNumber()).append("\n")
                                    .append("Qty: ")
                                    .append(ITEM.data.getQuantity()).append("\n").append("\n");
                        }}}}}
        if (!found) {
            sb.append("Item not found.\n");
        }
        return sb.toString();
    }

    // removegood item (5 ours 43 mins) search helped alot and used a bit of remove node linkedlists from linked list class for this
    public String removeGoodItem(String floorName, String aisleName, int shelfNumber, String itemName, int quantity) {
        StringBuilder sb = new StringBuilder();
        boolean removed = false;

        outerLoop: // label for breaking out of all loops once removal is done
        for (Node<FloorArea> FLOOR = floorAreas.head; FLOOR != null; FLOOR = FLOOR.next) {
            if (FLOOR.data.getName().equals(floorName)) {
                // as each floor is traversed it checks the name, if it doesnt match, it keeps going until
                // we find the floor used for the good item (same as we go down)
                for (Node<Aisle> AISLE = FLOOR.data.getAisles().head; AISLE != null; AISLE = AISLE.next) {
                    if (AISLE.data.getAisleName().equals(aisleName)) {
                        for (Node<Shelf> SHELF = AISLE.data.getShelves().head; SHELF != null; SHELF = SHELF.next) {
                            if (SHELF.data.getShelfNumber() == shelfNumber) {
                                // ^ same thing as before, traverse through the classes
                                Node<GoodItems> prev = null; // < used for removal of item
                                for (Node<GoodItems> ITEM = SHELF.data.getItems().head; ITEM != null; ITEM = ITEM.next) {
                                    if (ITEM.data.getItemName().equals(itemName)) {
                                        if (ITEM.data.getQuantity() > quantity) {
                                            ITEM.data.setQuantity(ITEM.data.getQuantity() - quantity);
                                            // here is for reducing the quantity of the item ^
                                        } else {
                                            if (prev == null) {
                                                SHELF.data.getItems().head = ITEM.next; // removing the head
                                            } else {
                                                prev.next = ITEM.next; // we link the node before the removed one to the one
                                            }
                                            // after it essentially cutting it out
                                        }
                                        removed = true;
                                        // ^ here is the ACTUAL removal via prev like the linked list
                                        sb.append("---- Removing ----\n");
                                        sb.append("Removed ").append(quantity).append(" of ").append(itemName).append("\n");
                                        sb.append("Location:\n");
                                        sb.append("  Floor: ").append(floorName).append("\n");
                                        sb.append("  Aisle: ").append(aisleName).append("\n");
                                        sb.append("  Shelf: ").append(shelfNumber).append("\n\n");
                                        break outerLoop; // break out of all loops since item is removed
                                    }
                                    prev = ITEM;}}}}}}}
        if (!removed) {
            sb.append("Item '").append(itemName).append("' not found for removal.\n");
        }
        return sb.toString();
    }
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
    public String viewAllStock() {
        StringBuilder sb = new StringBuilder();
        sb.append("---- Stock of Supermarket ----\n\n");
        for (Node<FloorArea> floor = floorAreas.head; floor != null; floor = floor.next) {
            sb.append(floor.data.viewStock());
        }
        sb.append("Supermarket: ").append(countFloorAreas())
                .append(" Floor Areas, total goods value: €").append(getTotalValue()).append("\n");
        return sb.toString(); //
    }

    //by far the hardest thing here, had to learn tokenization
    public String smartAdd(GoodItems smartItem) {

        String COMBINEDTEXT = (smartItem.getItemName() + " " + smartItem.getDescription()).toLowerCase();
        LinkedList<String> TOKENS = new LinkedList<>();
        for (String word : COMBINEDTEXT.split("\\s+")) {
            TOKENS.add(word);
        }

        StringBuilder sb = new StringBuilder();
        boolean NAME_DESCRIPTION = false;
        boolean TEMPERATURE = false;
        boolean addedByFallback = false;

        // identical items to put with
        outerLoop: // label to exit all loops once item is added
        for (Node<FloorArea> F = floorAreas.head; F != null; F = F.next) {
            for (Node<Aisle> A = F.data.getAisles().head; A != null; A = A.next) {
                for (Node<Shelf> S = A.data.getShelves().head; S != null; S = S.next) {
                    for (Node<GoodItems> I = S.data.getItems().head; I != null; I = I.next) {
                        // turn existing name + description lowercase (validation)
                        String existingCombined = (I.data.getItemName() + " " + I.data.getDescription()).toLowerCase();
                        // tokenises the words
                        LinkedList<String> TOKENSEXISTING = new LinkedList<>();
                        for (String word : existingCombined.split("\\s+")) {
                            TOKENSEXISTING.add(word);
                        }
                        // compares the newword to the existing words
                        boolean matching = false;
                        for (Node<String> NEW = TOKENS.head; NEW != null; NEW = NEW.next) {
                            for (Node<String> EXISTING = TOKENSEXISTING.head; EXISTING != null; EXISTING = EXISTING.next) {
                                if (NEW.data.equals(EXISTING.data)) {
                                    matching = true;
                                    break;
                                }
                            }
                            if (matching) break;
                        }


                        //ADDITION/MERGED USING NAME
                        // add new item to the same shelf if matching
                        if (matching) {
                            //merge quantity only if the exact name matches
                            if (I.data.getItemName().equals(smartItem.getItemName())) {
                                I.data.setQuantity(I.data.getQuantity() + smartItem.getQuantity());
                                sb.append("This item '").append(I.data.getItemName())
                                        .append("' has their quantity merged").append("\n");
                            } else {
                                //add as a NEW SEPARATE ITEM on the same shelf
                                S.data.addItem(smartItem);
                                sb.append("Item smartly added to Shelf ").append(S.data.getShelfNumber());
                            }
                            NAME_DESCRIPTION = true;
                            break outerLoop; //exit all loops after adding
                        }
                    }
                }
            }
        }
        //identical temperature to put with
        if (!NAME_DESCRIPTION) {
            outerTemp:
            for (Node<FloorArea> F = floorAreas.head; F != null; F = F.next) {
                for (Node<Aisle> A = F.data.getAisles().head; A != null; A = A.next) {
                    if (A.data.getTemperature().equals(smartItem.getStorageTemperature())
                            && A.data.getShelves().head != null) {
                        A.data.getShelves().head.data.addItem(smartItem);
                        sb.append("Item smartly added based on temperature to Shelf ").append(A.data.getShelves().head.data.getShelfNumber()).append("\n");
                        TEMPERATURE = true;
                        break outerTemp;
                    }
                }
            }
        }
        //any place it belongs
        if (!NAME_DESCRIPTION && !TEMPERATURE) {
            if (floorAreas.head != null &&
                    floorAreas.head.data.getAisles().head != null &&
                    floorAreas.head.data.getAisles().head.data.getShelves().head != null) {
                floorAreas.head.data.getAisles().head.data.getShelves().head.data.addItem(smartItem);
                sb.append("Item smartly added to first available shelf\n");
                addedByFallback = true;
            }
        }
        return sb.toString();
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
