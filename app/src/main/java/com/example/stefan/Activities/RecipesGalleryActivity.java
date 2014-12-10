package com.example.stefan.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.example.stefan.recipesuggestor.R;

import Models.Recipe;

public class RecipesGalleryActivity extends Activity implements View.OnClickListener{

    private Button mNewImageBtn;
    private GridView mGalleryGridView;
    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_gallery);

        this.initViews();
        this.init();
    }

    private void init(){
        mNewImageBtn.setOnClickListener(this);
        mRecipe = (Recipe)getIntent().getSerializableExtra("Recipe");
    }

    private void initViews(){
        mNewImageBtn = (Button)findViewById(R.id.recipesGalleryNewImageBtnId);
        mGalleryGridView = (GridView)findViewById(R.id.recipesGalleryGridViewId);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipes_gallery, menu);
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
        if (mNewImageBtn.getId() == view.getId()){
            //navigate to CaptureImageActivity
            Intent intent = new Intent(this,CaptureImageActivity.class);
            intent.putExtra("Recipe",mRecipe);

            startActivity(intent);
        }
    }
}
