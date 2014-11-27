package com.example.stefan.recipesuggestor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import Models.Recipe;
import Tasks.Converter;


public class AddSpicesActivity extends Activity implements View.OnClickListener{

    Recipe recipe;
    Button goToRecipePreparingBtn;
    Converter converter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_spices);

        this.init();
    }

    private void init(){
        recipe = (Recipe)getIntent().getSerializableExtra("Recipe");
        goToRecipePreparingBtn = (Button)findViewById(R.id.goToRecipePreparingBtnId);
        goToRecipePreparingBtn.setOnClickListener(this);

        converter = new Converter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_spices, menu);
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
        if (this.goToRecipePreparingBtn.getId() == view.getId()){
            EditText spicesEditText = (EditText)findViewById(R.id.spicesInputId);
            String spicesStr = spicesEditText.getText().toString();

            if (spicesStr != null && !spicesStr.isEmpty()){
                List<String> spicesList = converter.convertStringToList(spicesStr);
                recipe.setSpices(spicesList);

                Intent i = new Intent(this,RecipePreparingActivity.class);
                i.putExtra("Recipe",recipe);

                startActivity(i);
            }
            else{
                Toast.makeText(this,"Spices input field cannot be empty",Toast.LENGTH_LONG);
            }
        }
    }
}
