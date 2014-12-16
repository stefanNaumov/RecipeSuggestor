package com.example.stefan.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.stefan.recipesuggestor.R;

import java.util.ArrayList;
import java.util.List;

import Models.Recipe;
import Persistors.SQLiteDBManager;
import Tasks.KeyboardHider;
import Utils.RecipeAdapter;


public class MyRecipesActivity extends Activity implements AdapterView.OnItemClickListener,View.OnTouchListener{

    private ListView mListView;
    private RecipeAdapter mAdapter;
    private SQLiteDBManager mDbManager;
    private List<Recipe> mAllRecipeList;
    //default recipe list used as a container in which lists are swapped
    private List<Recipe> mDefaultRecipeList;
    private EditText mSearchText;
    private KeyboardHider mKeyboardHider;
    private LinearLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recipes);

        this.initViews();
        this.init();
    }

    private void init(){
        mKeyboardHider = new KeyboardHider(this);

        mDbManager = new SQLiteDBManager(this);

        mAllRecipeList = mDbManager.getSortedByName();
        //set all recipes in the container
        if (mAllRecipeList != null){
            mDefaultRecipeList = mAllRecipeList;

            mAdapter = new RecipeAdapter(this,R.layout.main_list_row_recipe, mDefaultRecipeList);
            mListView.setAdapter(mAdapter);
        }

        mListView.setOnItemClickListener(this);
        mLayout.setOnTouchListener(this);
        //clear focus in order not to show keyboard when starting the activity
        mSearchText.setSelected(false);

        this.AddTextChangeListener(mSearchText);
    }

    private void initViews(){
        mListView = (ListView)findViewById(R.id.myRecipesListViewId);

        mSearchText = (EditText)findViewById(R.id.myRecipesSearchViewId);

        mLayout = (LinearLayout)findViewById(R.id.myRecipesLayoutId);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_recipes, menu);
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

    private void AddTextChangeListener(final EditText editText){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                List<Recipe> newList = new ArrayList<Recipe>();
                newList.clear();
                int textLength = editText.getText().length();
                for (int i = 0; i < mAllRecipeList.size(); i++){

                    if (textLength <= mAllRecipeList.get(i).getName().length()){

                        if (editText.getText().toString().equalsIgnoreCase(
                                (String) mAllRecipeList.get(i).getName().subSequence(0,textLength))){
                            newList.add(mAllRecipeList.get(i));
                        }
                    }
                }
                /*set the new recipe list in the container
                 in order to get the correct recipe index in onItemClick*/
                mDefaultRecipeList = newList;
                mListView.setAdapter(new RecipeAdapter(MyRecipesActivity.this,
                        R.layout.main_list_row_recipe, mDefaultRecipeList));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        mKeyboardHider.hideKeyobard(view);
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this,RecipeDetailsActivity.class);
        intent.putExtra("Recipe", this.mDefaultRecipeList.get(i));

        startActivity(intent);
    }
}
