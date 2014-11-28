package Models;

/**
 * Created by Stefan on 11/28/2014.
 */
public class SQLiteRecipeModel {
    private String name;
    private String ingredients;
    private String spices;
    private String preparing;
    private int timesSeen;

    public SQLiteRecipeModel(String name, String ingredients, String spices, String preparing, int timesSeen){
        this.name = name;
        this.ingredients = ingredients;
        this.spices = spices;
        this.preparing = preparing;
        this.timesSeen = timesSeen;
    }

    public String getName(){
        return this.name;
    }

    public String getIngredients(){
        return this.ingredients;
    }

    public String getSpices(){
        return this.spices;
    }

    public String getPreparing(){
        return this.preparing;
    }

    public int getTimesSeen(){
        return  this.timesSeen;
    }
}
