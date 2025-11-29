package dsa_ca1.controllers;

import dsa_ca1.models.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.List;
import java.util.ArrayList;



public class SupermarketController {


    private SupermarketAPI API = new SupermarketAPI();

    @FXML private TabPane mainTabPane;

    @FXML private TextField floorNameField;

    @FXML private TextField floorNumberField;

    @FXML private Button createFloorButton;

    @FXML private ComboBox<String> floorComboBox;

    @FXML private TextField aisleNameField;

    @FXML private TextField aisleLengthField;

    @FXML private TextField aisleWidthField;

    @FXML private ChoiceBox<String> aisleTemperatureField;

    @FXML private Button createAisleButton;

    @FXML private ComboBox<String> aisleComboBoxShelf;

    @FXML private ComboBox<String> floorComboBoxShelf;

    @FXML private TextField shelfNumberField;

    @FXML private Button createShelfButton;

    @FXML private ComboBox<String> floorComboBoxItem;

    @FXML private ComboBox<String> aisleComboBoxItem;

    @FXML private ComboBox<String> shelfComboBoxItem;

    @FXML private TextField itemNameField;

    @FXML private TextField itemDescriptionField;

    @FXML private TextField unitSizeField;

    @FXML private TextField unitPriceField;

    @FXML private TextField quantityField;

    @FXML private ChoiceBox<String> storageField;

    @FXML private TextField photoURLField;

    @FXML private Button createItemButton;

    @FXML private TextField smartNameField;

    @FXML private TextField smartDescriptionField;

    @FXML private TextField smartQuantityField;

    @FXML private TextField smartPriceField;

    @FXML private ChoiceBox<String> smartTemperatureField;

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

    @FXML private AnchorPane mapPane;

    @FXML
    public void initialize() {
        Tab AISLES_TAB = mainTabPane.getTabs().get(1);
        Tab SHELF_TAB = mainTabPane.getTabs().get(2);
        Tab ITEMS_TAB = mainTabPane.getTabs().get(3);

        AISLES_TAB.setDisable(true);
        SHELF_TAB.setDisable(true);
        ITEMS_TAB.setDisable(true);

        //action buttons
        //these three unlock after the prerequisite is made
        createFloorButton.setOnAction(e -> {
            createFloor();
            AISLES_TAB.setDisable(false);
        });
        createAisleButton.setOnAction(e -> {
            createAisle();
            SHELF_TAB.setDisable(false);
        });
        createShelfButton.setOnAction(e -> {
            createShelf();
            ITEMS_TAB.setDisable(false);
        });
        floorComboBoxShelf.setOnAction(e -> refreshAisleComboBoxes());
        floorComboBoxItem.setOnAction(e -> refreshAisleComboBoxes());
        aisleComboBoxItem.setOnAction(e -> refreshShelfComboBoxes());
        createItemButton.setOnAction(e -> createItem());
        smartAddButton.setOnAction(e -> smartAddItem());
        searchItemButton.setOnAction(e -> searchItem());
        removeItemButton.setOnAction(e -> removeItem());
        viewStockButton.setOnAction(e -> viewStock());
        resetAllButton.setOnAction(e -> resetAll());

        //initializing populating
        refreshFloorComboBoxes();
        refreshAisleComboBoxes();
        refreshShelfComboBoxes();

        //populate storageChoiceBox for Smart Add
        storageField.getItems().clear();
        storageField.getItems().addAll("Regular", "Chilled", "Frozen");
        storageField.setValue("Regular");

        //initializing map
        drawMap();
    }
    //all the create methods
    //.trim() in case of accidental spaces which could break certain operation methods
    private void createFloor() {
        String floorName = floorNameField.getText().trim();
        int floorNum = parseFloorNumber();

        if (floorName.isEmpty()) {
            outputArea.appendText("Error: Floor name cannot be empty.\n");
            return;
        }
        if (floorNum == -1) {
            outputArea.appendText("Error: Must enter a valid floor number.\n");
            return;
        }
        //floor creation
        FloorArea floor = new FloorArea(floorName, floorNum);
        API.addFloorArea(floor);
        outputArea.appendText("Created Floor: " + floorName + "\n");
        refreshFloorComboBoxes();
        drawMap();
    }



