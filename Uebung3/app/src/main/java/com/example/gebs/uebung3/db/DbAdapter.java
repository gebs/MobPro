package com.example.gebs.uebung3.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by gebs on 3/16/17.
 */

public class DbAdapter {
    private final DbHelper dbHelper;
    private SQLiteDatabase db;
    static String DB_NAME = "MyDB";
    static int DB_Version = 1;

    public DbAdapter(final Context context) {
        dbHelper = new DbHelper(context);
    }

    public void open() {
        if (db == null || !db.isOpen()) {
            db = dbHelper.getWritableDatabase();
        }

    }

    public void close() {
        dbHelper.close();
    }

    public ArrayList<Note> getNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        final Cursor result = db.query(Note.TABLENAME, new String[]{Note.IDFIELD, Note.TITLE_FIELD, Note.TEXT_FIELD, Note.DATE_FIELD}, null, null, null, null, null);
        if (result.getCount() > 0) {
            result.moveToNext();
            while (!result.isAfterLast()) {
                notes.add(getNextNote(result));
            }
            return notes;
        } else
            return new ArrayList<>();
    }

    public Note getNote(final long id) {
        Note note = null;
        final Cursor result = db.query(Note.TABLENAME, new String[]{Note.IDFIELD, Note.TITLE_FIELD, Note.TEXT_FIELD, Note.DATE_FIELD}, Note.IDFIELD + "=" + id
                , null, null, null, null);
        final boolean found = result.moveToFirst();
        if (found)
            note = getNextNote(result);

        result.close();
        return note;

    }

    private Note getNextNote(final Cursor cursor) {
        final Note note = new Note(cursor.getInt(0), cursor.getString(1), cursor.getString(2), new Date(cursor.getLong(3)));
        cursor.moveToNext();
        return note;
    }

    public boolean insertNote(final Note note) {
        final ContentValues values = new ContentValues();
        values.put("title", note.getTitle());
        values.put("text", note.getText());
        values.put("datum", note.getDatum().getTime());
        final long id = db.insert(Note.TABLENAME, null, values);
        note.set_id(id);
        return true;
    }

    public boolean updateNote(final Note note) {
        final ContentValues values = new ContentValues();
        values.put("title", note.getTitle());
        values.put("text", note.getText());
        values.put("datum", note.getDatum().getTime());
        return db.update(Note.TABLENAME, values, Note.IDFIELD + "=" + note.get_id(), null) > 0;
    }

    public boolean deleteNote(final long id) {
        return db.delete(Note.TABLENAME, Note.IDFIELD + "=" + id, null) > 0;
    }
}
