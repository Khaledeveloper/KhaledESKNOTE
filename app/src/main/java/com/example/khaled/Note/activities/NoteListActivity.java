package com.example.khaled.Note.activities;

import android.support.v4.app.Fragment;

import com.example.khaled.Note.NoteListFragment;
import com.example.khaled.Note.R;

/**
 * Created by khaled on 16/10/2017.
 */

public class NoteListActivity extends AbstractFragmentActivity {
    @Override
    protected Fragment creatFragment() {
        return new NoteListFragment();
    }

    @Override
    protected int getLayoutResID() {
       // return R.layout.tablet_layout_activity;
        return R.layout.activity_masterdetail;
    }
}
