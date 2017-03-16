package com.example.gebs.uebung3;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.gebs.uebung3.db.DbAdapter;
import com.example.gebs.uebung3.db.Note;

import org.w3c.dom.Text;

import java.util.Date;

public class NoteActivity extends AppCompatActivity {

    long noteId;
    private TextInputEditText txtTitle;
    private TextInputEditText txtNote;
    private TextView lblTitle;
    private TextView lblNote;
    DbAdapter db;
    private Button btnSaveNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        noteId = getIntent().getLongExtra("noteId", -1);
        db = new DbAdapter(this);
        if (noteId < 0) {
            setContentView(R.layout.activity_edit_note);
            txtTitle = (android.support.design.widget.TextInputEditText) findViewById((R.id.txtTitle));
            txtNote = (TextInputEditText) findViewById(R.id.txtNote);
            btnSaveNote = (Button) findViewById(R.id.btnSaveNote);
            btnSaveNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveNote();
                }
            });
        } else {
            setContentView(R.layout.activity_show_note);
            lblTitle = (TextView) findViewById(R.id.lblTitle);
            lblNote = (TextView) findViewById(R.id.lblNote);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (noteId > 0) {
            MenuInflater mi = getMenuInflater();
            mi.inflate(R.menu.notes_menu, menu);
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (super.onOptionsItemSelected(menuItem)) {
            return true;
        }

        switch (menuItem.getItemId()) {
            case R.id.mDelete:
                deleteNote();
                break;
            default:
                break;

        }

        return true;
    }

    private void deleteNote() {
        db.deleteNote(noteId);
        this.finish();
    }

    private void saveNote() {
        Note note = new Note();
        note.setTitle(this.txtTitle.getText().toString());
        note.setText(this.txtNote.getText().toString());
        note.setDatum(new Date());
        db.insertNote(note);
        this.finish();
    }

    private void updateNote() {
        Note note = db.getNote(noteId);
        note.setTitle(this.txtTitle.getText().toString());
        note.setText(this.txtNote.getText().toString());
        db.updateNote(note);
    }

    private void loadNote() {
        if (noteId > 0) {
            Note note = db.getNote(noteId);
            if (note != null) {
                this.lblTitle.setText(note.getTitle());
                this.lblNote.setText(note.getText());
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        db.open();

        if (noteId > 0) {
            loadNote();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        db.close();
    }
}
