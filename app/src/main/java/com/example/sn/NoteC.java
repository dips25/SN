package com.example.sn;

import android.widget.ArrayAdapter;

import java.util.List;

import static android.os.Build.VERSION_CODES.N;

/**
 * Created by Dips25 on 1/5/2017.
 */

public class NoteC {

    private int id;
    private String title = "";
    private String description = "";


    public NoteC() {
    }

        public int getId () {
            return id;
        }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}







