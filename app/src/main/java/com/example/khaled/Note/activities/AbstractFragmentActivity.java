package com.example.khaled.Note.activities;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;


import com.example.khaled.Note.R;

/**
 * Created by khaled on 16/10/2017.
 */

public abstract class AbstractFragmentActivity extends AppCompatActivity {
    protected abstract Fragment creatFragment();

    @LayoutRes
    protected int getLayoutResID(){
        return R.layout.activity_main;
        //alias resource

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.NoteListFrameLayoutID);
        if (fragment==null){
            fragment = creatFragment();
            fm.beginTransaction()
                    .add(R.id.NoteListFrameLayoutID, fragment)
                    .commit();
        }
    }
}