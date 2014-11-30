package com.example.stefan.recipesuggestor;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Models.Recipe;
import Models.SQLiteRecipeModel;
import Persistors.SQLiteDBHelper;
import Persistors.SQLiteDBManager;
import Utils.IngredientsContainer;
import Utils.RecipeAdapter;
import Utils.SpicesContainer;


public class MainActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Button addRecipeBtn, myRecipesBtn, suggestRecipeBtn;
    private SQLiteDBManager dbManager;
    private List<Recipe> modelsList;
    private RecipeAdapter adapter;
    private static final int MOST_USED_LIST_SIZE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onClick(View view) {

        Intent i;
        if (view.getId() == this.addRecipeBtn.getId()){
            i = new Intent(this, AddRecipeActivity.class);

        }
        else if (view.getId() == this.myRecipesBtn.getId()){
            i = new Intent(this, MyRecipesActivity.class);

        }
        else {
            i = new Intent(this, SuggestRecipeActivity.class);
        }

        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.clearContainers();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Intent intent = new Intent(this,RecipeDetailsActivity.class);
        intent.putExtra("Recipe",modelsList.get(i));

        startActivity(intent);
    }

    private void init(){
        addRecipeBtn = (Button)findViewById(R.id.addRecipeBtn);
        myRecipesBtn = (Button)findViewById(R.id.myRecipesBtn);
        suggestRecipeBtn = (Button)findViewById(R.id.suggestRecipeBtn);

        addRecipeBtn.setOnClickListener(this);
        myRecipesBtn.setOnClickListener(this);
        suggestRecipeBtn.setOnClickListener(this);

        dbManager = new SQLiteDBManager(getApplicationContext());

        modelsList = dbManager.getSortedByTimesUsedWithRange(MOST_USED_LIST_SIZE);
        ListView listView = (ListView)findViewById(R.id.list);

        adapter = new RecipeAdapter(this,R.layout.main_list_row_recipe,modelsList);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

/*method used to clear both containers if the user goes back from adding a new recipe*/
    private void clearContainers(){
        IngredientsContainer ingredientsContainer = IngredientsContainer.getInstance();
        SpicesContainer spicesContainer = SpicesContainer.getInstance();

        if (ingredientsContainer.getIngredientsList() != null){
            ingredientsContainer.clearList();
        }

        if (spicesContainer.getSpicesList() != null){
            spicesContainer.clearList();
        }
    }
}
