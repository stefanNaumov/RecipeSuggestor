package com.example.stefan.recipesuggestor;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Models.Recipe;
import Persistors.SQLiteDBManager;
import Tasks.Converter;
import Tasks.KeyboardHider;
import Tasks.TypeWriterAnimator;
import Utils.IngredientsContainer;
import Utils.RecipeAdapter;


public class SuggestRecipeActivity extends Activity implements View.OnClickListener, View.OnTouchListener,
        AdapterView.OnItemLongClickListener{

    private TypeWriterAnimator mAvailableIngredientsTypeAnimator;
    private KeyboardHider mKeyBoardHider;
    private Converter mConverter;
    private ListView mAvailableIngredientsListView;
    private ListView mSuggestedRecipesListView;
    private Button mAddAvailableIngredientBtn;
    private IngredientsContainer mIngredientsContainer;
    private EditText mAvailableIngredientInput;
    private SQLiteDBManager mDbManager;
    private ArrayAdapter<String> mAvailableIngredientsAdapter;
    private List<Recipe> mAllRecipeList;
    private RecipeAdapter mRecipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest_recipe);

        this.init();
    }

    private void init(){
        mAvailableIngredientsTypeAnimator =
                (TypeWriterAnimator)findViewById(R.id.suggestRecipeTitleTypeWriterAnimatorId);
        mAvailableIngredientsTypeAnimator.animateText("What do I have available..");

        mKeyBoardHider = new KeyboardHider(this);

        mConverter = new Converter();

        LinearLayout layout = (LinearLayout)findViewById(R.id.suggestRecipeLayoutId);
       /* the two list views for available ingredients and suggested recipes are in a sub-layout -
        hide keyboard when user touches it too*/
        LinearLayout subLayout = (LinearLayout)findViewById(R.id.suggestRecipeSubLayoutId);
        layout.setOnTouchListener(this);
        subLayout.setOnTouchListener(this);

        mAddAvailableIngredientBtn = (Button)
                findViewById(R.id.suggestRecipeAddAvailableIngredientBtnId);
        mAddAvailableIngredientBtn.setOnClickListener(this);

        mAvailableIngredientInput = (EditText)
                findViewById(R.id.suggestRecipeIngredientAvailableInputId);

        //classes need for the ingredients list view
        mAvailableIngredientsListView = (ListView)
                findViewById(R.id.suggestRecipeAvailableIngredientsListViewId);
        mAvailableIngredientsListView.setLongClickable(true);
        mAvailableIngredientsListView.setOnItemLongClickListener(this);

        mIngredientsContainer = IngredientsContainer.getInstance();

        mAvailableIngredientsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,mIngredientsContainer.getIngredientsList());

        mAvailableIngredientsListView.setAdapter(mAvailableIngredientsAdapter);

        //classes need for the suggested recipes list view
        mSuggestedRecipesListView = (ListView)
                findViewById(R.id.suggestRecipeSuggestedRecipesListViewId);

        mDbManager = new SQLiteDBManager(this);
        mAllRecipeList = mDbManager.getSortedByName();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_suggest_recipe, menu);
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
    public boolean onTouch(View view, MotionEvent motionEvent) {
        mKeyBoardHider.hideKeyobard(view);
        return false;
    }

    @Override
    public void onClick(View view) {
        if (this.mAddAvailableIngredientBtn.getId() == view.getId()){
            String availableIngredient = this.mAvailableIngredientInput.getText()
                    .toString().trim().toLowerCase();

            if (availableIngredient == null || availableIngredient.isEmpty()){
                Toast.makeText(this,"Cannot add an empty ingredient",Toast.LENGTH_LONG).show();
            }
            else{
                this.mIngredientsContainer.AddIngredient(availableIngredient);
                this.mAvailableIngredientsListView.setAdapter(
                        new ArrayAdapter<String>(
                                this,android.R.layout.simple_list_item_1,
                                        this.mIngredientsContainer.getIngredientsList()));

                //clear input
                this.mAvailableIngredientInput.setText("");

                //a new available ingredient is added - check for suggested recipe
                this.checkForSuggestedRecipes();
            }
        }
    }

    private void checkForSuggestedRecipes(){
        List<Recipe> suggestedRecipes = new ArrayList<Recipe>();
        List<String> currentIngredients = mIngredientsContainer.getIngredientsList();

        for (int i = 0; i < this.mAllRecipeList.size(); i++){
            Recipe currRecipe = mAllRecipeList.get(i);
           List<String> ingredientsList = mConverter.convertStringToList(
                   currRecipe.getIngredients());

            /*counter used to check what part of the ingredients needed for each recipe are available
            * if the ingredients available are more than half the count of the ingredients needed
            * the recipe is added to the suggested recipes list*/
            int counter = 0;
            for (int j = 0; j < ingredientsList.size(); j++){

                for (int k = 0; k < currentIngredients.size(); k++){

                    if (currentIngredients.get(k).equals(ingredientsList.get(j))){
                        counter++;
                    }
                }
                if (counter > (ingredientsList.size() / 2)){
                    suggestedRecipes.add(currRecipe);
                    break;
                }
            }
        }

        //set suggested recipes to the suggested recipes list view
        mRecipeAdapter = new RecipeAdapter(this,R.layout.main_list_row_recipe,suggestedRecipes);
        mSuggestedRecipesListView.setAdapter(mRecipeAdapter);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

        this.mIngredientsContainer.getIngredientsList().remove(i);
        this.mAvailableIngredientsListView.setAdapter(
                new ArrayAdapter<String>(
                        this,android.R.layout.simple_list_item_1,
                        this.mIngredientsContainer.getIngredientsList()));

        //ingredients list has changed - check for suggested recipes
        this.checkForSuggestedRecipes();

        return false;
    }
}
