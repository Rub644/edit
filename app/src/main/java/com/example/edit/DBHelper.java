package com.example.edit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "Login2.db";
    public static final String TABLENAME = "users";
    public static final String COL1 = "username";
    public static final String COL2 = "password";

    public DBHelper(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create Table " + TABLENAME + "(" + COL1 + " TEXT primary key, " + COL2 + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop Table if exists " + TABLENAME);

    }

    public Boolean insertData(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL1, username);
        contentValues.put(COL2, password);
        long result = MyDB.insert(TABLENAME, null, contentValues);
        if(result==-1) return false;
        return true;
    }
    public Boolean checkUsername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from " + TABLENAME + " where " + COL1 + " = ?", new
                String[]{username});
        if (cursor.getCount() > 0) return true;
        return false;
    }
    public Boolean checkUsernamePassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from " + TABLENAME + " where " + COL1 + " = ? and " + COL2 +
                " = ?", new String[] {username,password});
        if(cursor.getCount()>0) return true;
        return false;
    }

    public String getUsername() {
        String username = "";
        Cursor cursor = this.getReadableDatabase().query(
                TABLENAME,               // Table name
                new String[] { COL1 },   // Columns to retrieve
                null,                    // Selection criteria (null for all rows)
                null,                    // Selection arguments (null, as selection is null)
                null,                    // Group by (null, if not used)
                null,                    // Having (null, if not used)
                null                     // Order by (null, if not used)
        );

        if (cursor.moveToFirst()) {
            do {
                username = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return username;
    }



}
