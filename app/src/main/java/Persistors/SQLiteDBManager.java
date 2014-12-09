package Persistors;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import Models.Recipe;

/**
 * Created by Stefan on 11/28/2014.
 */
public class SQLiteDBManager  extends SQLiteDBHelper{

    public SQLiteDBManager(Context context) {
        super(context);
    }

    public void addRecord(String recipeName, String ingredients, String spices, String preparing){
        if (recipeName == null || recipeName.isEmpty() || ingredients == null || ingredients.isEmpty()
                || spices == null || spices.isEmpty() || preparing == null || preparing.isEmpty()){
            return;
        }

        try {
            open();

            ContentValues values = new ContentValues();
            values.put(SQLiteDBHelper.COLUMN_NAME,recipeName);
            values.put(SQLiteDBHelper.COLUMN_INGREDIENTS,ingredients);
            values.put(SQLiteDBHelper.COLUMN_SPICES,spices);
            values.put(SQLiteDBHelper.COLUMN_PREPARING,preparing);
            values.put(SQLiteDBHelper.COLUMN_TIMES_USED,0);

            db.insert(SQLiteDBHelper.TABLE_RECIPES,null,values);

            close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateTimesSeen(String name, int timesUsed){
        ContentValues values = new ContentValues();
        values.put(SQLiteDBHelper.COLUMN_TIMES_USED,timesUsed);

        this.updateRecord(name, values);
    }

    public void deleteAll(){
        try {
            open();
            String selectQuery = "DELETE FROM recipes";
            db.execSQL(selectQuery);

        }
        catch (SQLException e){
            e.printStackTrace();
        }

        close();
    }

    public Recipe getById(int id){
        String query = "SELECT " + SQLiteDBHelper.COLUMN_ID + ", "
                + SQLiteDBHelper.COLUMN_NAME + ", "
                + SQLiteDBHelper.COLUMN_INGREDIENTS + ", "
                + SQLiteDBHelper.COLUMN_SPICES + ", "
                + SQLiteDBHelper.COLUMN_PREPARING + ", "
                + SQLiteDBHelper.COLUMN_TIMES_USED + " "
                + "FROM " + SQLiteDBHelper.TABLE_RECIPES + " "
                + "WHERE " + SQLiteDBHelper.COLUMN_ID + "= \"" +  id + "\"" ;

        return this.getRecipeByQuery(query);
    }

    public Recipe getByName(String name){
        String query = "SELECT " + SQLiteDBHelper.COLUMN_ID + ", "
                + SQLiteDBHelper.COLUMN_NAME + ", "
                + SQLiteDBHelper.COLUMN_INGREDIENTS + ", "
                + SQLiteDBHelper.COLUMN_SPICES + ", "
                + SQLiteDBHelper.COLUMN_PREPARING + ", "
                + SQLiteDBHelper.COLUMN_TIMES_USED + " "
                + "FROM " + SQLiteDBHelper.TABLE_RECIPES + " "
                + "WHERE " + SQLiteDBHelper.COLUMN_NAME + "= \"" +  name + "\"" ;

        return this.getRecipeByQuery(query);
    }

    public List<Recipe> getAll(){
        String query = "SELECT * FROM " + SQLiteDBHelper.TABLE_RECIPES;

        Recipe currModel;

        try {
            open();
            Cursor cursor = db.rawQuery(query,null);
            List<Recipe> modelsList = new ArrayList<Recipe>(cursor.getCount());

            if (cursor.moveToFirst()){
                while (!cursor.isAfterLast()){
                    currModel = this.mapModel(cursor);
                    modelsList.add(currModel);

                    cursor.moveToNext();
                }

                return modelsList;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            close();
        }

        return null;
    }

    public List<Recipe> getSortedByTimesUsed(){
        List<Recipe> collection = this.getAll();
        if (collection != null) {
            Collections.sort(collection, new TimesUsedComparator());
            Collections.reverse(collection);
            Log.d("SIZE", String.valueOf(collection.size()));
            return collection;
        }

        return null;
    }

    //get range of elements for "most used" list in mainactivity
    public List<Recipe> getSortedByTimesUsedWithRange(int range){
        List<Recipe> collection = this.getSortedByTimesUsed();
        if (collection != null) {

            if (range < collection.size()) {
                List<Recipe> rangeCollection = new ArrayList<Recipe>(range);
                for (int i = 0; i < range - 1; i++) {
                    rangeCollection.add(collection.get(i));
                }

                return rangeCollection;
            } else {
                return collection;
            }
        }

        return null;
    }

    public List<Recipe> getSortedByName(){
        List<Recipe> colleciton = this.getAll();
        Collections.sort(colleciton,new RecipeNameComparator());

        return colleciton;
    }

    private Recipe getRecipeByQuery(String query){
        Recipe recipeModel;
        try {
            open();

            Cursor cursor = db.rawQuery(query,null);

            if (cursor.moveToFirst()){
                recipeModel = this.mapModel(cursor);

                return recipeModel;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            close();
        }

        return null;
    }

    private void updateRecord(String username, ContentValues values){
        try {
            open();

            this.db.update(SQLiteDBHelper.TABLE_RECIPES,values,
                    SQLiteDBHelper.COLUMN_NAME + "=?", new String[]{username});
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    private Recipe mapModel(Cursor cursor){
        int id;
        String recipeName,ingredients,spices,preparing;
        int timesUsed;
        Recipe recipeModel;

        id = cursor.getInt(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_ID));
        recipeName = cursor.getString(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_NAME));
        ingredients = cursor.getString(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_INGREDIENTS));
        spices = cursor.getString(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_SPICES));
        preparing = cursor.getString(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_PREPARING));
        timesUsed = cursor.getInt(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_TIMES_USED));

        recipeModel = new Recipe(recipeName);
        recipeModel.setId(id);
        recipeModel.setIngredients(ingredients);
        recipeModel.setSpices(spices);
        recipeModel.setPreparing(preparing);
        recipeModel.setTimesUsed(timesUsed);

        return recipeModel;
    }

    static class TimesUsedComparator implements Comparator<Recipe>{

        @Override
        public int compare(Recipe recipe, Recipe recipe2) {
            return Integer.compare(recipe.getTimesUsed(),recipe2.getTimesUsed());
        }
    }

    static class RecipeNameComparator implements Comparator<Recipe>{

        @Override
        public int compare(Recipe recipe, Recipe recipe2) {
            return recipe.getName().compareTo(recipe2.getName());
        }
    }
}
