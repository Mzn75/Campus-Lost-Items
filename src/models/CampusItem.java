package models;
import java.io.Serializable;

public abstract class CampusItem implements Serializable, Matchable {

    //Instance Variables
    private int itemID;
    private String description;
    private String itemLocation;
    private int contactPhone;

    //Constructor
    public CampusItem(int itemId, String description, String itemLocation, int phoneNum){
        this.itemID = itemId;
        this.description = description;
        this.itemLocation = itemLocation;
        this.contactPhone = phoneNum;
    }

    //Getters
    public int getItemID() {
        return itemID;
    }
    public String getDescription() {
        return description;
    }
    public String getItemLocation() { return itemLocation; }
    public int getContactPhone() {
        return contactPhone;
    }

    //Setters

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setCampusLocation(String location) {
        this.itemLocation = location;
    }
    public void setContactPhone(int contactPhone) {
        this.contactPhone = contactPhone;
    }
}
