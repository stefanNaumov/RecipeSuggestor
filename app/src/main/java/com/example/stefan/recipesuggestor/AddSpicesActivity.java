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

import Models.Recipe;
import Tasks.Converter;
import Tasks.KeyboardHider;
import Utils.SpicesContainer;


public class AddSpicesActivity extends Activity implements View.OnClickListener, View.OnTouchListener{

   private Recipe mRecipe;
   private Button mGoToRecipePreparingBtn, mAddSpiceBtn;
   private Converter mConverter;
   private KeyboardHider mKeyBrdHider;
   private SpicesContainer mSpicesContainer;
   private EditText mAddSpiceInput;
   private ListView mSpicesListView;
   private ArrayAdapter<String> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_spices);

        this.init();
    }

    private void init(){
        mRecipe = (Recipe)getIntent().getSerializableExtra("Recipe");
        mGoToRecipePreparingBtn = (Button)findViewById(R.id.goToRecipePreparingBtnId);
        mGoToRecipePreparingBtn.setOnClickListener(this);

        mAddSpiceBtn = (Button)findViewById(R.id.addSpicesAddBtnId);
        mAddSpiceBtn.setOnClickListener(this);

        mConverter = new Converter();

        mAddSpiceInput = (EditText)findViewById(R.id.spicesInputId);

        RelativeLayout layout = (RelativeLayout)findViewById(R.id.addSpicesLayout);
        layout.setOnTouchListener(this);

        mKeyBrdHider = new KeyboardHider(this);

        mSpicesContainer = SpicesContainer.getInstance();

        mSpicesListView = (ListView)findViewById(R.id.addSpicesListViewId);
        mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
                this.mSpicesContainer.getSpicesList());
        mSpicesListView.setAdapter(mAdapter);
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
        if (this.mGoToRecipePreparingBtn.getId() == view.getId()){

            if (this.mSpicesContainer.getSpicesList() == null
                    || this.mSpicesContainer.getSpicesList().size() < 1){
                Toast.makeText(this,"You must enter at least one spice",Toast.LENGTH_LONG).show();
            }
            else{
                //convert spices list to String and set it to the current mRecipe
                String spicesAsStr = this.mConverter.convertListToString(
                        this.mSpicesContainer.getSpicesList());
                this.mRecipe.setSpices(spicesAsStr);

                Intent intent = new Intent(this,RecipePreparingActivity.class);
                intent.putExtra("Recipe", mRecipe);

                startActivity(intent);
            }
        }
        else if (this.mAddSpiceBtn.getId() == view.getId()){
            String spicesStr = mAddSpiceInput.getText().toString().trim().toLowerCase();

            if (spicesStr == null || spicesStr.isEmpty()) {
                Toast.makeText(this, "Spices input field cannot be empty", Toast.LENGTH_LONG).show();
            }
            else{
                this.mSpicesContainer.AddSpice(spicesStr);

                this.mSpicesListView.setAdapter(new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1,
                        this.mSpicesContainer.getSpicesList()));

                //clear spice input
                this.mAddSpiceInput.setText("");
            }
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        mKeyBrdHider.hideKeyobard(view);
        return false;
    }
}
