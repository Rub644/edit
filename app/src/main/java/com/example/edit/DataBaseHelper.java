package com.example.edit;


   import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

        import androidx.annotation.Nullable;

        import java.util.ArrayList;
        import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String Post_TABLE = "Post_TABLE";
    public static final String COLUMN_POST= "COLUMN_POST";
    public static final String COLUMN_ID = "ID";
    public DataBaseHelper(@Nullable Context context) {
        super(context, "post.db", null, 1);
    }
    // when creating the database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "Create TABLE " + Post_TABLE + "  ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_POST + " TEXT )";
        sqLiteDatabase.execSQL(createTable);
    }
    // when upgrading
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
    public boolean addOne(Post post){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_POST, post.getPost());

        long insert = db.insert(Post_TABLE, null, cv);
        if(insert == -1){
            return false;
        }
        else {
            return true;
        }
    }
    public boolean DeleteOne(Post post){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString= "Delete From " + Post_TABLE + " WHERE " +
                COLUMN_ID + " = " + post.getId() ;
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            return true;
        } else{
            // nothing happens. no one is added.
            return false;
        }
        //close
    }





    public List<Post> getEveryone(){
        List<Post> returnList = new ArrayList<>();
        // get data from database
        String queryString = "Select * from "+ Post_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            // loop through cursor results
            do{
                int SID = cursor.getInt(0); // student ID
                String SName = cursor.getString(1);
                Post newPost = new Post(SID, SName);
                returnList.add(newPost);
            }while (cursor.moveToNext());
        } else{
            // nothing happens. no one is added.
        }
        //close
        cursor.close();
        db.close();
        return returnList;
    }

    public void editOne(Post clickedStudent, String st2) {
        clickedStudent.setPost(st2);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_POST, clickedStudent.getPost());
        String whereClause = COLUMN_ID + " = ?";
        String[] whereArgs = {String.valueOf(clickedStudent.getId())};

        int rowCount = db.update(Post_TABLE, cv, whereClause, whereArgs);
    }
}