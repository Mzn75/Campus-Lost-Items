package models;

public class Jewelry extends CampusItem{

    //Instance Variables
    private String itemType;
    private String ColorOrMetal;

    //Constructor
    public Jewelry(int itemId, String description, String itemLocation, int phoneNum, String type, String clrOrMtl){
        super(itemId, description, itemLocation, phoneNum);
        this.itemType = type;
        this.ColorOrMetal = clrOrMtl;
    }

    //Getters
    public String getItemType(){return itemType;}
    public String getColorOrMetal(){return ColorOrMetal;}

    //Implementing claculateMatchScore()
    @Override
    public int calculateMatchScore(CampusItem otherItem) {
        int score = 0;

        if (otherItem instanceof Jewelry) {

            //Parsing to Jewelry
            Jewelry otherJewelry = (Jewelry) otherItem;

            //Checking the Type First
            if (!this.itemType.equalsIgnoreCase(otherJewelry.getItemType())) {

                //If it's not the same type it doesn't get any points
                return 0;

            }else {

                //If it's the same type give it 50 Points
                score += 50;

            }

            //If It's the same color or metal give it 40 Points
            if (this.ColorOrMetal.equalsIgnoreCase(otherJewelry.getColorOrMetal())) {

                score += 40;

            }

            //If It's the same location or like it give it 10 Points
            if (this.getItemLocation() != null && otherJewelry.getItemLocation() != null &&
                    !this.getItemLocation().trim().isEmpty() && !otherJewelry.getItemLocation().trim().isEmpty()) {

                // 2. Convert both to lowercase for easy comparison
                String myLoc = this.getItemLocation().toLowerCase();
                String otherLoc = otherJewelry.getItemLocation().toLowerCase();

                // 3. Forgiving match: Check if one word is inside the other
                if (myLoc.contains(otherLoc) || otherLoc.contains(myLoc)) {
                    score += 10;
                }
            }
        }
        return score;
    }

}
