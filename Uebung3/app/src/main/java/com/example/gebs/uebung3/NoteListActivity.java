package com.example.gebs.uebung3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.gebs.uebung3.ViewAdapter.NoteViewAdapter;
import com.example.gebs.uebung3.db.DbAdapter;
import com.example.gebs.uebung3.db.Note;

import java.util.ArrayList;

public class NoteListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private NoteViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    DbAdapter db;
    ArrayList<Note> src = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        db = new DbAdapter(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.rcNotes);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
      //  mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new NoteViewAdapter(src, new NoteViewAdapter.NoteAdapterClickListener() {
            @Override
            public void recyclerViewClick(long noteID) {
                onNoteClicked(noteID);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }
    private void onNoteClicked(long noteId){
        Intent i = new Intent(this,NoteActivity.class);
        i.putExtra("noteId",noteId);
        startActivity(i);
    }

    @Override
    protected void onResume(){
        super.onResume();
        db.open();

        src = db.getNotes();
        mAdapter.notes = src;
        mAdapter.notifyDataSetChanged();

    }
    @Override
    protected void onPause(){
        super.onPause();
        db.close();
    }
}
