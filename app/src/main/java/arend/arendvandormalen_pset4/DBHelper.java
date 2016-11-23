package arend.arendvandormalen_pset4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Arend on 2016-11-22.
 * Class that helps with database actions.
 *
 */

public class DBHelper extends SQLiteOpenHelper{

    // private static final NAMEN VAN DATABASES
    private static final String DATABASE_NAME = "toDoList.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE = "toDoList";

    private String task_id = "task";
    private String checked_id = "checked";


    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // onCreate
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){

        String CREATE_TABLE = "CREATE TABLE " + TABLE + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + task_id + " TEXT, " + checked_id + " TEXT)";
        sqLiteDatabase.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(sqLiteDatabase);

    }

    public void create(Task task){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(task_id, task.task);
        values.put(checked_id, task.checked);
        db.insert(TABLE, null, values);
        db.close();

    }

    public ArrayList<HashMap<String, String>> read(){

        String query = "SELECT _id, " + task_id + " , " + checked_id + " FROM " + TABLE;
        ArrayList<HashMap<String, String>> taskList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                HashMap<String, String> taskData = new HashMap<>();
                taskData.put("id", cursor.getString(cursor.getColumnIndex("_id")));
                taskData.put("task", cursor.getString(cursor.getColumnIndex(task_id)));
                taskData.put("checked", cursor.getString(cursor.getColumnIndex(checked_id)));
                taskList.add(taskData);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return taskList;
    }

    public void update(Task task){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(task_id, task.task);
        values.put(checked_id, task.checked);
        db.update(TABLE, values, "_id = ? ", new String[]{String.valueOf(task.id)});
        db.close();
    }

    public void delete(Task task){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE, "_id = ? ", new String[]{String.valueOf(task.id)});
        db.close();
    }

}