    private void createAisle() {
        String floorName = floorComboBox.getValue();
        String name = aisleNameField.getText().trim();
        int length = Integer.parseInt(aisleLengthField.getText());
        int width = Integer.parseInt(aisleWidthField.getText());
        String temperature = aisleTemperatureField.getValue();


        FloorArea floor = API.getFloorAreaByName(floorName);
        if (floor != null) {
            Aisle aisle = new Aisle(name, temperature, length, width, new LinkedList<>());
            floor.getAisles().add(aisle);

            outputArea.appendText("Created Aisle: " + name + " on Floor: " + floorName + "\n");

            refreshAisleComboBoxes();
            drawMap();
        }
    }

    private void createShelf() {
        String floorName = floorComboBoxShelf.getValue();
        String aisleName = aisleComboBoxShelf.getValue();
        int shelfNumber = parseShelfNumber();

        if (shelfNumber == -1) return;

        FloorArea floor = API.getFloorAreaByName(floorName);
        if (floor != null) {
            for (Node<Aisle> a = floor.getAisles().head; a != null; a = a.next) {
                if (a.data.getAisleName().equals(aisleName)) {
                    Shelf shelf = new Shelf(shelfNumber, new LinkedList<>());
                    a.data.getShelves().add(shelf);

                    outputArea.appendText("Created Shelf: " + shelfNumber + "\n" + " on Aisle: " + aisleName + "\n");

                    refreshShelfComboBoxes();
                }
            }
        }
    }

    private void createItem() {
        String floorName = floorComboBoxItem.getValue();
        String aisleName = aisleComboBoxItem.getValue();
        int shelfNumber = Integer.parseInt(shelfComboBoxItem.getValue());
        String name = itemNameField.getText().trim();
        String description = itemDescriptionField.getText().trim();
        String size = unitSizeField.getText().trim();
        double price = Double.parseDouble(unitPriceField.getText());
        int quantity = parseItemQuantity();
        if (quantity == -1) return;
        String storage = storageField.getValue();
        String photoURL = photoURLField.getText().trim();

        FloorArea floor = API.getFloorAreaByName(floorName);
        if (floor != null) {
            for (Node<Aisle> a = floor.getAisles().head; a != null; a = a.next) {
                if (a.data.getAisleName().equals(aisleName)) {
                    for (Node<Shelf> s = a.data.getShelves().head; s != null; s = s.next) {
                        if (s.data.getShelfNumber() == shelfNumber) {
                            GoodItems item = new GoodItems(name, description, size, price, quantity, storage, photoURL);
                            s.data.getItems().add(item);

                            outputArea.appendText("Created Item: " + name + "\n" + " Description: " + "\n" + "Qty: " + quantity + "\n" + " on Shelf: " + shelfNumber + "\n");

                            refreshItemComboBoxes();
                        }
                    }
                }
            }
        }
    }

    //smart add
    private void smartAddItem() {
        String name = smartNameField.getText().trim();
        int quantity = Integer.parseInt(smartQuantityField.getText());
        String storage = smartTemperatureField.getValue().trim();
        String description = smartDescriptionField.getText().trim();
        double price = Double.parseDouble(smartPriceField.getText());

        GoodItems item = new GoodItems(name, description, "", price, quantity, storage, "");
        String result = API.smartAdd(item);
        outputArea.appendText(result + "\n");
    }

    //search
    private void searchItem() {
        String name = searchItemNameField.getText().trim();
        String result = API.searchGoodItem(name);  // now returns a message
        outputArea.appendText(result);
    }
    //remove
    private void removeItem() {
        String floor = removeFloorField.getText().trim();
        String aisle = removeAisleField.getText().trim();
        int shelf = Integer.parseInt(removeShelfField.getText());
        String itemName = removeItemNameField.getText().trim();
        int qty = Integer.parseInt(removeItemQuantityField.getText());

        API.removeGoodItem(floor, aisle, shelf, itemName, qty);
    }

    //view stock
    private void viewStock() {
        String report = API.viewAllStock();
        outputArea.appendText(report + "\n");
    }

