package models;
import java.io.Serializable;

public class NetworkRequest implements Serializable {

    //Instance Variables
    private String action;
    private CampusItem data;

    //Constructor
    public NetworkRequest(String a, CampusItem d) {
        this.action = a;
        this.data = d;
    }

    //Getters
    public String getAction(){ return action; }

    public CampusItem getData(){ return data; }

}
