package com.example.stefan.recipesuggestor;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import Models.Recipe;
import Persistors.SQLiteDBManager;


public class HowToPrepareActivity extends Activity {

    TextView howToPrepare;
    Recipe recipe;
    SQLiteDBManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_prepare);

        this.init();
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

    private void init(){
        howToPrepare = (TextView)findViewById(R.id.howToPrepareTextViewId);
        recipe = (Recipe)getIntent().getSerializableExtra("Recipe");
        dbManager = new SQLiteDBManager(this);

        if (recipe != null){
            howToPrepare.setText(recipe.getPreparing());
            /*If a user opens clicks the HowToPrepare button he will use the current recipe
            - incremetn the timesSeen value*/
            int timesSeen = recipe.getTimesUsed();
            timesSeen += 1;
            dbManager.updateTimesSeen(recipe.getName(),timesSeen);
        }
    }
}
