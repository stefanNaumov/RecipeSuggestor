package com.example.stefan.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.stefan.recipesuggestor.R;

import Models.Recipe;
import Tasks.KeyboardHider;


public class AddRecipeActivity extends Activity implements View.OnClickListener, View.OnTouchListener{

    private Button mGotoAddIngredientsBtn;
    private Recipe mNewRecipe;
    private EditText mNameEditText;
    private KeyboardHider mKeyBrdHider;
    private RelativeLayout mLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        this.initViews();
        this.init();
    }

    private void init(){
        mKeyBrdHider = new KeyboardHider(this);

        mGotoAddIngredientsBtn.setOnClickListener(this);
        mLayout.setOnTouchListener(this);
    }

    private void initViews(){
        mGotoAddIngredientsBtn = (Button)findViewById(R.id.goToAddIngredientsBtnId);

        mNameEditText = (EditText)findViewById(R.id.recipeNameId);

        mLayout = (RelativeLayout) findViewById(R.id.addRecipeLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_recipe, menu);
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
        if (mGotoAddIngredientsBtn.getId() == view.getId()){
            //get recipe name on onclick

            String recipeName = mNameEditText.getText().toString();

            if (recipeName != null && !recipeName.isEmpty()){
                //create new Recipe with the entered name and pass it to AddIngredients activity
                mNewRecipe = new Recipe(recipeName);
                Intent i = new Intent(this, AddIngredientsActivity.class);
                i.putExtra("Recipe", mNewRecipe);
                startActivity(i);
            }
            else {
                Toast.makeText(this, "Please enter recipe name", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        mKeyBrdHider.hideKeyobard(view);
        return false;
    }
}
