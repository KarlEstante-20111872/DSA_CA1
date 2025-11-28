package dsa_ca1.tests;

import dsa_ca1.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FloorAreaTest {

    FloorArea floor;
    Aisle aisle1, aisle2;

    @BeforeEach
    void setUp() {
        floor = new FloorArea("Dairy", 1);

        LinkedList<Shelf> shelves1 = new LinkedList<>();
        LinkedList<Shelf> shelves2 = new LinkedList<>();
        aisle1 = new Aisle("Cheese", "Refrigerated", 10, 5, shelves1);
        aisle2 = new Aisle("Milk", "Refrigerated", 12, 6, shelves2);
    }

    @Test
    void getName() {
        assertEquals("Dairy", floor.getName());
    }

    @Test
    void setName() {
        floor.setName("Frozen Foods");
        assertEquals("Frozen Foods", floor.getName());
    }

    @Test
    void addAisle() {
        floor.addAisle(aisle1);
        assertTrue(floor.searchAisle(aisle1));
    }

    @Test
    void removeAisle() {
        floor.addAisle(aisle1);
        floor.addAisle(aisle2);
        floor.removeAisle(aisle2); // remove by object
        assertFalse(floor.searchAisle(aisle2));
        assertTrue(floor.searchAisle(aisle1));
    }

    @Test
    void searchAisle() {
        floor.addAisle(aisle1);
        assertTrue(floor.searchAisle(aisle1));
        assertFalse(floor.searchAisle(aisle2));
    }

    @Test
    void printAisles() {
        floor.addAisle(aisle1);
        floor.addAisle(aisle2);
        floor.printAisles(); // visual check
    }

    @Test
    void testToString() {
        String str = floor.toString();
        assertTrue(str.contains("Dairy"));
    }
}
