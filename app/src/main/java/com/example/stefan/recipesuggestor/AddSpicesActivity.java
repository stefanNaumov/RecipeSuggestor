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
import android.widget.Toast;

import java.util.List;

import Models.Recipe;
import Tasks.Converter;
import Tasks.KeyboardHider;
import Utils.SpicesContainer;


public class AddSpicesActivity extends Activity implements View.OnClickListener, View.OnTouchListener{

    Recipe recipe;
    Button goToRecipePreparingBtn, addSpiceBtn;
    Converter converter;
    KeyboardHider keyBrdHider;
    SpicesContainer spicesContainer;
    EditText addSpiceInput;
    ListView spicesListView;
    ArrayAdapter<String> adapter;
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

        addSpiceBtn = (Button)findViewById(R.id.addSpicesAddBtnId);
        addSpiceBtn.setOnClickListener(this);

        converter = new Converter();

        addSpiceInput = (EditText)findViewById(R.id.spicesInputId);

        RelativeLayout layout = (RelativeLayout)findViewById(R.id.addSpicesLayout);
        layout.setOnTouchListener(this);

        keyBrdHider = new KeyboardHider(this);

        spicesContainer = SpicesContainer.getInstance();

        spicesListView = (ListView)findViewById(R.id.addSpicesListViewId);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
                this.spicesContainer.getSpicesList());
        spicesListView.setAdapter(adapter);
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

            if (this.spicesContainer.getSpicesList() == null
                    || this.spicesContainer.getSpicesList().size() < 1){
                Toast.makeText(this,"You must enter at least one spice",Toast.LENGTH_LONG).show();
            }
            else{
                //convert spices list to String and set it to the current recipe
                String spicesAsStr = this.converter.convertListToString(
                        this.spicesContainer.getSpicesList());
                this.recipe.setSpices(spicesAsStr);

                Intent intent = new Intent(this,RecipePreparingActivity.class);
                intent.putExtra("Recipe",recipe);

                startActivity(intent);
            }
        }
        else if (this.addSpiceBtn.getId() == view.getId()){
            String spicesStr = addSpiceInput.getText().toString().trim().toLowerCase();

            if (spicesStr == null || spicesStr.isEmpty()) {
                Toast.makeText(this, "Spices input field cannot be empty", Toast.LENGTH_LONG).show();
            }
            else{
                this.spicesContainer.AddSpice(spicesStr);

                this.spicesListView.setAdapter(new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1,
                        this.spicesContainer.getSpicesList()));

                //clear spice input
                this.addSpiceInput.setText("");
            }
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        keyBrdHider.hideKeyobard(view);
        return false;
    }
}
