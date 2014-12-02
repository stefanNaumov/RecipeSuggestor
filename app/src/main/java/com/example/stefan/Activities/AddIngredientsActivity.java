package com.example.stefan.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.stefan.recipesuggestor.R;

import Models.Recipe;
import Tasks.Converter;
import Tasks.KeyboardHider;
import Utils.IngredientsContainer;


public class AddIngredientsActivity extends Activity implements View.OnClickListener, View.OnTouchListener,
        AdapterView.OnItemLongClickListener{

    private Recipe recipe;
    private EditText mAddIngredientInput;
    private Button mGoToAddSpicesBtn, mAddIngredientButton;
    private IngredientsContainer mIngredientsContainer;
    private Converter mConverter;
    private KeyboardHider mKeyBrdHider;
    private ListView mListView;
    private ArrayAdapter<String> mAdapter;
    private RelativeLayout mLayout;
    private Vibrator mVibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredients);

        this.init();
    }

    private void init(){
        mGoToAddSpicesBtn = (Button)findViewById(R.id.goToAddSpicesBtnId);
        mGoToAddSpicesBtn.setOnClickListener(this);

        mAddIngredientButton = (Button)findViewById(R.id.addIngredientsAddBtnId);
        mAddIngredientButton.setOnClickListener(this);

        recipe = (Recipe)getIntent().getSerializableExtra("Recipe");

        mIngredientsContainer = IngredientsContainer.getInstance();
        mConverter = new Converter();

        mLayout = (RelativeLayout) findViewById(R.id.addIngredientsLayout);
        mLayout.setOnTouchListener(this);
        mKeyBrdHider = new KeyboardHider(this);

        mAddIngredientInput = (EditText)findViewById(R.id.addIngredientsInputId);

        mListView = (ListView)findViewById(R.id.addIngredientslistViewId);
        mListView.setOnItemLongClickListener(this);

        mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
                mIngredientsContainer.getIngredientsList());
        mListView.setAdapter(mAdapter);

        mVibrator = (Vibrator)this.getSystemService(VIBRATOR_SERVICE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_ingredients, menu);
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

        if (mGoToAddSpicesBtn.getId() == view.getId()){
            if (this.mIngredientsContainer.getIngredientsList() == null
                    || this.mIngredientsContainer.getIngredientsList().size() < 1){
                Toast.makeText(this,"You must enter at least one ingredient",
                        Toast.LENGTH_LONG).show();
            }
            else{
                //convert ingredients list to String and set it to the current recipe
                String ingredientsAsStr = this.mConverter.convertListToString(
                        this.mIngredientsContainer.getIngredientsList());
                this.recipe.setIngredients(ingredientsAsStr);

                Intent intent = new Intent(this,AddSpicesActivity.class);
                intent.putExtra("Recipe",recipe);

                startActivity(intent);
            }
        }
        else if (mAddIngredientButton.getId() == view.getId()){
            String ingredient = mAddIngredientInput.getText().toString().trim().toLowerCase();
            if (ingredient == null || ingredient.isEmpty()) {
                Toast.makeText(this, "Ingredients input field cannot be empty"
                        , Toast.LENGTH_LONG).show();
            }
            else{
                this.mIngredientsContainer.AddIngredient(ingredient);
                mListView.setAdapter(new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, this.mIngredientsContainer.getIngredientsList()));

                //clear ingredient input
                mAddIngredientInput.setText("");
            }
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        mKeyBrdHider.hideKeyobard(view);
        return false;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        this.mIngredientsContainer.getIngredientsList().remove(i);
        this.mListView.setAdapter(new ArrayAdapter<String>(
                this,android.R.layout.simple_list_item_1,
                this.mIngredientsContainer.getIngredientsList()));
        mVibrator.vibrate(300);
        return false;
    }
}
