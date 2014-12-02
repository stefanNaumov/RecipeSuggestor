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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stefan.recipesuggestor.R;

import Models.Recipe;
import Persistors.SQLiteDBManager;
import Tasks.KeyboardHider;


public class RecipePreparingActivity extends Activity implements View.OnClickListener, View.OnTouchListener{

    private static final String TITLE_MESSAGE = "How to prepare";
    private Recipe mRecipe;
    private Button mCreateRecipeBtn;
    private SQLiteDBManager mDbManager;
    private KeyboardHider mKeyBrdHider;
    private EditText mRecipePreparingInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_preparing);

        this.init();
    }

    private void init(){
        mDbManager = new SQLiteDBManager(getApplicationContext());

        mRecipe = (Recipe)getIntent().getSerializableExtra("Recipe");
        TextView titleTextView = (TextView)findViewById(R.id.recipePreparingTitleId);
        titleTextView.setText(TITLE_MESSAGE + " " + mRecipe.getName() + "?");
        mCreateRecipeBtn = (Button)findViewById(R.id.createRecipeBtnId);
        mCreateRecipeBtn.setOnClickListener(this);

        LinearLayout layout = (LinearLayout)findViewById(R.id.recipePreparingLayout);
        layout.setOnTouchListener(this);

        mKeyBrdHider = new KeyboardHider(this);
        mRecipePreparingInput = (EditText)findViewById(R.id.recipePreparingInputId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_preparing, menu);
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
        if (this.mCreateRecipeBtn.getId() == view.getId()){

            String recipePreparingStr = mRecipePreparingInput.getText().toString();
            if (recipePreparingStr != null && !recipePreparingStr.isEmpty()){
                mRecipe.setPreparing(recipePreparingStr);

                mDbManager.addRecord(mRecipe.getName(), mRecipe.getIngredients(),
                        mRecipe.getSpices(), mRecipe.getPreparing());
                Toast.makeText(this,"Added to database",Toast.LENGTH_LONG).show();

                //clear input
                this.mRecipePreparingInput.setText("");

                //navigate to main window
                Intent intent = new Intent(this,MainActivity.class);

            }
            else{
                Toast.makeText(this,"Recipe preparing input field cannot be empty!"
                        ,Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        mKeyBrdHider.hideKeyobard(view);
        return false;
    }
}
