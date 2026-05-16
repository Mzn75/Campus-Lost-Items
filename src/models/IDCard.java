package models;

public class IDCard extends CampusItem{

    //Instance Variables
    private String StudentName;
    private int StudentID;

    //Constructor
    public IDCard(int itemId, String description, String itemLocation, int phoneNum, String n, int id){
        super(itemId, description, itemLocation, phoneNum);
        this.StudentName = n;
        this.StudentID = id;
    }

    //Getters
    public String getName(){return StudentName;}
    public int getId(){return StudentID;}

    //Implementing claculateMatchScore()
    @Override
    public int calculateMatchScore(CampusItem otherItem){
        int score = 0;

        if (otherItem instanceof IDCard){

            //Parsing to IDCard
            IDCard otherIDCard = (IDCard) otherItem;

            //Checking for the ID which is unique so if it's the same it gets 100 Points
            if (this.StudentID == otherIDCard.StudentID){
                score += 100;
            }

            //If it's the same name give it 80 Points
            if (this.StudentName.equalsIgnoreCase(otherIDCard.getName())){
                score += 80;
            }

        }

        return score;
    }
}
