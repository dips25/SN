package com.example.sn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class Note extends AppCompatActivity implements View.OnClickListener {

    Button button;
    DatabaseHandler databaseHandler;
    public static NotesAdapter notesAdapter;
    List<NoteC> noteCList;
    ListView noteListView;
    String Id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_note);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

        databaseHandler = new DatabaseHandler(this);
        noteCList = databaseHandler.getAllNotes();

        notesAdapter = new NotesAdapter(this, noteCList);

        noteListView = (ListView) findViewById(R.id.listnotes);
        try {
            noteListView.setAdapter(notesAdapter);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            

            }
        });
    }


    public void onClick(View v) {

        Intent myIntent = new Intent(Note.this, ENote.class);
        Bundle bundle = new Bundle();
        bundle.putString("source", "addpress");

        myIntent.putExtras(bundle);
        startActivity(myIntent);

    }
}









