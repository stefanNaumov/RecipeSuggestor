package Persistors;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;

/**
 * Created by Stefan on 11/27/2014.
 */
public class SQLiteDBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "recipesuggestordb";
    public static final int DB_CURRENT_VERSION = 1;
    public static final String TABLE_RECIPES = "recipes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_INGREDIENTS = "ingredients";
    public static final String COLUMN_SPICES = "spices";
    public static final String COLUMN_PREPARING = "preparing";
    public static final String COLUMN_TIMES_USED = "timesused";

    protected SQLiteDatabase db;

    private static final String DB_CREATE = "create table " + TABLE_RECIPES + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null unique, "
            + COLUMN_INGREDIENTS + " text not null, "
            + COLUMN_SPICES + " text not null, "
            + COLUMN_PREPARING + " text not null, "
            + COLUMN_TIMES_USED + " integer "+ ");";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS ";

    public SQLiteDBHelper(Context context) {
        super(context, DBNAME, null, DB_CURRENT_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DB_CREATE);
        Log.d("Database","CREATED");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(DROP_TABLE + TABLE_RECIPES);
        onCreate(sqLiteDatabase);
    }

    public void open() throws SQLException{
        db = getWritableDatabase();
    }

    public void close(){
        db.close();
    }
}
