package com.example.stefan.recipesuggestor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import Models.Recipe;
import Persistors.SQLiteDBManager;
import Tasks.TypeWriterAnimator;
import Utils.IngredientsContainer;
import Utils.RecipeAdapter;
import Utils.SpicesContainer;


public class MainActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Button mAddRecipeBtn, mMyRecipesBtn, mSuggestRecipeBtn;
    private SQLiteDBManager mDbManager;
    private List<Recipe> mModelsList;
    private RecipeAdapter mAdapter;
    private TypeWriterAnimator mMyFavouritesTypeAnimator;

    //size of my favourite recipes list view
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
        if (view.getId() == this.mAddRecipeBtn.getId()){
            i = new Intent(this, AddRecipeActivity.class);

        }
        else if (view.getId() == this.mMyRecipesBtn.getId()){
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

        /*method used to clear both containers if the user goes back from adding a new recipe.
        * This allows these containers to be used in the SuggestRecipeActivity too
        * because the user will always navigate through the MainActivity
        * and the containers will be cleared*/
        this.clearContainers();

        this.fetchRecipes();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Intent intent = new Intent(this,RecipeDetailsActivity.class);
        intent.putExtra("Recipe", mModelsList.get(i));

        startActivity(intent);
    }

    private void init(){
        mAddRecipeBtn = (Button)findViewById(R.id.addRecipeBtn);
        mMyRecipesBtn = (Button)findViewById(R.id.myRecipesBtn);
        mSuggestRecipeBtn = (Button)findViewById(R.id.suggestRecipeBtn);

        mAddRecipeBtn.setOnClickListener(this);
        mMyRecipesBtn.setOnClickListener(this);
        mSuggestRecipeBtn.setOnClickListener(this);

        mDbManager = new SQLiteDBManager(getApplicationContext());

        mMyFavouritesTypeAnimator = (TypeWriterAnimator)findViewById(R.id.mainActivityMyFavouritesId);
        mMyFavouritesTypeAnimator.setDelay(250);
        mMyFavouritesTypeAnimator.animateText("My Favourites");

        //mDbManager.deleteAll();
    }

    private void fetchRecipes(){
        mModelsList = mDbManager.getSortedByTimesUsedWithRange(MOST_USED_LIST_SIZE);
        if (mModelsList != null) {
            ListView listView = (ListView) findViewById(R.id.list);

            mAdapter = new RecipeAdapter(this, R.layout.main_list_row_recipe, mModelsList);

            listView.setAdapter(mAdapter);
            listView.setOnItemClickListener(this);
        }
    }

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
