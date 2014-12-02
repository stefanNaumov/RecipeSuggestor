package com.example.stefan.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.stefan.recipesuggestor.R;

import Models.Recipe;
import Persistors.SQLiteDBManager;
import Tasks.TypeWriterAnimator;


public class HowToPrepareActivity extends Activity {

    private TypeWriterAnimator mHowToPrepare;
    private Recipe recipe;
    private SQLiteDBManager mDbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_prepare);

        this.init();
    }

    private void init(){
        mHowToPrepare = (TypeWriterAnimator)findViewById(R.id.howToPrepareTypeWriterAnimatorId);
        recipe = (Recipe)getIntent().getSerializableExtra("Recipe");
        mDbManager = new SQLiteDBManager(this);

        if (recipe != null){
            mHowToPrepare.animateText(recipe.getPreparing());
            /*If a user  clicks the mHowToPrepare button he will use the current mRecipe
            - increment the timesSeen value*/
            int timesSeen = recipe.getTimesUsed();
            timesSeen += 1;
            mDbManager.updateTimesSeen(recipe.getName(), timesSeen);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_how_to_prepare, menu);
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
}
