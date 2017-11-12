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

        String Folder = getString(getColumnIndex(NoteTable.Cols.FOLDER));
        int TitleColor = getInt(getColumnIndex(NoteTable.Cols.TITLECOLOR));
        int ContentColor = getInt(getColumnIndex(NoteTable.Cols.CONTENTCOLOR));
        int TitleSize = getInt(getColumnIndex(NoteTable.Cols.TITLESIZE));
        int ContentSize = getInt(getColumnIndex(NoteTable.Cols.CONTENTSIZE));
        int NoteBackground = getInt(getColumnIndex(NoteTable.Cols.NOTEBACKGROUND));
        int ToolbarListColor = getInt(getColumnIndex(NoteTable.Cols.TOOLBARLISTCOLOR));
        int ToolbarNoteColor = getInt(getColumnIndex(NoteTable.Cols.TOOLBARNOTECOLOR));





        Note note = new Note(UUID.fromString(uuidString));
        note.setTitle(title);
        note.setContent(content);
        note.setDate(new Date(date));
        note.setSolved(isSolved !=0);
        note.setContactnumber(CONTACTNUMBER);

        note.setFolder(Folder);
        note.setTitleColor(TitleColor);
        note.setContentColor(ContentColor);
        note.setTitlSize(TitleSize);
        note.setContentSize(ContentSize);
        note.setNoteBackground(NoteBackground);
        note.setToolbarListColor(ToolbarListColor);
        note.setToolbarNoteColor(ToolbarNoteColor);







        return note;



    }


}
