package com.example.stefan.recipesuggestor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Models.Recipe;
import Tasks.Converter;
import Tasks.KeyboardHider;


public class AddIngredientsActivity extends Activity implements View.OnClickListener, View.OnTouchListener{

    Recipe recipe;
    Button goToAddSpicesBtn;
    List<String> ingredients;
    Converter converter;
    KeyboardHider keyBrdHider;

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
        TextView recipeNameTextView = (TextView)findViewById(R.id.recipeTitleNameId);
        recipeNameTextView.setText(recipe.getName());

        ingredients = new ArrayList<String>();
        converter = new Converter();

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.addIngredientsLayout);
        layout.setOnTouchListener(this);
        keyBrdHider = new KeyboardHider(this);
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
            String ingredientsStr = ingredientsEditText.getText().toString().trim().toLowerCase();
            if (ingredientsStr != null && !ingredientsStr.isEmpty()){

                recipe.setIngredients(ingredientsStr);

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

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        keyBrdHider.hideKeyobard(view);
        return false;
    }
}
