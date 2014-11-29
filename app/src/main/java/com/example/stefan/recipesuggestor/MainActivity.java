package com.example.stefan.recipesuggestor;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Models.SQLiteRecipeModel;
import Persistors.SQLiteDBHelper;
import Persistors.SQLiteDBManager;
import Utils.RecipeAdapter;


public class MainActivity extends Activity implements View.OnClickListener {

    private Button addRecipeBtn, myRecipesBtn, suggestRecipeBtn;
    private SQLiteDBManager dbManager;
    private List<SQLiteRecipeModel> modelsList;
    private RecipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.init();
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

    private void init(){
        addRecipeBtn = (Button)findViewById(R.id.addRecipeBtn);
        myRecipesBtn = (Button)findViewById(R.id.myRecipesBtn);
        suggestRecipeBtn = (Button)findViewById(R.id.suggestRecipeBtn);

        addRecipeBtn.setOnClickListener(this);
        myRecipesBtn.setOnClickListener(this);
        suggestRecipeBtn.setOnClickListener(this);

        dbManager = new SQLiteDBManager(getApplicationContext());

        modelsList = dbManager.getAll();

        ListView listView = (ListView)findViewById(R.id.list);
        List<String> values = new ArrayList<String>();
        for (int i = 0;i < modelsList.size(); i++){
            values.add(modelsList.get(i).getName());
        }
        adapter = new RecipeAdapter(this,R.layout.main_list_row_recipe,modelsList);

        listView.setAdapter(adapter);
    }
}
