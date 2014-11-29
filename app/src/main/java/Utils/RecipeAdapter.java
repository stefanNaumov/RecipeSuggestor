package Utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.stefan.recipesuggestor.R;

import org.w3c.dom.Text;

import java.util.List;

import Models.Recipe;

/**
 * Created by Stefan on 11/29/2014.
 */
public class RecipeAdapter extends ArrayAdapter<Recipe> {
    private Context context;
    private int layoutId;
    private List<Recipe> recipeList;

    public RecipeAdapter(Context context, int resource, List<Recipe> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutId = resource;
        this.recipeList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View currRow = convertView;
        RecipeHolder holder;
        Recipe recipe;

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        currRow = inflater.inflate(this.layoutId,parent,false);

        if (currRow == null){
            TextView nameTextView = (TextView)currRow.findViewById(R.id.recipe_row_name);

            holder = new RecipeHolder();
            holder.name = nameTextView;

            currRow.setTag(holder);
        }
        else{
            holder = (RecipeHolder)currRow.getTag();
        }

        recipe = this.recipeList.get(position);

        if (recipe != null){
            holder.name.setText(recipe.getName());
        }

        return currRow;
    }

    static class RecipeHolder{
        private TextView name;
    }
}
