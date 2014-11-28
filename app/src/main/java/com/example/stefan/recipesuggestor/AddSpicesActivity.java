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
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

import Models.Recipe;
import Tasks.Converter;
import Tasks.KeyboardHider;


public class AddSpicesActivity extends Activity implements View.OnClickListener, View.OnTouchListener{

    Recipe recipe;
    Button goToRecipePreparingBtn;
    Converter converter;
    KeyboardHider keyBrdHider;
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

        RelativeLayout layout = (RelativeLayout)findViewById(R.id.addSpicesLayout);
        layout.setOnTouchListener(this);

        keyBrdHider = new KeyboardHider(this);
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
            String spicesStr = spicesEditText.getText().toString().trim();

            if (spicesStr != null && !spicesStr.isEmpty()){

                recipe.setSpices(spicesStr);

                Intent i = new Intent(this,RecipePreparingActivity.class);
                i.putExtra("Recipe",recipe);

                startActivity(i);
            }
            else{
                Toast.makeText(this,"Spices input field cannot be empty",Toast.LENGTH_LONG);
            }
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        keyBrdHider.hideKeyobard(view);
        return false;
    }
}
