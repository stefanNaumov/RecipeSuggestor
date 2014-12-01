package com.example.stefan.recipesuggestor;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import Tasks.KeyboardHider;
import Tasks.TypeWriterAnimator;


public class SuggestRecipeActivity extends Activity implements View.OnTouchListener{

    private TypeWriterAnimator mAvailableIngredientsTypeAnimator;
    private KeyboardHider mKeyBoardHider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest_recipe);

        this.init();
    }

    private void init(){
        this.mAvailableIngredientsTypeAnimator =
                (TypeWriterAnimator)findViewById(R.id.suggestRecipeTitleTypeWriterAnimatorId);
        this.mAvailableIngredientsTypeAnimator.animateText("What do I have available..");

        this.mKeyBoardHider = new KeyboardHider(this);

        LinearLayout layout = (LinearLayout)findViewById(R.id.suggestRecipeLayoutId);
       /* the two list views for available ingredients and suggested recipes are in a sub-layout -
        hide keyboard when user touches it too*/
        LinearLayout subLayout = (LinearLayout)findViewById(R.id.suggestRecipeSubLayoutId);
        layout.setOnTouchListener(this);
        subLayout.setOnTouchListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_suggest_recipe, menu);
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
    public boolean onTouch(View view, MotionEvent motionEvent) {
        mKeyBoardHider.hideKeyobard(view);
        return false;
    }
}
