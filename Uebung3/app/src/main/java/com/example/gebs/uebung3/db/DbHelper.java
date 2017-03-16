package com.example.gebs.uebung3.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gebs on 3/16/17.
 */

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(final Context context){
        super(context,DbAdapter.DB_NAME, null,DbAdapter.DB_Version);
    }

    @Override
    public void onCreate(final SQLiteDatabase db){
        //creates DB
        db.execSQL("CREATE TABLE "+Note.TABLENAME+"(datum INTEGER not null,title TEXT not null, text TEXT not null,_id INTEGER PRIMARY KEY)");
    }
    @Override
    public  void onUpgrade(SQLiteDatabase db, int old,int n){

    }
}
