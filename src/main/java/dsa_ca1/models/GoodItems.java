package dsa_ca1.models;


public class GoodItems {
    private String itemName;
    private String description;
    private String unitSize;
    private double unitPrice;
    private int quantity;
    private String storageTemperature;
    private String photoURL;

    public GoodItems(String itemName, String description, String unitSize, double unitPrice, int quantity, String storageTemperature, String photoURL) {
        this.itemName = itemName;
        this.description = description;
        this.unitSize = unitSize;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.storageTemperature = storageTemperature;
        this.photoURL = photoURL;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnitSize() {
        return unitSize;
    }

    public void setUnitSize(String unitSize) {
        this.unitSize = unitSize;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStorageTemperature() {
        return storageTemperature;
    }

    public void setStorageTemperature(String storageTemperature) {
        this.storageTemperature = storageTemperature;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public double getTotalValue() {
        return unitPrice * quantity;
    }


    @Override
    public String toString() {
        return "GoodItems{" +
                "itemName='" + itemName + '\'' +
                "storageTemperature='" + storageTemperature + '\'' +
                ", description='" + description + '\'' +
                ", unitSize='" + unitSize + '\'' +
                ", unitPrice=" + unitPrice +
                ", quantity=" + quantity +
                '}';
    }
}
