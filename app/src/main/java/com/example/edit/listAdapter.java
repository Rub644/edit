package com.example.edit;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;


public class listAdapter extends ArrayAdapter<com.example.edit.Post> {
    String string="helo";
    private Context mcontext;
    int mresource;
    private DataBaseHelper db;
    private static class ViewHolder{
        ImageView img;
        TextView txt;

    }

    public listAdapter (Context context, int resource, ArrayList<com.example.edit.Post>objects, DataBaseHelper db) {
        super(context,resource,objects);
        mcontext= context;
        mresource= resource;
        this.db=db;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Bitmap img = getItem(position).getImage();
        String StringIO = getItem(position).getPost();


        com.example.edit.Post post = new com.example.edit.Post(-1, img, StringIO);


        View result ;
        ViewHolder holder;

        if(convertView==null){
            LayoutInflater inflater=LayoutInflater.from(mcontext);
            convertView= inflater.inflate(mresource,parent,false);
            holder=new ViewHolder();
            holder.img=(ImageView) convertView.findViewById(R.id.imageView);
            holder.txt=(TextView) convertView.findViewById(R.id.textView);
            ImageButton deleteButton = convertView.findViewById(R.id.delbtn);
            ImageButton editButton = convertView.findViewById(R.id.editbtn);

            deleteButton.setTag(position);
            editButton.setTag(position);



            //  OnClickListener for the delete button
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int clickedPosition = (int) v.getTag();
                    com.example.edit.Post postToDelete = getItem(clickedPosition);
                    db.DeleteOne(postToDelete);
                    remove(postToDelete); // Remove the item from the adapter
                    notifyDataSetChanged(); // Notify the adapter of the data change
                }
            });

            //  OnClickListener for the edit button
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int clickedPosition = (int) v.getTag();
                    com.example.edit.Post postToEdit = getItem(clickedPosition);
                    AlertDialog.Builder mydialog= new AlertDialog.Builder(listAdapter.this.getContext());
                    mydialog.setTitle("Edit the text");
                    final EditText editText=new EditText(listAdapter.this.getContext());
                    editText.setInputType(InputType.TYPE_CLASS_TEXT);
                    mydialog.setView(editText);

                    mydialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            string= editText.getText().toString();
                        } });

                    mydialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    mydialog.show();

                    db.editOne(postToEdit,string);

                    notifyDataSetChanged(); // Notify the adapter of the data change


                }
            });


            result=convertView;
            convertView.setTag(holder);
        }
        else {
            holder=(ViewHolder) convertView.getTag();
            result=convertView;
        }
        holder.img.setImageBitmap(post.getImage());
        holder.txt.setText(post.getPost());
        return convertView;
    }
}