package com.example.stefan.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.stefan.recipesuggestor.R;

import java.util.ArrayList;
import java.util.List;

import Models.Recipe;
import Tasks.TypeWriterAnimator;


public class RecipeDetailsActivity extends Activity implements View.OnClickListener{

    private Recipe mRecipe;
    private TextView mNameTextView;
    private TypeWriterAnimator mIngredientsAnimator, mSpicesAnimator;
    private Button mPreparingBtn;
    private ApplicationBase mAppBase;
    private SetTextAnimationTask task;
    //list of animators used in the extended async task
    private List<TypeWriterAnimator> animatorsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        this.init();
    }

    private void init(){
        mRecipe = (Recipe)getIntent().getSerializableExtra("Recipe");

        mNameTextView = (TextView)findViewById(R.id.recipeDetailsNameId);
        mIngredientsAnimator = (TypeWriterAnimator)findViewById(R.id.recipeDetailsIngredientsId);
        mSpicesAnimator = (TypeWriterAnimator)findViewById(R.id.recipeDetailsSpicesId);
        mPreparingBtn = (Button)findViewById(R.id.recipeDetailsPreparingBtnId);
        mPreparingBtn.setOnClickListener(this);
        animatorsList = new ArrayList<TypeWriterAnimator>();
        animatorsList.add(mIngredientsAnimator);
        animatorsList.add(mSpicesAnimator);

        mAppBase = new ApplicationBase();

        task = new SetTextAnimationTask();
        task.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_details, menu);
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

    //make sure that if the user goes back the pencil typing sound will be stopped
    @Override
    protected void onPause() {
        super.onPause();
        task.cancel(true);

        mIngredientsAnimator.stop();
        mSpicesAnimator.stop();
    }

     @Override
    public void onClick(View view) {
        if (mPreparingBtn.getId() == view.getId()){
            //stop animating and set text directly
            mIngredientsAnimator.setText(mRecipe.getIngredients());
            mSpicesAnimator.setText(mRecipe.getSpices());

            Intent intent = new Intent(this,HowToPrepareActivity.class);
            intent.putExtra("Recipe", mRecipe);

            startActivity(intent);
        }
    }

    private void setContent(final Recipe recipe){
        if (recipe != null){
            mNameTextView.setText(recipe.getName());
            mIngredientsAnimator.animateText(recipe.getIngredients());
            mSpicesAnimator.animateText(recipe.getSpices());

        }
    }

    private class SetTextAnimationTask extends AsyncTask<Void,TypeWriterAnimator,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            //animate spices after the ingredients are already set
            for(TypeWriterAnimator animator : animatorsList ){
                publishProgress(animator);
                //add a little delay before animating each textView

                try {
                    Thread.sleep(mRecipe.getIngredients().length() * (mAppBase.getAnimatorDelay() + 10));
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(TypeWriterAnimator... values) {
            if (values[0].getId() == mIngredientsAnimator.getId()){
                mIngredientsAnimator.animateText(mRecipe.getIngredients());
            }
            else if(values[0].getId() == mSpicesAnimator.getId()){
                mSpicesAnimator.animateText(mRecipe.getSpices());
            }
        }
    }
}
