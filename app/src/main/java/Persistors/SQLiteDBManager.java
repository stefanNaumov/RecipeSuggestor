package Persistors;

import android.content.ContentValues;
import android.content.Context;

import java.sql.SQLException;

/**
 * Created by Stefan on 11/28/2014.
 */
public class SQLiteDBManager  extends SQLiteDBHelper{

    public SQLiteDBManager(Context context) {
        super(context);
    }

    public void addRecord(String recipeName, String ingredients, String spices, String preparing){
        if (recipeName == null || recipeName.isEmpty() || ingredients == null || ingredients.isEmpty()
                || spices == null || spices.isEmpty() || preparing == null || preparing.isEmpty()){
            return;
        }

        try {
            open();

            ContentValues values = new ContentValues();
            values.put(SQLiteDBHelper.COLUMN_NAME,recipeName);
            values.put(SQLiteDBHelper.COLUMN_INGREDIENTS,ingredients);
            values.put(SQLiteDBHelper.COLUMN_SPICES,spices);
            values.put(SQLiteDBHelper.COLUMN_PREPARING,preparing);
            values.put(SQLiteDBHelper.COLUMN_TIMES_USED,0);

            db.insert(SQLiteDBHelper.TABLE_RECIPES,null,values);

            close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
