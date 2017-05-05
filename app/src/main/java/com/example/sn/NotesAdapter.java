package com.example.sn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Dips25 on 1/7/2017.
 */

public class NotesAdapter extends ArrayAdapter<NoteC> {
    private List<NoteC> noteCList;
    private Context context;

    public NotesAdapter(Context context,List<NoteC> noteCList){
        super(context, R.layout.list_notes,noteCList);
        this.noteCList=noteCList;
        this.context=context;


    }

    @NonNull
    public View getView(final int position, View convertView, @NonNull ViewGroup parent){
        View v=convertView;

        if(v==null){
            LayoutInflater v1=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=v1.inflate(R.layout.list_notes,null);
        }

        final NoteC noteC=noteCList.get(position);
        if(noteC!=null){

            TextView title=(TextView) v.findViewById(R.id.textView2);
            TextView description=(TextView) v.findViewById(R.id.textView3);
            TextView index=(TextView) v.findViewById(R.id.textView);
            if(title!=null){
                title.setText(noteC.getTitle());
                index.setText((position)+".");
            }
            if (description!=null){
                description.setText(noteC.getDescription());
            }
        }

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent=new Intent(context,ENote.class);
                Bundle bundle=new Bundle();
                bundle.putString("source","editpress");

                assert noteC != null;
                bundle.putString("noteTitle",noteC.getTitle());
                bundle.putString("noteDescription",noteC.getDescription());
                bundle.putInt("noteId",noteC.getId());
                myIntent.putExtras(bundle);
                context.startActivity(myIntent);

            }
        });
            return v;
        }

    }

