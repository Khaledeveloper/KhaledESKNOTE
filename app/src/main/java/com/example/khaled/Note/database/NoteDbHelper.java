package com.example.khaled.Note.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.khaled.Note.database.NoteDbSchema.NoteTable;

/**
 * Created by khaled on 22/10/2017.
 */

public class NoteDbHelper extends SQLiteOpenHelper {
    private static final String TAG ="NoteDbHelper";
    private static final int VERSION = 2;
    private static final String DATABASE_NAME = "crimeDB";

    public NoteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
//the space after table is very important!!!!!!
        db.execSQL("create table "+ NoteTable.NAME+"("+
        "_id integer primary key autoincrement,"+
                NoteTable.Cols.UUID+","+
                NoteTable.Cols.TITLE+","+
                NoteTable.Cols.CONTENT+","+
                NoteTable.Cols.DATE+","+
                NoteTable.Cols.SOLVED+","+
                NoteTable.Cols.CONTACTNUMBER+","+
                        NoteTable.Cols.FOLDER+","+
                NoteTable.Cols.TITLECOLOR+","+
                        NoteTable.Cols.CONTENTCOLOR+","+
                        NoteTable.Cols.TITLESIZE+","+
                        NoteTable.Cols.CONTENTSIZE+","+
                        NoteTable.Cols.NOTEBACKGROUND+","+
                        NoteTable.Cols.TOOLBARLISTCOLOR+","+
                        NoteTable.Cols.TOOLBARNOTECOLOR+
                        ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
