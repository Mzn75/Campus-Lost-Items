package models;

public class ElectronicDevice extends CampusItem {

    //Instance Variables
    private String brand;
    private String type;
    private String model;

    //Constructor
    public ElectronicDevice(int itemId, String description, String itemLocation, int phoneNum, String b, String m, String t){
        super(itemId, description, itemLocation, phoneNum);
        this.brand = b;
        this.model = m;
        this.type = t;
    }

    //Getters
    public String getBrand(){return brand;}
    public String getModel(){return model;}
    public String getType(){return type;}

    //Implementing claculateMatchScore()
    @Override
    public int calculateMatchScore(CampusItem otherItem){
        int score = 0;

        if (otherItem instanceof ElectronicDevice){

            //Parsing to ElectronicDevice
            ElectronicDevice otherDevice = (ElectronicDevice) otherItem;

            //Checking the Type First
            if (!this.type.equalsIgnoreCase(otherDevice.getType())) {

                //If it's not the same type it doesn't get any points
                return 0;

            }else{

                //If it's the same type give it 30 Points
                score += 30;

            }

            //If It's the same brand give it 40 Points
            if (this.brand.equalsIgnoreCase(otherDevice.getBrand())){
                score += 40;
            }

            //If it's the same model give it 20 Points
            if (this.model.equalsIgnoreCase(otherDevice.getModel())){
                 score += 20;
            }

            //If it's the same location give it 10 Points
            if (this.getItemLocation() != null && otherDevice.getItemLocation() != null && !this.getItemLocation().trim().isEmpty() && !otherDevice.getItemLocation().trim().isEmpty()) {

                String myLoc = this.getItemLocation().toLowerCase();
                String otherLoc = otherDevice.getItemLocation().toLowerCase();

                if (myLoc.contains(otherLoc) || otherLoc.contains(myLoc)) {
                    score += 10;
                }
            }

        }

        return score;
    }

}
