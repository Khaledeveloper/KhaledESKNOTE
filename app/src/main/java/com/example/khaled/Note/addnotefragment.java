package com.example.khaled.Note;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.khaled.Note.activities.ViewPagerActivity;
import com.example.khaled.Note.models.Note;
import com.example.khaled.Note.models.NoteLab;


/**
 * A simple {@link Fragment} subclass.
 */
public class addnotefragment extends Fragment {


    public addnotefragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_addnotefragment, container, false);
    }


    public void AddNewCrime(){
        Note note = new Note();
      //  note.setFolder(Folder); //set folder before add crime
        NoteLab.get(getActivity()).addCrime(note);


    }

}
