package com.example.stefan.recipesuggestor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Models.Recipe;


public class AddRecipeActivity extends Activity implements View.OnClickListener{

    Button gotoAddIngredientsBtn;
    Recipe newRecipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        gotoAddIngredientsBtn = (Button)findViewById(R.id.goToAddIngredientsBtnId);
        gotoAddIngredientsBtn.setOnClickListener(this);
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
        if (gotoAddIngredientsBtn.getId() == view.getId()){
            //get recipe name on onclick
            EditText nameEditText = (EditText)findViewById(R.id.recipeNameId);
            String recipeName = nameEditText.getText().toString();

            if (recipeName != null && !recipeName.isEmpty()){
                //create new Recipe with the entered name and pass it to AddIngredients activity
                newRecipe = new Recipe(recipeName);
                Intent i = new Intent(this, AddIngredientsActivity.class);
                i.putExtra("Recipe",newRecipe);
                startActivity(i);
            }
            else {
                Toast.makeText(this, "Please enter recipe name", Toast.LENGTH_LONG).show();
            }
        }
    }
}
