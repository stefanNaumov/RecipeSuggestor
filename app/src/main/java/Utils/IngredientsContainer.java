package Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stefan on 11/30/2014.
 */

/*class used for storing the ingredients list - if the user goes back from the addIngredientsActivity
    and then returns, the added ingredients will be loaded*/

public class IngredientsContainer {
    private static IngredientsContainer instance;
    private static List<String> ingredientsList;

    public static IngredientsContainer getInstance(){
        if (instance == null) {
            instance = new IngredientsContainer();
            ingredientsList = new ArrayList<String>();
        }

        return instance;
    }

    public List<String> getIngredientsList(){
        return ingredientsList;
    }

    public void AddIngredient(String ingredient){
        if (ingredientsList == null){
            return;
        }

        ingredientsList.add(ingredient);
    }

    public void clearList(){
        ingredientsList.clear();
    }

}
