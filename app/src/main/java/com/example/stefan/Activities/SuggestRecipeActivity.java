package com.example.stefan.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
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

import com.example.stefan.recipesuggestor.R;

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
        AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener{

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
    private Vibrator mVibrator;
    private LinearLayout mLayout;
    private LinearLayout mSubLayout;
    private List<Recipe> mSuggestedRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest_recipe);

        this.initViews();
        this.init();
    }

    private void init(){

        mAvailableIngredientsTypeAnimator.animateText("What do I have available..");

        mKeyBoardHider = new KeyboardHider(this);

        mConverter = new Converter();

        mLayout.setOnTouchListener(this);
         /* the two list views for available ingredients and suggested recipes are in a sub-mLayout -
        hide keyboard when user touches it too*/
        mSubLayout.setOnTouchListener(this);
        mAddAvailableIngredientBtn.setOnClickListener(this);
        mAvailableIngredientsListView.setOnItemLongClickListener(this);
        mSuggestedRecipesListView.setOnItemClickListener(this);

        mIngredientsContainer = IngredientsContainer.getInstance();

        mAvailableIngredientsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,mIngredientsContainer.getIngredientsList());

        mAvailableIngredientsListView.setAdapter(mAvailableIngredientsAdapter);

        mDbManager = new SQLiteDBManager(this);
        mAllRecipeList = mDbManager.getSortedByName();

        mVibrator = (Vibrator)this.getSystemService(VIBRATOR_SERVICE);
    }

    private void initViews(){
        mAvailableIngredientsTypeAnimator =
                (TypeWriterAnimator)findViewById(R.id.suggestRecipeTitleTypeWriterAnimatorId);

        mLayout = (LinearLayout)findViewById(R.id.suggestRecipeLayoutId);

        mSubLayout = (LinearLayout)findViewById(R.id.suggestRecipeSubLayoutId);

        mAddAvailableIngredientBtn = (Button)
                findViewById(R.id.suggestRecipeAddAvailableIngredientBtnId);

        mAvailableIngredientInput = (EditText)
                findViewById(R.id.suggestRecipeIngredientAvailableInputId);

        mAvailableIngredientsListView = (ListView)
                findViewById(R.id.suggestRecipeAvailableIngredientsListViewId);

        mSuggestedRecipesListView = (ListView)
                findViewById(R.id.suggestRecipeSuggestedRecipesListViewId);
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

    //make sure that if the user goes back the pencil typing sound will be stopped
    @Override
    protected void onPause() {
        super.onPause();
        if (mAvailableIngredientsTypeAnimator != null) {
            mAvailableIngredientsTypeAnimator.stop();
        }
    }

    private void checkForSuggestedRecipes(){
        mSuggestedRecipes = new ArrayList<Recipe>();
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
                    mSuggestedRecipes.add(currRecipe);
                    break;
                }
            }
        }

        //set suggested recipes to the suggested recipes list view
        mRecipeAdapter = new RecipeAdapter(this,R.layout.main_list_row_recipe, mSuggestedRecipes);
        mSuggestedRecipesListView.setAdapter(mRecipeAdapter);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

        this.mIngredientsContainer.getIngredientsList().remove(i);
        this.mAvailableIngredientsListView.setAdapter(
                new ArrayAdapter<String>(
                        this,android.R.layout.simple_list_item_1,
                        this.mIngredientsContainer.getIngredientsList()));
        //notify user
        mVibrator.vibrate(300);

        //ingredients list has changed - check for suggested recipes
        this.checkForSuggestedRecipes();

        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this,RecipeDetailsActivity.class);
        intent.putExtra("Recipe",mSuggestedRecipes.get(i));

        startActivity(intent);
    }
}
