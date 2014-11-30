package com.example.stefan.recipesuggestor;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import Models.Recipe;
import Persistors.SQLiteDBManager;
import Utils.RecipeAdapter;


public class MyRecipesActivity extends Activity{

    ListView listView;
    RecipeAdapter adapter;
    SQLiteDBManager dbManager;
    List<Recipe> recipeList;
    EditText searchText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recipes);

        this.init();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_recipes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void init(){
        listView = (ListView)findViewById(R.id.myRecipesListViewId);
        dbManager = new SQLiteDBManager(this);
        recipeList = dbManager.getSortedByName();
        adapter = new RecipeAdapter(this,R.layout.main_list_row_recipe,recipeList);
        searchText = (EditText)findViewById(R.id.myRecipesSearchViewId);
        listView.setAdapter(adapter);

        this.AddTextChangeListener(searchText);
    }

    private void AddTextChangeListener(final EditText editText){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }
            //TODO fix search
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                List<Recipe> newList = new ArrayList<Recipe>();
                newList.clear();
                int textLength = editText.getText().length();
                for (int i = 0; i < recipeList.size(); i++){

                    if (textLength <= recipeList.get(i).getName().length()){

                        if (editText.getText().toString().equalsIgnoreCase(
                                (String)recipeList.get(i).getName().subSequence(0,textLength))){
                            newList.add(recipeList.get(i));
                        }
                    }
                }
                listView.setAdapter(new RecipeAdapter(MyRecipesActivity.this,R.layout.main_list_row_recipe,newList));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
