package com.example.stefan.recipesuggestor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity implements View.OnClickListener {

    private Button addRecipeBtn, myRecipesBtn, suggestRecipeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addRecipeBtn = (Button)findViewById(R.id.addRecipeBtn);
        myRecipesBtn = (Button)findViewById(R.id.myRecipesBtn);
        suggestRecipeBtn = (Button)findViewById(R.id.suggestRecipeBtn);

        addRecipeBtn.setOnClickListener(this);
        myRecipesBtn.setOnClickListener(this);
        suggestRecipeBtn.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        Intent i;
        if (view.getId() == this.addRecipeBtn.getId()){
            i = new Intent(this, AddRecipeActivity.class);

        }
        else if (view.getId() == this.myRecipesBtn.getId()){
            i = new Intent(this, MyRecipesActivity.class);
        }
        else {
            i = new Intent(this, SuggestRecipeActivity.class);
        }

        startActivity(i);
    }
}
