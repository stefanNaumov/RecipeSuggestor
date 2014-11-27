package com.example.stefan.recipesuggestor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Models.Recipe;
import Tasks.Converter;


public class AddIngredientsActivity extends Activity implements View.OnClickListener{

    Recipe recipe;
    Button goToAddSpicesBtn;
    List<String> ingredients;
    Converter converter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredients);

        this.init();
    }

    private void init(){
        goToAddSpicesBtn = (Button)findViewById(R.id.goToAddSpicesBtnId);
        goToAddSpicesBtn.setOnClickListener(this);

        recipe = (Recipe)getIntent().getSerializableExtra("Recipe");
        TextView recipeNameTextView = (TextView)findViewById(R.id.recipeNameId);
        recipeNameTextView.setText(recipe.getName());

        ingredients = new ArrayList<String>();
        converter = new Converter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_ingredients, menu);
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

        if (goToAddSpicesBtn.getId() == view.getId()){
            //get ingredients as string and convert it to List by splitting it
            EditText ingredientsEditText = (EditText)findViewById(R.id.ingredientsInputId);
            String ingredientsStr = ingredientsEditText.getText().toString();
            if (ingredientsStr != null && !ingredientsStr.isEmpty()){
                ingredients = this.converter.convertStringToList(ingredientsStr);
                recipe.setIngredients(ingredients);

                Intent i = new Intent(this,AddSpicesActivity.class);
                i.putExtra("Recipe",recipe);
                startActivity(i);
            }
            else{
                Toast.makeText(this,"Ingredients input field cannot be empty"
                        ,Toast.LENGTH_LONG).show();
            }
        }
    }

}
