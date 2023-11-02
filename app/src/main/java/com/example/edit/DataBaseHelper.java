package com.example.edit;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String Post_TABLE = "Post_TABLE";
    public static final String COLUMN_POST = "post";
    public static final String COLUMN_ID = "id";
    public static final String COL_IMAGE = "image";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "nn.db", null, 1);
    }

    // when creating the database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "Create TABLE " + Post_TABLE + "  ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_IMAGE + " BLOB, " +
                COLUMN_POST + " TEXT )";
        sqLiteDatabase.execSQL(createTable);
    }

    // when upgrading
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Post_TABLE);
        onCreate(db);
    }

    public boolean addOne(Post post) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_POST, post.getPost());

        if (post==null) return false;

        Bitmap img = post.getImage();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 100, stream);
        cv.put(COL_IMAGE, stream.toByteArray());


        long insert = db.insert(Post_TABLE, null, cv);
        db.close();

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }


    public Bitmap getImagedb(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Bitmap b = null;
        Cursor cursor = db.rawQuery("SELECT * FROM " + Post_TABLE + " WHERE " + COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        if (cursor.moveToNext()) {
            int imageColumnIndex = cursor.getColumnIndex(COL_IMAGE);

            byte[] imag = cursor.getBlob(imageColumnIndex);
            b = BitmapFactory.decodeByteArray(imag, 0, imag.length);
        }
        cursor.close();
        return b;
    }

    public String getPostdb(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String s = "";
        Cursor cursor = db.rawQuery("SELECT * FROM " + Post_TABLE + " WHERE " + COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        if (cursor.moveToNext()) {
            int postColumnIndex = cursor.getColumnIndex(COLUMN_POST);

            s = cursor.getString(postColumnIndex);
        }
        cursor.close();
        return s;
    }


    public ArrayList<Post> getli() {
        ArrayList<Post> postList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Post_TABLE, null);

        int idColumnIndex = cursor.getColumnIndex(COLUMN_ID);
        int imageColumnIndex = cursor.getColumnIndex(COL_IMAGE);
        int postColumnIndex = cursor.getColumnIndex(COLUMN_POST);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(idColumnIndex);
            if (id >= 0) {
                byte[] imag = cursor.getBlob(imageColumnIndex);
                String post = cursor.getString(postColumnIndex);
                Bitmap b = BitmapFactory.decodeByteArray(imag, 0, imag.length);
                Post p = new Post(id, b, post);
                postList.add(p);
            }
        }

        cursor.close();
        return postList;
    }

    public boolean DeleteOne(Post post) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + Post_TABLE + " WHERE " + COLUMN_ID + " = " + post.getId();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public void editOne(Post postToEdit, String st2) {
        postToEdit.setPost(st2);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_POST, postToEdit.getPost());
        String whereClause = COLUMN_ID + " = ?";
        String[] whereArgs = {String.valueOf(postToEdit.getId())};

        int rowCount = db.update(Post_TABLE, cv, whereClause, whereArgs);
    }






}