package com.example.edit;


import android.graphics.Bitmap;
import android.widget.Button;

public class Post {
    private int id;
    private Bitmap image;
    private String post;

    private Button delbtn;
    private Button editbtn;


    public Button getDel() {
        return delbtn;
    }

    public void setDel(Button del) {
        this.delbtn = del;
    }

    public Button getEdit() {
        return editbtn;
    }

    public void setEdit(Button edit) {
        this.editbtn = edit;
    }

    public Post(int id, Bitmap image, String post) {
        this.id = id;
        this.image = image;
        this.post = post;


    }

    public Bitmap getImage() {
        return image;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "Post{" +
                "image=" + image +
                ", id=" + id +
                ", post='" + post + '\'' +
                '}';
    }
}