package com.example.khaled.Note.activities;

import android.support.v4.app.Fragment;

//import com.example.khaled.Note.R;
import com.example.khaled.Note.addnotefragment;

public class AddNote extends AbstractFragmentActivity {


    @Override
    protected Fragment creatFragment() {
        return new addnotefragment();
    }
}