package com.example.stefan.recipesuggestor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Models.Recipe;
import Tasks.Converter;
import Tasks.KeyboardHider;
import Utils.IngredientsContainer;


public class AddIngredientsActivity extends Activity implements View.OnClickListener, View.OnTouchListener{

    Recipe recipe;
    EditText addIngredientInput;
    Button goToAddSpicesBtn, addIngredientButton;
    IngredientsContainer ingredientsContainer;
    Converter converter;
    KeyboardHider keyBrdHider;
    ListView listView;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredients);

        this.init();
    }

    private void init(){
        goToAddSpicesBtn = (Button)findViewById(R.id.goToAddSpicesBtnId);
        goToAddSpicesBtn.setOnClickListener(this);

        addIngredientButton = (Button)findViewById(R.id.addIngredientsAddBtnId);
        addIngredientButton.setOnClickListener(this);

        recipe = (Recipe)getIntent().getSerializableExtra("Recipe");

        ingredientsContainer = IngredientsContainer.getInstance();
        converter = new Converter();

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.addIngredientsLayout);
        layout.setOnTouchListener(this);
        keyBrdHider = new KeyboardHider(this);

        addIngredientInput = (EditText)findViewById(R.id.addIngredientsInputId);

        listView = (ListView)findViewById(R.id.addIngredientslistViewId);

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ingredientsContainer.getIngredientsList());
        listView.setAdapter(adapter);
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
            if (this.ingredientsContainer.getIngredientsList() == null
                    || this.ingredientsContainer.getIngredientsList().size() < 1){
                Toast.makeText(this,"You must enter at least one ingredient",
                        Toast.LENGTH_LONG).show();
            }
            else{
                Intent intent = new Intent(this,AddSpicesActivity.class);
                intent.putExtra("Recipe",recipe);

                startActivity(intent);
            }
        }
        else if (addIngredientButton.getId() == view.getId()){
            String ingredient = addIngredientInput.getText().toString().trim().toLowerCase();
            if (ingredient == null || ingredient.isEmpty()) {
                Toast.makeText(this, "Ingredients input field cannot be empty"
                        , Toast.LENGTH_LONG).show();
            }
            else{
                this.ingredientsContainer.AddIngredient(ingredient);
                listView.setAdapter(new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1,this.ingredientsContainer.getIngredientsList()));

                //clear ingredient input
                addIngredientInput.setText("");
            }
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        keyBrdHider.hideKeyobard(view);
        return false;
    }
}