    //reset supermarket
    private void resetAll() {
        API.setFloorAreas(new LinkedList<>());
        refreshFloorComboBoxes();
        refreshAisleComboBoxes();
        refreshShelfComboBoxes();
        outputArea.appendText("Reset All Data\n");
        mapPane.getChildren().clear();
    }

    // combobox refreshing
    private void refreshFloorComboBoxes() {
        floorComboBox.getItems().clear();
        floorComboBoxShelf.getItems().clear();
        floorComboBoxItem.getItems().clear();
        for (Node<FloorArea> FLOOR = API.getFloorAreas().head; FLOOR != null; FLOOR = FLOOR.next) {
            String name = FLOOR.data.getName();
            floorComboBox.getItems().add(name);
            floorComboBoxShelf.getItems().add(name);
            floorComboBoxItem.getItems().add(name);
        }
    }

    private void refreshAisleComboBoxes() {
        aisleComboBoxShelf.getItems().clear();
        aisleComboBoxItem.getItems().clear();

        String floorNameShelf = floorComboBoxShelf.getValue();
        String floorNameItem = floorComboBoxItem.getValue();

        //checks the floor to add aisles to the combo box
        if (floorNameShelf != null) {
            FloorArea FLOOR = API.getFloorAreaByName(floorNameShelf);
            if (FLOOR != null) {
                for (Node<Aisle> AISLE = FLOOR.getAisles().head; AISLE != null; AISLE = AISLE.next) {
                    aisleComboBoxShelf.getItems().add(AISLE.data.getAisleName());
                }
            }
        }
        //same thing but with items
        if (floorNameItem != null) {
            FloorArea floor = API.getFloorAreaByName(floorNameItem);
            if (floor != null) {
                for (Node<Aisle> AISLE = floor.getAisles().head; AISLE != null; AISLE = AISLE.next) {
                    aisleComboBoxItem.getItems().add(AISLE.data.getAisleName());
                }
            }
            refreshShelfComboBoxes();
        }
    }

    private void refreshShelfComboBoxes() {
        shelfComboBoxItem.getItems().clear();
        String floorName = floorComboBoxItem.getValue();
        String aisleName = aisleComboBoxItem.getValue();

        if (floorName != null && aisleName != null) {
            FloorArea floor = API.getFloorAreaByName(floorName);
            Aisle aisle = floor.getAisleByName(aisleName);
            for (Node<Shelf> s = aisle.getShelves().head; s != null; s = s.next) {
                shelfComboBoxItem.getItems().add(String.valueOf(s.data.getShelfNumber()));
            }
        }
    }

    private void refreshItemComboBoxes() {
        shelfComboBoxItem.getItems().clear();
        String selectedFloor = floorComboBoxItem.getValue();
        String selectedAisle = aisleComboBoxItem.getValue();

        if (selectedFloor != null && selectedAisle != null) {
            FloorArea floor = API.getFloorAreaByName(selectedFloor);
            if (floor != null) {
                Aisle aisle = floor.getAisleByName(selectedAisle);
                if (aisle != null) {
                    for (Node<Shelf> s = aisle.getShelves().head; s != null; s = s.next) {
                        shelfComboBoxItem.getItems().add(String.valueOf(s.data.getShelfNumber()));
                    }
                }
            }
        }
    }

