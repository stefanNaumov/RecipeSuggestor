package com.example.stefan.recipesuggestor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import Models.Recipe;


public class RecipeDetailsActivity extends Activity implements View.OnClickListener{

    private Recipe mRecipe;
    private TextView mNameTextView, mIngredientsTextView, mSpicesTextView;
    private Button mPreparingBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        this.init();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_details, menu);
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
        if (mPreparingBtn.getId() == view.getId()){
            Intent intent = new Intent(this,HowToPrepareActivity.class);
            intent.putExtra("Recipe", mRecipe);

            startActivity(intent);
        }
    }

    private void init(){
        mRecipe = (Recipe)getIntent().getSerializableExtra("Recipe");

        mNameTextView = (TextView)findViewById(R.id.recipeDetailsNameId);
        mIngredientsTextView = (TextView)findViewById(R.id.recipeDetailsIngredientsId);
        mSpicesTextView = (TextView)findViewById(R.id.recipeDetailsSpicesId);
        mPreparingBtn = (Button)findViewById(R.id.recipeDetailsPreparingBtnId);
        mPreparingBtn.setOnClickListener(this);

        this.setContent(mRecipe);

        Log.d("TIMES SEEN", String.valueOf(mRecipe.getTimesUsed()));
    }

    private void setContent(Recipe recipe){
        if (recipe != null){
            mNameTextView.setText(recipe.getName());
            mIngredientsTextView.setText(recipe.getIngredients());
            mSpicesTextView.setText(recipe.getSpices());
        }
    }

}
