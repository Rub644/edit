package com.example.edit;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    Button btn_add, btn_view;
    EditText et_name, editText,editTextText2;
    ListView lv_StudentList;
    ArrayAdapter studentArrayAdapter;
    DataBaseHelper dataBaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // on create, give value
        btn_add = findViewById(R.id.btn_add);
        btn_view = findViewById(R.id.btn_view);
        editText= findViewById(R.id.editText);
        editTextText2= findViewById(R.id.editTextText2);

        et_name = findViewById(R.id.et_name);
        lv_StudentList = findViewById(R.id.lv_StudentList);
        dataBaseHelper = new DataBaseHelper(MainActivity.this);
        ShowPostOnListView(dataBaseHelper);
        //Listeners:
        btn_view.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v){
                dataBaseHelper = new DataBaseHelper(MainActivity.this);
                ShowPostOnListView(dataBaseHelper);
                //Toast.makeText(MainActivity.this, everyone.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        btn_add.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create model
                Post post;
                try {
                    post = new Post(-1,
                            et_name.getText().toString()
                    );
                    Toast.makeText(MainActivity.this,
                            post.toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Enter Valid input",
                            Toast.LENGTH_SHORT).show();
                    post = new Post(-1, "ERROR");
                }
                DataBaseHelper dataBaseHelper = new
                        DataBaseHelper(MainActivity.this);
                boolean b = dataBaseHelper.addOne(post);
                Toast.makeText(MainActivity.this, "SUCCESS= "+ b,
                        Toast.LENGTH_SHORT).show();
                ShowPostOnListView(dataBaseHelper);
            }
        });
        lv_StudentList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                String st=editText.getText().toString();
                if(st.equals("delete")){
                    Post clickedStudent= (Post) parent.getItemAtPosition(position);
                    dataBaseHelper.DeleteOne(clickedStudent);
                    ShowPostOnListView(dataBaseHelper);
                    Toast.makeText(MainActivity.this,"deleted "+ clickedStudent.toString(), Toast.LENGTH_SHORT).show();}

                if(st.equals("edit")){
                    Post clickedStudent= (Post) parent.getItemAtPosition(position);
                    String st2=editTextText2.getText().toString();

                    dataBaseHelper.editOne(clickedStudent,st2);
                    ShowPostOnListView(dataBaseHelper);
                    Toast.makeText(MainActivity.this,"edited "+ clickedStudent.toString(), Toast.LENGTH_SHORT).show();}

            }
        });
    }

    private void ShowPostOnListView(DataBaseHelper dataBaseHelper) {
        studentArrayAdapter = new
                ArrayAdapter<Post>(MainActivity.this,
                android.R.layout.simple_list_item_1, dataBaseHelper.getEveryone());
        lv_StudentList.setAdapter(studentArrayAdapter);
    }
}