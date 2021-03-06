
package com.example.khaled.Note.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.khaled.Note.NoteFragment;

import java.util.UUID;

public class MainActivity extends AbstractFragmentActivity {

    public static String TAGACTIVITY ="MainActivity";

    private static final String Crime_ID_KEY ="com.example.khaled.crime.crimeID";
    private static final String FOLDER_NAME ="FOLDERNAME";


    public static Intent newIntent(Context context, UUID crimeID, String Folder){
        Intent intent = new Intent(context,MainActivity.class);
        intent.putExtra(Crime_ID_KEY,crimeID);
        intent.putExtra(FOLDER_NAME, Folder);
        return intent;

    }

//crimeActivity
    @Override
    protected Fragment creatFragment() {
        //to send it to method of newInstance for Argue
        UUID crimeID = (UUID)getIntent().getSerializableExtra(Crime_ID_KEY);
        String Folder = getIntent().getStringExtra(FOLDER_NAME);

        return NoteFragment.newInstance(crimeID, Folder);
    }




}
