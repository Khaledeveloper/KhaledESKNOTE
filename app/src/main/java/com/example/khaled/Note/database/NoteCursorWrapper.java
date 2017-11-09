package com.example.khaled.Note.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.example.khaled.Note.database.NoteDbSchema.NoteTable;

import com.example.khaled.Note.models.Note;

import java.util.Date;
import java.util.UUID;

/**
 * Created by khaled on 22/10/2017.
 */

public class NoteCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public NoteCursorWrapper(Cursor cursor) {

        //impelement in Crimlab
        super(cursor);
    }
    public Note getCrime(){//impelemnt in crimelap (for query)
        String uuidString = getString(getColumnIndex(NoteTable.Cols.UUID));
        String title = getString(getColumnIndex(NoteTable.Cols.TITLE));
        String content = getString(getColumnIndex(NoteTable.Cols.CONTENT));
        long date = getLong(getColumnIndex(NoteTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(NoteTable.Cols.SOLVED));
        String CONTACTNUMBER = getString(getColumnIndex(NoteTable.Cols.CONTACTNUMBER));


        Note note = new Note(UUID.fromString(uuidString));
        note.setTitle(title);
        note.setContent(content);
        note.setDate(new Date(date));
        note.setSolved(isSolved !=0);
        note.setContactnumber(CONTACTNUMBER);







        return note;



    }


}
