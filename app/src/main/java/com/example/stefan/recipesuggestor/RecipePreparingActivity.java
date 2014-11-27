package com.example.stefan.recipesuggestor;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Models.Recipe;
import Persistors.SQLiteDBManager;


public class RecipePreparingActivity extends Activity implements View.OnClickListener{

    private static final String TITLE_MESSAGE = "How to prepare";
    Recipe recipe;
    Button createRecipeBtn;
    SQLiteDBManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_preparing);

        this.init();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_preparing, menu);
        return true;
    }

    private void init(){
        dbManager = new SQLiteDBManager(getApplicationContext());

        recipe = (Recipe)getIntent().getSerializableExtra("Recipe");
        TextView titleTextView = (TextView)findViewById(R.id.recipePreparingTitleId);
        titleTextView.setText(TITLE_MESSAGE + " " + recipe.getName() + "?");
        createRecipeBtn = (Button)findViewById(R.id.createRecipeBtnId);
        createRecipeBtn.setOnClickListener(this);
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
        if (this.createRecipeBtn.getId() == view.getId()){
            EditText recipePreparing = (EditText)findViewById(R.id.recipePreparingInputId);
            String recipePreparingStr = recipePreparing.getText().toString();
            if (recipePreparingStr != null && !recipePreparingStr.isEmpty()){
                recipe.setPreparing(recipePreparingStr);

                dbManager.addRecord(recipe.getName(),recipe.getIngredients(),
                        recipe.getSpices(),recipe.getPreparing());
                Toast.makeText(this,"Added to database",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this,"Recipe preparing input field cannot be empty!"
                        ,Toast.LENGTH_LONG).show();
            }

            /*TODO Create SQLite database and add recipe to it*/
        }
    }
}
