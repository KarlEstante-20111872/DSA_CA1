package dsa_ca1.tests;

import dsa_ca1.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShelfTest {

    Shelf shelf;
    GoodItems item1, item2;

    @BeforeEach
    void setUp() {
        LinkedList<GoodItems> itemsList = new LinkedList<>();
        shelf = new Shelf(1, itemsList);

        item1 = new GoodItems("Cornflakes", "Breakfast Cereal", "720g", 3.99, 16, "Unrefrigerated", ""
        );

        item2 = new GoodItems("Tea Bags", "Black Tea", "500g", 4.50, 10, "Unrefrigerated", ""
        );
    }

    @Test
    void getShelfNumber() {
        assertEquals(1, shelf.getShelfNumber());
    }

    @Test
    void setShelfNumber() {
        shelf.setShelfNumber(2);
        assertEquals(2, shelf.getShelfNumber());
    }

    @Test
    void addGoodItem() {
        shelf.addItem(item1);
        assertTrue(shelf.searchItem(item1));
    }

    @Test
    void removeGoodItem() {
        shelf.addItem(item1);
        shelf.addItem(item2);
        shelf.removeItem(item1);
        assertFalse(shelf.searchItem(item1));
        assertTrue(shelf.searchItem(item2));
    }

    @Test
    void searchGoodItem() {
        shelf.addItem(item1);
        assertTrue(shelf.searchItem(item1));
        assertFalse(shelf.searchItem(item2));
    }

    @Test
    void printGoodItems() {
        shelf.addItem(item1);
        shelf.addItem(item2);
        shelf.printItems(); // manual visual inspection
    }

    @Test
    void testToString() {
        String str = shelf.toString();
        assertTrue(str.contains("1"));
    }
}