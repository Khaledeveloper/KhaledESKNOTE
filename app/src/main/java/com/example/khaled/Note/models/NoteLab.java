
package com.example.khaled.Note.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.example.khaled.Note.database.NoteCursorWrapper;
import com.example.khaled.Note.database.NoteDbHelper;
import com.example.khaled.Note.database.NoteDbSchema.NoteTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by khaled on 16/10/2017.
 */
public class NoteLab {
    private static NoteLab sNoteLab;
    private ArrayList<Note> mNotes;
    //new for database
    private SQLiteDatabase mSQLiteDatabase;
    Context mContext;

    public static NoteLab get(Context context){
        if (sNoteLab == null){
            sNoteLab = new NoteLab(context); //for private constructor below
        }

        return sNoteLab;
    }

    private NoteLab(Context context){
        //new for database
        //for DatabaseopenHelper class to write

        mContext = context.getApplicationContext();
        mSQLiteDatabase = new NoteDbHelper(mContext)
                .getWritableDatabase();


      //*********************************************

      //for loading emptylist to ad crime
      //  mNotes = new ArrayList<>();

      //************************************************



      //that for loading a dafult list
       /* for (int i =0 ;i <100 ; i++){
            Note crime = new Note();
            crime.setTitle("Note #" + i);
            crime.setSolved(i% 2 == 0);
            mNotes.add(crime);*/
        }

        //a static method for database to insert it
    public static ContentValues getContentValues(Note note){
        ContentValues contentValues = new ContentValues();
        contentValues.put(NoteTable.Cols.UUID, note.getId().toString());
        contentValues.put(NoteTable.Cols.TITLE, note.getTitle());
        contentValues.put(NoteTable.Cols.CONTENT, note.getContent());
        contentValues.put(NoteTable.Cols.DATE, note.getDate().getTime());
        contentValues.put(NoteTable.Cols.SOLVED, note.isSolved()?1:0);
        contentValues.put(NoteTable.Cols.CONTACTNUMBER, note.getContactnumber());

        return contentValues;
    }



    public List<Note> getCrimes(){
       List<Note> notes = new ArrayList<>();

        NoteCursorWrapper cursor  = queryCrimes(null, null);//query crime is below here

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            notes.add(cursor.getCrime());
            cursor.moveToNext();
        }
        cursor.close();

        return notes;





       //***********************************
       //this line was only before DataBase
       // return mNotes;
    }


    public Note getCrime(UUID id){


        NoteCursorWrapper cursor = queryCrimes(
                NoteTable.Cols.UUID +"= ?",
                new String[] {id.toString()}

        );

        try {
            if (cursor.getCount() == 0){
                return null;
            }

            cursor.moveToFirst();
            return cursor.getCrime();
        }finally {
            cursor.close();
        }


        /* this was before database
        for (Note crime: mNotes){
            if (crime.getId().equals(id)){
                return crime;
            }
        }
        return null;
        */
    }


    public void addCrime( Note c){
        //new for database
        ContentValues contentValues = getContentValues(c);

        mSQLiteDatabase.insert(NoteTable.NAME,null, contentValues);

        //**********************************************
        //this was for adding a crime before DataBase
      //  mNotes.add(c);
    }




    //a method  for database

    public void updateCrime(Note note){
        String uuidString = note.getId().toString();

        ContentValues contentValues = getContentValues(note);

        mSQLiteDatabase.update(NoteTable.NAME ,contentValues, NoteTable.Cols.UUID +"= ?",new String[] {uuidString});

    }


    public void deleteNote(Note note){

        String uuidString = note.getId().toString();

        ContentValues contentValues =getContentValues(note);

        mSQLiteDatabase.delete(NoteTable.NAME, NoteTable.Cols.UUID+"= ?" , new String[] {uuidString});

    }


    //a method for database

    private NoteCursorWrapper queryCrimes(String whereClause , String[] whereArgs){
        Cursor cursor =mSQLiteDatabase.query(
                NoteTable.NAME,
                null, //for columns , null select all columns
                whereClause,
                whereArgs,
                null,  //groupBy
                null, //having
                NoteTable.Cols.DATE+" DESC" //orderBy
        );



        return new NoteCursorWrapper(cursor);
    }

    //checking
    //check if there is external storage
    public File getPhotoFile( Note note){
        File externalFilesDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if ( externalFilesDir == null){
            return null;
        }

        return new File( externalFilesDir , note.getPhotoFilename());
    }





}
