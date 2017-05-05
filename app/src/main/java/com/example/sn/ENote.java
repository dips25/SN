package com.example.sn;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

/**
 * Created by Dips25 on 1/7/2017.
 */

public class ENote extends AppCompatActivity implements View.OnClickListener {

    EditText editTitle, editDesc;
    TextView tv, tv1;
    String title, description;

    Button saveb, deleteb;
    int Id;
    Boolean isUpdateNode;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enote);

        tv = (TextView) findViewById(R.id.textView4);
        tv1 = (TextView) findViewById(R.id.textView6);

        editTitle = (EditText) this.findViewById(R.id.editText);
        editDesc = (EditText) this.findViewById(R.id.editText2);

        saveb = (Button) findViewById(R.id.button2);
        saveb.setOnClickListener(this);

        deleteb = (Button) findViewById(R.id.button3);
        deleteb.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null && bundle.containsKey("source")) {
            if (bundle.getString("source").equalsIgnoreCase("editPress")) {
                isUpdateNode = true;
                Id = bundle.getInt("noteId");
                editTitle.setText(bundle.getString("noteTitle"));

                editDesc.setText(bundle.getString("noteDescription"));


                deleteb.setVisibility(View.VISIBLE);
            } else if (bundle.getString("source").equalsIgnoreCase("addPress")) {

                isUpdateNode = false;
                deleteb.setVisibility(View.GONE);
            } else {
                Toast.makeText(this, "Invalid Arg", Toast.LENGTH_LONG).show();
                super.onBackPressed();

            }
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2 = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:
                saveNote();
                break;
            case R.id.button3:
                deleteNode();
                break;

        }
    }


    public void saveNote() {
        NoteC notec=new NoteC();

        title = editTitle.getText().toString();
        description = editDesc.getText().toString();




        if (!isValidNote()) {
            return  ;
        } else {
            notec.setTitle(title);
            notec.setDescription(description);

            DatabaseHandler databasehandler = new DatabaseHandler(this);
            if (isUpdateNode) {
                notec.setId(Id);

                databasehandler.updateNote(notec);
                Toast.makeText(this, "Note Updated Successfully", Toast.LENGTH_LONG).show();

            } else  {


                databasehandler.addNote(notec);
                Toast.makeText(this, "Note Added Successfully", Toast.LENGTH_LONG).show();

            }


            List<NoteC> notes = databasehandler.getAllNotes();
            Note.notesAdapter.clear();
            Note.notesAdapter.addAll(notes);
            Note.notesAdapter.notifyDataSetChanged();
            super.onBackPressed();
        }
    }

    private void deleteNode() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Delete Node")
                .setMessage("Sure")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHandler databaseHandler = new DatabaseHandler(ENote.this);


                        databaseHandler.deleteNote(String.valueOf(Id));
                        finish();


                        List<NoteC> notes = databaseHandler.getAllNotes();
                        Note.notesAdapter.clear();
                        Note.notesAdapter.addAll(notes);
                        Note.notesAdapter.notifyDataSetChanged();
                        ENote.this.onBackPressed();
                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }





    @NonNull
    private Boolean isValidNote() {
        if (title.isEmpty() && description.isEmpty()) {
            Toast.makeText(this, "Please Enter Title and Description", Toast.LENGTH_LONG).show();
            return false;
        } else if (title.isEmpty()) {
            Toast.makeText(this, "Please Enter Title", Toast.LENGTH_LONG).show();
            return false;
        } else if (description.isEmpty()) {
            Toast.makeText(this, "Please Enter Description", Toast.LENGTH_LONG).show();
            return false;
        }


        return true;
    }
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ENote Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2.connect();
        AppIndex.AppIndexApi.start(client2, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client2, getIndexApiAction());
        client2.disconnect();
    }
}















