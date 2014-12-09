package Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stefan on 11/27/2014.
 */
public class Recipe implements Serializable{
    public int timesUsed;

    private String name;
    private String preparing;
    private String ingredients;
    private String spices;
    private int Id;


    public Recipe(String name){
        this.name = name;
        this.timesUsed = 0;
    }


    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getIngredients(){
        return this.ingredients;
    }

    public void setIngredients(String ingredients){
        this.ingredients = ingredients;
    }

    public String getSpices(){
        return this.spices;
    }

    public void setSpices(String spices){
        this.spices = spices;
    }

    public String getPreparing(){
        return this.preparing;
    }

    public void setPreparing(String preparing){
        this.preparing = preparing;
    }

    public int getTimesUsed(){
        return this.timesUsed;
    }

    public void setTimesUsed(int timesUsed){
        this.timesUsed = timesUsed;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }
}
