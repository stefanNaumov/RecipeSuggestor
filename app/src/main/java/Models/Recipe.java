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
    private List<String> ingredients;
    private List<String> spices;


    public Recipe(String name){
        this.name = name;
        this.ingredients = new ArrayList<String>();
        this.spices = new ArrayList<String>();
        this.timesUsed = 0;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public List<String> getIngredients(){
        return this.ingredients;
    }

    public void setIngredients(List<String> ingredients){
        this.ingredients = ingredients;
    }

    public List<String> getSpices(){
        return this.spices;
    }

    public void setSpices(List<String> spices){
        this.spices = spices;
    }

    public String getPreparing(){
        return this.preparing;
    }

    public void setPreparing(String preparing){
        this.preparing = preparing;
    }
}
