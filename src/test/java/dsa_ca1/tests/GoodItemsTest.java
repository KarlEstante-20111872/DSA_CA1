package dsa_ca1.tests;

import dsa_ca1.models.GoodItems;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GoodItemsTest {

    GoodItems item;

    @BeforeEach
    void setUp() {
        item = new GoodItems(
                "Cornflakes", "Healthy breakfast cereal", "720g", 3.99, 7, "Unrefrigerated", ""
        );
    }

    @Test
    void getDescription() {
        assertEquals("Healthy breakfast cereal", item.getDescription());
    }

    @Test
    void setDescription() {
        item.setDescription("Rice Cereal");
        assertEquals("Rice Cereal", item.getDescription());
    }

    @Test
    void getUnitSize() {
        assertEquals("720g", item.getUnitSize());
    }

    @Test
    void setUnitSize() {
        item.setUnitSize("500g");
        assertEquals("500g", item.getUnitSize());
    }

    @Test
    void getUnitPrice() {
        assertEquals(3.99, item.getUnitPrice());
    }

    @Test
    void setUnitPrice() {
        item.setUnitPrice(4.50);
        assertEquals(4.50, item.getUnitPrice());
    }

    @Test
    void getQuantity() {
        assertEquals(7, item.getQuantity());
    }

    @Test
    void setQuantity() {
        item.setQuantity(10);
        assertEquals(10, item.getQuantity());
    }

    @Test
    void getStorageTemperature() {
        assertEquals("Unrefrigerated", item.getStorageTemperature());
    }

    @Test
    void setStorageTemperature() {
        item.setStorageTemperature("Frozen");
        assertEquals("Frozen", item.getStorageTemperature());
    }

    @Test
    void getPhotoURL() {
        assertEquals("https://example.com/cornflakes.jpg", item.getPhotoURL());
    }

    @Test
    void testToString() {
        String str = item.toString();
        assertTrue(str.contains("Cornflakes"));
        assertTrue(str.contains("Healthy breakfast cereal"));
        assertTrue(str.contains("720g"));
        assertTrue(str.contains("3.99"));
        assertTrue(str.contains("7"));
        assertTrue(str.contains("Unrefrigerated"));
    }
}
