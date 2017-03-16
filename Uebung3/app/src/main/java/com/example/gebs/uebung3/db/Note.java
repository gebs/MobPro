package com.example.gebs.uebung3.db;

import java.util.Date;

/**
 * Created by gebs on 3/16/17.
 */

public class Note {
    static String TABLENAME = "tbl_notes";
    private long _id;
    static String IDFIELD = "_id";
    private String title;
    static String TITLE_FIELD = "title";
    private String text;
    static  String TEXT_FIELD = "text";
    private Date datum;
    static  String DATE_FIELD = "datum";

    public Note(int _id, String title, String text, Date datum) {
        this._id = _id;
        this.title = title;
        this.text = text;
        this.datum = datum;
    }
    public Note(){}

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }
}