    //map (i needed help from other people to complete this, never done this before)
    private void drawMap() {
        mapPane.getChildren().clear();

        double padding = 10;
        double startX = padding;
        double startY = padding;

        Canvas canvas = new Canvas(mapPane.getWidth(), mapPane.getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();

        List<Rectangle> floorRects = new ArrayList<>();
        List<FloorArea> floorData = new ArrayList<>();
        List<Rectangle> aisleRects = new ArrayList<>();
        List<Aisle> aisleData = new ArrayList<>();
        List<Double> floorYs = new ArrayList<>();
        List<Double> aisleXs = new ArrayList<>();
        List<Double> aisleYs = new ArrayList<>();

        for (Node<FloorArea> f = API.getFloorAreas().head; f != null; f = f.next) {
            FloorArea floor = f.data;
            //floor make
            double floorWidth = 300;
            double floorHeight = 150;
            gc.setFill(Color.LIGHTBLUE);
            gc.fillRect(startX, startY, floorWidth, floorHeight);
            gc.setStroke(Color.BLUE);
            gc.strokeRect(startX, startY, floorWidth, floorHeight);
            //floor css and label
            gc.setFill(Color.BLACK);
            gc.fillText(floor.getName(), startX + 5, startY + 15);
            //mouse interactions
            floorRects.add(new Rectangle(startX, startY, floorWidth, floorHeight));
            floorData.add(floor);
            floorYs.add(startY);
            //draw aisles
            double aisleX = startX + 10;
            double aisleY = startY + 25;
            double aisleHeight = 40;
            double aisleWidth = 60;

            for (Node<Aisle> a = floor.getAisles().head; a != null; a = a.next) {
                Aisle aisle = a.data;

                gc.setFill(Color.LIGHTGREEN);
                gc.fillRect(aisleX, aisleY, aisleWidth, aisleHeight);
                gc.setStroke(Color.GREEN);
                gc.strokeRect(aisleX, aisleY, aisleWidth, aisleHeight);
                gc.setFill(Color.BLACK);
                gc.fillText(aisle.getAisleName(), aisleX + 3, aisleY + 15);
                aisleRects.add(new Rectangle(aisleX, aisleY, aisleWidth, aisleHeight));
                aisleData.add(aisle);
                aisleXs.add(aisleX);
                aisleYs.add(aisleY);

                aisleX += aisleWidth + 10;
            }

            startY += 200;
        }

        //mouse events
        canvas.setOnMouseClicked(e -> {
            double x = e.getX();
            double y = e.getY();

            //logic for floor and aisle clicks(interactive gui)
            for (int i = 0; i < floorRects.size(); i++) {
                Rectangle r = floorRects.get(i);
                if (r.contains(x, y)) {
                    FloorArea floor = floorData.get(i);
                    mainTabPane.getSelectionModel().select(0);
                    floorNumberField.setText(String.valueOf(floor.getFloor()));
                    floorNameField.setText(floor.getName());
                    return;
                }
            }

            for (int i = 0; i < aisleRects.size(); i++) {
                Rectangle r = aisleRects.get(i);
                if (r.contains(x, y)) {
                    Aisle aisle = aisleData.get(i);
                    // Find corresponding floor for this aisle
                    int floorIndex = i / (aisleRects.size() / floorRects.size()); // approximate
                    FloorArea floor = floorData.get(floorIndex);

                    mainTabPane.getSelectionModel().select(1); // Aisle tab
                    floorComboBoxShelf.setValue(floor.getName());
                    aisleNameField.setText(aisle.getAisleName());
                    return;
                }
            }
        });

        mapPane.getChildren().add(canvas);
    }

    // save and load
        @FXML
    public void saveData() {
        try {
            API.save();
            outputArea.appendText("Saved.\n");
        } catch (Exception e) {
            outputArea.appendText("Error: " + e.getMessage() + "\n");
            System.err.println("Save error: " + e);
        }
    }

    @FXML
    public void loadData() {
        try {
            API.load();
            refreshFloorComboBoxes();
            refreshAisleComboBoxes();
            refreshShelfComboBoxes();
            drawMap();

            outputArea.appendText("Loaded supermarket data.\n");
        } catch (Exception e) {
            outputArea.appendText("Error loading data: " + e.getMessage() + "\n");
            System.err.println("Load error: " + e);
        }
    }

    //validation
    // parsing
    private int parseFloorNumber() {
        try {
            return Integer.parseInt(floorNumberField.getText().trim());
        } catch (NumberFormatException e) {
            outputArea.appendText("Error: Needs to be integer.\n");
            return -1; // -1 indicates invalid input
        }
    }

    private int parseShelfNumber() {
        try {
            return Integer.parseInt(shelfNumberField.getText().trim());
        } catch (NumberFormatException e) {
            outputArea.appendText("Error: Needs to be integer.\n");
            return -1;
        }
    }

    private int parseItemQuantity() {
        try {
            return Integer.parseInt(quantityField.getText().trim());
        } catch (NumberFormatException e) {
            outputArea.appendText("Error: Needs to be int.\n");
            return -1;
        }
    }
}
