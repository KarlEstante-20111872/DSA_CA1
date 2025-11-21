package dsa_ca1.tests;

import dsa_ca1.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AisleTest {

    Aisle aisle;
    Shelf shelf1, shelf2;

    @BeforeEach
    void setUp() {
        LinkedList<Shelf> shelvesList = new LinkedList<>();
        aisle = new Aisle("Bread", "Unrefrigerated", 10, 5, shelvesList);

        LinkedList<GoodItems> itemsList1 = new LinkedList<>();
        LinkedList<GoodItems> itemsList2 = new LinkedList<>();

        shelf1 = new Shelf(1, itemsList1);
        shelf2 = new Shelf(2, itemsList2);
    }


    @Test
    void getAisleName() {
        assertEquals("Bread", aisle.getAisleName());
    }

    @Test
    void setAisleName() {
        aisle.setAisleName("Dairy");
        assertEquals("Dairy", aisle.getAisleName());
    }

    @Test
    void getTemperature() {
        assertEquals("Unrefrigerated", aisle.getTemperature());
    }

    @Test
    void setTemperature() {
        aisle.setTemperature("Frozen");
        assertEquals("Frozen", aisle.getTemperature());
    }

    @Test
    void getWidth() {
        assertEquals(5, aisle.getWidth());
    }

    @Test
    void setWidth() {
        aisle.setWidth(8);
        assertEquals(8, aisle.getWidth());
    }

    @Test
    void getLength() {
        assertEquals(10, aisle.getLength());
    }

    @Test
    void setLength() {
        aisle.setLength(12);
        assertEquals(12, aisle.getLength());
    }

    @Test
    void addShelf() {
        aisle.addShelf(shelf1);
        assertTrue(aisle.searchShelf(shelf1));
    }

    @Test
    void removeShelf() {
        aisle.addShelf(shelf1);
        aisle.addShelf(shelf2);
        aisle.removeShelf(shelf2); // remove by object
        assertFalse(aisle.searchShelf(shelf2));
        assertTrue(aisle.searchShelf(shelf1));
    }

    @Test
    void searchShelf() {
        aisle.addShelf(shelf1);
        assertTrue(aisle.searchShelf(shelf1));
        assertFalse(aisle.searchShelf(shelf2));
    }

    @Test
    void printShelves() {
        aisle.addShelf(shelf1);
        aisle.addShelf(shelf2);
        aisle.printShelves(); // just visually check output
    }

    @Test
    void testToString() {
        String str = aisle.toString();
        assertTrue(str.contains("Bread"));
        assertTrue(str.contains("Unrefrigerated"));
        assertTrue(str.contains("10"));
        assertTrue(str.contains("5"));
    }
}
