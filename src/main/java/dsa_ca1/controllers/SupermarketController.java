package dsa_ca1.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import dsa_ca1.models.*;

public class SupermarketController {

    private SupermarketAPI api = new SupermarketAPI();

    @FXML private TextField floorNumberField;
    @FXML private Button createFloorButton;
    @FXML private ComboBox<String> floorComboBox;

    @FXML private TextField aisleNameField;
    @FXML private TextField aisleLengthField;
    @FXML private TextField aisleWidthField;
    @FXML private TextField aisleTemperatureField;
    @FXML private Button createAisleButton;
    @FXML private ComboBox<String> aisleComboBoxShelf;

    @FXML private TextField shelfNumberField;
    @FXML private Button createShelfButton;

    @FXML private TextField smartDescriptionField;
    @FXML private TextField smartTemperatureField;
    @FXML private TextField smartQuantityField;
    @FXML private Button smartAddButton;

    @FXML private TextField searchItemNameField;
    @FXML private Button searchItemButton;

    @FXML private TextField removeFloorField;
    @FXML private TextField removeAisleField;
    @FXML private TextField removeShelfField;
    @FXML private TextField removeItemNameField;
    @FXML private TextField removeItemQuantityField;
    @FXML private Button removeItemButton;

    @FXML private Button viewStockButton;
    @FXML private Button resetAllButton;

    @FXML private TextArea outputArea;

    @FXML private AnchorPane mapPane; // interactive map

    @FXML
    public void initialize() {
        createAisleButton.setDisable(true);
        createShelfButton.setDisable(true);
        smartAddButton.setDisable(true);
        searchItemButton.setDisable(true);
        removeItemButton.setDisable(true);
        viewStockButton.setDisable(true);
        resetAllButton.setDisable(false);
    }

    // Creation
    @FXML
    public void createFloor() {
        String floorText = floorNumberField.getText().trim();
        if (floorText.isEmpty()) {
            outputArea.appendText("Enter a floor number.\n");
            return;
        }
        FloorArea floor = new FloorArea(floorText, Integer.parseInt(floorText));
        api.addFloorArea(floor);
        outputArea.appendText("Floor '" + floorText + "' created.\n");

        floorComboBox.getItems().add(floorText);
        createAisleButton.setDisable(false);
        smartAddButton.setDisable(false);

        drawMap();
    }

    @FXML
    public void createAisle() {
        String selectedFloor = floorComboBox.getSelectionModel().getSelectedItem();
        if (selectedFloor == null) {
            outputArea.appendText("Select a floor first.\n");
            return;
        }

        String name = aisleNameField.getText().trim();
        String lengthText = aisleLengthField.getText().trim();
        String widthText = aisleWidthField.getText().trim();
        String temp = aisleTemperatureField.getText().trim();

        if (name.isEmpty() || lengthText.isEmpty() || widthText.isEmpty() || temp.isEmpty()) {
            outputArea.appendText("Fill all aisle fields.\n");
            return;
        }

        FloorArea floor = api.getFloorAreaByName(selectedFloor);
        if (floor == null) return;

        Aisle aisle = new Aisle(name, temp, Integer.parseInt(lengthText), Integer.parseInt(widthText), new LinkedList<>());
        floor.addAisle(aisle);

        outputArea.appendText("Aisle '" + name + "' added to floor '" + selectedFloor + "'.\n");
        createShelfButton.setDisable(false);

        drawMap();
    }

    @FXML
    public void createShelf() {
        String selectedFloor = floorComboBox.getSelectionModel().getSelectedItem();
        String selectedAisle = aisleComboBoxShelf.getSelectionModel().getSelectedItem();

        if (selectedFloor == null || selectedAisle == null) {
            outputArea.appendText("Select floor and aisle.\n");
            return;
        }

        String shelfText = shelfNumberField.getText().trim();
        if (shelfText.isEmpty()) {
            outputArea.appendText("Enter a shelf number.\n");
            return;
        }

        FloorArea floor = api.getFloorAreaByName(selectedFloor);
        Aisle aisle = floor.getAisleByName(selectedAisle);

        if (aisle == null) return;

        Shelf shelf = new Shelf(Integer.parseInt(shelfText), new LinkedList<>());
        aisle.addShelf(shelf);

        outputArea.appendText("Shelf " + shelfText + " added to aisle '" + selectedAisle + "'.\n");

        drawMap();
    }

    // smart add
    @FXML
    public void smartAddItem() {
        String desc = smartDescriptionField.getText().trim();
        String temp = smartTemperatureField.getText().trim();
        String qtyText = smartQuantityField.getText().trim();

        if (desc.isEmpty() || temp.isEmpty() || qtyText.isEmpty()) {
            outputArea.appendText("Fill all Smart Add fields.\n");
            return;
        }

        GoodItems item = new GoodItems(desc, "", 0.0, Integer.parseInt(qtyText), temp, "");
        api.smartAdd(item);

        outputArea.appendText("Added " + qtyText + " of '" + desc + "' to stock.\n");
    }

