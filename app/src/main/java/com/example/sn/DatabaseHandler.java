package com.example.sn;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dips25 on 1/5/2017.
 */

class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="Noting";
    private static final String TABLE_NOTES="Notes";

    private static final String NOTES_ID="id";
    private static final String NOTES_TITLE="title";
    private static final String NOTES_DESCRIPTION="description";

    private static final String TABLE_NOTES_CREATE="create table Notes (id integer primary key not null, " + "title text not null,description text not null);";
    private static final String TABLE_NOTES_DROP="drop table if exists Notes";

    DatabaseHandler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_NOTES_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TABLE_NOTES_DROP);
        onCreate(db);

    }
   public void addNote(NoteC noteC) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NOTES_TITLE, noteC.getTitle());
        values.put(NOTES_DESCRIPTION, noteC.getDescription());

        db.insert(TABLE_NOTES, null, values);
        db.close();
    }

   public void updateNote(NoteC noteC){

        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(NOTES_TITLE,noteC.getTitle());
        values.put(NOTES_DESCRIPTION,noteC.getDescription());

        db.update(TABLE_NOTES,values,NOTES_ID+"=?",new String[]{String.valueOf(noteC.getId())});
        db.close();


    }

   public void deleteNote(String id){

        SQLiteDatabase db=this.getWritableDatabase();
        String deleteQuery="delete from Notes where id="+id;
        db.execSQL(deleteQuery);
        db.close();

    }

    List<NoteC> getAllNotes(){
        List<NoteC>notes=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        String selectQuery="select * from Notes";
        Cursor cursor=db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                NoteC noteC=new NoteC();
                noteC.setId(Integer.parseInt(cursor.getString(0)));
                noteC.setTitle(cursor.getString(1));
                noteC.setDescription(cursor.getString(2));
                notes.add(noteC);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;
    }
}
