package dsa_ca1.controllers;

import dsa_ca1.models.*;

public class SupermarketAPI {

    private LinkedList<FloorArea> floorAreas;

    public SupermarketAPI() {
        floorAreas = new LinkedList<>();
    }

    public void addFloorArea(FloorArea fa) {
        floorAreas.insertAtPosition(fa, 0 );
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


    // 7. PLACEHOLDER FOR METHOD
    // 8. PLACEHOLDER FOR METHOD
    // 9. PLACEHOLDER FOR METHOD
}