    // search item
    @FXML
    public void searchItem() {
        String name = searchItemNameField.getText().trim();
        if (name.isEmpty()) {
            outputArea.appendText("Enter an item name.\n");
            return;
        }

        StringBuilder results = new StringBuilder();
        for (Node<FloorArea> f = api.getFloorAreas().head; f != null; f = f.next) {
            for (Node<Aisle> a = f.data.getAisles().head; a != null; a = a.next) {
                for (Node<Shelf> s = a.data.getShelves().head; s != null; s = s.next) {
                    for (Node<GoodItems> i = s.data.getItems().head; i != null; i = i.next) {
                        if (i.data.getDescription().equalsIgnoreCase(name)) {
                            results.append("Found '").append(name).append("' on Floor '")
                                    .append(f.data.getName()).append("', Aisle '")
                                    .append(a.data.getAisleName()).append("', Shelf ")
                                    .append(s.data.getShelfNumber()).append(", Qty=")
                                    .append(i.data.getQuantity()).append("\n");
                        }
                    }
                }
            }
        }

        if (results.isEmpty()) results.append("Item '").append(name).append("' not found.\n");
        outputArea.appendText(results.toString());
    }

    // Remove item
    @FXML
    public void removeItem() {
        String floor = removeFloorField.getText().trim();
        String aisle = removeAisleField.getText().trim();
        String shelf = removeShelfField.getText().trim();
        String name = removeItemNameField.getText().trim();
        String qty = removeItemQuantityField.getText().trim();

        if (floor.isEmpty() || aisle.isEmpty() || shelf.isEmpty() || name.isEmpty() || qty.isEmpty()) {
            outputArea.appendText("Fill all remove fields.\n");
            return;
        }

        api.removeGoodItem(floor, aisle, Integer.parseInt(shelf), name, Integer.parseInt(qty));
        outputArea.appendText("Attempted removal of " + qty + " " + name + " from Shelf " + shelf + ".\n");
    }

    // viewstock
    @FXML
    public void viewStock() {
        StringBuilder report = new StringBuilder();
        for (Node<FloorArea> f = api.getFloorAreas().head; f != null; f = f.next) {
            report.append("=== Floor: ").append(f.data.getName()).append(" ===\n");
            for (Node<Aisle> a = f.data.getAisles().head; a != null; a = a.next) {
                report.append("Aisle: ").append(a.data.getAisleName())
                        .append(" (").append(a.data.getTemperature()).append(")\n");
                for (Node<Shelf> s = a.data.getShelves().head; s != null; s = s.next) {
                    report.append("  Shelf ").append(s.data.getShelfNumber()).append(":\n");
                    for (Node<GoodItems> i = s.data.getItems().head; i != null; i = i.next) {
                        report.append("    ").append(i.data.getDescription())
                                .append(" Qty=").append(i.data.getQuantity()).append("\n");
                    }}}}
        outputArea.appendText(report.toString());
    }
    // reset
    @FXML
    public void resetSystem() {
        api = new SupermarketAPI();

        floorNumberField.clear();
        aisleNameField.clear();
        aisleLengthField.clear();
        aisleWidthField.clear();
        aisleTemperatureField.clear();
        shelfNumberField.clear();
        smartDescriptionField.clear();
        smartTemperatureField.clear();
        smartQuantityField.clear();
        searchItemNameField.clear();
        removeFloorField.clear();
        removeAisleField.clear();
        removeShelfField.clear();
        removeItemNameField.clear();
        removeItemQuantityField.clear();

        floorComboBox.getItems().clear();
        aisleComboBoxShelf.getItems().clear();

        outputArea.clear();
        mapPane.getChildren().clear();

        createAisleButton.setDisable(true);
        createShelfButton.setDisable(true);
        smartAddButton.setDisable(true);
        searchItemButton.setDisable(true);
        removeItemButton.setDisable(true);
        viewStockButton.setDisable(true);

        outputArea.appendText("System reset completed!\n");
    }

    // map
    // took me around a day to figure out through testing and online vids
    @FXML
    public void drawMap() {
        mapPane.getChildren().clear();

        double floorStartY = 10; //starting Y position for the first floor
        double floorHeight = 100; //height of each floor rectangle
        double floorSpacing = 20; //spacing between floors
        double floorWidth = 600; //fixed width for simplicity
        double floorStartX = 10; //left margin

        //loop through each floor
        for (Node<FloorArea> floorsAreCool = api.getFloorAreas().head; floorsAreCool != null; floorsAreCool = floorsAreCool.next) {
            FloorArea floor = floorsAreCool.data;

            Rectangle floorRect = new Rectangle(floorStartX, floorStartY, floorWidth, floorHeight);
            floorRect.setStrokeWidth(2);

            mapPane.getChildren().add(floorRect);

            // add floor label
            Label floorLabel = new Label("Floor: " + floor.getName());
            floorLabel.setLayoutX(floorStartX + 5);
            floorLabel.setLayoutY(floorStartY + 5);
            mapPane.getChildren().add(floorLabel);

            // draw aisles inside the floor
            double aisleStartX = floorStartX + 10; // margin inside floor
            double aisleStartY = floorStartY + 30; // start below floor label
            double aisleWidth = 80; // fixed width for aisles
            double aisleHeight = 20; // fixed height for aisles
            double aisleSpacing = 10; // spacing between aisles

            for (Node<Aisle> aNode = floor.getAisles().head; aNode != null; aNode = aNode.next) {
                Aisle aisle = aNode.data;

                Rectangle rect = new Rectangle(aisleStartX, aisleStartY, aisleWidth, aisleHeight);
                rect.setStrokeWidth(1);
                mapPane.getChildren().add(rect);

                Label AL = new Label(aisle.getAisleName());
                AL.setLayoutX(aisleStartX + 5);
                AL.setLayoutY(aisleStartY + 2);
                mapPane.getChildren().add(AL);

                // move to next aisle vertically
                aisleStartY += aisleHeight + aisleSpacing;
            }

            // move to next floor
            floorStartY += floorHeight + floorSpacing;
        }
    }

}
