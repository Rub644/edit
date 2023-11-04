package com.example.edit;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.Toast;

public class home extends AppCompatActivity {
    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontentlayout);
        db=new DataBaseHelper(this);
        ListView listView= (ListView) findViewById(R.id.listview1);
        ArrayList<Post> yarab= db.getli();
        listAdapter listAdapter=new listAdapter(home.this,R.layout.view_content_layout,yarab,db);
        listView.setAdapter(listAdapter);
    }

    public void onCustomToggleClick(View view){
        Toast.makeText(this,"CustomToggle",Toast.LENGTH_SHORT).show();
    }
}