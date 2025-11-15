package dsa_ca1.tests;

import dsa_ca1.models.Node;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void testNodeCreation() {
        Node<String> node = new Node<>("Hello");
        assertEquals("Hello", node.data);
        assertNull(node.next);
    }


}